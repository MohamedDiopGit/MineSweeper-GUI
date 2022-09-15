/*
    Graphical User Interface
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI extends JPanel{
    JLabel lab = new JLabel("Hi !");
    JLabel lab2 = new JLabel("Hi for the second time !");

    private final Field field;
    private final Main main;

    GUI(Main main){
        this.main = main;
        this.field = main.getField();
        this.displayGUI();
    }

    public void displayGUI() {


        JPanel monPanel = new JPanel();
        int dimParam = this.field.getDim();
        monPanel.setLayout(new GridLayout(dimParam, dimParam));

        for (int x = 0; x < dimParam; x++) {
            for (int y = 0; y < dimParam; y++) {    // For loop on the matrix to display all objects
                monPanel.add( new JLabel(this.field.getElementFromXY(x,y) ) );
            }
        }
        add(monPanel);
    }

}
