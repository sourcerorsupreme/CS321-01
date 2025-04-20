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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
/**
 * Class Battle handles the logic of the game; It will do so by managing the turns of players and entities
 * Player turns end once an action has been selected
 * Entity turns are randomly selected by this class
 * @author tommy
 */
public class Battle {
    
    private Entity player;
    private Entity enemy;
    private BattleView battleView;
    private GameData data = new GameData("data.json");
    private boolean turn;
    
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
            this.turn = battleView.getIsPlayerReady();
            player.addItem(data.getRandomItem());
            startBattle();
            
        }
        
        private void startBattle() {
            while (player.isAlive()) {
                // Loop while enemy is alive
                while (enemy.isAlive()) {

                    // Wait till user clicks TextBoxOK
                    while (!turn){
                        turn = battleView.getIsPlayerReady();
                        delay();
                    }

                    if (enemy.isAlive()) {
                        enemyTurn(enemy, player);
                        battleView.setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                    }
                    turn = false;
                    battleView.setTurn(true);
                    //battleView.setIsPlayerTurn(turn);
                    battleView.setIsPlayerReady(turn);
                }
                
                String defeatText = "\n" + enemy.getName() + " has been defeated!";
                battleView.setEnemyActionLabel(defeatText);
                //System.out.println("\n" + enemy.getName() + " has been defeated!");
                newEnemy();
                //return;
            }

            System.out.println("You have died.");
    }
    private void newEnemy(){
        battleView.setActionDescriberLabel("A new enemy has appeared.");
        enemy = data.getRandomEntity();
        battleView.setEnemy(enemy);
        battleView.setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
        player.addToInventory(data.getRandomItem());
        player.addToInventory(data.getRandomItem());
    }
    private void enemyTurn(Entity enemy, Entity player) {
        double healthPercent = (double) enemy.getCurrentHealth() / enemy.getMaxHealth();

        if (healthPercent >= 0.80) {
            battleView.setEnemyActionLabel(enemy.getName() + " attacks!");
//            System.out.println(enemy.getName() + " attacks!");
            enemy.attack(player);
        } else if (healthPercent >= 0.30) {
            Random rand = new Random();
            int actionRoll = rand.nextInt(100); // 0–99

            if (actionRoll < 70) {
                battleView.setEnemyActionLabel(enemy.getName() + " attacks!");
//                System.out.println(enemy.getName() + " attacks!");
                enemy.attack(player);
            } else {
                if (attemptHeal(enemy)) {
                    battleView.setEnemyActionLabel(enemy.getName() + " uses a potion to heal!");
                    //System.out.println(enemy.getName() + " uses a potion to heal!");
                    enemy.heal(10);
                } else {
                    battleView.setEnemyActionLabel(enemy.getName() + " tries to heal but has no potions. Attacking!");
                    // System.out.println(enemy.getName() + " tries to heal but has no potions. Attacking!");
                    enemy.attack(player);
                }
            }
        } else {
            if (attemptHeal(enemy)) {
                battleView.setEnemyActionLabel(enemy.getName() + " desperately uses a potion!");
                //System.out.println(enemy.getName() + " desperately uses a potion!");
                enemy.heal(10);
            } else {
                battleView.setEnemyActionLabel(enemy.getName() + " is low on health but keeps fighting!");
                //System.out.println(enemy.getName() + " is low on health but keeps fighting!");
                enemy.attack(player);
            }

        }
    }
    private void delay(){
         try {
             Thread.sleep(50);
         } 
         catch (InterruptedException e) {
                 e.printStackTrace();
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

    private JLabel enemyActionLabel = new JLabel("");
    private JLabel actionDescriberLabel = new JLabel("");
    
    private JButton attack = new JButton("ATTACK");
    private JButton heal = new JButton("HEAL");
    private JButton useItem = new JButton("USE ITEM");
    private JButton surrender = new JButton("SURRENDER");

    private JButton textBoxOK = new JButton("OK");
    private JButton actionDescriberOK = new JButton("BACK");
    private Entity player;
    private Entity enemy;
    
    private JScrollPane itemPanel = new JScrollPane();
    private boolean isPlayerTurn = true;
    private boolean isPlayerReady = false;
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
        enemyActionLabelStyle();
        actionDescriberLabelStyle();
        boxLabels();
        actionDescriberOK();
        textBoxOK();
        backgroundDecorations();
        setActionListButtonStyle();
        actionListListeners();
        panel.setBackground(Color.BLACK);
        panel.setOpaque(true);
        //
        setItemDisplayStyle();
        
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
    public void setEnemy(Entity entity){
        enemy = entity;
    }
    public void displayActionListButtons(){
        panel.add(attack);
        panel.add(heal);
        panel.add(useItem);
        panel.add(surrender);
        panel.setComponentZOrder(attack,0);
        panel.setComponentZOrder(heal,0);
        panel.setComponentZOrder(useItem,0);
        panel.setComponentZOrder(surrender,0);
        panel.revalidate();
        panel.repaint();
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
    public void setEnemyActionLabel(String text){
        enemyActionLabel.setText(text);
    }
    public void setIsPlayerTurn(boolean turn){
        isPlayerTurn = turn;
    }
    public void setIsPlayerReady(boolean turn){
        isPlayerReady = turn;
    }
    public boolean getIsPlayerReady(){
        return isPlayerReady;
    }
    private void setActionDescriberLabel(String text){
        actionDescriberLabel.setText(text);
    }
    private void addItemsToLabel(){
        itemPanel.setVisible(true);
        List<Item> inventory = player.getInventory();
        JPanel container = new JPanel();
        container.setBackground(Color.darkGray);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(true);
        
        for (Item item: inventory){
            JButton button = new JButton(item.getItemName());
            button.setFont(new java.awt.Font("Arial",Font.BOLD,25));
            button.setBackground(Color.darkGray);
            button.setForeground(Color.white);
            button.setOpaque(true);
            button.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e){
                    setActionDescriberLabel(item.getItemDescription());
                }
                @Override
                public void mouseExited(MouseEvent e){
                    setActionDescriberLabel("");
                }
             });
            button.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if (isPlayerTurn){
                             if (item.getItemType() == Item.itemEffect.Heal){
                                item.useItem(player);
                                setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                             }
                             else if (item.getItemType() == Item.itemEffect.Damage)
                                item.useItem(enemy);
                                setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
                        }
                        player.removeItem(item);
                        setTurn(false);
                        isPlayerTurn=false;
                        container.removeAll();
                        itemPanel.setVisible(false);
                        displayActionListButtons();
                        panel.revalidate();
                        panel.repaint();

                    }
                 });
            container.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical spacing
            container.add(button);
        }
        container.add(Box.createVerticalGlue());
        itemPanel.setViewportView(container);
        itemPanel.revalidate();
        itemPanel.repaint();
    }
    // Action Listener
    public void actionListListeners(){
        actionDescriberOK.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               if(itemPanel.isVisible()){
                   itemPanel.setVisible(false);
                   displayActionListButtons();
                   panel.revalidate();
                   panel.repaint();
                }
           }
        });
        textBoxOK.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               if (!isPlayerTurn){
                    System.out.println("TextBoxOK Clicked");
                    isPlayerReady=true;
                    isPlayerTurn=true;
               }
               
           }
        });
        // Hover Actio
        attack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                setActionDescriberLabel("Deal " + player.getAttackPower() + " damage to the enemy.");
            }
            @Override
            public void mouseExited(MouseEvent e){
                setActionDescriberLabel("");
            }
        });
        heal.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                setActionDescriberLabel("Heal yourself for 10 HP.");
            }
            @Override
            public void mouseExited(MouseEvent e){
                setActionDescriberLabel("");
            }
        });
        useItem.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseEntered(MouseEvent e){
               setActionDescriberLabel("Select an item to consume.");
           }
           @Override
           public void mouseExited(MouseEvent e){
               setActionDescriberLabel("");
           }
        });
        // Click Action
        attack.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPlayerTurn){
                System.out.println("ATTACK was clicked!");
                player.attack(enemy);
                setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
                setTurn(false);
                isPlayerTurn=false;
                }
            
            }
        });
        
        heal.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPlayerTurn){
                System.out.println("HEAL was clicked!");
                player.heal(10);
                setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                setTurn(false);
                isPlayerTurn = false;
            }
            
            }
        });
        
        useItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                if (isPlayerTurn){
                    System.out.println("USE ITEM was clicked!");
                    removeActionListButtons();
                    addItemsToLabel();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
    }
    // Functions that will only be called once
    public void setItemDisplayStyle(){
        itemPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemPanel.setBounds(30,455,360,235);
        itemPanel.getViewport().setBackground(Color.darkGray);
        itemPanel.setForeground(Color.white);
        itemPanel.setOpaque(true);
        itemPanel.setVisible(false);
        itemPanel.getViewport().setOpaque(true);
        frame.add(itemPanel);
    }
    public void actionDescriberLabelStyle(){
        actionDescriberLabel.setFont(new java.awt.Font("Arial",Font.BOLD,15));
        actionDescriberLabel.setBackground(Color.darkGray);
        actionDescriberLabel.setForeground(Color.white);
        actionDescriberLabel.setBounds(450,455,360,55);
        panel.add(actionDescriberLabel);
    }
    public void enemyActionLabelStyle(){
        enemyActionLabel.setFont(new java.awt.Font("Arial",Font.BOLD,15));
        enemyActionLabel.setBackground(Color.darkGray);
        enemyActionLabel.setForeground(Color.white);
        enemyActionLabel.setBounds(875,455,360,55);
        panel.add(enemyActionLabel);
    }
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
        actionDescriberOK.setBounds(745,660,80,40);
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
        
        JLabel textBox = new JLabel("  Enemy Action");
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
