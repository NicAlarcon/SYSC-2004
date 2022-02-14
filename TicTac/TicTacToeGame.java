import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import sun.audio.*;
import java.io.*;

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 * 
 * 
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 03-26-2019
 */

public class TicTacToeGame implements ActionListener
{
    JButton[][] button; // 3X3 array of buttons
    private int numberOfTurns; // # of turns completed
    private int scoreCount; //score of the players
    private JTextField score; // to show the score
    private boolean gameOver; // if winner found sets to true
    private BorderLayout border; // Border layout for the content pane
    private JMenuBar menuList; //drop down menu list
    private JMenuItem resetItem; //reset option from menulist
    private JMenuItem quitItem; //quit option from menulist
    private JLabel label; //feild used to output state of game
    
  /**
   * Automatically creates new instance of TicTacToeGame
   */  
  public static void play()
   {
       new TicTacToeGame(); 
   }
       
  /** 
  * Constructs a new JFrame containing a JPanel, and JMenuBar.
  * Actionlisteners assigned to each button used in the content pane.
  */ 
     private TicTacToeGame() {
     gameOver = false;
     numberOfTurns = 0;
          
     JFrame frame = new JFrame("Tic Tac Toe Game");     
     
     Container content = frame.getContentPane();
     border = new BorderLayout();
     content.setLayout(border);
     
     button = new JButton[3][3];
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(3,3));
     
     menuList = new JMenuBar();
     frame.setJMenuBar(menuList);
     
     // create the 3x3 button layout with action listeners for each button
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
             button[i][j] = new JButton(); // init the button one by one
             button[i][j].setText("");
             button[i][j].setFont(new Font(null, Font.BOLD, 100));
             buttonPanel.add(button[i][j]);
             button[i][j].addActionListener(this);
         }
     }
     content.add(buttonPanel, BorderLayout.CENTER);
     
     JMenu fileMenu = new JMenu("Game");
     fileMenu.setFont(new Font(null, Font.BOLD, 20));
     menuList.add(fileMenu);
     
     resetItem = new JMenuItem("New Game"); 
     resetItem.setFont(new Font(null, Font.BOLD, 20));
     fileMenu.add(resetItem);
      
     quitItem = new JMenuItem("Quit Game");
     quitItem.setFont(new Font(null, Font.BOLD, 20));
     fileMenu.add(quitItem);
     
     
     // this allows us to use shortcuts (e.g. Ctrl-R and Ctrl-Q)
     final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // to save typing
     resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
     quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
     
     // listen for menu selections
     resetItem.addActionListener(this);
     quitItem.addActionListener(this); 
     
     label = new JLabel();
     label.setText("Game has Begun....Player X will start");
     label.setFont(new Font(null, Font.BOLD, 20));
     content.add(label, BorderLayout.SOUTH);
     
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pressing the red x closes the game      
     frame.pack();
     frame.setLocation(600, 200);
     frame.setSize(500, 500);
     frame.setResizable(true);
     frame.setVisible(true);  
 } 
 
 /**
  * Method used to check the each position of the board for a winner
  * @param row of the board
  * @param column of the board
  */ 
 
 private void hasWinner(int row, int col) {
     if(numberOfTurns > 4) {
           if(button[row][0].getText().equals(button[row][1].getText()) &&
              button[row][1].getText().equals(button[row][2].getText())) {
                gameOver = true;
           }
           else if(button[0][col].getText().equals(button[1][col].getText()) &&
                   button[1][col].getText().equals(button[2][col].getText())) {
                gameOver = true;
           }               
           else if(button[0][0].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][2].getText())) {
                gameOver = true;
           }
           else if(button[0][2].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][0].getText())) {
                gameOver = true; 
           }
     }
  }
 
  public void actionPerformed(ActionEvent event)
   {
       Object object = event.getSource(); // find out whether a menu item or a button was clicked
       if(object instanceof JButton) {
           playMusic("click"); //play clicking sound on each turn
           JButton theButton = (JButton) event.getSource();// find out which button has been pressed
           if(numberOfTurns % 2 == 0) { // Player X always starts the games
               //Loops used to search each spot on the board for the button pressed
               for(int i=0; i<3; i++){ //increments through rows                     
                   for(int j=0; j<3; j++) { //increments through columns
                       //loop used for the X player -> checks for winner, tie, or makes a play
                       if(theButton == button[i][j]) {
                           button[i][j].setText("X");
                           button[i][j].setEnabled(false);
                           numberOfTurns++;
                           hasWinner(i, j);                            
                           if(gameOver) {
                               disableButtons();
                               label.setText(" Game Over : X Wins");
                               playMusic("winner");
                           }   
                           else if(numberOfTurns == 9 && !gameOver) {
                               label.setText(" Game Over : Tie");
                               playMusic("tie");
                           }
                           else {
                               label.setText(" Game In Progress : O Player's Turn");
                           }
                       }
                   }
               }         
           }
           else {
               //loop used for the O player -> checks for winner, tie, or makes a play
               for(int i=0; i<3; i++){
                   for(int j=0; j<3; j++) {
                       if(theButton == button[i][j]) {
                           button[i][j].setText("O");
                           button[i][j].setEnabled(false);
                           numberOfTurns++;
                           hasWinner(i, j);             
                           if(gameOver) {
                               disableButtons();
                               label.setText(" Game Over : O Wins");
                               playMusic("winner");
                           }
                           else if(numberOfTurns == 9 && !gameOver) {
                               label.setText(" Game Over : Tie");
                               playMusic("tie");
                           }
                           else {
                               label.setText(" Game In Progress : X Player's Turn");
                           }
                       }
                   }
               }        
           }
       }   
       else { // Find out which menuItem has been clicked      
           JMenuItem element = (JMenuItem) object;
           if(element == resetItem) { // the player chose to reset the game
               clearBoard();
               label.setText(" Game In Progress : X Player's Turn");
           }
           if (element == quitItem) { // the player chose to quit the game
               System.exit(0);            
           }
       }
    }
  
 /**
  * Method used to reset the boards button tiles 
  */
 
 private void clearBoard() {
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
            button[i][j].setEnabled(true);
            numberOfTurns = 0;
            button[i][j].setText("");
            gameOver = false;
        }
    }
 } 
 
 /**
  *  Method used to disable the buttons after a winner is found
  */
 private void disableButtons() {
     for(int i=0;i<3;i++) {
         for(int j=0; j<3; j++) {
            button[i][j].setEnabled(false);
        }
    }
 }
 
 /**
  * This method plays an audio clip to enhance the games "wow factor"
  * @param title the title of the desired audio clip to be played
  */
 private void playMusic(String title)
  {
      AudioPlayer musicPlayer = AudioPlayer.player; //creates audio player
      
      if(title.equals("winner")){
          //plays winner sound -> first makes sure the file exists and is in the same directory as the game
          try{
              AudioStream winnerSound = new AudioStream(new FileInputStream("Winner.wav"));
              musicPlayer.start(winnerSound);
          }catch(IOException error){
              System.out.println(error);
          }
        }
      else if(title.equals("click")){
          //plays tile click sound -> first makes sure the file exists and is in the same directory as the game
          try{
              AudioStream clickSound = new AudioStream(new FileInputStream("Click.wav"));
              musicPlayer.start(clickSound);
          }catch(IOException error){
              System.out.println(error);
          }
        }
      else if(title.equals("tie")){
          //plays game tied sound -> first makes sure the file exists and is in the same directory as the game
          try{
              AudioStream clickSound = new AudioStream(new FileInputStream("Tie.wav"));
              musicPlayer.start(clickSound);
          }catch(IOException error){
              System.out.println(error);
          }
        }
 }
}