package tests;

import be.ac.ucl.info.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import student.ClosestPair;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;


public class ClosestPairTest {
   
    public static int[] closestPairCorrect(int [] input, int x) {
        Arrays.sort(input);
        int best = 0;
        int [] res = new int []{input[0],input[0]};
        int i = 0;
        int j = input.length-1;
        while (i < j) {
            if (input[i]+input[j] > x) {
                j--;
            }
            else if (input[i]+input[j] < x) {
                i++;
            }
            else {
                res[0] = input[i];
                res[1] = input[j];
                return res;
            }
            if (Math.abs(x-input[i]-input[j]) < Math.abs(x-res[0]-res[1])) {
                res[0] = input[i];
                res[1] = input[j];
            }
        }
        return res;
    }    


    /**
     *
     * @param input
     * @param res
     * @return true iff res[0] and res[1] are values from input
     */
    public boolean inArray(int [] input, int [] res) {
        boolean a = false;
        boolean b = false;
        for (int v : input) {
            a |= (v == res[0]);
            b |= (v == res[1]);
            if (a && b) return true;
        }
        return false;
    }


    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair0() {
        int [] input = new int [] {-5};

        int x = 155;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(165,Math.abs(x-res[0]-res[1]));
    }


 
    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair1() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 155;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(x,res[0]+res[1]);
    }


 
    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair2() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 36;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(x,res[0]+res[1]);
    }

 
    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair3() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 13;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(2,Math.abs(x-res[0]-res[1]));

    }


    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair4() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 170;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(1,Math.abs(x-res[0]-res[1]));
    }

    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair5() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = -1;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(3,Math.abs(x-res[0]-res[1]));
    }

    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair6() {
        int [] input = new int [] {5,10,1,150,151,155,18,50,30};

        int x = 1000;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(690,Math.abs(x-res[0]-res[1]));
    }


    @Test(timeout=300)
    @Grade(value=1) 
    public void testClosestPair7() {
        int [] input = new int [] {5,10,1,75,150,151,155,18,75,50,30};

        int x = 140;

        int [] res = ClosestPair.closestPair(input.clone(),x);
        assertTrue(inArray(input,res));
        assertEquals(2,res.length);
        assertEquals(10,Math.abs(x-res[0]-res[1]));
    }

    @Test(timeout=1000)
    @Grade(value=92)
    public void testClosestPairRandomWithTimeComplexity() {
        Random r = new Random();


        for (int i = 0; i < 100; i++) {

            int n = 100;
            int [] input = new int [n];
            for (int j = 0; j < n; j++) {
                input[j] = r.nextInt(n*n);
            }

            int x = n*n/2;
            int [] resCorrect = closestPairCorrect(input.clone(),x);
            int [] res = ClosestPair.closestPair(input.clone(),x);

            assertTrue(inArray(input,res));
            assertEquals(Math.abs(x-resCorrect[0]-resCorrect[1]),Math.abs(x-res[0]-res[1]));
        }

        for (int i = 0; i < 5; i++) {

            int n = 100000;
            int [] input = new int [n];
            for (int j = 0; j < n; j++) {
                input[j] = r.nextInt(n*n);
            }

            int x = n*n/2;
            int [] resCorrect = closestPairCorrect(input.clone(),x);
            int [] res = ClosestPair.closestPair(input.clone(),x);

            assertTrue(inArray(input,res));
            assertEquals(Math.abs(x-resCorrect[0]-resCorrect[1]),Math.abs(x-res[0]-res[1]));
        }

    }


}


