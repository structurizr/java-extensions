package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.*;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class StructurizrPlantUMLWriterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        StructurizrPlantUMLWriter structurizrPlantUMLWriter = new StructurizrPlantUMLWriter();

        Collection<PlantUMLDiagram> diagrams = structurizrPlantUMLWriter.toPlantUMLDiagrams(workspace);
        assertEquals(7, diagrams.size());

        PlantUMLDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
        assertEquals("@startuml(id=SystemLandscape)\n" +
                "title System Landscape for Big Bank plc\n" +
                "caption The system landscape diagram for Big Bank plc.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<1>> {\n" +
                "  BackgroundColor #08427b\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #052e56\n" +
                "}\n" +
                "skinparam rectangle<<12>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<2>> {\n" +
                "  BackgroundColor #1168bd\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #0b4884\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<15>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<9>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "rectangle \"==Personal Banking Customer\\n<size:10>[Person]</size>\\n\\nA customer of the bank, with personal bank accounts.\" <<1>> as 1\n" +
                "package \"Big Bank plc\" {\n" +
                "  rectangle \"==Back Office Staff\\n<size:10>[Person]</size>\\n\\nAdministration and support staff within the bank.\" <<15>> as 15\n" +
                "  rectangle \"==Customer Service Staff\\n<size:10>[Person]</size>\\n\\nCustomer service staff within the bank.\" <<12>> as 12\n" +
                "  rectangle \"==ATM\\n<size:10>[Software System]</size>\\n\\nAllows customers to withdraw cash.\" <<9>> as 9\n" +
                "  rectangle \"==E-mail System\\n<size:10>[Software System]</size>\\n\\nThe internal Microsoft Exchange e-mail system.\" <<6>> as 6\n" +
                "  rectangle \"==Internet Banking System\\n<size:10>[Software System]</size>\\n\\nAllows customers to view information about their bank accounts, and make payments.\" <<2>> as 2\n" +
                "  rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<4>> as 4\n" +
                "}\n" +
                "9 .[#707070,thickness=2].> 4 : \"Uses\"\n" +
                "15 .[#707070,thickness=2].> 4 : \"Uses\"\n" +
                "12 .[#707070,thickness=2].> 4 : \"Uses\"\n" +
                "6 .[#707070,thickness=2].> 1 : \"Sends e-mails to\"\n" +
                "2 .[#707070,thickness=2].> 6 : \"Sends e-mail using\"\n" +
                "2 .[#707070,thickness=2].> 4 : \"Gets account information from, and makes payments using\"\n" +
                "1 .[#707070,thickness=2].> 9 : \"Withdraws cash using\"\n" +
                "1 .[#707070,thickness=2].> 12 : \"Asks questions to\\n<size:8>[Telephone]</size>\"\n" +
                "1 .[#707070,thickness=2].> 2 : \"Views account balances, and makes payments using\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
        assertEquals("@startuml(id=SystemContext)\n" +
                "title Internet Banking System - System Context\n" +
                "caption The system context diagram for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<1>> {\n" +
                "  BackgroundColor #08427b\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #052e56\n" +
                "}\n" +
                "skinparam rectangle<<2>> {\n" +
                "  BackgroundColor #1168bd\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #0b4884\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "rectangle \"==Personal Banking Customer\\n<size:10>[Person]</size>\\n\\nA customer of the bank, with personal bank accounts.\" <<1>> as 1\n" +
                "  rectangle \"==E-mail System\\n<size:10>[Software System]</size>\\n\\nThe internal Microsoft Exchange e-mail system.\" <<6>> as 6\n" +
                "  rectangle \"==Internet Banking System\\n<size:10>[Software System]</size>\\n\\nAllows customers to view information about their bank accounts, and make payments.\" <<2>> as 2\n" +
                "  rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<4>> as 4\n" +
                "6 .[#707070,thickness=2].> 1 : \"Sends e-mails to\"\n" +
                "2 .[#707070,thickness=2].> 6 : \"Sends e-mail using\"\n" +
                "2 .[#707070,thickness=2].> 4 : \"Gets account information from, and makes payments using\"\n" +
                "1 .[#707070,thickness=2].> 2 : \"Views account balances, and makes payments using\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
        assertEquals("@startuml(id=Containers)\n" +
                "title Internet Banking System - Containers\n" +
                "caption The container diagram for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<1>> {\n" +
                "  BackgroundColor #08427b\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #052e56\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<18>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<19>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<20>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "rectangle \"==E-mail System\\n<size:10>[Software System]</size>\\n\\nThe internal Microsoft Exchange e-mail system.\" <<6>> as 6\n" +
                "rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<4>> as 4\n" +
                "rectangle \"==Personal Banking Customer\\n<size:10>[Person]</size>\\n\\nA customer of the bank, with personal bank accounts.\" <<1>> as 1\n" +
                "package \"Internet Banking System\\n[Software System]\" {\n" +
                "  rectangle \"==API Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nProvides Internet banking functionality via a JSON/HTTPS API.\" <<20>> as 20\n" +
                "  database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<21>> as 21\n" +
                "  rectangle \"==Mobile App\\n<size:10>[Container: Xamarin]</size>\\n\\nProvides a limited subset of the Internet banking functionality to customers via their mobile device.\" <<18>> as 18\n" +
                "  rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<17>> as 17\n" +
                "  rectangle \"==Web Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nDelivers the static content and the Internet banking single page application.\" <<19>> as 19\n" +
                "}\n" +
                "20 .[#707070,thickness=2].> 21 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "20 .[#707070,thickness=2].> 6 : \"Sends e-mail using\\n<size:8>[SMTP]</size>\"\n" +
                "20 .[#707070,thickness=2].> 4 : \"Makes API calls to\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "6 .[#707070,thickness=2].> 1 : \"Sends e-mails to\"\n" +
                "18 .[#707070,thickness=2].> 20 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "1 .[#707070,thickness=2].> 18 : \"Views account balances, and makes payments using\"\n" +
                "1 .[#707070,thickness=2].> 17 : \"Views account balances, and makes payments using\"\n" +
                "1 .[#707070,thickness=2].> 19 : \"Visits bigbank.com/ib using\\n<size:8>[HTTPS]</size>\"\n" +
                "17 .[#707070,thickness=2].> 20 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "19 .[#707070,thickness=2].> 17 : \"Delivers to the customer's web browser\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
        assertEquals("@startuml(id=Components)\n" +
                "title Internet Banking System - API Application - Components\n" +
                "caption The component diagram for the API Application.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<33>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam rectangle<<34>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam rectangle<<18>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<29>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam rectangle<<30>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam rectangle<<31>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<32>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<21>> as 21\n" +
                "rectangle \"==E-mail System\\n<size:10>[Software System]</size>\\n\\nThe internal Microsoft Exchange e-mail system.\" <<6>> as 6\n" +
                "rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<4>> as 4\n" +
                "rectangle \"==Mobile App\\n<size:10>[Container: Xamarin]</size>\\n\\nProvides a limited subset of the Internet banking functionality to customers via their mobile device.\" <<18>> as 18\n" +
                "rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<17>> as 17\n" +
                "package \"API Application\\n[Container: Java and Spring MVC]\" {\n" +
                "  rectangle \"==Accounts Summary Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\\n\\nProvides customers with a summary of their bank accounts.\" <<30>> as 30\n" +
                "  rectangle \"==E-mail Component\\n<size:10>[Component: Spring Bean]</size>\\n\\nSends e-mails to users.\" <<34>> as 34\n" +
                "  rectangle \"==Mainframe Banking System Facade\\n<size:10>[Component: Spring Bean]</size>\\n\\nA facade onto the mainframe banking system.\" <<33>> as 33\n" +
                "  rectangle \"==Reset Password Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\\n\\nAllows users to reset their passwords with a single use URL.\" <<31>> as 31\n" +
                "  rectangle \"==Security Component\\n<size:10>[Component: Spring Bean]</size>\\n\\nProvides functionality related to signing in, changing passwords, etc.\" <<32>> as 32\n" +
                "  rectangle \"==Sign In Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\\n\\nAllows users to sign in to the Internet Banking System.\" <<29>> as 29\n" +
                "}\n" +
                "30 .[#707070,thickness=2].> 33 : \"Uses\"\n" +
                "34 .[#707070,thickness=2].> 6 : \"Sends e-mail using\"\n" +
                "33 .[#707070,thickness=2].> 4 : \"Uses\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "18 .[#707070,thickness=2].> 30 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "18 .[#707070,thickness=2].> 31 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "18 .[#707070,thickness=2].> 29 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "31 .[#707070,thickness=2].> 34 : \"Uses\"\n" +
                "31 .[#707070,thickness=2].> 32 : \"Uses\"\n" +
                "32 .[#707070,thickness=2].> 21 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "29 .[#707070,thickness=2].> 32 : \"Uses\"\n" +
                "17 .[#707070,thickness=2].> 30 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "17 .[#707070,thickness=2].> 31 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "17 .[#707070,thickness=2].> 29 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("@startuml(id=SignIn)\n" +
                "title API Application - Dynamic\n" +
                "caption Summarises how the sign in feature works in the single-page application.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<29>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam rectangle<<32>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<17>> as 17\n" +
                "rectangle \"==Sign In Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\\n\\nAllows users to sign in to the Internet Banking System.\" <<29>> as 29\n" +
                "rectangle \"==Security Component\\n<size:10>[Component: Spring Bean]</size>\\n\\nProvides functionality related to signing in, changing passwords, etc.\" <<32>> as 32\n" +
                "database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<21>> as 21\n" +
                "17 .[#707070,thickness=2].> 29 : \"1. Submits credentials to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "29 .[#707070,thickness=2].> 32 : \"2. Validates credentials using\"\n" +
                "32 .[#707070,thickness=2].> 21 : \"3. select * from users where username = ?\\n<size:8>[JDBC]</size>\"\n" +
                "32 <.[#707070,thickness=2]. 21 : \"4. Returns user data to\\n<size:8>[JDBC]</size>\"\n" +
                "29 <.[#707070,thickness=2]. 32 : \"5. Returns true if the hashed password matches\"\n" +
                "17 <.[#707070,thickness=2]. 29 : \"6. Sends back an authentication token to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
        assertEquals("@startuml(id=DevelopmentDeployment)\n" +
                "title Internet Banking System - Deployment - Development\n" +
                "caption An example development deployment scenario for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam node<<55>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<56>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<57>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam node<<59>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<60>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam database<<61>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<50>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<51>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<63>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<52>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<64>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<53>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam rectangle<<54>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "node \"Big Bank plc\\n[Deployment Node: Big Bank plc data center]\" <<55>> as 55 {\n" +
                "  node \"bigbank-dev001\\n[Deployment Node]\" <<56>> as 56 {\n" +
                "    rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<57>> as 57\n" +
                "  }\n" +
                "}\n" +
                "node \"Developer Laptop\\n[Deployment Node: Microsoft Windows 10 or Apple macOS]\" <<50>> as 50 {\n" +
                "  node \"Docker Container - Database Server\\n[Deployment Node: Docker]\" <<59>> as 59 {\n" +
                "    node \"Database Server\\n[Deployment Node: Oracle 12c]\" <<60>> as 60 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<61>> as 61\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Docker Container - Web Server\\n[Deployment Node: Docker]\" <<51>> as 51 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<52>> as 52 {\n" +
                "      rectangle \"==API Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nProvides Internet banking functionality via a JSON/HTTPS API.\" <<54>> as 54\n" +
                "      rectangle \"==Web Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nDelivers the static content and the Internet banking single page application.\" <<53>> as 53\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Web Browser\\n[Deployment Node: Chrome, Firefox, Safari, or Edge]\" <<63>> as 63 {\n" +
                "    rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<64>> as 64\n" +
                "  }\n" +
                "}\n" +
                "54 .[#707070,thickness=2].> 61 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "54 .[#707070,thickness=2].> 57 : \"Makes API calls to\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "64 .[#707070,thickness=2].> 54 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "53 .[#707070,thickness=2].> 64 : \"Delivers to the customer's web browser\"\n" +
                "@enduml", diagram.getDefinition());

        diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
        assertEquals("@startuml(id=LiveDeployment)\n" +
                "title Internet Banking System - Deployment - Live\n" +
                "caption An example live deployment scenario for the Internet Banking System.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<77>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<89>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<67>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<79>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<68>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<69>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<90>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam database<<91>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<80>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<81>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<70>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<71>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<72>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<73>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<74>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6b6b6b\n" +
                "}\n" +
                "skinparam node<<85>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<86>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<75>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam database<<87>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam node<<76>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "node \"Big Bank plc\\n[Deployment Node: Big Bank plc data center]\" <<72>> as 72 {\n" +
                "  node \"bigbank-api*** (x8)\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<79>> as 79 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<80>> as 80 {\n" +
                "      rectangle \"==API Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nProvides Internet banking functionality via a JSON/HTTPS API.\" <<81>> as 81\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db01\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<85>> as 85 {\n" +
                "    node \"Oracle - Primary\\n[Deployment Node: Oracle 12c]\" <<86>> as 86 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<87>> as 87\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db02\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<89>> as 89 {\n" +
                "    node \"Oracle - Secondary\\n[Deployment Node: Oracle 12c]\" <<90>> as 90 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<91>> as 91\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-prod001\\n[Deployment Node]\" <<73>> as 73 {\n" +
                "    rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<74>> as 74\n" +
                "  }\n" +
                "  node \"bigbank-web*** (x4)\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<75>> as 75 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<76>> as 76 {\n" +
                "      rectangle \"==Web Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nDelivers the static content and the Internet banking single page application.\" <<77>> as 77\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's computer\\n[Deployment Node: Microsoft Windows or Apple macOS]\" <<69>> as 69 {\n" +
                "  node \"Web Browser\\n[Deployment Node: Chrome, Firefox, Safari, or Edge]\" <<70>> as 70 {\n" +
                "    rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<71>> as 71\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's mobile device\\n[Deployment Node: Apple iOS or Android]\" <<67>> as 67 {\n" +
                "  rectangle \"==Mobile App\\n<size:10>[Container: Xamarin]</size>\\n\\nProvides a limited subset of the Internet banking functionality to customers via their mobile device.\" <<68>> as 68\n" +
                "}\n" +
                "81 .[#707070,thickness=2].> 91 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "81 .[#707070,thickness=2].> 87 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "81 .[#707070,thickness=2].> 74 : \"Makes API calls to\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "68 .[#707070,thickness=2].> 81 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "86 .[#707070,thickness=2].> 90 : \"Replicates data to\"\n" +
                "71 .[#707070,thickness=2].> 81 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "77 .[#707070,thickness=2].> 71 : \"Delivers to the customer's web browser\"\n" +
                "@enduml", diagram.getDefinition());

        structurizrPlantUMLWriter.setUseSequenceDiagrams(true);
        diagrams = structurizrPlantUMLWriter.toPlantUMLDiagrams(workspace);

        diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
        assertEquals("@startuml(id=SignIn)\n" +
                "title API Application - Dynamic\n" +
                "caption Summarises how the sign in feature works in the single-page application.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam sequenceParticipant<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "skinparam sequenceParticipant<<29>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam sequenceParticipant<<32>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5d82a8\n" +
                "}\n" +
                "skinparam sequenceParticipant<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2e6295\n" +
                "}\n" +
                "participant \"Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\" as 17 <<17>> #438dd5\n" +
                "participant \"Sign In Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\" as 29 <<29>> #85bbf0\n" +
                "participant \"Security Component\\n<size:10>[Component: Spring Bean]</size>\" as 32 <<32>> #85bbf0\n" +
                "database \"Database\\n<size:10>[Container: Oracle Database Schema]</size>\" as 21 <<21>> #438dd5\n" +
                "17 -[#707070]> 29 : 1. Submits credentials to\n" +
                "29 -[#707070]> 32 : 2. Validates credentials using\n" +
                "32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
                "32 <-[#707070]- 21 : 4. Returns user data to\n" +
                "29 <-[#707070]- 32 : 5. Returns true if the hashed password matches\n" +
                "17 <-[#707070]- 29 : 6. Sends back an authentication token to\n" +
                "@enduml", diagram.getDefinition());
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadThemes(workspace);
        workspace.getViews().getDeploymentViews().iterator().next().enableAutomaticLayout(AutomaticLayout.RankDirection.LeftRight, 300, 300);

        Collection<PlantUMLDiagram> diagrams = new StructurizrPlantUMLWriter().toPlantUMLDiagrams(workspace);
        assertEquals(1, diagrams.size());

        PlantUMLDiagram diagram = diagrams.stream().findFirst().get();
        assertEquals("@startuml(id=AmazonWebServicesDeployment)\n" +
                "title Spring PetClinic - Deployment - Default\n" +
                "caption An example deployment diagram.\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "left to right direction\n" +
                "skinparam rectangle<<11>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #693cc5\n" +
                "  BorderColor #693cc5\n" +
                "  roundCorner 20\n" +
                "}\n" +
                "skinparam node<<14>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #3b48cc\n" +
                "  BorderColor #3b48cc\n" +
                "}\n" +
                "skinparam node<<15>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #3b48cc\n" +
                "  BorderColor #3b48cc\n" +
                "}\n" +
                "skinparam database<<16>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #b2b2b2\n" +
                "}\n" +
                "skinparam node<<5>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #232f3e\n" +
                "  BorderColor #232f3e\n" +
                "}\n" +
                "skinparam node<<6>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #147eba\n" +
                "  BorderColor #147eba\n" +
                "}\n" +
                "skinparam node<<7>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #cc2264\n" +
                "  BorderColor #cc2264\n" +
                "}\n" +
                "skinparam node<<8>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #d86613\n" +
                "  BorderColor #d86613\n" +
                "}\n" +
                "skinparam rectangle<<9>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #b2b2b2\n" +
                "  roundCorner 20\n" +
                "}\n" +
                "skinparam rectangle<<10>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #693cc5\n" +
                "  BorderColor #693cc5\n" +
                "  roundCorner 20\n" +
                "}\n" +
                "node \"Amazon Web Services\\n[Deployment Node]\" <<5>> as 5 {\n" +
                "  node \"US-East-1\\n[Deployment Node]\" <<6>> as 6 {\n" +
                "    node \"Amazon RDS\\n[Deployment Node]\" <<14>> as 14 {\n" +
                "      node \"MySQL\\n[Deployment Node]\" <<15>> as 15 {\n" +
                "        database \"==Database\\n<size:10>[Container: Relational database schema]</size>\\n\\nStores information regarding the veterinarians, the clients, and their pets.\" <<16>> as 16\n" +
                "      }\n" +
                "    }\n" +
                "    node \"Autoscaling group\\n[Deployment Node]\" <<7>> as 7 {\n" +
                "      node \"Amazon EC2\\n[Deployment Node]\" <<8>> as 8 {\n" +
                "        rectangle \"==Web Application\\n<size:10>[Container: Java and Spring Boot]</size>\\n\\nAllows employees to view and manage information regarding the veterinarians, the clients, and their pets.\" <<9>> as 9\n" +
                "      }\n" +
                "    }\n" +
                "    rectangle \"==Elastic Load Balancer\\n<size:10>[Infrastructure Node]</size>\" <<11>> as 11\n" +
                "    rectangle \"==Route 53\\n<size:10>[Infrastructure Node]</size>\" <<10>> as 10\n" +
                "  }\n" +
                "}\n" +
                "11 .[#707070,thickness=2].> 9 : \"Forwards requests to\\n<size:8>[HTTPS]</size>\"\n" +
                "10 .[#707070,thickness=2].> 11 : \"Forwards requests to\\n<size:8>[HTTPS]</size>\"\n" +
                "9 .[#707070,thickness=2].> 16 : \"Reads from and writes to\\n<size:8>[JDBC/SSL]</size>\"\n" +
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
        new StructurizrPlantUMLWriter().write(viewAll, stringWriter);
        assertEquals("@startuml(id=all)\n" +
                "title Deployment - Default\n" +
                "caption description\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam node<<5>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<6>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<7>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "skinparam node<<8>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<9>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "node \"Deployment Node\\n[Deployment Node]\" <<5>> as 5 {\n" +
                "  node \"Child 1\\n[Deployment Node]\" <<6>> as 6 {\n" +
                "    rectangle \"==Container 1\\n<size:10>[Container: Technology]</size>\\n\\nDescription\" <<7>> as 7\n" +
                "  }\n" +
                "  node \"Child 2\\n[Deployment Node]\" <<8>> as 8 {\n" +
                "    rectangle \"==Container 2\\n<size:10>[Container: Technology]</size>\\n\\nDescription\" <<9>> as 9\n" +
                "  }\n" +
                "}\n" +
                "@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());

        stringWriter = new StringWriter();
        new StructurizrPlantUMLWriter().write(view1, stringWriter);
        assertEquals("@startuml(id=softwaresystem1)\n" +
                "title Software System 1 - Deployment - Default\n" +
                "caption description\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam node<<5>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam node<<6>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #888888\n" +
                "}\n" +
                "skinparam rectangle<<7>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "node \"Deployment Node\\n[Deployment Node]\" <<5>> as 5 {\n" +
                "  node \"Child 1\\n[Deployment Node]\" <<6>> as 6 {\n" +
                "    rectangle \"==Container 1\\n<size:10>[Container: Technology]</size>\\n\\nDescription\" <<7>> as 7\n" +
                "  }\n" +
                "}\n" +
                "@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());
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
        new StructurizrPlantUMLWriter().write(containerView, stringWriter);
        assertEquals("@startuml(id=Containers)\n" +
                "title Software System 1 - Containers\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
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
                "package \"Software System 1\\n[Software System]\" {\n" +
                "  rectangle \"==Container 1\\n<size:10>[Container]</size>\" <<2>> as 2\n" +
                "}\n" +
                "package \"Software System 2\\n[Software System]\" {\n" +
                "  rectangle \"==Container 2\\n<size:10>[Container]</size>\" <<4>> as 4\n" +
                "}\n" +
                "2 .[#707070,thickness=2].> 4 : \"Uses\"\n" +
                "@enduml", stringWriter.toString());
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
        new StructurizrPlantUMLWriter().write(componentView, stringWriter);
        assertEquals("@startuml(id=Components)\n" +
                "title Software System 1 - Container 1 - Components\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
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
                "package \"Container 1\\n[Container]\" {\n" +
                "  rectangle \"==Component 1\\n<size:10>[Component]</size>\" <<3>> as 3\n" +
                "}\n" +
                "package \"Container 2\\n[Container]\" {\n" +
                "  rectangle \"==Component 2\\n<size:10>[Component]</size>\" <<6>> as 6\n" +
                "}\n" +
                "3 .[#707070,thickness=2].> 6 : \"Uses\"\n" +
                "@enduml", stringWriter.toString());
    }

    @Test
    public void test_renderDiagramWithoutDiagramMetadata() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System");

        SystemLandscapeView view = workspace.getViews().createSystemLandscapeView("key", "Description");
        view.add(softwareSystem);

        StringWriter stringWriter = new StringWriter();
        StructurizrPlantUMLWriter plantUMLWriter = new StructurizrPlantUMLWriter();
        plantUMLWriter.setIncludeDiagramMetadata(false);
        plantUMLWriter.write(view, stringWriter);
        assertEquals("@startuml(id=key)\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "rectangle \"==Software System\\n<size:10>[Software System]</size>\" <<1>> as 1\n" +
                "@enduml", stringWriter.toString());
    }

    @Test
    public void test_renderDiagramWithDiagramMetadata() {
        Workspace workspace = new Workspace("Name", "Description");
        SoftwareSystem softwareSystem = workspace.getModel().addSoftwareSystem("Software System");

        SystemLandscapeView view = workspace.getViews().createSystemLandscapeView("key", "Description");
        view.add(softwareSystem);

        StringWriter stringWriter = new StringWriter();
        StructurizrPlantUMLWriter plantUMLWriter = new StructurizrPlantUMLWriter();
        plantUMLWriter.setIncludeDiagramMetadata(true);
        plantUMLWriter.write(view, stringWriter);
        assertEquals("@startuml(id=key)\n" +
                "title System Landscape\n" +
                "caption Description\n" +
                "\n" +
                "skinparam {\n" +
                "  shadowing false\n" +
                "  arrowFontSize 10\n" +
                "  defaultTextAlignment center\n" +
                "  wrapWidth 200\n" +
                "  maxMessageSize 100\n" +
                "}\n" +
                "hide stereotype\n" +
                "skinparam rectangle<<1>> {\n" +
                "  BackgroundColor #dddddd\n" +
                "  FontColor #000000\n" +
                "  BorderColor #9a9a9a\n" +
                "}\n" +
                "rectangle \"==Software System\\n<size:10>[Software System]</size>\" <<1>> as 1\n" +
                "@enduml", stringWriter.toString());
    }

}