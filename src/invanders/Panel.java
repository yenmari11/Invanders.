/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invanders;

import Controller.KeyboardController;
import List.SimpleList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Yendry Diaz Solis.
 */
public class Panel extends JPanel {
    
    private Timer timer;
    private KeyboardController controller;
    SimpleList simpleList = new SimpleList();

    
    private final int Width = 800;
    private final int Height = 800;
    private final int framesPerSecond = 120;
    
    Random r = new Random();
    private int score = 0;
    private int level = 1;
    private int numberOfLives = 3;
    private int highScore;
    private int markerX, markerY;
   // private static int bossHealth = 5; //////////////////////
    File f = new File("Highscore.txt");   
    
        // Added Objects
    private Player player;
    private Player singleLife;
    private Enemy enemy;
    //private Shield shield;
    private Bullet bullet;
    
    
        // Added Booleans
    private boolean newBulletCanFire = true;
    private boolean newBeamCanFire = true;
    private boolean hitMarker = false;
    

    private ImageIcon background = new ImageIcon("images/backgroundSkin.jpg");

    
    
    public final void setup(){
        
    
        if (level == 1){
            for (int row = 0;  row< 7; row++) {
                for (int column = 0; column < 1; column++) {
                
        
                    enemy = new Enemy((200+(row*70)),(50 + (column * 60)) , level, 0, null,40,40); // Enemy speed will increase each level
                    simpleList.add(enemy);
                } 
            }
        }
        if(level==2){
            for (int row = 0;  row< 7; row++) {
                for (int column = 0; column < 1; column++) {
                
        
                    enemy = new Enemy((200+(row*70)),(50 + (column * 60)) , level, 0, null,40,40); // Enemy speed will increase each level
                    simpleList.add(enemy);
                } 
            }
            
        }
            
           
            
 
        
        
        
        
        // Resets all controller movement
        controller.resetController();

        // Sets the player's ship values   
        player = new Player(375, 730, null, controller);
        
       
        
        
    }
        
        ///////////////////////// PAINT
   
    @Override
    public void paint(Graphics g) 
    {
       
        // Sets background image
        background.paintIcon(null, g, 0, -150);

        // Draws the player's ship
        player.draw(g);  
        
        
              //Draws the aliens 
        try {
         
            for (int index = 0; index < simpleList.Size(); index++) {
                simpleList.getInPosition(index).getEnemy().draw(g);
            }

        } catch (IndexOutOfBoundsException e) {
        } catch (Exception ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         // Draw a bullet on space bar press
        if (controller.getKeyStatus(32)) {
            if (newBulletCanFire) {
                bullet = new Bullet(player.getXPosition() + 22, player.getYPosition() - 20, 0, Color.RED);
                
                newBulletCanFire = false;
            }
        }
        // Only attempts to draw bullet after key press
        if (bullet != null) {
            bullet.draw(g);
        }
        
        
          // Sets the score display
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 260, 20);
        
          // Sets level display
        g.setColor(Color.WHITE);
        g.drawString("Level " + level, 750, 20);
        
          // Sets Highscore display
        g.setColor(Color.WHITE);
        g.drawString("Highscore: " + highScore, 440, 20);
        
        
     }
    
    /////////// END PAINT
    
    
       //////////////////////////////////// UPDATE STATE
    
    public void updateState(int frame) throws Exception
    {
        // Allows player to move left and right
        player.move();

        // Updates highscore
        try {
            Scanner fileScan = new Scanner(f);
            while (fileScan.hasNextInt()) {
                String nextLine = fileScan.nextLine();
                Scanner lineScan = new Scanner(nextLine);
                highScore = lineScan.nextInt();
            }
        } catch (FileNotFoundException e) {
        }
        // Adds option to reset highScore
        if (controller.getKeyStatus(82)) {
            int answer = JOptionPane.showConfirmDialog(null, "Would you like to reset the high score?", ":)", 0);
            controller.resetController();
            if (answer == 0) {
                try {
                    String scoreString = Integer.toString(0);
                    try (PrintWriter printerWriter = new PrintWriter(new FileOutputStream(f, false))) {
                        printerWriter.write(scoreString);
                    }
                } catch (FileNotFoundException e) {
                }
            }
        }
        // Updates the high score text file if your score exceeds the previous high score
        try {
            if (score > highScore) {
                String scoreString = Integer.toString(score);
                try (PrintWriter printerWriter = new PrintWriter(new FileOutputStream(f, false))) {
                    printerWriter.write(scoreString);
                }
            }
        } catch (FileNotFoundException e) {
        }

        
        // Makes enemies move and change direction at borders
        if ((simpleList.getInPosition(simpleList.Size() - 1).getEnemy().getXPosition() + simpleList.getInPosition(simpleList.Size() - 1).getEnemy().getXVelocity()) > 760 || (simpleList.getInPosition(0).getEnemy().getXPosition() + simpleList.getInPosition(0).getEnemy().getXVelocity()) < 0) {
            for (int index = 0; index < simpleList.Size(); index++) {
                simpleList.getInPosition(index).getEnemy().setXVelocity(simpleList.getInPosition(index).getEnemy().getXVelocity() * -1);
                simpleList.getInPosition(index).getEnemy().setYPosition(simpleList.getInPosition(index).getEnemy().getYPosition() + 10);
            }
        }else {
            for (int index = 0; index < simpleList.Size(); index++) {
                simpleList.getInPosition(index).getEnemy().move();
            }
        }
        
        
        // Move bullet
        if (bullet != null) 
        {
            bullet.setYPosition(bullet.getYPosition() - 15);
            if (bullet.getYPosition() < 0)
            {
                newBulletCanFire = true;
                
            }
           
                      // Checks for collisions with enemies
            for (int ind = 0; ind < simpleList.Size(); ind++) {
                if (bullet.Colliding(simpleList.getInPosition(ind).getEnemy())) {
                   
                   
                    bullet = new Bullet(0, 0, 0, null);
                   //bullet=null;
                    newBulletCanFire = true;
                    // Updates score for normal levels
                    
                    if (level==1){
                        score += 100;
                        hitMarker = true;
                        markerX = simpleList.getInPosition(ind).getEnemy().getXPosition(); // Gets positions that the "+ 100" spawns off of
                        markerY = simpleList.getInPosition(ind).getEnemy().getYPosition();
                        simpleList.remove(ind);
                        System.out.println(simpleList.Size());
                        
                        
                    }
                
               
                } 
                
            } 
        }
        
        
                //Destroys shields if aliens collide with them
        for (int input = 0; input < simpleList.Size(); input++) {
     
            // If aliens exceed this X Position, you reset the level and lose a life
            if (simpleList.getInPosition(input).getEnemy().getYPosition() + 50 >= 750) {
                simpleList.clear();
         
                numberOfLives = 0;
               
            
            }
        }
        if (numberOfLives ==0) {
          
            int answer = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "You lost the game with " + score + " points", 0);
            // If they choose to play again, this resets every element in the game
            if (answer == 0) {
                simpleList.clear();
                score = 0;
                level = 1;
                numberOfLives = 1;
                newBulletCanFire = true;
                newBeamCanFire = true;
                setup();
            }
            // If they choose not to play again, it closes the game
            if (answer == 1) {
                System.exit(0);
            }
        }
        
       // Goes to next level, resets all lists, sets all counters to correct values
        if (simpleList.isEmpty()) {
            level += 1;
            setup();
        } 
            
        
        
        
    }
    ////////////////////////////////////END UPDATE STATE
    
     /////////////////////////////////////////////////////// PANEL      
    
     public Panel() {
        // Set the size of the Panel
        this.setSize(Width, Height);
        this.setPreferredSize(new Dimension(Width, Height));
        this.setBackground(Color.BLACK);

        // Register KeyboardController as KeyListener
        controller = new KeyboardController();
        this.addKeyListener(controller);

        // Call setupGame to initialize fields
        this.setup();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    

    
    
    /**
     * Method to start the Timer that drives the animation for the game. It is
     * not necessary for you to modify this code unless you need to in order to
     * add some functionality.
     */
   public void start() {
             // Set up a new Timer to repeat every 20 milliseconds (50 FPS)
        timer = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Tracks the number of frames that have been produced.
            // May be useful for limiting action rates
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update the game's state and repaint the screen
                    updateState(frameNumber++);
                } catch (Exception ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint();
            }

     
        });
        Timer TimerHitMarker = new Timer(1000, (ActionEvent e) -> {
            // Update the game's state and repaint the screen
            hitMarker = false;
        } // Tracks the number of frames that have been produced.
        // May be useful for limiting action rates
        );

        timer.setRepeats(true);
        timer.start();
        TimerHitMarker.setRepeats(true);
        TimerHitMarker.start();   
    }
    
}
