/*
    Graphical User Interface
 */

import javax.swing.*;

public class GUI extends JPanel{
    JLabel lab = new JLabel("Hi !");
    JLabel lab2 = new JLabel("Hi for the second time !");

    GUI(){
        this.add(lab);
        this.add(lab2);      // Add the label on the panel
    }

}
