import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


/**
 * Created by pschaus on 31/07/17.
 */
public class NodeQueueTest {


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


    public boolean testCorrect() {
        java.util.Queue<Integer> javaQueue = new ArrayDeque<>();
        Queue<Integer> queue = new NodeQueue<Integer>();
        Random r = new java.util.Random();

        int nb = 1000 + r.nextInt(1000);
        for (int i = 0; i < nb; i++) {
            int a = r.nextInt(1000);
            queue.enqueue(a);
            javaQueue.add(a);
        }
        try {
            for (int i = 0; i < nb; i++) {
                int a = queue.front();
                int b = javaQueue.peek();
                if (a != b) return false;

                a = queue.dequeue();
                b = javaQueue.poll();
                if (a != b) {
                    System.out.println("wrong value "+a+" "+b);
                    return false;
                }
                if (queue.size() != javaQueue.size()) return false;
                if (queue.isEmpty() != javaQueue.isEmpty()) return false;
            }
            boolean exceptionOk = false;
            try {
                queue.front();
            } catch (QueueEmptyException e) {
                exceptionOk = true;
            }
            return exceptionOk;


        } catch (Exception e) {
            return false;
        }
    }


    public boolean testTime() {

        java.util.Queue<Integer> javaQueue = new ArrayDeque<>();

        long t0 = System.currentTimeMillis();
        int nb = 10000000;
        for (int i = 0; i < nb; i++) {
            javaQueue.add(i);
            javaQueue.peek();
        }
        for (int i = 0; i < nb; i++) {
            javaQueue.poll();
        }
        long t1 = System.currentTimeMillis();

        long baseline = t1 - t0;


        boolean timeOk = new TimeLimitedCodeBlock() {
            @Override
            public void codeBlock() {
                try {

                    Queue<Integer> queue = new NodeQueue<Integer>();
                    for (int i = 0; i < nb; i++) {
                        queue.enqueue(i);
                        queue.front();
                    }
                    for (int i = 0; i < nb; i++) {

                        queue.dequeue();

                    }
                } catch (Exception e) {
                    feedback("bad idea 1!", -100, false);
                }
            }
        }.run((t1 - t0) * 10);
        return timeOk;
    }


    private void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void feedback(String message, int grade, boolean ok) {
        System.out.println(message);
        try {
            Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", "\n" + message + "\n"}).waitFor();
            Runtime.getRuntime().exec("feedback-grade "+grade).waitFor();
            
            if (ok ) Runtime.getRuntime().exec("feedback-result success").waitFor();
            else Runtime.getRuntime().exec("feedback-result failed").waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void testGrade() {
        int score = 0;

        try {
            if (testCorrect()) {
                score += 50;

                if (testTime()) {
                    score += 50;
                    feedback("All is correct!", score, true);
                    return;
                }  else {
                feedback("nicorrect time complexity :-50", score, false);
                    return;
                }
            } else {
                feedback("Incorrect behavior :-100", score, false);
                return;
            }
        } catch (Exception e) {
			feedback("Incorrect behavior:-100", score, false);
        }
    }

    public static void main(String[] args) {
        new NodeQueueTest().testGrade();
    }
}