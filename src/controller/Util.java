package controller;

import javax.swing.JFrame;

/**
 *
 * @author 3152203415
 */
public class Util {

    public void frame(JFrame frameAntigo, JFrame frameNovo) {
        frameAntigo.dispose();
        frameNovo.setVisible(true);
        frameNovo.setLocationRelativeTo(null);  
        //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

}
