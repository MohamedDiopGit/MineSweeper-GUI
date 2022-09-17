/*
    Graphical User Interface
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel{


    private final Field field;

    private final Main main;

    private Timer timer;
    private int seconds =0;  // Seconds elapsed since the beginning
    private JLabel score = new JLabel("Score: "); // Score of the minesweeper
    private JButton restart = new JButton("Restart"); // restart button
    private JLabel scoreDeux = new JLabel(); // Score to display
    private JPanel panelCenter = new JPanel();

    GUI(Main main){
        this.main = main;
        this.field = main.getField();
        this.displayGUI();
    }

    public void displayGUI(){
        setLayout(new BorderLayout());
        this.timeElapsed();
        this.displayScore();
        this.initialisationField();
        this.restartGame();
    }

    public void initialisationField() {

        ;
        add(panelCenter, BorderLayout.CENTER);

        int dimParam = this.field.getDim();     // Get the dimensions of the field
        panelCenter.setLayout(new GridLayout(dimParam, dimParam));

        for (int x = 0; x < dimParam; x++) {
            for (int y = 0; y < dimParam; y++) {    // For loop on the matrix to display all objects
                final int xBox = x;
                final int yBox = y;
                JButton box = new JButton(this.field.getElementFromXY(x,y,false));  // Clickeable button on each minefield's boxes
                panelCenter.add(box);
                box.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        box.setText(field.getElementFromXY(xBox,yBox,true));
                    }
                });

            }
        }
    }

    public void displayScore(){
        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add(score);
        panelNorth.add(scoreDeux);
    }


    public void restartGame(){
        add(restart, BorderLayout.SOUTH);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds =0;
                scoreDeux.setText(String.valueOf(seconds));
                timer.start();
            }
        });

    }


    public void timeElapsed(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                scoreDeux.setText(String.valueOf(seconds));
            }
        });
    }
}
