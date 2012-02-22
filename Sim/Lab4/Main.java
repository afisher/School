import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    JButton loadButton;
    JButton saveButton;
    JButton displayButton;
    JButton deleteButton;
    JButton clearButton;

    JTextArea fileTextArea;
    JTextArea infoTextArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        setTitle("Disk Simulation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadButtonClicked();
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonClicked();
            }
        });

        displayButton = new JButton("Display Disk Contents");
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayButtonClicked();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteButtonClicked();
            }
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearButtonClicked();
            }
        });

        fileTextArea = new JTextArea();
        fileTextArea.setPreferredSize(new Dimension(640, 200));

        infoTextArea = new JTextArea();
        infoTextArea.setPreferredSize(new Dimension(640, 200));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(displayButton);
        panel.add(deleteButton);
        panel.add(clearButton);

        setPreferredSize(new Dimension(640, 480));

        JPanel filePanel = new JPanel();
        filePanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));

        filePanel.add(fileTextArea);

        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));

        infoPanel.add(infoTextArea);

        add(panel, BorderLayout.NORTH);
        add(filePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void loadButtonClicked() {
    }

    private void saveButtonClicked() {
    }

    private void displayButtonClicked() {
    }

    private void deleteButtonClicked() {
    }

    private void clearButtonClicked() {
    }
}
