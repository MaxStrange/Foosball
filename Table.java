package fussball;

import java.awt.Point;
import javax.swing.JPanel;

/**
 *A class to represent the table that the game is played on.
 * @author Max Strange
 */
public class Table {
    private LeftRightWall leftWall;
    private LeftRightWall rightWall;
    
    private TopBottomWall topWall;
    private TopBottomWall bottomWall;
    
    private Goal leftGoal;
    private Goal rightGoal;
    
    /**
     * Constructor for the table.
     * @param parent The JPanel that the table will be drawn on.
     */
    public Table(JPanel parent) {
        //Initialize the walls
        double boarderSizeX = parent.getWidth() / 10;
        double xStart = boarderSizeX;
        double xWidth = parent.getWidth() - (2 * boarderSizeX);
        double boarderSizeY = parent.getHeight() / 10;
        double yStart = boarderSizeY;
        double yWidth = parent.getHeight() - (2 * boarderSizeY);
        
        this.leftWall = new LeftRightWall((int)xStart, (int)yStart, (int)yWidth);
        this.rightWall = new LeftRightWall((int)(xStart + xWidth), (int)yStart, (int)yWidth);
        
        this.topWall = new TopBottomWall((int)yStart, (int)xStart, (int)xWidth);
        this.bottomWall = new TopBottomWall((int)(yStart + yWidth), (int)xStart, (int)xWidth);
        
        //Initialize the goals
        double goalLXStart = xStart - (boarderSizeX / 2);
        double goalLYStart = yStart + (yWidth / 3.0);//a third of the way down the box
        
        double goalLWidth = boarderSizeX / 2;
        double goalLHeight = yWidth / 3.0;
        
        double goalRXStart = xStart + xWidth;
        double goalRYStart = goalLYStart;
        
        double goalRWidth = goalLWidth;
        double goalRHeight = goalLHeight;        
        
        this.leftGoal = new Goal((int)goalLYStart, (int)(goalLYStart + goalLHeight), (int)goalLXStart, (int)(goalLXStart + goalLWidth));
        this.rightGoal = new Goal((int)goalRYStart, (int)(goalRYStart + goalRHeight), (int)goalRXStart, (int)(goalRXStart + goalRWidth));
    }
    
    /**
     * Gets the left wall of the table.
     * @return The left wall
     */
    public LeftRightWall getLeftWall() { return this.leftWall; }
    /**
     * Gets the right wall of the table
     * @return The right wall
     */
    public LeftRightWall getRightWall() { return this.rightWall; }
    /**
     * Gets the top wall of the table
     * @return The top wall
     */
    public TopBottomWall getTopWall() { return this.topWall; }
    /**
     * Gets the bottom wall of the table
     * @return The bottom wall
     */
    public TopBottomWall getBottomWall() { return this.bottomWall; }
    /**
     * Gets the left goal of the table
     * @return The left goal
     */
    public Goal getLeftGoal() { return this.leftGoal; }
    /**
     * Gets the right goal of the table
     * @return The right goal
     */
    public Goal getRightGoal() { return this.rightGoal; }
    
    /**
     * Gets the center of the table and returns it as a Point object.
     * @return The Point that represents the center of the table.
     */
    public Point getCenter() {
        int tableLeftX = this.getLeftWall().getX();
        int tableWidth = this.getRightWall().getX() - tableLeftX;
        int tableTopY = this.getTopWall().getY();
        int tableHeight = this.getBottomWall().getY() - tableTopY;
        
        int x = (int)((tableWidth / 2.0) + 0.5) + tableLeftX;
        int y = (int)((tableHeight / 2.0) + 0.5) + tableTopY;
        
        
        return new Point(x, y);
    }
}