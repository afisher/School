import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class SpecialSlider extends JSlider {
    public SpecialSlider(int min, int max, int defaultVal, int majorTick, int minorTick) {
        super(min, max, defaultVal);
        setMajorTickSpacing(majorTick);
        setMinorTickSpacing(minorTick);
        setPaintTicks(true);
        setPaintLabels(true);
    }
}
