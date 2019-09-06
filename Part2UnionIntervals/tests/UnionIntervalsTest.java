package tests;

/*to be added for grading and test*/
import be.ac.ucl.info.javagrading.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import student.Union;
import student.Interval;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * This is just a limited number of tests provided for convenience
 * Don't hesitate to extend it with other tests
 */
public class UnionIntervalsTest {

    public static Interval [] unionSolution(Interval [] intervals) {
        if (intervals.length == 0) return intervals;
        Arrays.sort(intervals);
        int min = intervals[0].getMin();
        int max = intervals[0].getMax();
        ArrayList<Interval> res = new ArrayList<Interval>();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].getMin() > max) {
                // close
                res.add(new Interval(min,max));
                min = intervals[i].getMin();
                max = intervals[i].getMax();
            } else {
                max = Math.max(max, intervals[i].getMax());
            }
        }
        res.add(new Interval(min,max));
        return res.toArray(new Interval[0]);
    }

    public static boolean test(Interval[] input, Interval[] expectedOutput) {
        Interval [] result = Union.union(input);
        boolean ok = Arrays.equals(expectedOutput,result);
        if (!ok) {
            String res = "----bug found----\ninput:";
            for (int i = 0; i < input.length; i++) {
                System.out.println("=>"+input[i]);
            }

            for (Interval i: input) res += i;
            res += "\nexpected output:";
            for (Interval i: expectedOutput) res += i;
            res += "\nactual output:";
            for (Interval i: result) res += i;
            feedback(res+"\n");
        }
        return ok;
    }

    @Test
    @Grade(value=25)
    public void testUnits() {
        boolean ok = true;
        if (ok) ok = test(new Interval[]{},new Interval[]{});
        if (ok) {
            Interval i1 = new Interval(1, 3);
            Interval i2 = new Interval(1, 3);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 3)});
        }
        if (ok) {
            Interval i1 = new Interval(1, 3);
            Interval i2 = new Interval(2, 4);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 4)});
        }
        if (ok) {
            Interval i1 = new Interval(1, 2);
            Interval i2 = new Interval(2, 4);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 4)});
        }
        if (ok) {
            Interval i1 = new Interval(1, 2);
            Interval i2 = new Interval(3, 4);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 2), new Interval(3, 4)});
        }
        if (ok) {
            Interval i1 = new Interval(1, 2);
            Interval i2 = new Interval(2, 2);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 2)});
        }
        if (ok) {
            Interval i1 = new Interval(1, 1);
            Interval i2 = new Interval(2, 2);
            ok = test(new Interval[]{i1, i2},new Interval[]{new Interval(1, 1), new Interval(2, 2)});
        }
        if (ok) {
            Interval i0 = new Interval(7, 9);
            Interval i1 = new Interval(5, 8);
            Interval i2 = new Interval(2, 4);
            ok = test(new Interval[]{i0, i1, i2},new Interval[] {new Interval(2, 4), new Interval(5, 9)});
        }
        if (ok) {
            Interval i0 = new Interval(10, 10);
            Interval i1 = new Interval(2, 4);
            Interval i2 = new Interval(3, 4);
            Interval i3 = new Interval(5, 6);
            Interval i4 = new Interval(6, 9);
            Interval i5 = new Interval(6, 8);
            ok = test(new Interval[]{i0, i1, i2, i3, i4, i5},new Interval[]{new Interval(2, 4), new Interval(5, 9), new Interval(10, 10)});
        }
        assertTrue(ok);

    }


    public static Interval randomInterval(Random rand) {
        int min = rand.nextInt(20);
        return new Interval(min, min + rand.nextInt(4));
    }

    private static boolean testRandom2() {
        int [] seeds = new int[] {1,5,7,11,13};

        Random rand = new java.util.Random(seeds[2]);
        boolean ok = true;

        for (int i = 0; i < 500 & ok; i++) {
            Interval[] intervals = new Interval[10];
            for (int k = 0; k < intervals.length; k++) {
                intervals[k] = randomInterval(rand);
            }
            ok = test(intervals,unionSolution(intervals));
        }
        return ok;
    }
	
    @Test(timeout=1000)
    @Grade(value=25)
    public void testRandom() {

        final boolean [] ok = new boolean[]{false};
        ok[0] = testRandom2();
        
        
        assertTrue(ok[0]);
    }

    abstract static class TimeLimitedCodeBlock {

        public boolean run(long time) {
            final Runnable stuffToDo = new Thread() {
                @Override
                public void run() {
                    codeBlock();
                }
            };

            final ExecutorService executor = Executors.newSingleThreadExecutor();
            final Future future = executor.submit(stuffToDo);
            executor.shutdown(); // This does not cancel the already-scheduled task.
            boolean ok = true;
            try {
                future.get(time, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ie) {
                ok = false;
            } catch (ExecutionException ee) {
                ok = false;
            } catch (TimeoutException te) {
                ok = false;
            }
            if (!executor.isTerminated()) {
                future.cancel(true);
                executor.shutdownNow();
                executor.shutdownNow(); // If you want to stop the code that hasn't finished.
            }
            return ok;
        }

        public abstract void codeBlock();
    }

	@Test(timeout=11000)
    @Grade(value=50)
    public void testComplexity() {
        final Interval[] intervals = new Interval[1000000];
        Random rand = new java.util.Random();
        for (int k = 0; k < intervals.length; k++) {
            int min = rand.nextInt(Integer.MAX_VALUE-1000);
            intervals[k] = new Interval(min, min + rand.nextInt(1000));
        }

        Interval [] res = Union.union(intervals);
    }
    
    /*
    public void testComplexity() {
        final Interval[] intervals = new Interval[1000000];
        Random rand = new java.util.Random();
        for (int k = 0; k < intervals.length; k++) {
            int min = rand.nextInt(Integer.MAX_VALUE-1000);
            intervals[k] = new Interval(min, min + rand.nextInt(1000));
        }

        boolean timeOk = new TimeLimitedCodeBlock() {
            @Override
            public void codeBlock() {
                Interval [] res = Union.union(intervals);
            }
        }.run(10000);
        return timeOk;
    }*/


    private static void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    private static void feedback(String message) {
        System.out.println(message);
        try {
            Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", "\n" + message + "\n"}).waitFor();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) {
        int grade = 0;
        if (testUnits()) {
            grade += 25;
        }
        if (testRandom()) {
            grade += 25;
        }
        if (grade == 50 && testComplexity()) {
            grade += 50;
        }

        System.out.println("%grade:" + grade);

        exec("feedback-grade "+grade);


        if (grade == 100) {
            feedback("Congratulations");
            exec("feedback-result success");
        }
        else {
            feedback("Not yet good (bugs and/or time-complexity)");
            exec("feedback-result failed");
        }


    }*/


}
