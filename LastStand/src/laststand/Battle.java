/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import java.util.Scanner;

/**
 * Class Battle handles the logic of the game; It will do so by managing the turns of players and entities
 * Player turns end once an action has been selected
 * Entity turns are randomly selected by this class
 * @author tommy
 */
public class Battle {
    
    Scanner scanner = new Scanner(System.in);
    /**
     * Battle Logic
     * Create logic the battle follows
     * create turns
     * Set up as simple text in console, for idea of the flow of the battle,
     * will transform logic onto a window and add GUI elements and create more depth to choices and enemy
     */
    
    int turn = 0;
    
    public void battleEncounter(){
        Entity player = new Entity("Player", 100, 10, 5);
        
         /**
          * Note: Create a boolean function to check entity health ?
          */
        while (player.isAlive()){
            Entity enemy = new Entity("Enemy", 50, 10, 5);
            
            while (enemy.isAlive()){
                System.out.println("Enter the action you would like to take.");
                System.out.print("Attack\n Heal\n Inventory\n ");

                turn = turn + 1;
                String choice = scanner.nextLine();
                switch (choice.toLowerCase()) {
                    case "attack":
                        player.attack(enemy);
                        break;
                    case "heal":
                        player.heal(0);
                        break;
                    case "inventory":
                        player.showInventory();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter Attack, Heal, or Inventory.");
                        break;
                    
                }
                /**
                 * Enemy Logic
                 * Created basic attack logic until we discuss how we want the "ai" to function
                 */
                if (enemy.isAlive()){
                     enemy.attack(player);
                }else{
                    
                }
            }
        }
        
        System.out.println("You have died");
    }
}
