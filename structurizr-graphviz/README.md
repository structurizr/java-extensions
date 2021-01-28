# structurizr-graphviz

structurizr-graphviz is a wrapper around the [Graphviz tool](http://www.graphviz.org), which allows you to apply the Graphviz layout algorithm to the views in a Structurizr workspace.

> You will need Graphviz installed.

For example:

```java
Workspace workspace = ...

GraphvizAutomaticLayout graphviz = new GraphvizAutomaticLayout();
graphviz.apply(workspace);
```

The ```structurizr-graphviz``` library does the following for every view in the workspace:

1. Export the view to a DOT file.
2. Run Graphviz (via the ```dot``` command), with the output format set to SVG.
3. Parse the generated SVG to extract layout information, and apply this to the Structurizr view (element x,y positions, relationship vertices, and paper size).

Once the layout has been applied, you can upload your workspace to the Structurizr cloud service/on-premises installation as usual.

## Changelog

### 1.5.0 (unreleased)

- Adds support for relationships between deployment nodes.
- Adds support for element grouping.
- Fixes a bug with double-quote characters in element names.

### 1.4.0 (18th December 2020)

- Adds support for return/response messages on dynamic views.
- Adds support for software system instances on deployment views.
- Fixes a bug where a view's paper size isn't set when the diagram is larger than A0.

### 1.3.7 (27th June 2020)

- Fixes a bug that assumed some numbers in the SVG output from Graphviz would be integers.

### 1.3.6 (9th June 2020)

- Fixes issues with running the Graphviz extension in non-English locales.

### 1.3.5 (8th April 2020)

- Initial version.

