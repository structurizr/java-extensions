package com.structurizr.io.dot;

import com.structurizr.Workspace;
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
import java.io.StringWriter;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class DOTWriterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        DOTWriter dotWriter = new DOTWriter();

        Collection<DOTDiagram> diagrams = dotWriter.toDOTDiagrams(workspace);
        assertEquals(7, diagrams.size());

        DOTDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">System Landscape for Big Bank plc</font><br /><font point-size=\"24\">The system landscape diagram for Big Bank plc.</font>>\n" +
                "  subgraph cluster_enterprise {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Big Bank plc</font><br /><font point-size=\"19\">[Enterprise]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "    12 [id=12,shape=rect, label=<<font point-size=\"32\">Customer Service<br />Staff</font><br /><font point-size=\"17\">[Person]</font><br /><br /><font point-size=\"22\">Customer service staff within<br />the bank.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "    2 [id=2,shape=rect, label=<<font point-size=\"34\">Internet Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Allows customers to view<br />information about their bank<br />accounts, and make payments.</font>>, style=filled, color=\"#0b4884\", fillcolor=\"#1168bd\", fontcolor=\"#ffffff\"]\n" +
                "    4 [id=4,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "    15 [id=15,shape=rect, label=<<font point-size=\"32\">Back Office Staff</font><br /><font point-size=\"17\">[Person]</font><br /><br /><font point-size=\"22\">Administration and support<br />staff within the bank.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "    6 [id=6,shape=rect, label=<<font point-size=\"34\">E-mail System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">The internal Microsoft<br />Exchange e-mail system.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "    9 [id=9,shape=rect, label=<<font point-size=\"34\">ATM</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Allows customers to withdraw<br />cash.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "\n" +
                "  1 [id=1,shape=rect, label=<<font point-size=\"32\">Personal Banking<br />Customer</font><br /><font point-size=\"17\">[Person]</font><br /><br /><font point-size=\"22\">A customer of the bank, with<br />personal bank accounts.</font>>, style=filled, color=\"#052e56\", fillcolor=\"#08427b\", fontcolor=\"#ffffff\"]\n" +
                "\n" +
                "  15 -> 4 [id=16, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 2 [id=3, label=<<font point-size=\"24\">Views account balances,<br />and makes payments using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 12 [id=14, label=<<font point-size=\"24\">Asks questions to</font><br /><font point-size=\"19\">[Telephone]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  2 -> 4 [id=5, label=<<font point-size=\"24\">Gets account information<br />from, and makes payments<br />using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  12 -> 4 [id=13, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 9 [id=11, label=<<font point-size=\"24\">Withdraws cash using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  2 -> 6 [id=7, label=<<font point-size=\"24\">Sends e-mail using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  6 -> 1 [id=8, label=<<font point-size=\"24\">Sends e-mails to</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  9 -> 4 [id=10, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Internet Banking System - System Context</font><br /><font point-size=\"24\">The system context diagram for the Internet Banking System.</font>>\n" +
                "  1 [id=1,shape=rect, label=<<font point-size=\"32\">Personal Banking<br />Customer</font><br /><font point-size=\"17\">[Person]</font><br /><br /><font point-size=\"22\">A customer of the bank, with<br />personal bank accounts.</font>>, style=filled, color=\"#052e56\", fillcolor=\"#08427b\", fontcolor=\"#ffffff\"]\n" +
                "  2 [id=2,shape=rect, label=<<font point-size=\"34\">Internet Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Allows customers to view<br />information about their bank<br />accounts, and make payments.</font>>, style=filled, color=\"#0b4884\", fillcolor=\"#1168bd\", fontcolor=\"#ffffff\"]\n" +
                "  4 [id=4,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  6 [id=6,shape=rect, label=<<font point-size=\"34\">E-mail System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">The internal Microsoft<br />Exchange e-mail system.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "\n" +
                "  1 -> 2 [id=3, label=<<font point-size=\"24\">Views account balances,<br />and makes payments using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  2 -> 4 [id=5, label=<<font point-size=\"24\">Gets account information<br />from, and makes payments<br />using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  2 -> 6 [id=7, label=<<font point-size=\"24\">Sends e-mail using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  6 -> 1 [id=8, label=<<font point-size=\"24\">Sends e-mails to</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Internet Banking System - Containers</font><br /><font point-size=\"24\">The container diagram for the Internet Banking System.</font>>\n" +
                "  1 [id=1,shape=rect, label=<<font point-size=\"32\">Personal Banking<br />Customer</font><br /><font point-size=\"17\">[Person]</font><br /><br /><font point-size=\"22\">A customer of the bank, with<br />personal bank accounts.</font>>, style=filled, color=\"#052e56\", fillcolor=\"#08427b\", fontcolor=\"#ffffff\"]\n" +
                "  4 [id=4,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  6 [id=6,shape=rect, label=<<font point-size=\"34\">E-mail System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">The internal Microsoft<br />Exchange e-mail system.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  subgraph cluster_2 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Internet Banking System</font><br /><font point-size=\"19\">[Software System]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#444444\"\n" +
                "    17 [id=17,shape=rect, label=<<font point-size=\"34\">Single-Page<br />Application</font><br /><font point-size=\"19\">[Container: JavaScript and Angular]</font><br /><br /><font point-size=\"24\">Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    18 [id=18,shape=rect, label=<<font point-size=\"34\">Mobile App</font><br /><font point-size=\"19\">[Container: Xamarin]</font><br /><br /><font point-size=\"24\">Provides a limited subset of<br />the Internet banking<br />functionality to customers via<br />their mobile device.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    19 [id=19,shape=rect, label=<<font point-size=\"34\">Web Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Delivers the static content<br />and the Internet banking<br />single page application.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    20 [id=20,shape=rect, label=<<font point-size=\"34\">API Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Provides Internet banking<br />functionality via a JSON/HTTPS<br />API.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    21 [id=21,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "\n" +
                "  20 -> 6 [id=28, label=<<font point-size=\"24\">Sends e-mail using</font><br /><font point-size=\"19\">[SMTP]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  20 -> 4 [id=27, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[XML/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  20 -> 21 [id=26, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  19 -> 17 [id=25, label=<<font point-size=\"24\">Delivers to the customer's<br />web browser</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  17 -> 20 [id=36, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  18 -> 20 [id=40, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 18 [id=24, label=<<font point-size=\"24\">Views account balances,<br />and makes payments using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 17 [id=23, label=<<font point-size=\"24\">Views account balances,<br />and makes payments using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  1 -> 19 [id=22, label=<<font point-size=\"24\">Visits bigbank.com/ib<br />using</font><br /><font point-size=\"19\">[HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  6 -> 1 [id=8, label=<<font point-size=\"24\">Sends e-mails to</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Internet Banking System - API Application - Components</font><br /><font point-size=\"24\">The component diagram for the API Application.</font>>\n" +
                "  4 [id=4,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  17 [id=17,shape=rect, label=<<font point-size=\"34\">Single-Page<br />Application</font><br /><font point-size=\"19\">[Container: JavaScript and Angular]</font><br /><br /><font point-size=\"24\">Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  6 [id=6,shape=rect, label=<<font point-size=\"34\">E-mail System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">The internal Microsoft<br />Exchange e-mail system.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  18 [id=18,shape=rect, label=<<font point-size=\"34\">Mobile App</font><br /><font point-size=\"19\">[Container: Xamarin]</font><br /><br /><font point-size=\"24\">Provides a limited subset of<br />the Internet banking<br />functionality to customers via<br />their mobile device.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  21 [id=21,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  subgraph cluster_20 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />API Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#444444\"\n" +
                "    33 [id=33,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System Facade</font><br /><font point-size=\"19\">[Component: Spring Bean]</font><br /><br /><font point-size=\"24\">A facade onto the mainframe<br />banking system.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    34 [id=34,shape=rect, label=<<font point-size=\"34\">E-mail Component</font><br /><font point-size=\"19\">[Component: Spring Bean]</font><br /><br /><font point-size=\"24\">Sends e-mails to users.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    29 [id=29,shape=rect, label=<<font point-size=\"34\">Sign In Controller</font><br /><font point-size=\"19\">[Component: Spring MVC Rest Controller]</font><br /><br /><font point-size=\"24\">Allows users to sign in to the<br />Internet Banking System.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    30 [id=30,shape=rect, label=<<font point-size=\"34\">Accounts Summary<br />Controller</font><br /><font point-size=\"19\">[Component: Spring MVC Rest Controller]</font><br /><br /><font point-size=\"24\">Provides customers with a<br />summary of their bank<br />accounts.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    31 [id=31,shape=rect, label=<<font point-size=\"34\">Reset Password<br />Controller</font><br /><font point-size=\"19\">[Component: Spring MVC Rest Controller]</font><br /><br /><font point-size=\"24\">Allows users to reset their<br />passwords with a single use<br />URL.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    32 [id=32,shape=rect, label=<<font point-size=\"34\">Security Component</font><br /><font point-size=\"19\">[Component: Spring Bean]</font><br /><br /><font point-size=\"24\">Provides functionality related<br />to signing in, changing<br />passwords, etc.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "\n" +
                "  18 -> 31 [id=41, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  18 -> 30 [id=42, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  29 -> 32 [id=43, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  17 -> 31 [id=37, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  17 -> 29 [id=35, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  30 -> 33 [id=44, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  31 -> 32 [id=45, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  31 -> 34 [id=46, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  32 -> 21 [id=47, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  33 -> 4 [id=48, label=<<font point-size=\"24\">Uses</font><br /><font point-size=\"19\">[XML/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  17 -> 30 [id=38, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  34 -> 6 [id=49, label=<<font point-size=\"24\">Sends e-mail using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  18 -> 29 [id=39, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">API Application - Dynamic</font><br /><font point-size=\"24\">Summarises how the sign in feature works in the single-page application.</font>>\n" +
                "  subgraph cluster_20 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">API Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "    29 [id=29,shape=rect, label=<<font point-size=\"34\">Sign In Controller</font><br /><font point-size=\"19\">[Component: Spring MVC Rest Controller]</font><br /><br /><font point-size=\"24\">Allows users to sign in to the<br />Internet Banking System.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "    32 [id=32,shape=rect, label=<<font point-size=\"34\">Security Component</font><br /><font point-size=\"19\">[Component: Spring Bean]</font><br /><br /><font point-size=\"24\">Provides functionality related<br />to signing in, changing<br />passwords, etc.</font>>, style=filled, color=\"#5d82a8\", fillcolor=\"#85bbf0\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "  17 [id=17,shape=rect, label=<<font point-size=\"34\">Single-Page<br />Application</font><br /><font point-size=\"19\">[Container: JavaScript and Angular]</font><br /><br /><font point-size=\"24\">Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  21 [id=21,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "\n" +
                "  17 -> 29 [id=35, label=<<font point-size=\"24\">1. Submits credentials to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  29 -> 32 [id=43, label=<<font point-size=\"24\">2. Validates credentials<br />using</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  32 -> 21 [id=47, label=<<font point-size=\"24\">3. select * from users<br />where username = ?</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  21 -> 32 [id=47, label=<<font point-size=\"24\">4. Returns user data to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  32 -> 29 [id=43, label=<<font point-size=\"24\">5. Returns true if the<br />hashed password matches</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  29 -> 17 [id=35, label=<<font point-size=\"24\">6. Sends back an<br />authentication token to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Internet Banking System - Deployment - Development</font><br /><font point-size=\"24\">An example development deployment scenario for the Internet Banking System.</font>>\n" +
                "subgraph cluster_55 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Big Bank plc</font><br /><font point-size=\"19\">[Deployment Node: Big Bank plc data center]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#888888\"\n" +
                "    fontcolor=\"#000000\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  subgraph cluster_56 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-dev001</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    57 [id=57,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "}\n" +
                "subgraph cluster_50 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Developer Laptop</font><br /><font point-size=\"19\">[Deployment Node: Microsoft Windows 10 or Apple macOS]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#888888\"\n" +
                "    fontcolor=\"#000000\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  subgraph cluster_59 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">Docker Container - Database Server</font><br /><font point-size=\"19\">[Deployment Node: Docker]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_60 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Database Server</font><br /><font point-size=\"19\">[Deployment Node: Oracle 12c]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      61 [id=61,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "  subgraph cluster_51 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">Docker Container - Web Server</font><br /><font point-size=\"19\">[Deployment Node: Docker]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_52 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Apache Tomcat</font><br /><font point-size=\"19\">[Deployment Node: Apache Tomcat 8.x]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      54 [id=54,shape=rect, label=<<font point-size=\"34\">API Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Provides Internet banking<br />functionality via a JSON/HTTPS<br />API.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "      53 [id=53,shape=rect, label=<<font point-size=\"34\">Web Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Delivers the static content<br />and the Internet banking<br />single page application.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "  subgraph cluster_63 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">Web Browser</font><br /><font point-size=\"19\">[Deployment Node: Chrome, Firefox, Safari, or Edge]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    64 [id=64,shape=rect, label=<<font point-size=\"34\">Single-Page<br />Application</font><br /><font point-size=\"19\">[Container: JavaScript and Angular]</font><br /><br /><font point-size=\"24\">Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "  54 -> 61 [id=62, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  64 -> 54 [id=65, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  53 -> 64 [id=66, label=<<font point-size=\"24\">Delivers to the customer's<br />web browser</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  54 -> 57 [id=58, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[XML/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Internet Banking System - Deployment - Live</font><br /><font point-size=\"24\">An example live deployment scenario for the Internet Banking System.</font>>\n" +
                "subgraph cluster_67 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Customer's mobile device</font><br /><font point-size=\"19\">[Deployment Node: Apple iOS or Android]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#888888\"\n" +
                "    fontcolor=\"#000000\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  68 [id=68,shape=rect, label=<<font point-size=\"34\">Mobile App</font><br /><font point-size=\"19\">[Container: Xamarin]</font><br /><br /><font point-size=\"24\">Provides a limited subset of<br />the Internet banking<br />functionality to customers via<br />their mobile device.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "}\n" +
                "subgraph cluster_69 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Customer's computer</font><br /><font point-size=\"19\">[Deployment Node: Microsoft Windows or Apple macOS]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#888888\"\n" +
                "    fontcolor=\"#000000\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  subgraph cluster_70 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">Web Browser</font><br /><font point-size=\"19\">[Deployment Node: Chrome, Firefox, Safari, or Edge]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    71 [id=71,shape=rect, label=<<font point-size=\"34\">Single-Page<br />Application</font><br /><font point-size=\"19\">[Container: JavaScript and Angular]</font><br /><br /><font point-size=\"24\">Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "}\n" +
                "subgraph cluster_72 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Big Bank plc</font><br /><font point-size=\"19\">[Deployment Node: Big Bank plc data center]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#888888\"\n" +
                "    fontcolor=\"#000000\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  subgraph cluster_79 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-api***</font><br /><font point-size=\"19\">[Deployment Node: Ubuntu 16.04 LTS]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_80 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Apache Tomcat</font><br /><font point-size=\"19\">[Deployment Node: Apache Tomcat 8.x]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      81 [id=81,shape=rect, label=<<font point-size=\"34\">API Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Provides Internet banking<br />functionality via a JSON/HTTPS<br />API.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "  subgraph cluster_85 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-db01</font><br /><font point-size=\"19\">[Deployment Node: Ubuntu 16.04 LTS]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_86 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Oracle - Primary</font><br /><font point-size=\"19\">[Deployment Node: Oracle 12c]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      87 [id=87,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "  subgraph cluster_89 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-db02</font><br /><font point-size=\"19\">[Deployment Node: Ubuntu 16.04 LTS]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_90 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Oracle - Secondary</font><br /><font point-size=\"19\">[Deployment Node: Oracle 12c]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      91 [id=91,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Oracle Database Schema]</font><br /><br /><font point-size=\"24\">Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "  subgraph cluster_73 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-prod001</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    74 [id=74,shape=rect, label=<<font point-size=\"34\">Mainframe Banking<br />System</font><br /><font point-size=\"19\">[Software System]</font><br /><br /><font point-size=\"24\">Stores all of the core banking<br />information about customers,<br />accounts, transactions, etc.</font>>, style=filled, color=\"#6b6b6b\", fillcolor=\"#999999\", fontcolor=\"#ffffff\"]\n" +
                "  }\n" +
                "  subgraph cluster_75 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">bigbank-web***</font><br /><font point-size=\"19\">[Deployment Node: Ubuntu 16.04 LTS]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#888888\"\n" +
                "      fontcolor=\"#000000\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_76 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Apache Tomcat</font><br /><font point-size=\"19\">[Deployment Node: Apache Tomcat 8.x]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#888888\"\n" +
                "        fontcolor=\"#000000\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      77 [id=77,shape=rect, label=<<font point-size=\"34\">Web Application</font><br /><font point-size=\"19\">[Container: Java and Spring MVC]</font><br /><br /><font point-size=\"24\">Delivers the static content<br />and the Internet banking<br />single page application.</font>>, style=filled, color=\"#2e6295\", fillcolor=\"#438dd5\", fontcolor=\"#ffffff\"]\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "  87 -> 91 [id=93, label=<<font point-size=\"24\">Replicates data to</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\",ltail=cluster_86,lhead=cluster_90]\n" +
                "  68 -> 81 [id=82, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  71 -> 81 [id=83, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[JSON/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  81 -> 91 [id=92, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  81 -> 74 [id=84, label=<<font point-size=\"24\">Makes API calls to</font><br /><font point-size=\"19\">[XML/HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  77 -> 71 [id=78, label=<<font point-size=\"24\">Delivers to the customer's<br />web browser</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  81 -> 87 [id=88, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        Collection<DOTDiagram> diagrams = new DOTWriter().toDOTDiagrams(workspace);
        assertEquals(1, diagrams.size());

        DOTDiagram diagram = diagrams.stream().findFirst().get();
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=LR, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Spring PetClinic - Deployment - Default</font><br /><font point-size=\"24\">An example deployment diagram.</font>>\n" +
                "subgraph cluster_5 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\">Amazon Web Services</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#232f3e\"\n" +
                "    fontcolor=\"#232f3e\"\n" +
                "    fillcolor=\"#ffffff\"\n" +
                "  subgraph cluster_6 {\n" +
                "      margin=25\n" +
                "      label=<<font point-size=\"24\">US-East-1</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "      labelloc=b\n" +
                "      color=\"#147eba\"\n" +
                "      fontcolor=\"#147eba\"\n" +
                "      fillcolor=\"#ffffff\"\n" +
                "    subgraph cluster_14 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Amazon RDS</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#3b48cc\"\n" +
                "        fontcolor=\"#3b48cc\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      subgraph cluster_15 {\n" +
                "          margin=25\n" +
                "          label=<<font point-size=\"24\">MySQL</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "          labelloc=b\n" +
                "          color=\"#3b48cc\"\n" +
                "          fontcolor=\"#3b48cc\"\n" +
                "          fillcolor=\"#ffffff\"\n" +
                "        16 [id=16,shape=cylinder, label=<<font point-size=\"34\">Database</font><br /><font point-size=\"19\">[Container: Relational database schema]</font><br /><br /><font point-size=\"24\">Stores information regarding<br />the veterinarians, the<br />clients, and their pets.</font>>, style=filled, color=\"#b2b2b2\", fillcolor=\"#ffffff\", fontcolor=\"#000000\"]\n" +
                "      }\n" +
                "    }\n" +
                "    subgraph cluster_7 {\n" +
                "        margin=25\n" +
                "        label=<<font point-size=\"24\">Autoscaling group</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "        labelloc=b\n" +
                "        color=\"#cc2264\"\n" +
                "        fontcolor=\"#cc2264\"\n" +
                "        fillcolor=\"#ffffff\"\n" +
                "      subgraph cluster_8 {\n" +
                "          margin=25\n" +
                "          label=<<font point-size=\"24\">Amazon EC2</font><br /><font point-size=\"19\">[Deployment Node]</font>>\n" +
                "          labelloc=b\n" +
                "          color=\"#d86613\"\n" +
                "          fontcolor=\"#d86613\"\n" +
                "          fillcolor=\"#ffffff\"\n" +
                "        9 [id=9,shape=rect, label=<<font point-size=\"34\">Web Application</font><br /><font point-size=\"19\">[Container: Java and Spring Boot]</font><br /><br /><font point-size=\"24\">Allows employees to view and<br />manage information regarding<br />the veterinarians, the<br />clients, and their pets.</font>>, style=filled, color=\"#b2b2b2\", fillcolor=\"#ffffff\", fontcolor=\"#000000\"]\n" +
                "      }\n" +
                "    }\n" +
                "    11 [id=11,shape=rect, label=<<font point-size=\"34\">Elastic Load Balancer</font><br /><font point-size=\"19\">[Infrastructure Node]</font>>, style=filled, color=\"#693cc5\", fillcolor=\"#ffffff\", fontcolor=\"#693cc5\"]\n" +
                "    10 [id=10,shape=rect, label=<<font point-size=\"34\">Route 53</font><br /><font point-size=\"19\">[Infrastructure Node]</font>>, style=filled, color=\"#693cc5\", fillcolor=\"#ffffff\", fontcolor=\"#693cc5\"]\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "  9 -> 16 [id=17, label=<<font point-size=\"24\">Reads from and writes to</font><br /><font point-size=\"19\">[JDBC/SSL]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  11 -> 9 [id=13, label=<<font point-size=\"24\">Forwards requests to</font><br /><font point-size=\"19\">[HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "  10 -> 11 [id=12, label=<<font point-size=\"24\">Forwards requests to</font><br /><font point-size=\"19\">[HTTPS]</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", diagram.getDefinition());
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

        StringWriter stringWriter = new StringWriter();
        new DOTWriter().write(containerView, stringWriter);
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Software System 1 - Containers</font>>\n" +
                "  subgraph cluster_1 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Software System 1</font><br /><font point-size=\"19\">[Software System]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#444444\"\n" +
                "    2 [id=2,shape=rect, label=<<font point-size=\"34\">Container 1</font><br /><font point-size=\"19\">[Container]</font>>, style=filled, color=\"#9a9a9a\", fillcolor=\"#dddddd\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "  subgraph cluster_3 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Software System 2</font><br /><font point-size=\"19\">[Software System]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#cccccc\"\n" +
                "    fontcolor=\"#cccccc\"\n" +
                "    fillcolor=\"#cccccc\"\n" +
                "    4 [id=4,shape=rect, label=<<font point-size=\"34\">Container 2</font><br /><font point-size=\"19\">[Container]</font>>, style=filled, color=\"#9a9a9a\", fillcolor=\"#dddddd\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "\n" +
                "  2 -> 4 [id=5, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", stringWriter.toString());
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

        StringWriter stringWriter = new StringWriter();
        new DOTWriter().write(componentView, stringWriter);
        assertEquals("digraph {\n" +
                "  compound=true\n" +
                "  graph [fontname=\"Arial\", rankdir=TB, ranksep=1.0, nodesep=1.0]\n" +
                "  node [fontname=\"Arial\", shape=box, margin=\"0.4,0.3\"]\n" +
                "  edge [fontname=\"Arial\"]\n" +
                "  label=<<br /><font point-size=\"34\">Software System 1 - Container 1 - Components</font>>\n" +
                "  subgraph cluster_2 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Container 1</font><br /><font point-size=\"19\">[Container]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#444444\"\n" +
                "    fontcolor=\"#444444\"\n" +
                "    fillcolor=\"#444444\"\n" +
                "    3 [id=3,shape=rect, label=<<font point-size=\"34\">Component 1</font><br /><font point-size=\"19\">[Component]</font>>, style=filled, color=\"#9a9a9a\", fillcolor=\"#dddddd\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "  subgraph cluster_5 {\n" +
                "    margin=25\n" +
                "    label=<<font point-size=\"24\"><br />Container 2</font><br /><font point-size=\"19\">[Container]</font>>\n" +
                "    labelloc=b\n" +
                "    color=\"#cccccc\"\n" +
                "    fontcolor=\"#cccccc\"\n" +
                "    fillcolor=\"#cccccc\"\n" +
                "    6 [id=6,shape=rect, label=<<font point-size=\"34\">Component 2</font><br /><font point-size=\"19\">[Component]</font>>, style=filled, color=\"#9a9a9a\", fillcolor=\"#dddddd\", fontcolor=\"#000000\"]\n" +
                "  }\n" +
                "\n" +
                "  3 -> 6 [id=7, label=<<font point-size=\"24\">Uses</font>>, style=\"dashed\", color=\"#707070\", fontcolor=\"#707070\"]\n" +
                "}", stringWriter.toString());
    }

}