package nyancat;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author K-
 */
public class NyancatOld {

    private static final float BS = 10;
    private static final Stroke BORDER_STROKE = new BasicStroke(BS);
    private static final Color C1 = new Color(255, 204, 153);
    private static final Color C2 = new Color(255, 153, 255);
    private static final Color C3 = new Color(255, 51, 153);

    private Point2D pos = new Point2D.Float(25, 10);

    private final Rectangle2D.Float OUTHER_RECTANGLE = new Rectangle2D.Float(
            (float) pos.getX() + BS,
            (float) pos.getY() + BS,
            160, 135
    );
    private final Rectangle2D.Float INNER_RECTANGLE = new Rectangle2D.Float(
            OUTHER_RECTANGLE.x + BS * 2,
            OUTHER_RECTANGLE.y + BS * 2,
            OUTHER_RECTANGLE.width - BS * 4,
            OUTHER_RECTANGLE.height - BS * 4
    );

    private final Rectangle2D.Float BORDER_RECTANGLE = new Rectangle2D.Float(
            OUTHER_RECTANGLE.x - BS / 2,
            OUTHER_RECTANGLE.y - BS / 2,
            OUTHER_RECTANGLE.width + BS / 2,
            OUTHER_RECTANGLE.height + BS / 2
    );

    private float translationX, translationY;
    private float translationSpeedX = 4;

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
//        translationX += translationSpeedX;
//        g2.translate(translationX, translationY);
        drawBody(g2);
        drawHead(g2);
    }

    private void drawHead(Graphics2D g2) {
        GeneralPath gp = new GeneralPath();
        float side =100;
        gp.moveTo(0, 0);
        gp.lineTo(side, 0);
        gp.lineTo(side, side);
        gp.lineTo(0, side);
        gp.closePath();
        g2.fill(gp);
    }

    private void drawBody(Graphics2D g2) {
        g2.setColor(C1);
        g2.fill(OUTHER_RECTANGLE);

        g2.setColor(C2);
        g2.fill(INNER_RECTANGLE);

        g2.setStroke(BORDER_STROKE);

        paintExtraInner(g2);
        paintBorder(g2);
    }

    private void paintExtraInner(Graphics2D g2) {
        Rectangle2D.Float r = INNER_RECTANGLE;
        //vertical left
        drawLine(g2,
                r.x - BS / 2,
                r.y + BS * 1.5,
                r.x - BS / 2,
                r.y + r.height - BS * 1.5
        );
        //vertical right
        drawLine(g2,
                r.x + r.width + BS / 2,
                r.y + BS * 1.5,
                r.x + r.width + BS / 2,
                r.y + r.height - BS * 1.5
        );
        //horitzontal top
        drawLine(g2,
                r.x + BS * 1.5,
                r.y - BS * 0.5,
                r.x + r.width - BS * 1.5,
                r.y - BS * 0.5
        );
        //horitzontal bottom
        drawLine(g2,
                r.x + BS * 1.5,
                r.y + r.height + BS * 0.5,
                r.x + r.width - BS * 1.5,
                r.y + r.height + BS * 0.5
        );
    }

    private void paintBorder(Graphics2D g2) {
        g2.setColor(Color.black);

        Rectangle2D.Float r = BORDER_RECTANGLE;
        //vertical left
        drawLine(g2,
                r.x,
                r.y + BS * 2,
                r.x,
                r.y + r.height - BS * 1.5
        );
        //vertical right
        drawLine(g2,
                r.x + r.width + BS / 2,
                r.y + BS * 2,
                r.x + r.width + BS / 2,
                r.y + r.height - BS * 1.5
        );
        //horitzontal top
        drawLine(g2,
                r.x + BS * 2,
                r.y,
                r.x + r.width - BS * 1.5,
                r.y
        );
        //horitzontal bottom
        drawLine(g2,
                r.x + BS * 2,
                r.y + r.height + BS / 2,
                r.x + r.width - BS * 1.5,
                r.y + r.height + BS / 2
        );

        //dots
        //dot top left
        drawLine(g2,
                r.x + BS,
                r.y + BS,
                r.x + BS,
                r.y + BS
        );
        //dot top right
        drawLine(g2,
                r.x - BS / 2 + r.width,
                r.y + BS,
                r.x - BS / 2 + r.width,
                r.y + BS
        );
        //dot bot left
        drawLine(g2,
                r.x + BS,
                r.y - BS / 2 + r.height,
                r.x + BS,
                r.y - BS / 2 + r.height
        );
        //dot bot right
        drawLine(g2,
                r.x - BS / 2 + r.width,
                r.y - BS / 2 + r.height,
                r.x - BS / 2 + r.width,
                r.y - BS / 2 + r.height
        );
    }

    private static void drawLine(Graphics2D g2, double x0, double y0, double x1, double y1) {
        g2.draw(new Line2D.Double(x0, y0, x1, y1));
    }

}
