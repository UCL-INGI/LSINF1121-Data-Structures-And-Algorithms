import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static tests.INGIniousHelper.checkHeapiness;
import static org.junit.Assert.assertTrue;
import static tests.INGIniousHelper.readTestData;

@RunWith(Parameterized.class)
public class TestHeapEasy {
    private int[] toInsert;

    public TestHeapEasy(int[] toInsert, int[][] expected, int[] expectedSteps) {
        this.toInsert = toInsert;
    }

    @Test
    @Grade(value=1)
    public void runAsExpected() {
        Heap heap = new Heap(10);
        HashMap<Integer, Integer> counts = new HashMap<>();

        for(int i = 0; i < toInsert.length; i++) {
            counts.put(toInsert[i], counts.getOrDefault(toInsert[i], 0)+1);
            heap.push(toInsert[i]);
            assertTrue(checkHeapiness(heap.getContent(), i+1, counts));
        }
    }

    @Parameterized.Parameters
    public static List<Object[]> data() throws IOException {
        return readTestData();
    }
}
