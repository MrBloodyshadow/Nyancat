package nyancat;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author K-
 */
public class Main {
    
//http://stackoverflow.com/questions/2135982/turn-java-app-into-windows-screensaver
//https://sourceforge.net/projects/jssaver/
//https://support.microsoft.com/en-us/kb/182383
    
    public static void main(String[] args) {
        Nyancat nyancat = new Nyancat();
        InvisibleFrame invisibleFrame = new InvisibleFrame() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.translate(50, 25);
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
}
