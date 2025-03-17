/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import java.util.Scanner;

/**
 *
 * @author tommy
 */


public class Battle {
    
    private Scanner scanner = new Scanner(System.in);
    
    /**
     * Default constructor for battle.
     */
    public Battle(){}
    
    
    /**
     * Battle Logic
     * Create logic the battle follows
     * create turns
     * Set up as simple text in console, for idea of the flow of the battle,
     * will transform logic onto a window and add GUI elements and create more depth to choices and enemy
     */
    
    /**
     * 
     * @param player
     * @param enemy
     * @return 
     */
    public boolean startBattle(Entity player, Entity enemy){
        /**
         * I think it would be a good idea to add a new method in entity to display its name to 
         */
        System.out.println("Your next challenger is " + "enemy.getName()" + ". Prepare yourself.");
        
        int turn = 0;
        
        while (player.isAlive() && enemy.isAlive()){
            updateTurn(turn);
            
            /**
             * Assuming Player will always go first.
             * Players turn
             */
            System.out.println("Choose your action: Attack | Heal | Inventory");
            String choice = scanner.nextLine().toLowerCase();
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
                        System.out.println("Invalid choice Try again.");
        }
            
            //Check if enemy has been defeated
            if(!enemy.isAlive()){
                System.out.println("enemy.getName()" + " has beeen defeated!");
                return true;
            }
            // Enemy Turn (Basic AI)
            System.out.println("enemy.getName()" + " attacks!");
            enemy.attack(player);

            // Check if player is defeated
            if (!player.isAlive()) {
                System.out.println("You have been defeated...");
                return false;
            }
        }
        return false;
    }//End of start Battle
    
    /**
     * 
     * @param turn
     * @return turn incremented by 1
     */
    public static int updateTurn(int turn){
        turn++;
        System.out.println("\nTurn " + turn);
        return turn;
        
        
    }//End of update Turn

}//End of Battle Class
