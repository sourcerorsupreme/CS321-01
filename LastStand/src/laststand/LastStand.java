/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package laststand;
import javax.swing.*;
import java.awt.*;

/**
 * Branch: Rose Bevill
 * Author of branch: Rose Bevill
 * 
 * This is the branch that includes a basic GUI for the actions list.
 * There isn't much here other than four buttons (attack, heal, use item,
 * and surrender buttons that increment by one just to show that it works.
 * 
 * Change log:
 * 17/feb/25 - created branch and file
 * 19/feb/25 - added four basic buttons and increment counters
 * 15/mar/25 - updated GUI to reflect design made previously,
 *             removed actionlistener from attack, heal, use item, 
 *             and surrender buttons until I figure out how to use it.
 * 31/mar/25 - updating for sake of report: been trying to move my code 
 *             to the battle class, but been held back both by schedule
 *             and outside situations. I'm definitely working on it
 *             though!
 * 
 */
public class LastStand{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Last Stand");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        //Turn label
        JLabel label = new JLabel(" It's Your Turn! ");
        label.setFont(new java.awt.Font("Arial", Font.BOLD, 22));
        label.setOpaque(true);
        label.setBackground(new Color(255,242,218));
        label.setBorder(BorderFactory.createLineBorder(new Color(255,189,71), 5, true));
        label.setBounds(10,10,175,55);
        
        //decoration label 1
        JLabel decoration1 = new JLabel(" ");
        decoration1.setOpaque(true);
        decoration1.setBackground(Color.darkGray);
        decoration1.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration1.setBounds(10,430,406,285);
        
        //decoration label 2
        JLabel decoration2 = new JLabel(" ");
        decoration2.setOpaque(true);
        decoration2.setBackground(Color.darkGray);
        decoration2.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration2.setBounds(426,430,416,285);
        
        //decoration label 3
        JLabel decoration3 = new JLabel(" ");
        decoration3.setOpaque(true);
        decoration3.setBackground(Color.darkGray);
        decoration3.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        decoration3.setBounds(852,430,406,285);
        
        //label for the action list
        JLabel actionList = new JLabel("  Action list");
        actionList.setOpaque(true);
        actionList.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionList.setBackground(Color.black);
        actionList.setForeground(Color.LIGHT_GRAY);
        actionList.setBounds(20,415,200,30);

        
        //label for the action describer
        JLabel actionDescriber = new JLabel("  Action Describer");
        actionDescriber.setOpaque(true);
        actionDescriber.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        actionDescriber.setBackground(Color.black);
        actionDescriber.setForeground(Color.LIGHT_GRAY);
        actionDescriber.setBounds(440,415,250,30);
        
        //label for the text box (? workshop name)
        JLabel textBox = new JLabel("  Text Box");
        textBox.setOpaque(true);
        textBox.setFont(new java.awt.Font("Arial",Font.BOLD,23));
        textBox.setBackground(Color.black);
        textBox.setForeground(Color.LIGHT_GRAY);
        textBox.setBounds(870,415,200,30);
        
        //label for enemy HP
        JLabel enemyHP = new JLabel("ENEMY HP: 100/100");
        enemyHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        enemyHP.setForeground(Color.white);
        enemyHP.setBounds(525,50,250,30);
        
        //label for player HP
        JLabel playerHP = new JLabel("YOUR HP: 100/100");
        playerHP.setFont(new java.awt.Font("Arial",Font.BOLD,20));
        playerHP.setForeground(Color.white);
        playerHP.setBounds(530,370,250,30);
        
        //help button
        JButton helpButton = new JButton("Help!");
        helpButton.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        helpButton.setBackground(Color.darkGray);
        helpButton.setForeground(Color.white);
        helpButton.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        helpButton.setBounds(1150,10,100,55);
        
        //attack button
        JButton attack = new JButton("ATTACK");
        attack.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        attack.setBackground(Color.darkGray);
        attack.setForeground(Color.white);
        attack.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        attack.setBounds(30,455,360,55);

        //heal button
        JButton heal = new JButton("HEAL");
        heal.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        heal.setBackground(Color.darkGray);
        heal.setForeground(Color.white);
        heal.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        heal.setBounds(30,515,360,55);
        
        //use item button
        JButton useItem = new JButton("USE ITEM");
        useItem.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        useItem.setBackground(Color.darkGray);
        useItem.setForeground(Color.white);
        useItem.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        useItem.setBounds(30,575,360,55);
        
        //surrender button
        JButton surrender = new JButton("SURRENDER");
        surrender.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        surrender.setBackground(Color.darkGray);
        surrender.setForeground(Color.white);
        surrender.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        surrender.setBounds(30,635,360,55);
        
        //ok button for action describer
        JButton ok1 = new JButton("OK");
        ok1.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        ok1.setBackground(Color.darkGray);
        ok1.setForeground(Color.white);
        ok1.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        ok1.setBounds(765,660,60,40);
        
        //ok button for text box
        JButton ok2 = new JButton("OK");
        ok2.setFont(new java.awt.Font("Arial",Font.BOLD,25));
        ok2.setBackground(Color.darkGray);
        ok2.setForeground(Color.white);
        ok2.setBorder(BorderFactory.createLineBorder(Color.white, 5, true));
        ok2.setBounds(1181,660,60,40);

        /**
        * Notice: buttons don't have actionlistener anymore. I couldn't
        * figure out how to implement actionlistener with the GUI I propped
        * up. For now, for report 1, I'll keep it as is.
        */
        
        panel.add(label);
        panel.add(enemyHP);
        panel.add(playerHP);
        panel.add(helpButton);
        panel.add(actionList);
        panel.add(actionDescriber);
        panel.add(textBox);
        panel.add(ok1);
        panel.add(ok2);
        panel.add(attack);
        panel.add(heal);
        panel.add(useItem);
        panel.add(surrender);
        panel.add(decoration1);
        panel.add(decoration2);
        panel.add(decoration3);
        panel.setBackground(Color.BLACK);
        
        frame.add(panel);
        frame.setSize(1280,760);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
