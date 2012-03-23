/*  View.java

The view for the prototype traffic simulation
Illustrates the use of a button without drag and drop; it uses an
in-line anonymous class to handle the action event.

Author: JRL 10/98
Modified for Phase 1 of Lab 7: 11/11/00
 */

import java.awt.*;
import java.awt.event.*;

public class View extends Panel {
    Model myModel;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 300;
    Button addButton, maxButton, iOwnButton, ideeButton;
    Button aveButton, stopButton, startButton;
    TextArea theTA = new TextArea();
    final static int IDEE = 0;
    final static int OWN = 1;
    final static int AVE = 2;
    final static int MAX = 3;
    int driverType = IDEE;
    int count; // count the cars?

    int randSpeed() {
        return 50 + rand(25);
    }

    int rand(int max) {
        return (int) (max * Math.random());
    }

    public View(Model m) {
        myModel = m;
        addButton = new Button("add 100 cars");
        add(addButton);
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                count += 100;
                theTA.append("count=" + count);
                for (int i = 0; i < 100; i++) {
                    Car newCar = new Car(randLoc(), byDriverType(driverType), randSpeed(), "slow", driverType);
                    myModel.addVehicle(newCar);
                    newCar.setPreferredLaneIf();  // if idee fixee
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

        stopButton = new Button("exit");
        add(stopButton);
        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startButton = new Button("start");
        add(startButton);
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myModel.theController.start();
            }
        });
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
        System.out.println("shere" + (int) (Math.random() * WIDTH));
        return (int) (Math.random() * WIDTH);
    }

    int getRand(int max) {
        return (int) (Math.random() * max);
    }
}
