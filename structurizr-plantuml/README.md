# structurizr-plantuml

structurizr-plantuml can export the views in a Structurizr workspace to diagram definitions that are compatible with [PlantUML](http://www.plantuml.com). The following diagram types are supported:

- System Landscape
- System Context
- Container
- Component
- Dynamic
- Deployment

Create your software architecture model and views as usual, and use one of the PlantUML writer classes to export the views. [For example](https://github.com/structurizr/java-extensions/blob/master/structurizr-examples/src/com/structurizr/example/PlantUML.java):

```java
Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");
Model model = workspace.getModel();

Person user = model.addPerson("User", "A user of my software system.");
SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
user.uses(softwareSystem, "Uses");

ViewSet views = workspace.getViews();
SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "An example of a System Context diagram.");
contextView.addAllSoftwareSystems();
contextView.addAllPeople();

Styles styles = views.getConfiguration().getStyles();
styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

AbstractPlantUMLWriter plantUMLWriter = new StructurizrPlantUMLWriter();
System.out.println(plantUMLWriter.toString(contextView));
```

This code will generate and output a PlantUML diagram definition that looks like this:

```
@startuml(id=SystemContext)
title Software System - System Context
caption An example of a System Context diagram.

skinparam {
  shadowing false
  arrowFontSize 10
  defaultTextAlignment center
  wrapWidth 200
  maxMessageSize 100
}
hide stereotype
skinparam rectangle<<1>> {
  BackgroundColor #08427b
  FontColor #ffffff
  BorderColor #052E56
}
skinparam rectangle<<2>> {
  BackgroundColor #1168bd
  FontColor #ffffff
  BorderColor #0B4884
}
rectangle "==User\n<size:10>[Person]</size>\n\nA user of my software system." <<1>> as 1
rectangle "==Software System\n<size:10>[Software System]</size>\n\nMy software system." <<2>> as 2
1 .[#707070].> 2 : "Uses"
@enduml
```

If you copy/paste this into [PlantUML online](http://www.plantuml.com/plantuml/), you will get something like this:

![An example PlantUML diagram](docs/images/getting-started.png)

There are three PlantUML writer implementations:

- `StructurizrPlantUMLWriter`: most closely resembles the diagram notation used on the [C4 model website](https://c4model.com), and the [Structurizr](https://structurizr.com) web-based renderer.
- `PlantUMLWriter`: default PlantUML styling (with UML stereotypes).
- `C4PlantUMLWriter`: produces diagram definitions that use the [C4-PlantUML macros](https://github.com/plantuml-stdlib/C4-PlantUML).

## Changelog

## 1.6.4 (unreleased)

- Fixes a bug that would render empty relationship technologies, instead of omitting them.
- Adds support to the `StructurizrPlantUMLWriter` for hiding the element metadata and/or description.

## 1.6.3 (28th March 2021)

- Uses custom terminology, if defined in the workspace.
- Provides a way for the diagram title/description to be omitted. 

## 1.6.2 (20th March 2021)

- Adds support for "external" containers on container views.
- Adds support for "external" components on component views.

### 1.6.1 (8th March 2021)

- Fixes a NullPointerException when writing older dynamic views (#45).

### 1.6.0 (12th January 2021)

- Renamed `PlantUMLWriter` to `BasicPlantUMLWriter`.

### 1.5.3 (6th January 2021)

- Uses new C4-PlantUML macros at https://github.com/plantuml-stdlib/C4-PlantUML
- Adds support for line thickness to the StructurizrPlantUMLWriter.

### 1.5.2 (15th September 2020)

- Fixes issue #34 (Function Rel_D does not found while using C4PlantUMLWriter).

### 1.5.1 (6th September 2020)

- Fixes a bug where the auto-layout direction could not be set for dynamic views.

### 1.5.0 (14th August 2020)

- Adds support for return/response messages on dynamic views.
- Adds support for software system instances on deployment views.
- Fixes a bug where all children of a deployment node would be rendered on a deployment view, irrespective of the defined elements.

### 1.4.0 (6th July 2020)

- Adds a StructurizrPlantUMLWriter, which emulates the style of the Structurizr web-based renderer.

### 1.3.8 (27th June 2020)

- Adds support for infrastructure nodes.
- Element types are now always included in the stereotypes.

### 1.3.7 (22nd June 2020)

- Allows customization of C4-PlantUML URL, so that different forks can be used.

### 1.3.6 (19th June 2020)

- Fixes the ordering of elements on a sequence diagram.

### 1.3.1 (29th October 2019)

- The structurizr-annotations library can now be more easily used with OSGi applications.
- Fixes a bug with the PlantUML and WebSequenceDiagram writers, where relationships were sorted incorrectly (alphabetically, rather than numerically).