# Graphviz automatic layout

The ```structurizr-graphviz``` library is a wrapper around the [Graphviz tool](http://www.graphviz.org), which allows you to apply the Graphviz layout algorithm to the views in a Structurizr workspace.

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