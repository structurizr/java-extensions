package com.structurizr.example;

import com.structurizr.api.StructurizrClient;
import com.structurizr.fluent.FluentWorkspace;
import com.structurizr.fluent.model.FluentModel;

public class FluentApi {

    public static void main(String[] args) throws Exception {
        FluentWorkspace workspace = new FluentWorkspace("Fluent Structurizr example", "This is a model of my software system");
        FluentModel.SofwareSystemSearch search = workspace.model().softwareSystem();
        workspace.model()
                .createImpliedRelationshipsUnlessAnyRelationshipExistsStrategy()
                .addPerson("User", "A user of my software system")
                    .url("http://myself.com")
                    .external()
                    .tags("T1", "T2", "T3")
                    .addProperty("Some property", "Some value")
                    .parent()
                .addSoftwareSystem("Software System", "My software system")
                    .internal()
                    .addPerspective("P1", "P1 description")
                    .addContainer("A container", "My container")
                        .technology("The best technology")
                        .parent()
                    .parent()
                .addSoftwareSystem("Another system")
                    .uses(search.byNameGet("Software System"), "redirects traffic to").source()
                    .uses(search.byNameGet("Another system"), "use self");

        StructurizrClient structurizrClient = new StructurizrClient("API_KEY", "API_SECRET");
        structurizrClient.putWorkspace(1L, workspace.asStructurizr());
    }

}
