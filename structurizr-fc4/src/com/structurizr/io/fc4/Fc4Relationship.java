package com.structurizr.io.fc4;

class Fc4Relationship {

    private String source;
    private String description;
    private String destination;

    Fc4Relationship(String source, String description, String destination) {
        this.source = source;
        this.description = description;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Fc4Relationship{" +
                "source='" + source + '\'' +
                ", description='" + description + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

}