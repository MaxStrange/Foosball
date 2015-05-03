package fussball;

import java.awt.event.KeyEvent;

/**
 *A class to handle all the Game's logic. The Logic class should be the only
 * one that actually manipulates the GameState - everyone else can obtain stuff from
 * it, but it should only actually be manipulated by the Logic class.
 * @author Max Strange
 */
public class GameLogic {
    private GameState state = null;//The state of the game
    
    public GameLogic() {
        //Nothing to do here
    }
    
    /**
     * Sets up the logic by loading in the state that the logic will use.
     * @param state The state of the game. The Logic class should be the only
     * one that actually manipulates it - everyone else can obtain stuff from
     * it, but it should only actually be manipulated by the Logic class.
     */
    public void initialize(GameState state) {
        this.state = state;
    }
    
    /**
     * Handles the given event by checking if it is valid user input, and then
     * doing what the user wanted to do.
     * @param k The event in question.
     */
    public void handleKeyPressed(KeyEvent k) {
        //Handle arrow keys (rotate the selected spindles or move them up/down)
        switch (k.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.state.moveSelectedSpindles(true);
                break;
            case KeyEvent.VK_DOWN:
                this.state.moveSelectedSpindles(false);
                break;
            case KeyEvent.VK_LEFT:
                this.state.rotateSelectedSpindles(true);
                break;
            case KeyEvent.VK_RIGHT:
                this.state.rotateSelectedSpindles(false);
                break;
            case KeyEvent.VK_SPACE:
                this.state.getBall().reset();
                break;
        }
    }
    
    /**
     * Handles the given event by checking if it is valid user input, and then
     * doing what the user wanted to do.
     * @param k The event in question.
     */
    public void handleKeyReleased(KeyEvent k) {
        //Handle letter keys (select or unselect the specified spindles)
        this.state.toggleSpindleSelect(k.getKeyCode());
    }
    
    /**
     * Update the game because of a timer event.
     * @param numTicks the number of times the timer has ticked so far.
     */
    public void timerTick(int numTicks) {
        handleAI(numTicks);
        
        handleCollisionsWithPlayers();
        
        moveBall();
        
        decayMomentum(numTicks);
    }
    
    /**
     * Decays the momentum of each spindle.
     * @param numTicks The number of ticks so far
     */
    private void decayMomentum(int numTicks) {
        if ((numTicks % 10) == 0) {
            Spindle[] spindles = this.state.getAllSpindles();
            
            for (Spindle s : spindles) {
                s.decayMomentum();
            }
        }
    }
    
    /**
     * Handles the game's "AI" by invoking the AI class every 5 ticks.
     * @param numTicks The number of ticks so far.
     */
    private void handleAI(int numTicks) {
        if ((numTicks % 5) == 0)//Don't do the computer every moment - that's too fast
            GameAI.doMoves(state);        
    }
    
    /**
     * Checks for and deals with possible collisions between players and the
     * ball.
     */
    private void handleCollisionsWithPlayers() {
        /*
        Go through every spindle's players and check for collision with ball.
        */
        Player[] players = this.state.getAllPlayers();
        
        for (Player p : players) {
            p.collide(this.state.getBall());
        }
    }
    
    /**
     * Moves the ball according to its current velocity vector.
     */
    private void moveBall() {
        Ball ball = this.state.getBall();
        ball.move(this.state.getTable());
    }
}
