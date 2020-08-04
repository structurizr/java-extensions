package com.structurizr.io.websequencediagrams;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.DynamicView;
import com.structurizr.view.RelationshipView;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A simple writer that outputs dynamic diagram definitions that can be copy-pasted
 * into https://www.websequencediagrams.com
 *
 * This implementation only supports a basic sequence of interactions,
 * both synchronous and asynchronous. It doesn't support return messages,
 * parallel behaviour, etc.
 */
public final class WebSequenceDiagramsWriter {

    private static final String SYNCHRONOUS_INTERACTION = "->";
    private static final String ASYNCHRONOUS_INTERACTION = "->>";
    private static final String SYNCHRONOUS_INTERACTION_RETURN = "-->";
    private static final String ASYNCHRONOUS_INTERACTION_RETURN = "-->>";

    /**
     * Gets a single view as a WebSequenceDiagrams diagram definition.
     *
     * @param view      the view to write
     * @return          the WebSequenceDiagrams definition as a String
     */
    public String toString(DynamicView view) {
        StringWriter stringWriter = new StringWriter();
        write(view, stringWriter);
        return stringWriter.toString();
    }

    /**
     * Writes the dynamic views in the given workspace as WebSequenceDiagrams definitions, to the specified writer.
     *
     * @param workspace     the workspace containing the views to be written
     * @param writer        the Writer to write to
     */
    public void write(Workspace workspace, Writer writer) {
        if (workspace != null && writer != null) {
            for (DynamicView view : workspace.getViews().getDynamicViews()) {
                write(view, writer);
            }
        }
    }

    /**
     * Writes the dynamic views in the given workspace as WebSequenceDiagrams definitions, to stdout.
     *
     * @param workspace     the workspace containing the views to be written
     */
    public void write(Workspace workspace) {
        StringWriter stringWriter = new StringWriter();
        write(workspace, stringWriter);
        System.out.println(stringWriter.toString());
    }

    public void write(DynamicView view, Writer writer) {
        try {
            writer.write("title " + view.getName() + " - " + view.getKey());
            writer.write(System.lineSeparator());
            writer.write(System.lineSeparator());

            Set<Element> elements = new LinkedHashSet<>();
            for (RelationshipView relationshipView : view.getRelationships()) {
                elements.add(relationshipView.getRelationship().getSource());
                elements.add(relationshipView.getRelationship().getDestination());
            }

            for (Element element : elements) {
                if (element instanceof Person) {
                    writer.write(String.format("actor <<Person>>>\\n%s as %s", element.getName(), element.getName()));
                    writer.write(System.lineSeparator());
                } else if (element instanceof SoftwareSystem) {
                    writer.write(String.format("participant <<Software System>>>\\n%s as %s", element.getName(), element.getName()));
                    writer.write(System.lineSeparator());
                } else if (element instanceof Container) {
                    writer.write(String.format("participant <<Container>>>\\n%s as %s", element.getName(), element.getName()));
                    writer.write(System.lineSeparator());
                } else if (element instanceof Component) {
                    writer.write(String.format("participant <<Component>>>\\n%s as %s", element.getName(), element.getName()));
                    writer.write(System.lineSeparator());
                }
            }

            writer.write(System.lineSeparator());

            for (RelationshipView relationshipView : view.getRelationships()) {
                Relationship r = relationshipView.getRelationship();

                Element source = r.getSource();
                Element destination = r.getDestination();
                String arrow = r.getInteractionStyle() == InteractionStyle.Asynchronous ? ASYNCHRONOUS_INTERACTION : SYNCHRONOUS_INTERACTION;

                if (relationshipView.isResponse()) {
                    source = r.getDestination();
                    destination = r.getSource();
                    arrow = r.getInteractionStyle() == InteractionStyle.Asynchronous ? ASYNCHRONOUS_INTERACTION_RETURN : SYNCHRONOUS_INTERACTION_RETURN;
                }

                // Thing A->Thing B: Description
                writer.write(String.format("%s%s%s: %s",
                        source.getName(),
                        arrow,
                        destination.getName(),
                        relationshipView.getDescription()
                ));
                writer.write(System.lineSeparator());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Write the dynamic views in the given workspace as WebSequenceDiagrams definitions, to stdout.
     *
     * @param workspace     the workspace containing the views to be written
     */
    public void toStdOut(Workspace workspace) {
        if (workspace == null) {
            throw new IllegalArgumentException("A workspace must be provided.");
        }

        StringWriter stringWriter = new StringWriter();
        write(workspace, stringWriter);
        System.out.println(stringWriter.toString());
    }

}