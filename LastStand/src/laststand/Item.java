/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 *
 * @author Peter Cao
 */

public class Item {
    public enum itemEffect{
       Heal, Damage
    }   
    private String itemName;
    private float value; 
    private itemEffect type;
    // private PlayerClassName ItemOwner;       // ownero fhe item
    
    // Constructor, add Player owner
    public Item(String name, float value,itemEffect type){
        this.itemName = name;
        this.value = value;
        this.type = type;
    }
    
    // Take player class as input
    public void useItem(){
        switch(this.type){
            case Heal:
                // increase health of player
                break;
            case Damage:
                // decrease health of entity
                break;
            default:
                break;
        }
     // switch(Item Type)
     // Heal -> ItemOwner.setHealth()
     // Damage -> Enemy.setHealth()
 
    }
    
}
