import javax.swing.*;

public class Main extends JFrame {
    Main(){
        setTitle("Minesweeper GUI");
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);        // Close correctly the frame
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Main();
    }
}