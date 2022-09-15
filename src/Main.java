import javax.swing.*;

public class Main extends JFrame {
    private final GUI gui;
    private final Field field;
    Main(){
        setTitle("Minesweeper GUI");
        gui = new GUI();
        new GUI();

        setContentPane(gui);  // Set the center Panel for the frame

        //getContentPane().add(lab);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);        // Close correctly the frame
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Main();
    }


    public Field getField(){
        return this.field;
    }
}