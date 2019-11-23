package test.AnnotatedMethodComponentFinderStrategy;

@Configuration
public class SomeConfiguration {

    @Bean
    public FakeComponent someComponent(FakeEfferentComponentImpl someEfferentComponent) {
        return new FakeComponentImpl(someEfferentComponent);
    }

    @Bean
    public FakeEfferentComponentImpl someEfferentComponent() {
        return new FakeEfferentComponentImpl();
    }

    @Bean
    public FakeComponent someComponentDuplicate() {
        return new FakeComponentImpl(someEfferentComponent());
    }
}
