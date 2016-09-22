package nyancat;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author K-
 */
public class Nyancat {

    private final int numStars = 10, numRainChunks = 30;

    private final Point2D.Float scale = new Point2D.Float(0.1f, 0.1f);
    private final Point2D.Float position = new Point2D.Float(0, 10);
    private final Point2D.Float speed = new Point2D.Float(3, 0);
    private final List<Mesh> scene = new ArrayList<Mesh>();

    private Mesh poptart, face, feet, tail, rainbow;
    private List<List<Mesh>> stars;
    private int frame = 0;

    public void paint(Graphics2D g2) {

        scale.x += 0.001f;
        scale.y += 0.001f;
        position.x += speed.x;
        position.y += (speed.y) / scale.y;

        g2.scale(scale.x, scale.y);
        //rotate
        g2.translate(position.x, position.y);
        

        for (Mesh mesh : scene) {
            mesh.paint(g2);
        }
    }

    private void helper(Mesh o, double x, double y, double w, double h, int c, int a) {
        o.add((float) (x), (float) (y), (float) (w), (float) (h), c, a);
    }

    private void helper(Mesh o, double x, double y, double z,
            double w, double h, double d, int c) {
        helper(o, x, -y, w, h, c, 255);
    }

    private void helper(Mesh o, double x, double y, double z,
            double w, double h, double d, int c, int a) {
        helper(o, x, -y, w, h, c, a);
    }

    public Nyancat() {
        rainbow();
        feet();
        poptart();
        tail();
        face();
//        stars();
    }

    //TODO implement it for the whole screen or arround the nyancat
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

    private void rainbow() {
        //RAINBOW
        rainbow = new Mesh();
        int alphaSpacing = Math.floorDiv( 255,numRainChunks);
        for (int i = 0; i < numRainChunks - 1; i++) {
            int alpha = alphaSpacing * (numRainChunks - i);
            int yOffset = 8 - i % 2;
            double xOffset = (-i * 8) - 16.5;
            helper(rainbow, xOffset, yOffset, 0, 8, 3, 1, 0xff0000, alpha);
            helper(rainbow, xOffset, yOffset - 3, 0, 8, 3, 1, 0xff9900, alpha);
            helper(rainbow, xOffset, yOffset - 6, 0, 8, 3, 1, 0xffff00, alpha);
            helper(rainbow, xOffset, yOffset - 9, 0, 8, 3, 1, 0x33ff00, alpha);
            helper(rainbow, xOffset, yOffset - 12, 0, 8, 3, 1, 0x0099ff, alpha);
            helper(rainbow, xOffset, yOffset - 15, 0, 8, 3, 1, 0x6633ff, alpha);
        }
        scene.add(rainbow);

        rainbow.position.y = 18;
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

        face.position.x = -.5;
        face.position.y = 14;
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
        tail.position.y = 16;
        scene.add(tail);
    }

    private void feet() {
        //FEET
        feet = new Mesh();
        helper(feet, 1, -2, .49, 3, 3, 1, 0x222222);
        helper(feet, 2, -1, .49, 3, 3, 1, 0x222222);
        helper(feet, 2, -2, -.01, 2, 2, 2, 0x999999);
        helper(feet, 3, -1, -.01, 2, 2, 2, 0x999999);

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
        feet.position.y = 24;
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
        final float rainbowDispalcement = 4.5f;

        moveStars();
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
                tail.position.y--;
                rainbow.position.x -= rainbowDispalcement;
                break;
            case 2:
                feet.position.x--;
                break;
            case 3:
                face.position.x--;
                feet.position.x--;
                rainbow.position.x += rainbowDispalcement;
                break;
            case 4:
                face.position.y++;
                break;
            case 5:
                poptart.position.y++;
                tail.position.y++;
                feet.position.y++;
                rainbow.position.x -= rainbowDispalcement;
                break;
            case 6://8th frame
                face.position.x++;
                feet.position.x++;
                break;
            case 7:
                poptart.position.y--;
                tail.position.y--;
                face.position.y--;
                feet.position.x++;
                feet.position.y--;
                rainbow.position.x += rainbowDispalcement;
                break;
            case 8:
                feet.position.x--;
                break;
            case 9:
                face.position.x--;
                feet.position.x--;
                rainbow.position.x -= rainbowDispalcement;
                break;
            case 10:
                face.position.y++;
                break;
            case 11://1st frame
                poptart.position.y++;
                tail.position.y++;
                feet.position.y++;
                rainbow.position.x += rainbowDispalcement;
                break;
        }
        frame--;
        if (frame == -1) {
            frame = 11;
        }
    }

    private void moveStars() {
        if (stars == null) {
            return;
        }
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
    }
}
