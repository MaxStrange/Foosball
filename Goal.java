package fussball;

/**
 *Class to represent the goals on the table of the fussball game.
 * @author Max Strange
 */
public class Goal {
    private final int yTop;//The y value of the top bound of the goal
    private final int yBottom;//The y value of the bottom bound of the goal
    private final int xStart;//The x value of the left-most side of the goal
    private final int xEnd;//The x value of the right-most side of the goal
    
    
    /**
     * Constructor for the Goal class.
     * @param yTop The y value for the top of the goal.
     * @param yBottom The y value for the bottom of the goal.
     * @param xStart The x value for the left of the goal.
     * @param xEnd The x value for the right of the goal.
     */
    public Goal(int yTop, int yBottom, int xStart, int xEnd) {
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.xStart = xStart;
        this.xEnd = xEnd;
    }
    
    public int getTopLeftCornerX() { return this.xStart; }
    public int getTopLeftCornerY() { return this.yTop; }
    public int getWidth() { return this.xEnd - this.xStart; }
    public int getHeight() { return this.yBottom - this.yTop; }
}
