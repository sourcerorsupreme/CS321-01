/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Class Item is one of the actions selectable by the Player Class
 * itemEffect describes what the item selected by the Player does
 *          heal will increase the health parameter of the player
 *          damage will decrease the health parameter of entities
 * 
 */

public class Item {
     /**
     * Enumerated type itemEffect describes what the item's effect is
     * Heal will increase the health of the entity/player using it
     * Damage will decrease the health of the targeted user
     */
    public enum itemEffect{
       Heal, Damage
    }   
    /**
     * Enumerated type itemEffect describes what the item's effect is
     * Heal will increase the health of the entity/player using it
     * Damage will decrease the health of the targeted user
     */
    private String itemName;
    private int value; 
    private itemEffect type;
    
    // Constructor, add Player owner
    @JsonCreator
    public Item(@JsonProperty("name")String name,
            @JsonProperty("value") int value,
            @JsonProperty("itemEffect")itemEffect type){
        this.itemName = name;
        this.value = value;
        this.type = type;
    }
    
    // Take player class as input
    /**
     * This will be used in game logic to consume the item and perform some
     * function dependent on the item's effect.
     * @param target The entity that will be affected by the item
     */
    public void useItem(Entity target){
        switch(this.type){
            case Heal -> // increase health of player
                target.heal(this.value);
            case Damage -> // decrease health of entity
                target.heal(-this.value);
            default -> {
            }
        }
    }
    public int getValue(){
        return value;
    }
    public String getItemName(){
        return itemName;
    }
    public itemEffect getItemEffect(){
        return type;
    }
    /**
     * Used by the view to easily generate an informative description of the item's
     * effects to the user.
     * @return 
     */
    @JsonIgnore
    public String getItemDescription() {
        return switch(this.type) {
            case Heal -> "Heal yourself for " + this.value + " hp.";
            case Damage -> "Deal " + this.value + " damage to enemy.";
            default -> "Unknown item type.";
    };
}
    
}
