/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 *
 * @author sourc
 */
public class Health {
    private int healthPoints;
    
    public Health(int healthPoints) {
        this.healthPoints = healthPoints;
    }
    
    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }
    
    public boolean isAlive() {
        return healthPoints > 0;
    }
    
    public int getHealthPoints() {
        return healthPoints;
    }
}
