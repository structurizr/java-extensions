package com.structurizr.io.plantuml;

import com.structurizr.io.Diagram;
import com.structurizr.io.IndentingWriter;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class StructurizrPlantUMLExporter extends AbstractPlantUMLExporter {

    public StructurizrPlantUMLExporter() {
        addSkinParam("shadowing", "false");
        addSkinParam("arrowFontSize", "10");
        addSkinParam("defaultTextAlignment", "center");
        addSkinParam("wrapWidth", "200");
        addSkinParam("maxMessageSize", "100");
        addSkinParam("PackageBorderColor<<group>>", "#cccccc");
        addSkinParam("PackageFontColor<<group>>", "#cccccc");
    }

    @Override
    protected void writeHeader(View view, IndentingWriter writer) {
        super.writeHeader(view, writer);

        writer.writeLine("hide stereotype");

        if (view instanceof DynamicView && isUseSequenceDiagrams()) {
            // do nothing
        } else {
            if (view.getAutomaticLayout() != null) {
                switch (view.getAutomaticLayout().getRankDirection()) {
                    case LeftRight:
                        writer.writeLine("left to right direction");
                        break;
                    default:
                        writer.writeLine("top to bottom direction");
                        break;
                }
            }

            writer.writeLine();
        }

        List<Element> elements = view.getElements().stream().map(ElementView::getElement).sorted(Comparator.comparing(Element::getName)).collect(Collectors.toList());
        for (Element element : elements) {
            String id = idOf(element);

            if (element instanceof StaticStructureElementInstance) {
                element = ((StaticStructureElementInstance)element).getElement();
            }

            String type = plantUMLShapeOf(view, element);
            if ("actor".equals(type)) {
                type = "rectangle"; // the actor shape is not supported in this implementation
            }

            ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(element);

            String background = elementStyle.getBackground();
            String stroke = elementStyle.getStroke();
            String color = elementStyle.getColor();
            Shape shape = elementStyle.getShape();

            if (view instanceof DynamicView && isUseSequenceDiagrams()) {
                type = "sequenceParticipant";
            }

            writer.writeLine(format("skinparam %s<<%s>> {", type, id));
            writer.indent();
            if (element instanceof DeploymentNode) {
                writer.writeLine("BackgroundColor #ffffff");
            } else {
                writer.writeLine(String.format("BackgroundColor %s", background));
            }
            writer.writeLine(String.format("FontColor %s", color));
            writer.writeLine(String.format("BorderColor %s", stroke));

            if (shape == Shape.RoundedBox) {
                writer.writeLine("roundCorner 20");
            }
            writer.outdent();
            writer.writeLine("}");
        }

        writer.writeLine();
    }

    @Override
    protected void startEnterpriseBoundary(String enterpriseName, IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.writeLine(String.format("package \"%s\" <<enterprise>> {", enterpriseName));
            writer.indent();
            writer.writeLine("skinparam PackageBorderColor<<enterprise>> #444444");
            writer.writeLine("skinparam PackageFontColor<<enterprise>> #444444");
            writer.writeLine();
        }
    }

    @Override
    protected void endEnterpriseBoundary(IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.outdent();
            writer.writeLine("}");
            writer.writeLine();
        }
    }

    @Override
    protected void startGroupBoundary(String group, IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.writeLine(String.format("package \"%s\\n[Group]\" <<group>> {", group));
            writer.indent();
        }
    }

    @Override
    protected void endGroupBoundary(IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.outdent();
            writer.writeLine("}");
            writer.writeLine();
        }
    }

    @Override
    protected void startSoftwareSystemBoundary(View view, SoftwareSystem softwareSystem, IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            String color;
            if (softwareSystem.equals(view.getSoftwareSystem())) {
                color = "#444444";
            } else {
                color = "#cccccc";
            }

            writer.writeLine(String.format("package \"%s\\n%s\" <<%s>> {", softwareSystem.getName(), typeOf(view, softwareSystem, true), softwareSystem.getId()));
            writer.indent();
            writer.writeLine(String.format("skinparam PackageBorderColor<<%s>> %s", softwareSystem.getId(), color));
            writer.writeLine(String.format("skinparam PackageFontColor<<%s>> %s", softwareSystem.getId(), color));
            writer.writeLine();
        }
    }

    @Override
    protected void endSoftwareSystemBoundary(IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.outdent();
            writer.writeLine("}");
            writer.writeLine();
        }
    }

    @Override
    protected void startContainerBoundary(View view, Container container, IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            String color = "#444444";
            if (view instanceof ComponentView) {
                if (container.equals(((ComponentView) view).getContainer())) {
                    color = "#444444";
                } else {
                    color = "#cccccc";
                }
            } else if (view instanceof DynamicView) {
                if (container.equals(((DynamicView) view).getElement())) {
                    color = "#444444";
                } else {
                    color = "#cccccc";
                }
            }

            writer.writeLine(String.format("package \"%s\\n%s\" <<%s>> {", container.getName(), typeOf(view, container, true), container.getId()));
            writer.indent();
            writer.writeLine(String.format("skinparam PackageBorderColor<<%s>> %s", container.getId(), color));
            writer.writeLine(String.format("skinparam PackageFontColor<<%s>> %s", container.getId(), color));
            writer.writeLine();
        }
    }

    @Override
    protected void endContainerBoundary(IndentingWriter writer) {
        if (!isUseSequenceDiagrams()) {
            writer.outdent();
            writer.writeLine("}");
            writer.writeLine();
        }
    }

    @Override
    protected void startDeploymentNodeBoundary(DeploymentView view, DeploymentNode deploymentNode, IndentingWriter writer) {
        writer.writeLine(
                format("node \"%s\\n%s\" <<%s>> as %s {",
                        deploymentNode.getName() + (deploymentNode.getInstances() > 1 ? " (x" + deploymentNode.getInstances() + ")" : ""),
                        typeOf(view, deploymentNode, true),
                        idOf(deploymentNode),
                        idOf(deploymentNode)
                )
        );
        writer.indent();
    }

    @Override
    protected void endDeploymentNodeBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("}");
        writer.writeLine();
    }

    @Override
    public Diagram export(DynamicView view) {
        if (isUseSequenceDiagrams()) {
            IndentingWriter writer = new IndentingWriter();
            writeHeader(view, writer);

            Set<Element> elements = new LinkedHashSet<>();
            for (RelationshipView relationshipView : view.getRelationships()) {
                elements.add(relationshipView.getRelationship().getSource());
                elements.add(relationshipView.getRelationship().getDestination());
            }

            for (Element element : elements) {
                writeElement(view, element, writer);
            }

            writeRelationships(view, writer);
            writeFooter(view, writer);

            return new Diagram(view, writer.toString());
        } else {
            return super.export(view);
        }
    }

    @Override
    protected void writeElement(View view, Element element, IndentingWriter writer) {
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(element);

        if (view instanceof DynamicView && isUseSequenceDiagrams()) {
            writer.writeLine(String.format("%s \"%s\\n<size:10>%s</size>\" as %s <<%s>> %s%s",
                    plantumlSequenceType(view, element),
                    element.getName(),
                    typeOf(view, element, true),
                    element.getId(),
                    element.getId(),
                    elementStyle.getBackground(),
                    System.lineSeparator()));
        } else {
            String shape = plantUMLShapeOf(view, element);
            if ("actor".equals(shape)) {
                shape = "rectangle";
            }
            String name = element.getName();
            String description = element.getDescription();
            String type = typeOf(view, element, true);

            if (element instanceof StaticStructureElementInstance) {
                StaticStructureElementInstance elementInstance = (StaticStructureElementInstance) element;
                elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(elementInstance.getElement());
                name = elementInstance.getElement().getName();
                description = elementInstance.getElement().getDescription();
                type = typeOf(view, elementInstance.getElement(), true);
                shape = plantUMLShapeOf(view, elementInstance.getElement());
            }

            if (StringUtils.isNullOrEmpty(description) || false == elementStyle.getDescription()) {
                description = "";
            } else {
                description = "\\n\\n" + description;
            }

            if (false == elementStyle.getMetadata()) {
                type = "";
            } else {
                type = String.format("\\n<size:10>%s</size>", type);
            }

            String id = idOf(element);

            writer.writeLine(format("%s \"==%s%s%s\" <<%s>> as %s",
                    shape,
                    name,
                    type,
                    description,
                    id,
                    id)
            );
        }
    }

    @Override
    protected void writeRelationship(View view, RelationshipView relationshipView, IndentingWriter writer) {
        Relationship relationship = relationshipView.getRelationship();
        RelationshipStyle style = relationshipStyleOf(view, relationship);

        String description = "";

        if (!StringUtils.isNullOrEmpty(relationshipView.getOrder())) {
            description = relationshipView.getOrder() + ". ";
        }

        description += (hasValue(relationshipView.getDescription()) ? relationshipView.getDescription() : hasValue(relationshipView.getRelationship().getDescription()) ? relationshipView.getRelationship().getDescription() : "");

        if (view instanceof DynamicView && isUseSequenceDiagrams()) {
            String arrowStart = "-";
            String arrowEnd = ">";

            if (relationshipView.isResponse() != null && relationshipView.isResponse() == true) {
                arrowStart = "<-";
                arrowEnd = "-";
            }

            writer.writeLine(
                    String.format("%s %s[%s]%s %s : %s",
                            relationship.getSource().getId(),
                            arrowStart,
                            style.getColor(),
                            arrowEnd,
                            relationship.getDestination().getId(),
                            description));
        } else {
            if (style.getDashed() == null) {
                style.setDashed(true);
            }

            String arrowStart;
            String arrowEnd;
            String relationshipStyle = style.getColor();

            if (style.getThickness() != null) {
                relationshipStyle += ",thickness=" + style.getThickness();
            }

            if (relationshipView.isResponse() != null && relationshipView.isResponse()) {
                arrowStart = style.getDashed() ? "<." : "<-";
                arrowEnd = style.getDashed() ? "." : "-";
            } else {
                arrowStart = style.getDashed() ? "." : "-";
                arrowEnd = style.getDashed() ? ".>" : "->";
            }

            // 1 .[#rrggbb,thickness=n].> 2 : "...\n<size:8>...</size>
            writer.writeLine(format("%s %s[%s]%s %s : \"%s%s\"",
                    idOf(relationship.getSource()),
                    arrowStart,
                    relationshipStyle,
                    arrowEnd,
                    idOf(relationship.getDestination()),
                    description,
                    (StringUtils.isNullOrEmpty(relationship.getTechnology()) ? "" : "\\n<size:8>[" + relationship.getTechnology() + "]</size>")
            ));
        }
    }

}