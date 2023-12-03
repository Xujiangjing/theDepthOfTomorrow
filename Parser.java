import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

/**
 * This class is part of the "The depths of Tomorrow" application. 
 * "The depths of Tomorrow" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class Parser {
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() {
        System.out.print("> "); // print prompt

        String inputLine = reader.nextLine();
        String[] words = inputLine.split(" ");
        String commandWord = null;
        String description = null;
        String characterWord = getCharacterName(inputLine);

        //Check the first whether is valid command
        if (words.length > 0 && commands.isCommand(words[0])) {
            commandWord = words[0];
            description = extractDescription(inputLine, characterWord, 1);
        } else if (words.length > 1) {
            String twoWordCommand = words[0] + " " + words[1];
            if (commands.isCommand(twoWordCommand)) {
                commandWord = twoWordCommand;
                description = extractDescription(inputLine, characterWord, 2);
            }
        }
        return new Command(commandWord, description, characterWord);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands() {
        commands.showAll();
    }

    /**
     * Check the inputLine whether have the character name, if have 
     * @return characterName
     * if doesn't have
     * @return ""
     */
    private String getCharacterName(String inputLine) {
        List<String> possibeNames = Arrays.asList("Admin Officer", "Police", "Thief", "Scammer");
        for(String name : possibeNames){
            if(inputLine.contains(name)){
                return name;
            }
        }
        return "";
    }
    
    /**
     * Extract the description word by deleting character name and command word
     * @return the description
     */
    private String extractDescription(String inputLine, String characterWord, int commandWordCount) {
        if (characterWord != null) {
            inputLine = inputLine.replace(characterWord, "").trim(); // Remove character word
        }
        String[] words = inputLine.split(" ");
        String description = String.join(" ", Arrays.copyOfRange(words, commandWordCount, words.length));
        return description.isEmpty() ? null : description;
    }
}