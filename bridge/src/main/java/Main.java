import draw.AwtDrawingApi;
import draw.DrawingApi;
import draw.JavaFxDrawingApi;
import graph.MatrixGraph;
import graph.ListGraph;
import graph.Graph;

public class Main {
    public static void main(final String[] args) {
        System.err.println(args.length);
        if (args == null || args.length != 2) {
            System.err.println("Usage: fx/awt list/matrix");
            return;
        }

        DrawingApi api;
        switch (args[0]) {
            case "fx":
                api = new JavaFxDrawingApi();
                break;
            case "awt":
                api = new AwtDrawingApi();
                break;
            default:
                System.err.println("Expected fx or awt, found " + args[1]);
                return;
        }

        final int vertices = 13;
        Graph graph;
        switch (args[1]) {
            case "list":
                graph = new ListGraph(api, vertices);
                break;
            case "matrix":
                graph = new MatrixGraph(api, vertices);
                break;
            default:
                System.err.println("Expected list or matrix, found " + args[2]);
                return;
        }

        for (int v = 0; v < vertices; ++v) {
            for (int u = v + 1; u < vertices; ++u) {
                graph.addEdge(v, u);
            }
        }
        graph.draw();
    }
}
