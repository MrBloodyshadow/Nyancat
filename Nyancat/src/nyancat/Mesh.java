package nyancat;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author K-
 */
class Mesh {

    public final Point2D.Double position = new Point2D.Double();
    private final List<Pixel> pixels = new ArrayList<Pixel>();

    public void add(Pixel pixel) {
        pixels.add(pixel);
    }

    public void add(float x, float y, float w, float h, int c, int a) {
        Pixel p = new Pixel(x, y, w, h, c, a);
        add(p);
    }

    public void paint(Graphics2D g2) {
        AffineTransform transform = g2.getTransform();
        g2.translate(position.x, position.y);
        for (Pixel mesh : pixels) {
            mesh.paint(g2);
        }
        g2.setTransform(transform);
    }
}
