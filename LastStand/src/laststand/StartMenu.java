/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.File;
        
/**
 *
 * @author tommy
 * 4/20/25: Class Created to make and load start menu.
 */
public class StartMenu extends JPanel{
    private JFrame frame;
    private final JPanel panel = new JPanel(null);
    
    //Animated Title
    private JLabel      titleLabel;
    private ImageIcon[] titleFrames;
    private int         currentFrame = 0;
    private Timer       animationTimer;

    // Menu Button 
    private final JLabel  titleLbl  = new JLabel("LAST STAND", SwingConstants.CENTER);
    private JButton newGameButton   = new JButton("New Game");
    private JButton loadButton      = new JButton("Load Save");
    private JButton quitButton      = new JButton("Quit");
    
    private JScrollPane pane = new JScrollPane();
    
    public StartMenu(JFrame frame){
        this.frame = frame;
        loadTitleImages();
        buildPanel();
        addListeners();
        startMenuTitleAnimation();
        //panel.add(pane);
        frame.setContentPane(panel);
        frame.pack();                  
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private void loadTitleImages(){
        String[] paths ={
            "/assets/title/lastStandTitle1.png",
            "/assets/title/lastStandTitle2.png",
            "/assets/title/lastStandTitle3.png",
            "/assets/title/lastStandTitle4.png",
            "/assets/title/lastStandTitle5.png",
            "/assets/title/lastStandTitle6.png",
            "/assets/title/lastStandTitle7.png",
            "/assets/title/lastStandTitle8.png",
            "/assets/title/lastStandTitle9.png",
            "/assets/title/lastStandTitle10.png",
            "/assets/title/lastStandTitle11.png",
            "/assets/title/lastStandTitle12.png",
            "/assets/title/lastStandTitle13.png",
            "/assets/title/lastStandTitle14.png",
            "/assets/title/lastStandTitle15.png",
            "/assets/title/lastStandTitle16.png",
            "/assets/title/lastStandTitle17.png",
            "/assets/title/lastStandTitle18.png",
            "/assets/title/lastStandTitle19.png",
            "/assets/title/lastStandTitle20.png",
            "/assets/title/lastStandTitle21.png",
            "/assets/title/lastStandTitle22.png",
            "/assets/title/lastStandTitle23.png",
            "/assets/title/lastStandTitle24.png"
        };
        int width = 960;
        int height = 370;
        
        titleFrames = new ImageIcon[paths.length];
        for (int i = 0; i < paths.length; i++){
            // 1) load original
            ImageIcon orig = new ImageIcon(getClass().getResource(paths[i]));
            // 2) Scale it to desired size
            Image scaled = orig.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // 3) Wrap back in an ImageIcon
            titleFrames[i] = new ImageIcon(scaled);
        }
    }
    
    
    private void buildPanel() {
        panel.setPreferredSize(new Dimension(1280, 760));
        panel.setBackground(Color.BLACK);

        // animated title label
        titleLabel = new JLabel(titleFrames[0], SwingConstants.CENTER);
        titleLabel.setBounds(0, 80, 1280, 370);
        panel.add(titleLabel);

        /* ----- Decorative frame like BattleView ----- */
        JLabel deco = new JLabel();
        deco.setOpaque(true);
        deco.setBackground(Color.DARK_GRAY);
        deco.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, true));
        deco.setBounds(410, 320, 460, 360);
        panel.add(deco);

        /* ----- Buttons ----- */
        int btnW = 420, btnH = 70, btnX = (1280 - btnW) / 2;
        styleButton(newGameButton, btnX, 350, btnW, btnH);
        styleButton(loadButton,    btnX, 450, btnW, btnH);
        styleButton(quitButton,    btnX, 550, btnW, btnH);

        // paint order: put buttons on top of grey box
        panel.setComponentZOrder(newGameButton, 0);
        panel.setComponentZOrder(loadButton,    0);
        panel.setComponentZOrder(quitButton,    0);
    }

    
    private void startMenuTitleAnimation(){
        animationTimer = new Timer(150, e ->{
            currentFrame = (currentFrame + 1) % titleFrames.length;
            titleLabel.setIcon(titleFrames[currentFrame]);
        });
        animationTimer.start();
    }
    
    private void styleButton(JButton b, int x, int y, int w, int h) {
        b.setFont(new Font("Arial", Font.BOLD, 32));
        b.setBackground(Color.DARK_GRAY);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5, true));
        b.setFocusPainted(false);
        b.setBounds(x, y, w, h);
        panel.add(b);
    }
    
    private void startGame(){
        Entity player = new Entity("Hero", 100, 15, 5);
        GameData data = new GameData("data.json");
        Entity enemy = data.getRandomEntity();
        player.addItem(data.getRandomItem());
        player.addItem(data.getRandomItem());
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        
        new Battle(player, enemy, frame);
    }
    
    private void startGame(Entity player){
        GameData data = new GameData("data.json");
        Entity enemy = data.getRandomEntity();
        
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        
        new Battle(player, enemy, frame);
    }
    private void addListeners(){
        newGameButton.addActionListener(e -> startGame());
        
        if (GameData.countJsonFiles() > 0){
            loadButton.addActionListener(e -> {
            displaySaves();
        });
        }
        

        quitButton.addActionListener(e -> System.exit(0));
    }
    
    private void displaySaves(){
        panel.removeAll();
        
        JScrollPane pane = new JScrollPane();
        
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setBounds(410,320,360,235);
        pane.getViewport().setBackground(Color.darkGray);
        pane.setForeground(Color.white);
        pane.setOpaque(true);
        JScrollBar verticalScrollBar = pane.getVerticalScrollBar();

        // Basic styling
        verticalScrollBar.setBackground(Color.BLACK);
        verticalScrollBar.setForeground(Color.WHITE);
        pane.getViewport().setOpaque(true);
        JPanel container = new JPanel();
        File[] files = GameData.getFiles();
        for (int i = 0; i < GameData.countJsonFiles(); i++){
            String name = GameData.extractName(files[i].getPath());
            JButton button = new JButton(name);
            button.setFont(new java.awt.Font("Arial",Font.BOLD,25));
            button.setBackground(Color.darkGray);
            button.setForeground(Color.white);
            button.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
            button.setOpaque(true);
            Entity player = GameData.loadSave(name+".json");
            button.addActionListener(e -> startGame(player));
            container.add(Box.createRigidArea(new Dimension(0, 10)));
            container.add(button);
        }
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.darkGray);
        container.add(Box.createVerticalGlue());
        container.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        pane.setViewportView(container);
        pane.revalidate();
        pane.repaint();
        setComponentZOrder(pane, 0);
        panel.add(pane);
        panel.repaint();
        panel.revalidate();
    }
}
    
    