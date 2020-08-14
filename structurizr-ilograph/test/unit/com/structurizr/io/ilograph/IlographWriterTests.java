package com.structurizr.io.ilograph;

import com.structurizr.Workspace;
import com.structurizr.util.ThemeUtils;
import com.structurizr.util.WorkspaceUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class IlographWriterTests {

    @Test
    public void test_BigBankPlcExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-36141-workspace.json"));
        IlographWriter ilographWriter = new IlographWriter();

        String definition = ilographWriter.toString(workspace);

        assertEquals("resources:\n" +
                "  - id: \"1\"\n" +
                "    name: \"Personal Banking Customer\"\n" +
                "    subtitle: \"[Person]\"\n" +
                "    description: \"A customer of the bank, with personal bank accounts.\"\n" +
                "    backgroundColor: \"#08427b\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"12\"\n" +
                "    name: \"Customer Service Staff\"\n" +
                "    subtitle: \"[Person]\"\n" +
                "    description: \"Customer service staff within the bank.\"\n" +
                "    backgroundColor: \"#999999\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"15\"\n" +
                "    name: \"Back Office Staff\"\n" +
                "    subtitle: \"[Person]\"\n" +
                "    description: \"Administration and support staff within the bank.\"\n" +
                "    backgroundColor: \"#999999\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "  - id: \"2\"\n" +
                "    name: \"Internet Banking System\"\n" +
                "    subtitle: \"[Software System]\"\n" +
                "    description: \"Allows customers to view information about their bank accounts, and make payments.\"\n" +
                "    backgroundColor: \"#1168bd\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "    children:\n" +
                "      - id: \"17\"\n" +
                "        name: \"Single-Page Application\"\n" +
                "        subtitle: \"[Container: JavaScript and Angular]\"\n" +
                "        description: \"Provides all of the Internet banking functionality to customers via their web browser.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "      - id: \"18\"\n" +
                "        name: \"Mobile App\"\n" +
                "        subtitle: \"[Container: Xamarin]\"\n" +
                "        description: \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "      - id: \"19\"\n" +
                "        name: \"Web Application\"\n" +
                "        subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "        description: \"Delivers the static content and the Internet banking single page application.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "      - id: \"20\"\n" +
                "        name: \"API Application\"\n" +
                "        subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "        description: \"Provides Internet banking functionality via a JSON/HTTPS API.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"29\"\n" +
                "            name: \"Sign In Controller\"\n" +
                "            subtitle: \"[Component: Spring MVC Rest Controller]\"\n" +
                "            description: \"Allows users to sign in to the Internet Banking System.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "          - id: \"30\"\n" +
                "            name: \"Accounts Summary Controller\"\n" +
                "            subtitle: \"[Component: Spring MVC Rest Controller]\"\n" +
                "            description: \"Provides customers with a summary of their bank accounts.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "          - id: \"31\"\n" +
                "            name: \"Reset Password Controller\"\n" +
                "            subtitle: \"[Component: Spring MVC Rest Controller]\"\n" +
                "            description: \"Allows users to reset their passwords with a single use URL.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "          - id: \"32\"\n" +
                "            name: \"Security Component\"\n" +
                "            subtitle: \"[Component: Spring Bean]\"\n" +
                "            description: \"Provides functionality related to signing in, changing passwords, etc.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "          - id: \"33\"\n" +
                "            name: \"Mainframe Banking System Facade\"\n" +
                "            subtitle: \"[Component: Spring Bean]\"\n" +
                "            description: \"A facade onto the mainframe banking system.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "          - id: \"34\"\n" +
                "            name: \"E-mail Component\"\n" +
                "            subtitle: \"[Component: Spring Bean]\"\n" +
                "            description: \"Sends e-mails to users.\"\n" +
                "            backgroundColor: \"#85bbf0\"\n" +
                "            color: \"#000000\"\n" +
                "\n" +
                "\n" +
                "      - id: \"21\"\n" +
                "        name: \"Database\"\n" +
                "        subtitle: \"[Container: Oracle Database Schema]\"\n" +
                "        description: \"Stores user registration information, hashed authentication credentials, access logs, etc.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "  - id: \"4\"\n" +
                "    name: \"Mainframe Banking System\"\n" +
                "    subtitle: \"[Software System]\"\n" +
                "    description: \"Stores all of the core banking information about customers, accounts, transactions, etc.\"\n" +
                "    backgroundColor: \"#999999\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "  - id: \"6\"\n" +
                "    name: \"E-mail System\"\n" +
                "    subtitle: \"[Software System]\"\n" +
                "    description: \"The internal Microsoft Exchange e-mail system.\"\n" +
                "    backgroundColor: \"#999999\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "  - id: \"9\"\n" +
                "    name: \"ATM\"\n" +
                "    subtitle: \"[Software System]\"\n" +
                "    description: \"Allows customers to withdraw cash.\"\n" +
                "    backgroundColor: \"#999999\"\n" +
                "    color: \"#ffffff\"\n" +
                "\n" +
                "\n" +
                "  - id: \"50\"\n" +
                "    name: \"Developer Laptop\"\n" +
                "    subtitle: \"[Deployment Node: Microsoft Windows 10 or Apple macOS]\"\n" +
                "    description: \"A developer laptop.\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "    - id: \"51\"\n" +
                "      name: \"Docker Container - Web Server\"\n" +
                "      subtitle: \"[Deployment Node: Docker]\"\n" +
                "      description: \"A Docker container.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"52\"\n" +
                "        name: \"Apache Tomcat\"\n" +
                "        subtitle: \"[Deployment Node: Apache Tomcat 8.x]\"\n" +
                "        description: \"An open source Java EE web server.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"53\"\n" +
                "            name: \"Web Application\"\n" +
                "            subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "            description: \"Delivers the static content and the Internet banking single page application.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "          - id: \"54\"\n" +
                "            name: \"API Application\"\n" +
                "            subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "            description: \"Provides Internet banking functionality via a JSON/HTTPS API.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"59\"\n" +
                "      name: \"Docker Container - Database Server\"\n" +
                "      subtitle: \"[Deployment Node: Docker]\"\n" +
                "      description: \"A Docker container.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"60\"\n" +
                "        name: \"Database Server\"\n" +
                "        subtitle: \"[Deployment Node: Oracle 12c]\"\n" +
                "        description: \"A development database.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"61\"\n" +
                "            name: \"Database\"\n" +
                "            subtitle: \"[Container: Oracle Database Schema]\"\n" +
                "            description: \"Stores user registration information, hashed authentication credentials, access logs, etc.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"63\"\n" +
                "      name: \"Web Browser\"\n" +
                "      subtitle: \"[Deployment Node: Chrome, Firefox, Safari, or Edge]\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "        - id: \"64\"\n" +
                "          name: \"Single-Page Application\"\n" +
                "          subtitle: \"[Container: JavaScript and Angular]\"\n" +
                "          description: \"Provides all of the Internet banking functionality to customers via their web browser.\"\n" +
                "          backgroundColor: \"#438dd5\"\n" +
                "          color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"55\"\n" +
                "    name: \"Big Bank plc\"\n" +
                "    subtitle: \"[Deployment Node: Big Bank plc data center]\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "    - id: \"56\"\n" +
                "      name: \"bigbank-dev001\"\n" +
                "      subtitle: \"[Deployment Node]\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "        - id: \"57\"\n" +
                "          name: \"Mainframe Banking System\"\n" +
                "          subtitle: \"[Software System]\"\n" +
                "          description: \"Stores all of the core banking information about customers, accounts, transactions, etc.\"\n" +
                "          backgroundColor: \"#999999\"\n" +
                "          color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"67\"\n" +
                "    name: \"Customer's mobile device\"\n" +
                "    subtitle: \"[Deployment Node: Apple iOS or Android]\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "      - id: \"68\"\n" +
                "        name: \"Mobile App\"\n" +
                "        subtitle: \"[Container: Xamarin]\"\n" +
                "        description: \"Provides a limited subset of the Internet banking functionality to customers via their mobile device.\"\n" +
                "        backgroundColor: \"#438dd5\"\n" +
                "        color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"69\"\n" +
                "    name: \"Customer's computer\"\n" +
                "    subtitle: \"[Deployment Node: Microsoft Windows or Apple macOS]\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "    - id: \"70\"\n" +
                "      name: \"Web Browser\"\n" +
                "      subtitle: \"[Deployment Node: Chrome, Firefox, Safari, or Edge]\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "        - id: \"71\"\n" +
                "          name: \"Single-Page Application\"\n" +
                "          subtitle: \"[Container: JavaScript and Angular]\"\n" +
                "          description: \"Provides all of the Internet banking functionality to customers via their web browser.\"\n" +
                "          backgroundColor: \"#438dd5\"\n" +
                "          color: \"#ffffff\"\n" +
                "\n" +
                "  - id: \"72\"\n" +
                "    name: \"Big Bank plc\"\n" +
                "    subtitle: \"[Deployment Node: Big Bank plc data center]\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "    - id: \"73\"\n" +
                "      name: \"bigbank-prod001\"\n" +
                "      subtitle: \"[Deployment Node]\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "        - id: \"74\"\n" +
                "          name: \"Mainframe Banking System\"\n" +
                "          subtitle: \"[Software System]\"\n" +
                "          description: \"Stores all of the core banking information about customers, accounts, transactions, etc.\"\n" +
                "          backgroundColor: \"#999999\"\n" +
                "          color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"75\"\n" +
                "      name: \"bigbank-web***\"\n" +
                "      subtitle: \"[Deployment Node: Ubuntu 16.04 LTS]\"\n" +
                "      description: \"A web server residing in the web server farm, accessed via F5 BIG-IP LTMs.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"76\"\n" +
                "        name: \"Apache Tomcat\"\n" +
                "        subtitle: \"[Deployment Node: Apache Tomcat 8.x]\"\n" +
                "        description: \"An open source Java EE web server.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"77\"\n" +
                "            name: \"Web Application\"\n" +
                "            subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "            description: \"Delivers the static content and the Internet banking single page application.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"79\"\n" +
                "      name: \"bigbank-api***\"\n" +
                "      subtitle: \"[Deployment Node: Ubuntu 16.04 LTS]\"\n" +
                "      description: \"A web server residing in the web server farm, accessed via F5 BIG-IP LTMs.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"80\"\n" +
                "        name: \"Apache Tomcat\"\n" +
                "        subtitle: \"[Deployment Node: Apache Tomcat 8.x]\"\n" +
                "        description: \"An open source Java EE web server.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"81\"\n" +
                "            name: \"API Application\"\n" +
                "            subtitle: \"[Container: Java and Spring MVC]\"\n" +
                "            description: \"Provides Internet banking functionality via a JSON/HTTPS API.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"85\"\n" +
                "      name: \"bigbank-db01\"\n" +
                "      subtitle: \"[Deployment Node: Ubuntu 16.04 LTS]\"\n" +
                "      description: \"The primary database server.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"86\"\n" +
                "        name: \"Oracle - Primary\"\n" +
                "        subtitle: \"[Deployment Node: Oracle 12c]\"\n" +
                "        description: \"The primary, live database server.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"87\"\n" +
                "            name: \"Database\"\n" +
                "            subtitle: \"[Container: Oracle Database Schema]\"\n" +
                "            description: \"Stores user registration information, hashed authentication credentials, access logs, etc.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "    - id: \"89\"\n" +
                "      name: \"bigbank-db02\"\n" +
                "      subtitle: \"[Deployment Node: Ubuntu 16.04 LTS]\"\n" +
                "      description: \"The secondary database server.\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#000000\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"90\"\n" +
                "        name: \"Oracle - Secondary\"\n" +
                "        subtitle: \"[Deployment Node: Oracle 12c]\"\n" +
                "        description: \"A secondary, standby database server, used for failover purposes only.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "        children:\n" +
                "          - id: \"91\"\n" +
                "            name: \"Database\"\n" +
                "            subtitle: \"[Container: Oracle Database Schema]\"\n" +
                "            description: \"Stores user registration information, hashed authentication credentials, access logs, etc.\"\n" +
                "            backgroundColor: \"#438dd5\"\n" +
                "            color: \"#ffffff\"\n" +
                "\n" +
                "perspectives:\n" +
                "  - name: Static Structure\n" +
                "    relations:\n" +
                "      - from: \"1\"\n" +
                "        to: \"9\"\n" +
                "        label: \"Withdraws cash using\"\n" +
                "\n" +
                "      - from: \"1\"\n" +
                "        to: \"12\"\n" +
                "        label: \"Asks questions to\"\n" +
                "        description: \"Telephone\"\n" +
                "\n" +
                "      - from: \"1\"\n" +
                "        to: \"2\"\n" +
                "        label: \"Views account balances, and makes payments using\"\n" +
                "\n" +
                "      - from: \"12\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"15\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"2\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Gets account information from, and makes payments using\"\n" +
                "\n" +
                "      - from: \"2\"\n" +
                "        to: \"6\"\n" +
                "        label: \"Sends e-mail using\"\n" +
                "\n" +
                "      - from: \"6\"\n" +
                "        to: \"1\"\n" +
                "        label: \"Sends e-mails to\"\n" +
                "\n" +
                "      - from: \"9\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"1\"\n" +
                "        to: \"19\"\n" +
                "        label: \"Visits bigbank.com/ib using\"\n" +
                "        description: \"HTTPS\"\n" +
                "\n" +
                "      - from: \"1\"\n" +
                "        to: \"17\"\n" +
                "        label: \"Views account balances, and makes payments using\"\n" +
                "\n" +
                "      - from: \"1\"\n" +
                "        to: \"18\"\n" +
                "        label: \"Views account balances, and makes payments using\"\n" +
                "\n" +
                "      - from: \"17\"\n" +
                "        to: \"20\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"18\"\n" +
                "        to: \"20\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"19\"\n" +
                "        to: \"17\"\n" +
                "        label: \"Delivers to the customer's web browser\"\n" +
                "\n" +
                "      - from: \"20\"\n" +
                "        to: \"21\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - from: \"20\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"XML/HTTPS\"\n" +
                "\n" +
                "      - from: \"20\"\n" +
                "        to: \"6\"\n" +
                "        label: \"Sends e-mail using\"\n" +
                "        description: \"SMTP\"\n" +
                "\n" +
                "      - from: \"17\"\n" +
                "        to: \"29\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"17\"\n" +
                "        to: \"31\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"17\"\n" +
                "        to: \"30\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"18\"\n" +
                "        to: \"29\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"18\"\n" +
                "        to: \"31\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"18\"\n" +
                "        to: \"30\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"29\"\n" +
                "        to: \"32\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"30\"\n" +
                "        to: \"33\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"31\"\n" +
                "        to: \"32\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"31\"\n" +
                "        to: \"34\"\n" +
                "        label: \"Uses\"\n" +
                "\n" +
                "      - from: \"32\"\n" +
                "        to: \"21\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - from: \"33\"\n" +
                "        to: \"4\"\n" +
                "        label: \"Uses\"\n" +
                "        description: \"XML/HTTPS\"\n" +
                "\n" +
                "      - from: \"34\"\n" +
                "        to: \"6\"\n" +
                "        label: \"Sends e-mail using\"\n" +
                "\n" +
                "  - name: Dynamic - API Application - Dynamic\n" +
                "    sequence:\n" +
                "      start: \"17\"\n" +
                "      steps:\n" +
                "      - to: \"29\"\n" +
                "        label: \"1. Submits credentials to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - to: \"32\"\n" +
                "        label: \"2. Validates credentials using\"\n" +
                "\n" +
                "      - to: \"21\"\n" +
                "        label: \"3. select * from users where username = ?\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - to: \"32\"\n" +
                "        label: \"4. Returns user data to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - to: \"29\"\n" +
                "        label: \"5. Returns true if the hashed password matches\"\n" +
                "\n" +
                "      - to: \"17\"\n" +
                "        label: \"6. Sends back an authentication token to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "  - name: Deployment - Development\n" +
                "    relations:\n" +
                "      - from: \"53\"\n" +
                "        to: \"64\"\n" +
                "        label: \"Delivers to the customer's web browser\"\n" +
                "\n" +
                "      - from: \"54\"\n" +
                "        to: \"57\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"XML/HTTPS\"\n" +
                "\n" +
                "      - from: \"54\"\n" +
                "        to: \"61\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - from: \"64\"\n" +
                "        to: \"54\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "  - name: Deployment - Live\n" +
                "    relations:\n" +
                "      - from: \"68\"\n" +
                "        to: \"81\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"71\"\n" +
                "        to: \"81\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"JSON/HTTPS\"\n" +
                "\n" +
                "      - from: \"77\"\n" +
                "        to: \"71\"\n" +
                "        label: \"Delivers to the customer's web browser\"\n" +
                "\n" +
                "      - from: \"81\"\n" +
                "        to: \"74\"\n" +
                "        label: \"Makes API calls to\"\n" +
                "        description: \"XML/HTTPS\"\n" +
                "\n" +
                "      - from: \"81\"\n" +
                "        to: \"87\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n" +
                "      - from: \"81\"\n" +
                "        to: \"91\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC\"\n" +
                "\n".replaceAll("\n", System.lineSeparator()), definition);
    }

    @Test
    public void test_AmazonWebServicesExample() throws Exception {
        Workspace workspace = WorkspaceUtils.loadWorkspaceFromJson(new File("./test/structurizr-54915-workspace.json"));
        ThemeUtils.loadStylesFromThemes(workspace);
        IlographWriter ilographWriter = new IlographWriter();

        String definition = ilographWriter.toString(workspace);

        assertEquals("resources:\n" +
                "\n" +
                "  - id: \"1\"\n" +
                "    name: \"Spring PetClinic\"\n" +
                "    subtitle: \"[Software System]\"\n" +
                "    description: \"Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.\"\n" +
                "    backgroundColor: \"#dddddd\"\n" +
                "    color: \"#000000\"\n" +
                "\n" +
                "    children:\n" +
                "      - id: \"2\"\n" +
                "        name: \"Web Application\"\n" +
                "        subtitle: \"[Container: Java and Spring Boot]\"\n" +
                "        description: \"Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "\n" +
                "      - id: \"3\"\n" +
                "        name: \"Database\"\n" +
                "        subtitle: \"[Container: Relational database schema]\"\n" +
                "        description: \"Stores information regarding the veterinarians, the clients, and their pets.\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#000000\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "  - id: \"5\"\n" +
                "    name: \"Amazon Web Services\"\n" +
                "    subtitle: \"[Deployment Node]\"\n" +
                "    backgroundColor: \"#ffffff\"\n" +
                "    color: \"#232f3e\"\n" +
                "\n" +
                "    children:\n" +
                "    - id: \"6\"\n" +
                "      name: \"US-East-1\"\n" +
                "      subtitle: \"[Deployment Node]\"\n" +
                "      backgroundColor: \"#ffffff\"\n" +
                "      color: \"#147eba\"\n" +
                "\n" +
                "      children:\n" +
                "      - id: \"14\"\n" +
                "        name: \"Amazon RDS\"\n" +
                "        subtitle: \"[Deployment Node]\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#3b48cc\"\n" +
                "\n" +
                "        children:\n" +
                "        - id: \"15\"\n" +
                "          name: \"MySQL\"\n" +
                "          subtitle: \"[Deployment Node]\"\n" +
                "          backgroundColor: \"#ffffff\"\n" +
                "          color: \"#3b48cc\"\n" +
                "\n" +
                "          children:\n" +
                "            - id: \"16\"\n" +
                "              name: \"Database\"\n" +
                "              subtitle: \"[Container: Relational database schema]\"\n" +
                "              description: \"Stores information regarding the veterinarians, the clients, and their pets.\"\n" +
                "              backgroundColor: \"#ffffff\"\n" +
                "              color: \"#000000\"\n" +
                "\n" +
                "      - id: \"7\"\n" +
                "        name: \"Autoscaling group\"\n" +
                "        subtitle: \"[Deployment Node]\"\n" +
                "        backgroundColor: \"#ffffff\"\n" +
                "        color: \"#cc2264\"\n" +
                "\n" +
                "        children:\n" +
                "        - id: \"8\"\n" +
                "          name: \"Amazon EC2\"\n" +
                "          subtitle: \"[Deployment Node]\"\n" +
                "          backgroundColor: \"#ffffff\"\n" +
                "          color: \"#d86613\"\n" +
                "\n" +
                "          children:\n" +
                "            - id: \"9\"\n" +
                "              name: \"Web Application\"\n" +
                "              subtitle: \"[Container: Java and Spring Boot]\"\n" +
                "              description: \"Allows employees to view and manage information regarding the veterinarians, the clients, and their pets.\"\n" +
                "              backgroundColor: \"#ffffff\"\n" +
                "              color: \"#000000\"\n" +
                "\n" +
                "        - id: \"10\"\n" +
                "          name: \"Route 53\"\n" +
                "          subtitle: \"[Infrastructure Node]\"\n" +
                "          backgroundColor: \"#ffffff\"\n" +
                "          color: \"#693cc5\"\n" +
                "\n" +
                "        - id: \"11\"\n" +
                "          name: \"Elastic Load Balancer\"\n" +
                "          subtitle: \"[Infrastructure Node]\"\n" +
                "          backgroundColor: \"#ffffff\"\n" +
                "          color: \"#693cc5\"\n" +
                "\n" +
                "perspectives:\n" +
                "  - name: Static Structure\n" +
                "    relations:\n" +
                "      - from: \"2\"\n" +
                "        to: \"3\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC/SSL\"\n" +
                "\n" +
                "  - name: Deployment - Default\n" +
                "    relations:\n" +
                "      - from: \"9\"\n" +
                "        to: \"16\"\n" +
                "        label: \"Reads from and writes to\"\n" +
                "        description: \"JDBC/SSL\"\n" +
                "\n" +
                "      - from: \"10\"\n" +
                "        to: \"11\"\n" +
                "        label: \"Forwards requests to\"\n" +
                "        description: \"HTTPS\"\n" +
                "\n" +
                "      - from: \"11\"\n" +
                "        to: \"9\"\n" +
                "        label: \"Forwards requests to\"\n" +
                "        description: \"HTTPS\"\n" +
                "\n".replaceAll("\n", System.lineSeparator()), definition);
    }

}