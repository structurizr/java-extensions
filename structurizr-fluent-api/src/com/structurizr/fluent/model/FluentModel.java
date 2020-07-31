package com.structurizr.fluent.model;

import com.structurizr.fluent.Search;
import com.structurizr.model.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true, fluent = true)
public class FluentModel {

    private final Model model;

    public FluentModel enterprise(String name) {
        Enterprise enterprise = new Enterprise(name);
        model.setEnterprise(enterprise);
//        model.addDeploymentNode();
        return this;
    }

    public FluentModel createImpliedRelationshipsUnlessAnyRelationshipExistsStrategy() {
        model.setImpliedRelationshipsStrategy(new CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy());
        return this;
    }

    public FluentModel createImpliedRelationshipsUnlessSameRelationshipExistsStrategy() {
        model.setImpliedRelationshipsStrategy(new CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy());
        return this;
    }

    public FluentModel defaultImpliedRelationshipStrategy() {
        model.setImpliedRelationshipsStrategy(new DefaultImpliedRelationshipsStrategy());
        return this;
    }

    public FluentPerson addPerson(String name, String description) {
        return addPerson(Location.Unspecified, name, description);
    }

    public FluentPerson addPerson(String name) {
        return addPerson(name, null);
    }

    public FluentPerson addPerson(Location location, String name, String description) {
        Person person = model.addPerson(location, name, description);
        return new FluentPerson(person, this);
    }

    public FluentPerson addPerson(Location location, String name) {
        return addPerson(location, name, null);
    }

    public PeopleSearch people() {
        return new PeopleSearch();
    }

    public FluentSoftwareSystem addSoftwareSystem(String name, String description) {
        return addSoftwareSystem(Location.Unspecified, name, description);
    }

    public FluentSoftwareSystem addSoftwareSystem(String name) {
        return addSoftwareSystem(name, null);
    }

    public FluentSoftwareSystem addSoftwareSystem(Location location, String name, String description) {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(location, name, description);
        return new FluentSoftwareSystem(softwareSystem, this);
    }

    public FluentSoftwareSystem addSoftwareSystem(Location location, String name) {
        return addSoftwareSystem(location, name, null);
    }

    public SofwareSystemSearch softwareSystem() {
        return new SofwareSystemSearch();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class SofwareSystemSearch implements Search<FluentSoftwareSystem> {

        private FluentSoftwareSystem toSoftwareSystem(SoftwareSystem softwareSystem) {
            return new FluentSoftwareSystem(softwareSystem, FluentModel.this);
        }

        @Override
        public Set<FluentSoftwareSystem> all() {
            return model.getSoftwareSystems()
                    .stream()
                    .map(this::toSoftwareSystem)
                    .collect(Collectors.toSet());
        }

        @Override
        public Optional<FluentSoftwareSystem> byName(String name) {
            return Optional.ofNullable(model.getSoftwareSystemWithName(name))
                    .map(this::toSoftwareSystem);
        }

        @Override
        public Optional<FluentSoftwareSystem> byId(String id) {
            return Optional.ofNullable(model.getSoftwareSystemWithId(id))
                    .map(this::toSoftwareSystem);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class PeopleSearch implements Search<FluentPerson> {

        private FluentPerson toFluentPerson(Person person) {
            return new FluentPerson(person, FluentModel.this);
        }

        @Override
        public Set<FluentPerson> all() {
            return model.getPeople()
                    .stream()
                    .map(this::toFluentPerson)
                    .collect(Collectors.toSet());
        }

        @Override
        public Optional<FluentPerson> byName(String name) {
            return Optional.ofNullable(model.getPersonWithName(name))
                    .map(this::toFluentPerson);
        }

        @Override
        public Optional<FluentPerson> byId(String id) {
            throw new UnsupportedOperationException("People can't be searched by ID");
        }
    }
}
