/*
 * 
 *  Main application : Minesweeper application
 */

import javax.swing.*;

public class Main extends JFrame {
    private final GUI gui;
    private final Field field = new Field(Levels.EASY);  // EASY mode for start / by default
    Main(){
        System.out.println("Minesweeper GUI : Launched");
        setTitle("Minesweeper GUI");
        this.field.initField();   // initialisation of the field
        gui = new GUI(this);
        setContentPane(gui);  // Set the center Panel for the frame
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);        // Close correctly the frame
    }
    public static void main(String[] args) {
        new Main();  // Running the minesweeper program
    }


    public Field getField(){  // Getter of the field
        return this.field;
    }
}