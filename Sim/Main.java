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
    private boolean chattering = true;
    private int delay = 200;
    private Random randGen = new Random();

    private JSpinner  runsSpinner;
    private JButton   stayButton;
    private JButton   switchButton;
    private JButton   quitButton;
    private JTextArea theTA;

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        setTitle("Let's make a deal!");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        runsSpinner = new JSpinner();
        runsSpinner.setValue(1000);

        stayButton = new JButton("Stay");
        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stayButtonClicked();
            }
        });

        switchButton = new JButton("Switch");
        switchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchButtonClicked();
            }
        });

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quitButtonClicked();
            }
        });

        theTA = new JTextArea();
        theTA.setEditable(false);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(runsSpinner);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(stayButton);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(switchButton);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(quitButton);

        JScrollPane pane = new JScrollPane(theTA);
        pane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pane.setPreferredSize(new Dimension(400, 260));

        add(panel, BorderLayout.NORTH);
        add(pane,  BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void stayButtonClicked() {
        new Thread() {
            public void run() {
                runHelper(false);
            }
        }.start();
    }

    private void switchButtonClicked() {
        new Thread() {
            public void run() {
                runHelper(true);
            }
        }.start();
    }

    private void quitButtonClicked() {
        System.exit(0);
    }

    private void runHelper(boolean doSwitch) {
        int runs = (Integer)(runsSpinner.getValue());

        Random randGen = new Random();

        for (int i = 0; i < runs; i++) {
            Contestant contestant;
            int prize = randGen.nextInt(3);

            if (doSwitch) {
                contestant = new SwitchContestant();
            } else {
                contestant = new StayContestant();
            }

            chat("Behind one door is a fabulous prize! Behind the other two, a donkey.");
            chat("Which door would you like?");
            
            int choice = contestant.guess();

            chat("You have chosen door #" + (choice + 1));
            
            int showDoor;
            do {
                showDoor = randGen.nextInt(3);
            } while (showDoor == choice || showDoor == prize);

            chat("Let me show you what's behind door #" + (showDoor + 1));
            chat("...aw...");
            chat("Would you like to switch to the other door?");

            if (doSwitch) {
                int newChoice = 0;
                while (newChoice == choice || newChoice == showDoor) {
                    newChoice++;
                }
                choice = newChoice;
                chat("The contestant has chosen to switch to door #" + (choice +1));
            } else {
                chat("The contestant has decided to stick with door #" + (choice + 1));
            }

            chat("And... the winning door was... door #" + (prize + 1));
            if (choice == prize) chat("You win!!");
            else chat("Sorry... you have lost.");

            chat("\n");
        }
    }

    private void chat(String s) {
        if (chattering)  {
            theTA.append(s + "\n");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }
}
