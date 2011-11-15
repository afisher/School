/*
 * InfoFrame.java   --   created on Oct 24, 2011, 10:21:08 AM
 * @author levenick
 */
package ga;

import vehicles.*;
import javax.swing.*;

public class InfoFrame extends JFrame {
    GAVehiclesFrame theFrame;
    private Controller theController;

    public JTextField getTF() {return timeTF;}

    public InfoFrame(GAVehiclesFrame f) {
        theFrame = f;
        initComponents();
        setVisible(true);
        setTitle("InfoFrame");
    }

    //  WARNING: Do NOT modify the Generated Code!
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        runStopButton = new javax.swing.JButton();
        showButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());

        timeTF.setText("000000");
        timeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeTFActionPerformed(evt);
            }
        });
        getContentPane().add(timeTF);
        timeTF.setBounds(70, 40, 84, 28);

        jLabel1.setText("time");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 30, 28, 16);

        runStopButton.setText("stop");
        runStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runStopButtonActionPerformed(evt);
            }
        });
        getContentPane().add(runStopButton);
        runStopButton.setBounds(0, 10, 97, 29);

        showButton.setText("show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });
        getContentPane().add(showButton);
        showButton.setBounds(120, 10, 77, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeTFActionPerformed

    private void runStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runStopButtonActionPerformed
        toggle();
}//GEN-LAST:event_runStopButtonActionPerformed

    void toggle() {
        theController.toggleRunning();
        if (theController.getRunning()) {
            runStopButton.setText("stop");
        } else {
            runStopButton.setText("go!");
        }
    }
    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
        //theFrame.showing = !theFrame.showing;
        theFrame.setVisible(!theFrame.isVisible());
}//GEN-LAST:event_showButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton runStopButton;
    private javax.swing.JButton showButton;
    private javax.swing.JTextField timeTF;
    // End of variables declaration//GEN-END:variables

    /**
     * @param theController the theController to set
     */
    public void setTheController(Controller theController) {
        this.theController = theController;
    }
} // class InfoFrame
