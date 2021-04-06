package com.structurizr.io.mermaid;

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

public class MermaidDiagramExporterTests extends AbstractExporterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        MermaidDiagramExporter exporter = new MermaidDiagramExporter();

        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(7, diagrams.size());

        Diagram diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-SystemLandscape.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SystemContext")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-SystemContext.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-Containers.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-Components.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(d -> d.getKey().equals("SignIn")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-SignIn.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-DevelopmentDeployment.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/36141-LiveDeployment.mmd"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        MermaidDiagramExporter exporter = new MermaidDiagramExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(1, diagrams.size());

        Diagram diagram = diagrams.stream().findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/54915-AmazonWebServicesDeployment.mmd"));
        assertEquals(expected, diagram.getDefinition());
    }

    @Test
    public void test_GroupsExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/groups.json"));
        ThemeUtils.loadThemes(workspace);

        MermaidDiagramExporter exporter = new MermaidDiagramExporter();
        Collection<Diagram> diagrams = exporter.export(workspace);
        assertEquals(3, diagrams.size());

        Diagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        String expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/groups-SystemLandscape.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/groups-Containers.mmd"));
        assertEquals(expected, diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        expected = readFile(new File("./test/unit/com/structurizr/io/mermaid/groups-Components.mmd"));
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

        Diagram diagram = new MermaidDiagramExporter().export(containerView);
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 1 [Software System 1]\n" +
                "    style 1 fill:#ffffff,stroke:#444444,color:#444444\n" +
                "\n" +
                "    2[\"<div style='font-weight: bold'>Container 1</div><div style='font-size: 70%; margin-top: 0px'>[Container]</div>\"]\n" +
                "    style 2 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "  end\n" +
                "\n" +
                "  subgraph 3 [Software System 2]\n" +
                "    style 3 fill:#ffffff,stroke:#cccccc,color:#cccccc\n" +
                "\n" +
                "    4[\"<div style='font-weight: bold'>Container 2</div><div style='font-size: 70%; margin-top: 0px'>[Container]</div>\"]\n" +
                "    style 4 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "  end\n" +
                "\n" +
                "  2-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->4", diagram.getDefinition());
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

        Diagram diagram = new MermaidDiagramExporter().export(componentView);
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 2 [Container 1]\n" +
                "    style 2 fill:#ffffff,stroke:#444444,color:#444444\n" +
                "\n" +
                "    3[\"<div style='font-weight: bold'>Component 1</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>\"]\n" +
                "    style 3 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "  end\n" +
                "\n" +
                "  subgraph 5 [Container 2]\n" +
                "    style 5 fill:#ffffff,stroke:#cccccc,color:#cccccc\n" +
                "\n" +
                "    6[\"<div style='font-weight: bold'>Component 2</div><div style='font-size: 70%; margin-top: 0px'>[Component]</div>\"]\n" +
                "    style 6 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "  end\n" +
                "\n" +
                "  3-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->6", diagram.getDefinition());
    }

}