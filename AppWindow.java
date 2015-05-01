package fussball;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 * The main GUI class.
 * @author Max Strange
 */
public class AppWindow extends JFrame implements KeyListener {
    private GameState state;
    private AppPanel panel;
    private GameLogic logic;
    
    /**
     * Constructor for the AppWindow.
     */
    public AppWindow() {
        this.state = state;
        this.panel = new AppPanel();
        add(this.panel);
    }
    
    /**
     * Initializes the AppWindow object, loading in the passed state.
     * @param state The State to use for keeping track of everything in the Game.
     * @param logic The logic to handle the game, which will manipulate the state.
     */
    public void initialize(GameState state, GameLogic logic) {
        this.state = state;
        this.logic = logic;
        this.logic.initialize(state);
        this.panel.initialize(state, logic);
    }
    
    
    /**
     * Starts the app.
     */
    public void start() {
        //Begin responding to keyboard events
        addKeyListener(this);
        
        //Begin repainting on a timer
        this.panel.startTimer();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        this.logic.handleKeyPressed(ke);
        this.panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        this.logic.handleKeyReleased(ke);
        this.panel.repaint();
    }
}
