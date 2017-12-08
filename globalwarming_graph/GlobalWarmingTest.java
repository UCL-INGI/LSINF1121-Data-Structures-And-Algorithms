

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


    public static boolean relaxedTestNbIsland(GlobalWarming gw, int expected) {
        int res = gw.nbIslands();
        return res == expected || res == (expected + (gw.altitude.length*gw.altitude.length)-gw.nbSafePointsCorrect(gw.waterLevel));
    }

    public boolean testSafePointExam() {
        System.out.println("safe points returned (should be 14):"+new GlobalWarmingImpl(getExamMatrix(),3).nbSafePoints(2));
        return new GlobalWarmingImpl(getExamMatrix(),3).nbSafePoints(2) == 14;
    }

    public boolean testIslandsExam() {
        GlobalWarming g =  new GlobalWarmingImpl(getExamMatrix(),3);
        boolean ok = relaxedTestNbIsland(g,4);
        if (!ok) System.out.println("islands returned (should be 4):"+g.nbIslands());
        return ok;
    }

    public boolean testOnSameIslandExam() {
        GlobalWarming gw =  new GlobalWarmingImpl(getExamMatrix(),3);
        boolean ok1 = gw.onSameIsland(new GlobalWarming.Point(1,3),new GlobalWarming.Point(3,4)) == false;
        boolean ok2 = gw.onSameIsland(new GlobalWarming.Point(1,3),new GlobalWarming.Point(1,4)) == true;
        boolean ok3 = gw.onSameIsland(new GlobalWarming.Point(1,3),new GlobalWarming.Point(2,3)) == true;
        boolean ok4 = gw.onSameIsland(new GlobalWarming.Point(2,3),new GlobalWarming.Point(3,4)) == false;
        System.out.println("onsameIslandsExam:"+ok1+" "+ok2+" "+ok3+" "+ok4);
        return ok1 && ok2 && ok3 && ok4;
    }

    public boolean testShortestPathExam() {
        List<GlobalWarming.Point> path = new GlobalWarmingImpl(getExamMatrix(), 3).shortestPath(new GlobalWarming.Point(1, 0), new GlobalWarming.Point(3, 1));
        return path != null && path.size() == 4 && validPath(getExamMatrix(),3,point(1,0),point(3,1),path);
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
        GlobalWarming warming = new GlobalWarmingImpl(matrix,0);

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

        if (!relaxedTestNbIsland(warming,4)) {

            System.out.println("error in nbIslands"+ warming.nbIslands());
            return false;
        }

        if (warming.onSameIsland(point(0,0),point(0,0))) {
            System.out.println("error in onSameIsland");
            return false;
        }


        if (warming.onSameIsland(point(0, 0), point(0, 1))) {
            System.out.println("error in onSameIsland");
            return false;
        }

        if (!warming.onSameIsland(point(4,5),point(1,7))) {
            System.out.println("error in onSameIsland");
            return false;
        }

        List<GlobalWarming.Point> path1 = warming.shortestPath(point(1,1),point(1,1));

        if (!validPath(matrix,0,point(1,1),point(1,1),path1)) {
            System.out.println("error in shortestPath");
            return false;
        }

        if (!warming.shortestPath(point(9,9),point(9,9)).isEmpty()) {
            System.out.println("error in shortestPath");
            return false;
        }

        if (!warming.shortestPath(point(0,9),point(9,9)).isEmpty()) {
            System.out.println("error in shortestPath");
            return false;
        }

        List<GlobalWarming.Point> path2 = warming.shortestPath(point(4,5),point(1,7));
        if (!validPath(matrix,0,point(4,5),point(1,7),path2)) {
            System.out.println("error in shortestPath, path not valid");
            return false;
        }


        if (path2.size() != 8) {
            System.out.println("error in shortestPath, not correct length");
            System.out.println(path2.size());
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
            GlobalWarming g1 = new GlobalWarmingImpl(matrix,0);

            for (int l = 5; l < 10000000; l+= 10000) {
                if (g1.nbSafePoints(l) != nSafePoints(matrix,l)) {
                    return false;
                }
            }

        }
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix,0);

        if (warming.nbSafePoints(-1) != 100) {
            System.out.println("error in nbSafePoints");
            return false;
        }



        return true;
    }



    public boolean testCorrectnessShortestPath() {
        int level = 200000;
        GlobalWarming.Point p1 = point(10,10);
        GlobalWarming.Point p2 = point(15,15);

        for (int k = 0; k < 50; k++) {
            int [][] matrix = getRandomMatrix(50,1000000);
            GlobalWarming g1 = new GlobalWarmingImpl(matrix,level);

            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50-3; j++) {
                    if (matrix[i][j] > level && matrix[i][j+1] > level && matrix[i][j+2] > level) {

                        List<GlobalWarming.Point> path = g1.shortestPath(point(i,j),point(i,j+2));

                        if (path.size() != 3 && !validPath(matrix,level,point(i,j),point(i,j+2),path)) {
                            return false;
                        }
                    }
                }
            }

            List<GlobalWarming.Point> path1 = g1.shortestPath(point(10,10),point(15,15));
            //List<GlobalWarming.Point> path2 = g1.shortestPath(point(10,10),point(15,15));
            if (g1.onSameIsland(p1,p2) && !validPath(matrix,level,p1,p2,path1)) {
                return false;
            }
        }
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix,0);
        List<GlobalWarming.Point> path1 = warming.shortestPath(point(1,1),point(1,1));

        if (!validPath(matrix,0,point(1,1),point(1,1),path1)) {
            return false;
        }
        if (!warming.shortestPath(point(9,9),point(9,9)).isEmpty()) {
            return false;
        }
        if (!warming.shortestPath(point(0,9),point(9,9)).isEmpty()) {
            return false;
        }

        List<GlobalWarming.Point> path2 = warming.shortestPath(point(4,5),point(1,7));
        if (!validPath(matrix,0,point(4,5),point(1,7),path2)) {
            return false;
        }


        if (path2.size() != 8) {
            return false;
        }


        return true;
    }


    public boolean testCorrectnessOnSameIsland() {
        int level = 200000;
        GlobalWarming.Point p1 = point(10,10);
        GlobalWarming.Point p2 = point(15,15);


        for (int k = 0; k < 100; k++) {
            int [][] matrix = getRandomMatrix(100,1000000);
            GlobalWarming g1 = new GlobalWarmingImpl(matrix,level);

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100-3; j++) {
                    if (matrix[i][j] > level && matrix[i][j+1] > level && matrix[i][j+2] > level) {
                        if (!g1.onSameIsland(point(i,j),point(i,j+2))) {
                            return false;
                        }
                    }
                }
            }
        }
        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix,0);

        if (warming.onSameIsland(point(0,0),point(0,0))) {
            return false;
        }


        if (warming.onSameIsland(point(0, 0), point(0, 1))) {
            return false;
        }

        if (!warming.onSameIsland(point(4,5),point(1,7))) {
            return false;
        }


        return true;
    }

    public boolean testCorrectnessNbIslands() {

        int level = 200000;

        {
            int[][] matrix = getRandomMatrix(10, 10);

            if (!relaxedTestNbIsland(new GlobalWarmingImpl(matrix, 20),0)) {
                // all the bounds are under the sea level
                return false;
            }

            if (!relaxedTestNbIsland(new GlobalWarmingImpl(matrix, -20),1)) {
                // all the bounds are above the sea level

                return false;
            }

            matrix[0][0] = 30;
            matrix[0][1] = 30;
            matrix[0][9] = 30;
            matrix[9][0] = 30;
            matrix[9][9] = 30;
            matrix[9][8] = 30;

            if (!relaxedTestNbIsland(new GlobalWarmingImpl(matrix, 25),4)) {
                // all the bounds are above the sea level
                return false;
            }

        }

        for (int iter = 0; iter < 100; iter++) {


            int[][] matrix = new int[100][100];

            boolean [] generated = new boolean[10];
            int nIslandExpected = 0;
            for (int i = 0; i < rand.nextInt(10); i++) {
                int k = rand.nextInt(8);
                matrix[k*10][k*10] = 1;
                matrix[k*10+1][k*10] = 1;
                matrix[k*10][k*10+1] = 1;
                matrix[k*10+1][k*10+1] = 1;
                if (rand.nextBoolean()) {
                    matrix[k*10+2][k*10+1] = 1;
                }
                if (rand.nextBoolean()) {
                    matrix[k*10+2][k*10] = 1;
                }
                if (!generated[k]) {
                    generated[k] = true;
                    nIslandExpected += 1;
                }
            }
            if (!relaxedTestNbIsland(new GlobalWarmingImpl(matrix, 0),nIslandExpected)) {
                // all the bounds are above the sea level
                return false;
            }
        }

        int [][] matrix = getSimpleMatrix();
        GlobalWarming warming = new GlobalWarmingImpl(matrix,0);
        if (!relaxedTestNbIsland(warming,4)) {
            return false;
        }
        return true;
    }



    public boolean validPath(int [][] matrix, int level, GlobalWarming.Point p1, GlobalWarming.Point p2, List<GlobalWarming.Point> path) {
        for (GlobalWarming.Point p: path) {
            if (matrix[p.x][p.y] <= level) return false;
        }
        for (int i = 0; i < path.size()-1; i++) {
            if (!neighbors(path.get(i),path.get(i+1))) {
                return false;
            }
        }
        if (matrix[p1.x][p1.y] <= level && !path.isEmpty()) return false;
        if (matrix[p2.x][p2.y] <= level && !path.isEmpty()) return false;

        return !path.isEmpty() && path.get(0).equals(p1) && path.get(path.size()-1).equals(p2);
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
                    GlobalWarming g = new GlobalWarmingImpl(matrix,1000000 );
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

            final int [][] matrix = getRandomMatrix(500,2000000);
            final GlobalWarming g = new GlobalWarmingImpl(matrix,1000000 );

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;

                    // do some computation here
                    long t0 = System.currentTimeMillis();

                    g.nbSafePoints(1000000);

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

    public boolean timeComplexityNbIslands() {
        try {

            final int [][] matrix = getRandomMatrix(500,2000000);
            final GlobalWarming g = new GlobalWarmingImpl(matrix,1000000 );

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;

                    // do some computation here
                    long t0 = System.currentTimeMillis();

                    g.nbIslands();

                    long t1 = System.currentTimeMillis();
                    System.out.println("time nbIslands:"+(t1-t0));

                }
            }.run(5);
            if (!timeOk) return false;
            //Assert.assertTrue("correct time complexity", g.nCallsAdj < 3 * g.V());

        } catch (AssertionError e) {
            return false;
        }
        return true;
    }

    public boolean timeComplexityOnSameIsland() {
        try {

            final int [][] matrix = getRandomMatrix(500,2000000);
            final GlobalWarming g = new GlobalWarmingImpl(matrix,1000000 );

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;
                    // do some computation here
                    long t0 = System.currentTimeMillis();
                    int n = matrix.length;
                    for (int i = 0; i < n; i++){
                        for (int j = 0; j < n; j++) {
                            g.onSameIsland(point(i,j),point(n-1,n-1));
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    System.out.println("time onSameIsland:"+(t1-t0));
                }
            }.run(500);
            if (!timeOk) return false;
            //Assert.assertTrue("correct time complexity", g.nCallsAdj < 3 * g.V());

        } catch (AssertionError e) {
            return false;
        }
        return true;
    }

    public boolean timeComplexityShortestPath() {
        try {

            final int [][] matrix = getRandomMatrix(200,2000000);
            final GlobalWarming g = new GlobalWarmingImpl(matrix,1000000 );

            boolean timeOk = new TimeLimitedCodeBlock() {
                @Override
                public void codeBlock() {
                    int max = 0;
                    // do some computation here
                    long t0 = System.currentTimeMillis();
                    int n = matrix.length;
                    for (int i = 0; i < n; i++){
                        for (int j = 0; j < n; j++) {
                            g.shortestPath(point(i,j),point(n-1,n-1));
                        }
                    }
                    long t1 = System.currentTimeMillis();
                    System.out.println("time shortestPath:"+(t1-t0));
                }
            }.run(500);
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

            if (testOnSameIslandExam()) {
                score += 15;
            } else {
                System.out.println("exam on same island -15");
            }
            if (testShortestPathExam()) {
                score += 15;
            } else {
                System.out.println("exam shortest path -15");
            }
            if (testSafePointExam()) {
                score += 15;
            } else {
                System.out.println("exam safe point -15");
            }
            if (testIslandsExam()) {
                score += 15;
            } else {
                System.out.println("exam islands -15");
            }

            /*
            if (testSimpleAll()) {
                score += 5;

                if (timeComplexityConstructorCorrect()) {
                    score += 5;
                } else {
                    feedback("test complexity constructor:-10");
                }

            } else {
                feedback("test correctness all methods and constructor :-10");
            }*/


            if (testCorrectnessNbSafePoints()) {
                score += 5;

                if (timeComplexityNbSafePoints()) {
                    score += 5;
                } else {
                    feedback("test complexity nbSafePoints:-10");
                }

            } else {
                feedback("test correctness NbSafePoints on random graphs:-20");
            }


            if (testCorrectnessShortestPath()) {
                score += 10;

                if (timeComplexityShortestPath()) {
                    score += 10;
                } else {
                    feedback("test complexity shortestPath (hint:could you stop earlier, do you really need to visit all positions?):-10");
                }

            } else {
                feedback("test correctness Path on random graphs:-20");
            }


            if (testCorrectnessOnSameIsland()) {
                score += 10;

                if (timeComplexityOnSameIsland()) {
                    score += 10;
                } else {
                    feedback("test complexity nbIslands:-10");
                }
            } else {
                feedback("test correctness onSameIsland on random graphs:-20");
            }


            if (testCorrectnessNbIslands()) {
                score += 10;

                if (timeComplexityNbIslands()) {
                    score += 10;
                } else {
                    feedback("test complexity nbIslands:-10");
                }
            } else {
                feedback("test correctness nbIslands on random graphs:-20");
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

