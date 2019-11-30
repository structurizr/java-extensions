package test.AnnotatedMethodComponentFinderStrategy.main;

public class FakeComponentImpl implements FakeComponent {
    private final FakeEfferentComponentImpl someEfferentComponent;

    FakeComponentImpl(FakeEfferentComponentImpl someEfferentComponent) {
        this.someEfferentComponent = someEfferentComponent;
    }
}
