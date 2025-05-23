/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Entity represents the class where user actions are centered around
 * User's actions such as attack/useItem/heal are dependent on this class
 * entityName - name of Entity
 * Health Health - Class representing 'hitpoints'
 * entityAttackPower - Amount of 'damage' done by entity
 * entityDefense - Amount of 'damage' that can be mitigated by entity
 * inventory - List of items selectable by entity
 * @author sourc
 */
public class Entity {
    private String entityName;
    private Health entityHealth;
    private int entityAttackPower;
    private int entityDefense;
    private List<Item> inventory;
    
    @JsonCreator
    public Entity( @JsonProperty("name")String name,
            @JsonProperty("health") int health,
            @JsonProperty("attackPower") int attackPower,
            @JsonProperty("defense")int defense){
    this.entityName = name;
    this.entityHealth = new Health(health);
    this.entityAttackPower = attackPower;
    this.entityDefense = defense;
    this.inventory = new ArrayList<Item>();
    }
    @JsonIgnore
    public boolean isAlive() {
        return entityHealth.isAlive();
    }
    @JsonIgnore
    public int getMaxHealth(){
        return entityHealth.getMaxHealhPoints();
    }
    @JsonIgnore
    public int getCurrentHealth() {
        return entityHealth.getHealthPoints();
    }
    public int getHealth(){
        return entityHealth.getMaxHealhPoints();
    }
    public int getDefense(){
        return entityDefense;
    }
    public String getName(){
        return this.entityName;
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
            System.out.println(target.entityName + " defeated");
        }
    }
    
    public void heal(int amount) {
        entityHealth.heal(amount);
    }
    
    public void showInventory() {
        System.out.println(inventory);
    }
    public List<Item> getInventory(){
        return inventory;
    }
    
    public void addToInventory(Item item){
        inventory.add(item);
    }
    
    public int getAttackPower(){
        return entityAttackPower;
    }
    
    public void increaseStats(){
        entityAttackPower = entityAttackPower + 5;
        entityDefense = entityDefense + 2;
    }
}
