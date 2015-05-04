package fussball;

/**
 *Class to represent the goals on the table of the fussball game.
 * @author Max Strange
 */
public class Goal {
    private final int yTop;//The y value of the top bound of the goal
    private final int yBottom;//The y value of the bottom bound of the goal
    private final int xStart;//The x value of the left-most side of the goal
    private final int xEnd;//The x value of the right-most side of the goal
    private final int LIGHT_DELAY = 10;//The number of ticks the light up effect goes on for
    
    private boolean litUp = false;//Whether or not the goal is lit up currently
    private int litTime = 0;//The number of ticks so far lit up.
    
    
    /**
     * Constructor for the Goal class.
     * @param yTop The y value for the top of the goal.
     * @param yBottom The y value for the bottom of the goal.
     * @param xStart The x value for the left of the goal.
     * @param xEnd The x value for the right of the goal.
     */
    public Goal(int yTop, int yBottom, int xStart, int xEnd) {
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.xStart = xStart;
        this.xEnd = xEnd;
    }
    
    /**
     * Tells the goal that it has been lit up for another tick. (It will
     * turn itself off after a certain number of these).
     */
    public void incrementLightUpEffect() {
        this.litTime++;
        if (this.litTime > LIGHT_DELAY)
            lightUp(false);
    }
    
    /**
     * Lights up the goal or turns off the light up effect.
     * @param turnOn If true, lights up the goal. Otherwise, turns off the 
     * light up effect.
     */
    public void lightUp(boolean turnOn) {
        this.litUp = turnOn;
        if (!turnOn)
            this.litTime = 0;//Reset the litTime if we turn off the light.
    }
    
    
    public int getBottomLeftCornerY() { return this.yBottom; }
    public int getTopLeftCornerX() { return this.xStart; }
    public int getTopLeftCornerY() { return this.yTop; }
    public int getWidth() { return this.xEnd - this.xStart; }
    public int getHeight() { return this.yBottom - this.yTop; }
    public boolean isLitUp() { return this.litUp; }
}
