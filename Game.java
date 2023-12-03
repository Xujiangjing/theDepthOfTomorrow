import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *  This class is the main class of the "The depths of Tomorrow" application. 
 *  "The depths of Tomorrow" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. 
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 *  @author  Jiangjing.Xu
 *  K-Number: 21134158
 */

public class Game {   
    private Parser parser;
    private Room currentRoom;
    public HashMap<String, Room> rooms;
    private Stack<Room> roomHistory;
    private Stack<String> pickStack;
    private Stack<String> carryStack;
    private Integer totalCarryWeight; // The player can carry limitation weight
    private int goUseToday; // The player can use 5 times "go" a day
    private int currentDay; // The player have 3 days to win
    private boolean wantToQuit;
    private HashMap<String, Character> characters;
    private List<Room> movableRooms1; // The list of rooms to good characters to move
    private List<Room> movableRooms2; // The list of rooms to bad characters to move    
            
    /**
     * Create the game and initialise its internal map
     */
    public Game() {
        rooms = new HashMap<String, Room>();
        movableRooms1 = new ArrayList<Room>();
        movableRooms2 = new ArrayList<Room>();
        characters = new HashMap<String, Character>();
        
        createRooms();
        createItems();
        createCharacters();
        parser = new Parser();
        roomHistory = new Stack<>();
        pickStack = new Stack<>();
        carryStack = new Stack<>();
         
        totalCarryWeight = 0;  
        goUseToday = 0;
        currentDay = 1;        
    }

    /**
     * Create all the rooms and link their exits together, and add rooms to HashMap rooms, easily for calling the room in the class
     */
    private void createRooms() {  
        // Create the rooms 
        Room adminRoom = new Room("adminRoom", "in the admin room");
        Room storeHouse = new Room("storeHouse", "in the storeHouse");
        Room bar = new Room("bar", "in the bar");
        Room home = new Room("home", "in your home");
        Room hospital = new Room("hospital", "in the hospital");
        Room restaurant = new Room("restaurant", "in the restaurant");
        Room school = new Room("school", "in the school");
        Room policeStation = new Room("policeStation", "in the Police Station");
        Room market = new Room("market", "in the market");
        Room exit = new Room("exit", "the exit of the lowest floor");
        
        // Adding rooms to the rooms map to call the room in the class
        rooms.put("adminRoom", adminRoom);
        rooms.put("storeHouse", storeHouse);
        rooms.put("bar", bar);
        rooms.put("home", home);
        rooms.put("hospital", hospital);
        rooms.put("restaurant", restaurant);
        rooms.put("school", school);
        rooms.put("policeStation", policeStation);
        rooms.put("market", market);
        rooms.put("exit", exit);
        
        // Create TransporterRoom 
        TransporterRoom transporterRoom = new TransporterRoom("transporter","a magic transporter room", rooms);
        rooms.put("transporter", transporterRoom); 
        
        // Initialise room exits
        adminRoom.setExit("west", bar);
        adminRoom.setExit("east", school);
    
        storeHouse.setExit("north", hospital);
    
        bar.setExit("east", adminRoom);
        bar.setExit("west", transporterRoom);
        bar.setExit("south", home);
    
        hospital.setExit("west", restaurant);
        hospital.setExit("north", exit);
        hospital.setExit("south", storeHouse);
    
        home.setExit("north", bar);
        home.setExit("east", market);
    
        restaurant.setExit("east", hospital);
        restaurant.setExit("north", policeStation);
    
        school.setExit("east", policeStation);
        school.setExit("west", adminRoom);
        school.setExit("south", market);
    
        policeStation.setExit("east", exit);
        policeStation.setExit("west", school);
        policeStation.setExit("south", restaurant);
     
        market.setExit("north", school);
        market.setExit("west", home);
    
        exit.setExit("west", policeStation);
        exit.setExit("south", hospital);
        
        // Set the starting room
        currentRoom = home;       
    }
    
    /**
     * Use ItemFactory to create the name and weight of items, set their pickable, carryable, drinkable, and eatable
     * Link the room and the item in this room
     */
    private void createItems() {
        //Create items
        Item key = ItemFactory.createItem("Key", 10, true, true, false, false);
        rooms.get("adminRoom").addItem(key);
        
        Item aircraft1 = ItemFactory.createItem("Aircraft", 70, true, true, false, false);
        rooms.get("policeStation").addItem(aircraft1);
        
        Item aircraft2 = ItemFactory.createItem("Hover Car", 60, true, true, false, false);
        rooms.get("storeHouse").addItem(aircraft2);
        
        Item beer = ItemFactory.createItem("Authentic German Craft Beer", 10, false, false, true, false);
        rooms.get("bar").addItem(beer);
        
        Item energyPotion = ItemFactory.createItem("Energy Potion", 5, true, true, true, false);
        rooms.get("hospital").addItem(energyPotion);

        Item jacket = ItemFactory.createItem("Leather Jacket", 5, true, true, false, false);
        rooms.get("home").addItem(jacket);

        Item socks = ItemFactory.createItem("Smelly Socks", 5, true, true, false, false);
        rooms.get("home").addItem(socks);

        Item steak = ItemFactory.createItem("Synthetic Steak", 5, false, false, false, true);
        rooms.get("restaurant").addItem(steak);

        Item bottle = ItemFactory.createItem("Broken Sports Bottle", 5, true, true, false, false);
        rooms.get("school").addItem(bottle);

        Item bag = ItemFactory.createItem("Fake Gucci Bag", 7, true, true, false, false);
        rooms.get("market").addItem(bag);
    }

    /**
     * Create the name of the characters and the room they staying at the beginning
     * Create the list of rooms that they will go to randomly
     */
    private void createCharacters() {
        // MovableRooms1 is for good character, they can go to police station and admin officer
        movableRooms1.add(rooms.get("adminRoom"));
        movableRooms1.add(rooms.get("policeStation"));
        movableRooms1.add(rooms.get("bar"));
        movableRooms1.add(rooms.get("school"));
        movableRooms1.add(rooms.get("market"));
        movableRooms2.add(rooms.get("restaurant"));
    
        // MovableRooms is for bad character, they can't go to police station and admin officer
        movableRooms2.add(rooms.get("market"));
        movableRooms2.add(rooms.get("bar"));
        movableRooms2.add(rooms.get("restaurant"));

        // Create characters with name and room and list 
        Character adminOfficer = new Character("Admin Officer", rooms.get("adminRoom"), movableRooms1);
        Character police = new Character("Police", rooms.get("policeStation"), movableRooms1);
        Character scammer = new Character("Scammer", rooms.get("market"), movableRooms2);
        Character thief =new Character("Thief", rooms.get("market"), movableRooms2);

        // Put characters in List characters
        characters.put("adminOfficer", adminOfficer);
        characters.put("police", police);
        characters.put("scammer", scammer);
        characters.put("thief", thief);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // Execute them until the game is over.                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            moveAllCharacterRandom();
            finished = processCommand(command);
            
        }
        System.out.println("# Thank you for playing.  Good bye. #");
    }
    
    /**
     * Make characters move randomly
     */
    private void moveAllCharacterRandom() {
        characters.get("adminOfficer").moveRandom();
        characters.get("police").moveRandom();
        characters.get("scammer").moveRandom();
        characters.get("thief").moveRandom();
    }
    
    /**
     * Get room description with characters
     */
    private String getRoomDescriptionWithCharacters(Room room) {
        StringBuilder description = new StringBuilder(room.getLongDescription());
        // Check the room whether has characters
        for (Character character : characters.values()){
            String characeterDescription = character.getCharacterDescription(room);
            if(!characeterDescription.isEmpty()){
                description.append("\n").append(characeterDescription);
            }
        }
        return description.toString();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("# Welcome to The Depths of Tomorrow!                            #");
        System.out.println("# The Depths of Tomorrow is a dystopian intense adventure game. #");
        System.out.println("# Type 'help' if you need help.                                 #");
        System.out.println();
        System.out.println(getRoomDescriptionWithCharacters(currentRoom));      
        System.out.println(currentRoom.getAllItems());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        wantToQuit = false;

        String commandWord = command.getCommandWord();      
        if(command.isUnknown()) {
            System.out.println("# I don't know what you mean... #");
            return false;
        }

        // Everytime check the player whether meet the condition of winning 
        if(!commandWord.equals("escape") && checkWin()) {
            System.out.println("# You have all the necessary items at the exit! Use 'escape' to win the game. #");
            return false;
        }
        
        // Check whether is valid command
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("pick up")) {
            pickUp(command);
        }
        else if (commandWord.equals("carry")) {
            carry(command);
        }
        else if (commandWord.equals("put down")) {
            putDown(command);
        }
        else if (commandWord.equals("drink")) {
            drink(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("escape")) {
            escape();
        }
        else if(commandWord.equals("give")){
            give(command);
        }
       return wantToQuit;    
    }
    
    // Implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() {
        System.out.println("# You are on the lowest floor of the world.                                            #");
        System.out.println("# You need to find key, aircraft and Exit otherwise you will die.                      #");
        System.out.println("# You need to escape here in 3 days. You can use 5 times 'go' one day.                 #");
        System.out.println();
        System.out.println("# Your command words are: ");
        parser.showCommands();
        System.out.println("# If you want to carry Items, you need to pick up them firstly.                        #");
        System.out.println("# You can give item to characters, but the item will disappear forever.                #");
        System.out.println("# Try to drink or eat food, you may receive surprise.                                  #");
        System.out.println("# Try to give item to someone, you may receive surprise.                               #");
        System.out.println("# By the way, if you use 'give', you should follow the format of 'give Item Character'.#");
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * Caculate the times of using "go", 5 times is the max of one day
     * Caculate the day of using, the player can play 3 days
     */
    public void goRoom(Command command) {
        roomHistory.push(currentRoom); // Save the current room to the history before moving.
    
        if (!command.hasDescriptionWord()) {
            System.out.println("# Go where? #");
            return;
        }
    
        String direction = command.getDescriptionWord();
        Room nextRoom = currentRoom.getExit(direction);
    
        if (nextRoom == null) {
            System.out.println("# There is no door! #");
            return;
        }
    
        goUseToday++;
        dropPickedUpItems(); // Drop any picked up items that are not carried.
    
        // Transport the player if they are in a TransporterRoom.
        if (nextRoom instanceof TransporterRoom && goUseToday <= 5 && currentDay <= 3) {
            nextRoom = ((TransporterRoom) nextRoom).getRandomRoom();
            System.out.println("# The transporter room whisks you away to a random location! #");
        }
    
        // Move to the new room if the player still has moves left for the day.
        if (goUseToday <= 5 && currentDay <= 3) {
            currentRoom = nextRoom;
            System.out.println(getRoomDescriptionWithCharacters(currentRoom)); 
            System.out.println(currentRoom.getAllItems());
            System.out.println("# You can use " + (5 - goUseToday) + " times 'go' today. #");
        } else if (goUseToday > 5) {
            // Handle end of day if moves are used up.
            if (currentDay < 3) {
                System.out.println("# You are tired and need to rest for the day. #");
                currentRoom = rooms.get("home");
                System.out.println("# Sleeping................................... #");
                endDay();
            } else if (currentDay == 3) {
                endDay();
            }
        }
    }


    /**
     * Count the number of day
     * And if the time run out, game over
     */
    private void endDay() {
        currentDay ++;
        goUseToday = 0; 
        if(currentDay > 3) {
            System.out.println();
            System.out.println("***************************Day 4 begins****************************");
            System.out.println("# You have run out of time and have not completed your objective. #");
            System.out.println("# You were shot in the head by someone from the cleanup team.     #");
            System.out.println("# You haven't been able to escape your fate                       #");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("             Wish you good luck in the next life                   ");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("**************************GAME OVER********************************");
            wantToQuit = true;
        } else {
            roomHistory.clear();
            System.out.println("# Day: " + currentDay + " begins. You feel rested and ready to explore. #");
            System.out.println();
            currentRoom = rooms.get("home");
            System.out.println(getRoomDescriptionWithCharacters(currentRoom));       
            System.out.println(currentRoom.getAllItems());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if(command.hasDescriptionWord()) {
            System.out.println("# Quit what? #");
            return false;
        } else {       
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "Back" was entered.
     * It takes player back to the last room player’ve been in.
     * should keep track of every move made
     * allowing the player to eventually return to it’s starting room. 
     */    
    private void goBack() {
        if(!roomHistory.isEmpty()) {
            // After deciding to move back, drop the items
            dropPickedUpItems();
            // Go back
            currentRoom = roomHistory.pop();
            System.out.println(getRoomDescriptionWithCharacters(currentRoom)); 
            System.out.println(currentRoom.getAllItems());
        } else {
            System.out.println("# You are in the starting room! #");
        }
    }

    /**
     * "Pick up" entered
     * It allows user to pick up items in each room
     * Some items can be pick up, some items can't
     */
    private void pickUp(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Pick up what? #");
            System.out.println(currentRoom.getAllItems());
            return;
        }

        String itemName = command.getDescriptionWord();
        if(currentRoom.hasItem(itemName)) {
            Item itemToPick = currentRoom.getItem(itemName);
                // Check item whether can be picked up
                if(itemToPick.getItemPickAbility()) {
                    pickStack.push(itemName);
                    currentRoom.removeItem(itemName);
                    System.out.println("# " + itemName + " picked up. You can use 'carry' to carry it. #");  

                } else {
                    System.out.println("# " + itemName + " can't be picked up. #");
                }              
        } else {
            System.out.println("# There is no item with that name here. #");
        }
    }
    
    /**
     * If player just pick up items not carry them, 
     * The item will drop in the original room when them use goRoom or back
     */
    private void dropPickedUpItems() {
        while(!pickStack.isEmpty()) {
            String itemName = pickStack.pop();
            Item item = Item.getItemByName(itemName);
            currentRoom.addItem(item);
            System.out.println("# " + itemName + " is put back in " + currentRoom.getRoomName()+ ". #");
            pickStack.clear();
        }
    }

    /**
     * "Carry" entered
     * It allows player to carry items
     * There is a limitation that the player can carry a total of 100 weights.
     * Player can only "give" items which they are carried to characters
     */     
    private void carry(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Carry what? #");
            if (!pickStack.isEmpty()) {
                System.out.println("# Items available to carry: " + pickStack + ". #");
            }
            return;
        }

        String itemName = command.getDescriptionWord();
        // Check item if it is be picked up
        if(pickStack.contains(itemName)) {
            Item itemToCarry = Item.getItemByName(itemName);
                // Check item whether can be carried
                if(itemToCarry.getItemCarryAbility()) {
                    int itemWeight = itemToCarry.getItemWeight();
                    // Check the total carry weight if is below or equal to the maximum
                    if(totalCarryWeight + itemWeight <= 100) { // The limitation of totalCarryWeight                    
                        pickStack.remove(itemName);
                        carryStack.push(itemName);
                        totalCarryWeight += itemWeight;
                        currentRoom.removeItem(itemName);
                        System.out.println("# " + itemName + " carried. Total carry weight: " + totalCarryWeight + ". #");
                        System.out.println("# You have carried " + carryStack + ". #");  
                    } else {
                        System.out.println("# " + itemName + " is too heavy. Total carry weight exceeds limit. #");
                        System.out.println("# You can use 'put down' to put down your carried items. #");
                        System.out.println("# You have carried " + carryStack+ ". #");
                    }
                } else {
                    System.out.println("# " + itemName + "can't be carried. #");

                }
                     
        } else {
            System.out.println("# You have not picked up " + itemName + ". #");
        }
    }
        
    /**
     * "Put down" entered
     * It allows players to put items which they are carried in the currentRoom
     */
    private void putDown(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Put down what? #");
            System.out.println("# You have carried " + carryStack + ". #");
            return;
        }
    
        String itemName = command.getDescriptionWord();
        if (carryStack.contains(itemName)) {
            carryStack.remove(itemName);
            Item item = Item.getItemByName(itemName);
            if (item != null) {
                currentRoom.addItem(item);
                System.out.println("# " + itemName + " put down in " + currentRoom.getRoomName() + ". #");
                System.out.println(getRoomDescriptionWithCharacters(currentRoom)); 
                System.out.println(currentRoom.getAllItems());
            } else {
                System.out.println("# Item not found: " + itemName + ". #");
            }
            
        } else {
            System.out.println("# You are not carrying " + itemName + ". #");
        }
    }

    /**
     * "Drink" entered
     * It allows player drinks items which can drink
     * And if player drinks beer, they only can stay in the bar all day
     * if they drink enery potion, they can explore 6 room a day.
     */
    private void drink(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Drink what? #");
            return;
        }
        String itemName = command.getDescriptionWord();

        if(currentRoom.hasItem(itemName)) {
            Item item = currentRoom.getItem(itemName);
            if (item != null && item.getItemDrinkAbility()) {
                if(itemName.equals("Energy Potion")) {
                    System.out.println("# You feel energized. You can move one extra time today. #");
                    goUseToday--; // Allows one more room visit

                } else if(itemName.equals("Authentic German Craft Beer")) {
                    System.out.println("# You feel dizzy and can't move anymore today. Going back home. #");
                    currentRoom = rooms.get("home"); 
                    endDay();
                }
                
            } else {
                System.out.println("# You can't drink that. #");
            }
        } else {
                System.out.println("# There is no " + itemName + " here. #");
        }
    }

    /**
     * "Eat" entered
     * It allows player eats items which can eat
     * And if player eat steak, they can explore 6 room a day
     */
    private void eat(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Eat what? #");
            return;
        }

        String itemName = command.getDescriptionWord();
        if(currentRoom.hasItem(itemName)) {
            Item item = currentRoom.getItem(itemName);
            if(item != null && item.getItemEatAbility()) {
                if(itemName.equals("Synthetic Steak")) {
                    goUseToday --;
                    System.out.println("# You filled your stomach and regained some strength. #");
                    System.out.println("# You can use one more time go today. #");
                } else {
                    System.out.println("# Happy! #");
                }

            } else {
                System.out.println("# You can't eat that. #");
            }
        } else {
            System.out.println("# There is no " + itemName + " here. #");
        }
    }

    /**
     * Check play if win
     * When the player meet the conditions of winning, it returns true
     */
    private boolean checkWin() {
        if (currentRoom.getRoomName().equals("exit") && (carryStack.contains("Aircraft") || carryStack.contains("Hover Car") )&& carryStack.contains("Key")) {
            return true;
        }
        return false;
    }
    
    /**
     * "Escape" entered
     * It allows player to escape the lowest floor and to win the game
     */
    private void escape() {
        if (checkWin()) {
            System.out.println("#############################################################################");
            System.out.println("# You successfully escaped from the lowest floor and escaped your own fate. #");
            System.out.println("################ Congratulations, you have won the game!#####################");
            System.out.println("#############################################################################");
            wantToQuit = true;
        } else {
            System.out.println("# You cannot escape yet. #");
        }
    }
    
    /**
     * "Give" entered
     * It allows player gives Items which they have carried to characters
     * And the item will disappear forever
     */
    private void give(Command command) {
        if(!command.hasDescriptionWord()) {
            System.out.println("# Give what? #");
            return;
        }
        if(!command.hasCharacterWord()) {
            System.out.println("# To whom? #");
            return;
        } else {
            // Check whether the item carried by the player
            if(carryStack.contains(command.getDescriptionWord())) {
                System.out.println("# " + command.getCharacterWord() + " has received " + command.getDescriptionWord() + ". #");
                carryStack.remove(command.getDescriptionWord());
                
                // Special interaction with Police
                if("Police".equals(command.getCharacterWord())) {
                    Random random = new Random();
                    // 50% true
                    if(random.nextBoolean()) {
                        System.out.println("# You gain the goodwill of the Police!             #");
                        System.out.println("# The Police tell you the besy way to escape here! #");
                        System.out.println("# Go back to your home, then                       #");
                        System.out.println("# Go north--Go east and carry key--Go east--       #");
                        System.out.println("# Go east and carry Aircraft--Go east and escape   #"); 
                    }
                }
            } else {
                System.out.println("# You don't have " + command.getDescriptionWord() + ". #");
            }
        }
    }
}