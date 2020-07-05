package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.view.View;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AbstractPlantUMLWriterTests {

    @Test
    public void test_writeWorkspace_ThrowsAnExceptionWhenPassedANullWorkspace() throws Exception {
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();
        try {
            plantUMLWriter.write((Workspace)null, null);
            fail();
        } catch (Exception e) {
            assertEquals("A workspace must be provided.", e.getMessage());
        }
    }

    @Test
    public void test_writeWorkspace_ThrowsAnExceptionWhenPassedANullWriter() throws Exception {
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();
        Workspace workspace = new Workspace("Name", "Description");
        try {
            plantUMLWriter.write(workspace, null);
            fail();
        } catch (Exception e) {
            assertEquals("A writer must be provided.", e.getMessage());
        }
    }

    @Test
    public void test_writeView_ThrowsAnExceptionWhenPassedANullView() throws Exception {
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();
        try {
            plantUMLWriter.write((View)null, null);
            fail();
        } catch (Exception e) {
            assertEquals("A view must be provided.", e.getMessage());
        }
    }

}