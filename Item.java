import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents an item in the "The Depths of Tomorrow" game.
 * Each item has properties such as name, weight, and abilities like being pickable, carryable, drinkable, and eatable.
 * This class also maintains a static registry of all created items.
 * 
 * @author  Jiangjing.Xu
 * K-Number: 21134158
 */

public class Item {
    private static Map<String, Item> allItems = new HashMap<>(); // Static map of all items by their names.
    private String itemName;
    private Integer weight;
    private Boolean pickAbility;
    private Boolean carryAbility;
    private Boolean drinkAbility;
    private Boolean eatAbility;
    
    /**
     * Constructs an Item with specified name and weight.
     * Initially, the item has no abilities (pickable, carryable, etc.).
     * Adds the item to the static registry of all items.
     * @param itemName The name of the item.
     * @param weight The weight of the item.
     */
    public Item(String itemName, Integer weight) {
        this.itemName = itemName;
        this.weight = weight;
        this.pickAbility = false;
        this.carryAbility = false;
        this.drinkAbility = false;
        this.eatAbility = false;
        allItems.put(itemName, this);
    }
    
    /**
     * Retrieves an item by its name from the static registry.
     * @param name The name of the item to retrieve.
     * @return The Item object, or null if not found.
     */
    public static Item getItemByName(String name) {
        return allItems.get(name);
    }

    /**
     * Checks if an item exists in the static registry.
     * @param name The name of the item to check.
     * @return True if the item exists, false otherwise.
     */
    public static boolean hasItem(String name) {
        return allItems.containsKey(name);
    }
    
    /**
     * @return the name of the item
     */
    public String getItemName() {       
        return itemName;          
    }
    
    /**
     * Retrieves the names of all items in the static registry.
     * @return A set of item names
     */
    public static Set<String> getAllItemsName() {
        return new HashSet<>(allItems.keySet());
    }
    
    /**
     * Get the weight of item.
     * @return weight
     */
    public Integer getItemWeight() {
        return weight;
    }

    /**
     * Set this item whether can be picked up
     * @param pickAbility Boolean indicating if the item can be picked up.
     */
    public void itemPickAbility(Boolean pickAbility) {
        this.pickAbility = pickAbility;
    }

    /**
     * Set this item whether can be carried
     * @param carryAbility Boolean indicating if the item can be carried.
     */
    public void itemCarryAbility(Boolean carryAbility) {
        this.carryAbility = carryAbility;
    }

    /**
     * Set this item whether can be drunk
     * @param drinkAbility Boolean indicating if the item can be drunk.
     */
    public void itemDrinkAbility(Boolean drinkAbility) {
        this.drinkAbility = drinkAbility;
    }

    /**
     * Set this item whether can be eaten
     * @param eatAbility Boolean indicating if the item can be eaten.
     */
    public void itemEatAbility(Boolean eatAbility) {
        this.eatAbility = eatAbility;
    }

    /**
     * @return the item whether can be picked up
     */
    public Boolean getItemPickAbility() {
        return pickAbility;       
    }

    /**
     * @return the item whether can be carried
     */
    public Boolean getItemCarryAbility() {
        return carryAbility;
    }

    /**
     * @return the item whether can be drunk
     */
    public Boolean getItemDrinkAbility() {
        return drinkAbility;
    }

    /**
     * @return the item whether can be eaten
     */
    public Boolean getItemEatAbility() {
        return eatAbility;
    }
}