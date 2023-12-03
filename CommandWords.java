/**
 * This class is part of the "The depths of Tomorrow" application. 
 * "The depths of Tomorrow" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class CommandWords {
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", 
        "back", "pick up", "carry", 
        "put down", "drink", "eat", "escape", "give"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        String[] input = aString.split(" ");
        if(input.length > 1){
            String twoWordCommand = input[0] + " " + input[1];
            for(String command : validCommands) {
                if(command.equals(twoWordCommand)) {
                    return true;
                }
            }
        }
        for(String command : validCommands) {
            if(command.equals(input[0]))
                return true;
        }
        // If we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}