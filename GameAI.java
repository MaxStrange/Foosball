package fussball;

import java.util.Random;

/**
 *The "AI" for the game - the computer essentially just randomly flails around.
 * @author Max Strange
 */
public class GameAI {
    public GameAI() {
        
    }
    
    /**
     * Does the computer's moves.
     * @param state The state to update according to what the computer ends
     * up doing.
     */
    public static void doMoves(GameState state) {
        Random r = new Random();
        Spindle[] spindles = state.getComputerSpindles();
        
        for (Spindle s : spindles) {
            int i = r.nextInt(99);
            if (i < 33)
                s.move(true);
            else if (i < 66)
                s.move(false);
            //Otherwise, don't move the spindle
            
            i = r.nextInt(99);
            if (i < 33)
                s.rotate(true);
            else if (i < 66)
                s.rotate(false);
            //Otherwise, don't rotate
        }        
    }
}
