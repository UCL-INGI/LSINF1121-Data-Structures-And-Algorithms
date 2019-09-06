package src;
import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import templates.*;

import java.util.HashMap;

import static src.INGIniousHelper.checkHeapiness;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHeapFirst {
    @Test
    @Grade(value=25)
    public void basicTest() {
        HashMap<Integer, Integer> counts = new HashMap<>();
        Heap heap = new Heap(10);

        int[] content = new int[]{5, 1, 2, 3, 8, 10, 6, 0};
        for(int x: content) {
            counts.put(x, counts.getOrDefault(x, 0)+1);
            heap.push(x);
        }

        assertEquals(8, heap.getSize());
        assertTrue(checkHeapiness(heap.getContent(), 8, counts));

    }
}
