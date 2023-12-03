import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class TransporterRoom, a specialized version of the Room class
 * A magic room for player to transport them another room randomly
 * TransporterRoom extends Room, it inherits all its methods and properties
 * 
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class TransporterRoom extends Room {
    private HashMap<String, Room> rooms; // Stores a mapping of room names to Room objects

    /**
     * Constructor for TransporterRoom.
     * Initializes the room with a name, description, and a map of all rooms.
     *
     * @param roomName The name of the transporter room.
     * @param description A short description of the room.
     * @param allRooms A map of all room names to their corresponding Room objects.
     */
    public TransporterRoom(String roomName, String description, HashMap<String, Room> allRooms) {
        super(roomName,description);
        this.rooms = allRooms;
    }

    /**
     * Selects and returns a random Room from the map of rooms.
     * @return A randomly selected Room object from the rooms map.
     */
    public Room getRandomRoom() {
        List<Room> values = new ArrayList<>(rooms.values());
        return values.get((int) (Math.random() * values.size())); //Get random Integer in rooms size
    }
}