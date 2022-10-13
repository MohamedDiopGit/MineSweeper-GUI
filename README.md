# MineSweeper-GUI
<h2>School Project: Minesweeper GUI project done in Java school courses</h2>

This is a Minesweeper Game with a Graphic Interface. It has 4 mode : EASY, MEDIUM, HARD, and Custom.  
The first 3 modes are all set with their specific configurations, and you can select them in the menu bar, by clicking on "Difficulty"  
Then you have "CUSTOM" Mode, where you specify the dimension or the grid and the number of mines to place on it.   You can also play online by launching the server.

The "main.java" uses directly "GUI.java" which uses both "Levels.java" (enum types of four levels) and "Field.java"  
  
 <h2>GUI.java</h2> 
 The main component that runs the Graphic interface, and manages the front and back-end process to call entities and specific functions in order to display the information. It allows to display the grid, the menubar and the popus to give and collect data correctly.    


<h2>Field.java</h2>
The backend main component that create a grid for the minesweeper, the file makes the initialization of each boxes, and has getters to send data to the GUI.  
   
<h2>Levels.java</h2>
The four levels as an enum  

<h2>Main.java</h2>  
The running program and the start of the game with an easy mode by default.  


Enjoy.  
Developped with IntelliJ IDEA Community 2022.2.2  
