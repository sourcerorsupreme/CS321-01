/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laststand;
import javax.swing.*;
import java.awt.*;

/**
 * Class Player represents the class controlled by the User
 * @author Team 3
 */
public class Player {
    private final JLabel        targetLabel;
    private final ImageIcon[]   frames;
    private final Timer         timer;
    private int                 currentFrame = 0;
    
     /**
     * @param label        The JLabel whose icon you want to animate.
     * @param resourcePaths  Array of classpath resource paths, e.g. "/assets/player0.png"
     * @param frameWidth   Width to scale each frame to.
     * @param frameHeight  Height to scale each frame to.
     * @param delayMs      Milliseconds between frame switches.
     */
    
    public Player (JLabel label, String[] resourcePaths, int frameWidth, int frameHeight, int delayMs) {
        this.targetLabel = label;
        this.frames = new ImageIcon[resourcePaths.length];
        
        for (int i = 0; i < resourcePaths.length; i++) {
            ImageIcon orig = new ImageIcon(getClass().getResource(resourcePaths[i]));
            Image scaled = orig.getImage()
                               .getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
            frames[i] = new ImageIcon(scaled);
        }

        // timer
        this.timer = new Timer(delayMs, e -> {
            currentFrame = (currentFrame + 1) % frames.length;
            targetLabel.setIcon(frames[currentFrame]);
        });
    }

    
    
    //Start the looping animation 
    public void start() {
        // Immediately show frame 0
        targetLabel.setIcon(frames[0]);
        timer.start();
    }

    // Stop (and reset) the animation
    public void stop() {
        timer.stop();
        currentFrame = 0;
        targetLabel.setIcon(frames[0]);
    }
    
    
}
