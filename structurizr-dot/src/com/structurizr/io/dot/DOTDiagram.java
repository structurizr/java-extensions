package com.structurizr.io.dot;

public class DOTDiagram {

    private String key;
    private String name;
    private String definition;

    DOTDiagram(String key, String name, String definition) {
        this.key = key;
        this.name = name;
        this.definition = definition;
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