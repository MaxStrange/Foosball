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
    private Orientation orientation;//The current orientation of the player.

    public Player(Point location) {
        this.location = location;
        this.orientation = Orientation.DOWN;
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
        
        g.drawLine(foot.x, foot.y, hipTop.x, hipTop.y);
        g.drawLine(foot.x, foot.y, hipBottom.x, hipBottom.y);
    }
    
    /**
     * Gets the player's foot's location. If the player's orientation is UP,
     * this will return (-1, -1), so make sure to check the orientation of the
     * player before using this value.
     * @return The location of the player's foot, or (-1, -1) if the foot is
     * in the air (orientation is UP).
     */
    public Point getFootLocation() {
        int footX;
        switch (this.orientation) {
            case DOWN:
                footX = this.location.x;
                break;
            case UP:
                return new Point(-1, -1);
            case LEFT:
                footX = this.location.x - LEG_LENGTH;
                break;
            case RIGHT:
                footX = this.location.x + LEG_LENGTH;
                break;
            default:
                throw new IllegalStateException("Somehow the Orientation of the "
                        + "player is not possible.");
        }
        
        Point foot = new Point(footX, this.location.y);
        return foot;
    }
    /**
     * Gets the player's location (the center of the player's head).
     * @return A Point representing the player's head's location.
     */
    public Point getLocation() { return this.location; }
    public Orientation getOrientation() { return this.orientation; }
}
