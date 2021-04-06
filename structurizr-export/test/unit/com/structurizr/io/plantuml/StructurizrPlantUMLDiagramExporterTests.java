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

public class StructurizrPlantUMLDiagramExporterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        StructurizrPlantUMLExporter exporter = new StructurizrPlantUMLExporter();

        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(7, diagrams.size());

        Diagram diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SystemLandscape.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemContext")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SystemContext.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-Containers.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-Components.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SignIn.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-DevelopmentDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-LiveDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());

        // and the sequence diagram version
        exporter.setUseSequenceDiagrams(true);
        diagrams = exporter.export(workspace);
        diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SignIn-sequence.puml"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        StructurizrPlantUMLExporter exporter = new StructurizrPlantUMLExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(1, diagrams.size());

        Diagram diagram = diagrams.stream().findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/54915-AmazonWebServicesDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_GroupsExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/groups.json"));
        ThemeUtils.loadThemes(workspace);

        StructurizrPlantUMLExporter exporter = new StructurizrPlantUMLExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(3, diagrams.size());

        Diagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/groups-SystemLandscape.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/groups-Containers.puml"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/groups-Components.puml"));
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

        Diagram diagram = new StructurizrPlantUMLExporter().export(containerView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "  PackageBorderColor<<group>> #cccccc\n" +
                "  PackageFontColor<<group>> #cccccc\n" +
                "}\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Software System 1\\n[Software System]\" <<1>> {\n" +
                "  skinparam PackageBorderColor<<1>> #444444\n" +
                "  skinparam PackageFontColor<<1>> #444444\n" +
                "\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<2>> as 2\n" +
                "}\n" +
                "\n" +
                "package \"Software System 2\\n[Software System]\" <<3>> {\n" +
                "  skinparam PackageBorderColor<<3>> #cccccc\n" +
                "  skinparam PackageFontColor<<3>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<4>> as 4\n" +
                "}\n" +
                "\n" +
                "2 .[#707070,thickness=2].> 4 : \"Uses\"\n" +
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

        Diagram diagram = new StructurizrPlantUMLExporter().export(componentView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "  PackageBorderColor<<group>> #cccccc\n" +
                "  PackageFontColor<<group>> #cccccc\n" +
                "}\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<3>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Container 1\\n[Container]\" <<2>> {\n" +
                "  skinparam PackageBorderColor<<2>> #444444\n" +
                "  skinparam PackageFontColor<<2>> #444444\n" +
                "\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<3>> as 3\n" +
                "}\n" +
                "\n" +
                "package \"Container 2\\n[Container]\" <<5>> {\n" +
                "  skinparam PackageBorderColor<<5>> #cccccc\n" +
                "  skinparam PackageFontColor<<5>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<6>> as 6\n" +
                "}\n" +
                "\n" +
                "3 .[#707070,thickness=2].> 6 : \"Uses\"\n" +
                "@enduml", diagram.getDefinition());
    }

}