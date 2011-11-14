import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 *
 * @author Ashley Fisher
 */
public class Dialog extends JFrame {
    BoardGUI parentFrame;

    public Dialog(BoardGUI parent, String title, String text) {
        parentFrame = parent;
        setTitle(title);

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel(text);
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonClicked();
            }
        });

        container.add(label);
        container.add(button);
       
        setVisible(true);
        pack();
    }

    private void buttonClicked() {
        parentFrame.exit();
        dispose();
    }
}
