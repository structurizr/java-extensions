![Structurizr](docs/images/structurizr-banner.png)

# Structurizr for Java extensions

This GitHub repository is a collection of extensions for the [Structurizr for Java](https://github.com/structurizr/java) library, and supported by the community.

## Binaries
You can either [build from source](docs/building.md), or use the binaries hosted on [Maven Central](https://repo1.maven.org/maven2/com/structurizr/):

Name                                                  | Description
----------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------
com.structurizr:structurizr-analysis:1.3.5            | Provides analysis capabilities, using reflection on compiled bytecode to find components.
com.structurizr:structurizr-annotations:1.3.5         | A very small, standalone, library that allows you to add software architecture hints into your own code.
com.structurizr:structurizr-graphviz:1.4.0            | Applies the Graphviz automatic layout algorithm to the views in a Structurizr workspace.
com.structurizr:structurizr-spring:1.3.5              | Extends structurizr-analysis to help find Spring components that correspond to Java types annotated ```@Controller```, ```@RestController```, ```@Component```, ```@Service``` and ```@Repository```, plus those that extend ```JpaRepository```.

* Extracting software architecture information from code
    * [Component finder](docs/component-finder.md)
    * [Structurizr annotations](docs/structurizr-annotations.md)
    * [Type matchers](docs/type-matchers.md)
    * [Spring component finder strategies](docs/spring-component-finder-strategies.md)
    * [Supplementing the model from source code](docs/supplementing-from-source-code.md)
    * [Components and supporting types](docs/supporting-types.md)
    * [The Spring PetClinic example](docs/spring-petclinic.md)

* Related projects
    * [structurizr-core](https://github.com/structurizr/java): Structurizr for Java core library
    * [structurizr-client](https://github.com/structurizr/java): Structurizr for Java client library for Structurizr web API
    * [structurizr-dsl](https://github.com/structurizr/dsl): Structurizr DSL parser
    * [structurizr-export](https://github.com/structurizr/export): Export workspaces and views to other formats (PlantUML, Mermaid, etc)
    * [structurizr-documentation](https://github.com/structurizr/documentation): Import Markdown/AsciiDoc documentation and ADRs into a Structurizr workspace
    * [java-quickstart](https://github.com/structurizr/java-quickstart): A starting point for using Structurizr for Java
    
![Java CI with Gradle](https://github.com/structurizr/java-extensions/workflows/Java%20CI%20with%20Gradle/badge.svg)