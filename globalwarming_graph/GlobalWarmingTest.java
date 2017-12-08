

import java.io.IOException;
import java.util.List;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;


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

 
            if (testShortestPathExam()) {
                score += 15;
            } else {
                feedback("test exam shortest path -15");
            }


            if (testCorrectnessShortestPath()) {
                score += 35;

                if (timeComplexityShortestPath()) {
                    score += 50;
                } else {
                    feedback("test complexity shortestPath (hint:could you stop earlier, do you really need to visit all positions?):-50");
                }

            } else {
                feedback("test correctness Path on random graphs:-35");
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

