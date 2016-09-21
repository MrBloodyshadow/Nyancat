package nyancat;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * @author K-
 */
public class Main {

    public static void main(String[] args) {
        Nyancat nyancat = new Nyancat();
        InvisibleFrame invisibleFrame = new InvisibleFrame() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                nyancat.paint(g);
            }
        };
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                Graphics graphics = invisibleFrame.getGraphics();
//                if (graphics != null) {
//                    invisibleFrame.repaint();
//                }
//            }
//        }).start();
        invisibleFrame.setVisible(true);
    }
}
