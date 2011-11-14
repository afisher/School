import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class Lab2 extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;
    private JButton aButton;
    private JButton piButton;
    private JButton wButton;
    private JButton zButton;
    private JButton runButton;
    private JButton quitButton;
    private Experiment experiment;

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lab2();
            }
        });
    }

    public Lab2() {
        experiment = new Experiment();

        setPreferredSize(new Dimension(780, 320));
        setTitle("Lab 2");

        Container container = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

        scrollPane = new JScrollPane(textArea);

        aButton = new JButton("a");
        aButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aButtonClicked();
            }
        });

        piButton = new JButton("pi");
        piButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                piButtonClicked();
            }
        });

        wButton = new JButton("w");
        wButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wButtonClicked();
            }
        });

        zButton = new JButton("z");
        zButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zButtonClicked();
            }
        });

        runButton = new JButton("run");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runButtonClicked();
            }
        });

        quitButton = new JButton("quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quitButtonClicked();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(aButton);
        topPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        topPanel.add(piButton);
        topPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        topPanel.add(wButton);
        topPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        topPanel.add(zButton);
        topPanel.add(Box.createRigidArea(new Dimension(64, 0)));
        topPanel.add(runButton);
        topPanel.add(Box.createRigidArea(new Dimension(64, 0)));
        topPanel.add(quitButton);

        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
        botPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));
        botPanel.add(scrollPane);

        container.add(topPanel);
        container.add(botPanel);

        pack();
        setVisible(true);
    }

    private void quitButtonClicked() {
        System.exit(0);
    }

    private void aButtonClicked() {
        File dir = new File("../ptronData/a");
        loadPatterns(dir);
    }

    private void piButtonClicked() {
        File dir = new File("../ptronData/pi");
        loadPatterns(dir);
    }

    private void wButtonClicked() {
        File dir = new File("../ptronData/w");
        loadPatterns(dir);
    }

    private void zButtonClicked() {
        File dir = new File("../ptronData/z");
        loadPatterns(dir);
    }

    private void loadPatterns(File dir) {
        experiment.setPatterns(new PatternList());

        File [] files = dir.listFiles();

        for (File f : files) {
            if (f.isFile()) {
                String filename = f.toString();
                experiment.addPattern(new Pattern(filename)); 
            }
        }

        textArea.setText("");
    }

    private void runButtonClicked() {
        experiment.run(textArea);
    }
}
