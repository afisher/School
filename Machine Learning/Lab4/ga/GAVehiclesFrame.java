/*
 * GAVehiclesFrame.java   --   created on Oct 23, 2011, 11:57:34 AM
 * @author levenick
 */
package ga;

import vehicles.*;
import java.awt.*;

public class GAVehiclesFrame extends javax.swing.JFrame {

    Controller theController;
    static Image offScreen;
    InfoFrame infoFrame = new InfoFrame(this);
    boolean showing = true;
    static VehiclePanel thePanel;

    public GAVehiclesFrame() {
        initComponents();
        initController_World();
        setVisible(true);
        setTitle("GAVehiclesFrame");
    }

    public void initController_World() {
        theController = new Controller(this, infoFrame.getTF());
        theController.setStop(Population.getMaxTime());
        infoFrame.setTheController(theController);
        offScreen = createImage(ProtoWorld.IMAGE_SIZE, ProtoWorld.IMAGE_SIZE);
        if (thePanel != null)
            this.remove(thePanel);
        thePanel = new VehiclePanel(theController);
        add(thePanel);
        pack();
        setBounds(100,120,600, 600);


        //ProtoWorld.setOffG(offScreen.getGraphics());
        ProtoWorld.setOffScreenImage(offScreen);
        //repaint();

        theController.start();
    }

    //  WARNING: Do NOT modify the Generated Code!
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
} // class GAVehiclesFrame

