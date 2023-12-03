import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Character
 * The property of characters, their names, staying rooms at the beginning
 * I choose to make characters move randomly in specific rooms
 * 
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class Character {
    private String name;
    private Room currentRoom;
    private List<Room> movableRooms;

    public Character(String name, Room startingRoom, List<Room> movableRooms) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.movableRooms = new ArrayList<Room>(movableRooms);
    }
    
    /**
     * @return the name of character
     */ 
    public String getCharacterName() {
        return name;
    }

    /**
     * @return the character currently staying room
     */
    public Room getCharacterRoom() {
        return currentRoom;
    }

    /**
     * Make character move randomly
     */
    public void moveRandom() {
        if(movableRooms.isEmpty()) {
            return; // No room to move;
        }
        Random random = new Random();
        Room randomRoom = movableRooms.get(random.nextInt(movableRooms.size()));
        currentRoom = randomRoom;
    }

    /**
     * @return the description of the character 
     */
    public String getCharacterDescription(Room room) {
        if(this.currentRoom.equals(room)){
            return "# " + this.name + " is here. #";
        }
        return ""; // Return empty string if the character is not in the room
    }
}   