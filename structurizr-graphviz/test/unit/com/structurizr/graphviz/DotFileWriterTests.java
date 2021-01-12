package com.structurizr.graphviz;

import com.structurizr.Workspace;
import com.structurizr.model.Location;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.SystemLandscapeView;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DotFileWriterTests {

    private static final File PATH = new File("./build/");

    @Test
    public void test_writeSystemLandscapeViewWithNoEnterpiseBoundary() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setLocation(Location.External);
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setLocation(Location.Internal);
        user.uses(softwareSystem, "Uses");

        SystemLandscapeView view = workspace.getViews().createSystemLandscapeView("SystemLandscape", "");
        view.addAllElements();
        view.setEnterpriseBoundaryVisible(false);

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "SystemLandscape.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "  2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

    @Test
    public void test_writeSystemLandscapeViewWithNoEnterpiseBoundaryInGermanLocale() throws Exception {
        Locale.setDefault(new Locale("de", "DE"));
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setLocation(Location.External);
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setLocation(Location.Internal);
        user.uses(softwareSystem, "Uses");

        SystemLandscapeView view = workspace.getViews().createSystemLandscapeView("SystemLandscape", "");
        view.addAllElements();
        view.setEnterpriseBoundaryVisible(false);

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "SystemLandscape.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "  2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

    @Test
    public void test_writeSystemLandscapeViewWithAnEnterpriseBoundary() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setLocation(Location.External);
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setLocation(Location.Internal);
        user.uses(softwareSystem, "Uses");

        SystemLandscapeView view = workspace.getViews().createSystemLandscapeView("SystemLandscape", "");
        view.addAllElements();
        view.setEnterpriseBoundaryVisible(true);

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "SystemLandscape.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  subgraph cluster_enterprise {\n" +
                "    margin=25\n" +
                "    2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "  }\n" +
                "\n" +
                "  1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

    @Test
    public void test_writeSystemContextViewWithNoEnterpiseBoundary() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setLocation(Location.External);
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setLocation(Location.Internal);
        user.uses(softwareSystem, "Uses");

        SystemContextView view = workspace.getViews().createSystemContextView(softwareSystem, "SystemContext", "");
        view.addAllElements();
        view.setEnterpriseBoundaryVisible(false);

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "SystemContext.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "  2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }


    @Test
    public void test_writeSystemContextViewWithAnEnterpriseBoundary() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setLocation(Location.External);
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setLocation(Location.Internal);
        user.uses(softwareSystem, "Uses");

        SystemContextView view = workspace.getViews().createSystemContextView(softwareSystem, "SystemContext", "");
        view.addAllElements();
        view.setEnterpriseBoundaryVisible(true);

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "SystemContext.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  subgraph cluster_enterprise {\n" +
                "    margin=25\n" +
                "    2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "  }\n" +
                "\n" +
                "  1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

}