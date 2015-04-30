package fussball;

import javax.swing.JFrame;

/**
 * The main GUI class.
 * @author Max Strange
 */
public class AppWindow extends JFrame {
    private GameState state;
    private AppPanel panel;
    
    /**
     * Constructor for the AppWindow.
     */
    public AppWindow() {
        this.state = state;
        this.panel = new AppPanel();
        add(this.panel);
    }
    
    public void initialize(GameState state) {
        this.state = state;
        this.panel.initialize(state);
    }
    
    
    /**
     * Starts the app.
     */
    public void start() {
        //Begin responding to keyboard events
        //TODO
    }
}
