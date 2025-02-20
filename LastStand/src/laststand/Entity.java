/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sourc
 */
public class Entity {
    private String entityName;
    private Health entityHealth;
    private int entityAttackPower;
    private int entityDefense;
    private List<Item> inventory;
    
    public Entity(String name, int health, int attackPower,int defense){
    this.entityName = name;
    this.entityHealth = new Health(health);
    this.entityAttackPower = attackPower;
    this.entityDefense = defense;
    this.inventory = new ArrayList<Item>();
    }
    
    public boolean isAlive() {
        return entityHealth.isAlive();
    }
    
    public int getHealth() {
        return entityHealth.getHealthPoints();
    }
    
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    public void removeItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            System.out.println("item removed");
        } else {
            System.out.println("item not removed");
        }
    }
    
    //change entity health setter called "attack"
    public void attack(Entity target) {
        int damage = this.entityAttackPower;
        target.entityHealth.takeDamage(damage);
        System.out.println(this.entityName + " attacks" + target.entityName);
        if (!target.entityHealth.isAlive()) {
            System.out.println(target.entityName + "defeated");
        }
    }
    
    public void heal(int amount) {
        entityHealth.heal(amount);
    }
    
    public void showInventory() {
        System.out.println(inventory);
    }
    // show Inventory
}
