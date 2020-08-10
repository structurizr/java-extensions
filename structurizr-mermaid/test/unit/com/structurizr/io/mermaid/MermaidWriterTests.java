package com.structurizr.io.mermaid;

import com.structurizr.Workspace;
import com.structurizr.model.Container;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.util.ThemeUtils;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.AutomaticLayout;
import com.structurizr.view.DeploymentView;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class MermaidWriterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        MermaidWriter mermaidWriter = new MermaidWriter();

        Collection<MermaidDiagram> diagrams = mermaidWriter.toMermaidDiagrams(workspace);
        assertEquals(7, diagrams.size());

        MermaidDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "  1[\"<div style='font-weight: bold'>Personal Banking Customer</div><div style='font-size: 70%; margin-top: 0px'>[Person]</div><div style='font-size: 80%; margin-top:10px'>A customer of the bank, with<br />personal bank accounts.</div>\"]\n" +
                "  style 1 fill:#08427b,stroke:#052e56,color:#ffffff\n" +
                "  subgraph boundary [Big Bank plc]\n" +
                "    15[\"<div style='font-weight: bold'>Back Office Staff</div><div style='font-size: 70%; margin-top: 0px'>[Person]</div><div style='font-size: 80%; margin-top:10px'>Administration and support<br />staff within the bank.</div>\"]\n" +
                "    style 15 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    12[\"<div style='font-weight: bold'>Customer Service Staff</div><div style='font-size: 70%; margin-top: 0px'>[Person]</div><div style='font-size: 80%; margin-top:10px'>Customer service staff within<br />the bank.</div>\"]\n" +
                "    style 12 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    9[\"<div style='font-weight: bold'>ATM</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Allows customers to withdraw<br />cash.</div>\"]\n" +
                "    style 9 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    6[\"<div style='font-weight: bold'>E-mail System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>The internal Microsoft<br />Exchange e-mail system.</div>\"]\n" +
                "    style 6 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    2[\"<div style='font-weight: bold'>Internet Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Allows customers to view<br />information about their bank<br />accounts, and make payments.</div>\"]\n" +
                "    style 2 fill:#1168bd,stroke:#0b4884,color:#ffffff\n" +
                "    4[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "    style 4 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  end\n" +
                "  style boundary fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  9-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->4\n" +
                "  15-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->4\n" +
                "  12-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->4\n" +
                "  6-. \"<div>Sends e-mails to</div><div style='font-size: 70%'></div>\" .->1\n" +
                "  2-. \"<div>Sends e-mail using</div><div style='font-size: 70%'></div>\" .->6\n" +
                "  2-. \"<div>Gets account information<br />from, and makes payments<br />using</div><div style='font-size: 70%'></div>\" .->4\n" +
                "  1-. \"<div>Withdraws cash using</div><div style='font-size: 70%'></div>\" .->9\n" +
                "  1-. \"<div>Asks questions to</div><div style='font-size: 70%'>[Telephone]</div>\" .->12\n" +
                "  1-. \"<div>Views account balances, and<br />makes payments using</div><div style='font-size: 70%'></div>\" .->2\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "  1[\"<div style='font-weight: bold'>Personal Banking Customer</div><div style='font-size: 70%; margin-top: 0px'>[Person]</div><div style='font-size: 80%; margin-top:10px'>A customer of the bank, with<br />personal bank accounts.</div>\"]\n" +
                "  style 1 fill:#08427b,stroke:#052e56,color:#ffffff\n" +
                "  6[\"<div style='font-weight: bold'>E-mail System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>The internal Microsoft<br />Exchange e-mail system.</div>\"]\n" +
                "  style 6 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  2[\"<div style='font-weight: bold'>Internet Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Allows customers to view<br />information about their bank<br />accounts, and make payments.</div>\"]\n" +
                "  style 2 fill:#1168bd,stroke:#0b4884,color:#ffffff\n" +
                "  4[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "  style 4 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  6-. \"<div>Sends e-mails to</div><div style='font-size: 70%'></div>\" .->1\n" +
                "  2-. \"<div>Sends e-mail using</div><div style='font-size: 70%'></div>\" .->6\n" +
                "  2-. \"<div>Gets account information<br />from, and makes payments<br />using</div><div style='font-size: 70%'></div>\" .->4\n" +
                "  1-. \"<div>Views account balances, and<br />makes payments using</div><div style='font-size: 70%'></div>\" .->2\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "  6[\"<div style='font-weight: bold'>E-mail System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>The internal Microsoft<br />Exchange e-mail system.</div>\"]\n" +
                "  style 6 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  4[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "  style 4 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  1[\"<div style='font-weight: bold'>Personal Banking Customer</div><div style='font-size: 70%; margin-top: 0px'>[Person]</div><div style='font-size: 80%; margin-top:10px'>A customer of the bank, with<br />personal bank accounts.</div>\"]\n" +
                "  style 1 fill:#08427b,stroke:#052e56,color:#ffffff\n" +
                "  subgraph boundary [Internet Banking System]\n" +
                "    20[\"<div style='font-weight: bold'>API Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Provides Internet banking<br />functionality via a<br />JSON/HTTPS API.</div>\"]\n" +
                "    style 20 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    21[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "    style 21 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    18[\"<div style='font-weight: bold'>Mobile App</div><div style='font-size: 70%; margin-top: 0px'>[Container: Xamarin]</div><div style='font-size: 80%; margin-top:10px'>Provides a limited subset of<br />the Internet banking<br />functionality to customers<br />via their mobile device.</div>\"]\n" +
                "    style 18 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    17[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "    style 17 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    19[\"<div style='font-weight: bold'>Web Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Delivers the static content<br />and the Internet banking<br />single page application.</div>\"]\n" +
                "    style 19 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  end\n" +
                "  style boundary fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  20-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->21\n" +
                "  20-. \"<div>Sends e-mail using</div><div style='font-size: 70%'>[SMTP]</div>\" .->6\n" +
                "  20-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[XML/HTTPS]</div>\" .->4\n" +
                "  6-. \"<div>Sends e-mails to</div><div style='font-size: 70%'></div>\" .->1\n" +
                "  18-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->20\n" +
                "  1-. \"<div>Views account balances, and<br />makes payments using</div><div style='font-size: 70%'></div>\" .->18\n" +
                "  1-. \"<div>Views account balances, and<br />makes payments using</div><div style='font-size: 70%'></div>\" .->17\n" +
                "  1-. \"<div>Visits bigbank.com/ib using</div><div style='font-size: 70%'>[HTTPS]</div>\" .->19\n" +
                "  17-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->20\n" +
                "  19-. \"<div>Delivers to the customer's<br />web browser</div><div style='font-size: 70%'></div>\" .->17\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "  21[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "  style 21 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  6[\"<div style='font-weight: bold'>E-mail System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>The internal Microsoft<br />Exchange e-mail system.</div>\"]\n" +
                "  style 6 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  4[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "  style 4 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "  18[\"<div style='font-weight: bold'>Mobile App</div><div style='font-size: 70%; margin-top: 0px'>[Container: Xamarin]</div><div style='font-size: 80%; margin-top:10px'>Provides a limited subset of<br />the Internet banking<br />functionality to customers<br />via their mobile device.</div>\"]\n" +
                "  style 18 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  17[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "  style 17 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  subgraph boundary [API Application]\n" +
                "    30[\"<div style='font-weight: bold'>Accounts Summary Controller</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring MVC Rest Controller]</div><div style='font-size: 80%; margin-top:10px'>Provides customers with a<br />summary of their bank<br />accounts.</div>\"]\n" +
                "    style 30 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "    34[\"<div style='font-weight: bold'>E-mail Component</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring Bean]</div><div style='font-size: 80%; margin-top:10px'>Sends e-mails to users.</div>\"]\n" +
                "    style 34 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "    33[\"<div style='font-weight: bold'>Mainframe Banking System Facade</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring Bean]</div><div style='font-size: 80%; margin-top:10px'>A facade onto the mainframe<br />banking system.</div>\"]\n" +
                "    style 33 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "    31[\"<div style='font-weight: bold'>Reset Password Controller</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring MVC Rest Controller]</div><div style='font-size: 80%; margin-top:10px'>Allows users to reset their<br />passwords with a single use<br />URL.</div>\"]\n" +
                "    style 31 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "    32[\"<div style='font-weight: bold'>Security Component</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring Bean]</div><div style='font-size: 80%; margin-top:10px'>Provides functionality<br />related to signing in,<br />changing passwords, etc.</div>\"]\n" +
                "    style 32 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "    29[\"<div style='font-weight: bold'>Sign In Controller</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring MVC Rest Controller]</div><div style='font-size: 80%; margin-top:10px'>Allows users to sign in to<br />the Internet Banking System.</div>\"]\n" +
                "    style 29 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "  end\n" +
                "  style boundary fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  30-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->33\n" +
                "  34-. \"<div>Sends e-mail using</div><div style='font-size: 70%'></div>\" .->6\n" +
                "  33-. \"<div>Uses</div><div style='font-size: 70%'>[XML/HTTPS]</div>\" .->4\n" +
                "  18-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->30\n" +
                "  18-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->31\n" +
                "  18-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->29\n" +
                "  31-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->34\n" +
                "  31-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->32\n" +
                "  32-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->21\n" +
                "  29-. \"<div>Uses</div><div style='font-size: 70%'></div>\" .->32\n" +
                "  17-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->30\n" +
                "  17-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->31\n" +
                "  17-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->29\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("graph LR\n" +
                "  linkStyle default fill:#ffffff\n" +
                "  21[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "  style 21 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  32[\"<div style='font-weight: bold'>Security Component</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring Bean]</div><div style='font-size: 80%; margin-top:10px'>Provides functionality<br />related to signing in,<br />changing passwords, etc.</div>\"]\n" +
                "  style 32 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "  29[\"<div style='font-weight: bold'>Sign In Controller</div><div style='font-size: 70%; margin-top: 0px'>[Component: Spring MVC Rest Controller]</div><div style='font-size: 80%; margin-top:10px'>Allows users to sign in to<br />the Internet Banking System.</div>\"]\n" +
                "  style 29 fill:#85bbf0,stroke:#5d82a8,color:#000000\n" +
                "  17[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "  style 17 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  17-->|\"<div style='font-weight: bold'>1. Submits credentials to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\"|29\n" +
                "  29-->|\"<div style='font-weight: bold'>2. Validates credentials using</div><div style='font-size: 70%'></div>\"|32\n" +
                "  32-->|\"<div style='font-weight: bold'>3. select * from users where<br />username = ?</div><div style='font-size: 70%'>[JDBC]</div>\"|21\n" +
                "  21-->|\"<div style='font-weight: bold'>4. Returns user data to</div><div style='font-size: 70%'>[JDBC]</div>\"|32\n" +
                "  32-->|\"<div style='font-weight: bold'>5. Returns true if the hashed<br />password matches</div><div style='font-size: 70%'></div>\"|29\n" +
                "  29-->|\"<div style='font-weight: bold'>6. Sends back an authentication<br />token to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\"|17\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 55 [Big Bank plc]\n" +
                "    subgraph 56 [bigbank-dev001]\n" +
                "        57[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "        style 57 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    end\n" +
                "    style 56 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 55 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  subgraph 50 [Developer Laptop]\n" +
                "    subgraph 59 [Docker Container - Database Server]\n" +
                "      subgraph 60 [Database Server]\n" +
                "          61[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 61 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 60 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 59 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 51 [Docker Container - Web Server]\n" +
                "      subgraph 52 [Apache Tomcat]\n" +
                "          54[\"<div style='font-weight: bold'>API Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Provides Internet banking<br />functionality via a<br />JSON/HTTPS API.</div>\"]\n" +
                "          style 54 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "          53[\"<div style='font-weight: bold'>Web Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Delivers the static content<br />and the Internet banking<br />single page application.</div>\"]\n" +
                "          style 53 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 52 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 51 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 63 [Web Browser]\n" +
                "        64[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "        style 64 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    end\n" +
                "    style 63 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 50 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  54-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->61\n" +
                "  54-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[XML/HTTPS]</div>\" .->57\n" +
                "  64-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->54\n" +
                "  53-. \"<div>Delivers to the customer's<br />web browser</div><div style='font-size: 70%'></div>\" .->64\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 72 [Big Bank plc]\n" +
                "    subgraph 79 [bigbank-api***]\n" +
                "      subgraph 80 [Apache Tomcat]\n" +
                "          81[\"<div style='font-weight: bold'>API Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Provides Internet banking<br />functionality via a<br />JSON/HTTPS API.</div>\"]\n" +
                "          style 81 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 80 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 79 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 85 [bigbank-db01]\n" +
                "      subgraph 86 [Oracle - Primary]\n" +
                "          87[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 87 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 86 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 85 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 89 [bigbank-db02]\n" +
                "      subgraph 90 [Oracle - Secondary]\n" +
                "          91[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 91 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 90 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 89 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 73 [bigbank-prod001]\n" +
                "        74[\"<div style='font-weight: bold'>Mainframe Banking System</div><div style='font-size: 70%; margin-top: 0px'>[Software System]</div><div style='font-size: 80%; margin-top:10px'>Stores all of the core<br />banking information about<br />customers, accounts,<br />transactions, etc.</div>\"]\n" +
                "        style 74 fill:#999999,stroke:#6b6b6b,color:#ffffff\n" +
                "    end\n" +
                "    style 73 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 75 [bigbank-web***]\n" +
                "      subgraph 76 [Apache Tomcat]\n" +
                "          77[\"<div style='font-weight: bold'>Web Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Delivers the static content<br />and the Internet banking<br />single page application.</div>\"]\n" +
                "          style 77 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 76 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 75 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 72 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  subgraph 69 [Customer's computer]\n" +
                "    subgraph 70 [Web Browser]\n" +
                "        71[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "        style 71 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    end\n" +
                "    style 70 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 69 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  subgraph 67 [Customer's mobile device]\n" +
                "      68[\"<div style='font-weight: bold'>Mobile App</div><div style='font-size: 70%; margin-top: 0px'>[Container: Xamarin]</div><div style='font-size: 80%; margin-top:10px'>Provides a limited subset of<br />the Internet banking<br />functionality to customers<br />via their mobile device.</div>\"]\n" +
                "      style 68 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  end\n" +
                "  style 67 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  81-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->91\n" +
                "  81-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->87\n" +
                "  81-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[XML/HTTPS]</div>\" .->74\n" +
                "  68-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->81\n" +
                "  71-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->81\n" +
                "  77-. \"<div>Delivers to the customer's<br />web browser</div><div style='font-size: 70%'></div>\" .->71\n", diagram.getDefinition());

        mermaidWriter.setUseSequenceDiagrams(true);
        diagrams = mermaidWriter.toMermaidDiagrams(workspace);

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("sequenceDiagram\n" +
                "  participant 17 as Single-Page Application\n" +
                "  participant 29 as Sign In Controller\n" +
                "  participant 32 as Security Component\n" +
                "  participant 21 as Database\n" +
                "  17->>29: Submits credentials to\n" +
                "  29->>32: Validates credentials using\n" +
                "  32->>21: select * from users where<br />username = ?\n" +
                "  21-->>32: Returns user data to\n" +
                "  32-->>29: Returns true if the hashed<br />password matches\n" +
                "  29-->>17: Sends back an authentication<br />token to\n", diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadStylesFromThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        Collection<MermaidDiagram> diagrams = new MermaidWriter().toMermaidDiagrams(workspace);
        assertEquals(1, diagrams.size());

        MermaidDiagram diagram = diagrams.stream().findFirst().get();
        assertEquals("graph LR\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 5 [Amazon Web Services]\n" +
                "    subgraph 6 [US-East-1]\n" +
                "      subgraph 14 [Amazon RDS]\n" +
                "        subgraph 15 [MySQL]\n" +
                "            16[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Relational database schema]</div><div style='font-size: 80%; margin-top:10px'>Stores information regarding<br />the veterinarians, the<br />clients, and their pets.</div>\")]\n" +
                "            style 16 fill:#ffffff,stroke:#b2b2b2,color:#000000\n" +
                "        end\n" +
                "        style 15 fill:#ffffff,stroke:#3b48cc,color:#3b48cc\n" +
                "      end\n" +
                "      style 14 fill:#ffffff,stroke:#3b48cc,color:#3b48cc\n" +
                "      subgraph 7 [Autoscaling group]\n" +
                "        subgraph 8 [Amazon EC2]\n" +
                "            9(\"<div style='font-weight: bold'>Web Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring Boot]</div><div style='font-size: 80%; margin-top:10px'>Allows employees to view and<br />manage information regarding<br />the veterinarians, the<br />clients, and their pets.</div>\")\n" +
                "            style 9 fill:#ffffff,stroke:#b2b2b2,color:#000000\n" +
                "        end\n" +
                "        style 8 fill:#ffffff,stroke:#d86613,color:#d86613\n" +
                "      end\n" +
                "      style 7 fill:#ffffff,stroke:#cc2264,color:#cc2264\n" +
                "        11(\"<div style='font-weight: bold'>Elastic Load Balancer</div><div style='font-size: 70%; margin-top: 0px'>[Infrastructure Node]</div><div style='font-size: 80%; margin-top:10px'></div>\")\n" +
                "        style 11 fill:#ffffff,stroke:#693cc5,color:#693cc5\n" +
                "        10(\"<div style='font-weight: bold'>Route 53</div><div style='font-size: 70%; margin-top: 0px'>[Infrastructure Node]</div><div style='font-size: 80%; margin-top:10px'></div>\")\n" +
                "        style 10 fill:#ffffff,stroke:#693cc5,color:#693cc5\n" +
                "    end\n" +
                "    style 6 fill:#ffffff,stroke:#147eba,color:#147eba\n" +
                "  end\n" +
                "  style 5 fill:#ffffff,stroke:#232f3e,color:#232f3e\n" +
                "  11-. \"<div>Forwards requests to</div><div style='font-size: 70%'>[HTTPS]</div>\" .->9\n" +
                "  10-. \"<div>Forwards requests to</div><div style='font-size: 70%'>[HTTPS]</div>\" .->11\n" +
                "  9-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC/SSL]</div>\" .->16\n", diagram.getDefinition());
    }

    @Test
    public void test_renderDeploymentViewAssociatedWithASoftwareSystem_OnlyIncludesContainerInstancesAssociatedWithThatSoftwareSystem() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem1 = workspace.getModel().addSoftwareSystem("Software System 1");
        Container container1 = softwareSystem1.addContainer("Container 1", "Description", "Technology");
        SoftwareSystem softwareSystem2 = workspace.getModel().addSoftwareSystem("Software System 2");
        Container container2 = softwareSystem2.addContainer("Container 2", "Description", "Technology");

        DeploymentNode deploymentNode = workspace.getModel().addDeploymentNode("Deployment Node");
        deploymentNode.addDeploymentNode("Child 1").add(container1);
        deploymentNode.addDeploymentNode("Child 2").add(container2);

        DeploymentView viewAll = workspace.getViews().createDeploymentView("all", "description");
        viewAll.addAllDeploymentNodes();

        DeploymentView view1 = workspace.getViews().createDeploymentView(softwareSystem1, "softwaresystem1", "description");
        view1.addAllDeploymentNodes();

        StringWriter stringWriter = new StringWriter();
        new MermaidWriter().write(viewAll, stringWriter);
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 5 [Deployment Node]\n" +
                "    subgraph 6 [Child 1]\n" +
                "        7[\"<div style='font-weight: bold'>Container 1</div><div style='font-size: 70%; margin-top: 0px'>[Container: Technology]</div><div style='font-size: 80%; margin-top:10px'>Description</div>\"]\n" +
                "        style 7 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "    end\n" +
                "    style 6 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 8 [Child 2]\n" +
                "        9[\"<div style='font-weight: bold'>Container 2</div><div style='font-size: 70%; margin-top: 0px'>[Container: Technology]</div><div style='font-size: 80%; margin-top:10px'>Description</div>\"]\n" +
                "        style 9 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "    end\n" +
                "    style 8 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 5 fill:#ffffff,stroke:#000000,color:#000000\n".replaceAll("\n", System.lineSeparator()), stringWriter.toString());

        stringWriter = new StringWriter();
        new MermaidWriter().write(view1, stringWriter);
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 5 [Deployment Node]\n" +
                "    subgraph 6 [Child 1]\n" +
                "        7[\"<div style='font-weight: bold'>Container 1</div><div style='font-size: 70%; margin-top: 0px'>[Container: Technology]</div><div style='font-size: 80%; margin-top:10px'>Description</div>\"]\n" +
                "        style 7 fill:#dddddd,stroke:#9a9a9a,color:#000000\n" +
                "    end\n" +
                "    style 6 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 5 fill:#ffffff,stroke:#000000,color:#000000\n".replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

}