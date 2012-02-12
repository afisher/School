import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class SpecialSlider extends JSlider {
    public SpecialSlider() {
        super(0, 10, 1);
        setMajorTickSpacing(2);
        setMinorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);
    }
}
