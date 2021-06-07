package com.structurizr.io;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.StringUtils;
import com.structurizr.view.*;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractExporter {

    protected String breakText(int maxWidth, int fontSize, String s) {
        if (StringUtils.isNullOrEmpty(s)) {
            return "";
        }

        StringBuilder buf = new StringBuilder();

        double characterWidth = fontSize * 0.6;
        int maxCharacters = (int)(maxWidth / characterWidth);

        if (s.length() < maxCharacters) {
            return s;
        }

        String[] words = s.split(" ");
        String line = null;
        for (String word : words) {
            if (line == null) {
                line = word;
            } else {
                if ((line.length() + word.length() + 1) < maxCharacters) {
                    line += " ";
                    line += word;
                } else {
                    buf.append(line);
                    buf.append("<br />");
                    line = word;
                }
            }
        }

        if (line != null) {
            buf.append(line);
        }

        return buf.toString();
    }

    protected String typeOf(Workspace workspace, Element e, boolean includeMetadataSymbols) {
        return typeOf(workspace.getViews().getConfiguration(), e, includeMetadataSymbols);
    }

    protected String typeOf(View view, Element e, boolean includeMetadataSymbols) {
        return typeOf(view.getViewSet().getConfiguration(), e, includeMetadataSymbols);
    }

    private String typeOf(Configuration configuration, Element e, boolean includeMetadataSymbols) {
        String terminology = configuration.getTerminology().findTerminology(e);
        String type = "";

        if (e instanceof Person) {
            type = terminology;
        } else if (e instanceof SoftwareSystem) {
            type = terminology;
        } else if (e instanceof Container) {
            Container container = (Container)e;
            type = terminology + (hasValue(container.getTechnology()) ? ": " + container.getTechnology() : "");
        } else if (e instanceof Component) {
            Component component = (Component)e;
            type = terminology + (hasValue(component.getTechnology()) ? ": " + component.getTechnology() : "");
        } else if (e instanceof DeploymentNode) {
            DeploymentNode deploymentNode = (DeploymentNode)e;
            type = terminology + (hasValue(deploymentNode.getTechnology()) ? ": " + deploymentNode.getTechnology() : "");
        } else if (e instanceof InfrastructureNode) {
            InfrastructureNode infrastructureNode = (InfrastructureNode)e;
            type = terminology + (hasValue(infrastructureNode.getTechnology()) ? ": " + infrastructureNode.getTechnology() : "");
        }

        if (includeMetadataSymbols) {
            if (configuration.getMetadataSymbols() == null) {
                configuration.setMetadataSymbols(MetadataSymbols.SquareBrackets);
            }

            switch (configuration.getMetadataSymbols()) {
                case RoundBrackets:
                    return "(" + type + ")";
                case CurlyBrackets:
                    return "{" + type + "}";
                case AngleBrackets:
                    return "<" + type + ">";
                case DoubleAngleBrackets:
                    return "<<" + type + ">>";
                case None:
                    return type;
                default:
                    return "[" + type + "]";
            }
        } else {
            return type;
        }
    }

    protected boolean hasValue(String s) {
        return !StringUtils.isNullOrEmpty(s);
    }

    protected ElementStyle findElementStyle(View view, Element element) {
        if (element instanceof StaticStructureElementInstance) {
            return findElementStyle(view, ((StaticStructureElementInstance)element).getElement());
        } else {
            return view.getViewSet().getConfiguration().getStyles().findElementStyle(element);
        }
    }

    protected RelationshipStyle findRelationshipStyle(View view, Relationship relationship) {
        return view.getViewSet().getConfiguration().getStyles().findRelationshipStyle(relationship);
    }

}