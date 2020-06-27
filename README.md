![Structurizr](docs/images/structurizr-banner.png)

# Structurizr for Java extensions

This GitHub repository is a collection of extensions for the [Structurizr for Java](https://github.com/structurizr/java) library, and supported by the community.

## Binaries
You can either [build from source](docs/building.md), or use the binaries hosted on [Maven Central](https://repo1.maven.org/maven2/com/structurizr/):

Name                                                  | Description
----------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------
com.structurizr:structurizr-adr-tools:1.3.6 | Imports architecture decision records (ADRs) from the adr-tools tooling.
com.structurizr:structurizr-analysis:1.3.5 | Provides analysis capabilities, using reflection on compiled bytecode to find components.
com.structurizr:structurizr-annotations:1.3.5 | A very small, standalone, library that allows you to add software architecture hints into your own code.
com.structurizr:structurizr-dot:1.3.5 | Provides the ability to export view definitions to a DOT file, so they can be rendered with graphviz.
com.structurizr:structurizr-graphviz:1.3.7 | Applies the Graphviz automatic layout algorithm to the views in a Structurizr workspace.
com.structurizr:structurizr-mermaid:1.3.6  | Provides the ability to export view definitions to Mermaid diagram definitions.
com.structurizr:structurizr-plantuml:1.3.8 | Provides the ability to export view definitions to PlantUML diagram definitions.
com.structurizr:structurizr-spring:1.3.5 | Extends structurizr-analysis to help find Spring components that correspond to Java types annotated ```@Controller```, ```@RestController```, ```@Component```, ```@Service``` and ```@Repository```, plus those that extend ```JpaRepository```.
com.structurizr:structurizr-websequencediagrams:1.3.6 | Provides the ability to export dynamic view definitions to WebSequenceDiagrams diagram definitions.

* Extracting software architecture information from code
    * [Component finder](docs/component-finder.md)
    * [Structurizr annotations](docs/structurizr-annotations.md)
    * [Type matchers](docs/type-matchers.md)
    * [Spring component finder strategies](docs/spring-component-finder-strategies.md)
    * [Supplementing the model from source code](docs/supplementing-from-source-code.md)
    * [Components and supporting types](docs/supporting-types.md)
    * [The Spring PetClinic example](docs/spring-petclinic.md)
* Exporting and visualising with other tools
	* [Automatic layout with Graphviz](structurizr-graphviz)
    * [PlantUML](structurizr-plantuml)
    * [Mermaid](structurizr-mermaid)
    * [WebSequenceDiagrams](structurizr-websequencediagrams)
* Related projects
    * [java](https://github.com/structurizr/java): Structurizr for Java
    * [java-quickstart](https://github.com/structurizr/java-quickstart): A starting point for using Structurizr for Java
    * [structurizr-kotlin](https://github.com/Catalysts/structurizr-extensions/tree/master/structurizr-kotlin): An extension for Structurizr that lets you create your models in a fluent way.
    * [structurizr-spring-boot](https://github.com/Catalysts/structurizr-extensions/tree/master/structurizr-spring-boot): A way to apply dependency management to help modularise Structurizr code.
    * [structurizr-groovy](https://github.com/tidyjava/structurizr-groovy): An initial version of a Groovy wrapper around Structurizr for Java.
    
![Java CI with Gradle](https://github.com/structurizr/java-extensions/workflows/Java%20CI%20with%20Gradle/badge.svg)