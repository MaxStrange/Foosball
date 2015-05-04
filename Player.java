package fussball;

import java.awt.Graphics;
import java.awt.Point;

/**
 * A class to represent one of the players on the spindles.
 * @author Max Strange
 */
public class Player {
    private final int HEAD_RADIUS = 15;
    private final int LEG_LENGTH = 75;
    private final double OFFSET_Y = 3.0;//The proportion the ball will skew to the up or down when kicked
    private final double MOMENTUM_CHANGE = 5.0;//The change in momentum per arrow click
    private final double MAX_MOMENTUM = 25.0;//The maximum momentum possible
    
    private Point location;//The location of the Player
    private Point previousLocation;//The location the player was one tick ago.
    private Orientation orientation;//The current orientation of the player.
    private double momentum;//Negative means clockwise (curiously)
    private int time = 0;//The number of decays since the last time the momentum was changed by the user/computer

    public Player(Point location) {
        this.location = location;
        this.orientation = Orientation.DOWN;
        this.previousLocation = location;
        this.momentum = 0.0;
    }
    
    
    
    
    
    
    /**
     * Handles the collisions between the Player's feet, head, and the ball.
     * @param ball The game ball.
     */
    public void collide(Ball ball) {
        boolean collidedWithBody = false;
        
        //If the ball overlaps the body and the feet aren't up, there is a collision of type body
        if (overlapBodyAndBall(ball) && this.orientation != Orientation.UP) {
            collideWithBody(ball);
            collidedWithBody = true;
        } 
        
        //If the ball overlaps the feet and the feet aren't up...
        if (overlapFeetAndBall(ball) && this.orientation != Orientation.UP) {
            
            //If the player's momentum is small enough, and a body collision has not already been calculated
            if ((Math.abs(this.momentum) < 2.0) && !collidedWithBody) {
                collideWithBody(ball);
            } else {//Otherwise (the momentum is high enough), collide with the feet (kick the ball)
                ball.accelerateLeftRight(this.momentum);//Kick left/right
                maybeReflectY(ball);//Skew the ball's velocity up/down
            }
            
        }
    }
    
    /**
     * Decays the player's momentum.
     */
    public void decayMomentum() {
        this.time++;
        this.momentum = this.momentum / Math.exp(this.time);
    }    
    
    /**
     * Draw the Player.
     * @param g The Graphics object to draw with.
     */
    public void draw(Graphics g) {
        //Draw head
        if (this.orientation == Orientation.UP)
            g.drawOval(this.location.x - HEAD_RADIUS, this.location.y - HEAD_RADIUS, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
        else
            g.fillOval(this.location.x - HEAD_RADIUS, this.location.y - HEAD_RADIUS, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
        
        //Draw legs
        Point hipTop = new Point(this.location.x, this.location.y - HEAD_RADIUS);
        Point hipBottom = new Point(this.location.x, this.location.y + HEAD_RADIUS);
        
        Point foot = getFootLocation();
        
        if (this.orientation == Orientation.UP) {
            //Don't paint the foot
        } else {
            g.drawLine(foot.x, foot.y, hipTop.x, hipTop.y);
            g.drawLine(foot.x, foot.y, hipBottom.x, hipBottom.y);   
        }
    }
    
    /**
     * Moves the player the given distance up or down.
     * @param up If true, moves the player up. Otherwise moves the player down.
     * @param distance The distance the player moves.
     */
    public void move(boolean up, int distance) {
        if (up) {
            this.location = new Point(this.location.x, this.location.y - distance);
        } else {
            this.location = new Point(this.location.x, this.location.y + distance);
        }
    }
    
    /**
     * Rotates the player clockwise or counter clockwise (changing its
     * orientation).
     * @param clockwise If true, rotates clockwise. Otherwise, rotates counter
     * clockwise.
     */
    public void rotate(boolean clockwise) {
        switch (this.orientation) {
            case UP:
                this.orientation = clockwise ? Orientation.RIGHT : Orientation.LEFT;
                break;
            case DOWN:
                this.orientation = clockwise ? Orientation.LEFT : Orientation.RIGHT;
                break;
            case LEFT:
                this.orientation = clockwise ? Orientation.UP : Orientation.DOWN;
                break;
            case RIGHT:
                this.orientation = clockwise ? Orientation.DOWN : Orientation.UP;
                break;
        }
        increaseMomentum(clockwise);
    }
    
    
    
    
    
    
    /**
     * Gets the player's foot's location. If the player's orientation is UP,
     * this will return (-1, -1), so make sure to check the orientation of the
     * player before using this value.
     * @return The location of the player's foot, or (-1, -1) if the foot is
     * in the air (orientation is UP).
     */
    public Point getFootLocation() {
        return convertLocationToFootLocation(this.location, this.orientation);
    }
    /**
     * Gets the player's location (the center of the player's head).
     * @return A Point representing the player's head's location.
     */
    public Point getLocation() { return this.location; }
    public Point getPreviousLocation() { return this.previousLocation; }
    public Orientation getOrientation() { return this.orientation; }
    
    
    
    
    /**
     * Accelerates the ball due to the movement up or down of the spindle.
     * @param ball The game's ball
     */
    private void accelerateBallDueToMovement(Ball ball) {
        if (ball.getLocation().y > this.previousLocation.y)//ball is below player, accelerate it up
            ball.accelerateUpDown(true);
        else
            ball.accelerateUpDown(false);
    }    
    
    /**
     * Maybe reflect the ball's y velocity, depending on the ball and player's
     * relative position.
     * @param ball The ball
     */
    private void maybeReflectY(Ball ball) {
        Point body = getLocation();
        Point ballLoc = ball.getLocation();
        
        //Adjust the y velocity proportionally to the difference between the y locations
        ball.accelerateUpDown((ballLoc.y - getLocation().y) * OFFSET_Y);
    }
    
    /**
     * Checks for and returns whether or not the player's feet overlap with the
     * ball.
     * @param ball The game ball.
     * @return Whether or not the player's body overlaps with the ball.
     */
    private boolean overlapBodyAndBall(Ball ball) {
        Point body = getLocation();
        Point ballLoc = ball.getLocation();
        
        boolean insideXRange;
        if (body.x > ballLoc.x) {//Ball is to the left of the body
            insideXRange = ((ballLoc.x + ball.getRadius()) >= (body.x - this.HEAD_RADIUS));
        } else {//Ball is to the right of the body
            insideXRange = ((ballLoc.x - ball.getRadius()) <= (body.x + this.HEAD_RADIUS));
        }
        
        boolean insideYRange;
        if (body.y > ballLoc.y) {//Ball is above the body
            insideYRange = ((ballLoc.y + ball.getRadius()) >= (body.y - this.HEAD_RADIUS));
        } else {//Ball is below the body
            insideYRange = ((ballLoc.y - ball.getRadius()) <= (body.y + this.HEAD_RADIUS));
        }
        
        return insideXRange && insideYRange;
    }
    
    /**
     * Checks for and returns whether or not the player's feet overlap with the
     * ball.
     * @param ball The game ball.
     * @return Whether or not the player's feet overlap with the ball.
     */
    private boolean overlapFeetAndBall(Ball ball) {
        if (this.orientation == Orientation.UP)
            return false;//Can't collide with feet that are in the air!

        //Check for overlap
        Point foot = getFootLocation();
        Point body = getLocation();
        Point ballLoc = ball.getLocation();
        
        boolean insideXRange;
        if (body.x > ballLoc.x) {//Ball is to the left of the body
            insideXRange = ((ballLoc.x + ball.getRadius() ) >= foot.x);
        } else {//Ball is to the right of the body
            insideXRange = ((ballLoc.x - ball.getRadius()) <= foot.x);
        }
        
        boolean insideYRange;
        if (body.y > ballLoc.y) {//Ball is above the body
            insideYRange = ((ballLoc.y + ball.getRadius()) >= (foot.y - (HEAD_RADIUS / 2.0)));
        } else {//Ball below the body
            insideYRange = ((ballLoc.y - ball.getRadius()) <= (foot.y + (HEAD_RADIUS / 2.0)));
        }
        
        return insideXRange && insideYRange;
    }

    /**
     * Collide with body.
     * @param ball the ball.
     */
    private void collideWithBody(Ball ball) {
        //Reflect up/down left/right depending on relative position of centers
        if (ball.getLocation().x > this.location.x)
            ball.reflectLeftRight(false);//RIGHT
        else if (ball.getLocation().x < this.location.x)
            ball.reflectLeftRight(true);//LEFT

        if (ball.getLocation().y > this.location.y)
            ball.reflectUpDown(true);//UP
        else if (ball.getLocation().y < this.location.y)
            ball.reflectUpDown(false);//DOWN
    }
    
    /**
     * Gets the location of the foot of the player, given loc.
     * @param loc The location to use in finding the foot location
     * @param ori The orientation to use in finding the foot location
     * @return The location of the foot when the player is at the given location
     * and orientation.
     */
    private Point convertLocationToFootLocation(Point loc, Orientation ori) {
        int footX;
        switch (ori) {
            case DOWN:
                footX = loc.x;
                break;
            case UP:
                return new Point(-1, -1);
            case LEFT:
                footX = loc.x - LEG_LENGTH;
                break;
            case RIGHT:
                footX = loc.x + LEG_LENGTH;
                break;
            default:
                throw new IllegalStateException("Somehow the Orientation of the "
                        + "player is not possible.");
        }
        
        Point foot = new Point(footX, loc.y);
        return foot;
    }
    
    /**
     * Changes the momentum to be more clockwise or more counter clockwise, 
     * depending on the given argument.
     * @param clockwise 
     */
    private void increaseMomentum(boolean clockwise) {
        //Reset the time
        this.time = 0;
        
        if (this.momentum < 0.0) {//Momentum is currently clockwise
            
            //change it to be more clockwise, or else change it to be barely counter clockwise
            this.momentum = clockwise ? this.momentum - this.MOMENTUM_CHANGE : this.MOMENTUM_CHANGE;
            
            //Make sure the momentum doesn't go beyond the max
            this.momentum = (this.momentum < ((-1.0) * this.MAX_MOMENTUM)) ? (-1.0) * this.MAX_MOMENTUM : this.momentum;
            
        } else if (this.momentum > 0.0) {//Momentum is currently counter clockwise
            
            //Change it to be more counter clockwise, or else change it to be barely clockwise
            this.momentum = clockwise ? (-1.0) * this.MOMENTUM_CHANGE : this.momentum + this.MOMENTUM_CHANGE;
            
            //Make sure the momentum doesn't go beyond the max
            this.momentum = (this.momentum > this.MAX_MOMENTUM) ? this.MAX_MOMENTUM : this.momentum;
            
        } else {//Momentum is currently 0.
            
            //Set it to small amount above or below 0.0
            this.momentum = clockwise ? (-1.0) * this.MOMENTUM_CHANGE : this.MOMENTUM_CHANGE;
            
        }
    }
}
