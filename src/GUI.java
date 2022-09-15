/*
    Graphical User Interface
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JPanel{
    JLabel lab = new JLabel("Hi !");
    JLabel lab2 = new JLabel("Hi for the second time !");

    GUI(Field field){
        setLayout(new BorderLayout());
        this.add(lab, BorderLayout.NORTH);
        this.add(lab2, BorderLayout.SOUTH);      // Add the label on the panel
    }

}
