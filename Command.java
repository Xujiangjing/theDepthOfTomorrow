/**
 * This class is part of the "The depths of Tomorrow" application. 
 * "The depths of Tomorrow" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a description
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * Sometimes the command consists of 3 phrases, the command word, the description word
 * and character word
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class Command {
    private String commandWord;
    private String descriptionWord;
    private String characterWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param commandWord The first phrase of the command. Null if the command
     *                  was not recognised.
     * @param descriptionWord The second phrase of the command.
     * @param characterWord The third phrase of the command
     */
    public Command(String commandWord, String descriptionWord, String characterWord) {
        this.commandWord = commandWord;
        this.descriptionWord = descriptionWord;
        this.characterWord = characterWord;      
    }

    /**
     * Return the command word of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord() {
        return commandWord;
    }

    /**
     * @return The second phrase of this command. Returns null if there was no
     * description word.
     */
    public String getDescriptionWord() {
        return descriptionWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * @return true if the command has descriptionWord.
     */
    public boolean hasDescriptionWord() {
        return (descriptionWord != null);
    }

    /**
     * @return ture if the command has a CharacterWord
     */
    public boolean hasCharacterWord() {
        return (characterWord != null);
    }
    
    /**
     * @return characterWord
     */
    public String getCharacterWord() {
        return characterWord;
    }
}