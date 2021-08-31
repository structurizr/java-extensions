package com.structurizr.graphviz;

import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Writes a Structurizr view to a graphviz dot file. Please note that this is not a full export (colours, shapes, etc);
 * it just contains the basics required for layout purposes.
 */
class DotFileWriter {

    private static final int CLUSTER_INTERNAL_MARGIN = 25;
    private static final String INDENT = "  ";

    private Locale locale = Locale.US;
    private File path;
    private RankDirection rankDirection;
    private double rankSeparation;
    private double nodeSeparation;

    DotFileWriter(File path, RankDirection rankDirection, double rankSeparation, double nodeSeparation) {
        this.path = path;
        this.rankDirection = rankDirection;
        this.rankSeparation = rankSeparation;
        this.nodeSeparation = nodeSeparation;
    }

    void setLocale(Locale locale) {
        this.locale = locale;
    }

    private void writeHeader(Writer writer, View view) throws Exception {
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

            rankSeparation = view.getAutomaticLayout().getRankSeparation();
            nodeSeparation = view.getAutomaticLayout().getNodeSeparation();
        }

        rankSeparation = rankSeparation / Constants.STRUCTURIZR_DPI;
        nodeSeparation = nodeSeparation / Constants.STRUCTURIZR_DPI;

        writer.write("digraph {");
        writer.write("\n");
        writer.write("  compound=true");
        writer.write("\n");
        writer.write(String.format(locale, "  graph [splines=polyline,rankdir=%s,ranksep=%s,nodesep=%s,fontsize=5]", rankDirection.getCode(), rankSeparation, nodeSeparation));
        writer.write("\n");
        writer.write("  node [shape=box,fontsize=5]");
        writer.write("\n");
        writer.write("  edge []");
        writer.write("\n");
        writer.write("\n");
    }

    private void writeFooter(Writer writer) throws Exception {
        writer.write("}");
    }

    void write(CustomView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        Set<GroupableElement> elements = new LinkedHashSet<>();
        for (ElementView elementView : view.getElements()) {
            elements.add((GroupableElement)elementView.getElement());
        }
        writeElements(view, "  ", elements, fileWriter);

        writeRelationships(view, fileWriter);
        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(SystemLandscapeView view) throws Exception {
        write(view, view.isEnterpriseBoundaryVisible());
    }

    void write(SystemContextView view) throws Exception {
        write(view, view.isEnterpriseBoundaryVisible());
    }

    private void write(View view, boolean enterpriseBoundaryIsVisible) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        if (enterpriseBoundaryIsVisible) {
            fileWriter.write("  subgraph cluster_enterprise {\n");
            fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
            Set<GroupableElement> elementsInsideEnterpriseBoundary = new LinkedHashSet<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
            }
            writeElements(view, "    ", elementsInsideEnterpriseBoundary, fileWriter);
            fileWriter.write("  }\n\n");

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

            writeElements(view, "  ", elementsOutsideEnterpriseBoundary, fileWriter);
        } else {
            Set<GroupableElement> elements = new LinkedHashSet<>();
            for (ElementView elementView : view.getElements()) {
                elements.add((GroupableElement)elementView.getElement());
            }
            writeElements(view, "  ", elements, fileWriter);
        }

        writeRelationships(view, fileWriter);
        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(ContainerView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        SoftwareSystem softwareSystem = view.getSoftwareSystem();

        fileWriter.write(String.format(locale, "  subgraph cluster_%s {\n", softwareSystem.getId()));
        fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");

        Set<GroupableElement> scopedElements = new LinkedHashSet<>();
        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() == softwareSystem) {
                scopedElements.add((StaticStructureElement)elementView.getElement());
            }
        }
        writeElements(view, "    ", scopedElements, fileWriter);
        fileWriter.write("  }\n");

        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() != softwareSystem) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        }

        writeRelationships(view, fileWriter);

        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(ComponentView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        Container container = view.getContainer();

        fileWriter.write(String.format(locale, "  subgraph cluster_%s {\n", container.getId()));
        fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");

        Set<GroupableElement> scopedElements = new LinkedHashSet<>();
        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() == container) {
                scopedElements.add((StaticStructureElement)elementView.getElement());
            }
        }
        writeElements(view, "    ", scopedElements, fileWriter);
        fileWriter.write("  }\n");

        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() != container) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        }

        writeRelationships(view, fileWriter);

        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(DynamicView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        Element element = view.getElement();

        if (element == null) {
            for (ElementView elementView : view.getElements()) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        } else if (element instanceof SoftwareSystem) {
                List<SoftwareSystem> softwareSystems = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Container).map(c -> ((Container)c).getSoftwareSystem()).collect(Collectors.toSet()));
                softwareSystems.sort(Comparator.comparing(Element::getId));

                for (SoftwareSystem softwareSystem : softwareSystems) {
                    fileWriter.write(String.format(locale, "  subgraph cluster_%s {\n", softwareSystem.getId()));
                    fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
                    for (ElementView elementView : view.getElements()) {
                        if (elementView.getElement().getParent() == softwareSystem) {
                            writeElement(view, "    ", elementView.getElement(), fileWriter);
                        }
                    }
                    fileWriter.write("  }\n");
                }

                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() == null) {
                        writeElement(view, "  ", elementView.getElement(), fileWriter);
                    }
                }
        } else if (element instanceof Container) {
            List<Container> containers = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Component).map(c -> ((Component)c).getContainer()).collect(Collectors.toSet()));
            containers.sort(Comparator.comparing(Element::getId));

            for (Container container : containers) {
                fileWriter.write(String.format(locale, "  subgraph cluster_%s {\n", container.getId()));
                fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
                for (ElementView elementView : view.getElements()) {
                    if (elementView.getElement().getParent() == container) {
                        writeElement(view, "    ", elementView.getElement(), fileWriter);
                    }
                }
                fileWriter.write("  }\n");
            }

            for (ElementView elementView : view.getElements()) {
                if (!(elementView.getElement().getParent() instanceof Container)) {
                    writeElement(view, "  ", elementView.getElement(), fileWriter);
                }
            }
        }

        writeRelationships(view, fileWriter);

        writeFooter(fileWriter);
        fileWriter.close();
    }

     void write(DeploymentView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, view);

        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement() instanceof DeploymentNode && elementView.getElement().getParent() == null) {
                write(view, (DeploymentNode)elementView.getElement(), fileWriter, "");
            }
        }

        writeRelationships(view, fileWriter);

        writeFooter(fileWriter);
        fileWriter.close();
    }

    private void write(DeploymentView view, DeploymentNode deploymentNode, FileWriter fileWriter, String indent) throws Exception {
        fileWriter.write(String.format(locale, indent + "subgraph cluster_%s {\n", deploymentNode.getId()));
        fileWriter.write(indent + "  margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
        fileWriter.write(String.format(locale, indent + "  label=\"%s: %s\"\n", deploymentNode.getId(), deploymentNode.getName()));

        for (DeploymentNode child : deploymentNode.getChildren()) {
            if (view.isElementInView(child)) {
                write(view, child, fileWriter, indent + "  ");

            }
        }

        for (InfrastructureNode infrastructureNode : deploymentNode.getInfrastructureNodes()) {
            if (view.isElementInView(infrastructureNode)) {
                writeElement(view, indent + "  ", infrastructureNode, fileWriter);
            }
        }

        for (SoftwareSystemInstance softwareSystemInstance : deploymentNode.getSoftwareSystemInstances()) {
            if (view.isElementInView(softwareSystemInstance)) {
                writeElement(view, indent + "  ", softwareSystemInstance, fileWriter);
            }
        }

        for (ContainerInstance containerInstance : deploymentNode.getContainerInstances()) {
            if (view.isElementInView(containerInstance)) {
                writeElement(view, indent + "  ", containerInstance, fileWriter);
            }
        }

        fileWriter.write(indent + "}\n");
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
            writer.write( padding + "  margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
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
        writer.write(String.format(locale, "%s%s [width=%f,height=%f,fixedsize=true,id=%s,label=\"%s: %s\"]",
                padding,
                element.getId(),
                getElementWidth(view, element.getId()) / Constants.STRUCTURIZR_DPI, // convert Structurizr dimensions to inches
                getElementHeight(view, element.getId()) / Constants.STRUCTURIZR_DPI, // convert Structurizr dimensions to inches
                element.getId(),
                element.getId(),
                escape(element.getName())
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
            if (relationshipView.getRelationship().getSource() instanceof DeploymentNode || relationshipView.getRelationship().getDestination() instanceof DeploymentNode) {
                Element source = relationshipView.getRelationship().getSource();
                if (source instanceof DeploymentNode) {
                    source = findElementInside((DeploymentNode)source, view);
                }

                Element destination = relationshipView.getRelationship().getDestination();
                if (destination instanceof DeploymentNode) {
                    destination = findElementInside((DeploymentNode)destination, view);
                }

                if (source != null && destination != null) {
                    String clusterConfig = "";

                    if (relationshipView.getRelationship().getSource() instanceof DeploymentNode) {
                        clusterConfig += ",ltail=cluster_" + relationshipView.getRelationship().getSource().getId();
                    }

                    if (relationshipView.getRelationship().getDestination() instanceof DeploymentNode) {
                        clusterConfig += ",lhead=cluster_" + relationshipView.getRelationship().getDestination().getId();
                    }

                    writer.write(String.format(locale, "  %s -> %s [id=%s%s]",
                            source.getId(),
                            destination.getId(),
                            relationshipView.getId(),
                            clusterConfig
                    ));
                    writer.write("\n");
                }
            } else {
                Element source = relationshipView.getRelationship().getSource();
                Element destination = relationshipView.getRelationship().getDestination();

                if (relationshipView.isResponse() != null && relationshipView.isResponse()) {
                    source = relationshipView.getRelationship().getDestination();
                    destination = relationshipView.getRelationship().getSource();
                }

                writer.write(String.format(locale, "  %s -> %s [id=%s]",
                        source.getId(),
                        destination.getId(),
                        relationshipView.getId()
                ));
                writer.write("\n");
            }
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

    private int getElementWidth(View view, String elementId) {
        Element element = view.getModel().getElement(elementId);
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getWidth();
    }

    private int getElementHeight(View view, String elementId) {
        Element element = view.getModel().getElement(elementId);
        return view.getViewSet().getConfiguration().getStyles().findElementStyle(element).getHeight();
    }

}