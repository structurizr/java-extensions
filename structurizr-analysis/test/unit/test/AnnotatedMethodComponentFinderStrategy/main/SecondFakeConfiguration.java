package test.AnnotatedMethodComponentFinderStrategy.main;

class SecondFakeConfiguration {

    @Bean
    SecondFakeComponent secondFakeComponent() {
        return new SecondFakeComponent();
    }

    static class SecondFakeComponent {
    }
}
