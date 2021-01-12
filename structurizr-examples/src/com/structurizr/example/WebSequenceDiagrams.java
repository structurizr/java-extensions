package com.structurizr.example;

import com.structurizr.Workspace;
import com.structurizr.io.websequencediagrams.WebSequenceDiagramsWriter;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.*;

/**
 * An example of how to use the WebSequenceDiagrams writer. Run this program and copy/paste
 * the output into https://www.websequencediagrams.com
 */
public class WebSequenceDiagrams {

    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
        Model model = workspace.getModel();

        Person user = model.addPerson("User", "A user of my software system.");
        SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
        user.uses(softwareSystem, "Uses");

        ViewSet views = workspace.getViews();
        DynamicView dynamicView = views.createDynamicView("Dynamic", "An example of a dynamic diagram.");
        dynamicView.add(user, softwareSystem);

        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        WebSequenceDiagramsWriter webSequenceDiagramsWriter = new WebSequenceDiagramsWriter();
        webSequenceDiagramsWriter.toStdOut(workspace);
    }

}