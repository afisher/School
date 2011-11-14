import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class StartGUI extends JFrame {
    private JButton hexButton;
    private JButton octButton;
    private JButton trainHexButton;
    private JButton trainOctButton;

    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartGUI();
            }
        });
    }

    public StartGUI() {
        setTitle("Choose a game");

        Container container = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        hexButton = new JButton("hexapawn");
        hexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hexButtonClicked();
            }
        });

        octButton = new JButton("octapawn");
        octButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                octButtonClicked();
            }
        });

        trainHexButton = new JButton("train hex");
        trainHexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trainHexButtonClicked();
            }
        });

        trainOctButton = new JButton("train oct");
        trainOctButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trainOctButtonClicked();
            }
        });


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(hexButton);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(octButton);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(trainHexButton);
        panel.add(Box.createRigidArea(new Dimension(1, 0)));
        panel.add(trainOctButton);

        container.add(panel);

        pack();
        setVisible(true);
    }

    private void hexButtonClicked() {
        new BoardGUI(3);
    }

    private void octButtonClicked() {
        new BoardGUI(4);
    }

    private void trainHexButtonClicked() {
        train(3, 100);
    }

    private void trainOctButtonClicked() {
        train(4, 100);
    }

    private void train(int size, int games) {
        int wins1 = 0, wins2 = 0;

        for (int i = 0; i < games; i++) {
            System.out.println("Game " + i);
            Board board = new Board(size);
            int winner = Constants.EMPTY;
            int tries = 0;
            boolean success = false;
            while(winner == Constants.EMPTY) {
                success = board.randomPlay(Constants.PLAYER1);
                if (success) winner = board.winner();
                if (winner == Constants.EMPTY) {
                    success = board.smartPlay(Constants.PLAYER2);
                    if (success) winner = board.winner();
                }
            }
            if (winner == Constants.PLAYER1) { 
                wins1++;
                Learner.add(board.getLastBoard());
            }
            else if (winner == Constants.PLAYER2) {
                wins2++;
            } else {
                System.out.println("Nobody won? THIS SHOULDN'T HAPPEN!");
            }
        }

        System.out.println("Player 1 wins: " + wins1);
        System.out.println("Player 2 wins: " + wins2);
        System.out.println("Number of stored boards: " + Learner.boardsToAvoid.size());
    }
}
