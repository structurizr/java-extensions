# structurizr-export

structurizr-export provides the ability to export views defined in a Structurizr workspace to a number of formats, including:

- PlantUML
- C4-PlantUML
- DOT
- Mermaid
- WebSequenceDiagrams
- Ilograph

## Changelog

### 1.1.1 (9th June 2021)

- Adds support for "left to right direction" layouts with C4-PlantUML.

### 1.1.0 (7th June 2021)

- Adds support for "external" software system/container boundaries on dynamic views.
- Adds support for more shapes (pipe and hexagon) via the StructurizrPlantUMLExporter.
- Adds support for exporting animations (StructurizrPlantUML and C4-PlantUML only).

### 1.0.1 (29th April 2021)

- Trying to render a sequence diagram with C4-PlantUML now throws an unsupported exception, as C4-PlantUML doesn't natively support sequence diagrams.

### 1.0.0 (27th April 2021)

- Initial version, refactored from existing (and separate) PlantUML, Mermaid, DOT, WebSequenceDiagrams, and Ilograph exporters.