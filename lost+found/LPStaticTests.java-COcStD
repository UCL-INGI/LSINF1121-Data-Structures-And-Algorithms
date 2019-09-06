package tests;

import be.ac.ucl.info.javagrading.Grade;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import student.LinearProbingHashST;

import static org.junit.Assert.assertTrue;

public class LPStaticTests {

    @Test
    @Grade(value = 10)
    public void staticTest() {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(11);
        String str = "S E A R C H E X A M P L E";

        String expectedKeys = "X C E H L M P R S A ";
        String expectedVals = "7 4 12 5 11 9 10 3 0 8 ";

        int i = 0;
        for (String key : str.split(" ")){
            st.put(key, i);
            i++;
        }

        // print keys
        String obtainedKeys = "";
        String obtainedVals = "";
        for (String s : st.keys()) {
            obtainedKeys += s + " ";
            obtainedVals += st.get(s) + " ";
        }

        assertEquals(obtainedKeys,expectedKeys);
        assertEquals(obtainedVals,expectedVals);

    }


    @Test
    @Grade(value = 20)
    public void correctnessTest() {

        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>(7);

        String instance[] = generateInstance();
        Integer expectedVals[] = {12, 13, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15};

        int i = 0;
        for (String key : instance){
            st.put(key, i);
            i++;
        }

        //System.out.println("cap="+instance.capaity);
        i = 0;
        for (String s : st.keys()) {
            assertEquals(expectedVals[i],st.get(s));
            assertEquals(instance[expectedVals[i]],s);
            i++;
        }

        assertTrue(i != 0);
    }

    public String[] generateInstance(){
        String[] sp = new String[]{"Aa", "BB"};
        String[] list = new String[16];

        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 0; l < 2; l++) {
                        list[count] = sp[i] + sp[j] + sp[k] + sp[l];
                        count++;
                    }
                }
            }
        }

        return list;
    }

}


