package com.structurizr.io.fc4;

import com.structurizr.Workspace;
import com.structurizr.model.Element;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.Shape;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.SystemLandscapeView;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Imports software architecture models defined in the FC4 YAML DSL.
 *
 * Please note: This class is not thread-safe, and is a prototype only.
 */
public class Fc4Importer {

    private Workspace workspace;
    private Set<Fc4Relationship> relationships = new HashSet<>();
    private File path;

    public Fc4Importer(String name, String description, File path) {
        if (path == null) {
            throw new IllegalArgumentException("The path to the FC4 definition must be specified.");
        }

        if (!path.exists()) {
            throw new IllegalArgumentException(path.getAbsolutePath() + " does not exist.");
        }

        if (!path.isDirectory()) {
            throw new IllegalArgumentException(path.getAbsolutePath() + " is not a directory.");
        }

        this.path = path;
        this.workspace = new Workspace(name, description);
    }

    public Workspace importWorkspace() throws Exception {
        importFromDirectory(path);

        importRelationships();

        // create a single System Landscape diagram containing everything
        SystemLandscapeView systemLandscapeView = workspace.getViews().createSystemLandscapeView("SystemLandscape", "A system landscape view for " + workspace.getName());
        systemLandscapeView.addAllElements();
        systemLandscapeView.setAutomaticLayout(true);

        // and a system context view for all software systems
        for (SoftwareSystem softwareSystem : workspace.getModel().getSoftwareSystems()) {
            SystemContextView systemContextView = workspace.getViews().createSystemContextView(softwareSystem, softwareSystem.getName(), "A system context view for " + softwareSystem.getName() + '.');
            systemContextView.addNearestNeighbours(softwareSystem);
            systemContextView.setAutomaticLayout(true);
        }

        // add some basic styling
        workspace.getViews().getConfiguration().getStyles().addElementStyle(Tags.PERSON).shape(Shape.Person);
        workspace.getViews().getConfiguration().getStyles().addElementStyle(Tags.SOFTWARE_SYSTEM).shape(Shape.RoundedBox);

        return workspace;
    }

    private void importFromDirectory(File directory) throws Exception {
        if (directory.listFiles() == null) {
            return;
        }

        for (File file : directory.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(".yaml")) {
                importFrom(file);
            }

            if (file.isDirectory()) {
                importFromDirectory(file);
            }
        }
    }

    private void importFrom(File file) throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(file);
        Map<String, Object> map = yaml.load(inputStream);

        for (String key : map.keySet()) {
            if ("users".equals(key)) {
                Map<String, Object> users = (Map<String, Object>)map.get(key);
                for (String userName : users.keySet()) {
                    Map<String, Object> userProperties = (Map<String, Object>)users.get(userName);
                    String userDescription = userProperties.getOrDefault("description", "A user.").toString();

                    workspace.getModel().addPerson(userName, userDescription);
                    parseRelationshipsFor(userName, userProperties);
                }
            } else if ("system".equals(key)) {
                Map<String, Object> system = (Map<String, Object>)map.get(key);
                for (String systemName : system.keySet()) {
                    Map<String, Object> systemProperties = (Map<String, Object>)system.get(systemName);

                    String systemDescription = systemProperties.getOrDefault("description", "A software system.").toString();

                    workspace.getModel().addSoftwareSystem(systemName, systemDescription);
                    parseRelationshipsFor(systemName, systemProperties);
                }
            }

        }
    }

    private void parseRelationshipsFor(String sourceName, Map<String, Object> properties) {
        if (properties.containsKey("uses")) {
            // there are relationships
            Map<String,Object> relationships = (Map<String,Object>)properties.get("uses");

            for (String relationshipDestinationName : relationships.keySet()) {
                Map<String, Object> relationshipProperties = (Map<String, Object>)relationships.get(relationshipDestinationName);
                String relationshipDescription = relationshipProperties.getOrDefault("to", "Uses").toString();

                this.relationships.add(new Fc4Relationship(sourceName, relationshipDescription, relationshipDestinationName));
            }
        }
    }

    private void importRelationships() {
        for (Fc4Relationship relationship : relationships) {
            Element sourceElement = findElementWithName(relationship.getSource());
            Element destinationElement = findElementWithName(relationship.getDestination());

            if (sourceElement != null && destinationElement != null) {
                if (sourceElement instanceof SoftwareSystem) {
                    SoftwareSystem sourceSoftwareSystem = (SoftwareSystem)sourceElement;

                    if (destinationElement instanceof SoftwareSystem) {
                        sourceSoftwareSystem.uses((SoftwareSystem)destinationElement, relationship.getDescription());
                    } else if (destinationElement instanceof Person) {
                        sourceSoftwareSystem.delivers((Person)destinationElement, relationship.getDescription());
                    }
                } else if (sourceElement instanceof Person) {
                    Person sourcePerson = (Person)sourceElement;

                    if (destinationElement instanceof SoftwareSystem) {
                        sourcePerson.uses((SoftwareSystem)destinationElement, relationship.getDescription());
                    } else if (destinationElement instanceof Person) {
                        sourcePerson.interactsWith((Person)destinationElement, relationship.getDescription());
                    }
                }

            }
        }
    }

    private Element findElementWithName(String name) {
        return workspace.getModel().getElements().stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }

}