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
import java.awt.event.KeyEvent;
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
    private JFrame frame;
    
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
        public Battle(Entity player, Entity enemy, JFrame frame){
            this.player = player;
            this.enemy = enemy;
            this.frame = frame;
            this.battleView = new BattleView(player.getMaxHealth(), enemy.getMaxHealth(), player, enemy, frame);
            this.turn = battleView.getIsPlayerReady(); 
            
            frame.setContentPane(battleView);
            frame.revalidate();
            frame.repaint();
            // Start the loop in the background
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    startBattle(); // runs in a background thread
                    return null;
                }
            };
            worker.execute();
            
        }
        
        private void startBattle() {
            while (player.isAlive()) {
                // Loop while enemy is alive
                while (enemy.isAlive()) {
                    
                    System.out.println(player.isAlive());

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
                    
                    // Fallen in battle.
                    if (!player.isAlive())
                    {
                        handlePlayerDeath();
                    }
                }
                
                String defeatText = "\n" + enemy.getName() + " has been defeated!";
                battleView.setEnemyActionLabel(defeatText);
                delay();
                newEnemy();
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
        player.increaseStats();
    }
    private void enemyTurn(Entity enemy, Entity player) {
        double healthPercent = (double) enemy.getCurrentHealth() / enemy.getMaxHealth();

        if (healthPercent >= 0.80) {
            battleView.setEnemyActionLabel(enemy.getName() + " attacks!");
//            System.out.println(enemy.getName() + " attacks!");
            enemy.attack(player);
            battleView.flashPlayerSprite(battleView.playerBlockImg, 1500);
        } else if (healthPercent >= 0.30) {
            Random rand = new Random();
            int actionRoll = rand.nextInt(100); // 0–99

            if (actionRoll < 70) {
                battleView.setEnemyActionLabel(enemy.getName() + " attacks!");
//                System.out.println(enemy.getName() + " attacks!");
                enemy.attack(player);
                battleView.flashPlayerSprite(battleView.playerBlockImg, 1500);
            } else {
                if (attemptHeal(enemy)) {
                    battleView.setEnemyActionLabel(enemy.getName() + " uses a potion to heal!");
                    //System.out.println(enemy.getName() + " uses a potion to heal!");
                    enemy.heal(10);
                } else {
                    battleView.setEnemyActionLabel(enemy.getName() + " tries to heal but has no potions. Attacking!");
                    // System.out.println(enemy.getName() + " tries to heal but has no potions. Attacking!");
                    enemy.attack(player);
                    battleView.flashPlayerSprite(battleView.playerBlockImg, 1500);
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
                battleView.flashPlayerSprite(battleView.playerBlockImg, 1500);
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
    
    private void handlePlayerDeath() {
        // Disable battle controls while showing the dialog
        battleView.removeActionListButtons();

        // Set message about player's defeat
        battleView.setActionDescriberLabel(player.getName() + " has been defeated!");

        // Show the death confirmation dialog
        battleView.showDeathConfirmation();

        // If we get here, player chose to continue (handled in the UI)
        // Restore player health to continue
        player.heal(player.getMaxHealth()); // Fully restore health
        battleView.setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());

        // Reset battle state
        battleView.setTurn(true);
        battleView.setIsPlayerReady(false);
        battleView.setIsPlayerTurn(true);

        // Show action buttons again
        battleView.displayActionListButtons();
    }
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


class BattleView extends JPanel{
    /**
    * BattleView handles rendering the view as well as all changes that will be made to it
    */
    private JFrame frame;
  
    private JLabel turn = new JLabel("");
    private JLabel playerHP = new JLabel("");
    private JLabel enemyHP = new JLabel("");
    private JLabel playerSprite = new JLabel();
    private JLabel enemySprite = enemySprite = new JLabel();

//    private JLabel enemyActionLabel = new JLabel("");
    private JTextArea enemyActionLabel = new JTextArea("");
    private JLabel actionDescriberLabel = new JLabel("");
    
    private JButton attack = new JButton("ATTACK");
    private JButton heal = new JButton("HEAL");
    private JButton useItem = new JButton("USE ITEM");
    private JButton surrender = new JButton("SURRENDER");
    private JButton helpButton = new JButton("HELP!");
    
    private JButton textBoxOK = new JButton("OK");
    private JButton actionDescriberOK = new JButton("BACK");
    private Entity player;
    private Entity enemy;
    
    private JScrollPane itemPanel = new JScrollPane();
    private boolean isPlayerTurn = true;
    private boolean isPlayerReady = false;
    private static final int SPRITE_WIDTH = 320;
    private static final int SPRITE_HEIGHT = 320;
    private Player playerAnimator;
    
    public  String playerAttackImg = "/assets/player/playerAttack.png";
    public  String playerBlockImg = "/assets/player/playerBlock.png";
    
 
    private JPanel escapePanel = new JPanel();

    // Renders Initial Frame
    // TODO: Supply Item List    
    public BattleView(int maxPlayerHP, int maxEnemyHP, Entity player, Entity enemy, JFrame frame){
        this.player = player;
        this.enemy = enemy;
        this.frame = frame;
        setLayout(null);
        setBackground(Color.BLACK);

        
        // Load and scale player sprite
        playerSprite.setBounds(200, 100, SPRITE_WIDTH, SPRITE_HEIGHT);
        
         String[] playerPaths = {
        "/assets/player/player1.png",
        "/assets/player/player2.png",
        "/assets/player/player3.png",
        "/assets/player/player4.png",
        "/assets/player/player5.png",
        "/assets/player/player6.png",
        "/assets/player/player7.png"
    };
         
         playerAnimator = new Player(
            playerSprite, playerPaths,
            SPRITE_WIDTH, SPRITE_HEIGHT,
            450      
         );
         playerAnimator.start();

        // Load and scale enemy sprite

        ImageIcon enemyIcon = new ImageIcon(getClass().getResource("/assets/enemy1.png"));
        Image scaledEnemyImage = enemyIcon.getImage().getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
        enemySprite.setIcon(new ImageIcon(scaledEnemyImage));
        enemySprite.setBounds(900, 100, SPRITE_WIDTH, SPRITE_HEIGHT);

        // Add them to the panel
        add(playerSprite);
        add(enemySprite);
        revalidate();
        repaint();

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
        escapeLabelStyle();
        escapeKeyListener();
        setBackground(Color.BLACK);
        setOpaque(true);
        //
        setItemDisplayStyle();
        
        //removeActionListButtons();
        frame.setSize(1280,760);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void flashPlayerSprite(String resourcePath, int displayMs) {
    //stop the idle animation
    playerAnimator.stop();

    //load & scale the one-off icon
    ImageIcon ico = new ImageIcon(getClass().getResource(resourcePath));
    Image scaled = ico.getImage()
                     .getScaledInstance(SPRITE_WIDTH, SPRITE_HEIGHT, Image.SCALE_SMOOTH);
    playerSprite.setIcon(new ImageIcon(scaled));

    //after milliseconds, revert to the idle animation
    Timer t = new Timer(displayMs, e -> {
        ((Timer)e.getSource()).stop();
        playerAnimator.start();
    });
    t.setRepeats(false);
    t.start();
}
    // Remove Action List(){
    public void removeActionListButtons(){
        remove(attack);
        remove(heal);
        remove(useItem);
        remove(surrender);
        revalidate();
        repaint();  
    }
    // Display Action List
    public void setEnemy(Entity entity){
        enemy = entity;
    }
    public void displayActionListButtons(){
        add(attack);
        add(heal);
        add(useItem);
        add(surrender);
        setComponentZOrder(attack,0);
        setComponentZOrder(heal,0);
        setComponentZOrder(useItem,0);
        setComponentZOrder(surrender,0);
        revalidate();
        repaint();
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
    public void setActionDescriberLabel(String text){
        actionDescriberLabel.setText(text);
    }
    private void addItemsToLabel(){
        itemPanel.setVisible(true);
        List<Item> inventory = player.getInventory();
//        System.out.println(player.getInventory()[0]);
        JPanel container = new JPanel();
        container.setBackground(Color.darkGray);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(true);
        
        for (Item item: inventory){
            System.out.println(item.getItemName());
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
                             if (item.getItemEffect() == Item.itemEffect.Heal){
                                item.useItem(player);
                                setPlayerHP(player.getMaxHealth(), player.getCurrentHealth());
                             }
                             else if (item.getItemEffect() == Item.itemEffect.Damage)
                                item.useItem(enemy);
                                setEnemyHP(enemy.getMaxHealth(), enemy.getCurrentHealth());
                        }
                        player.removeItem(item);
                        setTurn(false);
                        isPlayerTurn=false;
                        container.removeAll();
                        itemPanel.setVisible(false);
                        displayActionListButtons();
                        container.revalidate();
                        container.repaint();

                    }
                 });
            container.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical spacing
            container.add(button);
        }
        container.add(Box.createVerticalGlue());
        itemPanel.setViewportView(container);
        itemPanel.revalidate();
        itemPanel.repaint();
        setComponentZOrder(itemPanel, 0);
    }
    public void escapeLabelStyle(){
        escapePanel.setLayout(null);
        escapePanel.setBackground(Color.DARK_GRAY);
        escapePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        escapePanel.setBounds(400, 250, 480, 200);
        escapePanel.setOpaque(true);

        JButton save = new JButton("SAVE");
        save.setFont(new Font("Arial", Font.BOLD, 18));
        save.setBackground(Color.BLACK);
        save.setForeground(Color.WHITE);
        save.setBounds(80, 100, 120, 40);
        escapePanel.add(save);

        JButton exit = new JButton("EXIT");
        exit.setFont(new Font("Arial", Font.BOLD, 18));
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.WHITE);
        exit.setBounds(260, 100, 120, 40);
        escapePanel.add(exit);
        escapePanel.setVisible(false);
        add(escapePanel);
        setComponentZOrder(escapePanel, 0); // bring to front
        repaint();
        revalidate();
        
        save.addActionListener(e -> {
            int saveAmount = GameData.countJsonFiles() + 1;
            String saveName = "save" + saveAmount + ".json";
            GameData.saveGame(player, saveName);
            System.exit(0);
        });
        exit.addActionListener(e -> System.exit(0));
    }
    // Action Listener
    public void escapeKeyListener(){
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
            "escape"
        );
    
        getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escapePanel.setVisible(!escapePanel.isVisible());
            }
        });
}
    public void actionListListeners(){
        actionDescriberOK.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               if(itemPanel.isVisible()){
                   itemPanel.setVisible(false);
                   displayActionListButtons();
                   revalidate();
                   repaint();
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
        // Hover Action
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
        surrender.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                setActionDescriberLabel("Lay down your sword and end your fight?");
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
                flashPlayerSprite(playerAttackImg, 750);
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
        
        // Action listener: If user clicks surrender button.
        surrender.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showSurrenderConfirmation();
        }
        });
        
        // Action listener If user clicks help button
        helpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                showHelp();
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
        add(itemPanel);

    }
    public void actionDescriberLabelStyle(){
        actionDescriberLabel.setFont(new java.awt.Font("Arial",Font.BOLD,15));
        actionDescriberLabel.setBackground(Color.darkGray);
        actionDescriberLabel.setForeground(Color.white);
        actionDescriberLabel.setBounds(450,455,360,55);
        add(actionDescriberLabel);
    }
    public void enemyActionLabelStyle(){
        enemyActionLabel.setFont(new java.awt.Font("Arial",Font.BOLD,15));
        enemyActionLabel.setBackground(Color.darkGray);
        enemyActionLabel.setForeground(Color.white);
        enemyActionLabel.setBounds(875,455,360,55);
        enemyActionLabel.setLineWrap(true);
        enemyActionLabel.setWrapStyleWord(true);
        add(enemyActionLabel);
    }
    public void textBoxOK(){
        textBoxOK.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        textBoxOK.setBackground(Color.darkGray);
        textBoxOK.setForeground(Color.white);
        textBoxOK.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        textBoxOK.setBounds(1181,660,60,40);
        add(textBoxOK);
    }
    public void actionDescriberOK(){
        actionDescriberOK.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        actionDescriberOK.setBackground(Color.darkGray);
        actionDescriberOK.setForeground(Color.white);
        actionDescriberOK.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        actionDescriberOK.setBounds(745,660,80,40);
        add(actionDescriberOK);

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
        helpButton.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        helpButton.setBackground(Color.darkGray);
        helpButton.setForeground(Color.white);
        helpButton.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        helpButton.setBounds(1150,10,100,55);
        add(helpButton);
    }
    public void setTurnStyle(){
        turn.setFont(new java.awt.Font("Arial", Font.BOLD, 22));
        turn.setOpaque(true);
        turn.setBackground(new Color(255,242,218));
        turn.setBorder(BorderFactory.createLineBorder(new Color(255,189,71), 5, true));
        turn.setBounds(10,10,175,55);
        add(turn);

    }
    public void setHpStyle(){
        // Player HP
        playerHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        playerHP.setForeground(Color.white);
        playerHP.setBounds(530,370,250,30);
        add(playerHP);
        // Enemy HP
        enemyHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        enemyHP.setForeground(Color.white);
        enemyHP.setBounds(525,50,250,30);
        add(enemyHP);
    }
    public void boxLabels(){
        JLabel actionList = new JLabel("  Action list");
        actionList.setOpaque(true);
        actionList.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionList.setBackground(Color.black);
        actionList.setForeground(Color.LIGHT_GRAY);
        actionList.setBounds(20,415,200,30);
        add(actionList);
        
        JLabel actionDescriber = new JLabel("  Action Describer");
        actionDescriber.setOpaque(true);
        actionDescriber.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionDescriber.setBackground(Color.black);
        actionDescriber.setForeground(Color.LIGHT_GRAY);
        actionDescriber.setBounds(440,415,250,30);
        add(actionDescriber);
        
        JLabel textBox = new JLabel("  Enemy Action");
        textBox.setOpaque(true);
        textBox.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        textBox.setBackground(Color.black);
        textBox.setForeground(Color.LIGHT_GRAY);
        textBox.setBounds(870,415,200,30);
        add(textBox);
    } 
    public void backgroundDecorations(){
        // action list background
        JLabel decoration1 = new JLabel(" ");
        decoration1.setOpaque(true);
        decoration1.setBackground(Color.darkGray);
        decoration1.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration1.setBounds(10,430,406,285);
        
        add(decoration1);
        // action description background
        JLabel decoration2 = new JLabel(" ");
        decoration2.setOpaque(true);
        decoration2.setBackground(Color.darkGray);
        decoration2.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration2.setBounds(426,430,416,285);
        
        add(decoration2);
        
        // 
        JLabel decoration3 = new JLabel(" ");
        decoration3.setOpaque(true);
        decoration3.setBackground(Color.darkGray);
        decoration3.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration3.setBounds(852,430,406,285);
        add(decoration3);
        }
    
    public void showSurrenderConfirmation(){
        JPanel popup = new JPanel();
        popup.setLayout(null);
        popup.setBackground(Color.DARK_GRAY);
        popup.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        popup.setBounds(400, 250, 480, 200);
        popup.setOpaque(true);

        JLabel confirmLabel = new JLabel("<html><div style='text-align: center;'>Are you sure you want to surrender?</div></html>", SwingConstants.CENTER);
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 18));
        confirmLabel.setBounds(40, 30, 400, 30);
        popup.add(confirmLabel);

        JButton yesButton = new JButton("YES");
        yesButton.setFont(new Font("Arial", Font.BOLD, 18));
        yesButton.setBackground(Color.BLACK);
        yesButton.setForeground(Color.WHITE);
        yesButton.setBounds(80, 100, 120, 40);
        popup.add(yesButton);

        JButton noButton = new JButton("NO");
        noButton.setFont(new Font("Arial", Font.BOLD, 18));
        noButton.setBackground(Color.BLACK);
        noButton.setForeground(Color.WHITE);
        noButton.setBounds(260, 100, 120, 40);
        popup.add(noButton);

        add(popup);
        setComponentZOrder(popup, 0); // bring to front
        repaint();
        revalidate();
        
        // YES Button Logic
        yesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                yesButton.setFont(new Font("Arial", Font.BOLD, 9));
                yesButton.setText("<HTML>Bring shame upon your house.<HTML>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                yesButton.setFont(new Font("Arial", Font.BOLD, 18));
                yesButton.setText("YES");
            }
        });
        
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActionDescriberLabel("<html><div style='width: 350px;'>You have surrendered and have been taken<br> prisoner.</div></html>");
                removeActionListButtons();
                remove(popup);
                repaint();

                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.exit(0);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        // NO button logic
        noButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noButton.setFont(new Font("Arial", Font.BOLD, 9));
                noButton.setText("<HTML>Live long and prosper!<HTML>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noButton.setFont(new Font("Arial", Font.BOLD, 18));
                noButton.setText("NO");
            }
        });
        
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(popup);
                repaint();
            }
        });
    }
    
    // Logic for when Help Button is pushed
    public void showHelp(){
        
        JPanel popup = new JPanel();
        popup.setLayout(null);
        popup.setBackground(Color.DARK_GRAY);
        popup.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        popup.setBounds(340, 100, 600, 350);
        popup.setOpaque(true);

        JLabel helpLabel = new JLabel("<html><div style='width: 500px;'>" +
            "<h2 style='color:white;'>How to Play</h2>" +
            "<p style='color:white;'>• Use the <b>ATTACK</b> button to damage enemies.<br>" +
            "• Use <b>HEAL</b> to restore your HP by 10 points.<br>" +
            "• Click <b>USE ITEM</b> to select from your inventory.<br>" +
            "• <b>SURRENDER</b> if you want to give up.<br>" +
            "• Press ESC if you wish to save or quit.<br>" +
            "• Enemies take their turn after yours — survive as long as possible.<br>" +
            "• New enemies appear after defeating one. Collect loot and keep fighting!<br>" +
                
            "</p></div></html>", SwingConstants.CENTER);
        
        helpLabel.setForeground(Color.WHITE);
        helpLabel.setFont(new Font("Arial", Font.BOLD, 16));
        helpLabel.setBounds(40, 20, 550, 250);
        popup.add(helpLabel);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBounds(240, 280, 120, 40);
        popup.add(closeButton);
        
        add(popup);
        setComponentZOrder(popup, 0);
        repaint();
        revalidate();
        
        closeButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            remove(popup);
            repaint();
        }
        });
    }

    public void showDeathConfirmation() {
        JPanel popup = new JPanel();
        popup.setLayout(null);
        popup.setBackground(Color.DARK_GRAY);
        popup.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4, true));
        popup.setBounds(400, 250, 480, 200);
        popup.setOpaque(true);

        JLabel confirmLabel = new JLabel("<html><div style='text-align: center;'>Continue to Struggle?</div></html>", SwingConstants.CENTER);
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 18));
        confirmLabel.setBounds(40, 30, 400, 30);
        popup.add(confirmLabel);

        JButton yesButton = new JButton("YES");
        yesButton.setFont(new Font("Arial", Font.BOLD, 18));
        yesButton.setBackground(Color.BLACK);
        yesButton.setForeground(Color.WHITE);
        yesButton.setBounds(80, 100, 120, 40);
        popup.add(yesButton);

        JButton noButton = new JButton("NO");
        noButton.setFont(new Font("Arial", Font.BOLD, 18));
        noButton.setBackground(Color.BLACK);
        noButton.setForeground(Color.WHITE);
        noButton.setBounds(260, 100, 120, 40);
        popup.add(noButton);

        add(popup);
        setComponentZOrder(popup, 0); // bring to front
        repaint();
        revalidate();

        // YES Button Logic - Continue playing
        yesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                yesButton.setFont(new Font("Arial", Font.BOLD, 9));
                yesButton.setText("<HTML>I can't fall now!<HTML>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                yesButton.setFont(new Font("Arial", Font.BOLD, 18));
                yesButton.setText("YES");
            }
        });

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Player chooses to continue
                setActionDescriberLabel("<html><div style='width: 350px;'>You struggle back to your feet, determined to fight on!</div></html>");
                remove(popup);
                repaint();
                // Player status will be reset in the handlePlayerDeath method
            }
        });

        // NO button logic - End game
        noButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noButton.setFont(new Font("Arial", Font.BOLD, 9));
                noButton.setText("<HTML>End this struggle.<HTML>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noButton.setFont(new Font("Arial", Font.BOLD, 18));
                noButton.setText("NO");
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActionDescriberLabel("<html><div style='width: 350px;'>I'm tired of the grind.</div></html>");
                removeActionListButtons();
                remove(popup);
                repaint();

                Timer timer = new Timer(50, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.exit(0);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}
