package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.io.AbstractExporterTests;
import com.structurizr.io.Diagram;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.AutomaticLayout;
import com.structurizr.view.ComponentView;
import com.structurizr.view.ContainerView;
import com.structurizr.view.ThemeUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class C4PlantUMLDiagramExporterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        C4PlantUMLExporter exporter = new C4PlantUMLExporter();

        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(7, diagrams.size());

        Diagram diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-SystemLandscape.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemContext")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-SystemContext.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-Containers.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-Components.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-SignIn.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-DevelopmentDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/36141-LiveDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());

        try {
            // and the sequence diagram version ... which isn't supported
            exporter.setUseSequenceDiagrams(true);
            exporter.export(workspace);
        } catch (UnsupportedOperationException uoe) {
            assertEquals("Sequence diagrams are not supported by C4-PlantUML", uoe.getMessage());
        }
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        C4PlantUMLExporter exporter = new C4PlantUMLExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(1, diagrams.size());

        Diagram diagram = diagrams.stream().findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/54915-AmazonWebServicesDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_GroupsExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/groups.json"));
        ThemeUtils.loadThemes(workspace);

        C4PlantUMLExporter exporter = new C4PlantUMLExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(3, diagrams.size());

        Diagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/groups-SystemLandscape.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/groups-Containers.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/c4plantuml/groups-Components.puml"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_renderContainerDiagramWithExternalContainers() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem1 = workspace.getModel().addSoftwareSystem("Software System 1");
        Container container1 = softwareSystem1.addContainer("Container 1");
        SoftwareSystem softwareSystem2 = workspace.getModel().addSoftwareSystem("Software System 2");
        Container container2 = softwareSystem2.addContainer("Container 2");

        container1.uses(container2, "Uses");

        ContainerView containerView = workspace.getViews().createContainerView(softwareSystem1, "Containers", "");
        containerView.add(container1);
        containerView.add(container2);

        containerView.setExternalSoftwareSystemBoundariesVisible(true);
        Diagram diagram = new C4PlantUMLExporter().export(containerView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
                "LAYOUT_WITH_LEGEND()\n" +
                "\n" +
                "System_Boundary(\"1_boundary\", \"Software System 1\") {\n" +
                "  Container(2, \"Container 1\", \"\")\n" +
                "}\n" +
                "\n" +
                "System_Boundary(\"3_boundary\", \"Software System 2\") {\n" +
                "  Container(4, \"Container 2\", \"\")\n" +
                "}\n" +
                "\n" +
                "Rel_D(2, 4, \"Uses\")\n" +
                "@enduml", diagram.getDefinition());


        containerView.setExternalSoftwareSystemBoundariesVisible(false);
        diagram = new C4PlantUMLExporter().export(containerView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
                "LAYOUT_WITH_LEGEND()\n" +
                "\n" +
                "System_Boundary(\"1_boundary\", \"Software System 1\") {\n" +
                "  Container(2, \"Container 1\", \"\")\n" +
                "}\n" +
                "\n" +
                "Container(4, \"Container 2\", \"\")\n" +
                "\n" +
                "Rel_D(2, 4, \"Uses\")\n" +
                "@enduml", diagram.getDefinition());
    }

    @Test
    public void test_renderComponentDiagramWithExternalComponents() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem1 = workspace.getModel().addSoftwareSystem("Software System 1");
        Container container1 = softwareSystem1.addContainer("Container 1");
        Component component1 = container1.addComponent("Component 1");
        SoftwareSystem softwareSystem2 = workspace.getModel().addSoftwareSystem("Software System 2");
        Container container2 = softwareSystem2.addContainer("Container 2");
        Component component2 = container2.addComponent("Component 2");

        component1.uses(component2, "Uses");

        ComponentView componentView = workspace.getViews().createComponentView(container1, "Components", "");
        componentView.add(component1);
        componentView.add(component2);

        componentView.setExternalSoftwareSystemBoundariesVisible(true);
        Diagram diagram = new C4PlantUMLExporter().export(componentView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
                "LAYOUT_WITH_LEGEND()\n" +
                "\n" +
                "Container_Boundary(\"2_boundary\", \"Container 1\") {\n" +
                "  Component(3, \"Component 1\", \"\")\n" +
                "}\n" +
                "\n" +
                "Container_Boundary(\"5_boundary\", \"Container 2\") {\n" +
                "  Component(6, \"Component 2\", \"\")\n" +
                "}\n" +
                "\n" +
                "Rel_D(3, 6, \"Uses\")\n" +
                "@enduml", diagram.getDefinition());

        componentView.setExternalSoftwareSystemBoundariesVisible(false);
        diagram = new C4PlantUMLExporter().export(componentView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
                "!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
                "LAYOUT_WITH_LEGEND()\n" +
                "\n" +
                "Container_Boundary(\"2_boundary\", \"Container 1\") {\n" +
                "  Component(3, \"Component 1\", \"\")\n" +
                "}\n" +
                "\n" +
                "Component(6, \"Component 2\", \"\")\n" +
                "\n" +
                "Rel_D(3, 6, \"Uses\")\n" +
                "@enduml", diagram.getDefinition());
    }

}