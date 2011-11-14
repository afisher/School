/*
 * This was a try at making it possible to add a button to run a second generation
 * ...it was a miserable failure... for a long time... but! now it works; yippee!
 * 
 * Created on Oct 28, 2011, 1:03:15 PM
 */
package ga;

import java.awt.Graphics;
import java.awt.Rectangle;
import vehicles.Controller;
import vehicles.ProtoWorld;

/**
 *
 * @author levenick
 */
public class VehiclePanel extends javax.swing.JPanel {

    Controller theController;

    /** Creates new form VehiclePanel */
    public VehiclePanel(Controller c) {
        initComponents();
        theController = c;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Text (you could delete this!)
        Rectangle b = getBounds();
        g.drawString(("w, h=" + b.width + " " + b.height), 10, 20);
        //System.out.print("p");
        ProtoWorld.setDrawSize(b);
        theController.paint(g);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
