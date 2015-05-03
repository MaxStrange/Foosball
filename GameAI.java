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

        //Decide if the spindle should rotate via a random method - skew the distribution towards rotating towards opponent's goal
        for (Spindle s : spindles) {
            int i = r.nextInt(99);
            if (i < 50)
                s.rotate(false);//rotate counter clockwise
            else if (i < 75)
                s.rotate(true);//rotate clockwise
            //Otherwise, don't rotate
        }
        
        //Move the spindle in a somewhat reasonable way.
        Ball ball = state.getBall();
        boolean moveIfUnclear = r.nextBoolean();
        boolean directionToMoveIfUnclear = r.nextBoolean();
        for (Spindle s : spindles) {
            if (ball.getLocation().y > s.getBottomPlayer().getLocation().y)
                s.move(false);//if the ball is below the bottom player, move the spindle down
            else if (ball.getLocation().y < s.getTopPlayer().getLocation().y)
                s.move(true);//If the ball is above the top player, move the spindle up
            else if (moveIfUnclear)
                s.move(directionToMoveIfUnclear);//If not clear what direction to move, maybe move randomly
        }
    }
}
