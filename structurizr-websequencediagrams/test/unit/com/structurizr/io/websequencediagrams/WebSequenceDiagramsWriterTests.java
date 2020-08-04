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

        webSequenceDiagramsWriter.write(workspace, stringWriter);
        assertEquals("title Dynamic - Some Key" + System.lineSeparator() +
                System.lineSeparator() +
                "actor <<Person>>>\\nUser as User" + System.lineSeparator() +
                "participant <<Software System>>>\\nSystem A as System A" + System.lineSeparator() +
                "participant <<Software System>>>\\nSystem B as System B" + System.lineSeparator() +
                System.lineSeparator() +
                "User->System A: Does something using" + System.lineSeparator() +
                "System A->>System B: Does something then using" + System.lineSeparator() +
                "System B-->>System A: Responds with X" + System.lineSeparator(), stringWriter.toString());
    }

}
