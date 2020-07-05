package com.structurizr.io.mermaid;

import com.structurizr.Workspace;
import com.structurizr.util.WorkspaceUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MermaidWriterTests {

    @Test
    public void test() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));

        Collection<MermaidDiagram> diagrams = new MermaidWriter().toMermaidDiagrams(workspace);
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
                "  29-->|\"<div style='font-weight: bold'>2. Calls isAuthenticated() on</div><div style='font-size: 70%'></div>\"|32\n" +
                "  32-->|\"<div style='font-weight: bold'>3. select * from users where<br />username = ?</div><div style='font-size: 70%'>[JDBC]</div>\"|21\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 50 [Developer Laptop]\n" +
                "    subgraph 55 [Docker Container - Database Server]\n" +
                "      subgraph 56 [Database Server]\n" +
                "          57[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 57 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 56 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 55 fill:#ffffff,stroke:#000000,color:#000000\n" +
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
                "    subgraph 59 [Web Browser]\n" +
                "        60[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "        style 60 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    end\n" +
                "    style 59 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 50 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  54-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->57\n" +
                "  60-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->54\n" +
                "  53-. \"<div>Delivers to the customer's<br />web browser</div><div style='font-size: 70%'></div>\" .->60\n", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        assertEquals("graph TB\n" +
                "  linkStyle default fill:#ffffff\n" +
                "\n" +
                "  subgraph 68 [Big Bank plc]\n" +
                "    subgraph 73 [bigbank-api***]\n" +
                "      subgraph 74 [Apache Tomcat]\n" +
                "          75[\"<div style='font-weight: bold'>API Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Provides Internet banking<br />functionality via a<br />JSON/HTTPS API.</div>\"]\n" +
                "          style 75 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 74 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 73 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 78 [bigbank-db01]\n" +
                "      subgraph 79 [Oracle - Primary]\n" +
                "          80[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 80 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 79 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 78 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 82 [bigbank-db02]\n" +
                "      subgraph 83 [Oracle - Secondary]\n" +
                "          84[(\"<div style='font-weight: bold'>Database</div><div style='font-size: 70%; margin-top: 0px'>[Container: Oracle Database Schema]</div><div style='font-size: 80%; margin-top:10px'>Stores user registration<br />information, hashed<br />authentication credentials,<br />access logs, etc.</div>\")]\n" +
                "          style 84 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 83 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 82 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    subgraph 69 [bigbank-web***]\n" +
                "      subgraph 70 [Apache Tomcat]\n" +
                "          71[\"<div style='font-weight: bold'>Web Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: Java and Spring MVC]</div><div style='font-size: 80%; margin-top:10px'>Delivers the static content<br />and the Internet banking<br />single page application.</div>\"]\n" +
                "          style 71 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "      end\n" +
                "      style 70 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "    end\n" +
                "    style 69 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 68 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  subgraph 65 [Customer's computer]\n" +
                "    subgraph 66 [Web Browser]\n" +
                "        67[\"<div style='font-weight: bold'>Single-Page Application</div><div style='font-size: 70%; margin-top: 0px'>[Container: JavaScript and Angular]</div><div style='font-size: 80%; margin-top:10px'>Provides all of the Internet<br />banking functionality to<br />customers via their web<br />browser.</div>\"]\n" +
                "        style 67 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "    end\n" +
                "    style 66 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  end\n" +
                "  style 65 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  subgraph 63 [Customer's mobile device]\n" +
                "      64[\"<div style='font-weight: bold'>Mobile App</div><div style='font-size: 70%; margin-top: 0px'>[Container: Xamarin]</div><div style='font-size: 80%; margin-top:10px'>Provides a limited subset of<br />the Internet banking<br />functionality to customers<br />via their mobile device.</div>\"]\n" +
                "      style 64 fill:#438dd5,stroke:#2e6295,color:#ffffff\n" +
                "  end\n" +
                "  style 63 fill:#ffffff,stroke:#000000,color:#000000\n" +
                "  75-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->80\n" +
                "  75-. \"<div>Reads from and writes to</div><div style='font-size: 70%'>[JDBC]</div>\" .->84\n" +
                "  64-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->75\n" +
                "  67-. \"<div>Makes API calls to</div><div style='font-size: 70%'>[JSON/HTTPS]</div>\" .->75\n" +
                "  71-. \"<div>Delivers to the customer's<br />web browser</div><div style='font-size: 70%'></div>\" .->67\n", diagram.getDefinition());
    }

}
