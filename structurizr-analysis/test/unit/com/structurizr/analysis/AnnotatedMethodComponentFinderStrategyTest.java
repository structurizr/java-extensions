package com.structurizr.analysis;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.model.Relationship;
import org.junit.Test;
import test.AnnotatedMethodComponentFinderStrategy.main.Bean;
import test.AnnotatedMethodComponentFinderStrategy.main.Configuration;
import test.AnnotatedMethodComponentFinderStrategy.main.FakeComponent;
import test.AnnotatedMethodComponentFinderStrategy.main.FakeComponentImpl;
import test.AnnotatedMethodComponentFinderStrategy.main.FakeEfferentComponentImpl;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.from;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AnnotatedMethodComponentFinderStrategyTest {

    private ComponentFinder componentFinder;
    private Container fakeContainer = new Workspace("Fake Workspace", "")
            .getModel()
            .addSoftwareSystem("Fake System", "")
            .addContainer("Fake Container", "", "");
    private String rootPackageToScan = "test.AnnotatedMethodComponentFinderStrategy";
    private String mainPackageToScan = "test.AnnotatedMethodComponentFinderStrategy.main";

    @Test
    public void test_findComponentsFromAnnotatedMethodsOnlyFromSpecifiedClassesIgnoringDuplicatesOfSameImplType() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, mainPackageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class, Configuration.class));

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
    public void test_findComponentsFromAllAnnotatedMethodsIgnoreDuplicatesOfSameImplType() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, mainPackageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class));

        Set<Component> components = componentFinder.findComponents();

        assertThat(components).hasSize(3);
    }

    @Test
    public void test_findComponentsFromAllAnnotatedMethodsIgnoringAllDuplicates_WhenIgnoreStrategyPassed() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, rootPackageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class, new IgnoreDuplicateComponentStrategy()));

        Set<Component> components = componentFinder.findComponents();

        assertThat(components).hasSize(3);
    }

    @Test
    public void test_findComponentsFromAllAnnotatedMethodsIgnoringAllDuplicates_WhenNullAsDuplicateStrategyPassed() throws Exception {
        componentFinder = new ComponentFinder(fakeContainer, rootPackageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class, null, null));

        Set<Component> components = componentFinder.findComponents();

        assertThat(components).hasSize(3);
    }

    @Test
    public void test_findComponents_ThrowsDuplicateComponentException_WhenThrowStrategyPassed() {
        componentFinder = new ComponentFinder(fakeContainer, rootPackageToScan, new AnnotatedMethodComponentFinderStrategy(Bean.class, new ThrowExceptionDuplicateComponentStrategy()));

        assertThatThrownBy(() -> componentFinder.findComponents()).isInstanceOf(DuplicateComponentException.class);
    }
}
