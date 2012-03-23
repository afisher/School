/*
 * Junk.java   --  created on Mar 13, 2012, 2:23:41 PM
 * @author levenick
 */

import java.awt.*;

public class Junk extends Frame {

    /** Creates new form Junk */
    public Junk() {
        initComponents();
        setBounds(100,100,500,500);
        setVisible(true);
    }

    public void paint(Graphics g) {

    }

    //  WARNING: Do NOT modify the Generated Code!
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        setLayout(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
} // class Junk

