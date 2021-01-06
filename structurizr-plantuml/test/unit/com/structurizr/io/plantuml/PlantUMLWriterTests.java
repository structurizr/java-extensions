package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.Container;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.util.ThemeUtils;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.DeploymentView;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class PlantUMLWriterTests {

   @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        PlantUMLWriter plantUMLWriter = new PlantUMLWriter();

        Collection<PlantUMLDiagram> diagrams = plantUMLWriter.toPlantUMLDiagrams(workspace);
        assertEquals(7, diagrams.size());

        PlantUMLDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        assertEquals("@startuml(id=SystemLandscape)\n" +
                "title System Landscape for Big Bank plc\n" +
                "caption The system landscape diagram for Big Bank plc.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "actor \"Personal Banking Customer\" <<Person>> as 1 #08427b\n" +
                "note right of 1\n" +
                "  A customer of the bank, with personal bank accounts.\n" +
                "end note\n" +
                "package \"Big Bank plc\" {\n" +
                "  actor \"Back Office Staff\" <<Person>> as 15 #999999\n" +
                "  note right of 15\n" +
                "    Administration and support staff within the bank.\n" +
                "  end note\n" +
                "  actor \"Customer Service Staff\" <<Person>> as 12 #999999\n" +
                "  note right of 12\n" +
                "    Customer service staff within the bank.\n" +
                "  end note\n" +
                "  rectangle 9 <<Software System>> #999999 [\n" +
                "    ATM\n" +
                "    --\n" +
                "    Allows customers to withdraw cash.\n" +
                "  ]\n" +
                "  rectangle 6 <<Software System>> #999999 [\n" +
                "    E-mail System\n" +
                "    --\n" +
                "    The internal Microsoft Exchange e-mail system.\n" +
                "  ]\n" +
                "  rectangle 2 <<Software System>> #1168bd [\n" +
                "    Internet Banking System\n" +
                "    --\n" +
                "    Allows customers to view information about their bank accounts, and make payments.\n" +
                "  ]\n" +
                "  rectangle 4 <<Software System>> #999999 [\n" +
                "    Mainframe Banking System\n" +
                "    --\n" +
                "    Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "  ]\n" +
                "}\n" +
                "9 .[#707070].> 4 : Uses\n" +
                "15 .[#707070].> 4 : Uses\n" +
                "12 .[#707070].> 4 : Uses\n" +
                "6 .[#707070].> 1 : Sends e-mails to\n" +
                "2 .[#707070].> 6 : Sends e-mail using\n" +
                "2 .[#707070].> 4 : Gets account information from, and makes payments using\n" +
                "1 .[#707070].> 9 : Withdraws cash using\n" +
                "1 .[#707070].> 12 : <<Telephone>>\\nAsks questions to\n" +
                "1 .[#707070].> 2 : Views account balances, and makes payments using\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
        assertEquals("@startuml(id=SystemContext)\n" +
                "title Internet Banking System - System Context\n" +
                "caption The system context diagram for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "actor \"Personal Banking Customer\" <<Person>> as 1 #08427b\n" +
                "note right of 1\n" +
                "  A customer of the bank, with personal bank accounts.\n" +
                "end note\n" +
                "  rectangle 6 <<Software System>> #999999 [\n" +
                "    E-mail System\n" +
                "    --\n" +
                "    The internal Microsoft Exchange e-mail system.\n" +
                "  ]\n" +
                "  rectangle 2 <<Software System>> #1168bd [\n" +
                "    Internet Banking System\n" +
                "    --\n" +
                "    Allows customers to view information about their bank accounts, and make payments.\n" +
                "  ]\n" +
                "  rectangle 4 <<Software System>> #999999 [\n" +
                "    Mainframe Banking System\n" +
                "    --\n" +
                "    Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "  ]\n" +
                "6 .[#707070].> 1 : Sends e-mails to\n" +
                "2 .[#707070].> 6 : Sends e-mail using\n" +
                "2 .[#707070].> 4 : Gets account information from, and makes payments using\n" +
                "1 .[#707070].> 2 : Views account balances, and makes payments using\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        assertEquals("@startuml(id=Containers)\n" +
                "title Internet Banking System - Containers\n" +
                "caption The container diagram for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "rectangle 6 <<Software System>> #999999 [\n" +
                "  E-mail System\n" +
                "  --\n" +
                "  The internal Microsoft Exchange e-mail system.\n" +
                "]\n" +
                "rectangle 4 <<Software System>> #999999 [\n" +
                "  Mainframe Banking System\n" +
                "  --\n" +
                "  Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "]\n" +
                "actor \"Personal Banking Customer\" <<Person>> as 1 #08427b\n" +
                "note right of 1\n" +
                "  A customer of the bank, with personal bank accounts.\n" +
                "end note\n" +
                "package \"Internet Banking System\" <<Software System>> {\n" +
                "  rectangle 20 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "    API Application\n" +
                "    --\n" +
                "    Provides Internet banking functionality via a JSON/HTTPS API.\n" +
                "  ]\n" +
                "  database 21 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "    Database\n" +
                "    --\n" +
                "    Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "  ]\n" +
                "  rectangle 18 <<Container: Xamarin>> #438dd5 [\n" +
                "    Mobile App\n" +
                "    --\n" +
                "    Provides a limited subset of the Internet banking functionality to customers via their mobile device.\n" +
                "  ]\n" +
                "  rectangle 17 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "    Single-Page Application\n" +
                "    --\n" +
                "    Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "  ]\n" +
                "  rectangle 19 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "    Web Application\n" +
                "    --\n" +
                "    Delivers the static content and the Internet banking single page application.\n" +
                "  ]\n" +
                "}\n" +
                "20 .[#707070].> 21 : <<JDBC>>\\nReads from and writes to\n" +
                "20 .[#707070].> 6 : <<SMTP>>\\nSends e-mail using\n" +
                "20 .[#707070].> 4 : <<XML/HTTPS>>\\nMakes API calls to\n" +
                "6 .[#707070].> 1 : Sends e-mails to\n" +
                "18 .[#707070].> 20 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "1 .[#707070].> 18 : Views account balances, and makes payments using\n" +
                "1 .[#707070].> 17 : Views account balances, and makes payments using\n" +
                "1 .[#707070].> 19 : <<HTTPS>>\\nVisits bigbank.com/ib using\n" +
                "17 .[#707070].> 20 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "19 .[#707070].> 17 : Delivers to the customer's web browser\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        assertEquals("@startuml(id=Components)\n" +
                "title Internet Banking System - API Application - Components\n" +
                "caption The component diagram for the API Application.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "database 21 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "  Database\n" +
                "  --\n" +
                "  Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "]\n" +
                "rectangle 6 <<Software System>> #999999 [\n" +
                "  E-mail System\n" +
                "  --\n" +
                "  The internal Microsoft Exchange e-mail system.\n" +
                "]\n" +
                "rectangle 4 <<Software System>> #999999 [\n" +
                "  Mainframe Banking System\n" +
                "  --\n" +
                "  Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "]\n" +
                "rectangle 18 <<Container: Xamarin>> #438dd5 [\n" +
                "  Mobile App\n" +
                "  --\n" +
                "  Provides a limited subset of the Internet banking functionality to customers via their mobile device.\n" +
                "]\n" +
                "rectangle 17 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "  Single-Page Application\n" +
                "  --\n" +
                "  Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "]\n" +
                "package \"API Application\" <<Container: Java and Spring MVC>> {\n" +
                "  rectangle 30 <<Component: Spring MVC Rest Controller>> #85bbf0 [\n" +
                "    Accounts Summary Controller\n" +
                "    --\n" +
                "    Provides customers with a summary of their bank accounts.\n" +
                "  ]\n" +
                "  rectangle 34 <<Component: Spring Bean>> #85bbf0 [\n" +
                "    E-mail Component\n" +
                "    --\n" +
                "    Sends e-mails to users.\n" +
                "  ]\n" +
                "  rectangle 33 <<Component: Spring Bean>> #85bbf0 [\n" +
                "    Mainframe Banking System Facade\n" +
                "    --\n" +
                "    A facade onto the mainframe banking system.\n" +
                "  ]\n" +
                "  rectangle 31 <<Component: Spring MVC Rest Controller>> #85bbf0 [\n" +
                "    Reset Password Controller\n" +
                "    --\n" +
                "    Allows users to reset their passwords with a single use URL.\n" +
                "  ]\n" +
                "  rectangle 32 <<Component: Spring Bean>> #85bbf0 [\n" +
                "    Security Component\n" +
                "    --\n" +
                "    Provides functionality related to signing in, changing passwords, etc.\n" +
                "  ]\n" +
                "  rectangle 29 <<Component: Spring MVC Rest Controller>> #85bbf0 [\n" +
                "    Sign In Controller\n" +
                "    --\n" +
                "    Allows users to sign in to the Internet Banking System.\n" +
                "  ]\n" +
                "}\n" +
                "30 .[#707070].> 33 : Uses\n" +
                "34 .[#707070].> 6 : Sends e-mail using\n" +
                "33 .[#707070].> 4 : <<XML/HTTPS>>\\nUses\n" +
                "18 .[#707070].> 30 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "18 .[#707070].> 31 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "18 .[#707070].> 29 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "31 .[#707070].> 34 : Uses\n" +
                "31 .[#707070].> 32 : Uses\n" +
                "32 .[#707070].> 21 : <<JDBC>>\\nReads from and writes to\n" +
                "29 .[#707070].> 32 : Uses\n" +
                "17 .[#707070].> 30 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "17 .[#707070].> 31 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "17 .[#707070].> 29 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("@startuml(id=SignIn)\n" +
                "title API Application - Dynamic\n" +
                "caption Summarises how the sign in feature works in the single-page application.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "rectangle 17 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "  Single-Page Application\n" +
                "  --\n" +
                "  Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "]\n" +
                "rectangle 29 <<Component: Spring MVC Rest Controller>> #85bbf0 [\n" +
                "  Sign In Controller\n" +
                "  --\n" +
                "  Allows users to sign in to the Internet Banking System.\n" +
                "]\n" +
                "rectangle 32 <<Component: Spring Bean>> #85bbf0 [\n" +
                "  Security Component\n" +
                "  --\n" +
                "  Provides functionality related to signing in, changing passwords, etc.\n" +
                "]\n" +
                "database 21 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "  Database\n" +
                "  --\n" +
                "  Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "]\n" +
                "17 -[#707070]> 29 : 1. Submits credentials to\n" +
                "29 -[#707070]> 32 : 2. Validates credentials using\n" +
                "32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
                "21 -[#707070]-> 32 : 4. Returns user data to\n" +
                "32 -[#707070]-> 29 : 5. Returns true if the hashed password matches\n" +
                "29 -[#707070]-> 17 : 6. Sends back an authentication token to\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        assertEquals("@startuml(id=DevelopmentDeployment)\n" +
                "title Internet Banking System - Deployment - Development\n" +
                "caption An example development deployment scenario for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 55 {\n" +
                "  node \"bigbank-dev001\" <<Deployment Node>> as 56 {\n" +
                "    rectangle 57 <<Software System>> #999999 [\n" +
                "      Mainframe Banking System\n" +
                "      --\n" +
                "      Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "node \"Developer Laptop\" <<Deployment Node: Microsoft Windows 10 or Apple macOS>> as 50 {\n" +
                "  node \"Docker Container - Database Server\" <<Deployment Node: Docker>> as 59 {\n" +
                "    node \"Database Server\" <<Deployment Node: Oracle 12c>> as 60 {\n" +
                "      database 61 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "        Database\n" +
                "        --\n" +
                "        Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Docker Container - Web Server\" <<Deployment Node: Docker>> as 51 {\n" +
                "    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 52 {\n" +
                "      rectangle 54 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        API Application\n" +
                "        --\n" +
                "        Provides Internet banking functionality via a JSON/HTTPS API.\n" +
                "      ]\n" +
                "      rectangle 53 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        Web Application\n" +
                "        --\n" +
                "        Delivers the static content and the Internet banking single page application.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 63 {\n" +
                "    rectangle 64 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "      Single-Page Application\n" +
                "      --\n" +
                "      Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "54 .[#707070].> 61 : <<JDBC>>\\nReads from and writes to\n" +
                "54 .[#707070].> 57 : <<XML/HTTPS>>\\nMakes API calls to\n" +
                "64 .[#707070].> 54 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "53 .[#707070].> 64 : Delivers to the customer's web browser\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        assertEquals("@startuml(id=LiveDeployment)\n" +
                "title Internet Banking System - Deployment - Live\n" +
                "caption An example live deployment scenario for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 72 {\n" +
                "  node \"bigbank-api*** (x8)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 79 {\n" +
                "    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 80 {\n" +
                "      rectangle 81 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        API Application\n" +
                "        --\n" +
                "        Provides Internet banking functionality via a JSON/HTTPS API.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db01\" <<Deployment Node: Ubuntu 16.04 LTS>> as 85 {\n" +
                "    node \"Oracle - Primary\" <<Deployment Node: Oracle 12c>> as 86 {\n" +
                "      database 87 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "        Database\n" +
                "        --\n" +
                "        Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db02\" <<Deployment Node: Ubuntu 16.04 LTS>> as 89 {\n" +
                "    node \"Oracle - Secondary\" <<Deployment Node: Oracle 12c>> as 90 {\n" +
                "      database 91 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "        Database\n" +
                "        --\n" +
                "        Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-prod001\" <<Deployment Node>> as 73 {\n" +
                "    rectangle 74 <<Software System>> #999999 [\n" +
                "      Mainframe Banking System\n" +
                "      --\n" +
                "      Stores all of the core banking information about customers, accounts, transactions, etc.\n" +
                "    ]\n" +
                "  }\n" +
                "  node \"bigbank-web*** (x4)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 75 {\n" +
                "    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 76 {\n" +
                "      rectangle 77 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        Web Application\n" +
                "        --\n" +
                "        Delivers the static content and the Internet banking single page application.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's computer\" <<Deployment Node: Microsoft Windows or Apple macOS>> as 69 {\n" +
                "  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 70 {\n" +
                "    rectangle 71 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "      Single-Page Application\n" +
                "      --\n" +
                "      Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's mobile device\" <<Deployment Node: Apple iOS or Android>> as 67 {\n" +
                "  rectangle 68 <<Container: Xamarin>> #438dd5 [\n" +
                "    Mobile App\n" +
                "    --\n" +
                "    Provides a limited subset of the Internet banking functionality to customers via their mobile device.\n" +
                "  ]\n" +
                "}\n" +
                "81 .[#707070].> 91 : <<JDBC>>\\nReads from and writes to\n" +
                "81 .[#707070].> 87 : <<JDBC>>\\nReads from and writes to\n" +
                "81 .[#707070].> 74 : <<XML/HTTPS>>\\nMakes API calls to\n" +
                "68 .[#707070].> 81 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "86 .[#707070].> 90 : Replicates data to\n" +
                "71 .[#707070].> 81 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "77 .[#707070].> 71 : Delivers to the customer's web browser\n" +
                "@enduml", diagram.getDefinition());

       plantUMLWriter.setUseSequenceDiagrams(true);
       diagrams = plantUMLWriter.toPlantUMLDiagrams(workspace);

       diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
       assertEquals("@startuml(id=SignIn)\n" +
               "title API Application - Dynamic\n" +
               "caption Summarises how the sign in feature works in the single-page application.\n" +
               "\n" +
               "skinparam {\n" +
               "  shadowing false\n" +
               "  arrowColor #707070\n" +
               "  actorBorderColor #707070\n" +
               "  componentBorderColor #707070\n" +
               "  rectangleBorderColor #707070\n" +
               "  noteBackgroundColor #ffffff\n" +
               "  noteBorderColor #707070\n" +
               "  defaultTextAlignment center\n" +
               "  wrapWidth 200\n" +
               "  maxMessageSize 100\n" +
               "}\n" +
               "participant \"Single-Page Application\" as 17 <<Container: JavaScript and Angular>> #438dd5\n" +
               "participant \"Sign In Controller\" as 29 <<Component: Spring MVC Rest Controller>> #85bbf0\n" +
               "participant \"Security Component\" as 32 <<Component: Spring Bean>> #85bbf0\n" +
               "database \"Database\" as 21 <<Container: Oracle Database Schema>> #438dd5\n" +
               "17 -[#707070]> 29 : 1. Submits credentials to\n" +
               "29 -[#707070]> 32 : 2. Validates credentials using\n" +
               "32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
               "21 -[#707070]-> 32 : 4. Returns user data to\n" +
               "32 -[#707070]-> 29 : 5. Returns true if the hashed password matches\n" +
               "29 -[#707070]-> 17 : 6. Sends back an authentication token to\n" +
               "@enduml", diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadStylesFromThemes(workspace);

        Collection<PlantUMLDiagram> diagrams = new PlantUMLWriter().toPlantUMLDiagrams(workspace);
        assertEquals(1, diagrams.size());

        PlantUMLDiagram diagram = diagrams.stream().findFirst().get();
        assertEquals("@startuml(id=AmazonWebServicesDeployment)\n" +
                "title Spring PetClinic - Deployment - Default\n" +
                "caption An example deployment diagram.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "node \"Amazon Web Services\" <<Deployment Node>> as 5 {\n" +
                "  node \"US-East-1\" <<Deployment Node>> as 6 {\n" +
                "    node \"Amazon RDS\" <<Deployment Node>> as 14 {\n" +
                "      node \"MySQL\" <<Deployment Node>> as 15 {\n" +
                "        database 16 <<Container: Relational database schema>> #ffffff [\n" +
                "          Database\n" +
                "          --\n" +
                "          Stores information regarding the veterinarians, the clients, and their pets.\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "    node \"Autoscaling group\" <<Deployment Node>> as 7 {\n" +
                "      node \"Amazon EC2\" <<Deployment Node>> as 8 {\n" +
                "        rectangle 9 <<Container: Java and Spring Boot>> #ffffff [\n" +
                "          Web Application\n" +
                "          --\n" +
                "          Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "    rectangle 11 <<Infrastructure Node>> #ffffff [\n" +
                "      Elastic Load Balancer\n" +
                "    ]\n" +
                "    rectangle 10 <<Infrastructure Node>> #ffffff [\n" +
                "      Route 53\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "11 .[#707070].> 9 : <<HTTPS>>\\nForwards requests to\n" +
                "10 .[#707070].> 11 : <<HTTPS>>\\nForwards requests to\n" +
                "9 .[#707070].> 16 : <<JDBC/SSL>>\\nReads from and writes to\n" +
                "@enduml", diagram.getDefinition());
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
        new PlantUMLWriter().write(viewAll, stringWriter);
        assertEquals("@startuml(id=all)\n" +
                "title Deployment - Default\n" +
                "caption description\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "node \"Deployment Node\" <<Deployment Node>> as 5 {\n" +
                "  node \"Child 1\" <<Deployment Node>> as 6 {\n" +
                "    rectangle 7 <<Container: Technology>> #dddddd [\n" +
                "      Container 1\n" +
                "      --\n" +
                "      Description\n" +
                "    ]\n" +
                "  }\n" +
                "  node \"Child 2\" <<Deployment Node>> as 8 {\n" +
                "    rectangle 9 <<Container: Technology>> #dddddd [\n" +
                "      Container 2\n" +
                "      --\n" +
                "      Description\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());

        stringWriter = new StringWriter();
        new PlantUMLWriter().write(view1, stringWriter);
        assertEquals("@startuml(id=softwaresystem1)\n" +
                "title Software System 1 - Deployment - Default\n" +
                "caption description\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowColor #707070\n" +
                "  actorBorderColor #707070\n" +
                "  componentBorderColor #707070\n" +
                "  rectangleBorderColor #707070\n" +
                "  noteBackgroundColor #ffffff\n" +
                "  noteBorderColor #707070\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "node \"Deployment Node\" <<Deployment Node>> as 5 {\n" +
                "  node \"Child 1\" <<Deployment Node>> as 6 {\n" +
                "    rectangle 7 <<Container: Technology>> #dddddd [\n" +
                "      Container 1\n" +
                "      --\n" +
                "      Description\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());
    }

}