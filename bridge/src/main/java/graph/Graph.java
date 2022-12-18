package graph;

import draw.DrawingApi;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private final DrawingApi api;

    protected final int vertices;

    protected Graph(final DrawingApi api, final int vertices) {
        this.api = api;
        this.vertices = vertices;
    }

    public abstract void addEdge(final int from, final int to);

    protected abstract List<Pair<Integer, Integer>> getEdges();

    public void draw() {
        final List<Pair<Integer, Integer>> edges = getEdges();

        final Pair<Double, Double> centerPoint = new Pair<>(DrawingApi.width / 2.0, DrawingApi.height / 2.0);
        final double radius = Math.min(centerPoint.getFirst(), centerPoint.getSecond()) * 0.8;

        final List<Pair<Double, Double>> coords = new ArrayList<>();
        for (int v = 0; v < vertices; ++v) {
            final double rad = 2 * Math.PI * v / vertices;
            final Pair<Double, Double> point = new Pair<>(
                    radius * Math.cos(rad) + centerPoint.getFirst(),
                    radius * Math.sin(rad) + centerPoint.getSecond()
            );
            api.drawCircle(point, radius / 100);
            coords.add(point);
        }
        for (final Pair<Integer, Integer> edge : edges) {
            api.drawLine(coords.get(edge.getFirst()), coords.get(edge.getSecond()));
        }
        api.plot();
    }
}
