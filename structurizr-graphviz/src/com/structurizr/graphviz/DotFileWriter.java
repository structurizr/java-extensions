package com.structurizr.graphviz;

import com.structurizr.model.*;
import com.structurizr.view.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * Writes a Structurizr view to a graphviz dot file. Please note that this is not a full export (colours, shapes, etc);
 * it just contains the basics required for layout purposes.
 */
class DotFileWriter {

    private static final int CLUSTER_INTERNAL_MARGIN = 25;

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

    private void writeHeader(Writer writer) throws Exception {
        writer.write("digraph {");
        writer.write("\n");
        writer.write(String.format(Locale.ROOT, "  graph [splines=polyline,rankdir=%s,ranksep=%s,nodesep=%s,fontsize=5]", rankDirection.getCode(), rankSeparation, nodeSeparation));
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

    void write(SystemLandscapeView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        System.out.println("Processing system landscape view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

        if (view.isEnterpriseBoundaryVisible()) {
            fileWriter.write("  subgraph cluster_enterprise {\n");
            fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() == Location.Internal) {
                    writeElement(view, "    ", elementView.getElement(), fileWriter);
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() == Location.Internal) {
                    writeElement(view, "    ", elementView.getElement(), fileWriter);
                }
            }
            fileWriter.write("  }\n\n");

            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() != Location.Internal) {
                    writeElement(view, "  ", elementView.getElement(), fileWriter);
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() != Location.Internal) {
                    writeElement(view, "  ", elementView.getElement(), fileWriter);
                }
            }
        } else {
            for (ElementView elementView : view.getElements()) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        }

        writeRelationships(view, fileWriter);
        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(SystemContextView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        System.out.println("Processing system context view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

        if (view.isEnterpriseBoundaryVisible()) {
            fileWriter.write("  subgraph cluster_enterprise {\n");
            fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() == Location.Internal) {
                    writeElement(view, "    ", elementView.getElement(), fileWriter);
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() == Location.Internal) {
                    writeElement(view, "    ", elementView.getElement(), fileWriter);
                }
            }
            fileWriter.write("  }\n\n");

            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() != Location.Internal) {
                    writeElement(view, "  ", elementView.getElement(), fileWriter);
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() != Location.Internal) {
                    writeElement(view, "  ", elementView.getElement(), fileWriter);
                }
            }
        } else {
            for (ElementView elementView : view.getElements()) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        }

        writeRelationships(view, fileWriter);

        writeFooter(fileWriter);
        fileWriter.close();
    }

    void write(ContainerView view) throws Exception {
        File file = new File(path, view.getKey() + ".dot");
        System.out.println("Processing container view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

        SoftwareSystem softwareSystem = view.getSoftwareSystem();

        fileWriter.write(String.format(Locale.ROOT, "  subgraph cluster_%s {\n", softwareSystem.getId()));
        fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() == softwareSystem) {
                writeElement(view, "    ", elementView.getElement(), fileWriter);
            }
        }
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
        System.out.println("Processing component view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

        Container container = view.getContainer();

        fileWriter.write(String.format(Locale.ROOT, "  subgraph cluster_%s {\n", container.getId()));
        fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() == container) {
                writeElement(view, "    ", elementView.getElement(), fileWriter);
            }
        }
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
        System.out.println("Processing dynamic view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

        Element element = view.getElement();

        if (element == null) {
            for (ElementView elementView : view.getElements()) {
                writeElement(view, "  ", elementView.getElement(), fileWriter);
            }
        } else {
            fileWriter.write(String.format(Locale.ROOT, "  subgraph cluster_%s {\n", element.getId()));
            fileWriter.write("    margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement().getParent() == element) {
                    writeElement(view, "    ", elementView.getElement(), fileWriter);
                }
            }
            fileWriter.write("  }\n");

            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement().getParent() != element) {
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
        System.out.println("Processing deployment view: " + view.getKey());
        System.out.println(" - Writing " + file.getAbsolutePath());

        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter);

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
        fileWriter.write(String.format(Locale.ROOT, indent + "subgraph cluster_%s {\n", deploymentNode.getId()));
        fileWriter.write(indent + "  margin=" + CLUSTER_INTERNAL_MARGIN + "\n");
        fileWriter.write(String.format(Locale.ROOT, indent + "  label=\"%s: %s\"\n", deploymentNode.getId(), deploymentNode.getName()));

        for (DeploymentNode child : deploymentNode.getChildren()) {
            if (view.isElementInView(child)) {
                write(view, child, fileWriter, indent + "  ");

            }
        }

        for (ContainerInstance containerInstance : deploymentNode.getContainerInstances()) {
            if (view.isElementInView(containerInstance)) {
                writeElement(view, indent + "  ", containerInstance, fileWriter);
            }
        }

        fileWriter.write(indent + "}\n");
    }

    private void writeElement(View view, String padding, Element element, Writer writer) throws Exception {
        writer.write(String.format(Locale.ROOT, "%s%s [width=%f,height=%f,fixedsize=true,id=%s,label=\"%s: %s\"]",
                padding,
                element.getId(),
                getElementWidth(view, element.getId()) / Constants.STRUCTURIZR_DPI, // convert Structurizr dimensions to inches
                getElementHeight(view, element.getId()) / Constants.STRUCTURIZR_DPI, // convert Structurizr dimensions to inches
                element.getId(),
                element.getId(),
                element.getName()
        ));
        writer.write("\n");
    }

    private void writeRelationships(View view, Writer writer) throws Exception {
        writer.write("\n");

        for (RelationshipView relationshipView : view.getRelationships()) {
            if (relationshipView.getRelationship().getSource() instanceof DeploymentNode || relationshipView.getRelationship().getDestination() instanceof DeploymentNode) {
                // todo: relationships to/from deployment nodes (graphviz clusters)
                continue;
            }

            writer.write(String.format(Locale.ROOT, "  %s -> %s [id=%s]",
                    relationshipView.getRelationship().getSourceId(),
                    relationshipView.getRelationship().getDestinationId(),
                    relationshipView.getId()
            ));
            writer.write("\n");
        }
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