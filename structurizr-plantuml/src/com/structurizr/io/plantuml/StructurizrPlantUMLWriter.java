package com.structurizr.io.plantuml;

import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static java.lang.String.format;

/**
 * A writer that outputs diagram definitions that can be used to create diagrams
 * using PlantUML (http://plantuml.com/plantuml/).
 *
 * System landscape, system context, container, component, dynamic and deployment diagrams are supported.
 */
public class StructurizrPlantUMLWriter extends AbstractPlantUMLWriter {

    /**
     * Creates a new PlantUMLWriter, with some default skin params.
     */
    public StructurizrPlantUMLWriter() {
        addSkinParam("shadowing", "false");
        addSkinParam("arrowFontSize", "10");
        addSkinParam("defaultTextAlignment", "center");
        addSkinParam("wrapWidth", "200");
        addSkinParam("maxMessageSize", "100");
    }

    @Override
    protected void write(ContainerView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Container))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, false));

            writer.write(format("package \"%s\\n%s\" {", view.getSoftwareSystem().getName(), typeOf(view.getSoftwareSystem(), true)));
            writer.write(System.lineSeparator());

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof Container && ev.getElement().getParent().equals(view.getSoftwareSystem()))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, true));

            writer.write("}");
            writer.write(System.lineSeparator());

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void write(ComponentView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Component))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, false));

            writer.write(format("package \"%s\\n%s\" {", view.getContainer().getName(), typeOf(view.getContainer(), true)));
            writer.write(System.lineSeparator());

            view.getElements().stream()
                    .filter(ev -> ev.getElement() instanceof Component && ev.getElement().getParent().equals(view.getContainer()))
                    .map(ElementView::getElement)
                    .sorted(Comparator.comparing(Element::getName))
                    .forEach(e -> write(view, e, writer, true));

            writer.write("}");
            writer.write(System.lineSeparator());

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void write(DynamicView view, Writer writer) {
        try {
            writeHeader(view, writer);

            if (!isUseSequenceDiagrams()) {
                writer.write("left to right direction");
                writer.write(System.lineSeparator());
                writer.write(System.lineSeparator());
            }

            Set<Element> elements = new LinkedHashSet<>();
            for (RelationshipView relationshipView : view.getRelationships()) {
                elements.add(relationshipView.getRelationship().getSource());
                elements.add(relationshipView.getRelationship().getDestination());
            }

            if (isUseSequenceDiagrams()) {
                elements.forEach(element -> {
                    try {
                        writer.write(format("%s \"%s\\n<size:10>%s</size>\" as %s <<%s>> %s%s",
                                plantumlSequenceType(view, element),
                                element.getName(),
                                typeOf(element, true),
                                idOf(element),
                                idOf(element),
                                backgroundOf(view, element),
                                System.lineSeparator()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                view.getRelationships().forEach(relationship -> {
                    try {
                        writer.write(
                                format("%s -[%s]> %s : %s. %s",
                                        idOf(relationship.getRelationship().getSource()),
                                        view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship.getRelationship()).getColor(),
                                        idOf(relationship.getRelationship().getDestination()),
                                        relationship.getOrder(),
                                        hasValue(relationship.getDescription()) ? relationship.getDescription() : hasValue(relationship.getRelationship().getDescription()) ? relationship.getRelationship().getDescription() : ""
                                )
                        );
                        writer.write(System.lineSeparator());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } else {
                elements.forEach(e -> write(view, e, writer, false));

                view.getRelationships().forEach(relationship -> {
                    writeRelationship(view, relationship.getRelationship(), relationship.getOrder() + ". " + (hasValue(relationship.getDescription()) ? relationship.getDescription() : hasValue(relationship.getRelationship().getDescription()) ? relationship.getRelationship().getDescription() : ""), writer);
                });
            }

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void write(View view, DeploymentNode deploymentNode, Writer writer, int indent) {
        try {
            writer.write(
                    format("%snode \"%s\\n%s\" <<%s>> as %s {",
                            calculateIndent(indent),
                            deploymentNode.getName() + (deploymentNode.getInstances() > 1 ? " (x" + deploymentNode.getInstances() + ")" : ""),
                            typeOf(deploymentNode, true),
                            idOf(deploymentNode),
                            idOf(deploymentNode)
                    )
            );

            writer.write(System.lineSeparator());

            List<DeploymentNode> children = new ArrayList<>(deploymentNode.getChildren());
            children.sort(Comparator.comparing(DeploymentNode::getName));
            for (DeploymentNode child : children) {
                write(view, child, writer, indent + 1);
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
                    format("%s}", calculateIndent(indent))
            );
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(View view, Element element, Writer writer, int indent) {
        try {
            String shape = plantUMLShapeOf(view, element);
            if ("actor".equals(shape)) {
                shape = "rectangle";
            }
            String name = element.getName();
            String description = element.getDescription();
            String type = typeOf(element, true);

            if (element instanceof ContainerInstance) {
                ContainerInstance containerInstance = (ContainerInstance)element;
                name = containerInstance.getContainer().getName();
                description = containerInstance.getContainer().getDescription();
                type = typeOf(containerInstance.getContainer(), true);
                shape = plantUMLShapeOf(view, containerInstance.getContainer());
            }

            final String prefix = calculateIndent(indent);
            final String separator = System.lineSeparator();
            final String id = idOf(element);

            writer.write(format("%s%s \"==%s\\n<size:10>%s</size>%s\" <<%s>> as %s%s",
                    prefix,
                    shape,
                    name,
                    type,
                    (StringUtils.isNullOrEmpty(description) ? "" : "\\n\\n" + description),
                    id,
                    id,
                    separator)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void writeRelationship(View view, Relationship relationship, Writer writer) {
        writeRelationship(view, relationship, relationship.getDescription(), writer);
    }

    private void writeRelationship(View view, Relationship relationship, String description, Writer writer) {
        try {
            RelationshipStyle style = relationshipStyleOf(view, relationship);
            if (style.getDashed() == null) {
                style.setDashed(true);
            }

            // 1 .[#rrggbb].> 2 : "...\n<size:8>...</size>
            writer.write(format("%s %s[%s]%s> %s : \"%s%s\"%s",
                    idOf(relationship.getSource()),
                    style.getDashed() ? "." : "-",
                    style.getColor(),
                    style.getDashed() ? "." : "-",
                    idOf(relationship.getDestination()),
                    description,
                    (StringUtils.isNullOrEmpty(relationship.getTechnology()) ? "" : "\\n<size:8>[" + relationship.getTechnology() + "]</size>"),
                    System.lineSeparator()
                ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void writeHeader(View view, Writer writer) throws IOException {
        super.writeHeader(view, writer);

        writer.write("hide stereotype");
        writer.write(System.lineSeparator());

        if (view instanceof DynamicView && isUseSequenceDiagrams()) {
            // do nothing
        } else {
            if (view.getAutomaticLayout() != null) {
                switch (view.getAutomaticLayout().getRankDirection()) {
                    case LeftRight:
                        writer.write("left to right direction");
                        break;
                    default:
                        writer.write("top to bottom direction");
                        break;
                }
                writer.write(System.lineSeparator());
            }
        }

        for (ElementView elementView : view.getElements()) {
            Element element = elementView.getElement();

            String id = idOf(element);

            if (element instanceof ContainerInstance) {
                element = ((ContainerInstance)element).getContainer();
            }

            String type = plantUMLShapeOf(view, element);
            if ("actor".equals(type)) {
                type = "rectangle"; // the actor shape is not supported in this implementation
            }
            String background = backgroundOf(view, element);
            String stroke = strokeOf(view, element);
            String color = colorOf(view, element);
            Shape shape = shapeOf(view, element);

            if (view instanceof DynamicView && isUseSequenceDiagrams()) {
                type = "sequenceParticipant";
            }

            writer.write(format("skinparam %s<<%s>> {%s", type, id, System.lineSeparator()));
            if (element instanceof DeploymentNode) {
                writer.write(format("  BackgroundColor #ffffff%s", System.lineSeparator()));
            } else {
                writer.write(format("  BackgroundColor %s%s", background, System.lineSeparator()));
            }
            writer.write(format("  FontColor %s%s", color, System.lineSeparator()));
            writer.write(format("  BorderColor %s%s", stroke, System.lineSeparator()));

            if (shape == Shape.RoundedBox) {
                writer.write(format("  roundCorner 20%s", System.lineSeparator()));
            }

            writer.write(format("}%s", System.lineSeparator()));
        }
    }

}