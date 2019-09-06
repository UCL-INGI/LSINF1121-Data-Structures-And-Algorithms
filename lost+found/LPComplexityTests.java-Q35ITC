package tests;

import be.ac.ucl.info.javagrading.Grade;
import org.junit.Test;

import student.LinearProbingHashST;

import static org.junit.Assert.assertTrue;

public class LPComplexityTests {
    @Test(timeout = 300)
    @Grade(value = 50)
    public void complexityTest() {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(17);

        int i = 0;
        String sources[] = Helpers.readfile("data/hash-attack.txt", 3000);

        long t0 = System.currentTimeMillis();
        for (String key : sources){
            st.put(key, i);
            i++;
        }
        long t1 = System.currentTimeMillis();
        System.out.println("time elapsed=:"+(t1-t0));

        assertTrue(!st.isEmpty());

    }

}

