/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 * Class Health represents the 'hitpoints' of an entity/player
 * healthPoints - current 'hitpoint' value
 * maxHealth - maximum 'hitpoint' value possible
 * Will contain class methods handling updating this value in entity/player class
 * @author sourc
 */
public class Health {
    //current health
    private int healthPoints;
    //maximum health
    private int maxHealth;
    
    public Health(int healthPoints) {
        this.healthPoints = healthPoints;
        this.maxHealth = healthPoints;
    }
    
    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }
    
    public void heal(int amount) {
        healthPoints += amount;
        if (healthPoints > maxHealth) {
            healthPoints = maxHealth;
        }
    }
    
    public boolean isAlive() {
        return healthPoints > 0;
    }
    
    public int getHealthPoints() {
        return healthPoints;
    }
    public int getMaxHealhPoints(){
        return maxHealth;
    }
}