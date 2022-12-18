package graph;

import draw.DrawingApi;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MatrixGraph extends Graph {
    private final List<List<Boolean>> matrix = new ArrayList<>();

    public MatrixGraph(final DrawingApi api, final int vertices) {
        super(api, vertices);

        for (int v = 0; v  < vertices; ++v) {
            final List<Boolean> row = new ArrayList<>();
            for (int u = 0; u < vertices; ++u) {
                row.add(false);
            }
            matrix.add(row);
        }
    }

    @Override
    public void addEdge(final int from, final int to) {
        matrix.get(from).set(to, true);
    }

    @Override
    protected List<Pair<Integer, Integer>> getEdges() {
        final List<Pair<Integer, Integer>> edges = new ArrayList<>();
        for (int v = 0; v < vertices; ++v) {
            for (int u = 0; u < vertices; ++u) {
                edges.add(new Pair<>(v, u));
            }
        }
        return edges;
    }
}
