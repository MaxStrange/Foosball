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
        //Handle arrow keys (rotate the selected spindles)
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
}
