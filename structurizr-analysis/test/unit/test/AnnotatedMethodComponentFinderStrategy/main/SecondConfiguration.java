package test.AnnotatedMethodComponentFinderStrategy.main;

class SecondConfiguration {

    @Bean
    SecondFakeComponent otherFakeComponent() {
        return new SecondFakeComponent();
    }

    static class SecondFakeComponent {
    }
}
