package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.util.WorkspaceUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class StructurizrPlantUMLWriterTests {

    @Test
    public void testBigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));

        Collection<PlantUMLDiagram> diagrams = new StructurizrPlantUMLWriter().toPlantUMLDiagrams(workspace);
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
                "  BorderColor #052E56\n" +
                "}\n" +
                "skinparam rectangle<<12>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<2>> {\n" +
                "  BackgroundColor #1168bd\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #0B4884\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<15>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<9>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
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
                "9 .[#707070].> 4 : \"Uses\"\n" +
                "15 .[#707070].> 4 : \"Uses\"\n" +
                "12 .[#707070].> 4 : \"Uses\"\n" +
                "6 .[#707070].> 1 : \"Sends e-mails to\"\n" +
                "2 .[#707070].> 6 : \"Sends e-mail using\"\n" +
                "2 .[#707070].> 4 : \"Gets account information from, and makes payments using\"\n" +
                "1 .[#707070].> 9 : \"Withdraws cash using\"\n" +
                "1 .[#707070].> 12 : \"Asks questions to\\n<size:8>[Telephone]</size>\"\n" +
                "1 .[#707070].> 2 : \"Views account balances, and makes payments using\"\n" +
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
                "  BorderColor #052E56\n" +
                "}\n" +
                "skinparam rectangle<<2>> {\n" +
                "  BackgroundColor #1168bd\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #0B4884\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "rectangle \"==Personal Banking Customer\\n<size:10>[Person]</size>\\n\\nA customer of the bank, with personal bank accounts.\" <<1>> as 1\n" +
                "  rectangle \"==E-mail System\\n<size:10>[Software System]</size>\\n\\nThe internal Microsoft Exchange e-mail system.\" <<6>> as 6\n" +
                "  rectangle \"==Internet Banking System\\n<size:10>[Software System]</size>\\n\\nAllows customers to view information about their bank accounts, and make payments.\" <<2>> as 2\n" +
                "  rectangle \"==Mainframe Banking System\\n<size:10>[Software System]</size>\\n\\nStores all of the core banking information about customers, accounts, transactions, etc.\" <<4>> as 4\n" +
                "6 .[#707070].> 1 : \"Sends e-mails to\"\n" +
                "2 .[#707070].> 6 : \"Sends e-mail using\"\n" +
                "2 .[#707070].> 4 : \"Gets account information from, and makes payments using\"\n" +
                "1 .[#707070].> 2 : \"Views account balances, and makes payments using\"\n" +
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
                "  BorderColor #052E56\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<18>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<19>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<20>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
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
                "20 .[#707070].> 21 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "20 .[#707070].> 6 : \"Sends e-mail using\\n<size:8>[SMTP]</size>\"\n" +
                "20 .[#707070].> 4 : \"Makes API calls to\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "6 .[#707070].> 1 : \"Sends e-mails to\"\n" +
                "18 .[#707070].> 20 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "1 .[#707070].> 18 : \"Views account balances, and makes payments using\"\n" +
                "1 .[#707070].> 17 : \"Views account balances, and makes payments using\"\n" +
                "1 .[#707070].> 19 : \"Visits bigbank.com/ib using\\n<size:8>[HTTPS]</size>\"\n" +
                "17 .[#707070].> 20 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "19 .[#707070].> 17 : \"Delivers to the customer's web browser\"\n" +
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
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam rectangle<<34>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam rectangle<<4>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<17>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<6>> {\n" +
                "  BackgroundColor #999999\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #6B6B6B\n" +
                "}\n" +
                "skinparam rectangle<<18>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<29>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam rectangle<<30>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam rectangle<<31>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<32>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
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
                "30 .[#707070].> 33 : \"Uses\"\n" +
                "34 .[#707070].> 6 : \"Sends e-mail using\"\n" +
                "33 .[#707070].> 4 : \"Uses\\n<size:8>[XML/HTTPS]</size>\"\n" +
                "18 .[#707070].> 30 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "18 .[#707070].> 31 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "18 .[#707070].> 29 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "31 .[#707070].> 34 : \"Uses\"\n" +
                "31 .[#707070].> 32 : \"Uses\"\n" +
                "32 .[#707070].> 21 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "29 .[#707070].> 32 : \"Uses\"\n" +
                "17 .[#707070].> 30 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "17 .[#707070].> 31 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "17 .[#707070].> 29 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
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
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<29>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam rectangle<<32>> {\n" +
                "  BackgroundColor #85bbf0\n" +
                "  FontColor #000000\n" +
                "  BorderColor #5D82A8\n" +
                "}\n" +
                "skinparam database<<21>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "left to right direction\n" +
                "\n" +
                "rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<17>> as 17\n" +
                "rectangle \"==Sign In Controller\\n<size:10>[Component: Spring MVC Rest Controller]</size>\\n\\nAllows users to sign in to the Internet Banking System.\" <<29>> as 29\n" +
                "rectangle \"==Security Component\\n<size:10>[Component: Spring Bean]</size>\\n\\nProvides functionality related to signing in, changing passwords, etc.\" <<32>> as 32\n" +
                "database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<21>> as 21\n" +
                "17 .[#707070].> 29 : \"1. Submits credentials to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "29 .[#707070].> 32 : \"2. Calls isAuthenticated() on\"\n" +
                "32 .[#707070].> 21 : \"3. select * from users where username = ?\\n<size:8>[JDBC]</size>\"\n" +
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
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<56>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam database<<57>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<59>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam rectangle<<60>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<50>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<51>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<52>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam rectangle<<53>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<54>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "node \"Developer Laptop\\n[Deployment Node: Microsoft Windows 10 or Apple macOS]\" <<50>> as 50 {\n" +
                "  node \"Docker Container - Database Server\\n[Deployment Node: Docker]\" <<55>> as 55 {\n" +
                "    node \"Database Server\\n[Deployment Node: Oracle 12c]\" <<56>> as 56 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<57>> as 57\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Docker Container - Web Server\\n[Deployment Node: Docker]\" <<51>> as 51 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<52>> as 52 {\n" +
                "      rectangle \"==API Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nProvides Internet banking functionality via a JSON/HTTPS API.\" <<54>> as 54\n" +
                "      rectangle \"==Web Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nDelivers the static content and the Internet banking single page application.\" <<53>> as 53\n" +
                "    }\n" +
                "  }\n" +
                "  node \"Web Browser\\n[Deployment Node: Chrome, Firefox, Safari, or Edge]\" <<59>> as 59 {\n" +
                "    rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<60>> as 60\n" +
                "  }\n" +
                "}\n" +
                "54 .[#707070].> 57 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "60 .[#707070].> 54 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "53 .[#707070].> 60 : \"Delivers to the customer's web browser\"\n" +
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
                "skinparam node<<66>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<78>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam rectangle<<67>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<79>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<68>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<69>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam database<<80>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<70>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam rectangle<<71>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<82>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<83>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam database<<84>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<73>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<74>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam node<<63>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "skinparam rectangle<<75>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam rectangle<<64>> {\n" +
                "  BackgroundColor #438dd5\n" +
                "  FontColor #ffffff\n" +
                "  BorderColor #2E6295\n" +
                "}\n" +
                "skinparam node<<65>> {\n" +
                "  BackgroundColor #ffffff\n" +
                "  FontColor #000000\n" +
                "  BorderColor #000000\n" +
                "}\n" +
                "node \"Big Bank plc\\n[Deployment Node: Big Bank plc data center]\" <<68>> as 68 {\n" +
                "  node \"bigbank-api*** (x8)\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<73>> as 73 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<74>> as 74 {\n" +
                "      rectangle \"==API Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nProvides Internet banking functionality via a JSON/HTTPS API.\" <<75>> as 75\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db01\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<78>> as 78 {\n" +
                "    node \"Oracle - Primary\\n[Deployment Node: Oracle 12c]\" <<79>> as 79 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<80>> as 80\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-db02\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<82>> as 82 {\n" +
                "    node \"Oracle - Secondary\\n[Deployment Node: Oracle 12c]\" <<83>> as 83 {\n" +
                "      database \"==Database\\n<size:10>[Container: Oracle Database Schema]</size>\\n\\nStores user registration information, hashed authentication credentials, access logs, etc.\" <<84>> as 84\n" +
                "    }\n" +
                "  }\n" +
                "  node \"bigbank-web*** (x4)\\n[Deployment Node: Ubuntu 16.04 LTS]\" <<69>> as 69 {\n" +
                "    node \"Apache Tomcat\\n[Deployment Node: Apache Tomcat 8.x]\" <<70>> as 70 {\n" +
                "      rectangle \"==Web Application\\n<size:10>[Container: Java and Spring MVC]</size>\\n\\nDelivers the static content and the Internet banking single page application.\" <<71>> as 71\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's computer\\n[Deployment Node: Microsoft Windows or Apple macOS]\" <<65>> as 65 {\n" +
                "  node \"Web Browser\\n[Deployment Node: Chrome, Firefox, Safari, or Edge]\" <<66>> as 66 {\n" +
                "    rectangle \"==Single-Page Application\\n<size:10>[Container: JavaScript and Angular]</size>\\n\\nProvides all of the Internet banking functionality to customers via their web browser.\" <<67>> as 67\n" +
                "  }\n" +
                "}\n" +
                "node \"Customer's mobile device\\n[Deployment Node: Apple iOS or Android]\" <<63>> as 63 {\n" +
                "  rectangle \"==Mobile App\\n<size:10>[Container: Xamarin]</size>\\n\\nProvides a limited subset of the Internet banking functionality to customers via their mobile device.\" <<64>> as 64\n" +
                "}\n" +
                "75 .[#707070].> 80 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "75 .[#707070].> 84 : \"Reads from and writes to\\n<size:8>[JDBC]</size>\"\n" +
                "64 .[#707070].> 75 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "79 .[#707070].> 83 : \"Replicates data to\"\n" +
                "67 .[#707070].> 75 : \"Makes API calls to\\n<size:8>[JSON/HTTPS]</size>\"\n" +
                "71 .[#707070].> 67 : \"Delivers to the customer's web browser\"\n" +
                "@enduml", diagram.getDefinition());
    }

}