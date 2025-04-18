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
    
    Scanner scanner = new Scanner(System.in);
    /**
     * Battle Logic
     * Create logic the battle follows
     * create turns
     * Set up as simple text in console, for idea of the flow of the battle,
     * will transform logic onto a window and add GUI elements and create more depth to choices and enemy
     */
   
//    public void battleEncounter(){
        public Battle(){
        int maxPlayerHP = 100;
        int maxEnemyHP = 100;
        Entity enemy = new Entity("Enemy", 100, 10,0);
        Player player = new Player("You",100,10,0);
        
        BattleView battleView = new BattleView(maxPlayerHP, maxEnemyHP, player, enemy);
        boolean turn = battleView.getTurn();
        
         /**
          * Note: Create a boolean function to check entity health ?
          */
        while (player.isAlive()){
            //enemy = new Entity("Enemy", maxEnemyHP, 10, 5);
            System.out.println("enemy turn");
            while (enemy.isAlive()){
                // Wait till enemies turn to perform action
                while (turn){
                    turn = battleView.getTurn();
                    delay();
                }
                /**
                 * Enemy Logic
                 * Created basic attack logic until we discuss how we want the "ai" to function
                 */
                battleView.setTurn(true);
                turn = battleView.getTurn();
                battleView.setTurnLabel(true);
                
                delay();
                double healthPercent = (double) enemy.getCurrentHealth()/enemy.getMaxHealth();
            
                if (healthPercent >= 0.80) {
                    System.out.println(enemy.getName() + " attacks!");
                    enemy.attack(player);
                    battleView.setPlayerHP(maxPlayerHP, player.getCurrentHealth());
                } else if (healthPercent >= 0.30) {
                    Random rand = new Random();
                    int actionRoll = rand.nextInt(100); // 0–99

                    if (actionRoll < 70) {
                        System.out.println(enemy.getName() + " attacks!");
                        enemy.attack(player);
                        battleView.setPlayerHP(maxPlayerHP, player.getCurrentHealth());
                    } else {
                        if (attemptHeal(enemy)) {
                            System.out.println(enemy.getName() + " uses a potion to heal!");
                            enemy.heal(10); // adjust amount
                            battleView.setEnemyHP(maxEnemyHP, enemy.getCurrentHealth());
                        } else {
                            System.out.println(enemy.getName() + " tries to heal but has no potions. Attacking!");
                            enemy.attack(player);
                            battleView.setPlayerHP(maxPlayerHP, player.getCurrentHealth());
                        }
                    }
                } else {
                    // Health < 50%, maybe go full survival mode
                    if (attemptHeal(enemy)) {
                        System.out.println(enemy.getName() + " desperately uses a potion!");
                        enemy.heal(10);
                        battleView.setEnemyHP(maxEnemyHP, enemy.getCurrentHealth());
                    } else {
                        System.out.println(enemy.getName() + " is low on health but keeps fighting!");
                        enemy.attack(player);
                        battleView.setEnemyHP(maxEnemyHP, enemy.getCurrentHealth());
                    }
                }
                
            }
        }
    }

    private boolean attemptHeal(Entity enemy) {
        Item[] inventory = enemy.getInventory();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getTag().equalsIgnoreCase("potion")) {
                inventory[i] = null; // consume potion
                return true;
            }
        }
        return false;
    }
    private void delay(){
        try {
            Thread.sleep(50);
        } 
        catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}

class BattleView{
    /**
    * BattleView handles rendering the view as well as all changes that will be made to it
    */
    private JFrame frame = new JFrame("Last Stand");
    private JPanel panel = new JPanel();
    private JLabel turnLabel = new JLabel("");
    private JLabel playerHP = new JLabel("");
    private JLabel enemyHP = new JLabel("");
    private JButton attack = new JButton("ATTACK");
    private JButton heal = new JButton("HEAL");
    private JButton useItem = new JButton("USE ITEM");
    private JButton surrender = new JButton("SURRENDER");
    private JButton actionDescriberOK = new JButton("OK");
    private JButton textBoxOK = new JButton("OK");
    private Entity player;
    private Entity enemy;
    
    private boolean turn = true;
    // Renders Initial Frame
    // TODO: Supply Item List    
    public BattleView(int maxPlayerHP, int maxEnemyHP, Entity player, Entity enemy){
        this.player = player;
        this.enemy = enemy;
        panel.setLayout(null);
        setTurnLabel(true);
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

//        removeActionListButtons();
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
    public void setTurnLabel(boolean turn){
        if (!turn){
            this.turnLabel.setText(" It's Your Enemies Turn!");
        }
        else{
            this.turnLabel.setText(" It's Your Turn!");
        }
    }
    public void setTurn(boolean turn){
        this.turn = turn;
    }
    // Action Listener
    public void actionListListeners(){
        textBoxOK.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (turn){
                    System.out.println("TextBox OK was clicked.");
                    turn = false;
                    System.out.println(turn);
                }
            }
        });
        attack.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                System.out.println("ATTACK was clicked!");
                player.attack(enemy);
                setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
                setTurnLabel(false);
            }
        });
        
        heal.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("HEAL was clicked!");
                player.heal(10);
                setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                setTurnLabel(false);
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
    public boolean getTurn(){
        return turn;
    }
    // Functions that will only be called once
    public void textBoxOK(){
        textBoxOK.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        textBoxOK.setBackground(Color.darkGray);
        textBoxOK.setForeground(Color.white);
        textBoxOK.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        textBoxOK.setBounds(1181,660,60,40);
        panel.add(textBoxOK);
    }
    public void actionDescriberOK(){
        actionDescriberOK.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        actionDescriberOK.setBackground(Color.darkGray);
        actionDescriberOK.setForeground(Color.white);
        actionDescriberOK.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        actionDescriberOK.setBounds(765,660,60,40);
        panel.add(actionDescriberOK);
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
        turnLabel.setFont(new java.awt.Font("Arial", Font.BOLD, 22));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(new Color(255,242,218));
        turnLabel.setBorder(BorderFactory.createLineBorder(new Color(255,189,71), 5, true));
        turnLabel.setBounds(10,10,175,55);
        panel.add(turnLabel);
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
