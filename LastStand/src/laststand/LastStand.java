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
 */
public class LastStand implements ActionListener {

    private int countA = 0;
    private int countB = 0;
    private int countC = 0;
    private int countD = 0;
    private JLabel LabelA;
    private JLabel LabelB;
    private JLabel LabelC;
    private JLabel LabelD;
    private JButton buttonA;
    private JButton buttonB;
    private JButton buttonC;
    private JButton buttonD;
    //key: A = attack, B = heal, C = use item, D = surrender
    
    private JFrame frame;
    private JPanel panel;
    
    public LastStand() {
        //add frame
        frame = new JFrame();
        
        //add buttons
        buttonA = new JButton("Attack");
        buttonA.addActionListener(this);
        
        buttonB = new JButton("Heal");
        buttonB.addActionListener(this);
        
        buttonC = new JButton("Use Item");
        buttonC.addActionListener(this);
        
        buttonD = new JButton("Surrender");
        buttonD.addActionListener(this);
        
        //add respective labels
        LabelA = new JLabel("Attk Counter: 0");
        LabelB = new JLabel("Heal Counter: 0");
        LabelC = new JLabel("Item Counter: 0");
        LabelD = new JLabel("Surr Counder: 0");
        
        //add elements to panel
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 20, 60));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(buttonA);
        panel.add(buttonB);
        panel.add(buttonC);
        panel.add(buttonD);
        panel.add(LabelA);
        panel.add(LabelB);
        panel.add(LabelC);
        panel.add(LabelD);
        
        //design options
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setTitle("Last Stand"); 
        frame.pack(); 
        frame.setVisible(true); 
    }
    
    public static void main(String[] args) {
        new LastStand();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        //to listen and react to a button being pressed.
        //I (Rose) will link functions to buttons once we merge into main.
        //For not all buttons just increase a counter that responds to clicking.
        
        if (evt.getSource()==buttonA)
        {
            countA++;
            LabelA.setText("Attk Counter: " + countA);
        }
        else if (evt.getSource()==buttonB)
        {
            countB++;
            LabelB.setText("Heal Counter: " + countB);
        }
        else if (evt.getSource()==buttonC)
        {
            countC++;
            LabelC.setText("Item Counter: " + countC);
        }
        else if (evt.getSource()==buttonD)
        {
            countD++;
            LabelD.setText("Surr Counder: " + countD);
        }
    }
    
}
