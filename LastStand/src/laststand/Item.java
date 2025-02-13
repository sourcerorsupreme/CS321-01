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
    private String itemName;
    private float value; 
    // include Item Type : Heal, Damage, Defense?
    // private PlayerClassName ItemOwner;       // ownero fhe item
    
    // Constructor, add Player owner
    public Item(String name, float value ){
        this.itemName = name;
        this.value = value;
    }
    
    // Take player class as input
    public void useItem(){
     // switch(Item Type)
     // Heal -> ItemOwner.setHealth()
     // Damage -> Enemy.setHealth()
 
    }
    
}
