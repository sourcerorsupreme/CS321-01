/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import java.util.Scanner;
import java.util.Random;    

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class Battle handles the logic of the game; It will do so by managing the turns of players and entities
 * Player turns end once an action has been selected
 * Entity turns are randomly selected by this class
 * @author tommy
 */
public class Battle {
    

    private final Scanner scanner = new Scanner(System.in);
    private Entity player;
    private Entity enemy;
    private BattleView battleView;
    
    boolean turn = true;
    
    /**
     * Battle Logic
     * Create logic the battle follows
     * create turns
     * Set up as simple text in console, for idea of the flow of the battle,
     * will transform logic onto a window and add GUI elements and create more depth to choices and enemy
     
     * @param player
     * @param enemy
     */
    
//    public void battleEncounter(){
        public Battle(Entity player, Entity enemy){
            this.player = player;
            this.enemy = enemy;
            this.battleView = new BattleView(player.getMaxHealth(), enemy.getMaxHealth(), player, enemy);
        
        
        //startBattle();
        }
        
        private void startBattle() {
        while (player.isAlive()) {
            // Loop while enemy is alive
            while (enemy.isAlive()) {
                System.out.println("\nEnter the action you would like to take.");
                System.out.print("Attack\nHeal\nInventory\n> ");
                String choice = scanner.nextLine().toLowerCase();

                switch (choice) {
                    case "attack":
                        player.attack(enemy);
                        battleView.setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
                        break;
                    case "heal":
                        player.heal(10); // You can change this logic to use potions later
                        battleView.setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                        break;
                    case "inventory":
                        player.showInventory();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter Attack, Heal, or Inventory.");
                        continue;
                }

                if (enemy.isAlive()) {
                    enemyTurn(enemy, player);
                    battleView.setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                }
            }

            System.out.println("\n" + enemy.getName() + " has been defeated!");
            return;
        }

        System.out.println("You have died.");
    }

    private void enemyTurn(Entity enemy, Entity player) {
        double healthPercent = (double) enemy.getCurrentHealth() / enemy.getMaxHealth();

        if (healthPercent >= 0.80) {
            System.out.println(enemy.getName() + " attacks!");
            enemy.attack(player);
        } else if (healthPercent >= 0.30) {
            Random rand = new Random();
            int actionRoll = rand.nextInt(100); // 0–99

            if (actionRoll < 70) {
                System.out.println(enemy.getName() + " attacks!");
                enemy.attack(player);
            } else {
                if (attemptHeal(enemy)) {
                    System.out.println(enemy.getName() + " uses a potion to heal!");
                    enemy.heal(10);
                } else {
                    System.out.println(enemy.getName() + " tries to heal but has no potions. Attacking!");
                    enemy.attack(player);
                }
            }
        } else {
            if (attemptHeal(enemy)) {
                System.out.println(enemy.getName() + " desperately uses a potion!");
                enemy.heal(10);
            } else {
                System.out.println(enemy.getName() + " is low on health but keeps fighting!");
                enemy.attack(player);
            }

        }
    }

    private boolean attemptHeal(Entity enemy) {
        if (enemy.getCurrentHealth()> 5) {
            System.out.println(enemy.getName() + "healed");
            return true;
        }else
            return false;
            
    }
//        List<Item> inventory = enemy.getInventory();
//        for (int i = 0; i < inventory.size(); i++) {
//            Item item = inventory.get(i);
//            if (item != null && item.getTag().equalsIgnoreCase("potion")) {
//                inventory.remove(i); // consume potion
//                return true;
//            }
//        }
//        return false;


class BattleView{
    /**
    * BattleView handles rendering the view as well as all changes that will be made to it
    */
    private JFrame frame = new JFrame("Last Stand");
    private JPanel panel = new JPanel();
  
    private JLabel turn = new JLabel("");
    private JLabel playerHP = new JLabel("");
    private JLabel enemyHP = new JLabel("");
    private JLabel playerSprite = new JLabel();
    private JLabel enemySprite = enemySprite = new JLabel();

    private JButton attack = new JButton("ATTACK");
    private JButton heal = new JButton("HEAL");
    private JButton useItem = new JButton("USE ITEM");
    private JButton surrender = new JButton("SURRENDER");

    private Entity player;
    private Entity enemy;
    private static final int SPRITE_WIDTH = 250;
    private static final int SPRITE_HEIGHT = 250;
 

    // Renders Initial Frame
    // TODO: Supply Item List    
    public BattleView(int maxPlayerHP, int maxEnemyHP, Entity player, Entity enemy){
        this.player = player;
        this.enemy = enemy;
        panel.setLayout(null);

        
        // Load and scale player sprite
        ImageIcon playerIcon = new ImageIcon(getClass().getResource("/assets/player1.png"));
        Image scaledPlayerImage = playerIcon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
        playerSprite.setIcon(new ImageIcon(scaledPlayerImage));
        playerSprite.setBounds(200, 100, SPRITE_WIDTH, SPRITE_HEIGHT);

        // Load and scale enemy sprite
        ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/assets/enemy.png"));
        Image scaledEnemyImage = enemyIcon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
        enemySprite.setIcon(new ImageIcon(scaledEnemyImage));
        enemySprite.setBounds(900, 100, SPRITE_WIDTH, SPRITE_HEIGHT);

        // Add them to the panel
        panel.add(playerSprite);
        panel.add(enemySprite);
        panel.revalidate();
        panel.repaint();

        
        setTurn(true);

        setPlayerHP(maxPlayerHP, maxPlayerHP);
        setEnemyHP(maxEnemyHP,maxEnemyHP);
        //
        setTurnStyle();
        setHpStyle();
        setHelpStyle();
        displayActionListButtons();
        boxLabels();
        actionDescriberOK();
        textBoxOK();
        backgroundDecorations();
        setActionListButtonStyle();
        actionListListeners();
        panel.setBackground(Color.BLACK);
        //

        //removeActionListButtons();
        frame.add(panel);
        frame.setSize(1280,760);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // Remove Action List(){
    public void removeActionListButtons(){
        panel.remove(attack);
        panel.remove(heal);
        panel.remove(useItem);
        panel.remove(surrender);
        panel.revalidate();
        panel.repaint();  
    }
    // Display Action List
    public void displayActionListButtons(){
        panel.add(attack);
        panel.add(heal);
        panel.add(useItem);
        panel.add(surrender);
    }
    // Set Enemy HP
    public void setEnemyHP(int maxHP, int currentHP){
        String text = "ENEMY HP: " + currentHP + "/" + maxHP;

        enemyHP.setText(text);
    }
    // Set Player HP
    public void setPlayerHP(int maxHP, int currentHP){
        String text = "YOUR HP: " + currentHP + "/" + maxHP;
        playerHP.setText(text);
    }
    // Turn indicator in top left corner
    public void setTurn(boolean turn){
        if (!turn){
            this.turn.setText(" It's Your Enemies Turn!");
        }
        else{
            this.turn.setText(" It's Your Turn!");
        }
    }
    // Action Listener
    public void actionListListeners(){
        attack.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("ATTACK was clicked!");
            player.attack(enemy);
            setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());

            }
        });
        
        heal.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("HEAL was clicked!");
            player.heal(10);
            setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());

            }
        });
        
        useItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("USE ITEM was clicked!");
            removeActionListButtons();
            }
        });
    }
    // Functions that will only be called once
    public void textBoxOK(){
        JButton ok2 = new JButton("OK");
        ok2.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        ok2.setBackground(Color.darkGray);
        ok2.setForeground(Color.white);
        ok2.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        ok2.setBounds(1181,660,60,40);
        panel.add(ok2);
    }
    public void actionDescriberOK(){
        JButton ok1 = new JButton("OK");
        ok1.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        ok1.setBackground(Color.darkGray);
        ok1.setForeground(Color.white);
        ok1.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        ok1.setBounds(765,660,60,40);
        panel.add(ok1);

    }
    public void setActionListButtonStyle(){
        attack.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        attack.setBackground(Color.darkGray);
        attack.setForeground(Color.white);
        attack.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        attack.setBounds(30,455,360,55);
        
        heal.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        heal.setBackground(Color.darkGray);
        heal.setForeground(Color.white);
        heal.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        heal.setBounds(30,515,360,55);
        
        useItem.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        useItem.setBackground(Color.darkGray);
        useItem.setForeground(Color.white);
        useItem.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        useItem.setBounds(30,575,360,55);
        
        surrender.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        surrender.setBackground(Color.darkGray);
        surrender.setForeground(Color.white);
        surrender.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        surrender.setBounds(30,635,360,55);
    }
    public void setHelpStyle(){
        JButton helpButton = new JButton("Help!");
        helpButton.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        helpButton.setBackground(Color.darkGray);
        helpButton.setForeground(Color.white);
        helpButton.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        helpButton.setBounds(1150,10,100,55);
        panel.add(helpButton);
    }
    public void setTurnStyle(){
        turn.setFont(new java.awt.Font("Arial", Font.BOLD, 22));
        turn.setOpaque(true);
        turn.setBackground(new Color(255,242,218));
        turn.setBorder(BorderFactory.createLineBorder(new Color(255,189,71), 5, true));
        turn.setBounds(10,10,175,55);
        panel.add(turn);

    }
    public void setHpStyle(){
        // Player HP
        playerHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        playerHP.setForeground(Color.white);
        playerHP.setBounds(530,370,250,30);
        panel.add(playerHP);
        // Enemy HP
        enemyHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        enemyHP.setForeground(Color.white);
        enemyHP.setBounds(525,50,250,30);
        panel.add(enemyHP);
    }
    public void boxLabels(){
        JLabel actionList = new JLabel("  Action list");
        actionList.setOpaque(true);
        actionList.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionList.setBackground(Color.black);
        actionList.setForeground(Color.LIGHT_GRAY);
        actionList.setBounds(20,415,200,30);
        panel.add(actionList);
        
        JLabel actionDescriber = new JLabel("  Action Describer");
        actionDescriber.setOpaque(true);
        actionDescriber.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionDescriber.setBackground(Color.black);
        actionDescriber.setForeground(Color.LIGHT_GRAY);
        actionDescriber.setBounds(440,415,250,30);
        panel.add(actionDescriber);
        
        JLabel textBox = new JLabel("  Text Box");
        textBox.setOpaque(true);
        textBox.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        textBox.setBackground(Color.black);
        textBox.setForeground(Color.LIGHT_GRAY);
        textBox.setBounds(870,415,200,30);
        panel.add(textBox);
    } 
    public void backgroundDecorations(){
        // action list background
        JLabel decoration1 = new JLabel(" ");
        decoration1.setOpaque(true);
        decoration1.setBackground(Color.darkGray);
        decoration1.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration1.setBounds(10,430,406,285);
        
        panel.add(decoration1);
        // action description background
        JLabel decoration2 = new JLabel(" ");
        decoration2.setOpaque(true);
        decoration2.setBackground(Color.darkGray);
        decoration2.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration2.setBounds(426,430,416,285);
        
        panel.add(decoration2);
        
        // 
        JLabel decoration3 = new JLabel(" ");
        decoration3.setOpaque(true);
        decoration3.setBackground(Color.darkGray);
        decoration3.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration3.setBounds(852,430,406,285);
        panel.add(decoration3);
        }
    }
}