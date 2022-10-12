import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Case extends JPanel implements MouseListener {
    private int DIM = 60;
    private String text;


    private boolean leftClick = false;
    private boolean rightClick = false;
    private JButton box;
    private GUI gui;
    private int flagPlaced = 0; 

    private ImageIcon bomb = new ImageIcon("bomb.png");
    private ImageIcon flag = new ImageIcon("flag.png");
    
    Case(int x, int y, GUI gui){
        this.gui = gui;
        text = gui.getFieldFromGUI().getElementFromXY(x, y, true);
        setPreferredSize(new Dimension(DIM,DIM));
        // addMouseListener(this);
        box = new JButton(); // Clickeable button on each
        box.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        box.setPreferredSize(new Dimension(getWidth(),getHeight()));
        // add(box);
        box.addMouseListener(new MouseAdapter() { // OnClick event : Place a flag or trigger the "Game over
                                                    // event"
            @Override
            public void mouseClicked(MouseEvent event) {
                if(isRightMouseButton(event)) {
                    rightClick = true;

                }
                else if(isLeftMouseButton(event)) {
                    leftClick = true;
                }
                setVisible(false);
                remove(box);
                setVisible(true);
            }
        });
        // box.addMouseListener(new MouseAdapter() { // OnClick event : Place a flag or trigger the "Game over
        //                                             // event"
        //     @Override
        //     public void mouseClicked(MouseEvent event) {
        //         String boxType = field.getElementFromXY(x, y, false);
        //         String typeClicked = "";
        //         if (isRightMouseButton(event)) // Set the box with a red flag
        //         {
        //             typeClicked = "rightClick";
        //             if (boxType.equals("x") && box.getText() != "F") {
        //                 scoreTemp++;
        //                 gui.changeScore(scoreTemp);
                        
        //                 if (scoreTemp == field.getNumberOfMines()) {
        //                     JOptionPane.showMessageDialog(null, "You won ! : what a player !",
        //                             "Game win", JOptionPane.WARNING_MESSAGE);
        //                     // resetMineSweeperOnServer();
        //                     gui.reInitField();
        //                 }
        //             }
        //             box.setText("F");
        //         }

        //         else if (isLeftMouseButton(event) && box.getText() != "F") {
        //             typeClicked = "leftClick";
        //             // clickBoxOnServer(x, y, typeClicked);
        //             if(boxType.equals("x")){
        //                 box.setText("X");
        //                 // Code To popup an Game Over message :
        //                 JOptionPane.showMessageDialog(null, "You clicked on a mine : Game Over LOOSER >-<",
        //                         "GAME OVER", JOptionPane.WARNING_MESSAGE);
        //                 // resetMineSweeperOnServer(); 
        //                 gui.reInitField();
        //             }

        //             else if(boxType.equals("0")){
        //                 box.setText(field.getElementFromXY(x, y, true)); 
        //                 box.setBackground(Color.GRAY);
        //                 switch ( Integer.valueOf(box.getText()) ) {
        //                     case 0:
        //                         box.setBackground(Color.GRAY);
        //                         break;
        //                     case 1:
        //                         box.setForeground(Color.BLUE);
        //                         break;
        //                     case 2:
        //                         box.setForeground(Color.GREEN);
        //                         break;
        //                     case 3:
        //                         box.setForeground(Color.RED);
        //                         break;
        //                     case 4:
        //                         box.setForeground(Color.ORANGE);
        //                         break;
        //                     case 5:
        //                         box.setForeground(Color.MAGENTA);
        //                         break;
        //                     case 6:
        //                         box.setForeground(Color.CYAN);
        //                         break;

        //                 }
        //             }
        //         }
        //     }
        // });
            addMouseListener(this);
    }   
    

    public void placeButton(){
        remove(box);
        box.setPreferredSize(new Dimension(getWidth(),getHeight()));
        add(box);
    }

    public void paintComponent(Graphics g){

        if(leftClick){
            super.paintComponent(g);
            setBorder(BorderFactory.createLoweredBevelBorder());
            if(text.equals("x")){
                g.drawImage(bomb.getImage(),0,0, getWidth(), getHeight(), this);
            }
            else{
                g.setColor(Color.lightGray);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setFont(new Font("TimesRoman", Font.BOLD, 14));
                switch (Integer.valueOf(text)) { // Set the Color of the number depending on
                    //                     // its value
                    case 0:
                        g.setColor(Color.GRAY);
                        break;
                    case 1:
                        g.setColor(Color.BLUE);
                        break;
                    case 2:
                        g.setColor(Color.GREEN);
                        break;
                    case 3:
                        g.setColor(Color.RED);
                        break;
                    case 4:
                        g.setColor(Color.ORANGE);
                        break;
                    case 5:
                        g.setColor(Color.MAGENTA);
                        break;
                    case 6:
                        g.setColor(Color.CYAN);
                        break;
                }
                if(text.equals("0")){
                    g.drawString(" ", getWidth()/2, getHeight()/2);
                }
                else{
                    g.drawString(text, -3+(getWidth()+1)/2, 3+(getHeight()+1)/2);
                }
            }
        }
        else if(rightClick){
            super.paintComponent(g);
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, getWidth(), getHeight());
            setBorder(BorderFactory.createRaisedBevelBorder());
            
            if(flagPlaced == 0){
                g.drawImage(flag.getImage(),0,0, getWidth(), getHeight(), this);
                gui.downScore();
                flagPlaced = 1-flagPlaced;
            }
            else{
                gui.upScore();
                flagPlaced = 1-flagPlaced;
            }
            rightClick = false;
        }
        else if(!leftClick && !rightClick){
            super.paintComponent(g);
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, getWidth(), getHeight());
            setBorder(BorderFactory.createRaisedBevelBorder());
        }

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if(isLeftMouseButton(e)) {  // left mouse button
            leftClick = true;
        }
        else if (isRightMouseButton(e)){// right mouse button
            rightClick = true;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // enter = true;
        // repaint();
        // // enter = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // exit = true;
        // repaint();
        // // exit = false;
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // nothing to do here
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // released = true;
        // released = false;
    }

}

