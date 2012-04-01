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
    public static final int HEIGHT = 200;
    JButton addButton, maxButton, iOwnButton, ideeButton;
    JButton aveButton, stopButton, startButton;
    //TextArea theTA = new TextArea();
    final static int IDEE = 0;
    final static int OWN = 1;
    final static int AVE = 2;
    final static int MAX = 3;
    int driverType = IDEE;
    int count; // count the cars?

    JSpinner numLanesSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)); 
    final JSpinner numCarsSpinner  = new JSpinner(new SpinnerNumberModel(100, 1, 300, 50)); 

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
        JLabel driverTypeLabel = new JLabel("Driver type:");


        /*addButton = new Button("add 100 cars");
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                count += 100;
                //theTA.append("count=" + count);
                for (int i = 0; i < 100; i++) {
                    Vehicle newVehicle;
                    int r = rand(10);

                    if (r != 0) {
                        newVehicle = new Car(randLoc(), byDriverType(driverType), randSpeed(), "slow", driverType);
                    } else {
                        newVehicle = new Semi(randLoc(), byDriverType(driverType), randSpeed(), "slow", driverType);
                    }

                    //Car newCar = new Car(randLoc(), byDriverType(driverType), randSpeed(), "slow", driverType);
                    myModel.addVehicle(newVehicle);
                    newVehicle.setPreferredLaneIf();  // if idee fixee
                }
            }
        });

        aveButton = new Button("ave driver");
        add(aveButton);
        aveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                driverType = AVE;
            }
        });

        maxButton = new Button("max driver");
        add(maxButton);
        maxButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                driverType = MAX;
            }
        });

        iOwnButton = new Button("own it");
        add(iOwnButton);
        iOwnButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                driverType = OWN;
            }
        });

        ideeButton = new Button("idee");
        add(ideeButton);
        ideeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                driverType = IDEE;
            }
        });
        */

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
                Random randGen = new Random();
                for (int i = 0; i < (Integer)(numCarsSpinner.getValue()); i++) {
                    Vehicle newVehicle;
                    int r = randGen.nextInt(10);

                    if (r != 0) {
                        newVehicle = new Car(randLoc(), byDriverType(driverType), randSpeed(), driverType);
                    } else {
                        newVehicle = new Semi(randLoc(), byDriverType(driverType), randSpeed(), driverType);
                    }

                    //Car newCar = new Car(randLoc(), byDriverType(driverType), randSpeed(), "slow", driverType);
                    myModel.addVehicle(newVehicle);
                    newVehicle.setPreferredLaneIf();  // if idee fixee
                }

                myModel.theController.start();
            }
        });

        panel.add(numLanesLabel);
        panel.add(numLanesSpinner);
        panel.add(numCarsLabel);
        panel.add(numCarsSpinner);
        panel.add(driverTypeLabel);
        panel.add(startButton);
        panel.add(stopButton);

        add(panel);

        pack();
        setVisible(true);
        //add(theTA);

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

    public void paint(Graphics g) {         // let the Model paint itself ?!
        myModel.paint(g);
    }

    Color randColor() {
        return new Color(getRand(256), getRand(256), getRand(256));
    }

    int randLoc() {
        return (int) (Math.random() * WIDTH);
    }

    int getRand(int max) {
        return (int) (Math.random() * max);
    }

    public int numCars() {
        return (Integer)(numCarsSpinner.getValue());
    }
}
