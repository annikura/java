import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleLazyTest {

    @Test
    public void createInstance() {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(new TestUtils.LifeMeaningSupplier());
        assertEquals(42, (int) lazy.get());
    }

    @Test
    public void multipleCall() {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(new TestUtils.CountingSupplier());
        for (int i = 0; i < 20; i++) {
            assertEquals(1, (int) lazy.get());
        }
    }
}