package com.structurizr.io.dot;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Writes Structurizr views to Graphviz DOT definitions.
 */
public class StructurizrDOTWriter {

    private static final int CLUSTER_INTERNAL_MARGIN = 25;
    private static final String INDENT = "  ";

    public StructurizrDOTWriter() {
    }

    private void writeHeader(View view, Writer writer) throws Exception {
        String title = view.getTitle();
        if (StringUtils.isNullOrEmpty(title)) {
            title = view.getName();
        }

        String description = view.getDescription();
        if (StringUtils.isNullOrEmpty(description)) {
            description = "";
        } else {
            description = String.format("<br /><font point-size=\"24\">%s</font>", description);
        }

        String fontName = "Arial";
        Font font = view.getViewSet().getConfiguration().getBranding().getFont();
        if (font != null) {
            fontName = font.getName();
        }

        RankDirection rankDirection = RankDirection.TopBottom;

        if (view.getAutomaticLayout() != null) {
            switch (view.getAutomaticLayout().getRankDirection()) {
                case TopBottom:
                    rankDirection = RankDirection.TopBottom;
                    break;
                case BottomTop:
                    rankDirection = RankDirection.BottomTop;
                    break;
                case LeftRight:
                    rankDirection = RankDirection.LeftRight;
                    break;
                case RightLeft:
                    rankDirection = RankDirection.RightLeft;
                    break;
            }
        }

        writer.write("digraph {");
        writer.write("\n");
        writer.write("  compound=true");
        writer.write("\n");
        writer.write(String.format("  graph [fontname=\"%s\", rankdir=%s, ranksep=1.0, nodesep=1.0]", fontName, rankDirection.getCode()));
        writer.write("\n");
        writer.write(String.format("  node [fontname=\"%s\", shape=box, margin=\"0.4,0.3\"]", fontName));
        writer.write("\n");
        writer.write(String.format("  edge [fontname=\"%s\"]", fontName));
        writer.write("\n");
        writer.write(String.format("  label=<<br /><font point-size=\"34\">%s</font>%s>", title, description));
        writer.write("\n");
    }

    private void writeFooter(Writer writer) throws Exception {
        writer.write("}");
    }

    /**
     * Writes the views in the given workspace as DOT definitions, to the specified writer.
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
     * Gets a single view as a DOT diagram definition.
     *
     * @param view      the view to write
     * @return          the DOT definition as a String
     */
    public final String toString(View view) {
        StringWriter stringWriter = new StringWriter();
        write(view, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Creates DOT diagram definitions based upon the specified workspace.
     *
     * @param workspace     the workspace containing the views to be written
     * @return  a collection of DOT diagram definitions, one per view
     */
    public final Collection<DOTDiagram> toDOTDiagrams(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        Collection<DOTDiagram> diagrams = new ArrayList<>();

        for (View view : workspace.getViews().getViews()) {
            StringWriter stringWriter = new StringWriter();
            write(view, stringWriter);

            diagrams.add(new DOTDiagram(view.getKey(), view.getName(), stringWriter.toString()));
        }

        return diagrams;
    }

    /**
     * Writes a single view as a DOT diagram definition, to the specified writer.
     *
     * @param view      the view to write
     * @param writer    the Writer to write the DOT definition to
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

    private void write(SystemLandscapeView view, Writer writer) {
        try {
            write(view, view.isEnterpriseBoundaryVisible(), writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(SystemContextView view, Writer writer) {
        try {
            write(view, view.isEnterpriseBoundaryVisible(), writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(View view, boolean enterpriseBoundaryIsVisible, Writer writer) throws Exception {
        writeHeader(view, writer);

        if (enterpriseBoundaryIsVisible) {
            String enterpriseName = "Enterprise";
            if (view.getModel().getEnterprise() != null) {
                enterpriseName = view.getModel().getEnterprise().getName();
            }

            writer.write("  subgraph cluster_enterprise {");
            writer.write("\n");
            writer.write("    margin=" + CLUSTER_INTERNAL_MARGIN);
            writer.write("\n");
            writer.write(String.format("    label=<<font point-size=\"24\"><br />%s</font><br /><font point-size=\"19\">[Enterprise]</font>>", enterpriseName));
            writer.write("\n");
            writer.write("    labelloc=b");
            writer.write("\n");
            writer.write("    color=\"#444444\"");
            writer.write("\n");
            writer.write("    fontcolor=\"#444444\"");
            writer.write("\n");
            writer.write( "    fillcolor=\"#ffffff\"");
            writer.write("\n");

            Set<GroupableElement> elementsInsideEnterpriseBoundary = new LinkedHashSet<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
            }

            writeElements(view, "    ", elementsInsideEnterpriseBoundary, writer);
            writer.write("  }\n\n");

            Set<GroupableElement> elementsOutsideEnterpriseBoundary = new LinkedHashSet<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() != Location.Internal) {
                    elementsOutsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() != Location.Internal) {
                    elementsOutsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
                if (elementView.getElement() instanceof CustomElement) {
                    elementsOutsideEnterpriseBoundary.add((CustomElement)elementView.getElement());
                }
            }

            writeElements(view, "  ", elementsOutsideEnterpriseBoundary, writer);
        } else {
            Set<GroupableElement> elements = new LinkedHashSet<>();
            for (ElementView elementView : view.getElements()) {
                elements.add((GroupableElement)elementView.getElement());
            }
            writeElements(view, "  ", elements, writer);
        }

        writeRelationships(view, writer);
        writeFooter(writer);
        writer.close();
    }

    private void write(ContainerView view, Writer writer) {
        try {
            writeHeader(view, writer);

            for (ElementView elementView : view.getElements()) {
                if (!(elementView.getElement() instanceof Container)) {
                    writeElement(view, "  ", elementView.getElement(), writer);
                }
            }

            List<SoftwareSystem> softwareSystems = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Container).map(c -> ((Container)c).getSoftwareSystem()).collect(Collectors.toSet()));
            softwareSystems.sort(Comparator.comparing(Element::getName));

            for (SoftwareSystem softwareSystem : softwareSystems) {
                String color;
                if (softwareSystem.equals(view.getSoftwareSystem())) {
                    color = "#444444";
                } else {
                    color = "#cccccc";
                }

                writer.write(String.format("  subgraph cluster_%s {\n", softwareSystem.getId()));
                writer.write("    margin=" + CLUSTER_INTERNAL_MARGIN);
                writer.write("\n");
                writer.write(String.format("    label=<<font point-size=\"24\"><br />%s</font><br /><font point-size=\"19\">%s</font>>", softwareSystem.getName(), typeOf(view, softwareSystem, true)));
                writer.write("\n");
                writer.write("    labelloc=b");
                writer.write("\n");
                writer.write(String.format("    color=\"%s\"", color));
                writer.write("\n");
                writer.write(String.format("    fontcolor=\"%s\"", color));
                writer.write("\n");
                writer.write(String.format("    fillcolor=\"%s\"", color));
                writer.write("\n");

                Set<GroupableElement> scopedElements = new LinkedHashSet<>();
                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() == softwareSystem) {
                        scopedElements.add((StaticStructureElement) elementView.getElement());
                    }
                }
                writeElements(view, "    ", scopedElements, writer);
                writer.write("  }\n");
            }

            writeRelationships(view, writer);

            writeFooter(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void write(ComponentView view, Writer writer) {
        try {
            writeHeader(view, writer);

            for (ElementView elementView : view.getElements()) {
                if (!(elementView.getElement() instanceof Component)) {
                    writeElement(view, "  ", elementView.getElement(), writer);
                }
            }

            List<Container> containers = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Component).map(c -> ((Component)c).getContainer()).collect(Collectors.toSet()));
            containers.sort(Comparator.comparing(Element::getName));

            for (Container container : containers) {
                String color;
                if (container.equals(view.getContainer())) {
                    color = "#444444";
                } else {
                    color = "#cccccc";
                }

                writer.write(String.format("  subgraph cluster_%s {\n", container.getId()));
                writer.write("    margin=" + CLUSTER_INTERNAL_MARGIN);
                writer.write("\n");
                writer.write(String.format("    label=<<font point-size=\"24\"><br />%s</font><br /><font point-size=\"19\">%s</font>>", container.getName(), typeOf(view, container, true)));
                writer.write("\n");
                writer.write("    labelloc=b");
                writer.write("\n");
                writer.write(String.format("    color=\"%s\"", color));
                writer.write("\n");
                writer.write(String.format("    fontcolor=\"%s\"", color));
                writer.write("\n");
                writer.write(String.format("    fillcolor=\"%s\"", color));
                writer.write("\n");

                Set<GroupableElement> scopedElements = new LinkedHashSet<>();
                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() == container) {
                        scopedElements.add((StaticStructureElement) elementView.getElement());
                    }
                }
                writeElements(view, "    ", scopedElements, writer);
                writer.write("  }\n");

            }

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void write(DynamicView view, Writer writer) {
        try {
            writeHeader(view, writer);

            Element element = view.getElement();

            if (element == null) {
                for (ElementView elementView : view.getElements()) {
                    writeElement(view, "  ", elementView.getElement(), writer);
                }
            } else {
                writer.write(String.format("  subgraph cluster_%s {\n", element.getId()));
                writer.write("    margin=" + CLUSTER_INTERNAL_MARGIN);
                writer.write("\n");
                writer.write(String.format("    label=<<font point-size=\"24\">%s</font><br /><font point-size=\"19\">%s</font>>", element.getName(), typeOf(view, element, true)));
                writer.write("\n");
                writer.write("    labelloc=b");
                writer.write("\n");
                writer.write("    color=\"#444444\"");
                writer.write("\n");
                writer.write("    fontcolor=\"#444444\"");
                writer.write("\n");
                writer.write( "    fillcolor=\"#ffffff\"");
                writer.write("\n");

                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() == element) {
                        writeElement(view, "    ", elementView.getElement(), writer);
                    }
                }
                writer.write("  }\n");

                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() != element) {
                        writeElement(view, "  ", elementView.getElement(), writer);
                    }
                }
            }

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     void write(DeploymentView view, Writer writer) {
         try {
             writeHeader(view, writer);

             for (ElementView elementView : view.getElements()) {
                 if (elementView.getElement() instanceof DeploymentNode && elementView.getElement().getParent() == null) {
                     write(view, (DeploymentNode)elementView.getElement(), writer, "");
                 }
             }

             writeRelationships(view, writer);

             writeFooter(writer);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    private void write(DeploymentView view, DeploymentNode deploymentNode, Writer writer, String indent) throws Exception {
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(deploymentNode);

        writer.write(String.format(indent + "subgraph cluster_%s {\n", deploymentNode.getId()));
        writer.write(indent + "    margin=" + CLUSTER_INTERNAL_MARGIN);
        writer.write("\n");
        writer.write(String.format(indent + "    label=<<font point-size=\"24\">%s</font><br /><font point-size=\"19\">%s</font>>", deploymentNode.getName(), typeOf(view, deploymentNode, true)));
        writer.write("\n");
        writer.write(indent + "    labelloc=b");
        writer.write("\n");
        writer.write(String.format(indent + "    color=\"%s\"", elementStyle.getStroke()));
        writer.write("\n");
        writer.write(String.format(indent + "    fontcolor=\"%s\"", elementStyle.getColor()));
        writer.write("\n");
        writer.write(indent + "    fillcolor=\"#ffffff\"");
        writer.write("\n");

        List<DeploymentNode> children = new ArrayList<>(deploymentNode.getChildren());
        children.sort(Comparator.comparing(DeploymentNode::getName));
        for (DeploymentNode child : children) {
            if (view.isElementInView(child)) {
                write(view, child, writer, indent + "  ");

            }
        }

        List<InfrastructureNode> infrastructureNodes = new ArrayList<>(deploymentNode.getInfrastructureNodes());
        infrastructureNodes.sort(Comparator.comparing(InfrastructureNode::getName));
        for (InfrastructureNode infrastructureNode : infrastructureNodes) {
            if (view.isElementInView(infrastructureNode)) {
                writeElement(view, indent + "  ", infrastructureNode, writer);
            }
        }

        List<SoftwareSystemInstance> softwareSystemInstances = new ArrayList<>(deploymentNode.getSoftwareSystemInstances());
        softwareSystemInstances.sort(Comparator.comparing(SoftwareSystemInstance::getName));
        for (SoftwareSystemInstance softwareSystemInstance : softwareSystemInstances) {
            if (view.isElementInView(softwareSystemInstance)) {
                writeElement(view, indent + "  ", softwareSystemInstance, writer);
            }
        }

        List<ContainerInstance> containerInstances = new ArrayList<>(deploymentNode.getContainerInstances());
        containerInstances.sort(Comparator.comparing(ContainerInstance::getName));
        for (ContainerInstance containerInstance : containerInstances) {
            if (view.isElementInView(containerInstance)) {
                writeElement(view, indent + "  ", containerInstance, writer);
            }
        }

        writer.write(indent + "}\n");
    }

    private void writeElements(View view, String padding, Set<GroupableElement> elements, Writer writer) throws Exception {
        Set<String> groups = new LinkedHashSet<>();
        for (GroupableElement element : elements) {
            String group = element.getGroup();

            if (!StringUtils.isNullOrEmpty(group)) {
                groups.add(group);
            }
        }

        // first render grouped elements
        int groupId = 1;
        for (String group : groups) {
            writer.write(padding + "subgraph cluster_group_" + groupId + " {\n");
            writer.write(padding + "  margin=" + CLUSTER_INTERNAL_MARGIN);
            writer.write("\n");
            writer.write(String.format(padding + "  label=<<font point-size=\"24\"><br />%s</font><br /><font point-size=\"19\">[Group]</font>>", group));
            writer.write("\n");
            writer.write(padding + "  labelloc=b");
            writer.write("\n");
            writer.write(padding + "  color=\"#cccccc\"");
            writer.write("\n");
            writer.write(padding + "  fontcolor=\"#cccccc\"");
            writer.write("\n");
            writer.write(padding + "  fillcolor=\"#ffffff\"");
            writer.write("\n");
            for (GroupableElement element : elements) {
                if (group.equals(element.getGroup())) {
                    writeElement(view, padding + INDENT, element, writer);
                }
            }
            writer.write(padding + "}\n");
            groupId++;
        }

        // then render ungrouped elements
        for (GroupableElement element : elements) {
            if (StringUtils.isNullOrEmpty(element.getGroup())) {
                writeElement(view, padding, element, writer);
            }
        }
    }

    private void writeElement(View view, String padding, Element element, Writer writer) throws Exception {
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(element);

        int nameFontSize = elementStyle.getFontSize() + 10;
        int metadataFontSize = elementStyle.getFontSize() - 5;
        int descriptionFontSize = elementStyle.getFontSize();


        String shape = shapeOf(view, element);
        String name = element.getName();
        String description = element.getDescription();
        String type = typeOf(view, element, true);

        if (element instanceof StaticStructureElementInstance) {
            StaticStructureElementInstance elementInstance = (StaticStructureElementInstance)element;
            name = elementInstance.getElement().getName();
            description = elementInstance.getElement().getDescription();
            type = typeOf(view, elementInstance.getElement(), true);
            shape = shapeOf(view, elementInstance.getElement());
            elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(elementInstance.getElement());
        }

        if (StringUtils.isNullOrEmpty(name)) {
            name = "";
        } else {
            name = String.format("<font point-size=\"%s\">%s</font>", nameFontSize, breakText(elementStyle.getWidth(), nameFontSize, escape(name)));
        }

        if (StringUtils.isNullOrEmpty(description)) {
            description = "";
        } else {
            description = String.format("<br /><br /><font point-size=\"%s\">%s</font>", descriptionFontSize, breakText(elementStyle.getWidth(), descriptionFontSize, escape(description)));
        }

        if (false == elementStyle.getMetadata()) {
            type = "";
        } else {
            type = String.format("<br /><font point-size=\"%s\">%s</font>", metadataFontSize, type);
        }

        writer.write(String.format("%s%s [id=%s,shape=%s, label=<%s%s%s>, style=filled, color=\"%s\", fillcolor=\"%s\", fontcolor=\"%s\"]",
                padding,
                element.getId(),
                element.getId(),
                shape,
                name,
                type,
                description,
                elementStyle.getStroke(),
                elementStyle.getBackground(),
                elementStyle.getColor()
        ));
        writer.write("\n");
    }

    private String escape(String s) {
        if (StringUtils.isNullOrEmpty(s)) {
            return s;
        } else {
            return s.replaceAll("\"", "\\\\\"");
        }
    }

    private void writeRelationships(View view, Writer writer) throws Exception {
        writer.write("\n");

        for (RelationshipView relationshipView : view.getRelationships()) {
            Element source;
            Element destination;

            RelationshipStyle relationshipStyle = view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationshipView.getRelationship());
            relationshipStyle.setWidth(400);
            int descriptionFontSize = relationshipStyle.getFontSize();
            int metadataFontSize = relationshipStyle.getFontSize() - 5;

            String description = relationshipView.getDescription();
            if (StringUtils.isNullOrEmpty(description)) {
                description = relationshipView.getRelationship().getDescription();
            }

            if (!StringUtils.isNullOrEmpty(relationshipView.getOrder())) {
                description = relationshipView.getOrder() + ". " + description;
            }

            if (StringUtils.isNullOrEmpty(description)) {
                description = "";
            } else {
                description = breakText(relationshipStyle.getWidth(), descriptionFontSize, description);
                description = String.format("<font point-size=\"%s\">%s</font>", descriptionFontSize, description);
            }

            String technology = relationshipView.getRelationship().getTechnology();
            if (StringUtils.isNullOrEmpty(technology)) {
                technology = "";
            } else {
                technology = String.format("<br /><font point-size=\"%s\">[%s]</font>", metadataFontSize, technology);
            }

            String clusterConfig = "";

            if (relationshipView.getRelationship().getSource() instanceof DeploymentNode || relationshipView.getRelationship().getDestination() instanceof DeploymentNode) {
                source = relationshipView.getRelationship().getSource();
                if (source instanceof DeploymentNode) {
                    source = findElementInside((DeploymentNode)source, view);
                }

                destination = relationshipView.getRelationship().getDestination();
                if (destination instanceof DeploymentNode) {
                    destination = findElementInside((DeploymentNode)destination, view);
                }

                if (source != null && destination != null) {

                    if (relationshipView.getRelationship().getSource() instanceof DeploymentNode) {
                        clusterConfig += ",ltail=cluster_" + relationshipView.getRelationship().getSource().getId();
                    }

                    if (relationshipView.getRelationship().getDestination() instanceof DeploymentNode) {
                        clusterConfig += ",lhead=cluster_" + relationshipView.getRelationship().getDestination().getId();
                    }
                }
            } else {
                source = relationshipView.getRelationship().getSource();
                destination = relationshipView.getRelationship().getDestination();

                if (relationshipView.isResponse() != null && relationshipView.isResponse()) {
                    source = relationshipView.getRelationship().getDestination();
                    destination = relationshipView.getRelationship().getSource();
                }
            }

            writer.write(String.format("  %s -> %s [id=%s, label=<%s%s>, style=\"%s\", color=\"%s\", fontcolor=\"%s\"%s]",
                    source.getId(),
                    destination.getId(),
                    relationshipView.getId(),
                    description,
                    technology,
                    relationshipStyle.getDashed() ? "dashed" : "solid",
                    relationshipStyle.getColor(),
                    relationshipStyle.getColor(),
                    clusterConfig
            ));
            writer.write("\n");
        }
    }

    private Element findElementInside(DeploymentNode deploymentNode, View view) {
        for (SoftwareSystemInstance softwareSystemInstance : deploymentNode.getSoftwareSystemInstances()) {
            if (view.isElementInView(softwareSystemInstance)) {
                return softwareSystemInstance;
            }
        }

        for (ContainerInstance containerInstance : deploymentNode.getContainerInstances()) {
            if (view.isElementInView(containerInstance)) {
                return containerInstance;
            }
        }

        for (InfrastructureNode infrastructureNode : deploymentNode.getInfrastructureNodes()) {
            if (view.isElementInView(infrastructureNode)) {
                return infrastructureNode;
            }
        }

        if (deploymentNode.hasChildren()) {
            for (DeploymentNode child : deploymentNode.getChildren()) {
                Element element = findElementInside(child, view);

                if (element != null) {
                    return element;
                }
            }
        }

        return null;
    }

    private String breakText(int maxWidth, int fontSize, String s) {
        if (StringUtils.isNullOrEmpty(s)) {
            return "";
        }

        StringBuilder buf = new StringBuilder();

        double characterWidth = fontSize * 0.6;
        int maxCharacters = (int)(maxWidth / characterWidth);

        if (s.length() < maxCharacters) {
            return s;
        }

        String[] words = s.split(" ");
        String line = null;
        for (String word : words) {
            if (line == null) {
                line = word;
            } else {
                if ((line.length() + word.length() + 1) < maxCharacters) {
                    line += " ";
                    line += word;
                } else {
                    buf.append(line);
                    buf.append("<br />");
                    line = word;
                }
            }
        }

        if (line != null) {
            buf.append(line);
        }

        return buf.toString();
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

    String shapeOf(View view, Element element) {
        if (element instanceof DeploymentNode) {
            return "node";
        }

        Shape shape = view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getShape();
        switch(shape) {
            case Circle:
                return "circle";
            case Component:
                return "component";
            case Cylinder:
                return "cylinder";
            case Ellipse:
                return "ellipse";
            case Folder:
                return "folder";
            case Hexagon:
                return "hexagon";
            default:
                return "rect";
        }
    }

    private boolean hasValue(String s) {
        return !StringUtils.isNullOrEmpty(s);
    }

}