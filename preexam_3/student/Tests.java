import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(Tests.class);

        if (!result.wasSuccessful()) {
            for (Failure fail : result.getFailures()) {
                // Only displays the exception thrown if it is not a "normal" exception thrown by JUnit
                // for a failed test
                if (fail.getException() instanceof AssertionError) {
                    System.out.println(fail.getMessage());
                } else {
                    fail.getException().printStackTrace();
                }
            }
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @Test
    public void testSimple()
    {
        String message = "Test [0-1, 0-2, 0-3, 0-4] with 1 as source";
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(message, dfs.hasPathTo(0));
        assertTrue(message, dfs.hasPathTo(1));
        assertTrue(message, dfs.hasPathTo(2));
        assertTrue(message, dfs.hasPathTo(3));
        assertTrue(message, dfs.hasPathTo(4));

        assertEquals(message, 2, dfs.pathTo(3).size());
    }

    public void testDisconnected()
    {
        String message = "Test [0-1, 1-2, 3-4] with [1] as sources";
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);
    }

    public void testLoop()
    {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-5, 5-0] with [0] as sources";
        Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 0);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 0);
    }

    public void testMultipleSources()
    {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-5] with [1, 5] as sources";
        Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);
    }
}
