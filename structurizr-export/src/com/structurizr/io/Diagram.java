package com.structurizr.io;

public class Diagram {

    private String key;
    private String name;
    private String definition;

    public Diagram(String key, String name, String definition) {
        this.key = key;
        this.name = name;

        if (definition.length() > 0) {
            this.definition = definition.substring(0, definition.length() - 1);
        } else {
            this.definition = definition;
        }
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDefinition() {
        return definition;
    }

}