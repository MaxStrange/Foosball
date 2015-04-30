package fussball;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *The main Panel GUI.
 * @author Max Strange
 */
public class AppPanel extends JPanel {
    private GameState state;
    
    /**
     * Constructor for AppPanel objects.
     */
    public AppPanel() {
    }
    
    /**
     * Initializes the AppPanel.
     * @param state The state to be used for the game.
     */
    public void initialize(GameState state) {
        this.state = state;
        this.state.initialize(this);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawTableOutline(g);
        drawBall(g);
        drawAllSpindles(g);
        drawControls(g);
        drawScores(g);
    }
    
    private void drawAllSpindles(Graphics g) {
        Spindle[] allSpindles = this.state.getAllSpindles();
        
        //Spindles are too complicated - have them draw themselves
        for (Spindle s : allSpindles) {
            s.draw(g);
        }
    }
    
    /**
     * Draws the ball on the playfield.
     * @param g The Graphics object to use to draw the ball.
     */
    private void drawBall(Graphics g) {
        Ball b = this.state.getBall();
        g.fillOval(b.getXLocation() - b.getRadius(), b.getYLocation() - b.getRadius(), 
                b.getRadius() * 2, b.getRadius() * 2);
    }
    
    /**
     * Draws the controls on to the screen to be helpful for the player.
     * @param g The Graphics object to use to draw.
     */
    private void drawControls(Graphics g) {
        int offset = (this.getHeight() - this.state.getBottomWall().getY()) / 8;
        
        for (int i = 0; i < this.state.getHumanSpindles().length; i++) {
            Spindle s = this.state.getHumanSpindles()[i];
            Point belowSpindle = new Point(s.getBottom().x, s.getBottom().y + offset);
            
            String control;
            if (i == 0)
                control = "S";
            else if (i == 1)
                control = "D";
            else
                control = "F";
            
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
            g.drawString(control, belowSpindle.x, belowSpindle.y);
        }
    }
    
    /**
     * Draws the scores for each player on to the panel.
     * @param g The Graphics object in use by the panel to draw things.
     */
    private void drawScores(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        
        int xOffset = this.state.getLeftWall().getX() / 2;
        Point compScoreLoc = new Point(xOffset, 
                this.state.getTopWall().getY() / 2);
        Point humanScoreLoc = new Point(this.state.getRightWall().getX() - (2 *xOffset),
                this.state.getTopWall().getY() / 2);
        
        g.drawString("Computer: " + this.state.getHumanScore(), compScoreLoc.x, compScoreLoc.y);
        g.drawString("Human: " + this.state.getComputerScore(), humanScoreLoc.x, humanScoreLoc.y);
    }
    
    /**
     * Draws the table outline (including the goals).
     * @param g The Graphics object used to draw the outline.
     */
    private void drawTableOutline(Graphics g) {
        LeftRightWall left = this.state.getLeftWall();
        LeftRightWall right = this.state.getRightWall();
        TopBottomWall top = this.state.getTopWall();
        TopBottomWall bottom = this.state.getBottomWall();
        
        //Draw the table
        g.drawLine(left.getX(), left.getTop(), left.getX(), left.getBottom());
        g.drawLine(right.getX(), right.getTop(), right.getX(), right.getBottom());
        g.drawLine(top.getLeft(), top.getY(), top.getRight(), top.getY());
        g.drawLine(bottom.getLeft(), bottom.getY(), bottom.getRight(), bottom.getY());
        
        Goal leftGoal = this.state.getLeftGoal();
        Goal rightGoal = this.state.getRightGoal();
        
        //Draw the goals
        g.drawRect(leftGoal.getTopLeftCornerX(), leftGoal.getTopLeftCornerY(),
                leftGoal.getWidth(), leftGoal.getHeight());
        g.drawRect(rightGoal.getTopLeftCornerX(), rightGoal.getTopLeftCornerY(),
                rightGoal.getWidth(), rightGoal.getHeight());
    }
}
