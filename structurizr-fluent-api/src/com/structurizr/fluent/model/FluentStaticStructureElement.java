package com.structurizr.fluent.model;

import com.structurizr.model.InteractionStyle;
import com.structurizr.model.Relationship;
import com.structurizr.model.StaticStructureElement;

@SuppressWarnings({"unchecked", "rawtypes"})
public class FluentStaticStructureElement<Self extends FluentStaticStructureElement<Self, U, Parent>, U extends StaticStructureElement, Parent> extends FluentElement<Self, U> {

    private final Parent parent;
    private final U staticElement;

    public FluentStaticStructureElement(U element, Parent parent) {
        super(element);
        this.parent = parent;
        this.staticElement = element;
    }

    public Parent parent() {
        return parent;
    }

    @Override
    public U asStructurizr() {
        return staticElement;
    }

    public FluentRelationship<Self, ?> uses(FluentStaticStructureElement<?,?,?> destination, String description, String technology, InteractionStyle interactionStyle) {
        Relationship relationship = staticElement.uses(destination.asStructurizr(), description, technology, interactionStyle);
        return new FluentRelationship<>((Self) this, destination, relationship);
    }
    
    public FluentRelationship<Self, ?> uses(FluentStaticStructureElement<?,?,?> destination, String description, String technology) {
        return uses(destination, description, technology, InteractionStyle.Synchronous);
    }
    
    public FluentRelationship<Self, ?> usesAsync(FluentStaticStructureElement<?,?,?> destination, String description, String technology) {
        return uses(destination, description, technology, InteractionStyle.Asynchronous);
    }
    
    public FluentRelationship<Self, ?> uses(FluentStaticStructureElement<?,?,?> destination, String description) {
        return uses(destination, description, null);
    }
    
    public FluentRelationship<Self, ?> usesAsync(FluentStaticStructureElement<?,?,?> destination, String description) {
        return usesAsync(destination, description, null);
    }

    public FluentRelationship<Self, FluentSoftwareSystem> uses(FluentSoftwareSystem destination, String description, String technology) {
        return (FluentRelationship<Self, FluentSoftwareSystem>) uses((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentSoftwareSystem> usesAsync(FluentSoftwareSystem destination, String description, String technology) {
        return (FluentRelationship<Self, FluentSoftwareSystem>) usesAsync((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentSoftwareSystem> uses(FluentSoftwareSystem destination, String description) {
        return uses(destination, description, null);
    }
    
    public FluentRelationship<Self, FluentSoftwareSystem> usesAsync(FluentSoftwareSystem destination, String description) {
        return usesAsync(destination, description, null);
    }
    
    public FluentRelationship<Self, FluentContainer> uses(FluentContainer destination, String description, String technology) {
        return (FluentRelationship<Self, FluentContainer>) uses((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentContainer> usesAsync(FluentContainer destination, String description, String technology) {
        return (FluentRelationship<Self, FluentContainer>) usesAsync((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentContainer> uses(FluentContainer destination, String description) {
        return uses(destination, description, null);
    }
    
    public FluentRelationship<Self, FluentContainer> usesAsync(FluentContainer destination, String description) {
        return uses(destination, description, null);
    }
    
    public FluentRelationship<Self, FluentComponent> uses(FluentComponent destination, String description, String technology) {
        return (FluentRelationship<Self, FluentComponent>) uses((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentComponent> usesAsync(FluentComponent destination, String description, String technology) {
        return (FluentRelationship<Self, FluentComponent>) usesAsync((FluentStaticStructureElement) destination, description, technology);
    }
    
    public FluentRelationship<Self, FluentComponent> uses(FluentComponent destination, String description) {
        return uses(destination, description, null);
    }
    
    public FluentRelationship<Self, FluentComponent> usesAsync(FluentComponent destination, String description) {
        return usesAsync(destination, description, null);
    }
}
