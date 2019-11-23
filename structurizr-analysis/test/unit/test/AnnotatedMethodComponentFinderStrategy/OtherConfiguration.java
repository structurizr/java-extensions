package test.AnnotatedMethodComponentFinderStrategy;

public class OtherConfiguration {

    @Bean
    public OtherFakeComponent otherFakeComponent() {
        return new OtherFakeComponent();
    }

    static class OtherFakeComponent {
    }
}
