package fussball;

import javax.swing.JFrame;

/**
 * @author Max Strange
 */
public class Fussball_Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppWindow gui = new AppWindow();
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.setSize(1000, 1000);
        gui.setResizable(false);
	gui.setVisible(true);

        GameState state = new GameState();
        GameLogic logic = new GameLogic();
        gui.initialize(state, logic);
        gui.start();
    }
}
