package fussball;

import javax.swing.JFrame;

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
        //Set up the GUI
//        AppWindow gui = new AppWindow();
//        
//        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	gui.setSize(1000, 1000);
//        gui.setResizable(false);
//	gui.setVisible(true);
//
//        //Start up the program back-end
//        GameState state = new GameState();
//        GameLogic logic = new GameLogic();
//        gui.initialize(state, logic);
//        gui.start();
//        
        
        ScoreViewer sv = new ScoreViewer(540);
        
        sv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	sv.setSize(1000, 1000);
        sv.setResizable(false);
	
        sv.go();
        
        sv.setVisible(true);        
    }
}
