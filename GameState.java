package fussball;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *The state of the game all encapsulated in one location.
 * @author Max Strange
 */
public class GameState {
    private Table table;
    private Ball ball;
    private Team human;
    private Team computer;
    private int humanScore = 0;
    private int computerScore = 0;
    
    /**
     * Constructor.
     */
    public GameState() {
    
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
                startingXHuman, widthInterval, stickOut, Color.BLUE, true);
        this.computer = new Team(topIntersect, bottomIntersect, 
                startingXComp, widthInterval, stickOut, Color.RED, false);
    }
    
    
    public LeftRightWall getLeftWall() { return this.table.getLeftWall(); }
    public LeftRightWall getRightWall() { return this.table.getRightWall(); }
    public TopBottomWall getTopWall() { return this.table.getTopWall(); }
    public TopBottomWall getBottomWall() { return this.table.getBottomWall(); }
    public Goal getLeftGoal() { return this.table.getLeftGoal(); }
    public Goal getRightGoal() { return this.table.getRightGoal(); }
    public Ball getBall() { return this.ball; }
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
    public int getHumanScore() { return this.humanScore; }
    public int getComputerScore() { return this.computerScore; }
}
