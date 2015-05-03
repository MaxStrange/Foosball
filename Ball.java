package fussball;

import java.awt.Point;
import java.util.Random;

/**
 * A class to represent the Ball that the game uses.
 * @author Max Strange
 */
public class Ball {
    private final Point startingLocation;
    private final int radius = 10;
    private final double accXMag = 25;
    private final double accYMag = 25;
    /**
     * The value to be multiplied against the velocity every tick to simulate
     * friction on the ball.
     */
    private final double FRIC_FRAC = 0.9;
    
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
        this.startingLocation = new Point(xLocation, yLocation);
        
        resetVelocity();
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
     * Accelerates the ball to the left or right proportional to the proportion
     * given.
     * @param proportion The proportion to multiply by. If it is negative, it
     * will accelerate the ball left; if positive, it will accelerate the ball
     * right.
     */
    public void accelerateLeftRight(double proportion) {
        double velX = this.velocity.getXComponent() + ((proportion / 2.0) * this.accXMag);
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
    
    /**
     * Accelerates the ball up or down proportional to the proportion given.
     * @param proportion The proportion to multiply by. If it is negative, it
     * will accelerate the ball upwards; if positive, it will accelerate the
     * ball downwards.
     */
    public void accelerateUpDown(double proportion) {
        double velY = this.velocity.getYComponent() + proportion;
        this.velocity = new Vector(this.velocity.getXComponent(), velY);
    }
    
    /**
     * Moves the ball. Checks for collision with walls and responds appropriately.
     * @param table The table the ball is on
     */
    public void move(Table table) {
        adjustLocationBlind();
        adjustLocationBasedOnBoundaries(table);
        bounceOffTableWalls(table);
        applyFriction();
    }
    
    /**
     * Reflects the ball TO THE left or right.
     * @param left To the left if true, otherwise to the right.
     */
    public void reflectLeftRight(boolean left) {
        double velY = this.velocity.getYComponent();
        double velX = left ? Math.abs(this.velocity.getXComponent()) * (-1.0) : Math.abs(this.velocity.getXComponent());
        this.velocity = new Vector(velX, velY);
    }
    
    /**
     * Reflects the ball TO THE up or down.
     * @param up To the up if true, otherwise to the down.
     */
    public void reflectUpDown(boolean up) {
        double velX = this.velocity.getXComponent();
        double velY = up ? Math.abs(this.velocity.getYComponent()) : Math.abs(this.velocity.getYComponent()) * (-1.0);
        this.velocity = new Vector(velX, velY);
    }
    
    /**
     * Resets the ball.
     */
    public void reset() {
        resetVelocity();
        this.xLoc = (int)(this.startingLocation.getX() + 0.5);
        this.yLoc = (int)(this.startingLocation.getY() + 0.5);
    }
    
    /**
     * Gets the radius of the ball.
     * @return the radius of the ball as a double.
     */
    public int getRadius() { return this.radius; }
    public Point getLocation() { return new Point(this.xLoc, this.yLoc); }
    
    /**
     * Adjusts the location so that the ball does not leave the boundaries of 
     * the table.
     * @param table 
     */
    private void adjustLocationBasedOnBoundaries(Table table) {
        TopBottomWall top = table.getTopWall();
        TopBottomWall bottom = table.getBottomWall();
        LeftRightWall left = table.getLeftWall();
        LeftRightWall right = table.getRightWall();
        if (this.yLoc <= top.getY())
            this.yLoc = top.getY() + this.radius;
        
        if (this.yLoc >= bottom.getY())
            this.yLoc = bottom.getY() - this.radius;
        
        if (this.xLoc <= left.getX())
            this.xLoc = left.getX() + this.radius;
        
        if (this.xLoc >= right.getX())
            this.xLoc = right.getX() - this.radius;
    }
    
    /**
     * Adjusts the location of the ball blindly (that is, without worrying about
     * collisions or boundaries)
     */
    private void adjustLocationBlind() {
        int prevX = (int)(this.xLoc + 0.5);
        int prevY = (int)(this.yLoc + 0.5);
        this.xLoc += this.velocity.getXComponent();
        this.yLoc += this.velocity.getYComponent();
        int afterX = (int)(this.xLoc + 0.5);
        int afterY = (int)(this.yLoc + 0.5);
    }
    
    /**
     * Applies friction to the ball's velocity.
     */
    private void applyFriction() {
        double fractionX = this.velocity.getXComponent() * FRIC_FRAC;
        double fractionY = this.velocity.getYComponent() * FRIC_FRAC;
        this.velocity = new Vector(fractionX, fractionY);
    }
    
    /**
     * Bounce off the table's walls.
     * @param table The table object used in the game.
     */
    private void bounceOffTableWalls(Table table) {
        TopBottomWall top = table.getTopWall();
        TopBottomWall bottom = table.getBottomWall();
        LeftRightWall left = table.getLeftWall();
        LeftRightWall right = table.getRightWall();
        
        if ((this.yLoc - this.radius) <= top.getY())//Collision with top wall
            reflect(false);
        else if ((this.yLoc + this.radius) >= bottom.getY())//Collision with bottom wall
            reflect(false);
        else if ((this.xLoc - this.radius) <= left.getX())//Collision with left wall
            reflect(true);
        else if ((this.xLoc + this.radius) >= right.getX())//Collision with right wall
            reflect(true);
    }
    
    /**
     * Reflects the ball's trajectory depending on whether or not it is bouncing
     * off the left, right, up, or down wall.
     * @param leftOrRight True if the wall it is bouncing off of is the left or
     * right. False for top or bottom.
     */
    private void reflect(boolean leftOrRight) {
        if (leftOrRight)
            this.velocity = new Vector(this.velocity.getXComponent() * (-1.0), this.velocity.getYComponent());
        else
            this.velocity = new Vector(this.velocity.getXComponent(), this.velocity.getYComponent() * (-1.0));
    }
    
    /**
     * Resets the velocity to a random one up to some upper limit.
     */
    private void resetVelocity() {
        Random r = new Random();
        boolean negX = r.nextBoolean();
        boolean negY = r.nextBoolean();
        int upperLim = 30;
        int vx = negX ? r.nextInt(upperLim) * (-1) : r.nextInt(upperLim);
        int vy = negY ? r.nextInt(upperLim) * (-1) : r.nextInt(upperLim);
        this.velocity = new Vector(vx, vy);
    }
}
