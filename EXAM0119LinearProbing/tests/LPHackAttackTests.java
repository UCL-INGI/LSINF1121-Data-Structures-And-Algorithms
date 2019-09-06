package tests;

import be.ac.ucl.info.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Random;

import student.LinearProbingHashST;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LPHackAttackTests {
    private Instance instance;
    private static final int TEST_SIZE = 10;
    private static final int INSTANCE_SIZE = 5;

    public LPHackAttackTests(Instance instance) {
        this.instance = instance;
    }

    @Test
    @Grade(value = 4)
    public void HackAttackTest() {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(instance.capaity);

        int i = 0;
        for (String key : instance.words){
            st.put(key, i);
            i++;
        }

        //System.out.println("cap="+instance.capaity);

        i = 0;
        for (String s : st.keys()) {
            assertEquals(new Integer(i),st.get(s));
            assertEquals(instance.words[i],s);
            i++;
        }

        assertTrue(i != 0);
    }

    @Parameterized.Parameters
    public static Instance[] data() {
        Random wordsRandom = new Random(12345L);
        Random capacityRandom = new Random(456L);

        String sources[] = Helpers.readfile("data/hash-attack.txt", 1000);

        Instance[] instances = new Instance[INSTANCE_SIZE];

        for (int j = 0; j < INSTANCE_SIZE; j++) {
            String[] tests = new String[TEST_SIZE];
            for (int i = 0; i < TEST_SIZE; i++) {
                tests[i] = sources[wordsRandom.nextInt(999)+1];
            }

            instances[j] = new Instance(tests, capacityRandom.nextInt(TEST_SIZE)+1 );
        }

        return instances;
    }

    private static class Instance{
        String[] words;
        int capaity;

        public Instance(String[] words, int capaity) {
            this.words = words;
            this.capaity = capaity;
        }
    }

}

