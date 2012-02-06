import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    JSlider timeSlider;
    JSlider gSlider;
    JSlider mSlider;
    JSlider delaySlider;

    JButton runButton;
    JButton stepButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        setTitle("Pendulum Simulation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel timeLabel  = new JLabel("timestep");
        JLabel gLabel     = new JLabel("g");
        JLabel mLabel     = new JLabel("m");
        JLabel delayLabel = new JLabel("delay");

        timeSlider  = new JSlider();
        gSlider     = new JSlider();
        mSlider     = new JSlider();
        delaySlider = new JSlider();

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runButtonClicked();
            }
        });

        stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepButtonClicked();
            }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        panel.add(timeLabel);
        panel.add(timeSlider);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(gLabel);
        panel.add(gSlider);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(mLabel);
        panel.add(mSlider);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(delayLabel);
        panel.add(delaySlider);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(runButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(stepButton);

        JPanel pendulumArea = new JPanel();

        setPreferredSize(new Dimension(640, 480));

        add(panel, BorderLayout.NORTH);
        add(pendulumArea, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void stepButtonClicked() {
    }

    private void runButtonClicked() {
        new Thread() {
            public void run() {
                runHelper();
            }
        }.start();
    }

    private void runHelper() {
    }
}
