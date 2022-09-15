import javax.swing.*;

public class Main extends JFrame {
    private final GUI gui;
    private final Field field = new Field(Levels.MEDIUM);
    Main(){
        setTitle("Minesweeper GUI");
        this.field.initField();   // initialisation of the field
        gui = new GUI(this);

        setContentPane(gui);  // Set the center Panel for the frame

        //getContentPane().add(lab);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);        // Close correctly the frame
    }
    public static void main(String[] args) {
        System.out.println("Minesweeper GUI : Launched");
        new Main();
        System.out.println("Minesweeper GUI : Closed.");
    }


    public Field getField(){  // Getter of the field
        return this.field;
    }
}