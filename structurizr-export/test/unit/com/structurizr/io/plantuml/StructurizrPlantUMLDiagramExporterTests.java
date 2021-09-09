package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.io.AbstractExporterTests;
import com.structurizr.io.Diagram;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.*;
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
        assertEquals(3, diagram.getFrames().size());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemContext")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SystemContext.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(4, diagram.getFrames().size());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-Containers.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(6, diagram.getFrames().size());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-Components.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(4, diagram.getFrames().size());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-SignIn.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(6, diagram.getFrames().size());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-DevelopmentDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(4, diagram.getFrames().size());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/plantuml/structurizr/36141-LiveDeployment.puml"));
        assertEquals(expected, diagram.getDefinition());
        assertEquals(6, diagram.getFrames().size());

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

        containerView.setExternalSoftwareSystemBoundariesVisible(true);
        Diagram diagram = new StructurizrPlantUMLExporter().export(containerView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Software System 1\\n[Software System]\" <<SoftwareSystem1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1>> #444444\n" +
                "\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<SoftwareSystem1.Container1>> as SoftwareSystem1.Container1\n" +
                "}\n" +
                "\n" +
                "package \"Software System 2\\n[Software System]\" <<SoftwareSystem2>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem2>> #cccccc\n" +
                "  skinparam PackageFontColor<<SoftwareSystem2>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<SoftwareSystem2.Container2>> as SoftwareSystem2.Container2\n" +
                "}\n" +
                "\n" +
                "SoftwareSystem1.Container1 .[#707070,thickness=2].> SoftwareSystem2.Container2 : \"<color:#707070>Uses\"\n" +
                "@enduml", diagram.getDefinition());

        containerView.setExternalSoftwareSystemBoundariesVisible(false);
        diagram = new StructurizrPlantUMLExporter().export(containerView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Software System 1\\n[Software System]\" <<SoftwareSystem1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1>> #444444\n" +
                "\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<SoftwareSystem1.Container1>> as SoftwareSystem1.Container1\n" +
                "}\n" +
                "\n" +
                "rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<SoftwareSystem2.Container2>> as SoftwareSystem2.Container2\n" +
                "\n" +
                "SoftwareSystem1.Container1 .[#707070,thickness=2].> SoftwareSystem2.Container2 : \"<color:#707070>Uses\"\n" +
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
        Diagram diagram = new StructurizrPlantUMLExporter().export(componentView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1.Component1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2.Component2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Container 1\\n[Container]\" <<SoftwareSystem1.Container1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1.Container1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1.Container1>> #444444\n" +
                "\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container1.Component1>> as SoftwareSystem1.Container1.Component1\n" +
                "}\n" +
                "\n" +
                "package \"Container 2\\n[Container]\" <<SoftwareSystem2.Container2>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem2.Container2>> #cccccc\n" +
                "  skinparam PackageFontColor<<SoftwareSystem2.Container2>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<SoftwareSystem2.Container2.Component2>> as SoftwareSystem2.Container2.Component2\n" +
                "}\n" +
                "\n" +
                "SoftwareSystem1.Container1.Component1 .[#707070,thickness=2].> SoftwareSystem2.Container2.Component2 : \"<color:#707070>Uses\"\n" +
                "@enduml", diagram.getDefinition());

        componentView.setExternalSoftwareSystemBoundariesVisible(false);
        diagram = new StructurizrPlantUMLExporter().export(componentView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1.Component1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2.Component2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Container 1\\n[Container]\" <<SoftwareSystem1.Container1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1.Container1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1.Container1>> #444444\n" +
                "\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container1.Component1>> as SoftwareSystem1.Container1.Component1\n" +
                "}\n" +
                "\n" +
                "rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<SoftwareSystem2.Container2.Component2>> as SoftwareSystem2.Container2.Component2\n" +
                "\n" +
                "SoftwareSystem1.Container1.Component1 .[#707070,thickness=2].> SoftwareSystem2.Container2.Component2 : \"<color:#707070>Uses\"\n" +
                "@enduml", diagram.getDefinition());
    }

    @Test
    public void test_renderDynamicDiagramWithExternalContainers() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem1 = workspace.getModel().addSoftwareSystem("Software System 1");
        Container container1 = softwareSystem1.addContainer("Container 1");
        SoftwareSystem softwareSystem2 = workspace.getModel().addSoftwareSystem("Software System 2");
        Container container2 = softwareSystem2.addContainer("Container 2");

        container1.uses(container2, "Uses");

        DynamicView dynamicView = workspace.getViews().createDynamicView(softwareSystem1, "Dynamic", "");
        dynamicView.add(container1, container2);

        dynamicView.setExternalBoundariesVisible(true);
        Diagram diagram = new StructurizrPlantUMLExporter().export(dynamicView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Dynamic\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Software System 1\\n[Software System]\" <<SoftwareSystem1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1>> #444444\n" +
                "\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<SoftwareSystem1.Container1>> as SoftwareSystem1.Container1\n" +
                "}\n" +
                "\n" +
                "package \"Software System 2\\n[Software System]\" <<SoftwareSystem2>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem2>> #cccccc\n" +
                "  skinparam PackageFontColor<<SoftwareSystem2>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<SoftwareSystem2.Container2>> as SoftwareSystem2.Container2\n" +
                "}\n" +
                "\n" +
                "SoftwareSystem1.Container1 .[#707070,thickness=2].> SoftwareSystem2.Container2 : \"<color:#707070>1. Uses\"\n" +
                "@enduml", diagram.getDefinition());

        dynamicView.setExternalBoundariesVisible(false);
        diagram = new StructurizrPlantUMLExporter().export(dynamicView);
        assertEquals("@startuml\n" +
                "title Software System 1 - Dynamic\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem2.Container2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Software System 1\\n[Software System]\" <<SoftwareSystem1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1>> #444444\n" +
                "\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<SoftwareSystem1.Container1>> as SoftwareSystem1.Container1\n" +
                "}\n" +
                "\n" +
                "rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<SoftwareSystem2.Container2>> as SoftwareSystem2.Container2\n" +
                "\n" +
                "SoftwareSystem1.Container1 .[#707070,thickness=2].> SoftwareSystem2.Container2 : \"<color:#707070>1. Uses\"\n" +
                "@enduml", diagram.getDefinition());
    }


    @Test
    public void test_renderDynamicDiagramWithExternalComponents() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem1 = workspace.getModel().addSoftwareSystem("Software System 1");
        Container container1 = softwareSystem1.addContainer("Container 1");
        Component component1 = container1.addComponent("Component 1");
        Container container2 = softwareSystem1.addContainer("Container 2");
        Component component2 = container2.addComponent("Component 2");

        component1.uses(component2, "Uses");

        DynamicView dynamicView = workspace.getViews().createDynamicView(container1, "Dynamic", "");
        dynamicView.add(component1, component2);

        dynamicView.setExternalBoundariesVisible(true);
        Diagram diagram = new StructurizrPlantUMLExporter().export(dynamicView);
        assertEquals("@startuml\n" +
                "title Container 1 - Dynamic\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1.Component1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem1.Container2.Component2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Container 1\\n[Container]\" <<SoftwareSystem1.Container1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1.Container1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1.Container1>> #444444\n" +
                "\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container1.Component1>> as SoftwareSystem1.Container1.Component1\n" +
                "}\n" +
                "\n" +
                "package \"Container 2\\n[Container]\" <<SoftwareSystem1.Container2>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1.Container2>> #cccccc\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1.Container2>> #cccccc\n" +
                "\n" +
                "  rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container2.Component2>> as SoftwareSystem1.Container2.Component2\n" +
                "}\n" +
                "\n" +
                "SoftwareSystem1.Container1.Component1 .[#707070,thickness=2].> SoftwareSystem1.Container2.Component2 : \"<color:#707070>1. Uses\"\n" +
                "@enduml", diagram.getDefinition());

        dynamicView.setExternalBoundariesVisible(false);
        diagram = new StructurizrPlantUMLExporter().export(dynamicView);
        assertEquals("@startuml\n" +
                "title Container 1 - Dynamic\n" +
                "\n" +
                "top to bottom direction\n" +
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
                "\n" +
                "hide stereotype\n" +
                "\n" +
                "skinparam rectangle<<SoftwareSystem1.Container1.Component1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam rectangle<<SoftwareSystem1.Container2.Component2>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "\n" +
                "package \"Container 1\\n[Container]\" <<SoftwareSystem1.Container1>> {\n" +
                "  skinparam PackageBorderColor<<SoftwareSystem1.Container1>> #444444\n" +
                "  skinparam PackageFontColor<<SoftwareSystem1.Container1>> #444444\n" +
                "\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container1.Component1>> as SoftwareSystem1.Container1.Component1\n" +
                "}\n" +
                "\n" +
                "rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<SoftwareSystem1.Container2.Component2>> as SoftwareSystem1.Container2.Component2\n" +
                "\n" +
                "SoftwareSystem1.Container1.Component1 .[#707070,thickness=2].> SoftwareSystem1.Container2.Component2 : \"<color:#707070>1. Uses\"\n" +
                "@enduml", diagram.getDefinition());
    }

}