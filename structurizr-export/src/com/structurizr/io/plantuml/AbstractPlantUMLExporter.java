package com.structurizr.io.plantuml;

import com.structurizr.io.AbstractDiagramExporter;
import com.structurizr.io.IndentingWriter;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.Element;
import com.structurizr.model.Relationship;
import com.structurizr.util.StringUtils;
import com.structurizr.view.RelationshipStyle;
import com.structurizr.view.Shape;
import com.structurizr.view.View;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public abstract class AbstractPlantUMLExporter extends AbstractDiagramExporter {

    private final Map<String, String> skinParams = new LinkedHashMap<>();
    private final List<String> includes = new ArrayList<>();

    private boolean useSequenceDiagrams = false;
    private boolean includeTitle = true;

    public List<String> getIncludes() {
        return includes;
    }

    public void addIncludeFile(String file) {
        addIncludeFile(file, null);
    }

    public void addIncludeFile(String file, int id) {
        addIncludeFile(file, String.valueOf(id));
    }

    public void addIncludeFile(String file, String id) {
        if (id==null) {
            includes.add(format("!include %s", file));
        } else {
            includes.add(format("!include %s!%s", file, id));
        }
    }

    public void addIncludeURL(URI file) {
        addIncludeURL(file, null);
    }

    public void addIncludeURL(URI file, int id) {
        addIncludeURL(file, String.valueOf(id));
    }

    public void addIncludeURL(URI file, String id) {
        if (id==null) {
            includes.add(format("!includeurl %s", file));
        } else {
            includes.add(format("!includeurl %s!%s", file, id));
        }
    }

    public void clearIncludes() {
        includes.clear();
    }

    protected Map<String, String> getSkinParams() {
        return skinParams;
    }

    public void addSkinParam(String name, String value) {
        skinParams.put(name, value);
    }

    public void clearSkinParams() {
        skinParams.clear();
    }

    public boolean isUseSequenceDiagrams() {
        return useSequenceDiagrams;
    }

    public void setUseSequenceDiagrams(boolean useSequenceDiagrams) {
        this.useSequenceDiagrams = useSequenceDiagrams;
    }

    public boolean isIncludeTitle() {
        return includeTitle;
    }

    public void setIncludeTitle(boolean includeTitle) {
        this.includeTitle = includeTitle;
    }

    String plantUMLShapeOf(View view, Element element) {
        Shape shape = findElementStyle(view, element).getShape();

        if (element instanceof DeploymentNode) {
            return "node";
        }

        switch(shape) {
            case Person:
            case Robot:
                return "person";
            case Component:
                return "component";
            case Cylinder:
                return "database";
            case Folder:
                return "folder";
            case Ellipse:
            case Circle:
                return "storage";
            case Hexagon:
                return "hexagon";
            case Pipe:
                return "queue";
            default:
                return "rectangle";
        }
    }

    String plantumlSequenceType(View view, Element element) {
        Shape shape = findElementStyle(view, element).getShape();

        switch(shape) {
            case Box:
                return "participant";
            case Person:
                return "actor";
            case Cylinder:
                return "database";
            case Folder:
                return "collections";
            case Ellipse:
            case Circle:
                return "entity";
            default:
                return "participant";
        }
    }

    String idOf(Element e) {
        return e.getId();
    }

    @Override
    protected void writeHeader(View view, IndentingWriter writer) {
        writer.writeLine("@startuml");

        for (String include : includes) {
            writer.writeLine(include);
        }

        if (includeTitle) {
            String viewTitle = view.getTitle();
            if (StringUtils.isNullOrEmpty(viewTitle)) {
                viewTitle = view.getName();
            }
            writer.writeLine("title " + viewTitle);
        }

        writer.writeLine();

        if (!skinParams.isEmpty()) {
            writer.writeLine("skinparam {");
            writer.indent();
            for (final String name : skinParams.keySet()) {
                writer.writeLine(format("%s %s", name, skinParams.get(name)));
            }
            writer.outdent();
            writer.writeLine("}");
        }
    }

    @Override
    protected void writeFooter(View view, IndentingWriter writer) {
        writer.writeLine("@enduml");
    }

}