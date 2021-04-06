package com.structurizr.io.websequencediagrams;

import com.structurizr.Workspace;
import com.structurizr.io.AbstractExporterTests;
import com.structurizr.io.Diagram;
import com.structurizr.util.WorkspaceUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class WebSequenceDiagramsExporterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        WebSequenceDiagramsExporter exporter = new WebSequenceDiagramsExporter();

        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(1, diagrams.size());

        Diagram diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/websequencediagrams/36141-SignIn.wsd"));
        assertEquals(expected, diagram.getDefinition());
    }

}