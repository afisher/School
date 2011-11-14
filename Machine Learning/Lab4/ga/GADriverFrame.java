/*
 * GADriverFrame.java   --   created on Oct 31, 2011, 10:47:29 AM
 * @author levenick
 */
package ga;

public class GADriverFrame extends javax.swing.JFrame {

    public GADriverFrame() {
        initComponents();
        setVisible(true);
        setTitle("GADriverFrame");
    }


    void doit() {
        GA_Runner theRunner = new GA_Runner();
        theRunner.start();
    }

    //  WARNING: Do NOT modify the Generated Code!
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        againButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());

        againButton.setText("again!");
        againButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                againButtonActionPerformed(evt);
            }
        });
        getContentPane().add(againButton);
        againButton.setBounds(0, 0, 97, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void againButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_againButtonActionPerformed
        doit();
    }//GEN-LAST:event_againButtonActionPerformed


    public static void main(String[]aasdf)  {
        GADriverFrame g = new GADriverFrame();
        g.doit(); // first time
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton againButton;
    // End of variables declaration//GEN-END:variables
} // class GADriverFrame
