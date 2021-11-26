package com.structurizr.io.mermaid;

import com.structurizr.io.AbstractDiagramExporter;
import com.structurizr.io.IndentingWriter;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import static java.lang.String.format;

/**
 * Exports diagram definitions that can be used to create diagrams
 * using mermaid (https://mermaidjs.github.io).
 *
 * System landscape, system context, container, component, dynamic and deployment diagrams are supported.
 * Deployment node -&gt; deployment node relationships are not rendered.
 */
public class MermaidDiagramExporter extends AbstractDiagramExporter {

    private int groupId = 0;

    public MermaidDiagramExporter() {
    }

    @Override
    protected void writeHeader(View view, IndentingWriter writer) {
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

        writer.writeLine("graph " + direction);
        writer.indent();
        writer.writeLine("linkStyle default fill:#ffffff");
        writer.writeLine();
    }

    @Override
    protected void writeFooter(View view, IndentingWriter writer) {
        writer.outdent();
    }

    @Override
    protected void startEnterpriseBoundary(String enterpriseName, IndentingWriter writer) {
        writer.writeLine("subgraph enterprise [" + enterpriseName + "]");
        writer.indent();
        writer.writeLine("style enterprise fill:#ffffff,stroke:#444444,color:#444444");
        writer.writeLine();
    }

    @Override
    protected void endEnterpriseBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("end");
        writer.writeLine();
    }

    @Override
    protected void startGroupBoundary(View view, String group, IndentingWriter writer) {
        groupId++;

        String color = "#cccccc";

        // is there a style for the group?
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle("Group:" + group);

        if (elementStyle == null || StringUtils.isNullOrEmpty(elementStyle.getColor())) {
            // no, so is there a default group style?
            elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle("Group");
        }

        if (elementStyle != null && !StringUtils.isNullOrEmpty(elementStyle.getColor())) {
            color = elementStyle.getColor();
        }

        writer.writeLine(String.format("subgraph group%s [" + group + "]", groupId));
        writer.indent();
        writer.writeLine(String.format("style group%s fill:#ffffff,stroke:%s,color:%s", groupId, color, color));
        writer.writeLine();
    }

    @Override
    protected void endGroupBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("end");
        writer.writeLine();
    }

    @Override
    protected void startSoftwareSystemBoundary(View view, SoftwareSystem softwareSystem, IndentingWriter writer) {
        String color;
        if (softwareSystem.equals(view.getSoftwareSystem())) {
            color = "#444444";
        } else {
            color = "#cccccc";
        }

        writer.writeLine(String.format("subgraph %s [%s]", softwareSystem.getId(), softwareSystem.getName()));
        writer.indent();
        writer.writeLine(String.format("style %s fill:#ffffff,stroke:%s,color:%s", softwareSystem.getId(), color, color));
        writer.writeLine();
    }

    @Override
    protected void endSoftwareSystemBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("end");
        writer.writeLine();
    }

    @Override
    protected void startContainerBoundary(View view, Container container, IndentingWriter writer) {
        String color = "#444444";
        if (view instanceof ComponentView) {
            if (container.equals(((ComponentView)view).getContainer())) {
                color = "#444444";
            } else {
                color = "#cccccc";
            }
        } else if (view instanceof DynamicView) {
            if (container.equals(((DynamicView)view).getElement())) {
                color = "#444444";
            } else {
                color = "#cccccc";
            }
        }

        writer.writeLine(String.format("subgraph %s [%s]", container.getId(), container.getName()));
        writer.indent();
        writer.writeLine(String.format("style %s fill:#ffffff,stroke:%s,color:%s", container.getId(), color, color));
        writer.writeLine();
    }

    @Override
    protected void endContainerBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("end");
        writer.writeLine();
    }

    @Override
    protected void startDeploymentNodeBoundary(DeploymentView view, DeploymentNode deploymentNode, IndentingWriter writer) {
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(deploymentNode);

        writer.writeLine(String.format("subgraph %s [%s]", deploymentNode.getId(), deploymentNode.getName()));
        writer.indent();
        writer.writeLine(String.format("style %s fill:#ffffff,stroke:%s,color:%s", deploymentNode.getId(), elementStyle.getStroke(), elementStyle.getColor()));
        writer.writeLine();
    }

    @Override
    protected void endDeploymentNodeBoundary(IndentingWriter writer) {
        writer.outdent();
        writer.writeLine("end");
        writer.writeLine();
    }

    @Override
    protected void writeElement(View view, Element element, IndentingWriter writer) {
        ElementStyle elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(element);

        String name = element.getName();
        String description = element.getDescription();
        String type = typeOf(view, element, true);

        if (element instanceof StaticStructureElementInstance) {
            StaticStructureElementInstance elementInstance = (StaticStructureElementInstance)element;
            name = elementInstance.getElement().getName();
            description = elementInstance.getElement().getDescription();
            type = typeOf(view, elementInstance.getElement(), true);
            elementStyle = view.getViewSet().getConfiguration().getStyles().findElementStyle(elementInstance.getElement());
        }

        String nodeOpeningSymbol = "[";
        String nodeClosingSymbol = "]";

        if (elementStyle.getShape() == Shape.RoundedBox) {
            nodeOpeningSymbol = "(";
            nodeClosingSymbol = ")";
        } else if (elementStyle.getShape() == Shape.Cylinder) {
            nodeOpeningSymbol = "[(";
            nodeClosingSymbol = ")]";
        }

        if (StringUtils.isNullOrEmpty(description) || false == elementStyle.getDescription()) {
            description = "";
        } else {
            description = String.format("<div style='font-size: 80%%; margin-top:10px'>%s</div>", lines(description));
        }

        if (false == elementStyle.getMetadata()) {
            type = "";
        } else {
            type = String.format("<div style='font-size: 70%%; margin-top: 0px'>%s</div>", type);
        }

        writer.writeLine(format("%s%s\"<div style='font-weight: bold'>%s</div>%s%s\"%s",
                element.getId(),
                nodeOpeningSymbol,
                name,
                type,
                description,
                nodeClosingSymbol
        ));

        if (!StringUtils.isNullOrEmpty(element.getUrl())) {
            writer.writeLine(format("click %s %s \"%s\"", element.getId(), element.getUrl(), element.getUrl()));
        }

        if (element instanceof StaticStructureElementInstance) {
            Element e = ((StaticStructureElementInstance)element).getElement();
            writer.writeLine(format("style %s fill:%s,stroke:%s,color:%s", element.getId(), elementStyle.getBackground(), elementStyle.getStroke(), elementStyle.getColor()));
        } else {
            writer.writeLine(format("style %s fill:%s,stroke:%s,color:%s", element.getId(), elementStyle.getBackground(), elementStyle.getStroke(), elementStyle.getColor()));
        }
    }

    @Override
    protected void writeRelationship(View view, RelationshipView relationshipView, IndentingWriter writer) {
        Relationship relationship = relationshipView.getRelationship();
        RelationshipStyle style = view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship);

        Element source = relationship.getSource();
        Element destination = relationship.getDestination();

        if (source instanceof DeploymentNode || destination instanceof DeploymentNode) {
            return;
        }

        if (relationshipView.isResponse() != null && relationshipView.isResponse()) {
            source = relationship.getDestination();
            destination = relationship.getSource();
        }

        // solid: A-- text -->B
        // dotted: A-. text .->B

        if (style.getDashed() == null) {
            style.setDashed(true);
        }

        String description = relationshipView.getDescription();
        if (StringUtils.isNullOrEmpty(description)) {
            description = relationshipView.getRelationship().getDescription();
        }

        if (!StringUtils.isNullOrEmpty(relationshipView.getOrder())) {
            description = relationshipView.getOrder() + ". " + description;
        }

        writer.writeLine(
                format("%s-%s \"<div>%s</div><div style='font-size: 70%%'>%s</div>\" %s->%s",
                        source.getId(),
                        style.getDashed() ? "." : "-",
                        lines(description),
                        !StringUtils.isNullOrEmpty(relationship.getTechnology()) ? "[" + relationship.getTechnology() + "]" : "",
                        style.getDashed() ? "." : "-",
                        destination.getId()
                )
        );
    }

    private String lines(final String text) {
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

}