package com.structurizr.fluent.model;

import com.structurizr.fluent.Search;
import com.structurizr.fluent.TechnologyAware;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FluentContainer extends FluentStaticStructureElement<FluentContainer, Container, FluentSoftwareSystem> implements TechnologyAware<FluentContainer> {

    private final Container container;

    public FluentContainer(Container container, FluentSoftwareSystem fluentSoftwareSystem) {
        super(container, fluentSoftwareSystem);
        this.container = container;
    }

    public FluentContainer technology(String technology) {
        container.setTechnology(technology);
        return this;
    }

    @Override
    public String technology() {
        return container.getTechnology();
    }

    public FluentSoftwareSystem softwareSystem() {
        return parent();
    }

    public FluentComponent addComponent(String name, Class<?> type, String description, String technology) {
        Component component = container.addComponent(name, type, description, technology);
        return new FluentComponent(component, this);
    }

    public FluentComponent addComponent(String name, String type, String description, String technology) {
        Component component = container.addComponent(name, type, description, technology);
        return new FluentComponent(component, this);
    }

    public FluentComponent addComponent(String name, String description, String technology) {
        return addComponent(name, (Class<?>) null, description, technology);
    }

    public FluentComponent addComponent(String name, String description) {
        return addComponent(name, description, null);
    }

    public FluentComponent addComponent(String name) {
        return addComponent(name, null);
    }

    public ComponentSearch component() {
        return new ComponentSearch();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class ComponentSearch implements Search<FluentComponent> {

        private FluentComponent toFluentComponent(Component component) {
            return new FluentComponent(component, FluentContainer.this);
        }

        @Override
        public Set<FluentComponent> all() {
            return container.getComponents()
                    .stream()
                    .map(this::toFluentComponent)
                    .collect(Collectors.toSet());
        }

        @Override
        public Optional<FluentComponent> byName(String name) {
            return Optional.ofNullable(container.getComponentWithName(name))
                    .map(this::toFluentComponent);
        }

        @Override
        public Optional<FluentComponent> byId(String id) {
            throw new UnsupportedOperationException("Components can't be searched by ID");
        }

        public Optional<FluentComponent> byType(String type) {
            return Optional.ofNullable(container.getComponentOfType(type))
                    .map(this::toFluentComponent);
        }

        public Optional<FluentComponent> byType(Class<?> type) {
            return Optional.ofNullable(container.getComponentOfType(type.getCanonicalName()))
                    .map(this::toFluentComponent);
        }
    }
}
