package fussball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**
 * A class to represent each person-stick.
 * @author Max Strange
 */
public class Spindle {
    private Point top;//Top of the stick
    private Point bottom;//Bottom of the stick
    private Point intersectTop;//The point where the top of the table meets the spindle
    private Point intersectBottom;//The point where the bottom of the table meets the spindle
    private Player[] players;//The players on the stick
    private Color color;//The color that the players should be painted.
    private boolean selected = false;//Whether the user has the spindle currently selected or not
    private char control;//The control char for the spindle
    private int distancePerMove;//The distance the spindle moves when moved
    
    /**
     * Constructor
     */
    public Spindle() {
        
    }
    
    /**
     * Since spindles are complicated, they come with the ability to draw
     * themselves!
     * @param g The Graphics object used to draw the Spindle.
     */
    public void draw(Graphics g) {
        Color prev = g.getColor();//Save the color to set back to at the end
        g.setColor(this.color);
        g.drawLine(this.top.x, this.top.y, this.bottom.x, this.bottom.y);
        
        for (Player p : this.players) {
            p.draw(g);
        }
        
        g.setColor(prev);//Set the color back to whatever it was to prevent surprises
    }
    
    /**
     * Initializes the Spindle
     * @param numberOfPlayers The number of players the spindle has on it - they
     * will be equally spaced and centered, so if you pass in the number 1, you
     * will get a Spindle with a single player on it who is right in the center
     * of the stick.
     * @param top The top of the Spindle (sticks over the edge of the table)
     * @param bottom The bottom of the Spindle
     * @param intersectTop The point where the top of the table intersects the spindle
     * @param intersectBottom The point where the bottom of the table intersects the spindle
     * @param color The color that the spindle and players should be colored.
     * @param control The char to be used to control this spindle (if human).
     */
    public void initialize(int numberOfPlayers, Point top, Point bottom, 
            Point intersectTop, Point intersectBottom, Color color, char control) {
        this.top = top;
        this.bottom = bottom;
        this.intersectTop = intersectTop;
        this.intersectBottom = intersectBottom;
        this.players = new Player[numberOfPlayers];
        this.color = color;
        this.control = control;
        
        double length = (double)(bottom.y - top.y);
        double denom = (double)(numberOfPlayers + 1.0);
        int distance;//The distance between each player (and the top to the first player and the bottom to the last).
        distance = (int)((length / denom) + 0.5);
        
        this.distancePerMove = (int)((length / 25.0) + 0.5);
        
        for (int i = 0; i < numberOfPlayers; i++) {
            Point loc = new Point(top.x, top.y + (distance * (i + 1)));
            this.players[i] = new Player(loc);
        }
    }
    
    /**
     * Moves the spindle up or down.
     * @param up If true, moves up. Otherwise, moves down.
     */
    public void move(boolean up) {
        //Adjust the top and bottom
        int dist;//The actual distance moved
        
        if (up) {
            Player top = getTopPlayer();
            int distToWall = top.getLocation().y - this.intersectTop.y;
            boolean distToWallIsLessThanNormalDist = 
                    distToWall < this.distancePerMove;
            dist = distToWallIsLessThanNormalDist ? distToWall : this.distancePerMove;
        } else {
            Player bottom = getBottomPlayer();
            int distToWall = this.intersectBottom.y - bottom.getLocation().y;
            boolean distToWallIsLessThanNormalDist = 
                    distToWall < this.distancePerMove;
            dist = distToWallIsLessThanNormalDist ? distToWall : this.distancePerMove;
        }
        
        int topY = up ? this.top.y - dist : this.top.y + dist;
        int bottomY = up ? this.bottom.y - dist : this.bottom.y + dist;
                
        this.top = new Point(this.top.x, topY);
        this.bottom = new Point(this.bottom.x, bottomY);
        
        //Adjust each player
        for (Player p : this.players) {
            p.move(up, dist);
        }
    }
    
    /**
     * Rotates the spindle clockwise or counter clockwise.
     * @param clockwise If true, rotates the spindle clockwise. Otherwise,
     * rotates it counter clockwise.
     */
    public void rotate(boolean clockwise) {
        for (Player p : this.players) {
            p.rotate(clockwise);
        }
    }
    
    /**
     * If the passed in ASCII value matches the control char for this spindle,
     * toggles whether or not the spindle is selected. Otherwise, does nothing.
     * @param asciiVal The ASCII value to compare against the control char.
     */
    public void toggleSelect(int asciiVal) {
        char c = (char)asciiVal;
        if (Character.toUpperCase(this.control) == Character.toUpperCase(c))
            this.selected = !this.selected;//Toggle
    }
    
    
    public Point getBottom() { return this.bottom; }
    public char getControl() { return this.control; }
    public Player[] getPlayers() { return this.players; }
    public boolean isSelected() { return this.selected; }
    
    
    
    /**
     * Returns the player who is at the bottom of the spindle.
     * @return The bottom player from the spindle.
     */
    private Player getBottomPlayer() {
        Player bottom = getPlayers()[0];
        for (Player p : getPlayers()) {
            if (p.getLocation().y > bottom.getLocation().y)
                bottom = p;
        }
        return bottom;
    }
    
    /**
     * Returns the player who is at the top of the spindle.
     * @return the top player from the spindle
     */
    private Player getTopPlayer() {
        Player top = getPlayers()[0];
        for (Player p : getPlayers()) {
            if (p.getLocation().y < top.getLocation().y)
                top = p;
        }
        return top;
    }
}
