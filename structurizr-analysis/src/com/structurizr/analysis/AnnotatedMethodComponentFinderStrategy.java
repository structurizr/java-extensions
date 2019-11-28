package com.structurizr.analysis;

import com.structurizr.model.Component;
import com.structurizr.model.Container;
import org.reflections.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A generic component finder strategy that finds components based on returned types of specifically annotated methods.
 * To filter scanned classes for specifically annotated methods specify classAnnotation as well.
 * This strategy uses first found implementation as a type property for a Component and
 * when component duplicates are found it behaves according to passed DuplicateComponentStrategy (by default it ignores duplicates if nothing is passed to constructor).
 * <pre>
 * With example below this strategy finds 2 components based on method annotation {@code @Bean},
 * e.g. component with name "SomeComponent" has efferent relation to "SomeEfferentComponent".
 *
 * {@code @Configuration}
 *  public class SomeConfiguration {
 *
 * {@code    @Bean}
 *     public SomeComponent someComponent(SomeEfferentComponent someEfferentComponent) {
 *         return new SomeComponentImpl(someEfferentComponent);
 *     }
 *
 * {@code    @Bean}
 *     public SomeEfferentComponent someEfferentComponent() {
 *         return new SomeEfferentComponentImpl();
 *     }
 *  }
 * </pre>
 */
public class AnnotatedMethodComponentFinderStrategy extends AbstractComponentFinderStrategy {
    private final Class<? extends Annotation> classAnnotation;
    private final Class<? extends Annotation> methodAnnotation;

    /**
     * Construct strategy that finds components based on return type of methods annotated with {@code methodAnnotation}
     *
     * @param methodAnnotation the annotation to look for on methods
     */
    public AnnotatedMethodComponentFinderStrategy(Class<? extends Annotation> methodAnnotation) {
        this(methodAnnotation, null, new IgnoreDuplicateComponentStrategy());
    }

    public AnnotatedMethodComponentFinderStrategy(Class<? extends Annotation> methodAnnotation, DuplicateComponentStrategy duplicateComponentStrategy) {
        this(methodAnnotation, null, duplicateComponentStrategy);
    }

    /**
     * Construct strategy that finds components based on return type of methods annotated with {@code methodAnnotation}
     * which belong only to classes annotated with {@code classAnnotation}
     *
     * @param methodAnnotation the annotation to look for on methods
     * @param classAnnotation  the annotation to look for on classes/interfaces
     */
    public AnnotatedMethodComponentFinderStrategy(Class<? extends Annotation> methodAnnotation, Class<? extends Annotation> classAnnotation) {
        this(methodAnnotation, classAnnotation, new IgnoreDuplicateComponentStrategy(), new ReferencedTypesSupportingTypesStrategy());
    }

    /**
     * Construct strategy that finds components based on return type of methods annotated with {@code methodAnnotation}
     * which belong only to classes annotated with {@code classAnnotation}.
     *
     * @param methodAnnotation           the annotation to look for on methods
     * @param classAnnotation            the annotation to look for on classes/interfaces to narrow scanned scope of methods
     * @param duplicateComponentStrategy strategy how to behave when component duplicates found
     * @param strategies                 strategies to look for supporting types for a component
     *                                   (component hides all types which support defined in component functionality behind abstraction)
     */
    public AnnotatedMethodComponentFinderStrategy(
            @Nonnull Class<? extends Annotation> methodAnnotation,
            @Nullable Class<? extends Annotation> classAnnotation,
            @Nullable DuplicateComponentStrategy duplicateComponentStrategy,
            SupportingTypesStrategy... strategies
    ) {
        super(strategies);
        DuplicateComponentStrategy duplicateStrategy = duplicateComponentStrategy != null ? duplicateComponentStrategy : new IgnoreDuplicateComponentStrategy();
        this.setDuplicateComponentStrategy(duplicateStrategy);
        this.classAnnotation = classAnnotation;
        this.methodAnnotation = methodAnnotation;
    }

    @Override
    protected Set<Component> doFindComponents() {
        Set<Component> components = new HashSet<>();
        Container container = getComponentFinder().getContainer();

        Set<Class<?>> classes = new HashSet<>();
        if (classAnnotation != null) {
            classes.addAll(findTypesAnnotatedWith(classAnnotation));
        } else {
            classes.addAll(getComponentFinder().getTypeRepository().getAllTypes());
        }
        for (Class<?> clazz : classes) {
            Set<SimpleImmutableEntry<Class, Class>> allMethods = ReflectionUtils.getAllMethods(clazz, m -> m.isAnnotationPresent(methodAnnotation))
                    .stream().map(this::interfaceToImpReturnedFrom).collect(Collectors.toSet());
            for (SimpleImmutableEntry<Class, Class> entry : allMethods) {
                Component component = addComponent(container, entry.getKey().getSimpleName(), entry.getValue().getName(), "", "");
                if (component != null) {
                    components.add(component);
                }
            }
        }
        return components;
    }

    private SimpleImmutableEntry<Class, Class> interfaceToImpReturnedFrom(Method method) {
        Class returnInterface = method.getReturnType();
        Class returnFirstImpl = method.getReturnType();
        if (returnInterface.isInterface()) {
            Class firstImplementationOfInterface = TypeUtils.findFirstImplementationOfInterface(returnInterface, getTypeRepository().getAllTypes());
            //firstImplementationOfInterface could be null when bean is defined inside class of packageToScan but impl is located in non scan package
            if (firstImplementationOfInterface != null) {
                returnFirstImpl = firstImplementationOfInterface;
            }
        }
        return new SimpleImmutableEntry<>(returnInterface, returnFirstImpl);
    }
}
