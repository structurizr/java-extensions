package com.structurizr.io.mermaid;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import static java.lang.String.format;

/**
 * A writer that outputs diagram definitions that can be used to create diagrams
 * using mermaid (https://mermaidjs.github.io).
 *
 * System landscape, system context, container, component, dynamic and deployment diagrams are supported.
 * Deployment node -&gt; deployment node relationships are not rendered.
 */
public class MermaidWriter {

    private static final String ELEMENT_DEFINITION = "  %s%s%s\"<div style='font-weight: bold'>%s</div><div style='font-size: 70%%; margin-top: 0px'>%s</div><div style='font-size: 80%%; margin-top:10px'>%s</div>\"%s";
    private static final String RELATIONSHIP_DEFINITION = "  %s-%s \"<div>%s</div><div style='font-size: 70%%'>%s</div>\" %s->%s";
    private boolean useSequenceDiagrams = false;

    /**
     * Creates a new PlantUMLWriter, with some default skin params.
     */
    public MermaidWriter() {
    }

    public boolean isUseSequenceDiagrams() {
        return useSequenceDiagrams;
    }

    public void setUseSequenceDiagrams(boolean useSequenceDiagrams) {
        this.useSequenceDiagrams = useSequenceDiagrams;
    }

    /**
     * Writes the views in the given workspace as PlantUML definitions, to the specified writer.
     *
     * @param workspace     the workspace containing the views to be written
     * @param writer        the Writer to write to
     */
    public void write(Workspace workspace, Writer writer) {
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
     * Creates Mermaid diagram definitions based upon the specified workspace.
     *
     * @param workspace     the workspace containing the views to be written
     * @return  a collection of Mermaid diagram definitions, one per view
     */
    public Collection<MermaidDiagram> toMermaidDiagrams(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        Collection<MermaidDiagram> diagrams = new ArrayList<>();

        for (View view : workspace.getViews().getViews()) {
            StringWriter stringWriter = new StringWriter();
            write(view, stringWriter);

            diagrams.add(new MermaidDiagram(view.getKey(), view.getName(), stringWriter.toString()));
        }

        return diagrams;
    }

    /**
     * Gets a single view as a mermaid diagram definition.
     *
     * @param view      the view to write
     * @return          the Mermaid definition as a String
     */
    public String toString(View view) {
        StringWriter stringWriter = new StringWriter();
        write(view, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Writes a single view as a PlantUML diagram definition, to the specified writer.
     *
     * @param view      the view to write
     * @param writer    the Writer to write the PlantUML definition to
     */
    public void write(View view, Writer writer) {
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

    private void writeSystemLandscapeOrContextView(View view, Writer writer, boolean showEnterpriseBoundary) {
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
                    .forEach(e -> write(view, e, writer, 0));

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() != Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 0));

            if (enterpriseBoundaryVisible) {
                String name = view.getModel().getEnterprise() != null ? view.getModel().getEnterprise().getName() : "Enterprise";
                writer.write("  subgraph boundary [" + name + "]");
                writer.write(System.lineSeparator());
            }

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof Person && ((Person)e).getLocation() == Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, enterpriseBoundaryVisible ? 1 : 0));

            view.getElements().stream()
                    .map(ElementView::getElement)
                    .filter(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() == Location.Internal)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, enterpriseBoundaryVisible ? 1 : 0));

            if (enterpriseBoundaryVisible) {
                writer.write("  end");
                writer.write(System.lineSeparator());
                writer.write("  style boundary fill:#ffffff,stroke:#000000,color:#000000");
                writer.write(System.lineSeparator());
            }

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(ContainerView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Container))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 0));

            writer.write("  subgraph boundary [" + view.getSoftwareSystem().getName() + "]");
            writer.write(System.lineSeparator());

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof Container)
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 1));

            writer.write("  end");
            writer.write(System.lineSeparator());
            writer.write("  style boundary fill:#ffffff,stroke:#000000,color:#000000");
            writer.write(System.lineSeparator());

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(ComponentView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Component))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 0));

            writer.write("  subgraph boundary [" + view.getContainer().getName() + "]");
            writer.write(System.lineSeparator());

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof Component)
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 1));

            writer.write("  end");
            writer.write(System.lineSeparator());
            writer.write("  style boundary fill:#ffffff,stroke:#000000,color:#000000");
            writer.write(System.lineSeparator());

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(DynamicView view, Writer writer) {
        try {
            Set<Element> elements = new LinkedHashSet<>();
            for (RelationshipView relationshipView : view.getRelationships()) {
                elements.add(relationshipView.getRelationship().getSource());
                elements.add(relationshipView.getRelationship().getDestination());
            }

            if (useSequenceDiagrams) {
                writer.write("sequenceDiagram");
                writer.write(System.lineSeparator());

                /**
                 * sequenceDiagram
                 *     participant A as Alice
                 *     participant J as John
                 *     A->>J: Hello John, how are you?
                 *     J->>A: Great!
                 */
                for (Element element : elements) {
                    writer.write(calculateIndent(1) + "participant " + element.getId() + " as " + element.getName());
                    writer.write(System.lineSeparator());
                }

                for (RelationshipView relationshipView : view.getRelationships()) {
                    Relationship relationship = relationshipView.getRelationship();
                    writer.write(format(
                            "%s%s->>%s: %s",
                            calculateIndent(1),
                            relationship.getSourceId(),
                            relationship.getDestinationId(),
                            lines(hasValue(relationshipView.getDescription()) ? relationshipView.getDescription() : hasValue(relationship.getDescription()) ? relationship.getDescription() : "")
                    ));
                    writer.write(System.lineSeparator());
                }
            } else {
                writeHeader(view, writer, "LR");

                elements.stream()
                        .sorted(Comparator.comparing(Element::getName)).
                        forEach(e -> write(view, e, writer, 0));

                view.getRelationships().stream()
                        .sorted(Comparator.comparing(RelationshipView::getOrder))
                        .forEach(relationship -> {
                            try {
                                writer.write(
                                        format("  %s-->|\"<div style='font-weight: bold'>%s. %s</div><div style='font-size: 70%%'>%s</div>\"|%s",
                                                idOf(relationship.getRelationship().getSource()),
                                                relationship.getOrder(),
                                                lines(hasValue(relationship.getDescription()) ? relationship.getDescription() : hasValue(relationship.getRelationship().getDescription()) ? relationship.getRelationship().getDescription() : ""),
                                                !StringUtils.isNullOrEmpty(relationship.getRelationship().getTechnology()) ? "[" + relationship.getRelationship().getTechnology() + "]" : "",
                                                idOf(relationship.getRelationship().getDestination())
                                        )
                                );

                                writer.write(System.lineSeparator());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(DeploymentView view, Writer writer) {
        try {
            writeHeader(view, writer);
            writer.write(System.lineSeparator());

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof DeploymentNode && ev.getElement().getParent() == null)
                    .map(ev -> (DeploymentNode)ev.getElement())
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, 1));

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(View view, DeploymentNode deploymentNode, Writer writer, int indent) {
        try {
            String stroke = strokeOf(view, deploymentNode);
            String color = colorOf(view, deploymentNode);

            writer.write(
                    format("%ssubgraph %s [%s]",
                            calculateIndent(indent),
                            deploymentNode.getId(),
                            deploymentNode.getName()
                    )
            );

            writer.write(System.lineSeparator());

            List<DeploymentNode> children = new ArrayList<>(deploymentNode.getChildren());
            children.sort(Comparator.comparing(DeploymentNode::getName));
            for (DeploymentNode child : children) {
                write(view, child, writer, indent+1);
            }

            List<InfrastructureNode> infrastructureNodes = new ArrayList<>(deploymentNode.getInfrastructureNodes());
            infrastructureNodes.sort(Comparator.comparing(InfrastructureNode::getName));
            for (InfrastructureNode infrastructureNode : infrastructureNodes) {
                write(view, infrastructureNode, writer, indent+1);
            }

            List<ContainerInstance> containerInstances = new ArrayList<>(deploymentNode.getContainerInstances());
            containerInstances.sort(Comparator.comparing(ContainerInstance::getName));
            for (ContainerInstance containerInstance : containerInstances) {
                write(view, containerInstance, writer, indent+1);
            }

            writer.write(
                    format("%send", calculateIndent(indent))
            );
            writer.write(System.lineSeparator());

            writer.write(
                    format("%sstyle %s fill:#ffffff,stroke:%s,color:%s", calculateIndent(indent), deploymentNode.getId(), stroke, color));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String calculateIndent(int indent) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < indent; i++) {
            buf.append("  ");
        }

        return buf.toString();
    }

    protected void write(View view, Element element, Writer writer, int indent) {
        try {
            final String separator = System.lineSeparator();

            String nodeOpeningSymbol = "[";
            String nodeClosingSymbol = "]";

            Shape shape = shapeOf(view, element);
            if (element instanceof ContainerInstance) {
                shape = shapeOf(view, ((ContainerInstance)element).getContainer());
            }

            if (shape == Shape.RoundedBox) {
                nodeOpeningSymbol = "(";
                nodeClosingSymbol = ")";
            } else if (shape == Shape.Cylinder) {
                    nodeOpeningSymbol = "[(";
                    nodeClosingSymbol = ")]";
            }

            if (element instanceof ContainerInstance) {
                Container container = ((ContainerInstance) element).getContainer();
                writer.write(format(ELEMENT_DEFINITION,
                        calculateIndent(indent),
                        idOf(element),
                        nodeOpeningSymbol,
                        container.getName(), typeOf(container), lines(container.getDescription()),
                        nodeClosingSymbol
                ));
            } else {
                writer.write(format(ELEMENT_DEFINITION,
                        calculateIndent(indent),
                        idOf(element),
                        nodeOpeningSymbol,
                        element.getName(), typeOf(element), lines(element.getDescription()),
                        nodeClosingSymbol
                ));
            }
            writer.write(format("%s", separator));

            if (!StringUtils.isNullOrEmpty(element.getUrl())) {
                writer.write(format("click %s %s \"%s\"", idOf(element), element.getUrl(), element.getUrl()));
                writer.write(format("%s", separator));
            }

            if (element instanceof ContainerInstance) {
                Container container = ((ContainerInstance) element).getContainer();
                writer.write(format("  %sstyle %s fill:%s,stroke:%s,color:%s", calculateIndent(indent),idOf(element), backgroundOf(view, container), strokeOf(view, container), colorOf(view, container)));
            } else {
                writer.write(format("  %sstyle %s fill:%s,stroke:%s,color:%s", calculateIndent(indent),idOf(element), backgroundOf(view, element), strokeOf(view, element), colorOf(view, element)));
            }

            writer.write(format("%s", separator));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeSequenceElement(View view, Element element, Writer writer, boolean indent, String type) throws IOException {
        writer.write(format("%s%s \"%s\" as %s <<%s>> %s%s",
                indent ? "  " : "",
                type,
                element.getName(),
                idOf(element),
                typeOf(element),
                backgroundOf(view, element),
                System.lineSeparator()));
    }

    protected String lines(final String text) {
        StringBuilder buf = new StringBuilder();
        if (text != null) {
            final String[] words = text.trim().split("\\s+");

            final StringBuilder line = new StringBuilder();
            for (final String word : words) {
                if (line.length() == 0) {
                    line.append(word);
                } else if (line.length() + word.length() + 1 < 30) {
                    line.append(' ').append(word);
                } else {
                    buf.append(line.toString());
                    buf.append("<br />");
                    line.setLength(0);
                    line.append(word);
                }
            }
            if (line.length() > 0) {
                buf.append(line.toString());
            }
        }

        return buf.toString();
    }

    protected String backgroundOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getBackground();
    }

    protected String strokeOf(View view, Element element) {
        String stroke = view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getStroke();

        if (element instanceof DeploymentNode) {
            return stroke != null ? stroke : "#000000";
        } else {
            if (stroke != null) {
                return stroke;
            } else {
                java.awt.Color color = java.awt.Color.decode(backgroundOf(view, element));
                return String.format("#%06X", (0xFFFFFF & color.darker().getRGB())).toLowerCase();
            }
        }
    }

    protected String colorOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getColor();
    }

    protected Shape shapeOf(View view, Element element) {
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getShape();
    }

    protected RelationshipStyle relationshipStyleOf(View view, Relationship relationship) {
        return view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship);
    }

    protected void writeRelationships(View view, Writer writer) {
        view.getRelationships().stream()
                .map(RelationshipView::getRelationship)
                .filter(r -> !(r.getSource() instanceof DeploymentNode || r.getDestination() instanceof DeploymentNode))
                .sorted((r1, r2) -> (r1.getSource().getName() + r1.getDestination().getName()).compareTo(r2.getSource().getName() + r2.getDestination().getName()))
                .forEach(r -> writeRelationship(view, r, writer));
    }

    protected void writeRelationship(View view, Relationship relationship, Writer writer) {
        try {
            RelationshipStyle style = relationshipStyleOf(view, relationship);
            // solid: A-- text -->B
            // dotted: A-. text .->B

            if (style.getDashed() == null) {
                style.setDashed(true);
            }

            writer.write(
                    format(RELATIONSHIP_DEFINITION,
                            idOf(relationship.getSource()),
                            style.getDashed() ? "." : "-",
                            lines(relationship.getDescription()),
                            !StringUtils.isNullOrEmpty(relationship.getTechnology()) ? "[" + relationship.getTechnology() + "]" : "",
                            style.getDashed() ? "." : "-",
                            idOf(relationship.getDestination())
                    )
            );
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String idOf(Element e) {
        return e.getId();
    }

    protected String typeOf(Element e) {
        String openingMetadataSymbol = "[";
        String closingMetadataSymbol = "]";

        String type;

        if (e instanceof SoftwareSystem) {
            type = "Software System";
        } else if (e instanceof Container) {
            Container container = (Container)e;
            type = "Container" + (hasValue(container.getTechnology()) ? ": " + container.getTechnology() : "");
        } else if (e instanceof Component) {
            Component component = (Component)e;
            type = "Component" + (hasValue(component.getTechnology()) ? ": " + component.getTechnology() : "");
        } else if (e instanceof DeploymentNode) {
            DeploymentNode deploymentNode = (DeploymentNode)e;
            type = "Deployment Node" + (hasValue(deploymentNode.getTechnology()) ? ": " + deploymentNode.getTechnology() : "");
        } else if (e instanceof InfrastructureNode) {
            InfrastructureNode infrastructureNode = (InfrastructureNode)e;
            type = "Infrastructure Node" + (hasValue(infrastructureNode.getTechnology()) ? ": " + infrastructureNode.getTechnology() : "");
        } else if (e instanceof ContainerInstance) {
            Container container = ((ContainerInstance)e).getContainer();
            type = "Container" + (hasValue(container.getTechnology()) ? ": " + container.getTechnology() : "");
        } else {
            type = e.getClass().getSimpleName();
        }

        return openingMetadataSymbol + type + closingMetadataSymbol;
    }

    protected boolean hasValue(String s) {
        return s != null && s.trim().length() > 0;
    }

    protected void writeHeader(View view, Writer writer) throws IOException {
        String direction = "TB";

        if (view.getAutomaticLayout() != null) {
            switch (view.getAutomaticLayout().getRankDirection()) {
                case TopBottom:
                    direction = "TB";
                    break;
                case BottomTop:
                    direction = "BT";
                    break;
                case LeftRight:
                    direction = "LR";
                    break;
                case RightLeft:
                    direction = "RL";
                    break;
            }
        }

        writeHeader(view, writer, direction);
    }

    protected void writeHeader(View view, Writer writer, String direction) throws IOException {
        writer.write("graph " + direction);
        writer.write(System.lineSeparator());
        writer.write("  linkStyle default fill:#ffffff");
        writer.write(System.lineSeparator());
    }

    protected void writeFooter(Writer writer) throws IOException {
    }

}