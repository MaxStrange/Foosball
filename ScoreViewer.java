package fussball;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *A GUI for showing the high scores and asking for a name to enter if the human
 * got a new high score.
 * @author Max Strange
 */
public class ScoreViewer extends JFrame {
    private final int MAX_USER_NAME_LENGTH = 20;//The longest the user's name is allowed to be
    private final double seconds;//The number of seconds passed since the start to the end - used for scoring
    
    
    /**
     * Constructor for the ScoreViewer.
     * @param seconds The number of seconds elapsed since the beginning of the
     * game. The fewer the number of seconds - the higher the score!
     */
    public ScoreViewer(double seconds) {
        this.seconds = seconds;
        
        this.setLayout(new GridLayout(0, 1));
        
        JLabel title = new JLabel("High Scores");
        title.setFont(new Font("Serif", Font.PLAIN, 80));
        add(title);
    }
    
    /**
     * Starts up the ScoreViewer.
     */
    public void go() {
        //Get the user's name in case it needs to be saved
        String name = getUserName();
        //Add the scores to the GUI
        addAllScores(name);
        this.repaint();
    }
    
    
    /**
     * Adds all the scores from the high score file to the GUI.
     * @param name The name to add to the high score file if one is to be added
     * (depends on how good the user's score is).
     */
    private void addAllScores(String name) {
        boolean remakeFile = false;
        try {
            
            //First, if the user's score is good enough, plug it into the high scores
            ScoreFileManager.maybeAddNewHighScore(name, this.seconds);
            
            //Then get all scores (including possibly, the user's score now)
            ArrayList<String> allScores = ScoreFileManager.getAllScores();
            
            //Place the files on the screen
            placeHighScores(allScores);
            
        } catch (FileNotFoundException e) {
            remakeFile = promptMakeNewHighScoreFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Something has gone wrong while trying to display the scores...");
        }

        //If the file couldn't be found, and the user wants to remake the high scores file
        if (remakeFile) {
            boolean keepTrying = false;
            
            do {
                keepTrying = false;//Reinitialize as false so that we exit the loop if the file is successfully made after a failure.
                try {//try remaking it
                    ScoreFileManager.createNewHighScoreFile();
                } catch (IOException e) {//In case something goes wrong, ask the user if they want to try again
                    keepTrying = askUserYesNo("Something went wrong when making "
                            + "the file. Would you like to try again?", "Try again?");
                }
            } while (keepTrying);//Keep trying to remake the file as long as it fails to be remade and the user wants to try again
        }
    }
    
    /**
     * Prompts the user for a yes/no input and returns whether the answer was
     * yes.
     * @param question The question to ask
     * @param title The title to display for the dialog box
     * @return Whether the user says yes or not.
     */
    private boolean askUserYesNo(String question, String title) {
        int answer = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);
        return (answer == JOptionPane.YES_OPTION);
    }
    
    /**
     * Gets the user's name from them. Guaranteed not to have ":" in it and
     * to be less than MAX_USER_NAME_LENGTH in characters.
     * @return The user's name.
     */
    private String getUserName() {
        String userName = "";
        boolean nameIsIllegal = false;
        do {
            String msg = "Enter your name. No colons allowed, and the name must be longer than one character but no longer"
                    + " than " + MAX_USER_NAME_LENGTH + " characters.";
            String title = "Winner! Game over. :)";
            userName = JOptionPane.showInputDialog(this, msg, title, 1);
            if ((userName == null) || userName.contains(":") || (userName.length() > MAX_USER_NAME_LENGTH) || (userName.length() < 1))
                nameIsIllegal = true;
            else 
                nameIsIllegal = false;
            
        } while (nameIsIllegal);
        
        return userName;
    }
    
    /**
     * Actually places the high scores on the screen.
     * @param scores The list of scores to write.
     */
    private void placeHighScores(ArrayList<String> scores) {
        for (String s : scores) {
            JLabel label = new JLabel(s);
            label.setFont(new Font("Serif", Font.PLAIN, 50));
            add(label);
        }
    }
    
    /**
     * Prompts the user for whether they would like to make a new high score
     * file and save it in the current directory.
     * @return Whether the user wants to make a new high score file or not.
     */
    private boolean promptMakeNewHighScoreFile() {
        String msg = "The high scores file could not be found in the game's "
                + "directory. Would you like to make a new one?";
        String title = "Create new high score file?";

        boolean remakeFile = askUserYesNo(msg, title);

        return remakeFile;
    }
}
