package test.AnnotatedMethodComponentFinderStrategy.other;

import test.AnnotatedMethodComponentFinderStrategy.main.Bean;
import test.AnnotatedMethodComponentFinderStrategy.main.FakeComponent;

class OtherConfiguration {

    @Bean
    FakeComponent otherFakeComponent() {
        return new OtherFakeComponentImpl();
    }

    class OtherFakeComponentImpl implements FakeComponent {
    }
}
