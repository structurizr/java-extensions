package com.structurizr.fluent.model;

import com.structurizr.fluent.Located;
import com.structurizr.fluent.Search;
import com.structurizr.model.Container;
import com.structurizr.model.Location;
import com.structurizr.model.SoftwareSystem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FluentSoftwareSystem extends FluentStaticStructureElement<FluentSoftwareSystem, SoftwareSystem, FluentModel> implements Located<FluentSoftwareSystem> {

    private final SoftwareSystem softwareSystem;
    
    FluentSoftwareSystem(SoftwareSystem softwareSystem, FluentModel model) {
        super(softwareSystem, model);
        this.softwareSystem = softwareSystem;
    }

    public FluentModel model() {
        return parent();
    }

    public FluentContainer addContainer(String name, String description, String technology) {
        Container container = softwareSystem.addContainer(name, description, technology);
        return new FluentContainer(container, this);
    }

    public FluentContainer addContainer(String name, String description) {
       return addContainer(name, description, null);
    }

    public FluentContainer addContainer(String name) {
       return addContainer(name, null, null);
    }

    public ContainerSearch container() {
        return new ContainerSearch();
    }

    @Override
    public FluentSoftwareSystem location(Location location) {
        softwareSystem.setLocation(location);
        return this;
    }

    @Override
    public Location location() {
        return softwareSystem.getLocation();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class ContainerSearch implements Search<FluentContainer> {

        private FluentContainer toFluentContainer(Container container) {
            return new FluentContainer(container, FluentSoftwareSystem.this);
        }

        @Override
        public Set<FluentContainer> all() {
            return softwareSystem.getContainers()
                    .stream()
                    .map(this::toFluentContainer)
                    .collect(Collectors.toSet());
        }

        @Override
        public Optional<FluentContainer> byName(String name) {
            return Optional.ofNullable(softwareSystem.getContainerWithName(name))
                    .map(this::toFluentContainer);
        }

        @Override
        public Optional<FluentContainer> byId(String id) {
            return Optional.ofNullable(softwareSystem.getContainerWithId(id))
                    .map(this::toFluentContainer);
        }
    }
}
