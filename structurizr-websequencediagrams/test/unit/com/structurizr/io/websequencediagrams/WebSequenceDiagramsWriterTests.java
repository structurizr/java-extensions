package com.structurizr.io.websequencediagrams;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.DynamicView;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class WebSequenceDiagramsWriterTests {

    private WebSequenceDiagramsWriter webSequenceDiagramsWriter;
    private Workspace workspace;
    private StringWriter stringWriter;

    @Before
    public void setUp() {
        webSequenceDiagramsWriter = new WebSequenceDiagramsWriter();
        workspace = new Workspace("Name", "Description");
        stringWriter = new StringWriter();
    }

    @Test
    public void test_write_DoesNotThrowAnExceptionWhenPassedNullParameters() throws Exception {
        webSequenceDiagramsWriter.write((Workspace)null, null);
        webSequenceDiagramsWriter.write(workspace, null);
        webSequenceDiagramsWriter.write((Workspace)null, stringWriter);
    }

    @Test
    public void test_write_createsAWebSequenceDiagram() throws Exception {
        Model model = workspace.getModel();
        Person user = model.addPerson("User", "");
        SoftwareSystem a = model.addSoftwareSystem("System A", "");
        SoftwareSystem b = model.addSoftwareSystem("System B", "");

        user.uses(a, "");
        a.uses(b, "", "", InteractionStyle.Asynchronous);
        DynamicView view = workspace.getViews().createDynamicView("Some Key", "A description of the diagram");
        view.add(user, "Does something using", a);
        view.add(a, "Does something then using", b);
        view.add(b, "Responds with X", a);
        view.add(a, "Sends X back", user);

        webSequenceDiagramsWriter.write(workspace, stringWriter);
        assertEquals("title Dynamic - Some Key\n" +
                "\n" +
                "actor <<Person>>>\\nUser as User\n" +
                "participant <<Software System>>>\\nSystem A as System A\n" +
                "participant <<Software System>>>\\nSystem B as System B\n" +
                "\n" +
                "User->System A: Does something using\n" +
                "System A->>System B: Does something then using\n" +
                "System B-->>System A: Responds with X\n" +
                "System A-->User: Sends X back\n".replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

}
