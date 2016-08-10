import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Tests {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(Tests.class);

        if (!result.wasSuccessful()) {
            for (Failure fail : result.getFailures()) {
                System.out.println(fail.getMessage());
            }
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @Test
    public void testSimple()
    {
        String message = "Test [0-1, 0-2, 0-3, 0-4] with [1] as sources";
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);

        BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1));

        assertEquals(message, 0, bfs.distTo(1));
        assertEquals(message, 1, bfs.distTo(0));
        assertEquals(message, 2, bfs.distTo(2));
        assertEquals(message, 2, bfs.distTo(3));
        assertEquals(message, 2, bfs.distTo(4));
    }

    @Test
    public void testDisconnected()
    {
        String message = "Test [0-1, 1-2, 3-4] with [1] as sources";
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1));
        assertEquals(message, 1, bfs.distTo(0));
        assertEquals(message, 1, bfs.distTo(2));

        assertEquals(message, BreadthFirstShortestPaths.INFINITY, bfs.distTo(3));
        assertEquals(message, BreadthFirstShortestPaths.INFINITY, bfs.distTo(4));
    }

    @Test
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

        BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(0));
        assertEquals(message, 0, bfs.distTo(0));
        assertEquals(message, 1, bfs.distTo(1));
        assertEquals(message, 2, bfs.distTo(2));
        assertEquals(message, 3, bfs.distTo(3));
        assertEquals(message, 2, bfs.distTo(4));
        assertEquals(message, 1, bfs.distTo(5));
    }

    @Test
    public void testMultipleSources()
    {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-5] with [1, 5] as sources";
        Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        BreadthFirstShortestPaths bfs = new BreadthFirstShortestPaths(graph, Arrays.asList(1, 5));
        assertEquals(message, 1, bfs.distTo(0));
        assertEquals(message, 0, bfs.distTo(1));
        assertEquals(message, 1, bfs.distTo(2));
        assertEquals(message, 2, bfs.distTo(3));
        assertEquals(message, 1, bfs.distTo(4));
        assertEquals(message, 0, bfs.distTo(5));
    }
}
