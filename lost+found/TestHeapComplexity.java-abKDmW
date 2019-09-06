import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static tests.INGIniousHelper.checkHeapiness;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestHeapComplexity {
    private int seed;

    public TestHeapComplexity(Integer seed) {
        this.seed = seed;
    }

    @Test(timeout=700)
    @Grade(value=1)
    public void runAsExpected() {
        Heap heap = new Heap(10);
        HashMap<Integer, Integer> counts = new HashMap<>();
        int size = 10000;
        int[] toInsert = generateRandom(size, seed);
        for(int i = 0; i < toInsert.length; i++) {
            heap.push(toInsert[i]);
            counts.put(toInsert[i], counts.getOrDefault(toInsert[i], 0)+1);
        }

        assertTrue(checkHeapiness(heap.getContent(), size, counts));
    }

    private int[] generateRandom(int size, int seed) {
        int[] todo = new int[size];
        Random r = new Random(seed);
        for(int i = 0; i < size; i++) {
            todo[i] = r.nextInt();
            if(todo[i] == Integer.MIN_VALUE)
                todo[i] = 0;
        }
        return todo;
    }

    @Parameterized.Parameters
    public static List<Object[]> data() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        for(int i = 0; i < 50; i++)
            list.add(new Object[] {i});
        return list;
    }
}
