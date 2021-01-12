package com.structurizr.io.plantuml;

import static java.lang.String.format;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.structurizr.model.*;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.RelationshipView;
import com.structurizr.view.View;

/**
 * This writer extends the classical one to use the C4-PlantUML sprite library
 * available on GitHub.
 * 
 * To make full use of that sprite library, we use Structurizr properties to
 * tweak rendering.
 * 
 * It is possible to change relationships directions and rendering using the
 * {@link #C4_LAYOUT_DIRECTION} and {@link #C4_LAYOUT_MODE} mode properties.
 * 
 * @see <a href="https://github.com/plantuml-stdlib/C4-PlantUML">https://github.com/plantuml-stdlib/C4-PlantUML</a>
 * @author nicolas-delsaux
 *
 */
public class C4PlantUMLWriter extends BasicPlantUMLWriter {
	private static final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(C4PlantUMLWriter.class.getName());


	/**
	 * Various layout abilities as provided by C4-PlantUML
	 * @see <a href="https://github.com/plantuml-stdlib/C4-PlantUML/blob/master/LayoutOptions.md">https://github.com/plantuml-stdlib/C4-PlantUML/blob/master/LayoutOptions.md</a>
	 * @author nicolas-delsaux
	 */
	public static enum Layout {
		LAYOUT_TOP_DOWN,
		LAYOUT_LEFT_RIGHT,
		LAYOUT_WITH_LEGEND,
		LAYOUT_AS_SKETCH,
	}
	
	/**
	 * This property indicates to C4-PlantUML library which relationship type to
	 * use. Possible values are given in the {@link Directions} enum
	 */
	public static final String C4_LAYOUT_DIRECTION = "c4:layout:direction";
	/**
	 * This property indicates to C4-PlantUML library which relationship mode to
	 * use. Possible values are given in the {@link RelationshipModes} enum
	 */
	public static final String C4_LAYOUT_MODE = "c4:layout:mode";
	/**
	 * Defines the {@link Type} of component.
	 */
	public static final String C4_ELEMENT_TYPE = "c4:element:type";

	public static enum Type {
		Default, Db
	}

	public static enum Directions {
		Up, Down, Right, Left;

		public String forMacro() {
			return name().substring(0, 1);
		}
	}

	public static enum RelationshipModes {
		Rel, Neighbor, Back, Back_Neighbor, Lay
	}

	public static class NoMacroFound extends RuntimeException {

	}

	private abstract class C4ElementWriter<WrittenElement extends Element> {

		public void write(View view, WrittenElement element, Writer writer, String prefix) throws IOException {
			final String id = idOf(element);
			final String separator = System.lineSeparator();
			doWrite(view, element, writer, prefix, id, separator);
		}

		abstract void doWrite(View view, WrittenElement element, Writer writer, String prefix, String id,
				String separator) throws IOException;

	}

	private class PersonWriter extends C4ElementWriter<Person> {

		@Override
		void doWrite(View view, Person element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			String macro = null;
			switch(element.getLocation()) {
			case External:
				macro = "Person_Ext";
				break;
			default:
				macro = "Person";
			}
			writer.write(format("%s%s(%s, \"%s\", \"%s\")%s", prefix, 
					macro, id, element.getName(),
					element.getDescription(), separator));
		}
	}

	private class SoftwareSystemWriter extends C4ElementWriter<SoftwareSystem> {
		@Override
		void doWrite(View view, SoftwareSystem element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			boolean internal = !element.getLocation().equals(Location.External);
			Type type = Type.valueOf(element.getProperties().getOrDefault(C4_ELEMENT_TYPE, Type.Default.name()));
			String macro = String.format("System%s%s", 
					type==Type.Default ? "" : type.name(),
							internal ? "" : "_Ext");
			writer.write(format("%s%s(%s, \"%s\", \"%s\")%s", prefix, macro, id, element.getName(),
					element.getDescription(), separator));
		}
	}

	private class ContainerWriter extends C4ElementWriter<Container> {
		@Override
		void doWrite(View view, Container element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			Type type = Type.valueOf(element.getProperties().getOrDefault(C4_ELEMENT_TYPE, Type.Default.name()));
			String macro = String.format("Container%s", 
					type==Type.Default ? "" : type.name());
			writer.write(format("%s%s(%s, \"%s\", \"%s\", \"%s\")%s", prefix, macro, id, element.getName(),
					element.getTechnology(), element.getDescription(), separator));
		}
	}

	private class ComponentWriter extends C4ElementWriter<Component> {
		@Override
		void doWrite(View view, Component element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			Type type = Type.valueOf(element.getProperties().getOrDefault(C4_ELEMENT_TYPE, Type.Default.name()));
			String macro = String.format("Component%s", 
					type==Type.Default ? "" : type.name());
			writer.write(format("%s%s(%s, \"%s\", \"%s\", \"%s\")%s", prefix, macro, id, element.getName(),
					element.getTechnology(), element.getDescription(), separator));
		}
	}

	private class SoftwareSystemInstanceWriter extends C4ElementWriter<SoftwareSystemInstance> {
		@Override
		void doWrite(View view, SoftwareSystemInstance element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			if (!view.isElementInView(element)) {
				return;
			}

			boolean internal = !element.getSoftwareSystem().getLocation().equals(Location.External);
			Type type = Type.valueOf(element.getProperties().getOrDefault(C4_ELEMENT_TYPE,
					element.getSoftwareSystem().getProperties().getOrDefault(C4_ELEMENT_TYPE,Type.Default.name())
			));
			String macro = String.format("System%s%s",
					type==Type.Default ? "" : type.name(),
					internal ? "" : "_Ext");
			writer.write(format("%s%s(%s, \"%s\", \"%s\")%s", prefix, macro, id,
					element.getSoftwareSystem().getName(),
					element.getSoftwareSystem().getDescription(), separator));
		}
	}

	private class ContainerInstanceWriter extends C4ElementWriter<ContainerInstance> {
		@Override
		void doWrite(View view, ContainerInstance element, Writer writer, String prefix, String id, String separator)
				throws IOException {
			if (!view.isElementInView(element)) {
				return;
			}

			Type type = Type.valueOf(element.getProperties().getOrDefault(C4_ELEMENT_TYPE,
					element.getContainer().getProperties().getOrDefault(C4_ELEMENT_TYPE,Type.Default.name())
			));
			String macro = String.format("Container%s",
					type==Type.Default ? "" : type.name());
			writer.write(format("%s%s(%s, \"%s\", \"%s\", \"%s\")%s", prefix, macro, id,
					element.getContainer().getName(), element.getContainer().getTechnology(),
					element.getContainer().getDescription(), separator));
		}
	}

	public static Collection<String> inferC4PlantUMLLibraryFrom(String urlText) {
		try {
			URL url = canonicalizeUrl(urlText);
			// Now we're sure url base is correct, add the library fragments
			
			return Arrays.asList("C4.puml", "C4_Context.puml", "C4_Container.puml", "C4_Component.puml").stream()
					.map(file -> {
						try {
							return new URL(url, file);
						} catch(MalformedURLException e) {
							throw new RuntimeException(String.format("Unable to create C4-PlantUML libray object from %s", urlText), e);
						}
					})
					.map(fileUrl -> fileUrl.toExternalForm())
					.collect(Collectors.toList());
		} catch (MalformedURLException e) {
			throw new RuntimeException(String.format("Unable to create C4-PlantUML libray object from %s", urlText), e);
		}
	}

	static URL canonicalizeUrl(String urlText) throws MalformedURLException {
		URL url = new URL(urlText);
		if(url.getHost().equals("github.com")) {
			// The substring trick is here to remove first character (which by design is a "/")
			String path = url.getPath().substring(1);
			String[] fragments = path.split("/");
			if(fragments.length<2) {
				throw new RuntimeException(String.format("The path you gave (%s) in you URL is wrong, as it is not a GitHub user repo but %s", 
						path, urlText));
			} else {
				String user = fragments[0];
				String repo = fragments[1];
				String branch = "master";
				if(fragments.length>2) {
					if("tree".equals(fragments[2])) {
						if(fragments.length==4) {
							branch = fragments[3];
						}
					} else {
						throw new RuntimeException(String.format("We don't know how to parse url %s. Please enter a bug report.", urlText));
					}
				}
				url = new URL(url.getProtocol(), url.getHost(),
						String.format("/%s/%s/tree/%s/", user, repo, branch));
			}
			url = new URL(url.getProtocol(), "raw.githubusercontent.com",
					url.getPath().replace("/tree/", "/"));
		}
		return url;
	}

	public C4PlantUMLWriter() {
		this(Layout.LAYOUT_WITH_LEGEND, "https://github.com/plantuml-stdlib/C4-PlantUML");
	}
	/**
	 * Constructur providing a base url and a layout
	 * @param layout layout to use for each graph
	 * @param c4PlantUMLBaseUrl base url of repository. The following GitHub url kinds are supported
	 * <ul>
	 * <li>raw content (i.e. "https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")</li>
	 * <li>Official repository name with branch or tag (i.e. "https://github.com/plantuml-stdlib/C4-PlantUML/tree/master")</li>
	 * <li>Official repository name without branch. Master is assumed in that case
	 * (i.ie "https://github.com/plantuml-stdlib/C4-PlantUML/")</li>
	 * </ul>
	 * @see #inferC4PlantUMLLibraryFrom
	 */
	public C4PlantUMLWriter(Layout layout, String c4PlantUMLBaseUrl) {
		this(layout, inferC4PlantUMLLibraryFrom(c4PlantUMLBaseUrl));
	}
	/**
	 * Constructor providing the layout and the full list of includes to use
	 * @param layout layout to use for each graph
	 * @param c4PlantUMLIncludes full list of C4PlantUML brushes. It should include C4.puml, C4_Context.puml, C4_Container.puml and C4_Component.puml. 
	 */
	public C4PlantUMLWriter(Layout layout, Collection<String> c4PlantUMLIncludes) {
		super();

		try {
			for(String url : c4PlantUMLIncludes) {
				addIncludeURL(new URI(url));
			}
			getIncludes().add(String.format("%s()\n", layout.name()));
		} catch (URISyntaxException e) {
			logger.log(Level.SEVERE, "Using C4-PlantUML should not trigger URI error", e);
		}

		// the C4-PlantUML macro provides its own skinparams
		clearSkinParams();
	}

	@Override
	protected void writeContainerForSoftwareSystem(ContainerView view, Writer writer, BiConsumer<ContainerView, Writer> packageContentWriter) throws IOException {
		writer.write(
				String.format("System_Boundary(%s_boundary, %s) {", view.getSoftwareSystemId(), view.getSoftwareSystem().getName()));
		writer.write(System.lineSeparator());

		packageContentWriter.accept(view, writer);

		writer.write("}");
		writer.write(System.lineSeparator());
	}

	@Override
	protected void writeContainerForContainer(ComponentView view, Writer writer, BiConsumer<ComponentView, Writer> packageContentWriter) throws IOException {
		writer.write(
				String.format("Container_Boundary(%s_boundary, %s) {", view.getContainerId(), view.getContainer().getName()));
		writer.write(System.lineSeparator());

		packageContentWriter.accept(view, writer);

		writer.write("}");
		writer.write(System.lineSeparator());
	}

	/**
	 * We replace the write element method to use macros provided by C4-PlantUML
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void write(View view, Element element, Writer writer, int indent) {
		try {
			final String prefix = calculateIndent(indent);
			getWriterFor(element).write(view, element, writer, prefix);
		} catch (NoMacroFound noMacro) {
			super.write(view, element, writer, indent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private C4ElementWriter getWriterFor(Element element) {
		if (element instanceof Person) {
			return new PersonWriter();
		} else if (element instanceof SoftwareSystem) {
			return new SoftwareSystemWriter();
		} else if (element instanceof Container) {
			return new ContainerWriter();
		} else if (element instanceof SoftwareSystemInstance) {
			return new SoftwareSystemInstanceWriter();
		} else if (element instanceof ContainerInstance) {
			return new ContainerInstanceWriter();
		} else if (element instanceof Component) {
			return new ComponentWriter();
		}
		throw new NoMacroFound();
	}

	@Override
	protected void writeRelationship(View view, RelationshipView relationshipView, Writer writer) {
		Relationship relationship = relationshipView.getRelationship();
		Element source = relationship.getSource();
		Element destination = relationship.getDestination();

		if (relationshipView.isResponse() != null && relationshipView.isResponse()) {
			source = relationship.getDestination();
			destination = relationship.getSource();
		}

		try {
			final String separator = System.lineSeparator();
			String relationshipMacro = null;
			RelationshipModes mode = RelationshipModes.Rel;
			if (relationship.getProperties().containsKey(C4_LAYOUT_MODE)) {
				mode = RelationshipModes.valueOf(relationship.getProperties().get(C4_LAYOUT_MODE));
			}
			switch (mode) {
			case Lay:
			case Rel:
				relationshipMacro = mode.name();
				Directions direction = Directions.Down;
				if (relationship.getProperties().containsKey(C4_LAYOUT_DIRECTION)) {
					direction = Directions.valueOf(relationship.getProperties().get(C4_LAYOUT_DIRECTION));
				}
				relationshipMacro = String.format("%s_%s", relationshipMacro, direction.forMacro());
				break;
			default:
				relationshipMacro = String.format("Rel_%s", mode);
			}
			if (mode==RelationshipModes.Lay) {
				writer.write(format("%s(%s, %s)%s", relationshipMacro, idOf(source), idOf(destination), separator));
			} else if (relationship.getDescription() == null) {
				writer.write(format("%s(%s, %s)%s", relationshipMacro, idOf(source), idOf(destination), separator));
			} else {
				if (relationship.getTechnology() == null) {
					writer.write(format("%s(%s, %s, \"%s\")%s", relationshipMacro, idOf(source), idOf(destination), relationship.getDescription(), separator));
				} else {
					writer.write(format("%s(%s, %s, \"%s\", \"%s\")%s", relationshipMacro, idOf(source), idOf(destination), relationship.getDescription(), relationship.getTechnology(), separator));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}