package draw;

import util.Pair;

public interface DrawingApi {
    long width = 600;
    long height = 600;

    void drawCircle(final Pair<Double, Double> center, final double radius);
    void drawLine(final Pair<Double, Double> from, final Pair<Double, Double> to);

    void plot();
}
