package com.structurizr.fluent;

import com.structurizr.model.Location;

public interface Located<T extends Located<T>> {

    T location(Location location);
    Location location();

    default T unspecifiedLocation() {
        return location(Location.Unspecified);
    }

    default T internal() {
        return location(Location.Internal);
    }

    default T external() {
        return location(Location.External);
    }


}
