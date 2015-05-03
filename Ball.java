package fussball;

import java.awt.Point;
import java.util.Random;

/**
 * A class to represent the Ball that the game uses.
 * @author Max Strange
 */
public class Ball {
    private final int RADIUS = 10;//The ball's radius
    private final double ACC_X_MAG = 25;//The magnitude of the ball's acceleration (when it accelerates) in the X
    private final double ACC_Y_MAG = 25;//The magnitude of the ball's acceleration (when it accelerates) in the Y
    /**
     * The value to be multiplied against the velocity every tick to simulate
     * friction on the ball.
     */
    private final double FRIC_FRAC = 0.9;
    private final int DELAY = 10;//The number of ticks to delay before resetting the ball after scoring
    private final Point startingLocation;//The Location the ball goes back to every time it resets
        
    private int xLoc;//The current x location of the ball
    private int yLoc;//The current y location of the ball
    private Vector velocity;//The current velocity of the ball
    private boolean offTable = false;//Whether the ball is currently off the table (waiting to be reset after a goal)
    private int reappearTimer = 0;//The number of elapsed ticks since the ball disappeared after a goal.
    
    
    
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
        double velX = left ? this.velocity.getXComponent() - this.ACC_X_MAG : 
                this.velocity.getXComponent() + this.ACC_X_MAG;
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
        double velX = this.velocity.getXComponent() + ((proportion / 2.0) * this.ACC_X_MAG);
        this.velocity = new Vector(velX, this.velocity.getYComponent());
    }
    
    /**
     * Accelerates the ball up or down.
     * @param up If true, accelerates the ball up. Otherwise, down.
     */
    public void accelerateUpDown(boolean up) {
        double velY = up ? this.velocity.getYComponent() - this.ACC_Y_MAG :
                this.velocity.getYComponent() + this.ACC_Y_MAG;
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
     * @return The player who scored (if anyone).
     */
    public Score move(Table table) {
        Score retr;
        
        //Check if the ball is off the table and maybe reset it (if it has been long enough off the table)
        if (this.offTable) {
            this.reappearTimer++;
            if (this.reappearTimer > DELAY) // Reset the ball (it has been gone long enough), then move it
                reset();
            else    //If the ball is off the table, just leave it alone - don't move it, just return.
                return Score.NOBODY;
        }
        
        adjustLocationBlind();
        adjustLocationBasedOnBoundaries(table);
        retr = bounceOffTableWalls(table);
        applyFriction();
        
        return retr;
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
        
        this.reappearTimer = 0;//reset the reappearTimer
        this.offTable = false;//No longer off the table.
    }
    
    
    
    /**
     * Gets the RADIUS of the ball.
     * @return the RADIUS of the ball as a double.
     */
    public int getRadius() { return this.RADIUS; }
    public Point getLocation() { return new Point(this.xLoc, this.yLoc); }
    public boolean isOffTable() { return this.offTable; }
    
    
    
    
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
            this.yLoc = top.getY() + this.RADIUS;
        
        if (this.yLoc >= bottom.getY())
            this.yLoc = bottom.getY() - this.RADIUS;
        
        if (this.xLoc <= left.getX())
            this.xLoc = left.getX() + this.RADIUS;
        
        if (this.xLoc >= right.getX())
            this.xLoc = right.getX() - this.RADIUS;
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
     * Bounce off the table's walls if it hits one. Checks if it is a score.
     * If so, disappears the ball so that it can be reset at a later time.
     * @param table The table object used in the game.
     * @return The player who scored (if anyone).
     */
    private Score bounceOffTableWalls(Table table) {
        TopBottomWall top = table.getTopWall();
        TopBottomWall bottom = table.getBottomWall();
        LeftRightWall left = table.getLeftWall();
        LeftRightWall right = table.getRightWall();
        
        if ((this.yLoc - this.RADIUS) <= top.getY()) {//Collision with top wall
            reflect(false);
        } else if ((this.yLoc + this.RADIUS) >= bottom.getY()) {//Collision with bottom wall
            reflect(false);
        } else if ((this.xLoc - this.RADIUS) <= left.getX()) {//Collision with left wall
            reflect(true);
            
            boolean belowTopOfGoal = ((this.yLoc - this.RADIUS) >= table.getLeftGoal().getTopLeftCornerY());
            boolean aboveBottomOfGoal = ((this.yLoc + this.RADIUS) <= table.getLeftGoal().getBottomLeftCornerY());
            
            if (belowTopOfGoal && aboveBottomOfGoal) {
                disappear();
                return Score.HUMAN;//The human scored on the computer's goal
            }
            
        } else if ((this.xLoc + this.RADIUS) >= right.getX()) {//Collision with right wall
            reflect(true);
            
            boolean belowTopOfGoal = ((this.yLoc - this.RADIUS) >= table.getRightGoal().getTopLeftCornerY());
            boolean aboveBottomOfGoal = ((this.yLoc + this.RADIUS) <= table.getRightGoal().getBottomLeftCornerY());
            
            if (belowTopOfGoal && aboveBottomOfGoal) {
                disappear();
                return Score.COMPUTER;
            }
        }
        
        return Score.NOBODY;
    }
    
    /**
     * Disappears the ball and initializes a counter to keep track of how long
     * it has been gone.
     */
    private void disappear() {
        this.offTable = true;
        this.reappearTimer = 0;
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
