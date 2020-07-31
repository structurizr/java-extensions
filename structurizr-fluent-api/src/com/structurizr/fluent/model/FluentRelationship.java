package com.structurizr.fluent.model;

import com.structurizr.fluent.StructurizrWrapper;
import com.structurizr.fluent.Tagged;
import com.structurizr.model.Relationship;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Set;

@Accessors(chain = true, fluent = true)
@SuppressWarnings("rawtypes")
public final class FluentRelationship<Source extends FluentStaticStructureElement, Destination extends FluentStaticStructureElement> implements Tagged<FluentRelationship<Source,Destination>>, StructurizrWrapper<Relationship> {

    @Getter
    private final Source source;
    @Getter
    private final Destination destination;
    private final Relationship relationship;

    public FluentRelationship(Source source, Destination destination, Relationship relationship) {
        this.source = source;
        this.destination = destination;
        this.relationship = relationship;
    }

    public FluentRelationship<Source, Destination> url(String url) {
        relationship.setUrl(url);
        return this;
    }

    public FluentRelationship<Source, Destination> addPerspective(String name, String description) {
        relationship.addPerspective(name, description);
        return this;
    }

    public FluentRelationship<Source, Destination> addProperty(String name, String value) {
        relationship.addProperty(name, value);
        return this;
    }

    @Override
    public Set<String> tags() {
        return relationship.getTagsAsSet();
    }

    @Override
    public String tagsAsString() {
        return relationship.getTags();
    }

    @Override
    public FluentRelationship<Source, Destination> tags(String... tags) {
        relationship.addTags(tags);
        return this;
    }

    public Relationship asStructurizr() {
        return relationship;
    }
}
