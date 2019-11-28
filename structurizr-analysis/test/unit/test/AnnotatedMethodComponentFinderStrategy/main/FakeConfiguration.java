package test.AnnotatedMethodComponentFinderStrategy.main;

@Configuration
class FakeConfiguration {

    @Bean
    FakeComponent fakeComponent(FakeEfferentComponentImpl someEfferentComponent) {
        return new FakeComponentImpl(someEfferentComponent);
    }

    @Bean
    FakeEfferentComponentImpl fakeEfferentComponent() {
        return new FakeEfferentComponentImpl();
    }

    @Bean
    FakeComponent secondFakeComponent() {
        return new FakeComponentImpl(fakeEfferentComponent());
    }
}
