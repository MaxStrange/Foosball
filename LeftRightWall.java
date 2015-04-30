package fussball;

/**
 *Left or Right wall.
 * @author Max Strange
 */
public class LeftRightWall{
    private final int x;
    private final int top;
    private final int length;
    
    /**
     * Constructor for a Left or Right wall.
     * @param x The x coordinate of the wall.
     * @param top The y coordinate of the wall.
     * @param length The length of the wall.
     */
    public LeftRightWall(int x, int top, int length) {
        this.x = x;
        this.top = top;
        this.length = length;
    }
    
    
    /**
     * Gets the x coordinate of the wall.
     * @return The x coordinate of the wall.
     */
    public int getX() { return this.x; }
    /**
     * Gets the y value for the top of the wall.
     * @return The y value for the top of the wall.
     */
    public int getTop() { return this.top; }
    /**
     * Gets the length of the wall.
     * @return The length of the wall.
     */
    public int getLength() { return this.length; }
    /**
     * Gets the y value for the bottom of the wall.
     * @return The y value for the bottom of the wall.
     */
    public int getBottom() { return this.top + this.length; }
}
