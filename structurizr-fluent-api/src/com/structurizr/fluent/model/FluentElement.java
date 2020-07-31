package com.structurizr.fluent.model;

import com.structurizr.fluent.StructurizrWrapper;
import com.structurizr.fluent.Tagged;
import com.structurizr.model.Element;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public abstract class FluentElement<Self extends FluentElement<Self, U>, U extends Element> implements Tagged<Self>, StructurizrWrapper<U> {

    private final U element;

    public Self url(String url) {
        element.setUrl(url);
        return (Self) this;
    }

    public Self description(String description) {
        element.setDescription(description);
        return (Self) this;
    }

    public Self addPerspective(String name, String description) {
        element.addPerspective(name, description);
        return (Self) this;
    }

    public Self addProperty(String name, String value) {
        element.addProperty(name, value);
        return (Self) this;
    }

    @Override
    public Set<String> tags() {
        return element.getTagsAsSet();
    }

    @Override
    public String tagsAsString() {
        return element.getTags();
    }

    @Override
    public Self tags(String... tags) {
        element.addTags(tags);
        return (Self) this;
    }

    public U asStructurizr() {
        return element;
    }
}
