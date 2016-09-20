package nyancat;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author K-
 */
public class Nyancat {

    private static final Point2D pos = new Point2D.Float(25, 10);
    private static final float LINE_WIDTH = 10;
    private static final Stroke BORDER_STROKE = new BasicStroke(LINE_WIDTH);

    private static final Color C1 = new Color(255, 204, 153);
    private static final Rectangle2D.Float INNER_RECTANGLE = new Rectangle2D.Float(
            (float) pos.getX() + LINE_WIDTH,
            (float) pos.getY() + LINE_WIDTH,
            160, 135
    );

    private static final Rectangle2D.Float BORDER_RECTANGLE = new Rectangle2D.Float(
            INNER_RECTANGLE.x - LINE_WIDTH / 2,
            INNER_RECTANGLE.y - LINE_WIDTH / 2,
            INNER_RECTANGLE.width + LINE_WIDTH / 2,
            INNER_RECTANGLE.height + LINE_WIDTH / 2
    );

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawBody(g2);
    }

    private void drawBody(Graphics2D g2) {
        g2.setColor(C1);
        g2.fill(INNER_RECTANGLE);

        g2.setStroke(BORDER_STROKE);
        g2.setColor(Color.black);
        //vertical left
        drawLine(g2,
                BORDER_RECTANGLE.x,
                BORDER_RECTANGLE.y + LINE_WIDTH * 2,
                BORDER_RECTANGLE.x,
                BORDER_RECTANGLE.y + BORDER_RECTANGLE.height - LINE_WIDTH * 1.5
        );
        //vertical right
        drawLine(g2,
                BORDER_RECTANGLE.x + BORDER_RECTANGLE.width + LINE_WIDTH / 2,
                BORDER_RECTANGLE.y + LINE_WIDTH * 2,
                BORDER_RECTANGLE.x + BORDER_RECTANGLE.width + LINE_WIDTH / 2,
                BORDER_RECTANGLE.y + BORDER_RECTANGLE.height - LINE_WIDTH * 1.5
        );
        //horitzontal top
        drawLine(g2,
                BORDER_RECTANGLE.x + LINE_WIDTH * 2,
                BORDER_RECTANGLE.y,
                BORDER_RECTANGLE.x + BORDER_RECTANGLE.width - LINE_WIDTH * 1.5,
                BORDER_RECTANGLE.y
        );
        //horitzontal bottom
        drawLine(g2,
                BORDER_RECTANGLE.x + LINE_WIDTH * 2,
                BORDER_RECTANGLE.y + BORDER_RECTANGLE.height + LINE_WIDTH / 2,
                BORDER_RECTANGLE.x + BORDER_RECTANGLE.width - LINE_WIDTH * 1.5,
                BORDER_RECTANGLE.y + BORDER_RECTANGLE.height + LINE_WIDTH / 2
        );

        //dots
        //dot top left
        drawLine(g2,
                BORDER_RECTANGLE.x + LINE_WIDTH,
                BORDER_RECTANGLE.y + LINE_WIDTH,
                BORDER_RECTANGLE.x + LINE_WIDTH,
                BORDER_RECTANGLE.y + LINE_WIDTH
        );
        //dot top right
        drawLine(g2,
                BORDER_RECTANGLE.x - LINE_WIDTH / 2 + BORDER_RECTANGLE.width,
                BORDER_RECTANGLE.y + LINE_WIDTH,
                BORDER_RECTANGLE.x - LINE_WIDTH / 2 + BORDER_RECTANGLE.width,
                BORDER_RECTANGLE.y + LINE_WIDTH
        );
        //dot bot left
        drawLine(g2,
                BORDER_RECTANGLE.x + LINE_WIDTH,
                BORDER_RECTANGLE.y - LINE_WIDTH / 2 + BORDER_RECTANGLE.height,
                BORDER_RECTANGLE.x + LINE_WIDTH,
                BORDER_RECTANGLE.y - LINE_WIDTH / 2 + BORDER_RECTANGLE.height
        );
        //dot bot right
        drawLine(g2,
                BORDER_RECTANGLE.x - LINE_WIDTH / 2 + BORDER_RECTANGLE.width,
                BORDER_RECTANGLE.y - LINE_WIDTH / 2 + BORDER_RECTANGLE.height,
                BORDER_RECTANGLE.x - LINE_WIDTH / 2 + BORDER_RECTANGLE.width,
                BORDER_RECTANGLE.y - LINE_WIDTH / 2 + BORDER_RECTANGLE.height
        );
    }

    private static void drawLine(Graphics2D g2, double x0, double y0, double x1, double y1) {
        g2.draw(new Line2D.Double(x0, y0, x1, y1));
    }

}
