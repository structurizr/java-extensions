package com.structurizr.io;

import com.structurizr.view.View;

public class Diagram {

    private View view;
    private String definition;

    public Diagram(View view, String definition) {
        this.view = view;

        if (definition.length() > 0) {
            this.definition = definition.substring(0, definition.length() - 1);
        } else {
            this.definition = definition;
        }
    }

    public String getKey() {
        return view.getKey();
    }

    public View getView() {
        return view;
    }

    public String getDefinition() {
        return definition;
    }

}