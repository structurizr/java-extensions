package com.structurizr.fluent.model;

import com.structurizr.fluent.TechnologyAware;
import com.structurizr.model.CodeElement;
import com.structurizr.fluent.model.FluentCodeElement.Language;
import com.structurizr.model.Component;

import java.util.Optional;

public class FluentComponent extends FluentStaticStructureElement<FluentComponent, Component, FluentContainer> implements TechnologyAware<FluentComponent> {
    private final Component component;
    private final String defaultLanguage;

    public FluentComponent(Component component, FluentContainer fluentContainer) {
        this(component, fluentContainer, Language.JAVA);
    }

    public FluentComponent(Component component, FluentContainer fluentContainer, String defaultLanguage) {
        super(component, fluentContainer);
        this.component = component;
        this.defaultLanguage = defaultLanguage;
    }

    public FluentComponent(Component component, FluentContainer fluentContainer, Language defaultLanguage) {
        this(component, fluentContainer, defaultLanguage.getName());
    }

    public FluentContainer container() {
        return parent();
    }

    public FluentComponent size(long size) {
        component.setSize(size);
        return this;
    }

    public FluentComponent technology(String technology) {
        component.setTechnology(technology);
        return this;
    }

    @Override
    public String technology() {
        return component.getTechnology();
    }

    private FluentCodeElement createFluentCodeElement(CodeElement element) {
        return new FluentCodeElement(element, this).language(defaultLanguage);
    }

    public FluentCodeElement type(String type) {
        CodeElement codeElement = component.setType(type);
        return createFluentCodeElement(codeElement);
    }

    public Optional<FluentCodeElement> type() {
        return Optional.ofNullable(component.getType())
                .map(this::createFluentCodeElement);
    }

    public FluentCodeElement addSupportingType(String type) {
        CodeElement codeElement = component.addSupportingType(type);
        return createFluentCodeElement(codeElement);
    }

    public FluentCodeElement addSupportingType(Class<?> type) {
        return addSupportingType(type.getCanonicalName());
    }

}
