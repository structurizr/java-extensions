package com.structurizr.io.ilograph;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.DynamicView;
import com.structurizr.view.ElementStyle;
import com.structurizr.view.RelationshipView;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Exports a Structurizr to the Ilograph definition language, for use with https://app.ilograph.com/
 */
public class IlographWriter {

    private String LINE_SEPARATOR = System.lineSeparator();

    private void writeModel(Workspace workspace, Writer writer) throws Exception {
        writer.append("resources:");
        writer.append(LINE_SEPARATOR);

        Model model = workspace.getModel();
        List<StaticStructureElement> elements = new ArrayList<>();

        List<Person> people = new ArrayList<>(model.getPeople());
        people.sort(Comparator.comparing(Person::getId));
        for (Person person : people) {
            writeElement(writer, "  ", workspace, person);
            elements.add(person);
        }
        writer.append(LINE_SEPARATOR);

        List<SoftwareSystem> softwareSystems = new ArrayList<>(model.getSoftwareSystems());
        softwareSystems.sort(Comparator.comparing(SoftwareSystem::getId));
        for (SoftwareSystem softwareSystem : softwareSystems) {
            writeElement(writer, "  ", workspace, softwareSystem);
            elements.add(softwareSystem);

            if (!softwareSystem.getContainers().isEmpty()) {
                writer.append("    children:");
                writer.append(LINE_SEPARATOR);

                List<Container> containers = new ArrayList<>(softwareSystem.getContainers());
                containers.sort(Comparator.comparing(Container::getId));
                for (Container container : containers) {
                    writeElement(writer, "      ", workspace, container);
                    elements.add(container);

                    if (!container.getComponents().isEmpty()) {
                        writer.append("        children:");
                        writer.append(LINE_SEPARATOR);

                        List<Component> components = new ArrayList<>(container.getComponents());
                        components.sort(Comparator.comparing(Component::getId));
                        for (Component component : components) {
                            writeElement(writer, "          ", workspace, component);
                            elements.add(component);
                        }
                    }

                    writer.append(LINE_SEPARATOR);
                }
            }
            writer.append(LINE_SEPARATOR);

        }

        List<DeploymentNode> deploymentNodes = new ArrayList<>(model.getDeploymentNodes());
        deploymentNodes.sort(Comparator.comparing(DeploymentNode::getId));
        for (DeploymentNode deploymentNode : deploymentNodes) {
            writeDeploymentNode(workspace, deploymentNode, "  ", writer);
        }

        Set<Relationship> relationships = new LinkedHashSet<>();
        Set<Class> elementTypes = new HashSet<>();

        elementTypes.add(Person.class);
        elementTypes.add(SoftwareSystem.class);
        for (StaticStructureElement element : elements) {
            List<Relationship> sortedRelationships = new ArrayList<>(element.getRelationships());
            sortedRelationships.sort(Comparator.comparing(Relationship::getId));
            for (Relationship relationship : sortedRelationships) {
                if (include(relationship, elementTypes)) {
                    relationships.add(relationship);
                }
            }
        }

        elementTypes.add(Container.class);
        for (StaticStructureElement element : elements) {
            List<Relationship> sortedRelationships = new ArrayList<>(element.getRelationships());
            sortedRelationships.sort(Comparator.comparing(Relationship::getId));
            for (Relationship relationship : sortedRelationships) {
                if (include(relationship, elementTypes)) {
                    relationships.add(relationship);
                }
            }
        }

        elementTypes.add(Component.class);
        for (StaticStructureElement element : elements) {
            List<Relationship> sortedRelationships = new ArrayList<>(element.getRelationships());
            sortedRelationships.sort(Comparator.comparing(Relationship::getId));
            for (Relationship relationship : sortedRelationships) {
                if (include(relationship, elementTypes)) {
                    relationships.add(relationship);
                }
            }
        }

        writeRelationshipsForStaticStructurePerspective(relationships, writer);

        for (DynamicView dynamicView : workspace.getViews().getDynamicViews()) {
            writeDynamicView(dynamicView, writer);
        }

        Set<String> deploymentEnvironments = new HashSet<>();
        for (DeploymentNode deploymentNode : model.getDeploymentNodes()) {
            deploymentEnvironments.add(deploymentNode.getEnvironment());
        }
        List<String> sortedDeploymentEnvironments = new ArrayList<>(deploymentEnvironments);
        sortedDeploymentEnvironments.sort(Comparator.comparing(String::toString));
        for (String deploymentEnvironment : sortedDeploymentEnvironments) {
            writeDeploymentEnvironment(workspace, deploymentEnvironment, writer);
        }
    }

    private void writeDeploymentNode(Workspace workspace, DeploymentNode deploymentNode, String indent, Writer writer) throws Exception {
        writeElement(writer, indent, workspace, deploymentNode);

        boolean hasChildren = !deploymentNode.getChildren().isEmpty() || !deploymentNode.getInfrastructureNodes().isEmpty() || !deploymentNode.getSoftwareSystemInstances().isEmpty() || !deploymentNode.getContainerInstances().isEmpty();

        if (hasChildren) {
            writer.append(indent + "  children:");
            writer.append(LINE_SEPARATOR);
        }

        List<DeploymentNode> deploymentNodes = new ArrayList<>(deploymentNode.getChildren());
        deploymentNodes.sort(Comparator.comparing(DeploymentNode::getId));
        for (DeploymentNode child : deploymentNodes) {
            writeDeploymentNode(workspace, child, indent + "  ", writer);
        }

        List<InfrastructureNode> infrastructureNodes = new ArrayList<>(deploymentNode.getInfrastructureNodes());
        infrastructureNodes.sort(Comparator.comparing(InfrastructureNode::getId));
        for (InfrastructureNode infrastructureNode : infrastructureNodes) {
            writeElement(writer, indent + "    ", workspace, infrastructureNode);
        }

        List<SoftwareSystemInstance> softwareSystemInstances = new ArrayList<>(deploymentNode.getSoftwareSystemInstances());
        softwareSystemInstances.sort(Comparator.comparing(SoftwareSystemInstance::getId));
        for (SoftwareSystemInstance softwareSystemInstance : softwareSystemInstances) {
            writeElement(writer, indent + "    ", workspace, softwareSystemInstance);
        }

        List<ContainerInstance> containerInstances = new ArrayList<>(deploymentNode.getContainerInstances());
        containerInstances.sort(Comparator.comparing(ContainerInstance::getId));
        for (ContainerInstance containerInstance : containerInstances) {
            writeElement(writer, indent + "    ", workspace, containerInstance);
        }
    }

    private void writeElement(Writer writer, String indent, Workspace workspace, Element element) throws Exception {
        writer.append(String.format("%s- id: \"%s\"", indent, element.getId()));
        writer.append(LINE_SEPARATOR);

        String name;
        String description;
        ElementStyle elementStyle;

        if (element instanceof StaticStructureElementInstance) {
            StaticStructureElementInstance elementInstance = (StaticStructureElementInstance)element;
            name = elementInstance.getElement().getName();
            description = elementInstance.getElement().getDescription();
            elementStyle = workspace.getViews().getConfiguration().getStyles().findElementStyle(elementInstance.getElement());
        } else {
            name = element.getName();
            description = element.getDescription();
            elementStyle = workspace.getViews().getConfiguration().getStyles().findElementStyle(element);
        }

        writer.append(String.format("%s  name: \"%s\"", indent, name));
        writer.append(LINE_SEPARATOR);
        writer.append(String.format("%s  subtitle: \"%s\"", indent, typeOf(element)));
        writer.append(LINE_SEPARATOR);

        if (!StringUtils.isNullOrEmpty(description)) {
            writer.append(String.format("%s  description: \"%s\"", indent, description));
            writer.append(LINE_SEPARATOR);
        }

        if (element instanceof DeploymentNode) {
            writer.append(String.format("%s  backgroundColor: \"%s\"", indent, "#ffffff"));
            writer.append(LINE_SEPARATOR);
        } else {
            writer.append(String.format("%s  backgroundColor: \"%s\"", indent, elementStyle.getBackground()));
            writer.append(LINE_SEPARATOR);
        }
        writer.append(String.format("%s  color: \"%s\"", indent, elementStyle.getColor()));
        writer.append(LINE_SEPARATOR);
        writer.append(LINE_SEPARATOR);
    }

    private void writeRelationshipsForStaticStructurePerspective(Collection<Relationship> relationships, Writer writer) throws Exception {
        writer.append("perspectives:");
        writer.append(LINE_SEPARATOR);
        writer.append("  - name: Static Structure");
        writer.append(LINE_SEPARATOR);
        writer.append("    relations:");
        writer.append(LINE_SEPARATOR);

        for (Relationship relationship : relationships) {
            writer.append(String.format("      - from: \"%s\"", relationship.getSourceId()));
            writer.append(LINE_SEPARATOR);
            writer.append(String.format("        to: \"%s\"", relationship.getDestinationId()));
            writer.append(LINE_SEPARATOR);

            if (!StringUtils.isNullOrEmpty(relationship.getDescription())) {
                writer.append(String.format("        label: \"%s\"", relationship.getDescription()));
                writer.append(LINE_SEPARATOR);
            }

            if (!StringUtils.isNullOrEmpty(relationship.getTechnology())) {
                writer.append(String.format("        description: \"%s\"", relationship.getTechnology()));
                writer.append(LINE_SEPARATOR);
            }

            writer.append(LINE_SEPARATOR);
        }
    }

    private void writeDynamicView(DynamicView dynamicView, Writer writer) throws Exception {
        writer.append("  - name: Dynamic - " + dynamicView.getName());
        writer.append(LINE_SEPARATOR);
        writer.append("    sequence:");
        writer.append(LINE_SEPARATOR);

        int count = 0;
        for (RelationshipView relationshipView : dynamicView.getRelationships()) {
            Relationship relationship = relationshipView.getRelationship();
            if (count == 0) {
                writer.append(String.format("      start: \"%s\"", relationship.getSourceId()));
                writer.append(LINE_SEPARATOR);
                writer.append("      steps:");
                writer.append(LINE_SEPARATOR);
                writer.append(String.format("      - to: \"%s\"", relationship.getDestinationId()));
            } else {
                writer.append(String.format("      - to: \"%s\"", relationship.getDestinationId()));
            }

            writer.append(LINE_SEPARATOR);

            if (!StringUtils.isNullOrEmpty(relationshipView.getDescription())) {
                writer.append(String.format("        label: \"%s. %s\"", relationshipView.getOrder(), relationshipView.getDescription()));
                writer.append(LINE_SEPARATOR);
            } else if (!StringUtils.isNullOrEmpty(relationship.getDescription())) {
                writer.append(String.format("        label: \"%s. %s\"", relationshipView.getOrder(), relationship.getDescription()));
                writer.append(LINE_SEPARATOR);
            }

            if (!StringUtils.isNullOrEmpty(relationship.getTechnology())) {
                writer.append(String.format("        description: \"%s\"", relationship.getTechnology()));
                writer.append(LINE_SEPARATOR);
            }

            writer.append(LINE_SEPARATOR);
            count++;
        }
    }

    private void writeDeploymentEnvironment(Workspace workspace, String deploymentEnvironment, Writer writer) throws Exception {
        writer.append("  - name: Deployment - " + deploymentEnvironment);
        writer.append(LINE_SEPARATOR);
        writer.append("    relations:");
        writer.append(LINE_SEPARATOR);

        List<DeploymentNode> topLevelDeploymentNodes = workspace.getModel().getDeploymentNodes().stream().filter(dn -> dn.getEnvironment().equals(deploymentEnvironment)).sorted(Comparator.comparing(DeploymentNode::getId)).collect(Collectors.toList());
        List<Element> deploymentElementsInEnvironment = new ArrayList<>(topLevelDeploymentNodes);
        for (DeploymentNode deploymentNode : topLevelDeploymentNodes) {
            deploymentElementsInEnvironment.addAll(findAllChildren(deploymentNode));
        }

        Collection<Relationship> relationships = findRelationships(deploymentElementsInEnvironment);

        for (Relationship relationship : relationships) {
            writer.append(String.format("      - from: \"%s\"", relationship.getSourceId()));
            writer.append(LINE_SEPARATOR);
            writer.append(String.format("        to: \"%s\"", relationship.getDestinationId()));
            writer.append(LINE_SEPARATOR);

            if (!StringUtils.isNullOrEmpty(relationship.getDescription())) {
                writer.append(String.format("        label: \"%s\"", relationship.getDescription()));
                writer.append(LINE_SEPARATOR);
            }

            if (!StringUtils.isNullOrEmpty(relationship.getTechnology())) {
                writer.append(String.format("        description: \"%s\"", relationship.getTechnology()));
                writer.append(LINE_SEPARATOR);
            }

            writer.append(LINE_SEPARATOR);
        }

    }

    private Collection<Element> findAllChildren(DeploymentNode deploymentNode) {
        List<Element> deploymentElements = new ArrayList<>();

        List<DeploymentNode> deploymentNodes = new ArrayList<>(deploymentNode.getChildren());
        deploymentNodes.sort(Comparator.comparing(DeploymentNode::getId));
        for (DeploymentNode child : deploymentNodes) {
            deploymentElements.addAll(findAllChildren(child));
        }

        deploymentElements.addAll(deploymentNode.getSoftwareSystemInstances().stream().sorted(Comparator.comparing(SoftwareSystemInstance::getId)).collect(Collectors.toList()));
        deploymentElements.addAll(deploymentNode.getContainerInstances().stream().sorted(Comparator.comparing(ContainerInstance::getId)).collect(Collectors.toList()));
        deploymentElements.addAll(deploymentNode.getInfrastructureNodes().stream().sorted(Comparator.comparing(InfrastructureNode::getId)).collect(Collectors.toList()));

        return deploymentElements;
    }

    private Collection<Relationship> findRelationships(Collection<Element> elements) {
        List<Relationship> relationships = new ArrayList<>();

        for (Element element : elements) {
            List<Relationship> sortedRelationships = new ArrayList<>(element.getRelationships());
            sortedRelationships.sort(Comparator.comparing(Relationship::getId));
            for (Relationship relationship : sortedRelationships) {
                if (elements.contains(relationship.getSource()) && elements.contains(relationship.getDestination())) {
                    relationships.add(relationship);
                }
            }
        }

        return relationships;
    }

    private boolean include(Relationship relationship, Set<Class> elementTypes) {
        Element source = relationship.getSource();
        Element destination = relationship.getDestination();

        return elementTypes.contains(source.getClass()) && elementTypes.contains(destination.getClass());
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
        } else if (e instanceof SoftwareSystemInstance) {
            type = "Software System";
        } else if (e instanceof ContainerInstance) {
            Container container = ((ContainerInstance)e).getContainer();
            type = "Container" + (hasValue(container.getTechnology()) ? ": " + container.getTechnology() : "");
        } else {
            type = e.getClass().getSimpleName();
        }

        return openingMetadataSymbol + type + closingMetadataSymbol;
    }

    private boolean hasValue(String s) {
        return !StringUtils.isNullOrEmpty(s);
    }

    public void write(Workspace workspace, Writer writer) throws Exception {
        writeModel(workspace, writer);
    }

    public String toString(Workspace workspace) throws Exception {
        StringWriter stringWriter = new StringWriter();
        writeModel(workspace, stringWriter);

        return stringWriter.toString();
    }
}