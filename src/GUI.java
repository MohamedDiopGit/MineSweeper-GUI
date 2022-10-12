import java.awt.*;
import java.awt.event.*;
import java.util.Random;
// import java.util.Scanner;  // For terminal entries mode, uncomment it if you want to use terminal entries. 
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * {@code GUI} : Graphic User Interface class, extends {@code JPanel}.
 * The main component that runs the Graphic interface,
 * and manages the front and back-end processes to call entities and specific
 * functions
 * in order to display the information.
 * It allows to display the grid, the menubar on the main frame and the pop-ups
 * to give and collect data correctly.
 */
public class GUI extends JPanel {

    /**
     * Field to be process for the grid and display in the GUI.
     */
    private Field field;

    /**
     * imported Main from the "main.java".
     */
    private Main main;

    /**
     * Timer for the session which update every second the {@code timeSession}.
     */
    private Timer timer;

    /**
     * Seconds elapsed since the beginning.
     */
    private int seconds = 0;
    /**
     * scoreLabel of the current game session.
     */
    private JLabel scoreLabel = new JLabel();
    /**
     * scoreLabel of the current game session to be copy on the {@code JLabel} instance.
     */
    private int score;
    private boolean propagated = false;

    /**
     * Time session (elapsed) information to display
     */
    private JLabel timeSession = new JLabel();

    /**
     * restart button's text
     */
    private ImageIcon restartImg = new ImageIcon("restart.png");
    private JButton restart = new JButton(new ImageIcon(restartImg.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH) ));
    // private JButton restart = new JButton(new ImageIcon("restart.png"));

    /**
     * Pane in the center of the screen that displays the grid
     */
    private JPanel panelCenter = new JPanel();
    /**
     * Current game level of the session
     * 
     * @see Levels
     */
    private Levels levelGame;

    /**
     * Game mode's text
     */
    private JLabel levelGameModeInfo = new JLabel();
    JPanel panelNorth = new JPanel(new FlowLayout());
    JPanel panelNorthCenter = new JPanel(new FlowLayout());

    /**
     * Constructor for the GUI, which starts the game.
     * 
     * @param {@code Main} : the main component that contains a {@code Field}.
     * @see #startNewGame()
     */
    GUI(Main main) {
        this.main = main;
        this.field = main.getField();
        startNewGame();
        panelNorth.setBackground(Color.lightGray);
        setBorder(BorderFactory.createRaisedBevelBorder());
        panelNorth.setBorder(BorderFactory.createLoweredBevelBorder());

    }

    public void propagationCase(int xOrigin, int yOrigin){

        propagated = true;
    }

    /**
     * Global starter method which starts and initializes the game
     * by launching {@code displayGUI()}.
     * {@code Field.initField()} methods and catch the game level in the GUI.
     * 
     * @see #displayGUI()
     */
    public void startNewGame() {
        field.initField();

        // Deprecated method of level initialization
        this.levelGame = field.getLevel();
        this.displayGUI();
    }

    /**
     * Main GUI initialization's method for the main frame, it displays the menu,
     * the time elapsed
     * the current scoreLabel, the restart button, and display the field at the beginning
     * (with hidden boxes)
     * on the frame.
     * 
     * @see #displayMenu()
     * @see #displayscoreLabel()
     * @see #timeElapsed()
     * @see #restartButton()
     * @see #reInitField()
     * @see #displayStartEmptyField()
     */
    public void displayGUI() {
        setLayout(new BorderLayout());
        this.timeElapsed();
        this.displayMenu();
        this.restartButton();
        this.reInitField();
        this.initializationField();  // Comment to have "Case" version of box
        // this.initializationField(0, 0); // Uncomment to have "Case" version of box
    }

    /**
     * Displays the menu bar for choosing between multiple difficulties
     * and display their informations.
     * It adds the {@code ActionListener} on the difficulty options,
     * in order to {@code startNewGame()} with parameters depending on level game
     * mode.
     * 
     * @see #startNewGame()
     */
    public void displayMenu() {
        remove(panelNorth);
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.removeAll();
        panelNorth.setLayout(new BorderLayout());



        JMenuBar menuBar = new JMenuBar();
        JMenuItem menu = new JMenu("Mode");
        JMenuItem easyMode = new JMenuItem("EASY");
        JMenuItem mediumMode = new JMenuItem("MEDIUM");
        JMenuItem hardMode = new JMenuItem("HARD");
        JMenuItem customMode = new JMenuItem("CUSTOM");
        JButton quit = new JButton("Quit");
        JMenuItem saveGame = new JMenuItem("Save");


        // Server options
        JMenuItem connectedClients = new JMenuItem("Connected clients");
        JMenuItem initConnection = new JMenuItem("Connection to server");
        JMenu infoServer = new JMenu("Server");
  
        infoServer.add(connectedClients);
        infoServer.add(initConnection);

        levelGameModeInfo.setText(String.valueOf(levelGame));

        quit.setBackground(Color.RED);
        quit.setForeground(Color.WHITE);

        menu.add(easyMode);
        menu.add(mediumMode);
        menu.add(hardMode);
        menu.add(customMode);
        
        menuBar.add(infoServer);
        menuBar.add(menu);
        menuBar.add(levelGameModeInfo);
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());

        // menuBar.add(quit);
        // restart.setBackground(Color.WHITE);
        menuBar.add(saveGame);
        
        timeSession.setForeground(Color.RED);
        scoreLabel.setForeground(Color.RED);

        scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        timeSession.setFont(new Font("Monospaced", Font.BOLD, 30));

        JPanel panelNorthWest = new JPanel(new FlowLayout());
        JPanel panelNorthEast = new JPanel(new FlowLayout());
        panelNorthCenter = new JPanel(new FlowLayout());

        panelNorthWest.add(scoreLabel);
        panelNorthEast.add(timeSession);
        
        panelNorthWest.setBackground(Color.black);
        panelNorthEast.setBackground(Color.black);


        restart.setBackground(Color.lightGray);
        restart.setBorder(BorderFactory.createEmptyBorder());
        panelNorthCenter.add(restart);
        panelNorthCenter.setBackground(Color.lightGray);


        panelNorth.add(panelNorthEast, BorderLayout.EAST);
        panelNorth.add(panelNorthCenter, BorderLayout.CENTER);
        panelNorth.add(panelNorthWest, BorderLayout.WEST);

        

        // Add menu options
        saveGame.addActionListener(evt -> saveGameLevel());
        quit.addActionListener(evt -> System.exit(0));

        // Add different mode in the menu
        easyMode.addActionListener(evt -> selectorLevelGame(Levels.EASY));
        mediumMode.addActionListener(evt -> selectorLevelGame(Levels.MEDIUM));
        hardMode.addActionListener(evt -> selectorLevelGame(Levels.HARD));
        customMode.addActionListener(evt -> selectorLevelGame(Levels.CUSTOM));



        // Addd the menu bar to the main frame.
        main.setJMenuBar(menuBar);




    }

    public void selectorLevelGame(Levels level) {
        field = new Field(level);
        levelGameModeInfo.setText(String.valueOf(level));
        startNewGame();
    }


    /**
     * This function takes the first "clicked" box {@code JButton}
     * to initialize the grid {@code GridLayout} from {@code JPanel} with the field
     * and uncover some boxes around it. It also adds {@code MouseAdapter} or
     * {@code ActionListener} events depending
     * on the value of the box (mine, hidden box, non-mine) and the user behaviour
     * (right or left click).
     * 
     * @param xOnStart : the x position of the first "clicked" on the hidden boxes'
     *                 field
     * @param yOnStart : the y position of the same box
     */
    public void initializationField() { // Initialization of boxes with different values for a
                                                                  // certain area / allow to place flags on mines

        remove(panelCenter); // initialization of the panel
        panelCenter = new JPanel();
        add(panelCenter, BorderLayout.CENTER);
        panelCenter.setBorder(BorderFactory.createLoweredBevelBorder());
        int dimParam = this.field.getDim(); // Get the dimensions of the field
        panelCenter.setLayout(new GridLayout(dimParam, dimParam));

        // Loop on the entire field elements
        for (int x = 0; x < dimParam; x++) {
            for (int y = 0; y < dimParam; y++) { // For loop on the matrix to display all objects

                /**
                 * Case mode for boxes. Uncomment panelCenter.add(boxCase) to get it.
                 */
                // Add a box on the grid
                // minefield's boxes
                Case boxCase = new Case(x,y,this);
                panelCenter.add(boxCase);

                /**
                 * Box case with JButton mode. Uncomment panelCenter.add(box) to get it.
                 */
                
            }
        }
    }

    /**
     * Activates the restart button by adding an {@code ActionListener} event
     * on the restart button. It will call the {@code reInitField()} method.
     * 
     * @see #reInitField()
     */
    public void restartButton() { // Restart a game
        
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reInitField();
            }
        });

    }

    /**
     * Generates a new field, and restarts the timer, and the scoreLabel of the current
     * game.
     * It also calls the {@code displayStartEmptyField()} method to clear the
     * field/grid.
     * 
     * @see #displayStartEmptyField()
     */
    public void reInitField() {
        seconds = 0;
        score = field.getNumberOfMines();
        scoreLabel.setText(String.valueOf(score));
        timeSession.setText(String.valueOf(seconds));
        timer.start();

        this.main.getField().initField();
        this.initializationField();
    }

    /**
     * Processes the time elapsed since the beginning of the start of a game
     * session.
     * It also checks if the time session has outdated the time limit, if so,
     * it will reinitialize the game after showing a popup (Game over) to the user.
     * 
     * @see #reInitField()
     */
    public void timeElapsed() { //
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timeSession.setText(String.valueOf(seconds));
            }
        });
    }

    /**
     * Saves the game level in a local file "LevelRegistred.dat"
     */
    public void saveGameLevel() {
        new LevelsFileWriter(this.levelGame);
    }



    public Field getFieldFromGUI() {
        return field;
    }

    public void upScore() {
        score++;
        scoreLabel.setText(String.valueOf(score));
    }

    public void downScore() {
        score--;
        scoreLabel.setText(String.valueOf(score));
    }

}
