import java.util.Random;
import java.util.Scanner;

public class Field {

    /* Field parameters */
    //private final static int NBMINES = 3;
    private final int nbMinesToPlace; // Number of mines to place on the minefield
    private final static int nbMines[] = {3 ,7 ,10, 15}; // Possible mines of the minefield depending on level difficulty
    private final static int DIM = 5;       // Dimension of the minefield
    private final static int dimParam[] = {5 ,7 ,9, 12};  // Possible dimensions of the minefield depending on level difficulty
    private final int dimParameter;

    private boolean [][] field; // Minefield
    private int nbMinesPlaced = 0;

    private Levels level;
    Field(){
        this.dimParameter = 5;
        this.nbMinesToPlace = 3;
    }
    Field(Levels level) {
        // Default constructor for field7
        if(level.ordinal() == 3){       // CUSTOM level = 3

            // The user select the number of mines to place:
            System.out.print("Select the number of mines: ");
            int nbMinesToSelect = setParameter();

            // The user select the dimension of the minefield:
            System.out.print("Select the X dimension of the square field: ");
            int dimParamToSelect = setParameter();

            nbMinesToPlace = nbMinesToSelect;
            dimParameter = dimParamToSelect;
        }
        else{
            nbMinesToPlace = nbMines[level.ordinal()];  // EASY = 0 / MEDIUM = 1 / HARD = 2
            dimParameter = dimParam[level.ordinal()];
        }
    }

    Field(int nbMinesPlaced, int dimParameter){
        this.dimParameter = dimParameter;
        this.nbMinesToPlace = nbMinesPlaced;
    }
    public int setParameter(){
        Scanner sc = new Scanner(System.in);
        int parameter = sc.nextInt();
        return parameter;
    }
    /* Field methods */
    public void initField() {      // Place the mine in the field
        this.field = new boolean[dimParameter][dimParameter];
        Random alea = new Random();
        while(nbMinesPlaced < nbMinesToPlace){     // Check if there is enough available places to place a mine
            int x = alea.nextInt(dimParameter);  // Random generation of place (x,y) on the field
            int y = alea.nextInt(dimParameter);
            if(!this.field[x][y]){      // Check if the position is not currently occupied
                this.field[x][y] = true;
                nbMinesPlaced++;        // increments the number of placed mines
            }
        }
    }

    public void display() {     // Display the entire field of mine
        for (int x =0; x < field.length;x++){
            for(int y = 0; y < field[0].length;y++){    // For loop on the matrix to display all objects
                if (field[x][y])
                    System.out.print('x'); // Display a mine if true
                else
                    System.out.print(computeNbMines(x,y)); // Display an empty case
            }
            System.out.println(); // Skips a line
        }
    }

    /* Level methods */

    int computeNbMines(int x, int y){
        int nb = 0;

        int borneInfX = x==0 ? 0 : x-1;
        int borneInfY = y==0 ? 0 : y-1;

        int borneSupX = x==field.length-1 ? field.length-1 : x+1;
        int borneSupY = y==field[0].length-1 ? field[0].length-1 : y+1;

        for(int i = borneInfX ; i <= borneSupX ; i++){
            for(int j = borneInfY ; j <= borneSupY ; j++){
                if(field[i][j]){
                    nb++;
                }
            }
        }
        return nb;
    }




}