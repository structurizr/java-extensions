package com.structurizr.io.plantuml;

import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.function.BiConsumer;

import static java.lang.String.format;

/**
 * A writer that outputs diagram definitions that can be used to create diagrams
 * using PlantUML (http://plantuml.com/plantuml/).
 *
 * System landscape, system context, container, component, dynamic and deployment diagrams are supported.
 */
public class PlantUMLWriter extends AbstractPlantUMLWriter {

    private boolean includeNotesForActors = true;

    /**
     * Creates a new PlantUMLWriter, with some default skin params.
     */
    public PlantUMLWriter() {
        // add some default skin params
        addSkinParam("shadowing", "false");
        addSkinParam("arrowColor", "#707070");
        addSkinParam("actorBorderColor", "#707070");
        addSkinParam("componentBorderColor", "#707070");
        addSkinParam("rectangleBorderColor", "#707070");
        addSkinParam("noteBackgroundColor", "#ffffff");
        addSkinParam("noteBorderColor", "#707070");
        addSkinParam("defaultTextAlignment", "center");
        addSkinParam("wrapWidth", "200");
        addSkinParam("maxMessageSize", "100");
    }

    protected boolean isIncludeNotesForActors() {
        return includeNotesForActors;
    }

    public void setIncludeNotesForActors(boolean includeNotesForActors) {
        this.includeNotesForActors = includeNotesForActors;
    }

    @Override
    protected void write(ContainerView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Container))
                    .map(ElementView::getElement)
                    .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                    .forEach(e -> write(view, e, writer, false));

            writeContainerForSoftwareSystem(view, writer, (writtenView, usedWriter) -> {
                writtenView.getElements().stream()
                        .filter(ev -> ev.getElement() instanceof Container)
                        .map(ElementView::getElement)
                        .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                        .forEach(e -> write(writtenView, e, usedWriter, true));
            });

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeContainerForSoftwareSystem(ContainerView view, Writer writer,
                                                 BiConsumer<ContainerView, Writer> packageContentWriter) throws IOException {
        writer.write("package \"" + view.getSoftwareSystem().getName() + "\" <<" + typeOf(view.getSoftwareSystem(), false) + ">> {");
        writer.write(System.lineSeparator());

        packageContentWriter.accept(view, writer);

        writer.write("}");
        writer.write(System.lineSeparator());
    }

    @Override
    protected void write(ComponentView view, Writer writer) {
        try {
            writeHeader(view, writer);

            view.getElements().stream()
                    .filter(ev -> !(ev.getElement() instanceof Component))
                    .map(ElementView::getElement)
                    .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                    .forEach(e -> write(view, e, writer, false));

            writeContainerForContainer(view, writer, (writtenView, usedWriter) -> {
                writtenView.getElements().stream()
                        .filter(ev -> ev.getElement() instanceof Component)
                        .map(ElementView::getElement)
                        .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                        .forEach(e -> write(writtenView, e, usedWriter, true));
            });

            writeRelationships(view, writer);

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeContainerForContainer(ComponentView view, Writer writer,
                                            BiConsumer<ComponentView, Writer> packageContentWriter) throws IOException {
        writer.write("package \"" + view.getContainer().getName() + "\" <<" + typeOf(view.getContainer(), false) + ">> {");
        writer.write(System.lineSeparator());

        packageContentWriter.accept(view, writer);

        writer.write("}");
        writer.write(System.lineSeparator());
    }

    @Override
    protected void write(DynamicView view, Writer writer) {
        try {
            writeHeader(view, writer);

            Set<Element> elements = new LinkedHashSet<>();
            for (RelationshipView relationshipView : view.getRelationships()) {
                elements.add(relationshipView.getRelationship().getSource());
                elements.add(relationshipView.getRelationship().getDestination());
            }

            if (isUseSequenceDiagrams()) {
                elements.forEach(element -> {
                   try {

                       writer.write(format("%s \"%s\" as %s <<%s>> %s%s",
                               plantumlSequenceType(view, element),
                               element.getName(),
                               idOf(element),
                               typeOf(element, false),
                               backgroundOf(view, element),
                               System.lineSeparator()));

                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                 });
            } else {
                elements.forEach(e -> write(view, e, writer, false));
            }
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

            writeFooter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void write(View view, DeploymentNode deploymentNode, Writer writer, int indent) {
        try {
            writer.write(
                    format("%snode \"%s\" <<%s>> as %s {",
                            calculateIndent(indent),
                            deploymentNode.getName() + (deploymentNode.getInstances() > 1 ? " (x" + deploymentNode.getInstances() + ")" : ""),
                            typeOf(deploymentNode, false),
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

    @Override
    protected void write(View view, Element element, Writer writer, int indent) {
        try {

            String shape = plantUMLShapeOf(view, element);

            if("actor".equals(shape)) {
                writeSimpleElement(view, element, writer, indent > 0, shape);

                if (includeNotesForActors) {
                    writeDescriptionAsNote(element, writer, indent > 0, element.getDescription());
                }
            } else {
                final String prefix = calculateIndent(indent);
                final String separator = System.lineSeparator();
                final String id = idOf(element);
                String background = backgroundOf(view, element);

                String name = element.getName();
                String description = element.getDescription();
                String type = typeOf(element, false);

                if (element instanceof ContainerInstance) {
                    ContainerInstance containerInstance = (ContainerInstance)element;
                    name = containerInstance.getContainer().getName();
                    description = containerInstance.getContainer().getDescription();
                    type = typeOf(containerInstance.getContainer(), false);
                    shape = plantUMLShapeOf(view, containerInstance.getContainer());
                    background = backgroundOf(view, containerInstance.getContainer());
                }

                writer.write(format("%s%s %s <<%s>> %s [%s",
                        prefix, shape, id, type, background, separator));
                writer.write(format("%s  %s%s", prefix, name, separator));
                if (!StringUtils.isNullOrEmpty(description)) {
                    writer.write(format("%s  --%s", prefix, separator));
                    writer.write(format("%s  %s%s", prefix, description, separator));
                }
                writer.write(format("%s]%s", prefix, separator));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSimpleElement(View view, Element element, Writer writer, boolean indent, String type) throws IOException {
        writer.write(format("%s%s \"%s\" <<%s>> as %s %s%s",
                indent ? "  " : "",
                type,
                element.getName(),
                typeOf(element, false),
                idOf(element),
                backgroundOf(view, element),
                System.lineSeparator()));
    }

    private void writeDescriptionAsNote(Element element, Writer writer, boolean indent, String description) throws IOException {
        if (!StringUtils.isNullOrEmpty(description)) {
            final String prefix = indent ? "  " : "";
            final String separator = System.lineSeparator();
            final String id = idOf(element);
            writer.write(format("%snote right of %s%s", prefix, id, separator));
            writer.write(format("%s  %s%s", prefix, description, separator));
            writer.write(format("%send note%s", prefix, separator));
        }
    }

    @Override
    protected void writeRelationship(View view, Relationship relationship, Writer writer) {
        try {
            String stereotypeAndDescription =
                (hasValue(relationship.getTechnology()) ? "<<" + relationship.getTechnology() + ">>\\n" : "") +
                (hasValue(relationship.getDescription()) ? relationship.getDescription() : "");

            writer.write(
                    format("%s .[%s].> %s %s",
                            idOf(relationship.getSource()),
                            view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship).getColor(),
                            idOf(relationship.getDestination()),
                            hasValue(stereotypeAndDescription) ? ": " + stereotypeAndDescription : ""
                    )
            );
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}