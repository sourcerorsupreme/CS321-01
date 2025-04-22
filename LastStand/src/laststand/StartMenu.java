/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
        
/**
 *
 * @author tommy
 * 4/20/25: Class Created to make and load start menu.
 */
public class StartMenu extends JPanel{
    private JFrame frame;

    private JButton newGameButton = new JButton("New Game");
    private JButton loadButton = new JButton("Load Save");
    private JButton quitButton = new JButton("Quit");
    
    public StartMenu(JFrame frame){
        this.frame = frame;
        setLayout(null);
        setBackground(Color.BLACK);
        
        JLabel title = new JLabel("LAST STAND", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(100, 50, 400, 50);
        add(title);
        
        newGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        newGameButton.setBounds(200, 130, 200, 40);
        newGameButton.setBackground(Color.DARK_GRAY);
        newGameButton.setForeground(Color.WHITE);
        add(newGameButton);

        loadButton.setFont(new Font("Arial", Font.BOLD, 24));
        loadButton.setBounds(200, 190, 200, 40);
        loadButton.setBackground(Color.DARK_GRAY);
        loadButton.setForeground(Color.WHITE);
        add(loadButton);

        quitButton.setFont(new Font("Arial", Font.BOLD, 24));
        quitButton.setBounds(200, 250, 200, 40);
        quitButton.setBackground(Color.DARK_GRAY);
        quitButton.setForeground(Color.WHITE);
        add(quitButton);
        
        frame.setContentPane(this);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addListeners();
    }
    
    private void startGame(){
        Entity player = new Entity("Hero", 100, 15, 5);
        GameData data = new GameData("data.json");
        Entity enemy = data.getRandomEntity();
        
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        
        new Battle(player, enemy, frame);
    }
    
    private void addListeners(){
        newGameButton.addActionListener(e -> startGame());
        
        loadButton.addActionListener(e -> {
            // Load save logic later
        });

        quitButton.addActionListener(e -> System.exit(0));
    }
}
    
    
