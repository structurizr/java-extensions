package com.structurizr.example;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.io.fc4.Fc4Importer;
import com.structurizr.util.WorkspaceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

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
        Workspace workspace = new Fc4Importer("Big Bank plc", "This is an example workspace to illustrate the key features of Structurizr, based around a fictional online banking system.", fc4Directory).importWorkspace();

        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.setMergeFromRemote(false);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}