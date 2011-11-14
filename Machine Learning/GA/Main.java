import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class Main extends JFrame {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;
    private JButton button;
    private Population population;

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        population = new Population(6);

        setPreferredSize(new Dimension(300, 500));
        setTitle("GA Prototype");

        Container container = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        textArea.append(population.toString());

        scrollPane = new JScrollPane(textArea);

        button = new JButton("Do a generation");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonClicked();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(button);

        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.LINE_AXIS));
        botPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));
        botPanel.add(scrollPane);

        container.add(topPanel);
        container.add(botPanel);

        pack();
        setVisible(true);
    }

    private void buttonClicked() {
        population.evaluateFitness(textArea);
        population.selectMatingPool(textArea);
        population.applyGeneticOperators(textArea);
        population.replacement(textArea);

        textArea.append("\n");

        textArea.append(population.toString());
    }
}
