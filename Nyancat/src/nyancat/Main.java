package nyancat;

import java.awt.Graphics;

/**
 * @author K-
 */
public class Main {

    public static void main(String[] args) {
        Nyancat nyancat = new Nyancat();
        InvisibleFrame invisibleFrame = new InvisibleFrame() {
            @Override
            public void paint(Graphics g) {
                nyancat.paint(g);
            }
        };
        invisibleFrame.setVisible(true);
    }
}
