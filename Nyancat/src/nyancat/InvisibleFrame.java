package nyancat;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import javax.swing.JWindow;

/**
 * @author K-
 */
public class InvisibleFrame extends JWindow {

    public static void main(String[] args) {
        InvisibleFrame invisibleFrame = new InvisibleFrame();
        invisibleFrame.setVisible(true);
    }

    private static final Color TRANSPARENT_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    private static final ModalExclusionType MODAL_MODE = Dialog.ModalExclusionType.NO_EXCLUDE;

    public InvisibleFrame() throws HeadlessException {
        setBackground(TRANSPARENT_COLOR);
//        setModalExclusionType(MODAL_MODE);
//        setAlwaysOnTop(true);
        Dimension screenSize = getScreenSize();
        lockSize(screenSize);
    }

    private Dimension getScreenSize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Dimension dimension = new Dimension(width, height);
        return dimension;
    }
    
    private Dimension getMultiScreenSize(){
        //TODO
        return null;
    }

    private void lockSize(Dimension screenSize) {
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
        setSize(screenSize);
        setPreferredSize(screenSize);
    }

}
