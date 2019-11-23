package com.structurizr.analysis;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Relationship;
import org.junit.Test;
import test.AnnotatedMethodComponentFinderStrategy.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AnnotatedMethodComponentFinderStrategyTest {

    private ComponentFinder componentFinder;
    private Container fakeContainer = new Workspace("Fake Workspace", "")
            .getModel()
            .addSoftwareSystem("Fake System", "")
            .addContainer("Fake Container", "", "");
    private String packageToScan = "test.AnnotatedMethodComponentFinderStrategy";

    @Test
    public void test_findComponentsFromAnnotatedMethodsAndClassesIgnoreDuplicates() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, packageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class, Configuration.class));

        Set<Component> components = componentFinder.findComponents();
        assertThat(components).hasSize(2);

        Component fakeComponent = fakeContainer.getComponentWithName(FakeComponent.class.getSimpleName());
        assertThat(fakeComponent)
                .returns(FakeComponentImpl.class.getName(), from(c -> c.getType().getType()))
                .returns("", from(Component::getDescription))
                .returns("", from(Component::getTechnology));

        Component fakeEfferentComponent = fakeContainer.getComponentWithName(FakeEfferentComponentImpl.class.getSimpleName());
        assertThat(fakeComponent.getRelationships()).hasOnlyOneElementSatisfying(relationship ->
                assertThat(relationship)
                        .returns(fakeComponent, Relationship::getSource)
                        .returns(fakeEfferentComponent, Relationship::getDestination)
        );
    }

    @Test
    public void test_findComponentsFromAnnotatedMethods() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, packageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class));

        Set<Component> components = componentFinder.findComponents();

        assertThat(components).hasSize(2);
    }
}
