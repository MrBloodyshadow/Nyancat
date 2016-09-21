package nyancat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author K-
 */
public class Nyancat2 {

    public static void main(String[] args) {
        Nyancat2 nyancat = new Nyancat2();
        InvisibleFrame invisibleFrame = new InvisibleFrame() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(150, 150);
                g2.scale(5, 5);
                nyancat.paint(g2);
            }
        };
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                Graphics graphics = invisibleFrame.getGraphics();
                if (graphics != null) {
                    nyancat.nextFrame();
                    invisibleFrame.repaint();
                }
            }
        }).start();
        invisibleFrame.setVisible(true);
    }

    private class Pixel {

        private Color hexToColor(float f) {
            String fs = Integer.toString((int) f);
            int color = (int) Long.parseLong(fs, 16);
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
//        int b = (color >> 0) & 0xFF;
            int b = (color) & 0xFF;
            Color c = new Color(r, g, b);
            return c;
        }

//        private final float x;
//        private final float y;
//        private final float w;
//        private final float h;
        private final Shape line;
//        private final Stroke s;
        private final Color c;

        public Pixel(float x, float y, float w, float h, float c) {
//            this.s = new BasicStroke(h);
//            line = new Line2D.Float(x, y, x + w, y);
            line = new Rectangle2D.Float(x, y, w, h);
            this.c = hexToColor(c);
        }

        public void paint(Graphics2D g2) {
//            g2.setStroke(s);
            g2.setColor(c);
            g2.fill(line);
        }
    }

    private class Mesh {

        private Point2D.Double position = new Point2D.Double();
        private final List<Pixel> pixels = new ArrayList<Pixel>();

        private void add(Pixel pixel) {
            pixels.add(pixel);
        }

        private void add(float x, float y, float w, float h, float c) {
            Pixel p = new Pixel(x, y, w, h, c);
            add(p);
        }

        public void paint(Graphics2D g2) {
            g2.translate(position.x, position.y);
            for (Pixel mesh : pixels) {
                mesh.paint(g2);
            }
        }
    }

    private final float scale = 1;
    private final int numStars = 10, numRainChunks = 30;

    private final List<Mesh> scene = new ArrayList<Mesh>();

    private Mesh poptart, face, feet, tail, rainbow, rainChunk;
    private List<List<Mesh>> stars;
    private int frame = 0;

    public void paint(Graphics2D g2) {
        for (Mesh mesh : scene) {
            mesh.paint(g2);
        }
    }

    private void helper(Mesh o, double x, double y, double w, double h, float c) {
        o.add((float)(x * scale), (float)(y * scale), (float)(w * scale), (float)(h * scale), c);
    }

    private void helper(Mesh o, double x, double y, double z, double w, double h, double d, float c) {
        helper(o, x, -y, w, h, c);
    }

    public Nyancat2() {
        poptart();
        feet();
        tail();
        face();
        rainbow();
        rain();
        stars();
    }

    private void stars() {
        stars = new ArrayList<List<Mesh>>(numStars);
        for (int state = 0; state < 6; state++) {
            stars.add(new ArrayList<Mesh>());
            for (int c = 0; c < numStars; c++) {
                Mesh star = new Mesh();
                star.position.x = Math.random() * 200 - 100;
                star.position.y = Math.random() * 200 - 100;
                buildStar(star, state);
                scene.add(star);
                stars.get(state).add(star);
            }
        }
    }

    private void rain() {
        rainChunk = new Mesh();
        helper(rainChunk, -16.5, 7, 0, 8, 3, 1, 0xff0000);
        helper(rainChunk, -16.5, 4, 0, 8, 3, 1, 0xff9900);
        helper(rainChunk, -16.5, 1, 0, 8, 3, 1, 0xffff00);
        helper(rainChunk, -16.5, -2, 0, 8, 3, 1, 0x33ff00);
        helper(rainChunk, -16.5, -5, 0, 8, 3, 1, 0x0099ff);
        helper(rainChunk, -16.5, -8, 0, 8, 3, 1, 0x6633ff);
        rainChunk.position.x -= (8 * (numRainChunks - 1));
        scene.add(rainChunk);
    }

    private void rainbow() {
        //RAINBOW
        rainbow = new Mesh();
        for (int c = 0; c < numRainChunks - 1; c++) {
            int yOffset = 8;
            if (c % 2 == 1) {
                yOffset = 7;
            }
            double xOffset = (-c * 8) - 16.5;
            helper(rainbow, xOffset, yOffset, 0, 8, 3, 1, 0xff0000);
            helper(rainbow, xOffset, yOffset - 3, 0, 8, 3, 1, 0xff9900);
            helper(rainbow, xOffset, yOffset - 6, 0, 8, 3, 1, 0xffff00);
            helper(rainbow, xOffset, yOffset - 9, 0, 8, 3, 1, 0x33ff00);
            helper(rainbow, xOffset, yOffset - 12, 0, 8, 3, 1, 0x0099ff);
            helper(rainbow, xOffset, yOffset - 15, 0, 8, 3, 1, 0x6633ff);
        }
        scene.add(rainbow);
    }

    private void face() {
        //FACE
        face = new Mesh();
        helper(face, 2, -3, -3, 12, 9, 4, 0x222222);
        helper(face, 0, -5, 0, 16, 5, 1, 0x222222);
        helper(face, 1, -1, 0, 4, 10, 1, 0x222222);
        helper(face, 11, -1, 0, 4, 10, 1, 0x222222);
        helper(face, 3, -11, 0, 10, 2, 1, 0x222222);
        helper(face, 2, 0, 0, 2, 2, 1, 0x222222);
        helper(face, 4, -2, 0, 2, 2, 1, 0x222222);
        helper(face, 12, 0, 0, 2, 2, 1, 0x222222);
        helper(face, 10, -2, 0, 2, 2, 1, 0x222222);

        helper(face, 1, -5, .5, 14, 5, 1, 0x999999);
        helper(face, 3, -4, .5, 10, 8, 1, 0x999999);
        helper(face, 2, -1, .5, 2, 10, 1, 0x999999);
        helper(face, 12, -1, .5, 2, 10, 1, 0x999999);
        helper(face, 4, -2, .5, 1, 2, 1, 0x999999);
        helper(face, 5, -3, .5, 1, 1, 1, 0x999999);
        helper(face, 11, -2, .5, 1, 2, 1, 0x999999);
        helper(face, 10, -3, .5, 1, 1, 1, 0x999999);
        //Eyes
        helper(face, 4, -6, .6, 2, 2, 1, 0x222222);
        helper(face, 11, -6, .6, 2, 2, 1, 0x222222);
        helper(face, 3.99, -5.99, .6, 1.01, 1.01, 1.01, 0xffffff);
        helper(face, 10.99, -5.99, .6, 1.01, 1.01, 1.01, 0xffffff);
        //MOUTH
        helper(face, 5, -10, .6, 7, 1, 1, 0x222222);
        helper(face, 5, -9, .6, 1, 2, 1, 0x222222);
        helper(face, 8, -9, .6, 1, 2, 1, 0x222222);
        helper(face, 11, -9, .6, 1, 2, 1, 0x222222);
        //CHEEKS
        helper(face, 2, -8, .6, 2, 2, .91, 0xff9999);
        helper(face, 13, -8, .6, 2, 2, .91, 0xff9999);

        face.position.x = 40.5;
        face.position.y = 10;
        scene.add(face);
    }

    private void tail() {
        //TAIL
        tail = new Mesh();
        helper(tail, 0, 0, -.25, 4, 3, 1.5, 0x222222);
        helper(tail, 1, -1, -.25, 4, 3, 1.5, 0x222222);
        helper(tail, 2, -2, -.25, 4, 3, 1.5, 0x222222);
        helper(tail, 3, -3, -.25, 4, 3, 1.5, 0x222222);
        helper(tail, 1, -1, -.5, 2, 1, 2, 0x999999);
        helper(tail, 2, -2, -.5, 2, 1, 2, 0x999999);
        helper(tail, 3, -3, -.5, 2, 1, 2, 0x999999);
        helper(tail, 4, -4, -.5, 2, 1, 2, 0x999999);

        tail.position.x = -16.5;
        tail.position.y = 2;
        scene.add(tail);
    }

    private void feet() {
        //FEET
        feet = new Mesh();
        helper(feet, 0, -2, .49, 3, 3, 1, 0x222222);
        helper(feet, 1, -1, .49, 3, 3, 1, 0x222222);
        helper(feet, 1, -2, -.01, 2, 2, 2, 0x999999);
        helper(feet, 2, -1, -.01, 2, 2, 2, 0x999999);

        helper(feet, 6, -2, -.5, 3, 3, 1, 0x222222);
        helper(feet, 6, -2, -.5, 4, 2, 1, 0x222222);
        helper(feet, 7, -2, -.99, 2, 2, 2, 0x999999);

        helper(feet, 16, -3, .49, 3, 2, 1, 0x222222);
        helper(feet, 15, -2, .49, 3, 2, 1, 0x222222);
        helper(feet, 15, -2, -.01, 2, 1, 2, 0x999999);
        helper(feet, 16, -3, -.01, 2, 1, 2, 0x999999);

        helper(feet, 21, -3, -.5, 3, 2, 1, 0x222222);
        helper(feet, 20, -2, -.5, 3, 2, 1, 0x222222);
        helper(feet, 20, -2, -.99, 2, 1, 2, 0x999999);
        helper(feet, 21, -3, -.99, 2, 1, 2, 0x999999);

        feet.position.x = -12.5;
        feet.position.y = -6;
        scene.add(feet);
    }

    private void poptart() {
        //POPTART
        poptart = new Mesh();
        //object        x   y   z   w   h   d  color
        helper(poptart, 0, -2, -1, 21, 14, 3, 0x222222);
        helper(poptart, 1, -1, -1, 19, 16, 3, 0x222222);
        helper(poptart, 2, 0, -1, 17, 18, 3, 0x222222);

        helper(poptart, 1, -2, -1.5, 19, 14, 4, 0xffcc99);
        helper(poptart, 2, -1, -1.5, 17, 16, 4, 0xffcc99);

        helper(poptart, 2, -4, 2, 17, 10, .6, 0xff99ff);
        helper(poptart, 3, -3, 2, 15, 12, .6, 0xff99ff);
        helper(poptart, 4, -2, 2, 13, 14, .6, 0xff99ff);

        helper(poptart, 4, -4, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 9, -3, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 12, -3, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 16, -5, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 8, -7, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 5, -9, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 9, -10, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 3, -11, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 7, -13, 2, 1, 1, .7, 0xff3399);
        helper(poptart, 4, -14, 2, 1, 1, .7, 0xff3399);

        poptart.position.x = -10.5f;
        poptart.position.y = 9;
        scene.add(poptart);
    }

    void buildStar(Mesh star, int state) {
        switch (state) {
            case 0:
                helper(star, 0, 0, 0, 1, 1, 1, 0xffffff);
                break;
            case 1:
                helper(star, 1, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, -1, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, 0, 1, 0, 1, 1, 1, 0xffffff);
                helper(star, 0, -1, 0, 1, 1, 1, 0xffffff);
                break;
            case 2:
                helper(star, 1, 0, 0, 2, 1, 1, 0xffffff);
                helper(star, -2, 0, 0, 2, 1, 1, 0xffffff);
                helper(star, 0, 2, 0, 1, 2, 1, 0xffffff);
                helper(star, 0, -1, 0, 1, 2, 1, 0xffffff);
                break;
            case 3:
                helper(star, 0, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, 2, 0, 0, 2, 1, 1, 0xffffff);
                helper(star, -3, 0, 0, 2, 1, 1, 0xffffff);
                helper(star, 0, 3, 0, 1, 2, 1, 0xffffff);
                helper(star, 0, -2, 0, 1, 2, 1, 0xffffff);
                break;
            case 4:
                helper(star, 0, 3, 0, 1, 1, 1, 0xffffff);
                helper(star, 2, 2, 0, 1, 1, 1, 0xffffff);
                helper(star, 3, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, 2, -2, 0, 1, 1, 1, 0xffffff);
                helper(star, 0, -3, 0, 1, 1, 1, 0xffffff);
                helper(star, -2, -2, 0, 1, 1, 1, 0xffffff);
                helper(star, -3, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, -2, 2, 0, 1, 1, 1, 0xffffff);
                break;
            case 5:
                helper(star, 2, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, -2, 0, 0, 1, 1, 1, 0xffffff);
                helper(star, 0, 2, 0, 1, 1, 1, 0xffffff);
                helper(star, 0, -2, 0, 1, 1, 1, 0xffffff);
                break;
        }
    }

    void nextFrame() {
        
        for (int c = 0; c < numStars; c++) {
            double tempX = stars.get(5).get(c).position.x;
            double tempY = stars.get(5).get(c).position.y;
            for (int state = 5; state > 0; state--) {
                Mesh star = stars.get(state).get(c);
                Mesh star2 = stars.get(state - 1).get(c);
                star.position.x = star2.position.x - 8;
                star.position.y = star2.position.y;

                if (star.position.x < -100) {
                    star.position.x += 200;
                    star.position.y = Math.random() * 200 - 100;
                }
            }
            stars.get(0).get(c).position.x = tempX;
            stars.get(0).get(c).position.y = tempY;
        }
        switch (frame) {
            case 0://2nd frame
                face.position.x++;
                feet.position.x++;
                break;
            case 1:
                face.position.y--;
                feet.position.x++;
                feet.position.y--;
                poptart.position.y--;
                rainbow.position.x -= 9;
                rainChunk.position.x += (8 * (numRainChunks - 1)) - 1;
                break;
            case 2:
                feet.position.x--;
                break;
            case 3:
                face.position.x--;
                feet.position.x--;
                rainbow.position.x += 9;
                rainChunk.position.x -= (8 * (numRainChunks - 1)) - 1;
                break;
            case 4:
                face.position.y++;
                break;
            case 5:
                poptart.position.y++;
                feet.position.y++;
                rainbow.position.x -= 9;
                rainChunk.position.x += (8 * (numRainChunks - 1)) - 1;
                break;
            case 6://8th frame
                face.position.x++;
                feet.position.x++;
                break;
            case 7:
                poptart.position.y--;
                face.position.y--;
                feet.position.x++;
                feet.position.y--;
                rainbow.position.x += 9;
                rainChunk.position.x -= (8 * (numRainChunks - 1)) - 1;
                break;
            case 8:
                feet.position.x--;
                break;
            case 9:
                face.position.x--;
                feet.position.x--;
                rainbow.position.x -= 9;
                rainChunk.position.x += (8 * (numRainChunks - 1)) - 1;
                break;
            case 10:
                face.position.y++;
                break;
            case 11://1st frame
                poptart.position.y++;
                feet.position.y++;
                rainbow.position.x += 9;
                rainChunk.position.x -= (8 * (numRainChunks - 1)) - 1;
                break;
        }
        frame++;
        if(frame==12){
            frame=0;
        }
    }
}
