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
    
    private Point location;//The location of the Player
    private Point previousLocation;//The location the player was one tick ago.
    private Orientation orientation;//The current orientation of the player.
    private Orientation previousOrientation;//The orientation the Player was one tick ago.

    public Player(Point location) {
        this.location = location;
        this.orientation = Orientation.DOWN;
        this.previousLocation = location;
        this.previousOrientation = Orientation.DOWN;
    }
    
    /**
     * Handles the collisions between the Player's feet, head, and the ball.
     * @param ball The game ball.
     */
    public void collide(Ball ball) {
        /*
        If the collision is with the FEET, check the player's previous orientation
        to see which way the ball should accelerate.
        If the collision is with the player's body, check he player's previous
        location to see which way the ball should accelerate.
        */
        collideWithFeet(ball);
        collideWithBody(ball);
    }
    
    /**
     * Checks for a collision between the feet and the ball.
     * @param ball The game's ball
     */
    private void collideWithFeet(Ball ball) {
        if (overlapFeetAndBall(ball)) {
            System.out.println("Collide!");
            //Process the collision
            switch (this.previousOrientation) {
                case UP:
                    if (this.orientation == Orientation.LEFT)//ball goes right
                        ball.accelerateLeftRight(false);//kick right
                    else//ball goes left
                        ball.accelerateLeftRight(true);//kick left
                    break;
                case DOWN:
                    if (this.orientation == Orientation.LEFT)//ball goes left
                        ball.accelerateLeftRight(true);//kick left
                    else//ball goes right
                        ball.accelerateLeftRight(false);//kick right
                    break;
                case LEFT://It was left, now it must be down
                    ball.accelerateLeftRight(false);//kick right
                    break;
                case RIGHT://It was right, now it must be down
                    ball.accelerateLeftRight(true);//kick left
                    break;
            }
        }
    }
    
    /**
     * Checks for a collision between the body and the ball.
     * @param ball The game's ball
     */
    private void collideWithBody(Ball ball) {
        if (overlapBodyAndBall(ball)) {
            //Process collision
        }
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
            insideYRange = ((ballLoc.y + ball.getRadius()) >= foot.y);
        } else {//Ball below the body
            insideYRange = ((ballLoc.y - ball.getRadius()) <= foot.y);
        }
        
        return insideXRange && insideYRange;
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
                this.previousOrientation = Orientation.UP;
                break;
            case DOWN:
                this.orientation = clockwise ? Orientation.LEFT : Orientation.RIGHT;
                this.previousOrientation = Orientation.DOWN;
                break;
            case LEFT:
                this.orientation = clockwise ? Orientation.UP : Orientation.DOWN;
                this.previousOrientation = Orientation.LEFT;
                break;
            case RIGHT:
                this.orientation = clockwise ? Orientation.DOWN : Orientation.UP;
                this.previousOrientation = Orientation.RIGHT;
                break;
        }
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
    public Point getPreviousFootLocation() {
        return convertLocationToFootLocation(this.previousLocation, this.previousOrientation);
    }
    /**
     * Gets the player's location (the center of the player's head).
     * @return A Point representing the player's head's location.
     */
    public Point getLocation() { return this.location; }
    public Point getPreviousLocation() { return this.previousLocation; }
    public Orientation getOrientation() { return this.orientation; }
    public Orientation getPreviousOrientation() { return this.previousOrientation; }
    
    
    
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
}
