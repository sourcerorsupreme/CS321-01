/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 *
 * @author sourc
 */
public class Entity {
    private String entityName;
    private int entityHealth;
    private int entityAttackPower;
    private int entityDefense;
    
    public Entity(String name, int health, int attackPower,int defense){
    this.entityName = name;
    this.entityHealth = health;
    this.entityAttackPower = attackPower;
    this.entityDefense = defense;
    }
}
