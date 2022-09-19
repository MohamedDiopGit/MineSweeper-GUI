import javax.swing.*;

/**
 * {@code Main} application : Minesweeper program.
 */
public class Main extends JFrame {
    /**
     * Main GUI for the game : Minesweeper.
     */
    private final GUI gui;

    /**
     * Field to start with in the game.
     */
    private final Field field = new Field(Levels.EASY); // EASY mode for start / by default

    Main() {
        System.out.println("Minesweeper GUI : Launched");
        setTitle("Minesweeper GUI");
        this.field.initField(); // initialisation of the field

        gui = new GUI(this);
        setContentPane(gui); // Set the center Panel for the frame
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close correctly the frame
    }

    /**
     * Runs the minesweeper program.
     * 
     * @param args : optional arguments to pass to the program.
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Returns the current field that {@code Main} is running.
     * 
     * @return {@code Field}
     */
    public Field getField() { // Getter of the field
        return this.field;
    }
}