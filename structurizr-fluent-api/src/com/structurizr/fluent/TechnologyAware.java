package com.structurizr.fluent;

public interface TechnologyAware<T extends Tagged<T>> {

    T technology(String technology);
    String technology();

}
