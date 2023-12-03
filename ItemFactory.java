/**
 * Class ItemFactory - Utilizes the Factory Design Pattern to create Item objects.
 * 
 * This factory centralizes the creation of Item objects, ensuring consistency across the application.
 * It simplifies the process of adding or modifying properties of Item objects.
 * Changes to the item creation process can be managed within this factory, avoiding modifications 
 * in multiple places where Items are instantiated.
 * This approach enhances code readability and manageability, especially when the object creation 
 * logic is complex or subject to future changes.
 * 
 * @author Jiangjing.Xu
 * K-Number: 21134158
 */

public class ItemFactory {
    /**
     * Creates and returns an Item with specified properties.
     * 
     * @param name The name of the item.
     * @param weight The weight of the item.
     * @param pickable Indicates if the item can be picked up.
     * @param carryable Indicates if the item can be carried.
     * @param drinkable Indicates if the item is drinkable.
     * @param eatable Indicates if the item is eatable.
     * @return The created Item object.
     */
    public static Item createItem(String name, int weight, boolean pickable, boolean carryable, boolean drinkable, boolean eatable) {
        Item item = new Item(name, weight);
        item.itemPickAbility(pickable);
        item.itemCarryAbility(carryable);
        item.itemDrinkAbility(drinkable);
        item.itemEatAbility(eatable);
        return item;
    }
}