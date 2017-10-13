

import java.io.IOException;
import java.util.List;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


/**
 * Created by pschaus on 31/07/17.
 */
public class GlobalWarmingTest {

    final int [] seeds = new int[]{0,7,13};

    Random rand = new Random(seeds[new java.util.Random().nextInt(3)]);

    abstract class TimeLimitedCodeBlock {

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




    public int [][] getSimpleMatrix() {
        int[][] matrix = new int[][]{
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        return matrix;
    }


    public int [][] getExamMatrix() {
        int [][] tab = new int[][] {{1,3,3,1,3},
                {4,2,2,4,5},
                {4,4,1,4,2},
                {1,4,2,3,6},
                {1,1,1,6,3}};
        return tab;
    }



    public boolean testSafePointExam() {
        System.out.println("safe points returned (should be 14):"+new GlobalWarmingImpl(getExamMatrix()).nbSafePoints(2));
        return new GlobalWarmingImpl(getExamMatrix()).nbSafePoints(2) == 14;
    }

    public int [][] getRandomMatrix(int n,int bound) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextInt(bound);
            }
        }
        return matrix;
    }


    public static GlobalWarming.Point point(int x, int y) {
        return new GlobalWarming.Point(x,y);
    }

    public boolean testSimpleAll() {
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix);

        if (warming.nbSafePoints(-1) != 100) {
            System.out.println("error in nbSafePoints");
            return false;
        }

        if (warming.nbSafePoints(0) != 24) {
            System.out.println("error in nbSafePoints");
            return false;
        }

        if (warming.nbSafePoints(1) != 0) {
            System.out.println("error in nbSafePoints");
            return false;
        }
        return true;
    }

    public static int nSafePoints(int [][] matrix,int level) {
        int c = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > level) c++;
            }
        }
        return c;
    }


    public boolean testCorrectnessNbSafePoints() {
        for (int i = 0; i < 100; i++) {
            int [][] matrix = getRandomMatrix(100,1000000);
            GlobalWarming g1 = new GlobalWarmingImpl(matrix);

            for (int l = 5; l < 10000000; l+= 10000) {
                if (g1.nbSafePoints(l) != nSafePoints(matrix,l)) {
                    return false;
                }
            }

        }
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix);

        if (warming.nbSafePoints(-1) != 100) {
            System.out.println("error in nbSafePoints");
            return false;
        }


        return true;
    }



    public boolean neighbors(GlobalWarming.Point p1,GlobalWarming.Point p2) {
        return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p2.y) == 1;
    }


    public boolean timeComplexityConstructorCorrect() {
        try {

            final int [][] matrix = getRandomMatrix(400,2000000);

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;

                    // do some computation here
                    long t0 = System.currentTimeMillis();
                    GlobalWarming g = new GlobalWarmingImpl(matrix);
                    long t1 = System.currentTimeMillis();
                    System.out.println("time constructor:"+(t1-t0));


                }
            }.run(500);
            if (!timeOk) return false;
            //Assert.assertTrue("correct time complexity", g.nCallsAdj < 3 * g.V());

        } catch (AssertionError e) {
            return false;
        }
        return true;
    }

    public boolean timeComplexityNbSafePoints() {
        try {

            final int [][] matrix = getRandomMatrix(100,2000000);
            final GlobalWarming g = new GlobalWarmingImpl(matrix);

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;

                    // do some computation here
                    long t0 = System.currentTimeMillis();

                    for (int i = 0; i < 1000; i++) {
                        g.nbSafePoints(1000000);
                    }

                    /*
                    int n = matrix.length;
                    for (int a = 0; a < n; a++) {
                        for (int b = 0; b < n; b++) {
                            if (matrix[a][b]> max) max = matrix[a][b];

                        }
                    }*/

                    long t1 = System.currentTimeMillis();
                    System.out.println("time safepoints:"+(t1-t0));


                }
            }.run(10);
            if (!timeOk) return false;
            //Assert.assertTrue("correct time complexity", g.nCallsAdj < 3 * g.V());

        } catch (AssertionError e) {
            return false;
        }
        return true;
    }

   







    private void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        }
        catch (IOException e) {e.printStackTrace(); }
        catch (InterruptedException e) {e.printStackTrace(); }
    }

    private void feedback(String message) {
        System.out.println(message);
        try {
            Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", "\n"+message+"\n"}).waitFor();
        }
        catch (IOException e) {e.printStackTrace(); }
        catch (InterruptedException e) {e.printStackTrace(); }
    }


    public void testGrade() {
        int score = 0;

        try {
           
            if (testSafePointExam()) {
                score += 10;
            } else {
                System.out.println("exam safe point -10");
            }
 
            if (testSimpleAll()) {
                score += 10;

                if (timeComplexityConstructorCorrect()) {
                    score += 20;
                } else {
                    feedback("test complexity constructor:-20");
                }

            } else {
                feedback("test correctness all methods and constructor :-30");
            }


            if (testCorrectnessNbSafePoints()) {
                score += 30;

                if (timeComplexityNbSafePoints()) {
                    score += 30;
                } else {
                    feedback("test complexity nbSafePoints:-30");
                }

            } else {
                feedback("test correctness NbSafePoints on random matrices:-60");
            }

        } catch (Exception e) {

        }

        System.out.println("%score:" + score);


        exec("feedback-grade "+score);


        if (Math.min(score,100) == 100) {
            feedback("Congratulations");
            exec("feedback-result success");
        }
        else {
            feedback("Not yet there");
            exec("feedback-result failed");
        }


    }

    public static void main(String[] args) {
        new GlobalWarmingTest().testGrade();
    }
}


