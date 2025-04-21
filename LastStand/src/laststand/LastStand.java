/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package laststand;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
*/
public class LastStand{
    public static void main(String[] args) {
        //Create player and enemy with test Values
        Entity player = new Entity("Player", 100, 15, 5);
        //Entity enemy = new Entity("Enemy", 60, 10, 3);
        GameData data = new GameData("data.json");
        Entity enemy = data.getRandomEntity();
         
        //Start screen Battle
        Battle battle = new Battle(player, enemy);
        
        //BattleTest newtest = new BattleTest();
        //newtest.runAllTests();

    }
}
