package com.structurizr.io.plantuml;

import com.structurizr.io.AbstractDiagramExporter;
import com.structurizr.io.IndentingWriter;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.DynamicView;
import com.structurizr.view.Shape;
import com.structurizr.view.View;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;

public abstract class AbstractPlantUMLExporter extends AbstractDiagramExporter {

    public static final String PLANTUML_TITLE_PROPERTY = "plantuml.title";
    public static final String PLANTUML_LEGEND_PROPERTY = "plantuml.legend";
    public static final String PLANTUML_INCLUDES_PROPERTY = "plantuml.includes";
    public static final String PLANTUML_SEQUENCE_DIAGRAMS_PROPERTY = "plantuml.sequenceDiagrams";

    private final Map<String, String> skinParams = new LinkedHashMap<>();

    protected Map<String, String> getSkinParams() {
        return skinParams;
    }

    public void addSkinParam(String name, String value) {
        skinParams.put(name, value);
    }

    public void clearSkinParams() {
        skinParams.clear();
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

    String idOf(ModelItem modelItem) {
        if (modelItem instanceof Element) {
            Element element = (Element)modelItem;
            if (element.getParent() == null) {
                if (element instanceof DeploymentNode) {
                    DeploymentNode dn = (DeploymentNode)element;
                    return filter(dn.getEnvironment()) + "." + id(dn);
                } else {
                    return id(element);
                }
            } else {
                return idOf(element.getParent()) + "." + id(modelItem);
            }
        }

        return id(modelItem);
    }

    private String id(ModelItem modelItem) {
        if (modelItem instanceof Person) {
            return id((Person)modelItem);
        } else  if (modelItem instanceof SoftwareSystem) {
            return id((SoftwareSystem)modelItem);
        } else  if (modelItem instanceof Container) {
            return id((Container)modelItem);
        } else  if (modelItem instanceof Component) {
            return id((Component)modelItem);
        } else  if (modelItem instanceof DeploymentNode) {
            return id((DeploymentNode)modelItem);
        } else  if (modelItem instanceof InfrastructureNode) {
            return id((InfrastructureNode)modelItem);
        } else  if (modelItem instanceof SoftwareSystemInstance) {
            return id((SoftwareSystemInstance)modelItem);
        } else  if (modelItem instanceof ContainerInstance) {
            return id((ContainerInstance)modelItem);
        }

        return modelItem.getId();
    }

    private String id(Person person) {
        return filter(person.getName());
    }

    private String id(SoftwareSystem softwareSystem) {
        return filter(softwareSystem.getName());
    }

    private String id(Container container) {
        return filter(container.getName());
    }

    private String id(Component component) {
        return filter(component.getName());
    }

    private String id(DeploymentNode deploymentNode) {
        return filter(deploymentNode.getName());
    }

    private String id(InfrastructureNode infrastructureNode) {
        return filter(infrastructureNode.getName());
    }

    private String id(SoftwareSystemInstance softwareSystemInstance) {
        return filter(softwareSystemInstance.getName()) + "_" + softwareSystemInstance.getInstanceId();
    }

    private String id(ContainerInstance containerInstance) {
        return filter(containerInstance.getName()) + "_" + containerInstance.getInstanceId();
    }

    private String filter(String s) {
        return s.replaceAll("\\W", "");
    }

    protected boolean includeTitle(View view) {
        return "true".equalsIgnoreCase(view.getViewSet().getConfiguration().getProperties().getOrDefault(PLANTUML_TITLE_PROPERTY, "true"));
    }

    protected boolean useSequenceDiagrams(View view) {
        return "true".equalsIgnoreCase(view.getViewSet().getConfiguration().getProperties().getOrDefault(PLANTUML_SEQUENCE_DIAGRAMS_PROPERTY, "false"));
    }

    @Override
    protected void writeHeader(View view, IndentingWriter writer) {
        writer.writeLine("@startuml");

        if (includeTitle(view)) {
            String viewTitle = view.getTitle();
            if (StringUtils.isNullOrEmpty(viewTitle)) {
                viewTitle = view.getName();
            }
            writer.writeLine("title " + viewTitle);
        }

        writer.writeLine();

        if (view instanceof DynamicView && useSequenceDiagrams(view)) {
            // do nothing
        } else {
            if (view.getAutomaticLayout() != null) {
                switch (view.getAutomaticLayout().getRankDirection()) {
                    case LeftRight:
                        writer.writeLine("left to right direction");
                        break;
                    default:
                        writer.writeLine("top to bottom direction");
                        break;
                }
            } else {
                writer.writeLine("top to bottom direction");
            }

            writer.writeLine();
        }

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

    protected void writeIncludes(View view, IndentingWriter writer) {
        String[] includes = view.getViewSet().getConfiguration().getProperties().getOrDefault(PLANTUML_INCLUDES_PROPERTY, "").split(",");
        for (String include : includes) {
            if (!StringUtils.isNullOrEmpty(include)) {
                include = include.trim();
                writer.writeLine("!include " + include);
            }
        }
    }

    @Override
    protected void writeFooter(View view, IndentingWriter writer) {
        writer.writeLine("@enduml");
    }

}