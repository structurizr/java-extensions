package com.structurizr.io.plantuml;

import com.structurizr.Workspace;
import com.structurizr.model.*;
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
	public void testBigBankPlcExample() throws Exception {
		Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
		workspace.getModel().getElements().stream().filter(e -> e.getTagsAsSet().contains("Database")).forEach(e -> e.addProperty(C4PlantUMLWriter.C4_ELEMENT_TYPE, "Db"));

		Collection<PlantUMLDiagram> diagrams = new C4PlantUMLWriter().toPlantUMLDiagrams(workspace);
		assertEquals(7, diagrams.size());

		PlantUMLDiagram diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemLandscape")).findFirst().get();
		assertEquals("@startuml(id=SystemLandscape)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
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
				"Rel_D(1, 12, \"Asks questions to\", Telephone)\n" +
				"Rel_D(1, 2, \"Views account balances, and makes payments using\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("SystemContext")).findFirst().get();
		assertEquals("@startuml(id=SystemContext)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
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
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
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
				"Rel_D(20, 21, \"Reads from and writes to\", JDBC)\n" +
				"Rel_D(20, 6, \"Sends e-mail using\", SMTP)\n" +
				"Rel_D(20, 4, \"Makes API calls to\", XML/HTTPS)\n" +
				"Rel_D(6, 1, \"Sends e-mails to\")\n" +
				"Rel_D(18, 20, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(1, 18, \"Views account balances, and makes payments using\")\n" +
				"Rel_D(1, 17, \"Views account balances, and makes payments using\")\n" +
				"Rel_D(1, 19, \"Visits bigbank.com/ib using\", HTTPS)\n" +
				"Rel_D(17, 20, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(19, 17, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("Components")).findFirst().get();
		assertEquals("@startuml(id=Components)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
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
				"Rel_D(33, 4, \"Uses\", XML/HTTPS)\n" +
				"Rel_D(18, 30, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(18, 31, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(18, 29, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(31, 34, \"Uses\")\n" +
				"Rel_D(31, 32, \"Uses\")\n" +
				"Rel_D(32, 21, \"Reads from and writes to\", JDBC)\n" +
				"Rel_D(29, 32, \"Uses\")\n" +
				"Rel_D(17, 30, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(17, 31, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(17, 29, \"Makes API calls to\", JSON/HTTPS)\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("SignIn")).findFirst().get();
		assertEquals("@startuml(id=SignIn)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
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
				"29 -[#707070]> 32 : 2. Calls isAuthenticated() on\n" +
				"32 -[#707070]> 21 : 3. select * from users where username = ?\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("DevelopmentDeployment")).findFirst().get();
		assertEquals("@startuml(id=DevelopmentDeployment)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - Deployment - Development\n" +
				"caption An example development deployment scenario for the Internet Banking System.\n" +
				"\n" +
				"node \"Developer Laptop\" <<Deployment Node: Microsoft Windows 10 or Apple macOS>> as 50 {\n" +
				"  node \"Docker Container - Database Server\" <<Deployment Node: Docker>> as 55 {\n" +
				"    node \"Database Server\" <<Deployment Node: Oracle 12c>> as 56 {\n" +
				"      ContainerDb(57, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"Docker Container - Web Server\" <<Deployment Node: Docker>> as 51 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 52 {\n" +
				"      Container(54, \"API Application\", \"Java and Spring MVC\", \"Provides Internet banking functionality via a JSON/HTTPS API.\")\n" +
				"      Container(53, \"Web Application\", \"Java and Spring MVC\", \"Delivers the static content and the Internet banking single page application.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 59 {\n" +
				"    Container(60, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"  }\n" +
				"}\n" +
				"Rel_D(54, 57, \"Reads from and writes to\", JDBC)\n" +
				"Rel_D(60, 54, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(53, 60, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());

		diagram = diagrams.stream().filter(md -> md.getKey().equals("LiveDeployment")).findFirst().get();
		assertEquals("@startuml(id=LiveDeployment)\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml\n" +
				"!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml\n" +
				"LAYOUT_WITH_LEGEND()\n" +
				"\n" +
				"title Internet Banking System - Deployment - Live\n" +
				"caption An example live deployment scenario for the Internet Banking System.\n" +
				"\n" +
				"node \"Big Bank plc\" <<Deployment Node: Big Bank plc data center>> as 68 {\n" +
				"  node \"bigbank-api*** (x8)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 73 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 74 {\n" +
				"      Container(75, \"API Application\", \"Java and Spring MVC\", \"Provides Internet banking functionality via a JSON/HTTPS API.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-db01\" <<Deployment Node: Ubuntu 16.04 LTS>> as 78 {\n" +
				"    node \"Oracle - Primary\" <<Deployment Node: Oracle 12c>> as 79 {\n" +
				"      ContainerDb(80, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-db02\" <<Deployment Node: Ubuntu 16.04 LTS>> as 82 {\n" +
				"    node \"Oracle - Secondary\" <<Deployment Node: Oracle 12c>> as 83 {\n" +
				"      ContainerDb(84, \"Database\", \"Oracle Database Schema\", \"Stores user registration information, hashed authentication credentials, access logs, etc.\")\n" +
				"    }\n" +
				"  }\n" +
				"  node \"bigbank-web*** (x4)\" <<Deployment Node: Ubuntu 16.04 LTS>> as 69 {\n" +
				"    node \"Apache Tomcat\" <<Deployment Node: Apache Tomcat 8.x>> as 70 {\n" +
				"      Container(71, \"Web Application\", \"Java and Spring MVC\", \"Delivers the static content and the Internet banking single page application.\")\n" +
				"    }\n" +
				"  }\n" +
				"}\n" +
				"node \"Customer's computer\" <<Deployment Node: Microsoft Windows or Apple macOS>> as 65 {\n" +
				"  node \"Web Browser\" <<Deployment Node: Chrome, Firefox, Safari, or Edge>> as 66 {\n" +
				"    Container(67, \"Single-Page Application\", \"JavaScript and Angular\", \"Provides all of the Internet banking functionality to customers via their web browser.\")\n" +
				"  }\n" +
				"}\n" +
				"node \"Customer's mobile device\" <<Deployment Node: Apple iOS or Android>> as 63 {\n" +
				"  Container(64, \"Mobile App\", \"Xamarin\", \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\")\n" +
				"}\n" +
				"Rel_D(75, 80, \"Reads from and writes to\", JDBC)\n" +
				"Rel_D(75, 84, \"Reads from and writes to\", JDBC)\n" +
				"Rel_D(64, 75, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(79, 83, \"Replicates data to\")\n" +
				"Rel_D(67, 75, \"Makes API calls to\", JSON/HTTPS)\n" +
				"Rel_D(71, 67, \"Delivers to the customer's web browser\")\n" +
				"@enduml", diagram.getDefinition());
	}

	public static class ValidateURLCanonicalizer {

	    @Test
	    public void test_can_validate_a_raw_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/")));
	    }

	    @Test
	    public void test_can_validate_a_github_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/RicardoNiepel/C4-PlantUML/tree/master"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/")));
	    }

	    @Test
	    public void test_can_validate_a_github_branchless_url() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.canonicalizeUrl("https://github.com/RicardoNiepel/C4-PlantUML"), 
	    			Is.is(new URL("https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/")));
	    }
	}

	public static class ValidateLibraryInferer {

	    @Test
	    public void test_can_build_the_corect_library() throws Exception {
	    	Assert.assertThat(C4PlantUMLWriter.inferC4PlantUMLLibraryFrom("https://github.com/RicardoNiepel/C4-PlantUML"),
	    					hasItems(
	    							"https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4.puml",
	    							"https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Context.puml",
	    							"https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Container.puml",
	    							"https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml"
	    							)
	    			);
	    }

	}

}