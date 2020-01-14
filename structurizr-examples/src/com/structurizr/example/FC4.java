package com.structurizr.example;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.io.fc4.Fc4Importer;
import com.structurizr.io.plantuml.C4PlantUMLWriter;

import java.io.File;
import java.io.StringWriter;

/**
 * Imports software architecture models defined in the fc4 YAML DSL.
 *
 * Please note: this is a prototype only.
 */
public class FC4 {

    private static final long WORKSPACE_ID = 50080;
    private static final String API_KEY = "key";
    private static final String API_SECRET = "secret";

    public static void main(String[] args) throws Exception {
        File fc4Directory = new File("./structurizr-examples/src/com/structurizr/example/fc4/bigbankplc");
        Workspace workspace = new Fc4Importer("Big Bank plc", "This is an example workspace, imported from an FC4 YAML model definition.", fc4Directory).importWorkspace();

        // upload the workspace to Structurizr
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.setMergeFromRemote(false);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);

        // example of how to export a Structurizr workspace to C4-PlantUML
        C4PlantUMLWriter c4PlantUMLWriter = new C4PlantUMLWriter();
        StringWriter stringWriter = new StringWriter();
        c4PlantUMLWriter.write(workspace, stringWriter);
        System.out.println(stringWriter.toString());
    }

}