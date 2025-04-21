/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import javax.swing.SwingUtilities;

/**
 * Testing class for the Battle system with more compact code structure
 */
public class BattleTest implements TestRunner {
    private final GameData data = new GameData("data.json");
    
    /**
     * Main method to run the battle test
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleTest().runAllTests());
    }
    
    /**
     *
     */
    @Override
    public void runAllTests() {
        testBasicBattle();
        testBattleWithVariedEntities();
        testBattleWithItems();
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getTestName() {
        return "Battle System Test";
    }
    
    // Backward compatibility
    public void runBattleTest() {
        runAllTests();
    }
    
    /**
     * Test a basic battle with default entities
     */
    public void testBasicBattle() {
        System.out.println("====== TESTING BASIC BATTLE ======");
        
        Entity player = new Entity("Test Player", 100, 15, 5);
        Entity enemy = new Entity("Test Enemy", 50, 10, 3);
        
        printEntityDetails(player);
        printEntityDetails(enemy);
        
        System.out.println("Starting battle...");
        simulateBattle(player, enemy, 4);
    }
    
    /**
     * Test battle with entities having varied attributes
     */
    public void testBattleWithVariedEntities() {
        System.out.println("\n====== TESTING BATTLE WITH VARIED ENTITIES ======");
        
        Entity glassCannonPlayer = new Entity("Glass Cannon", 50, 30, 1);
        Entity tankyEnemy = new Entity("Tank Enemy", 150, 5, 35);
        
        printEntityDetails(glassCannonPlayer);
        printEntityDetails(tankyEnemy);
        
        System.out.println("Starting battle...");
        simulateBattle(glassCannonPlayer, tankyEnemy, 5);
    }
    
    /**
     * Test battle with various items
     */
    public void testBattleWithItems() {
        System.out.println("\n====== TESTING BATTLE WITH ITEMS ======");
        
        Entity itemPlayer = new Entity("Item Tester", 100, 15, 7);
        Entity normalEnemy = new Entity("Normal Enemy", 80, 12, 3);
        
        // Add multiple items
        addItemsToEntity(itemPlayer, 5);
        
        printEntityDetails(itemPlayer);
        printEntityDetails(normalEnemy);
        
        System.out.println("Starting battle...");
        
        // Get the first item to use in battle
        if (!itemPlayer.getInventory().isEmpty()) {
            Item firstItem = itemPlayer.getInventory().get(0);
            System.out.println("Testing item: " + firstItem.getItemName());
            
            // Use item based on its type
            if (firstItem.getItemType() == Item.itemEffect.Heal) {
                itemPlayer.heal(10);
                System.out.println("Used healing item: " + itemPlayer.getName() + " HP: " + itemPlayer.getCurrentHealth());
            } else if (firstItem.getItemType() == Item.itemEffect.Damage) {
                itemPlayer.attack(normalEnemy);
                System.out.println("Used damage item: " + normalEnemy.getName() + " HP: " + normalEnemy.getCurrentHealth());
            }
            
            // Now do a short battle
            simulateBattle(itemPlayer, normalEnemy, 2);
        }
    }
    
    /**
     * Helper method to simulate battle turns
     * @param attacker First entity to attack
     * @param defender Second entity to attack
     * @param rounds Number of full rounds to simulate
     */
    private void simulateBattle(Entity attacker, Entity defender, int rounds) {
        for (int i = 1; i <= rounds; i++) {
            // Attacker's turn
            int damage = attacker.getAttackPower();
            attacker.attack(defender);
            System.out.printf("Round %d: %s attacks for %d damage | %s HP: %d/%d\n", 
                    i, attacker.getName(), damage, defender.getName(), 
                    defender.getCurrentHealth(), defender.getMaxHealth());
            
            if (!defender.isAlive()) {
                System.out.println(defender.getName() + " has been defeated!");
                break;
            }
            
            // Defender's turn
            damage = defender.getAttackPower();
            defender.attack(attacker);
            System.out.printf("Round %d: %s attacks for %d damage | %s HP: %d/%d\n", 
                    i, defender.getName(), damage, attacker.getName(), 
                    attacker.getCurrentHealth(), attacker.getMaxHealth());
            
            if (!attacker.isAlive()) {
                System.out.println(attacker.getName() + " has been defeated!");
                break;
            }
        }
    }
    
    /**
     * Helper method to print entity details
     */
    private void printEntityDetails(Entity entity) {
        System.out.printf("Created entity: %s (HP: %d/%d, Attack: %d)\n", 
                entity.getName(), entity.getCurrentHealth(), 
                entity.getMaxHealth(), entity.getAttackPower());
    }
    
    /**
     * Helper method to add random items to an entity
     */
    private void addItemsToEntity(Entity entity, int count) {
        for (int i = 0; i < count; i++) {
            Item item = data.getRandomItem();
            entity.addItem(item);
            System.out.println("Added item: " + item.getItemName());
        }
    }
}