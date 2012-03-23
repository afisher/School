/*
 * NewFrame.java   --  created on Mar 19, 2012, 9:33:16 AM
 * @author levenick
 */

import java.awt.*;

public class NewFrame extends Frame {

    /** Creates new form NewFrame */
    public NewFrame() {
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
} // class NewFrame

