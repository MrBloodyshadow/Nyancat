package nyancat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * @author K-
 */
class Pixel {

    private Color hexToColor(int num) {
        String hexString
                = String.format("0x%8s", Integer.toHexString(num))
                .replace(' ', '0');
        Color c = Color.decode(hexString);
        return c;
    }

    private final Shape shape;
    private final Color color;

    public Pixel(float x, float y, float w, float h, int c) {
        shape = new Rectangle2D.Float(x, y, w, h);
        this.color = hexToColor(c);
    }

    public void paint(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(shape);
    }
}
