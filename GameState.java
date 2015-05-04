package fussball;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *The state of the game all encapsulated in one location.
 * @author Max Strange
 */
public class GameState {
    private final Color HUMAN_COLOR = Color.BLUE;//The human player's color
    private final Color COMPUTER_COLOR = Color.RED;//The computer player's color
    private final int SCORE_TO_PLAY_TO = 6;//The score to play to
    
    private Table table;
    private Ball ball;
    private Team human;
    private Team computer;
    private int humanScore = 0;
    private int computerScore = 0;
    private long startTime;
    
    /**
     * Constructor.
     */
    public GameState() {
    
    }
    
    
    /**
     * Returns whether or not the game is over.
     * @return Whether or not the game is over.
     */
    public boolean gameIsOver() {
        boolean humanWins = getHumanScore() >= SCORE_TO_PLAY_TO;
        boolean computerWins = getComputerScore() >= SCORE_TO_PLAY_TO;
        
        return humanWins || computerWins;
    }
    
    /**
     * Initializes the GameState.
     * @param tableGUI The JPanel that the table will be drawn on.
     */
    public void initialize(JPanel tableGUI) {
        this.table = new Table(tableGUI);
        this.ball = new Ball(this.table.getCenter().x, this.table.getCenter().y);
        
        double width = (double)(getRightWall().getX() - getLeftWall().getX());
        
        int topIntersect = getTopWall().getY();
        int bottomIntersect = getBottomWall().getY();
        
        int widthInterval = (int)((width / 2.85) + 0.5);
        int smallAmount = (int)(((double)widthInterval / 6.0) + 0.5);
        int startingXComp = getLeftWall().getX() + smallAmount;
        int halfWidthInterval = (int)((widthInterval / 2.0) + 0.5);
        int startingXHuman = startingXComp + halfWidthInterval;
        int stickOut = getTopWall().getY() / 4;
        
        this.human = new Team(topIntersect, bottomIntersect, 
                startingXHuman, widthInterval, stickOut, HUMAN_COLOR, true);
        this.computer = new Team(topIntersect, bottomIntersect, 
                startingXComp, widthInterval, stickOut, COMPUTER_COLOR, false);
        
        this.startTime = System.nanoTime();
    }
    
    /**
     * Moves all spindles currently selected by the user up or down.
     * @param up If true, move up; otherwise move down.
     */
    public void moveSelectedSpindles(boolean up) {
        this.human.moveSelectedSpindles(up);
    }
    
    /**
     * Rotates all spindles currently selected by the user to the left or right.
     * @param left If true, rotates clockwise; otherwise, rotates counter
     * clockwise.
     */
    public void rotateSelectedSpindles(boolean left) {
        this.human.rotateSelectedSpindles(left);
    }
    
    /**
     * Scores a point for the given player. Also lights up the goal to let
     * the user know.
     * @param player The player who scored (or nobody).
     */
    public void score(Score player) {
        switch (player) {
            case HUMAN:
                this.humanScore++;
                getLeftGoal().lightUp(true);
                break;
            case COMPUTER:
                this.computerScore++;
                getRightGoal().lightUp(true);
                break;
        }
    }
    
    /**
     * Toggles the given spindle (if the key corresponds to one of the spindle's
     * keys, otherwise, does nothing).
     * @param keyCode The ASCII code for the key that was pressed.
     */
    public void toggleSpindleSelect(int keyCode) {
        //Try each spindle to see if it toggles that spindle's selection
        for (Spindle s : getHumanSpindles()) {
            s.toggleSelect(keyCode);
        }
    }
    
    /**
     * Gets the winner (if someone has won - otherwise returns NOBODY).
     * @return The winner, or NOBODY, in the case that nobody has won yet.
     */
    public Score getWinner() {
        if (gameIsOver())
            return (getHumanScore() > getComputerScore()) ? Score.HUMAN : Score.COMPUTER;
        else
            return Score.NOBODY;
    }
    public LeftRightWall getLeftWall() { return this.table.getLeftWall(); }
    public LeftRightWall getRightWall() { return this.table.getRightWall(); }
    public TopBottomWall getTopWall() { return this.table.getTopWall(); }
    public TopBottomWall getBottomWall() { return this.table.getBottomWall(); }
    public Goal getLeftGoal() { return this.table.getLeftGoal(); }
    public Goal getRightGoal() { return this.table.getRightGoal(); }
    public Ball getBall() { return this.ball; }
    public Spindle[] getComputerSpindles() { return this.computer.getSpindles(); }
    public Spindle[] getHumanSpindles() { return this.human.getSpindles(); }
    public Spindle[] getAllSpindles() {
        Spindle[] all = new Spindle[this.human.getSpindles().length + this.computer.getSpindles().length];
        for (int i = 0; i < all.length; i++) {
            if (i < this.human.getSpindles().length)
                all[i] = this.human.getSpindles()[i];
            else
                all[i] = this.computer.getSpindles()[i - this.human.getSpindles().length];
        }
        return all;
    }
    public Player[] getAllPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        
        for (Spindle s : getAllSpindles()) {
            Player[] players = s.getPlayers();
            
            for (Player p : players) {
                allPlayers.add(p);
            }
        }
        
        Player[] allPlayersAsArray = new Player[allPlayers.size()];
        return allPlayers.toArray(allPlayersAsArray);
    }
    public int getHumanScore() { return this.humanScore; }
    public int getComputerScore() { return this.computerScore; }
    public Table getTable() { return this.table; }
    public Color getHumanColor() { return this.HUMAN_COLOR; }
    public Color getComputerColor() { return this.COMPUTER_COLOR; }
    /**
     * Returns the number of seconds elapsed since the start of the game.
     * @return The number of seconds elapsed since the start of the game.
     */
    public double getElapsedTime() { 
        long elapsedTime = System.nanoTime() - this.startTime;
        double seconds = (double)elapsedTime / 1000000000.0;
        
        return seconds;
    }
}
