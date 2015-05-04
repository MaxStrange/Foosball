package fussball;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 *A class to actually manipulate the high score file so that the GUI doesn't
 * have to know how.
 * @author Max Strange
 */
public class ScoreFileManager {
    public static final String SCORE_FILE_NAME = "highScores.txt";
    public static final int NUMBER_OF_SCORES_TO_SAVE = 10;
    
    private static final String[] EXAMPLE_SCORES =
    {
        "Tom: 530", "Dick: 531", "Harry: 532", "Jeremy Clarkson: 456", "James May: 690",
        "Richard Hammond: 613", "Sally: 832", "Example Person: 890", "Xena: 902",
        "Batman: 789"
    };
    
    
    /**
     * Creates a new high score file in the current directory.
     * @throws java.io.IOException Throws an IOException when creating a new
     * file fails.
     */
    public static void createNewHighScoreFile() throws IOException {
        File f = new File(SCORE_FILE_NAME);
        f.createNewFile();
        
        HighScore[] toWrite = new HighScore[NUMBER_OF_SCORES_TO_SAVE];
        for (int i = 0; i < toWrite.length; i++) {
            toWrite[i] = new HighScore(EXAMPLE_SCORES[i]);
        }
        
        writeToFile(toWrite);
    }
    
    /**
     * Attempts to get all the high scores from the high score file.
     * @return The list of high scores and their corresponding names as strings
     * of the form "Name: SCORE".
     * @throws java.io.FileNotFoundException Throws a FileNotFoundException in
     * the case that the high score file cannot be located.
     * @throws IOException Thrown in the case that something unexpected happens
     * in the IO operation.
     */
    public static ArrayList<String> getAllScores() throws FileNotFoundException, IOException {
        File file = new File(SCORE_FILE_NAME);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
        
        ArrayList<String> listOfScores = new ArrayList<>();
        boolean keepReading = true;
        while (keepReading) {
            String line = reader.readLine();
            if (line == null)
                keepReading = false;
            else
                listOfScores.add(line);        
        }

        fis.close();
        bis.close();
        reader.close();
        
        return listOfScores;
    }
    
    /**
     * Checks whether the given score should go in the high score file, and
     * if so, adds it.
     * @param name The name of the person to put into the file with the score
     * @param score The score to potentially add to the file.
     * @throws FileNotFoundException Thrown if the high score file is not found
     * @throws IOException Thrown if something unforseen goes wrong with the IO 
     * operations.
     */
    public static void maybeAddNewHighScore(String name, double score) throws FileNotFoundException, IOException {
        ArrayList<String> scoresAsStrings = getAllScores();
        ArrayList<HighScore> scoresAsScores = new ArrayList<>();
        
        //if there aren't enough scores, something is wrong - replace the score file with the example scores
        if (scoresAsStrings.size() < NUMBER_OF_SCORES_TO_SAVE) {
            for (String s : EXAMPLE_SCORES) {
                scoresAsStrings.add(s);
            }
        }
        
        //Parse the strings into high score objects
        for (String s : scoresAsStrings) {
            HighScore hs = new HighScore(s);
            scoresAsScores.add(hs);
        }
        
        HighScore userScore = new HighScore(name, score);
        
        //Add the user's score, sort the list of scores, then take the first 10
        scoresAsScores.add(userScore);
        Collections.sort(scoresAsScores);
        
        //Take the first 10 and write them to the file
        HighScore[] listOfScoresToSave = new HighScore[NUMBER_OF_SCORES_TO_SAVE];
        for (int i = 0; i < NUMBER_OF_SCORES_TO_SAVE; i++) {
            listOfScoresToSave[i] = scoresAsScores.get(i);
        }
        
        writeToFile(listOfScoresToSave);
    }
    
    
    
    
    
    /**
     * Writes the given scores to the file, overwriting anything that was
     * previously in the file.
     * @param scoresToSave The scores to save in the file.
     * @throws Throws an IOException in case something unforseen happens while
     * writing the file.
     */
    private static void writeToFile(HighScore[] scoresToSave) throws IOException {
        FileWriter writer = new FileWriter(SCORE_FILE_NAME);
        
        for (int i = 0; i < scoresToSave.length; i++) {
            //Don't write more than the maximum number of scores to save
            if (i > NUMBER_OF_SCORES_TO_SAVE)
                break;
            
            String str = scoresToSave[i].toString();
            writer.write(str);
            writer.write(System.getProperty("line.separator"));
        }
        
        writer.close();
    }
    
    
    
    private static class HighScore implements Comparable<HighScore> {
        private final double score;
        private final String name;
        
        public HighScore(String name, double score) {
            this.score = score;
            this.name = name;
        }

        /**
         * Constructor for parsing a string of the form "Name: SCORE" into a
         * HighScore object.
         * @param toParse The string to parse.
         */
        public HighScore(String toParse) throws IllegalArgumentException {
            String[] parsed = toParse.split(":");
            if (parsed.length != 2) {
                throw new IllegalArgumentException("The given string is not of"
                        + " the form 'NAME: SCORE'");
            } else {
                this.name = parsed[0];
                this.score = Double.parseDouble(parsed[1]);
            }
        }
        
        @Override
        public int compareTo(HighScore other) {
            if (this.score < other.getScore())
                return -1;
            else if (this.score > other.getScore())
                return 1;
            else
                return 0;
        }
        
        @Override
        public String toString() {
            return this.name + ": " + this.score;
        }
        
        
        
        public final double getScore() { return this.score; }
        public final String getName() { return this.name; }
    }
}
