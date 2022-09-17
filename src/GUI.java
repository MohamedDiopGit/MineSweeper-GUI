/*
    Graphical User Interface
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
// import java.util.Scanner;  // For terminal entries mode

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class GUI extends JPanel{


    private Field field;    // Field to play with and display in the GUI

    private Main main;  // imported Main from the "main.java"

    private Timer timer;    // timer for the session which update every second the "timeSession"
    private int seconds =0;  // Seconds elapsed since the beginning
    private final int timeLimits[] = {300,100,30}; // Number of seconds fixing the time limit : it should depends on the level 
    private int timeLimit;  // Time limit for the game session
    
    private JLabel score = new JLabel();    // score of the current game session
    private int scoreTemp = 0;
    
    
    private JLabel timeSession = new JLabel();  // Time session (elapsed) information to display
    private JButton restart = new JButton("Restart"); // restart button
    private JPanel panelCenter = new JPanel();
    private Levels levelGame;       // Current game level
    private JLabel timeLimitInfo = new JLabel();    // Time limit info / selected
    private JLabel levelGameModeInfo = new JLabel();   

    GUI(Main main){     // Constructor for the GUI
        this.main = main;
        this.field = main.getField();
        startNewGame();
    }

    public void startNewGame(){      // Global starter method
        field.initField();
        this.levelGame = field.getLevel();
        this.setTimeLimit();
        this.displayGUI();
    }
    public void setTimeLimit(){ // Allow to set the time limit via Popup or terminal
        if(levelGame.ordinal() == 3){   // CUSTOM limit from Custom level option

            // /* TERMINAL ENTRIES MODE : */
            // Scanner sc = new Scanner(System.in);
            // System.out.print("[CUSTOM] Select the time limit: ");
            // int parameter = sc.nextInt();

            JTextField parameter = new JTextField();
            Object[] message = {
                "Time limit (s):", parameter,
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Set Game Parameters", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) { // Check if OK_OPTION is ok
                this.timeLimit = Integer.valueOf(parameter.getText());
            }  
        }
        else{
            this.timeLimit = timeLimits[levelGame.ordinal()];
        }
        timeLimitInfo.setText(String.valueOf(timeLimit));
    }
    public void displayGUI(){       // General GUI initialization's method
        setLayout(new BorderLayout());
        this.displayMenu();
        this.timeElapsed();
        this.displayScore();
        this.restartGame();
        this.reInitField();
        this.displayStartEmptyField();
    }
    public void displayMenu(){  // Menu bar for choosing between multiple difficulies and display informations
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menu = new JMenu("Difficulty");
        JMenuItem easyMode =new JMenuItem("EASY");
        JMenuItem mediumMode=new JMenuItem("MEDIUM");
        JMenuItem hardMode=new JMenuItem("HARD");
        JMenuItem customMode=new JMenuItem("CUSTOM");
        JLabel timeLimitText = new JLabel("Time Limit: ");
        JLabel levelGameModeText = new JLabel(" | Mode: ");
 
        levelGameModeInfo.setText("EASY");

        menu.add(easyMode);
        menu.add(mediumMode);
        menu.add(hardMode);
        menu.add(customMode);
        menuBar.add(menu);
        main.setJMenuBar(menuBar);

        // Add different mode on the menu
        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field = new Field(Levels.EASY);
                levelGameModeInfo.setText("EASY");
                startNewGame();
            }
        });
        mediumMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field = new Field(Levels.MEDIUM);
                levelGameModeInfo.setText("MEDIUM");
                startNewGame();
            }
        });
        hardMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field = new Field(Levels.HARD);
                levelGameModeInfo.setText("HARD");
                startNewGame();
            }
        });
        customMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field = new Field(Levels.CUSTOM);
                levelGameModeInfo.setText("CUSTOM");
                startNewGame();
            }
        });


        menuBar.add(timeLimitText);
        menuBar.add(timeLimitInfo);
        menuBar.add(levelGameModeText);
        menuBar.add(levelGameModeInfo);

    }
    public void displayStartEmptyField(){  // Restart the game with blank boxes
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


    // This function takes the first "clicked" boxe to initialize the field and clear some boxes around it
    public void initializationField(int xOnStart,int yOnStart) {   // Initialization of boxes with different values for a certain area  / allow to place flags on mines

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
                    box.setText("");   // Hide it with a white background and not text
                    
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
                                        "GAME OVER", JOptionPane.WARNING_MESSAGE);
                                reInitField();
                            }
                        }
                    });
                }



                else if(box.getText() == "0") {     // Operations on non-mined boxes
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

                                box.setText(field.getElementFromXY(xBox,yBox,true));    // Change the value with the computed one
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
                        }
                    });
                    }
                }
            }
        }
    }


    public void restartGame(){      // Restart a game 
        add(restart, BorderLayout.SOUTH);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reInitField();
            }
        });

    }

    public void reInitField(){      // re-init a field, timer, and parameter of the game
        seconds = 0;
        scoreTemp = 0;
        score.setText(String.valueOf(scoreTemp));
        timeSession.setText(String.valueOf(seconds));
        timer.start();

        this.main.getField().initField();
        this.displayStartEmptyField();
    }

    public void timeElapsed(){      // Display the time Elapsed since the beginning
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timeSession.setText(String.valueOf(seconds));
                if(seconds == timeLimit){
                    JOptionPane.showMessageDialog(main, "TIME LIMIT : Game Over LOOSER 🤣",
                                        "GAME OVER", JOptionPane.WARNING_MESSAGE);
                    reInitField();
                }
            }
        });
    }


    public void displayScore(){     // Display the current score of the player
        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add( new JLabel("Score: "));
        panelNorth.add(score);
        panelNorth.add( new JLabel(" | Time Elapsed(s): "));
        panelNorth.add(timeSession);


    }

}
