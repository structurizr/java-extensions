package com.structurizr.analysis;

import com.structurizr.model.Component;
import com.structurizr.model.Container;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A generic component finder strategy that finds components based on returned types of specifically annotated methods.
 * To filter scanned classes for specifically annotated methods specify classAnnotation as well.
 * This strategy ignores duplicates and uses first found implementation as a type property for a Component.
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
        this(methodAnnotation, null, new ReferencedTypesSupportingTypesStrategy());
    }

    /**
     * Construct strategy that finds components based on return type of methods annotated with {@code methodAnnotation}
     * which belong only to classes annotated with {@code classAnnotation}
     *
     * @param methodAnnotation the annotation to look for on methods
     * @param classAnnotation  the annotation to look for on classes/interfaces
     */
    public AnnotatedMethodComponentFinderStrategy(Class<? extends Annotation> methodAnnotation, Class<? extends Annotation> classAnnotation) {
        this(methodAnnotation, classAnnotation, new ReferencedTypesSupportingTypesStrategy());
    }

    /**
     * Construct strategy that finds components based on return type of methods annotated with {@code methodAnnotation}
     * which belong only to classes annotated with {@code classAnnotation}.
     *
     * @param methodAnnotation the annotation to look for on methods
     * @param classAnnotation  the annotation to look for on classes/interfaces to narrow scanned scope of methods
     * @param strategies       strategies to look for supporting types for a component
     *                         (component hides all types which support defined in component functionality behind abstraction)
     */
    public AnnotatedMethodComponentFinderStrategy(
            @Nonnull Class<? extends Annotation> methodAnnotation,
            @Nullable Class<? extends Annotation> classAnnotation,
            SupportingTypesStrategy... strategies
    ) {
        super(strategies);
        this.classAnnotation = classAnnotation;
        this.methodAnnotation = methodAnnotation;
    }

    @Override
    protected Set<Component> doFindComponents() {
        Set<Component> components = new HashSet<>();
        Container container = getComponentFinder().getContainer();

        Set<URL> classUrls = new HashSet<>();
        if (classAnnotation != null) {
            classUrls.addAll(findTypesAnnotatedWith(classAnnotation).stream().map(this::toUrl).collect(Collectors.toSet()));
        } else {
            classUrls.addAll(getComponentFinder().getTypeRepository().getAllTypes().stream().map(this::toUrl).collect(Collectors.toSet()));
        }

        ConfigurationBuilder config = new ConfigurationBuilder().addUrls(classUrls).addScanners(new MethodAnnotationsScanner());
        Set<SimpleImmutableEntry<Class, Class>> methodReturnTypes = new Reflections(config).getMethodsAnnotatedWith(methodAnnotation)
                .stream().map(this::interfaceToImpReturnedFrom).collect(Collectors.toSet());

        for (SimpleImmutableEntry<Class, Class> entry : methodReturnTypes) {
            Component component = container.addComponent(entry.getKey().getSimpleName(), entry.getValue(), "", "");
            components.add(component);
        }
        return components;
    }

    private URL toUrl(Class clazz) {
        if (getComponentFinder().getUrlClassLoader() == null) {
            return ClasspathHelper.forClass(clazz);
        }
        return ClasspathHelper.forClass(clazz, getComponentFinder().getUrlClassLoader());
    }

    private SimpleImmutableEntry<Class, Class> interfaceToImpReturnedFrom(Method method) {
        Class returnInterface = method.getReturnType();
        Class returnFirstImpl = method.getReturnType();
        if (returnInterface.isInterface()) {
            returnFirstImpl = TypeUtils.findFirstImplementationOfInterface(returnInterface, getTypeRepository().getAllTypes());
        }
        return new SimpleImmutableEntry<>(returnInterface, returnFirstImpl);
    }
}
