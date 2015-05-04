package fussball;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The Main for the app.
 * @author Max Strange
 */
public class Fussball_Main {

    /**
     * Main entry point for the program.
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        String title = "Directions for play";
        String directions = "How to play: The S D and F buttons select or "
                + "unselect the left, middle, and right spindles.\n The arrow "
                + "keys move the selected spindle up/down and rotate clockwise/"
                + "counter clockwise.\n The space bar resets the ball (in case"
                + " it gets stuck).\nFirst to get to six points wins! If you"
                + " do it fast enough, you can get a high score."
                + "\n\nWould you like to invert the left/right"
                + " arrows? They currently are set to: left = rotate clockwise.";
        int answer = JOptionPane.showConfirmDialog(null, directions, title, JOptionPane.YES_NO_OPTION);
        
        //Maybe invert the arrow keys
        boolean invert = false;
        if (answer == JOptionPane.YES_OPTION)
            invert = true;


        //Set up the GUI
        AppWindow gui = new AppWindow();
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.setSize(1000, 1000);
        gui.setResizable(false);
	gui.setVisible(true);

        //Start up the program back-end
        GameState state = new GameState();
        GameLogic logic = new GameLogic(invert);
        gui.initialize(state, logic);
        gui.start();
    }
}
