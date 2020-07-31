package com.structurizr.fluent;

import java.util.Set;

public interface Tagged<T extends Tagged<T>> {

    Set<String> tags();
    String tagsAsString();
    T tags(String... tags);
    default T tag(String tag) {
        return tags(tag);
    }

}
