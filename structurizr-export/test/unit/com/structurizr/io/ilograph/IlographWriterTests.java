package com.structurizr.io.ilograph;

import com.structurizr.Workspace;
import com.structurizr.io.AbstractExporterTests;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.ThemeUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class IlographWriterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        IlographExporter ilographExporter = new IlographExporter();
        String definition = ilographExporter.export(workspace);

        String expected = readFile(new File("./test/unit/com/structurizr/io/ilograph/36141.ilograph"));
        assertEquals(expected, definition);
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        IlographExporter ilographExporter = new IlographExporter();
        String definition = ilographExporter.export(workspace);

        String expected = readFile(new File("./test/unit/com/structurizr/io/ilograph/54915.ilograph"));
        assertEquals(expected, definition);
    }

}