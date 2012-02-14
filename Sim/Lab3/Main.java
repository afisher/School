import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    private boolean running = false;

    SimulationPanel pendulumArea = new SimulationPanel();

    SpecialSlider timeSlider;
    SpecialSlider gSlider;
    SpecialSlider mSlider;
    SpecialSlider delaySlider;

    JButton runButton;
    JButton stepButton;
    JButton resetButton;

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

        timeSlider  = new SpecialSlider();
        gSlider     = new SpecialSlider();
        mSlider     = new SpecialSlider();
        delaySlider = new SpecialSlider();

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

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonClicked();
            }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new FlowLayout());//new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setPreferredSize(new Dimension(200, 380));

        panel.add(timeLabel);
        panel.add(timeSlider);

        panel.add(gLabel);
        panel.add(gSlider);

        panel.add(mLabel);
        panel.add(mSlider);

        panel.add(delayLabel);
        panel.add(delaySlider);

        panel.add(runButton);
        panel.add(stepButton);
        panel.add(resetButton);

        pendulumArea.setPreferredSize(new Dimension(440, 380));

        setPreferredSize(new Dimension(640, 380));

        add(panel, BorderLayout.WEST);
        add(pendulumArea, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void stepButtonClicked() {
        step();
    }

    private void runButtonClicked() {
        new Thread() {
            public void run() {
                runHelper();
            }
        }.start();
    }

    private void runHelper() {
        if (running) {
            running = false;
            runButton.setText("Run");
        } else {
            running = true;
            runButton.setText("Stop");

            while (running) {
                step();

                try {
                    Thread.sleep(Params.delay);
                } catch (InterruptedException e) {}
            }
        }
    }

    private void resetButtonClicked() {
        pendulumArea.clear();
    }

    private void step() {
        Params.timestep  = timeSlider.getValue() * 10;
        Params.gravity   = gSlider.getValue();
        Params.magnetism = mSlider.getValue() * 10000;
        Params.delay     = delaySlider.getValue() * 100;

        pendulumArea.step();
        pendulumArea.repaint();
    }
}
