package com.structurizr.fluent.model;

import com.structurizr.fluent.StructurizrWrapper;
import com.structurizr.model.CodeElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FluentCodeElement implements StructurizrWrapper<CodeElement> {

    private final CodeElement element;
    private final FluentComponent component;

    public FluentComponent parent() {
        return component;
    }

    public FluentComponent component() {
        return component;
    }

    public FluentCodeElement category(String category) {
        element.setCategory(category);
        return this;
    }

    public FluentCodeElement description(String description) {
        element.setDescription(description);
        return this;
    }

    public FluentCodeElement language(String language) {
        element.setLanguage(language);
        return this;
    }

    public FluentCodeElement language(Language language) {
        element.setLanguage(language.getName());
        return this;
    }

    public FluentCodeElement size(long size) {
        element.setSize(size);
        return this;
    }

    public FluentCodeElement url(String url) {
        element.setUrl(url);
        return this;
    }

    public FluentCodeElement visibility(String visibility) {
        element.setVisibility(visibility);
        return this;
    }

    public CodeElement asStructurizr() {
        return element;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Language {

        JAVA("Java"),
        PYTHON("Python"),
        R("R"),
        JAVASCRIPT("JavaScript"),
        TYPESCRIPT("TypeScript"),
        KOTLIN("Kotlin"),
        SCALA("Scala"),
        CPP("C++"),
        C("C"),
        CSHARP("C#"),
        GO("Go"),
        SWIFT("Swift"),
        PHP("PHP");

        private final String name;

    }

}
