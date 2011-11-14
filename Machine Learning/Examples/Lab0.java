import java.util.regex.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Lab0 extends JFrame {
    private ArrayList<Pattern> patterns;
    private ArrayList<String> lines;
    private JFileChooser fileChooser;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame    frame;
    private JButton   inputButton;
    private JButton   outputButton;
    private JButton   scanButton;
    private JButton   quitButton;
    private Dimension size  = new Dimension(600, 370);
    private String    title = "Lab0";
    private String    inputButtonText  = "Input";
    private String    outputButtonText = "Output";
    private String    scanButtonText   = "Scan";
    private String    quitButtonText   = "Quit";

    public Lab0() {
        lines       = new ArrayList<String>();
        patterns    = new ArrayList<Pattern>();
        fileChooser = null;

        Container container = getContentPane();

        setPreferredSize(size);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        // setLayout(new BorderLayout());
        // getContentPane().setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        textArea   = new JTextArea();
        scrollPane = new JScrollPane(textArea);


        inputButton = new JButton(inputButtonText);
        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputButtonClicked();
            }
        });

        outputButton = new JButton(outputButtonText);
        outputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputButtonClicked();
            }
        });

        scanButton = new JButton(scanButtonText);
        scanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scanButtonClicked();
            }
        });

        quitButton = new JButton(quitButtonText);
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quitButtonClicked();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.add(inputButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        buttonPanel.add(scanButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        buttonPanel.add(outputButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(1, 0)));
        buttonPanel.add(quitButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
        container.add(buttonPanel);
        container.add(scrollPane);

        pack();
    }

    private void inputButtonClicked() {
        loadFile(openFileDialog());
        printLines();
    }

    private void outputButtonClicked() {
        saveFile(saveFileDialog());
    }

    private void scanButtonClicked() {
        scanDir(openDirDialog());
    }

    private void quitButtonClicked() {
        System.exit(0);
    }

    private void scanDir(File dir) {
        if (dir == null) return;

        File[] files = dir.listFiles();

        for (File file: files) {
            if (file.isFile()) {
                loadFile(file);
                patterns.add(new Pattern(lines));
            }
        }
        textArea.setText("");

        for (Pattern pattern: patterns) {
            textArea.append("" + pattern + "\n");
        }
    }

    private void loadFile(File file) {
        if (file == null) return;

        Scanner in = null;
        try {
            in = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.exit(1);
        }
        lines = new ArrayList<String>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
    }

    private void saveFile(File file) {
        if (file == null) return;

        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            out.print(textArea.getText());
            out.close();
        }
        catch (IOException e) {
            System.err.println("IO exception!");
        }
    }

    private File openFileDialog() {
        fileChooser   = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else {
            return null;
        }
    }

    private File openDirDialog() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else {
            return null;
        }
    }

    private File saveFileDialog() {
        fileChooser   = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else {
            return null;
        }
    }

    private void printLines() {
        textArea.setText("");
        printLinesNoClear();
    }

    private void printLinesNoClear() {
        for (String line: lines) {
            textArea.append(line + "\n");
        }
    }

    public void blastoff() {
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lab0().blastoff();
            }
        });
    }
}
