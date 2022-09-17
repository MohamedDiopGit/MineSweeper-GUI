/*
    Graphical User Interface
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GUI extends JPanel{


    private final Field field;

    private final Main main;

    private Timer timer;
    private int seconds =0;  // Seconds elapsed since the beginning
    private JLabel score = new JLabel();
    private int scoreTemp = 0;
    private JLabel timeSession = new JLabel();
    private JButton restart = new JButton("Restart"); // restart button
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
        this.displayStartEmptyField();
        this.restartGame();
    }

    public void displayStartEmptyField(){
        remove(panelCenter);  // initialization of the panel
        panelCenter = new JPanel();
        add(panelCenter, BorderLayout.CENTER);

        int dimParam = this.field.getDim();     // Get the dimensions of the field
        panelCenter.setLayout(new GridLayout(dimParam, dimParam));

        for (int x = 0; x < dimParam; x++) {
            for (int y = 0; y < dimParam; y++) {    // For loop on the matrix to display all objects
                JButton box = new JButton();  // Clickable button on each minefield's boxes
                box.setBackground(Color.WHITE);
                box.setPreferredSize(new Dimension(60,50));
                panelCenter.add(box);

                final int xOnStart = x;
                final int yOnStart = y;
                box.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        initializationField(xOnStart,yOnStart);
                        setVisible(true);       // Update of the frame fixing frozen frame bug
                    }
                });
            }
        }
    }

    public void initializationField(int xOnStart,int yOnStart) {

        remove(panelCenter);  // initialization of the panel
        panelCenter = new JPanel();
        add(panelCenter, BorderLayout.CENTER);

        int dimParam = this.field.getDim();     // Get the dimensions of the field
        panelCenter.setLayout(new GridLayout(dimParam, dimParam));

        for (int x = 0; x < dimParam; x++) {
            for (int y = 0; y < dimParam; y++) {    // For loop on the matrix to display all objects
                final int xBox = x;
                final int yBox = y;


                JButton box = new JButton(this.field.getElementFromXY(x,y,false));  // Clickeable button on each minefield's boxes
                box.setBackground(Color.WHITE);
                box.setPreferredSize(new Dimension(60,50));
                panelCenter.add(box);



                if(box.getText() == "x"){  // If there is a mine
                    box.setText("x");   // Hide it with a white background and not text
                    
                    box.addMouseListener(new MouseAdapter() { // OnClick event : Place a flag or trigger the "Game over event"
                        @Override
                        public void mouseClicked(MouseEvent event) {

                            if (isRightMouseButton(event))  // Set the box with a red flag
                            {
                                if(field.getElementFromXY(xBox,yBox,false) == "x" && box.getText() != "🚩"){  // Chech if there is a mine and not flagged before
                                   scoreTemp++;
                                   score.setText(String.valueOf(scoreTemp));
                                }
                                box.setText("🚩");
                            }
                            else if(isLeftMouseButton(event) && box.getText() != "🚩"){  // Check if Left click and not a mine discovered : GAME OVER
                                // Code To popup an Game Over message :
                                JOptionPane.showMessageDialog(main, "You clicked on a mine : Game Over LOOSER 🤣",
                                        "ERROR", JOptionPane.WARNING_MESSAGE);
                                reInitField();
                            }
                        }
                    });
                }



                else if(box.getText() == "0") {
                    double deltaX = Math.abs(xOnStart-xBox);
                    double deltaY = Math.abs(yOnStart-yBox);
                    double result = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
                    Random alea = new Random();

                    if(result <= alea.nextDouble((int) dimParam/1.5)){  // Undisover boxes that are in a calculated area with euclidian distance as radius


                        box.setBackground(Color.GRAY); // Color the unblocked boxes with gray color
                        box.setText(field.getElementFromXY(xBox,yBox,true));  // Update the text in the box with the computed value

                        switch(Integer.valueOf( box.getText() ) ){     // Set the Color of the number depending on its value
                            case 0:
                                box.setBackground(Color.GRAY);
                                break;
                            case 1:
                                box.setForeground(Color.BLUE);
                                break;
                            case 2:
                                box.setForeground(Color.GREEN);
                                break;
                            case 3:
                                box.setForeground(Color.RED);
                                break;
                            case 4:
                                box.setForeground(Color.ORANGE);
                                break;
                            case 5:
                                box.setForeground(Color.MAGENTA);
                                break;
                            case 6:
                                box.setForeground(Color.CYAN);
                                break;

                        }
                    }
                    else{  // Boxes outside the calculated area
                        box.setText("");  // The boxes are still hidden
                        box.addActionListener(new ActionListener() {  // OnClick event : Discover them and their computed value
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                box.setText(field.getElementFromXY(xBox,yBox,true));
                                box.setBackground(Color.GRAY);
                                switch(Integer.valueOf( box.getText() ) ){      // Set the Color of the number depending on its value
                                    case 0:
                                        box.setBackground(Color.GRAY);
                                        break;
                                    case 1:
                                        box.setForeground(Color.BLUE);
                                        break;
                                    case 2:
                                        box.setForeground(Color.GREEN);
                                        break;
                                    case 3:
                                        box.setForeground(Color.RED);
                                        break;
                                    case 4:
                                        box.setForeground(Color.ORANGE);
                                        break;
                                    case 5:
                                        box.setForeground(Color.MAGENTA);
                                        break;
                                    case 6:
                                        box.setForeground(Color.CYAN);
                                        break;

                                }

                            }  
                        });
                        
                    }
                }
            }
        }
    }


    public void restartGame(){
        add(restart, BorderLayout.SOUTH);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reInitField();
            }
        });

    }

    public void reInitField(){
        seconds =0;
        scoreTemp = 0;
        score.setText(String.valueOf(scoreTemp));
        timeSession.setText(String.valueOf(seconds));
        timer.start();

        this.main.getField().initField();
        this.displayStartEmptyField();
    }

    public void timeElapsed(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timeSession.setText(String.valueOf(seconds));
            }
        });
    }


    public void displayScore(){
        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add( new JLabel("Score: "));
        panelNorth.add(score);
        panelNorth.add( new JLabel(" | Time Elapsed(s): "));
        panelNorth.add(timeSession);


    }

}
