package fussball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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
     */
    public void initialize(int numberOfPlayers, Point top, Point bottom, 
            Point intersectTop, Point intersectBottom, Color color) {
        this.top = top;
        this.bottom = bottom;
        this.intersectTop = intersectTop;
        this.intersectBottom = intersectBottom;
        this.players = new Player[numberOfPlayers];
        this.color = color;
        
        double length = (double)(bottom.y - top.y);
        double denom = (double)(numberOfPlayers + 1.0);
        int distance;//The distance between each player (and the top to the first player and the bottom to the last).
        distance = (int)((length / denom) + 0.5);
        
        
        for (int i = 0; i < numberOfPlayers; i++) {
            Point loc = new Point(top.x, top.y + (distance * (i + 1)));
            this.players[i] = new Player(loc);
        }
    }
    
    public Point getBottom() { return this.bottom; }
    public Player[] getPlayers() { return this.players; }
}
