package com.structurizr.fluent;

import java.util.Optional;
import java.util.Set;

public interface Search<T> {

    Set<T> all();
    Optional<T> byName(String name);
    Optional<T> byId(String id);

    default T byNameGet(String name) {
        return byName(name).orElse(null);
    }

    default T byIdGet(String id) {
        return byId(id).orElse(null);
    }

}
