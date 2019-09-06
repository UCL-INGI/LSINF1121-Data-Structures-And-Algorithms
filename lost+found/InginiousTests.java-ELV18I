package src;

import com.github.guillaumederval.javagrading.Grade;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import templates.*;

public class InginiousTests {

    @Test
    @Grade(value=25)
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
    }

    @Test
    @Grade(value=25)
    public void testDisconnected()
    {
        String message = "Test [0-1, 1-2, 3-4] with 1 as source";
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 1);

        assertTrue(message, dfs.hasPathTo(0));
        assertTrue(message, dfs.hasPathTo(2));
        assertFalse(message, dfs.hasPathTo(3));
        assertFalse(message, dfs.hasPathTo(4));
    }

    @Test
    @Grade(value=25)
    public void testDiconnectedBis(){
        String message = "Test [0-1, 1-2, 3-4,4-5,5-6,5-7,7-8, 9-10,10-11,11-12] with 8 as source";
       	Graph graph = new Graph(13);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5,6);
        graph.addEdge(5,7);
        graph.addEdge(7,8);
        graph.addEdge(9,10);
        graph.addEdge(10,11);
        graph.addEdge(11,12);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 8);

        assertFalse(message, dfs.hasPathTo(0));
        assertFalse(message, dfs.hasPathTo(1));
        assertFalse(message, dfs.hasPathTo(2));

        assertTrue(message, dfs.hasPathTo(3));
        assertTrue(message, dfs.hasPathTo(4));
        assertTrue(message, dfs.hasPathTo(5));
        assertTrue(message, dfs.hasPathTo(6));
        assertTrue(message, dfs.hasPathTo(7));

        assertFalse(message, dfs.hasPathTo(9));
        assertFalse(message, dfs.hasPathTo(10));
        assertFalse(message, dfs.hasPathTo(11));
        assertFalse(message, dfs.hasPathTo(12));
    }

    @Test
    @Grade(value=25)
    public void testLoop()
    {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-0] with 0 as source";
        Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 0);

        assertTrue(message, dfs.hasPathTo(4));
    }

    /*@Test
    @Grade(value=20)
    public void testIterator()
    {
        String message = "Test [0-1, 1-2, 2-3, 3-4, 4-0] with 3 as source";
        Graph graph = new Graph(6);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        DepthFirstPaths dfs = new DepthFirstPaths(graph, 3);
        int[] pathCorr = new int[] {4, 0, 1, 2, 3};

        assertFalse(message, dfs.hasPathTo(5));
        assertFalse(dfs.pathTo(5) != null);

        assertTrue(message, dfs.hasPathTo(4));
        assertTrue(dfs.pathTo(4) != null);
        Iterable<Integer> path = dfs.pathTo(4);
        int k = 0;
        for (int i : path) {
            assertTrue(message,i == pathCorr[k++]);
        }

    }*/
}
