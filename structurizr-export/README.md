# structurizr-export

structurizr-export provides the ability to export views defined in a Structurizr workspace to a number of formats, including:

- PlantUML
- C4-PlantUML
- DOT
- Mermaid
- WebSequenceDiagrams
- Ilograph

## Changelog

### 1.0.2 (unreleased)

- Adds support for "external" software system/container boundaries on dynamic views.

### 1.0.1 (29th April 2021)

- Trying to render a sequence diagram with C4-PlantUML now throws an unsupported exception, as C4-PlantUML doesn't natively support sequence diagrams.

### 1.0.0 (27th April 2021)

- Initial version, refactored from existing (and separate) PlantUML, Mermaid, DOT, WebSequenceDiagrams, and Ilograph exporters.