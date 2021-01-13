package com.structurizr.graphviz;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
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
    public void test_writeSystemLandscapeViewWithGroupedElements() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setGroup("External");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setGroup("Internal");
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
                "  subgraph cluster_group_1 {\n" +
                "    margin=25\n" +
                "    1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "  }\n" +
                "  subgraph cluster_group_2 {\n" +
                "    margin=25\n" +
                "    2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "  }\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

    @Test
    public void test_writeSystemLandscapeViewWithNoEnterpiseBoundaryInGermanLocale() throws Exception {
        // ranksep=1.0 was being output as ranksep=1,0
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

    @Test
    public void test_writeSystemContextViewWithGroupedElements() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        Person user = workspace.getModel().addPerson("User", "");
        user.setGroup("External");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        softwareSystem.setGroup("Internal");
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
                "  subgraph cluster_group_1 {\n" +
                "    margin=25\n" +
                "    1 [width=1.500000,height=1.000000,fixedsize=true,id=1,label=\"1: User\"]\n" +
                "  }\n" +
                "  subgraph cluster_group_2 {\n" +
                "    margin=25\n" +
                "    2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Software System\"]\n" +
                "  }\n" +
                "\n" +
                "  1 -> 2 [id=3]\n" +
                "}", content);
    }

    @Test
    public void test_writeContainerViewWithGroupedElements() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        Container container1 = softwareSystem.addContainer("Container 1");
        container1.setGroup("Group 1");
        Container container2 = softwareSystem.addContainer("Container 2");
        container2.setGroup("Group 2");
        Container container3 = softwareSystem.addContainer("Container 3");

        container1.uses(container2, "Uses");
        container2.uses(container3, "Uses");

        ContainerView view = workspace.getViews().createContainerView(softwareSystem, "Containers", "");
        view.addAllElements();

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "Containers.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  subgraph cluster_1 {\n" +
                "    margin=25\n" +
                "    subgraph cluster_group_1 {\n" +
                "      margin=25\n" +
                "      2 [width=1.500000,height=1.000000,fixedsize=true,id=2,label=\"2: Container 1\"]\n" +
                "    }\n" +
                "    subgraph cluster_group_2 {\n" +
                "      margin=25\n" +
                "      3 [width=1.500000,height=1.000000,fixedsize=true,id=3,label=\"3: Container 2\"]\n" +
                "    }\n" +
                "    4 [width=1.500000,height=1.000000,fixedsize=true,id=4,label=\"4: Container 3\"]\n" +
                "  }\n" +
                "\n" +
                "  2 -> 3 [id=5]\n" +
                "  3 -> 4 [id=6]\n" +
                "}", content);
    }

    @Test
    public void test_writeComponentViewWithGroupedElements() throws Exception {
        Workspace workspace = new Workspace("Name", "");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System", "");
        Container container = softwareSystem.addContainer("Container");
        Component component1 = container.addComponent("Component 1", "");
        Component component2 = container.addComponent("Component 2", "");
        component2.setGroup("Group 2");
        Component component3 = container.addComponent("Component 3", "");
        component3.setGroup("Group 3");

        component1.uses(component2, "Uses");
        component2.uses(component3, "Uses");

        ComponentView view = workspace.getViews().createComponentView(container, "Components", "");
        view.addAllElements();

        DotFileWriter dotFileWriter = new DotFileWriter(PATH, RankDirection.TopBottom, 1.0, 1.0);
        dotFileWriter.write(view);

        File file = new File(PATH, "Components.dot");
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [splines=polyline,rankdir=TB,ranksep=1.0,nodesep=1.0,fontsize=5]\n" +
                "  node [shape=box,fontsize=5]\n" +
                "  edge []\n" +
                "\n" +
                "  subgraph cluster_2 {\n" +
                "    margin=25\n" +
                "    subgraph cluster_group_1 {\n" +
                "      margin=25\n" +
                "      4 [width=1.500000,height=1.000000,fixedsize=true,id=4,label=\"4: Component 2\"]\n" +
                "    }\n" +
                "    subgraph cluster_group_2 {\n" +
                "      margin=25\n" +
                "      5 [width=1.500000,height=1.000000,fixedsize=true,id=5,label=\"5: Component 3\"]\n" +
                "    }\n" +
                "    3 [width=1.500000,height=1.000000,fixedsize=true,id=3,label=\"3: Component 1\"]\n" +
                "  }\n" +
                "\n" +
                "  3 -> 4 [id=6]\n" +
                "  4 -> 5 [id=7]\n" +
                "}", content);
    }

}