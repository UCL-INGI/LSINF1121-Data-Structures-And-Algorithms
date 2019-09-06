
import java.util.concurrent.*;


public abstract class TimeLimitedCodeBlock {

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

        try {
            future.get(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie) {
            /* Handle the interruption. Or ignore it. */
            return false;
        } catch (ExecutionException ee) {
            /* Handle the error. Or ignore it. */
        } catch (TimeoutException te) {
            /* Handle the timeout. Or ignore it. */
            return false;
        }
        if (!executor.isTerminated())
            executor.shutdownNow(); // If you want to stop the code that hasn't finished.
        return true;
    }

    public abstract void codeBlock();
}

