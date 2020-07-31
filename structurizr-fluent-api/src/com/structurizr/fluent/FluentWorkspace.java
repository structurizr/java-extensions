package com.structurizr.fluent;

import com.structurizr.Workspace;
import com.structurizr.fluent.model.FluentModel;
import com.structurizr.fluent.view.FluentViewSet;

public class FluentWorkspace implements StructurizrWrapper<Workspace> {

    private final Workspace workspace;

    public FluentWorkspace(String name, String description) {
        this.workspace = new Workspace(name, description);
    }

    private FluentWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public static FluentWorkspace wrap(Workspace workspace) {
        return new FluentWorkspace(workspace);
    }

    public FluentModel model() {
        return new FluentModel(workspace.getModel());
    }

    public FluentViewSet views() {
        return new FluentViewSet(workspace.getViews());
    }

    public FluentWorkspace description(String description) {
        workspace.setDescription(description);
        return this;
    }

    public FluentWorkspace name(String name) {
        workspace.setName(name);
        return this;
    }

    public FluentWorkspace revision(long revision) {
        workspace.setRevision(revision);
        return this;
    }

    public FluentWorkspace version(String version) {
        workspace.setVersion(version);
        return this;
    }

    public FluentWorkspace thumbnail(String thumbnail) {
        workspace.setThumbnail(thumbnail);
        return this;
    }

    @Override
    public Workspace asStructurizr() {
        return workspace;
    }
}
