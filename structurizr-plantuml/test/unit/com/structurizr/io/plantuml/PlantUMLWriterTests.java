package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.util.ThemeUtils;
import com.structurizr.util.WorkspaceUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class PlantUMLWriterTests {

   @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));

        Collection<PlantUMLDiagram> diagrams = new PlantUMLWriter().toPlantUMLDiagrams(workspace);
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
                "29 -[#707070]> 32 : 2. Calls isAuthenticated() on\n" +
                "32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
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
                "node \"Developer Laptop\" <<Deployment Node: Microsoft Windows 10 or Apple macOS>> as 50 {\n" +
                "  node \"Docker Container - Database Server\" <<Deployment Node: Docker>> as 55 {\n" +
                "    node \"Database Server\" <<Deployment Node: Oracle 12c>> as 56 {\n" +
                "      database 57 <<Container: Oracle Database Schema>> #438dd5 [\n" +
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
                "  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 59 {\n" +
                "    rectangle 60 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "      Single-Page Application\n" +
                "      --\n" +
                "      Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "54 .[#707070].> 57 : <<JDBC>>\\nReads from and writes to\n" +
                "60 .[#707070].> 54 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "53 .[#707070].> 60 : Delivers to the customer's web browser\n" +
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
                "node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 68 {\n" +
                "  node \"bigbank-api*** (x8)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 73 {\n" +
                "    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 74 {\n" +
                "      rectangle 75 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        API Application\n" +
                "        --\n" +
                "        Provides Internet banking functionality via a JSON/HTTPS API.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db01\" <<Deployment Node: Ubuntu 16.04 LTS>> as 78 {\n" +
                "    node \"Oracle - Primary\" <<Deployment Node: Oracle 12c>> as 79 {\n" +
                "      database 80 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "        Database\n" +
                "        --\n" +
                "        Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db02\" <<Deployment Node: Ubuntu 16.04 LTS>> as 82 {\n" +
                "    node \"Oracle - Secondary\" <<Deployment Node: Oracle 12c>> as 83 {\n" +
                "      database 84 <<Container: Oracle Database Schema>> #438dd5 [\n" +
                "        Database\n" +
                "        --\n" +
                "        Stores user registration information, hashed authentication credentials, access logs, etc.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-web*** (x4)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 69 {\n" +
                "    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 70 {\n" +
                "      rectangle 71 <<Container: Java and Spring MVC>> #438dd5 [\n" +
                "        Web Application\n" +
                "        --\n" +
                "        Delivers the static content and the Internet banking single page application.\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's computer\" <<Deployment Node: Microsoft Windows or Apple macOS>> as 65 {\n" +
                "  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 66 {\n" +
                "    rectangle 67 <<Container: JavaScript and Angular>> #438dd5 [\n" +
                "      Single-Page Application\n" +
                "      --\n" +
                "      Provides all of the Internet banking functionality to customers via their web browser.\n" +
                "    ]\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's mobile device\" <<Deployment Node: Apple iOS or Android>> as 63 {\n" +
                "  rectangle 64 <<Container: Xamarin>> #438dd5 [\n" +
                "    Mobile App\n" +
                "    --\n" +
                "    Provides a limited subset of the Internet banking functionality to customers via their mobile device.\n" +
                "  ]\n" +
                "}\n" +
                "75 .[#707070].> 80 : <<JDBC>>\\nReads from and writes to\n" +
                "75 .[#707070].> 84 : <<JDBC>>\\nReads from and writes to\n" +
                "64 .[#707070].> 75 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "79 .[#707070].> 83 : Replicates data to\n" +
                "67 .[#707070].> 75 : <<JSON/HTTPS>>\\nMakes API calls to\n" +
                "71 .[#707070].> 67 : Delivers to the customer's web browser\n" +
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

}