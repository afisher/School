import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    private int h = 1000;
    private int p = 100;

    private double a = 0.01;
    private double b = 0.01;

    private double alpha = 0.001;
    private double beta  = 0.001;

    private ArrayList<DataPair> data = new ArrayList<DataPair>();

    private Grapher graph = new Grapher(data);

    JSpinner hSpinner;
    JSpinner pSpinner;
    JSpinner aSpinner;
    JSpinner bSpinner;
    JSpinner alphaSpinner;
    JSpinner betaSpinner;

    JButton runButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
        /*data.add(new DataPair(h, p));
        output();

        while (h > 0 && p > 0) {
            step();
            output();
        }*/
    }

    public Main() {
        setTitle("Lotka-Volterra");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JLabel hLabel     = new JLabel("h");
        JLabel pLabel     = new JLabel("p");
        JLabel aLabel     = new JLabel("a");
        JLabel bLabel     = new JLabel("b");
        JLabel alphaLabel = new JLabel("alpha");
        JLabel betaLabel  = new JLabel("beta");

        hSpinner     = new JSpinner(new SpinnerNumberModel(1000, 0, 1000000000, 100));
        pSpinner     = new JSpinner(new SpinnerNumberModel(100, 0, 1000000000, 10));
        aSpinner     = new JSpinner(new SpinnerNumberModel(0.1, 0.0, 1.0, 0.1));
        bSpinner     = new JSpinner(new SpinnerNumberModel(0.1, 0.0, 1.0, 0.1));
        alphaSpinner = new JSpinner(new SpinnerNumberModel(0.01, 0.0, 1.0, 0.01));
        betaSpinner  = new JSpinner(new SpinnerNumberModel(0.01, 0.0, 1.0, 0.01));

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runButtonClicked();
            }
        });

        graph.setPreferredSize(new Dimension(800, 480));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        panel.add(hLabel);
        panel.add(hSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(pLabel);
        panel.add(pSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(aLabel);
        panel.add(aSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(bLabel);
        panel.add(bSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(alphaLabel);
        panel.add(alphaSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(betaLabel);
        panel.add(betaSpinner);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(runButton);

        add(panel, BorderLayout.NORTH);
        add(graph, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void step() {
        double dH = a * h - alpha * h * p;
        double dP = beta * h * p - b * p;

        h += dH;
        p += dP;
    }

    private void output() {
        System.out.println("h = " + h + ", p = " + p);
    }

    private void runButtonClicked() {
        new Thread() {
            public void run() {
                runHelper();
            }
        }.start();
    }

    private void runHelper() {
        h     = (Integer)(hSpinner.getValue());
        p     = (Integer)(pSpinner.getValue());
        a     = (Double)(aSpinner.getValue());
        b     = (Double)(bSpinner.getValue());
        alpha = (Double)(alphaSpinner.getValue());
        beta  = (Double)(betaSpinner.getValue());

        data = new ArrayList<DataPair>();
        data.add(new DataPair(h, p));

        while (h > 0 && p > 0 && data.size() < 5000) {
            step();
            data.add(new DataPair(h, p));
        }

        graph.setData(data);
        graph.repaint(); 
    }
}
