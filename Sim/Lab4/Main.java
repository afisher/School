import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    String inputFile = "data.txt";

    /*JButton loadButton;
    JButton saveButton;
    JButton displayButton;
    JButton deleteButton;
    JButton clearButton;*/
    JButton quitButton;

    JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        Globals.init();

        setTitle("Disk Simulation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
/*
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

        displayButton = new JButton("Display");
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
*/
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quitButtonClicked();
            }
        });

        textArea = new JTextArea();

        JPanel panel = new JPanel();
/*
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(displayButton);
        panel.add(deleteButton);
        panel.add(clearButton);
*/
        panel.add(quitButton);

        JScrollPane scrollpane = new JScrollPane(textArea);

        setPreferredSize(new Dimension(640, 480));

        add(scrollpane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        run();
    }

    private void run() {
        java.io.File file = new java.io.File(inputFile);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split(" ");

                // which logical device to save to -- worry about it later
                int deviceNum = Integer.parseInt(pieces[1]);

                String type            = pieces[0];
                String timeUnconverted = pieces[2]; // need to convert this to milliseconds!
                String filename        = pieces[3];
                if (type.equals("save")) {
                    String data        = pieces[4];
                }

                int timeConverted = timeMillis(timeUnconverted);
            }
        } catch (FileNotFoundException e) {
        }
    }

    private int timeMillis(String timeStr) {
        String[] pieces = timeStr.split(":");

        int ret;
        ret =            Integer.parseInt(pieces[0]);
        ret = ret*60   + Integer.parseInt(pieces[1]);
        ret = ret*60   + Integer.parseInt(pieces[2]);
        ret = ret*1000 + Integer.parseInt(pieces[3]);

        return ret;
    }
/*
    private void loadButtonClicked() {
        textArea.setText(Globals.FS.load()); 
    }

    private void saveButtonClicked() {
        Globals.FS.save(textArea.getText()); 
    }

    private void displayButtonClicked() {
        textArea.setText(Globals.FS.toString());
    }

    private void deleteButtonClicked() {
        Globals.FS.delete();
    }

    private void clearButtonClicked() {
        Globals.FS.clear();
    }
*/
    private void quitButtonClicked() {
        System.exit(0);
    }
}
