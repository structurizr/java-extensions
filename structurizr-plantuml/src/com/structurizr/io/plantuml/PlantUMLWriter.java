package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.*;

import static java.lang.String.format;

public abstract class PlantUMLWriter {

    private final Map<String, String> skinParams = new LinkedHashMap<>();
    private final List<String> includes = new ArrayList<>();

    private boolean useSequenceDiagrams = false;

    private boolean includeDiagramMetadata = true;

    PlantUMLWriter() {
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void addIncludeFile(String file) {
        addIncludeFile(file, null);
    }

    public void addIncludeFile(String file, int id) {
        addIncludeFile(file, String.valueOf(id));
    }

    public void addIncludeFile(String file, String id) {
        if (id==null) {
            includes.add(format("!include %s", file));
        } else {
            includes.add(format("!include %s!%s", file, id));
        }
    }

    public void addIncludeURL(URI file) {
        addIncludeURL(file, null);
    }

    public void addIncludeURL(URI file, int id) {
        addIncludeURL(file, String.valueOf(id));
    }

    public void addIncludeURL(URI file, String id) {
        if (id==null) {
            includes.add(format("!includeurl %s", file));
        } else {
            includes.add(format("!includeurl %s!%s", file, id));
        }
    }

    public void clearIncludes() {
        includes.clear();
    }

    protected Map<String, String> getSkinParams() {
        return skinParams;
    }

    public void addSkinParam(String name, String value) {
        skinParams.put(name, value);
    }

    public void clearSkinParams() {
        skinParams.clear();
    }

    public boolean isUseSequenceDiagrams() {
        return useSequenceDiagrams;
    }

    public void setUseSequenceDiagrams(boolean useSequenceDiagrams) {
        this.useSequenceDiagrams = useSequenceDiagrams;
    }

    public boolean isIncludeDiagramMetadata() {
        return includeDiagramMetadata;
    }

    public void setIncludeDiagramMetadata(boolean includeDiagramMetadata) {
        this.includeDiagramMetadata = includeDiagramMetadata;
    }

    /**
     * Writes the views in the given workspace as PlantUML definitions, to the specified writer.
     *
     * @param workspace     the workspace containing the views to be written
     * @param writer        the Writer to write to
     */
    public final void write(Workspace workspace, Writer writer) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        if (writer == null) {
            throw new IllegalArgumentException("A writer must be provided.");
        }

        workspace.getViews().getSystemLandscapeViews().forEach(v -> write(v, writer));
        workspace.getViews().getSystemContextViews().forEach(v -> write(v, writer));
        workspace.getViews().getContainerViews().forEach(v -> write(v, writer));
        workspace.getViews().getComponentViews().forEach(v -> write(v, writer));
        workspace.getViews().getDynamicViews().forEach(v -> write(v, writer));
        workspace.getViews().getDeploymentViews().forEach(v -> write(v, writer));
    }

    /**
     * Gets a single view as a PlantUML diagram definition.
     *
     * @param view      the view to write
     * @return          the PlantUML definition as a String
     */
    public final String toString(View view) {
        StringWriter stringWriter = new StringWriter();
        write(view, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Creates PlantUML diagram definitions based upon the specified workspace.
     *
     * @param workspace     the workspace containing the views to be written
     * @return  a collection of PlantUML diagram definitions, one per view
     */
    public final Collection<PlantUMLDiagram> toPlantUMLDiagrams(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        Collection<PlantUMLDiagram> diagrams = new ArrayList<>();

        for (View view : workspace.getViews().getViews()) {
            StringWriter stringWriter = new StringWriter();
            write(view, stringWriter);

            diagrams.add(new PlantUMLDiagram(view.getKey(), view.getName(), stringWriter.toString()));
        }

        return diagrams;
    }

    /**
     * Writes a single view as a PlantUML diagram definition, to the specified writer.
     *
     * @param view      the view to write
     * @param writer    the Writer to write the PlantUML definition to
     */
    public final void write(View view, Writer writer) {
        if (view == null) {
            throw new IllegalArgumentException("A view must be provided.");
        }

        if (writer == null) {
            throw new IllegalArgumentException("A writer must be provided.");
        }

        if (SystemLandscapeView.class.isAssignableFrom(view.getClass())) {
            write((SystemLandscapeView) view, writer);
        } else if (SystemContextView.class.isAssignableFrom(view.getClass())) {
            write((SystemContextView) view, writer);
        } else if (ContainerView.class.isAssignableFrom(view.getClass())) {
            write((ContainerView) view, writer);
        } else if (ComponentView.class.isAssignableFrom(view.getClass())) {
            write((ComponentView) view, writer);
        } else if (DynamicView.class.isAssignableFrom(view.getClass())) {
            write((DynamicView) view, writer);
        } else if (DeploymentView.class.isAssignableFrom(view.getClass())) {
            write((DeploymentView) view, writer);
        }
    }

    protected void write(SystemLandscapeView view, Writer writer) {
        writeSystemLandscapeOrContextView(view, writer, view.isEnterpriseBoundaryVisible());
    }

    protected void write(SystemContextView view, Writer writer) {
        writeSystemLandscapeOrContextView(view, writer, view.isEnterpriseBoundaryVisible());
    }

    void writeSystemLandscapeOrContextView(View view, Writer writer, boolean showEnterpriseBoundary) {
        try {
            writeHeader(view, writer);

            boolean enterpriseBoundaryVisible;
            enterpriseBoundaryVisible =
                    showEnterpriseBoundary &&
                            (view.getElements().stream().map(ElementView::getElement).anyMatch(e -> e instanceof Person && ((Person)e).getLocation() == Location.Internal) ||
                                    view.getElements().stream().map(ElementView::getElement).anyMatch(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() == Location.Internal));

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof Person && ((Person)e).getLocation() != Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, false));

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() != Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, false));

            if (enterpriseBoundaryVisible) {
                String name = view.getModel().getEnterprise() != null ? view.getModel().getEnterprise().getName() : "Enterprise";
                writer.write("package \"" + name + "\" {");
                writer.write(System.lineSeparator());
            }

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof Person && ((Person)e).getLocation() == Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, true));

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() == Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, true));

            if (enterpriseBoundaryVisible) {
                writer.write("}");
                writer.write(System.lineSeparator());
            }

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void write(ContainerView view, Writer writer);

    protected abstract void write(ComponentView view, Writer writer);

    protected abstract void write(DynamicView view, Writer writer);

    void write(DeploymentView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof DeploymentNode && ev.getElement().getParent() == null)
                    .map(ev -> (DeploymentNode)ev.getElement())
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 0));

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void write(View view, DeploymentNode deploymentNode, Writer writer, int indent);

    String calculateIndent(int indent) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            buf.append("  ");
        }

        return buf.toString();
    }

    void write(View view, Element element, Writer writer, boolean indent) {
        write(view, element, writer, indent ? 1 : 0);
    }

    protected abstract void write(View view, Element element, Writer writer, int indent);

    String backgroundOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getBackground();
    }

    String strokeOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getStroke();
    }

    String colorOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getColor();
    }

    Shape shapeOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getShape();
    }

    String plantUMLShapeOf(View view, Element element) {
        Shape shape = view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getShape();

        if (element instanceof DeploymentNode) {
            return "node";
        }

        switch(shape) {
            case Person:
                return "actor";
            case Component:
                return "component";
            case Cylinder:
                return "database";
            case Folder:
                return "folder";
            case Ellipse:
            case Circle:
                return "storage";
            default:
                return "rectangle";
        }
    }

    String plantumlSequenceType(View view, Element element) {
        Shape shape = view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getShape();

        switch(shape) {
            case Box:
                return "participant";
            case Person:
                return "actor";
            case Cylinder:
                return "database";
            case Folder:
                return "collections";
            case Ellipse:
            case Circle:
                return "entity";
            default:
                return "participant";
        }
    }

    RelationshipStyle relationshipStyleOf(View view, Relationship relationship) {
        return view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship);
    }

    String idOf(Element e) {
        return e.getId();
    }

    String typeOf(View view, Element e, boolean includeMetadataSymbols) {
        String terminology = view.getViewSet().getConfiguration().getTerminology().findTerminology(e);
        String type = "";

        if (e instanceof Person) {
            type = terminology;
        } else if (e instanceof SoftwareSystem) {
            type = terminology;
        } else if (e instanceof Container) {
            Container container = (Container)e;
            type = terminology + (hasValue(container.getTechnology()) ? ": " + container.getTechnology() : "");
        } else if (e instanceof Component) {
            Component component = (Component)e;
            type = terminology + (hasValue(component.getTechnology()) ? ": " + component.getTechnology() : "");
        } else if (e instanceof DeploymentNode) {
            DeploymentNode deploymentNode = (DeploymentNode)e;
            type = terminology + (hasValue(deploymentNode.getTechnology()) ? ": " + deploymentNode.getTechnology() : "");
        } else if (e instanceof InfrastructureNode) {
            InfrastructureNode infrastructureNode = (InfrastructureNode)e;
            type = terminology + (hasValue(infrastructureNode.getTechnology()) ? ": " + infrastructureNode.getTechnology() : "");
        }

        if (includeMetadataSymbols) {
            return "[" + type + "]";
        } else {
            return type;
        }
    }

    boolean hasValue(String s) {
        return !StringUtils.isNullOrEmpty(s);
    }

    protected void writeHeader(View view, Writer writer) throws IOException {
        // Spaces in PlantUML ids can cause issues. Alternatively, id can be surrounded with double quotes
        writer.write(format("@startuml(id=%s)", view.getKey().replace(' ', '_')));
        writer.write(System.lineSeparator());

        for (String include : includes) {
            writer.write(include);
            writer.write(System.lineSeparator());
        }

        if (includeDiagramMetadata) {
            String viewTitle = view.getTitle();
            if (StringUtils.isNullOrEmpty(viewTitle)) {
                viewTitle = view.getName();
            }
            writer.write("title " + viewTitle);
            writer.write(System.lineSeparator());

            if (view.getDescription() != null && view.getDescription().trim().length() > 0) {
                writer.write("caption " + view.getDescription());
                writer.write(System.lineSeparator());
            }
        }

        writer.write(System.lineSeparator());

        if (!skinParams.isEmpty()) {
            writer.write(format("skinparam {%s", System.lineSeparator()));
            for (final String name : skinParams.keySet()) {
                writer.write(format("  %s %s%s", name, skinParams.get(name), System.lineSeparator()));
            }
            writer.write(format("}%s", System.lineSeparator()));
        }
    }

    protected void writeRelationships(View view, Writer writer) {
        view.getRelationships().stream()
                .sorted(Comparator.comparing(rv -> (rv.getRelationship().getSource().getName() + rv.getRelationship().getDestination().getName())))
                .forEach(r -> writeRelationship(view, r, writer));
    }

    protected abstract void writeRelationship(View view, RelationshipView relationshipView, Writer writer);

    protected void writeFooter(Writer writer) throws IOException {
        writer.write("@enduml");
    }

}