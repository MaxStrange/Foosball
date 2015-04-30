package fussball;

/**
 * A class to represent the Ball that the game uses.
 * @author Max Strange
 */
public class Ball {
    private final int radius = 10;
    
    private int xLoc;
    private int yLoc;
    
    /**
     * Constructor for the Ball.
     * @param xLocation The starting x location for the ball.
     * @param yLocation The starting y location for the ball.
     */
    public Ball(int xLocation, int yLocation) {
        this.xLoc = xLocation;
        this.yLoc = yLocation;
    }
    
    /**
     * Gets the radius of the ball.
     * @return the radius of the ball as a double.
     */
    public int getRadius() { return this.radius; }
    
    /**
     * Gets the ball's current x location.
     * @return The x location as an int.
     */
    public int getXLocation() { return this.xLoc; }
    
    /**
     * Sets the ball's x location.
     * @param x The new x location.
     */
    public void setXLocation(int x) { this.xLoc = x; }
    
    /**
     * Gets the ball's current y location.
     * @return The y location as an int.
     */
    public int getYLocation() { return this.yLoc; }
    
    /**
     * Sets the ball's y location.
     * @param y The new y location.
     */
    public void setYLocation(int y) { this.yLoc = y; }
}
