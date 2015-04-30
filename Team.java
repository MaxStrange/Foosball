package fussball;

import java.awt.Color;
import java.awt.Point;

/**
 *A team is essentially a list of Spindles on a given foosball "team".
 * @author Max Strange
 */
public class Team {
    private Spindle[] spindles = new Spindle[3];
    
    /**
     * Constructor.
     * @param topIntersect The y value of the top of the table
     * @param bottomIntersect The y value of the bottom of the table
     * @param startingX The left most wall's x value plus a little (for the computer)
     * or 1/2 widthInterval to the right of the computer's startingX for the human.
     * @param widthInterval How far between each spindle on a given team
     * @param stickOutInterval A multiple to be used to determine how far over
     * the table's bounds each spindle will stick out (each will stick out
     * a multiple of this number).
     * @param Color The color of the team.
     * @param human If the team is the human team or not
     */
    public Team(int topIntersect, int bottomIntersect, int startingX, 
            int widthInterval, int stickOutInterval, Color color, boolean human) {
        for (int i = 0; i < this.spindles.length; i++) {
            this.spindles[i] = new Spindle();
            
            
            int numPlayers;//The number of players on the spindle
            int x;//The x value of the spindle
            int stickOut;//How far the spindle will stick out from the table
            if (i == 0) {
                numPlayers = human ? 3 : 1;
                x = startingX;
                stickOut = stickOutInterval;
            } else if (i == 1) {
                numPlayers = 5;
                x = startingX + widthInterval;
                stickOut = 3 * stickOutInterval;
            } else {
                numPlayers = human ? 1 : 3;
                x = startingX + (2 * widthInterval);
                stickOut = stickOutInterval;
            }
            
            Point top = new Point(x, topIntersect - stickOut);
            Point bottom = new Point(x, bottomIntersect + stickOut);
            Point topTable = new Point(x, topIntersect);
            Point bottomTable = new Point(x, bottomIntersect);
            
            
            this.spindles[i].initialize(numPlayers, top, bottom, 
                    topTable, bottomTable, color);
        }
    }
    
    public Spindle[] getSpindles() { return this.spindles; }
}
