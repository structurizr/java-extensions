package com.structurizr.analysis;

import com.structurizr.model.Component;

public class IgnoreDuplicateComponentStrategy implements DuplicateComponentStrategy {

    /**
     * @param component   the existing Component object
     * @param name        the new component name
     * @param type        the new component type
     * @param description the new description
     * @param technology  the new technology
     * @return {@code null}  means a new component should not be created
     */
    @Override
    public Component duplicateComponentFound(Component component, String name, String type, String description, String technology) {
        return null;
    }
}