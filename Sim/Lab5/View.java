/*  View.java

The view for the prototype traffic simulation
Illustrates the use of a button without drag and drop; it uses an
in-line anonymous class to handle the action event.

Author: JRL 10/98
Modified for Phase 1 of Lab 7: 11/11/00
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class View extends JFrame {
    Model myModel;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 160;
    JButton addButton, maxButton, iOwnButton, ideeButton;
    JButton aveButton, stopButton, startButton, clearButton;
    final static int IDEE = 0;
    final static int OWN = 1;
    final static int AVE = 2;
    final static int MAX = 3;
    final static int CRAZY = 4;
    int count; // count the cars?

    private JSpinner numLanesSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)); 
    private final JSpinner numCarsSpinner  = new JSpinner(new SpinnerNumberModel(100, 0, 300, 50)); 
    private final JSpinner numSemisSpinner  = new JSpinner(new SpinnerNumberModel(10, 0, 300, 10)); 
    private static final Object[] typeNames = {"Idee", "Own", "Average", "MaxHeadRoom", "Crazy"};
    private final JComboBox driverTypeComboBox = new JComboBox(typeNames);

    private boolean running = false;

    int randSpeed() {
        return 50 + rand(25);
    }

    int rand(int max) {
        return (int) (max * Math.random());
    }

    public View(Model m) {
        myModel = m;

        JPanel panel = new JPanel();

        JLabel numLanesLabel = new JLabel("Num lanes:");
        JLabel numCarsLabel = new JLabel("Num cars:");
        JLabel numSemisLabel = new JLabel("Num semis:");
        JLabel driverTypeLabel = new JLabel("Driver type:");

        addButton = new JButton("add");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // adjust the number of lanes
                int numLanes   = (Integer)(numLanesSpinner.getValue());

                if (numLanes > Controller.numLanes) {
                    for (int i = 0; i < numLanes - Controller.numLanes; i++) {
                        myModel.theRoad.addLane();
                    }
                } else if (numLanes < Controller.numLanes) {
                    for (int i = 0; i < Controller.numLanes - numLanes; i++) {
                        myModel.theRoad.removeLane();
                    }
                }
                Controller.numLanes = numLanes;

                // add cars
                int driverType = driverTypeComboBox.getSelectedIndex();

                for (int i = 0; i < (Integer)(numCarsSpinner.getValue()); i++) {

                    Vehicle newVehicle = new Car(randLoc(), byDriverType(driverType), randSpeed(), driverType);

                    myModel.addVehicle(newVehicle);
                    newVehicle.setPreferredLaneIf();  // if idee fixee
                }
                
                for (int i = 0; i < (Integer)(numSemisSpinner.getValue()); i++) {

                    Vehicle newVehicle = new Semi(randLoc(), byDriverType(driverType), randSpeed(), driverType);

                    myModel.addVehicle(newVehicle);
                    newVehicle.setPreferredLaneIf();  // if idee fixee
                }

                myModel.theRoad.repaint();
            }
        });

        stopButton = new JButton("exit");
        add(stopButton);
        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startButton = new JButton("start");
        add(startButton);
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                running = !running;
                if (running) {
                    myModel.theController.setRunning(true);
                    myModel.theController.run();
                    startButton.setText("stop");
                } else {
                    myModel.theController.setRunning(false);
                    startButton.setText("start");
                }


            }
        });

        clearButton = new JButton("clear");
        add(clearButton);
        clearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myModel.theRoad.clear();
                myModel.theRoad.repaint();
            }
        });

        panel.add(numLanesLabel);
        panel.add(numLanesSpinner);
        panel.add(numCarsLabel);
        panel.add(numCarsSpinner);
        panel.add(numSemisLabel);
        panel.add(numSemisSpinner);
        panel.add(driverTypeLabel);
        panel.add(driverTypeComboBox);
        panel.add(addButton);
        panel.add(startButton);
        panel.add(clearButton);
        panel.add(stopButton);

        add(panel);

        pack();
        setVisible(true);
    }

    Color byDriverType(int driverType) {
        switch (driverType) {
            case IDEE:
                return Color.red;
            case OWN:
                return Color.black;
            case AVE:
                return Color.green;
            case MAX:
                return Color.BLUE;
        }

        return Color.pink;
    }

    public void update(Graphics g) {        // no flash
        paint(g);
    }

    Color randColor() {
        return new Color(getRand(256), getRand(256), getRand(256));
    }

    int randLoc() {
        return (int) (Math.random() * RoadPanel.width);
    }

    int getRand(int max) {
        return (int) (Math.random() * max);
    }

    public int numCars() {
        return (Integer)(numCarsSpinner.getValue());
    }
}
