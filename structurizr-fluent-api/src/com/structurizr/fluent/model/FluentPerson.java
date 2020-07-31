package com.structurizr.fluent.model;

import com.structurizr.fluent.Located;
import com.structurizr.model.Location;
import com.structurizr.model.Person;

public class FluentPerson extends FluentStaticStructureElement<FluentPerson, Person, FluentModel> implements Located<FluentPerson> {

    public FluentPerson(Person person, FluentModel fluentModel) {
        super(person, fluentModel);
    }

    public FluentPerson location(Location location) {
        asStructurizr().setLocation(location);
        return this;
    }

    @Override
    public Location location() {
        return asStructurizr().getLocation();
    }

}
