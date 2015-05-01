package fussball;

import java.awt.Point;

/**
 * A class to represent the Ball that the game uses.
 * @author Max Strange
 */
public class Ball {
    private final int radius = 10;
    private final double accXMag = 5;
    private final double accYMag = 5;
    
    private int xLoc;
    private int yLoc;
    private Vector velocity;
    
    
    /**
     * Constructor for the Ball.
     * @param xLocation The starting x location for the ball.
     * @param yLocation The starting y location for the ball.
     */
    public Ball(int xLocation, int yLocation) {
        this.xLoc = xLocation;
        this.yLoc = yLocation;
        
        this.velocity = new Vector(0, 0);
    }
    
    
    /**
     * Accelerates the ball to the left or right.
     * @param left If true, accelerates the ball to the left. Otherwise, to the
     * right.
     */
    public void accelerateLeftRight(boolean left) {
        double velX = left ? this.velocity.getXComponent() - this.accXMag : 
                this.velocity.getXComponent() + this.accXMag;
        this.velocity = new Vector(velX, this.velocity.getYComponent());
    }
    
    /**
     * Accelerates the ball up or down.
     * @param up If true, accelerates the ball up. Otherwise, down.
     */
    public void accelerateUpDown(boolean up) {
        double velY = up ? this.velocity.getYComponent() - this.accYMag :
                this.velocity.getYComponent() + this.accYMag;
        this.velocity = new Vector(this.velocity.getXComponent(), velY);
    }
    
    public void move() {
        this.xLoc += this.velocity.getXComponent();
        this.yLoc += this.velocity.getYComponent();
        
        //Apply friction
        double fractionX = this.velocity.getXComponent() * 0.9;
        double fractionY = this.velocity.getYComponent() * 0.9;
        this.velocity = new Vector(fractionX, fractionY);
    }
    
    /**
     * Gets the radius of the ball.
     * @return the radius of the ball as a double.
     */
    public int getRadius() { return this.radius; }
    public Point getLocation() { return new Point(this.xLoc, this.yLoc); }
}
