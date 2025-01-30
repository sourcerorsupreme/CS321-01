/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

/**
 *
 * @author Team 3
 */
public class Fighter {
    private String name;
    private int health;
    private int attackPower;
    private int defense;
    // private Move[] moves;
    
    public Fighter(String name, int health, int attackPower,int defense){
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
    }
}
