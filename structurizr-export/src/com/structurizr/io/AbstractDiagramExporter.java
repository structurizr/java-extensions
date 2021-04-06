package com.structurizr.io;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractDiagramExporter extends AbstractExporter {

    /**
     * Exports all views in the workspace.
     *
     * @param workspace     the workspace containing the views to be written
     * @return  a collection of diagram definitions, one per view
     */
    public final Collection<Diagram> export(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        Collection<Diagram> diagrams = new ArrayList<>();

        for (SystemLandscapeView view : workspace.getViews().getSystemLandscapeViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        for (SystemContextView view : workspace.getViews().getSystemContextViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        for (ContainerView view : workspace.getViews().getContainerViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        for (ComponentView view : workspace.getViews().getComponentViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        for (DynamicView view : workspace.getViews().getDynamicViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        for (DeploymentView view : workspace.getViews().getDeploymentViews()) {
            Diagram diagram = export(view);
            if (diagram != null) {
                diagrams.add(diagram);
            }
        }

        return diagrams;
    }

    public Diagram export(SystemLandscapeView view) {
        return export(view, view.isEnterpriseBoundaryVisible());
    }

    public Diagram export(SystemContextView view) {
        return export(view, view.isEnterpriseBoundaryVisible());
    }

    private Diagram export(View view, boolean enterpriseBoundaryIsVisible) {
        IndentingWriter writer = new IndentingWriter();
        writeHeader(view, writer);

        boolean showEnterpriseBoundary =
                enterpriseBoundaryIsVisible &&
                (view.getElements().stream().map(ElementView::getElement).anyMatch(e -> e instanceof Person && ((Person)e).getLocation() == Location.Internal) ||
                 view.getElements().stream().map(ElementView::getElement).anyMatch(e -> e instanceof SoftwareSystem && ((SoftwareSystem)e).getLocation() == Location.Internal));

        if (showEnterpriseBoundary) {
            String enterpriseName = "Enterprise";
            if (view.getModel().getEnterprise() != null) {
                enterpriseName = view.getModel().getEnterprise().getName();
            }

            startEnterpriseBoundary(enterpriseName, writer);

            List<GroupableElement> elementsInsideEnterpriseBoundary = new ArrayList<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement() instanceof Person && ((Person)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
                if (elementView.getElement() instanceof SoftwareSystem && ((SoftwareSystem)elementView.getElement()).getLocation() == Location.Internal) {
                    elementsInsideEnterpriseBoundary.add((StaticStructureElement)elementView.getElement());
                }
            }
            writeElements(view, elementsInsideEnterpriseBoundary, writer);

            endEnterpriseBoundary(writer);

            List<GroupableElement> elementsOutsideEnterpriseBoundary = new ArrayList<>();
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
            writeElements(view, elementsOutsideEnterpriseBoundary, writer);
        } else {
            List<GroupableElement> elements = new ArrayList<>();
            for (ElementView elementView : view.getElements()) {
                elements.add((GroupableElement)elementView.getElement());
            }
            writeElements(view, elements, writer);
        }

        writer.writeLine();
        writeRelationships(view, writer);
        writeFooter(view, writer);

        return new Diagram(view.getKey(), view.getName(), writer.toString());
    }

    public Diagram export(ContainerView view) {
        IndentingWriter writer = new IndentingWriter();
        writeHeader(view, writer);

        boolean elementsWritten = false;
        for (ElementView elementView : view.getElements()) {
            if (!(elementView.getElement() instanceof Container)) {
                writeElement(view, elementView.getElement(), writer);
                elementsWritten = true;
            }
        }

        if (elementsWritten) {
            writer.writeLine();
        }

        List<SoftwareSystem> softwareSystems = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Container).map(c -> ((Container)c).getSoftwareSystem()).collect(Collectors.toSet()));
        softwareSystems.sort(Comparator.comparing(Element::getId));

        for (SoftwareSystem softwareSystem : softwareSystems) {
            startSoftwareSystemBoundary(view, softwareSystem, writer);

            List<GroupableElement> scopedElements = new ArrayList<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement().getParent() == softwareSystem) {
                    scopedElements.add((StaticStructureElement) elementView.getElement());
                }
            }

            writeElements(view, scopedElements, writer);
            endSoftwareSystemBoundary(writer);
        }

        writeRelationships(view, writer);

        writeFooter(view, writer);

        return new Diagram(view.getKey(), view.getName(), writer.toString());
    }

    public Diagram export(ComponentView view) {
        IndentingWriter writer = new IndentingWriter();
        writeHeader(view, writer);

        boolean elementsWritten = false;
        for (ElementView elementView : view.getElements()) {
            if (!(elementView.getElement() instanceof Component)) {
                writeElement(view, elementView.getElement(), writer);
                elementsWritten = true;
            }
        }

        if (elementsWritten) {
            writer.writeLine();
        }

        List<Container> containers = new ArrayList<>(view.getElements().stream().map(ElementView::getElement).filter(e -> e instanceof Component).map(c -> ((Component)c).getContainer()).collect(Collectors.toSet()));
        containers.sort(Comparator.comparing(Element::getId));

        for (Container container : containers) {
            startContainerBoundary(view, container, writer);
            List<GroupableElement> scopedElements = new ArrayList<>();
            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement().getParent() == container) {
                    scopedElements.add((StaticStructureElement) elementView.getElement());
                }
            }
            writeElements(view, scopedElements, writer);
            endContainerBoundary(writer);
        }

        writeRelationships(view, writer);

        writeFooter(view, writer);

        return new Diagram(view.getKey(), view.getName(), writer.toString());
    }

    public Diagram export(DynamicView view) {
        IndentingWriter writer = new IndentingWriter();
        writeHeader(view, writer);

        Element element = view.getElement();

        boolean elementsWritten = false;
        for (ElementView elementView : view.getElements()) {
            if (elementView.getElement().getParent() != element) {
                writeElement(view, elementView.getElement(), writer);
                elementsWritten = true;
            }
        }

        if (elementsWritten) {
            writer.writeLine();
        }

        if (element == null) {
            for (ElementView elementView : view.getElements()) {
                writeElement(view, elementView.getElement(), writer);
            }
        } else {
            if (element instanceof SoftwareSystem) {
                startSoftwareSystemBoundary(view, (SoftwareSystem)element, writer);
            } else if (element instanceof Container) {
                startContainerBoundary(view, (Container)element, writer);
            }

            for (ElementView elementView : view.getElements()) {
                if (elementView.getElement().getParent() == element) {
                    writeElement(view, elementView.getElement(), writer);
                }
            }

            if (element instanceof SoftwareSystem) {
                endSoftwareSystemBoundary(writer);
            } else if (element instanceof Container) {
                endContainerBoundary(writer);
            }
        }

        writeRelationships(view, writer);
        writeFooter(view, writer);

        return new Diagram(view.getKey(), view.getName(), writer.toString());
    }

     public Diagram export(DeploymentView view) {
         IndentingWriter writer = new IndentingWriter();
         writeHeader(view, writer);

         for (ElementView elementView : view.getElements()) {
             if (elementView.getElement() instanceof DeploymentNode && elementView.getElement().getParent() == null) {
                 write(view, (DeploymentNode)elementView.getElement(), writer);
             }
         }

         writeRelationships(view, writer);
         writeFooter(view, writer);

         return new Diagram(view.getKey(), view.getName(), writer.toString());
     }

     private void write(DeploymentView view, DeploymentNode deploymentNode, IndentingWriter writer) {
        startDeploymentNodeBoundary(view, deploymentNode, writer);

        List<DeploymentNode> children = new ArrayList<>(deploymentNode.getChildren());
        children.sort(Comparator.comparing(DeploymentNode::getName));
        for (DeploymentNode child : children) {
            if (view.isElementInView(child)) {
                write(view, child, writer);

            }
        }

        List<InfrastructureNode> infrastructureNodes = new ArrayList<>(deploymentNode.getInfrastructureNodes());
        infrastructureNodes.sort(Comparator.comparing(InfrastructureNode::getName));
        for (InfrastructureNode infrastructureNode : infrastructureNodes) {
            if (view.isElementInView(infrastructureNode)) {
                writeElement(view, infrastructureNode, writer);
            }
        }

        List<SoftwareSystemInstance> softwareSystemInstances = new ArrayList<>(deploymentNode.getSoftwareSystemInstances());
        softwareSystemInstances.sort(Comparator.comparing(SoftwareSystemInstance::getName));
        for (SoftwareSystemInstance softwareSystemInstance : softwareSystemInstances) {
            if (view.isElementInView(softwareSystemInstance)) {
                writeElement(view, softwareSystemInstance, writer);
            }
        }

        List<ContainerInstance> containerInstances = new ArrayList<>(deploymentNode.getContainerInstances());
        containerInstances.sort(Comparator.comparing(ContainerInstance::getName));
        for (ContainerInstance containerInstance : containerInstances) {
            if (view.isElementInView(containerInstance)) {
                writeElement(view, containerInstance, writer);
            }
        }

        endDeploymentNodeBoundary(writer);
    }

    protected void writeElements(View view, List<GroupableElement> elements, IndentingWriter writer) {
        elements.sort(Comparator.comparing(Element::getId));

        Set<String> groupsAsSet = new HashSet<>();
        for (GroupableElement element : elements) {
            String group = element.getGroup();

            if (!StringUtils.isNullOrEmpty(group)) {
                groupsAsSet.add(group);
            }
        }

        List<String> groupsAsList = new ArrayList<>(groupsAsSet);
        Collections.sort(groupsAsList);

        // first render grouped elements
        for (String group : groupsAsList) {
            startGroupBoundary(group, writer);

            for (GroupableElement element : elements) {
                if (group.equals(element.getGroup())) {
                    writeElement(view, element, writer);
                }
            }

            endGroupBoundary(writer);
        }

        // then render ungrouped elements
        for (GroupableElement element : elements) {
            if (StringUtils.isNullOrEmpty(element.getGroup())) {
                writeElement(view, element, writer);
            }
        }
    }

    protected void writeRelationships(View view, IndentingWriter writer) {
        Collection<RelationshipView> relationshipList;

        if (view instanceof DynamicView) {
            relationshipList = view.getRelationships();
        } else {
            relationshipList = view.getRelationships().stream().sorted(Comparator.comparing(rv -> rv.getRelationship().getId())).collect(Collectors.toList());
        }

        for (RelationshipView relationshipView : relationshipList) {
            writeRelationship(view, relationshipView, writer);
        }
    }

    protected abstract void writeHeader(View view, IndentingWriter writer);
    protected abstract void writeFooter(View view, IndentingWriter writer);

    protected abstract void startEnterpriseBoundary(String enterpriseName, IndentingWriter writer);
    protected abstract void endEnterpriseBoundary(IndentingWriter writer);

    protected abstract void startGroupBoundary(String group, IndentingWriter writer);
    protected abstract void endGroupBoundary(IndentingWriter writer);

    protected abstract void startSoftwareSystemBoundary(View view, SoftwareSystem softwareSystem, IndentingWriter writer);
    protected abstract void endSoftwareSystemBoundary(IndentingWriter writer);

    protected abstract void startContainerBoundary(View view, Container container, IndentingWriter writer);
    protected abstract void endContainerBoundary(IndentingWriter writer);

    protected abstract void startDeploymentNodeBoundary(DeploymentView view, DeploymentNode deploymentNode, IndentingWriter writer);
    protected abstract void endDeploymentNodeBoundary(IndentingWriter writer);

    protected abstract void writeElement(View view, Element element, IndentingWriter writer);
    protected abstract void writeRelationship(View view, RelationshipView relationshipView, IndentingWriter writer);

}