import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "The depths of Tomorrow" application. 
 * "The depths of Tomorrow" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class Room {
    private String roomName;
    private String description;
    private HashMap<String, Room> exits;  // Stores exits of this room.
    private HashMap<String, Item> itemMap; // Stores items in the room, mapped by their names
        
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param roomName The room's name
     * @param description The room's description.
     */
    public Room(String roomName,String description) {
        this.roomName = roomName;
        this.description = description;
        exits = new HashMap<>();
        itemMap = new HashMap<>(); 
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {   
        return exits.get(direction);      
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     # You are in the kitchen. #
     *     # Exits: north west. #
     * @return A long description of this room
     */
    public String getLongDescription() {
        return "# You are " + description + ". #\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "# Exits: north west. #".
     * @return Details of the room's exits.
     */
    public String getExitString() {
        String returnString = "# Exits:";
        Set<String> keys = exits.keySet();  
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString + ". #";
    }
      
    /**
     * Gets the name of the room.
     * @return The name of the room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Adds an item to the room.
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        itemMap.put(item.getItemName(), item);
    }

    /**
     * Return a string describing all the items in the room.
     * @return A string listing all items, or a message if there are no items.
     */
    public String getAllItems() {
        if (itemMap.isEmpty()) {
            return "# There are no items in the room. #";
        }
        return "# Items: " + String.join(", ", itemMap.keySet()) +" #";
    }
    
    /**
     * Retrieves an item from the room based on its name.
     * @param itemName The name of the item to retrieve.
     * @return The item, or null if it's not found.
     */
    public Item getItem(String itemName) {
        return itemMap.get(itemName);
    }

    /**
     * Check if an item is in the room.
     * @param itemName The name of the item to check for.
     * @return true if the item is present, false otherwise.
     */
    public boolean hasItem(String itemName) {
        return itemMap.containsKey(itemName);
    }

    /**
     * Remove an item from the room.
     * @param itemName The name of the item to be removed.
     */
    public void removeItem(String itemName) {
        itemMap.remove(itemName);
    }
}