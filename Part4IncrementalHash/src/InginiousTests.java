package src;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.junit.Test;

import java.util.*;
import templates.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Random;


public class InginiousTests {

    public static class IncrementalHashBaseLine {


        private static final int R = 31;
        private int M;
        private int RM;
        private int Q;

        /**
         *
         * @param Q is the modulo to apply
         * @param M is the length of the words to hash
         */
        public IncrementalHashBaseLine(int Q, int M) {
            assert(M > 0);
            assert(Q > 0);
            this.Q = Q;
            this.M = M;
            // computes (R^(M-1))%Q
            // which might be useful for implementing nextHash
            RM = 1;
            for (int i = 1; i <= M-1; i++) {
                RM = (RM * R)%Q;
            }
        }

        /**
         * Compute the hash function on the substring
         * t[from,...,from+M-1] in O(1)
         * @param t
         * @param previousHash = hash(from-1)
         * @param 0 < from <= t.length-M
         * @return (t[from]*R^(M-1)+t[from+1]*R^(M-1)+...+t[from+M-1])%Q
         */
        public int nextHash(char[] t, int previousHash, int from) {
            int tmp = previousHash+Q-((t[from-1]*RM)%Q);
            return ((tmp*R)%Q+t[from+M-1])%Q;
        }


        /**
         * Compute the hash function on the substring
         * t[from,...,from+M-1] in O(M)
         * @param t
         * @param 0 <= from <= t.length-M
         * @return (t[from]*R^(M-1)+t[from+1]*R^(M-1)+...+t[from+M-1])%Q
         */
        public int hash(char[] t, int from) {
            int h = 0;
            for (int i = from; i < from+M; i++) {
                h = (R*h+t[i]) % Q;
            }
            return h;
        }

    }


    public boolean correct(int size, int Q, int M, int maxChar) {
        Random rnd = new Random(0);
        char [] input = new char[size];
        for (int i = 0; i < input.length; i++) {
            input[i] = (char) rnd.nextInt(maxChar);
        }
        IncrementalHash hasher = new IncrementalHash(Q,M);
        IncrementalHashBaseLine hasherb = new IncrementalHashBaseLine(Q,M);

        int prevHash = hasherb.hash(input,0);
        for (int i = 1; i < input.length-M; i++) {
            int h1 = hasher.nextHash(input,prevHash,i);
            int h2 = hasherb.nextHash(input,prevHash,i);
            if (h1 != h2) return false;
            prevHash = h1;
        }
        return true;
    }

    @Test(timeout=500)
    @Grade(value=80)
    public void timeComplexityOK() {
        /*boolean timeOk = new TimeLimitedCodeBlock() {
            @Override
            public void codeBlock() {
                correct((int)10E5,100,3000,65536);
            }
        }.run(500);
        assertTrue("the nextHash should execute in O(1):-100\n",timeOk);*/
        correct((int)10E5,100,3000,65536);
    }

	@Test
    @Grade(value=10)
    public void hashCorrectOnWords5InputUpTo3() {
        assertTrue("wrong nextHash value returned with input values <= 3: -40\n",correct(1000,100,5,3));
    }


    @Test
    @Grade(value=6)
    public void hashCorrectOnWords5InputUpTo10() {
        assertTrue("wrong nextHash value returned with input values <= 10, be careful with int overflow: -20\n",correct(1000,100,5,10));
    }

    @Test
    @Grade(value=4)
    public void hashCorrectOnWords5InputUpTo65536() {
        assertTrue("wrong nextHash value returned with input values <= 65536: be careful with int overflow: -20\n",correct(1000,100,5,65536));
    }


/*

    public void feedback(String message) {
        System.out.println(message);
        try {
            Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", message}).waitFor();
        }
        catch (IOException e) {e.printStackTrace(); }
        catch (InterruptedException e) {e.printStackTrace(); }
    }


    public void grade() {
        int score = 0;

        boolean timeComplexityOk = timeComplexityOK();

        if (!timeComplexityOk) {
            feedback("the nextHash should execute in O(1):-100\n");
        } else {
            if (hashCorrectOnWords5InputUpTo3())
                score += 50;
            else {
                feedback("wrong nextHash value returned with input values <= 3: -40\n");
            }
            if (hashCorrectOnWords5InputUpTo10())
                score += 30;
            else {
                feedback("wrong nextHash value returned with input values <= 10, be careful with int overflow: -20\n");
            }
            if (hashCorrectOnWords5InputUpTo65536())
                score += 20;
            else {
                feedback("wrong nextHash value returned with input values <= 65536: be careful with int overflow: -20\n");
            }
        }
        System.out.println("%score:" + score);
        try {
            Runtime.getRuntime().exec("feedback-grade "+score);

            if (score == 100) {
                Runtime.getRuntime().exec("feedback-result success");
                Runtime.getRuntime().exec("feedback-msg -ae -m \"congratulation\n\"");
            }
            else {
                Runtime.getRuntime().exec("feedback-msg -ae -m \"not yet there ...\n\"");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new IncrementalHashTest().grade();
        System.exit(0);
    }*/
}
