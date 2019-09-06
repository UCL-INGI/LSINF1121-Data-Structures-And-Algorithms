package src;
import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import templates.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TestDigraphComplexity {
    private Digraph student;
    private Digraph expected;

    public TestDigraphComplexity(Digraph student, Digraph expected) {
        this.student = student;
        this.expected = expected;
    }

    public void assertEqualsIterable(Iterable<Integer> one,Iterable<Integer> two) {
        ArrayList<Integer> oneList = new ArrayList<>();
        for (int i: one) {
            oneList.add(i);
        }
        ArrayList<Integer> twoList = new ArrayList<>();
        for (int i: two) {
            twoList.add(i);
        }
        Integer [] oneArray = oneList.toArray(new Integer[0]);
        Arrays.sort(oneArray);
        Integer [] twoArray = twoList.toArray(new Integer[0]);
        Arrays.sort(twoArray);
        assertArrayEquals("same adjacent nodes",oneArray,twoArray);
    }


    public void assertEqualsGraph(Digraph g1, Digraph g2) {
        assertEquals("same #nodes",g1.V(), g2.V());
        assertEquals("same #edges",g1.E(), g2.E());
        for (int i = 0; i < g1.V(); i++) {
            assertEqualsIterable(g1.adj(i),g2.adj(i));
        }
    }


    @Test(timeout=500)
    @Grade(value=25)
    public void sameRevert() {
        assertEqualsGraph(student.reverse(),expected.reverse());
    }


    @Parameterized.Parameters
    public static List<Object[]> data() throws IOException {
        List<Object[]> data = new ArrayList<>();

        int n = 10000;

        Digraph student1 = new DigraphImplem(n);
        Digraph correct1 = new DigraphImplemCorrect(n);

        for (int k = 0; k < n; k++) {

            student1.addEdge(k, (k + 1) % n);
            correct1.addEdge(k, (k + 1) % n);

        }
        data.add(new Object[]{student1, correct1});

        Digraph student2 = new DigraphImplem(n);
        Digraph correct2 = new DigraphImplemCorrect(n);

        for (int k = 1; k < n; k++) {

            student2.addEdge(0, k);
            correct2.addEdge(0, k);

        }
        data.add(new Object[]{student2, correct2});


        return data;
    }
}
