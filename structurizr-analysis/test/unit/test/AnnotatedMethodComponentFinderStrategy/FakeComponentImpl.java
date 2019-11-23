package test.AnnotatedMethodComponentFinderStrategy;

public class FakeComponentImpl implements FakeComponent {
    private final FakeEfferentComponentImpl someEfferentComponent;

    public FakeComponentImpl(FakeEfferentComponentImpl someEfferentComponent) {
        this.someEfferentComponent = someEfferentComponent;
    }
}
