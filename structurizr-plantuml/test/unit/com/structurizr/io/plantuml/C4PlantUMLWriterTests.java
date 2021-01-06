package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.ThemeUtils;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.*;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import com.structurizr.io.plantuml.*;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class C4PlantUMLWriterTests {

	@Test
	public void test_BigBankPlcExample() throws Exception {
		Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
		workspace.getModel().getElements().stream().filter(e -> e.getTagsAsSet().contains("Database")).forEach(e -> e.addProperty(C4PlantUMLWriter.C4_ELEMENT_TYPE, "Db"));
		C4PlantUMLWriter c4PlantUMLWriter = new C4PlantUMLWriter();

		Collection<PlantUMLDiagram> diagrams = c4PlantUMLWriter.toPlantUMLDiagrams(workspace);
		assertEquals(7, diagrams.size());

		PlantUMLDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
		assertEquals("@startuml(id=SystemLandscape)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title System Landscape for Big Bank plc\n" +
				"caption The system landscape diagram for Big Bank plc.\n" +
				"\n" +
				"Person_Ext(1, \"Personal Banking Customer\", \"A customer of the bank, with personal bank accounts.\")\n" +
				"package \"Big Bank plc\" {\n" +
				"  Person(15, \"Back Office Staff\", \"Administration and support staff within the bank.\")\n" +
				"  Person(12, \"Customer Service Staff\", \"Customer service staff within the bank.\")\n" +
				"  System(9, \"ATM\", \"Allows customers to withdraw cash.\")\n" +
				"  System(6, \"E-mail System\", \"The internal Microsoft Exchange e-mail system.\")\n" +
				"  System(2, \"Internet Banking System\", \"Allows customers to view information about their bank accounts, and make payments.\")\n" +
				"  System(4, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"}\n" +
				"Rel_D(9, 4, \"Uses\")\n" +
				"Rel_D(15, 4, \"Uses\")\n" +
				"Rel_D(12, 4, \"Uses\")\n" +
				"Rel_D(6, 1, \"Sends e-mails to\")\n" +
				"Rel_D(2, 6, \"Sends e-mail using\")\n" +
				"Rel_D(2, 4, \"Gets account information from, and makes payments using\")\n" +
				"Rel_D(1, 9, \"Withdraws cash using\")\n" +
				"Rel_D(1, 12, \"Asks questions to\", \"Telephone\")\n" +
				"Rel_D(1, 2, \"Views account balances, and makes payments using\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
		assertEquals("@startuml(id=SystemContext)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - System Context\n" +
				"caption The system context diagram for the Internet Banking System.\n" +
				"\n" +
				"Person_Ext(1, \"Personal Banking Customer\", \"A customer of the bank, with personal bank accounts.\")\n" +
				"  System(6, \"E-mail System\", \"The internal Microsoft Exchange e-mail system.\")\n" +
				"  System(2, \"Internet Banking System\", \"Allows customers to view information about their bank accounts, and make payments.\")\n" +
				"  System(4, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"Rel_D(6, 1, \"Sends e-mails to\")\n" +
				"Rel_D(2, 6, \"Sends e-mail using\")\n" +
				"Rel_D(2, 4, \"Gets account information from, and makes payments using\")\n" +
				"Rel_D(1, 2, \"Views account balances, and makes payments using\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("Containers")).findFirst().get();
		assertEquals("@startuml(id=Containers)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - Containers\n" +
				"caption The container diagram for the Internet Banking System.\n" +
				"\n" +
				"System(6, \"E-mail System\", \"The internal Microsoft Exchange e-mail system.\")\n" +
				"System(4, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"Person_Ext(1, \"Personal Banking Customer\", \"A customer of the bank, with personal bank accounts.\")\n" +
				"System_Boundary(2_boundary, Internet Banking System) {\n" +
				"  Container(20, \"API Application\", \"Java and Spring MVC\", \"Provides Internet banking functionality via a JSON/HTTPS API.\")\n" +
				"  ContainerDb(21, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"  Container(18, \"Mobile App\", \"Xamarin\", \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\")\n" +
				"  Container(17, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"  Container(19, \"Web Application\", \"Java and Spring MVC\", \"Delivers the static content and the Internet banking single page application.\")\n" +
				"}\n" +
				"Rel_D(20, 21, \"Reads from and writes to\", \"JDBC\")\n" +
				"Rel_D(20, 6, \"Sends e-mail using\", \"SMTP\")\n" +
				"Rel_D(20, 4, \"Makes API calls to\", \"XML/HTTPS\")\n" +
				"Rel_D(6, 1, \"Sends e-mails to\")\n" +
				"Rel_D(18, 20, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(1, 18, \"Views account balances, and makes payments using\")\n" +
				"Rel_D(1, 17, \"Views account balances, and makes payments using\")\n" +
				"Rel_D(1, 19, \"Visits bigbank.com/ib using\", \"HTTPS\")\n" +
				"Rel_D(17, 20, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(19, 17, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
		assertEquals("@startuml(id=Components)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - API Application - Components\n" +
				"caption The component diagram for the API Application.\n" +
				"\n" +
				"ContainerDb(21, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"System(6, \"E-mail System\", \"The internal Microsoft Exchange e-mail system.\")\n" +
				"System(4, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"Container(18, \"Mobile App\", \"Xamarin\", \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\")\n" +
				"Container(17, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"Container_Boundary(20_boundary, API Application) {\n" +
				"  Component(30, \"Accounts Summary Controller\", \"Spring MVC Rest Controller\", \"Provides customers with a summary of their bank accounts.\")\n" +
				"  Component(34, \"E-mail Component\", \"Spring Bean\", \"Sends e-mails to users.\")\n" +
				"  Component(33, \"Mainframe Banking System Facade\", \"Spring Bean\", \"A facade onto the mainframe banking system.\")\n" +
				"  Component(31, \"Reset Password Controller\", \"Spring MVC Rest Controller\", \"Allows users to reset their passwords with a single use URL.\")\n" +
				"  Component(32, \"Security Component\", \"Spring Bean\", \"Provides functionality related to signing in, changing passwords, etc.\")\n" +
				"  Component(29, \"Sign In Controller\", \"Spring MVC Rest Controller\", \"Allows users to sign in to the Internet Banking System.\")\n" +
				"}\n" +
				"Rel_D(30, 33, \"Uses\")\n" +
				"Rel_D(34, 6, \"Sends e-mail using\")\n" +
				"Rel_D(33, 4, \"Uses\", \"XML/HTTPS\")\n" +
				"Rel_D(18, 30, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(18, 31, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(18, 29, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(31, 34, \"Uses\")\n" +
				"Rel_D(31, 32, \"Uses\")\n" +
				"Rel_D(32, 21, \"Reads from and writes to\", \"JDBC\")\n" +
				"Rel_D(29, 32, \"Uses\")\n" +
				"Rel_D(17, 30, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(17, 31, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(17, 29, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
		assertEquals("@startuml(id=SignIn)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title API Application - Dynamic\n" +
				"caption Summarises how the sign in feature works in the single-page application.\n" +
				"\n" +
				"Container(17, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"Component(29, \"Sign In Controller\", \"Spring MVC Rest Controller\", \"Allows users to sign in to the Internet Banking System.\")\n" +
				"Component(32, \"Security Component\", \"Spring Bean\", \"Provides functionality related to signing in, changing passwords, etc.\")\n" +
				"ContainerDb(21, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"17 -[#707070]> 29 : 1. Submits credentials to\n" +
				"29 -[#707070]> 32 : 2. Validates credentials using\n" +
				"32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
				"21 -[#707070]-> 32 : 4. Returns user data to\n" +
				"32 -[#707070]-> 29 : 5. Returns true if the hashed password matches\n" +
				"29 -[#707070]-> 17 : 6. Sends back an authentication token to\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
		assertEquals("@startuml(id=DevelopmentDeployment)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - Deployment - Development\n" +
				"caption An example development deployment scenario for the Internet Banking System.\n" +
				"\n" +
				"node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 55 {\n" +
				"  node \"bigbank-dev001\" <<Deployment Node>> as 56 {\n" +
				"    System(57, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"  }\n" +
				"}\n" +
				"node \"Developer Laptop\" <<Deployment Node: Microsoft Windows 10 or Apple macOS>> as 50 {\n" +
				"  node \"Docker Container - Database Server\" <<Deployment Node: Docker>> as 59 {\n" +
				"    node \"Database Server\" <<Deployment Node: Oracle 12c>> as 60 {\n" +
				"      ContainerDb(61, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"Docker Container - Web Server\" <<Deployment Node: Docker>> as 51 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 52 {\n" +
				"      Container(54, \"API Application\", \"Java and Spring MVC\", \"Provides Internet banking functionality via a JSON/HTTPS API.\")\n" +
				"      Container(53, \"Web Application\", \"Java and Spring MVC\", \"Delivers the static content and the Internet banking single page application.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 63 {\n" +
				"    Container(64, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"  }\n" +
				"}\n" +
				"Rel_D(54, 61, \"Reads from and writes to\", \"JDBC\")\n" +
				"Rel_D(54, 57, \"Makes API calls to\", \"XML/HTTPS\")\n" +
				"Rel_D(64, 54, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(53, 64, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
		assertEquals("@startuml(id=LiveDeployment)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - Deployment - Live\n" +
				"caption An example live deployment scenario for the Internet Banking System.\n" +
				"\n" +
				"node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 72 {\n" +
				"  node \"bigbank-api*** (x8)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 79 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 80 {\n" +
				"      Container(81, \"API Application\", \"Java and Spring MVC\", \"Provides Internet banking functionality via a JSON/HTTPS API.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-db01\" <<Deployment Node: Ubuntu 16.04 LTS>> as 85 {\n" +
				"    node \"Oracle - Primary\" <<Deployment Node: Oracle 12c>> as 86 {\n" +
				"      ContainerDb(87, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-db02\" <<Deployment Node: Ubuntu 16.04 LTS>> as 89 {\n" +
				"    node \"Oracle - Secondary\" <<Deployment Node: Oracle 12c>> as 90 {\n" +
				"      ContainerDb(91, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-prod001\" <<Deployment Node>> as 73 {\n" +
				"    System(74, \"Mainframe Banking System\", \"Stores all of the core banking information about customers, accounts, transactions, etc.\")\n" +
				"  }\n" +
				"  node \"bigbank-web*** (x4)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 75 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 76 {\n" +
				"      Container(77, \"Web Application\", \"Java and Spring MVC\", \"Delivers the static content and the Internet banking single page application.\")\n" +
				"    }\n" +
				"  }\n" +
				"}\n" +
				"node \"Customer's computer\" <<Deployment Node: Microsoft Windows or Apple macOS>> as 69 {\n" +
				"  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 70 {\n" +
				"    Container(71, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"  }\n" +
				"}\n" +
				"node \"Customer's mobile device\" <<Deployment Node: Apple iOS or Android>> as 67 {\n" +
				"  Container(68, \"Mobile App\", \"Xamarin\", \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\")\n" +
				"}\n" +
				"Rel_D(81, 91, \"Reads from and writes to\", \"JDBC\")\n" +
				"Rel_D(81, 87, \"Reads from and writes to\", \"JDBC\")\n" +
				"Rel_D(81, 74, \"Makes API calls to\", \"XML/HTTPS\")\n" +
				"Rel_D(68, 81, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(86, 90, \"Replicates data to\")\n" +
				"Rel_D(71, 81, \"Makes API calls to\", \"JSON/HTTPS\")\n" +
				"Rel_D(77, 71, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());

		c4PlantUMLWriter.setUseSequenceDiagrams(true);
		diagrams = c4PlantUMLWriter.toPlantUMLDiagrams(workspace);

		diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
		assertEquals("@startuml(id=SignIn)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title API Application - Dynamic\n" +
				"caption Summarises how the sign in feature works in the single-page application.\n" +
				"\n" +
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

		Collection<PlantUMLDiagram> diagrams = new C4PlantUMLWriter().toPlantUMLDiagrams(workspace);
		assertEquals(1, diagrams.size());

		PlantUMLDiagram diagram = diagrams.stream().findFirst().get();
		assertEquals("@startuml(id=AmazonWebServicesDeployment)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Spring PetClinic - Deployment - Default\n" +
				"caption An example deployment diagram.\n" +
				"\n" +
				"node \"Amazon Web Services\" <<Deployment Node>> as 5 {\n" +
				"  node \"US-East-1\" <<Deployment Node>> as 6 {\n" +
				"    node \"Amazon RDS\" <<Deployment Node>> as 14 {\n" +
				"      node \"MySQL\" <<Deployment Node>> as 15 {\n" +
				"        Container(16, \"Database\", \"Relational database schema\", \"Stores information regarding the veterinarians, the clients, and their pets.\")\n" +
				"      }\n" +
				"    }\n" +
				"    node \"Autoscaling group\" <<Deployment Node>> as 7 {\n" +
				"      node \"Amazon EC2\" <<Deployment Node>> as 8 {\n" +
				"        Container(9, \"Web Application\", \"Java and Spring Boot\", \"Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.\")\n" +
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
				"Rel_D(11, 9, \"Forwards requests to\", \"HTTPS\")\n" +
				"Rel_D(10, 11, \"Forwards requests to\", \"HTTPS\")\n" +
				"Rel_D(9, 16, \"Reads from and writes to\", \"JDBC/SSL\")\n" +
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
		new C4PlantUMLWriter().write(viewAll, stringWriter);
		assertEquals("@startuml(id=all)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Deployment - Default\n" +
				"caption description\n" +
				"\n" +
				"node \"Deployment Node\" <<Deployment Node>> as 5 {\n" +
				"  node \"Child 1\" <<Deployment Node>> as 6 {\n" +
				"    Container(7, \"Container 1\", \"Technology\", \"Description\")\n" +
				"  }\n" +
				"  node \"Child 2\" <<Deployment Node>> as 8 {\n" +
				"    Container(9, \"Container 2\", \"Technology\", \"Description\")\n" +
				"  }\n" +
				"}\n" +
				"@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());

		stringWriter = new StringWriter();
		new C4PlantUMLWriter().write(view1, stringWriter);
		assertEquals("@startuml(id=softwaresystem1)\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Software System 1 - Deployment - Default\n" +
				"caption description\n" +
				"\n" +
				"node \"Deployment Node\" <<Deployment Node>> as 5 {\n" +
				"  node \"Child 1\" <<Deployment Node>> as 6 {\n" +
				"    Container(7, \"Container 1\", \"Technology\", \"Description\")\n" +
				"  }\n" +
				"}\n" +
				"@enduml".replaceAll("\n", System.lineSeparator()), stringWriter.toString());
	}

	public static class ValidateURLCanonicalizer {

	    @Test
	    public void test_can_validate_a_raw_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }

	    @Test
	    public void test_can_validate_a_github_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/plantuml-stdlib/C4-PlantUML/tree/master"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }

	    @Test
	    public void test_can_validate_a_github_branchless_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/plantuml-stdlib/C4-PlantUML"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/")));
	    }
	}

	public static class ValidateLibraryInferer {

	    @Test
	    public void test_can_build_the_corect_library() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.inferC4PlantUMLLibraryFrom("https://github.com/plantuml-stdlib/C4-PlantUML"),
	    					hasItems(
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml",
	    							"https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml"
	    							)
	    			);
	    }

	}

}