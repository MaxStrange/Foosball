package fussball;

/**
 *Top or bottom wall.
 * @author Max Strange
 */
public class TopBottomWall {
    private final int y;
    private final int left;
    private final int length;
    
    /**
     * Constructor for a top or bottom wall.
     * @param y The y value for the wall.
     * @param left The x value of the left end of the wall.
     * @param length The length of the wall.
     */
    public TopBottomWall(int y, int left, int length) {
        this.y = y;
        this.left = left;
        this.length = length;
    }
    
    
    
    /**
     * Gets the y value for the wall.
     * @return The y value for the wall.
     */
    public int getY() { return this.y; }
    /**
     * Gets the x value for the left end of the wall.
     * @return 
     */
    public int getLeft() { return this.left; }
    /**
     * Gets the length of the wall.
     * @return The length of the wall
     */
    public int getLength() { return this.length; }
    /**
     * Gets the x value for the right end of the wall.
     * @return The x value for the right end of the wall.
     */
    public int getRight() { return this.left + this.length; }
}




