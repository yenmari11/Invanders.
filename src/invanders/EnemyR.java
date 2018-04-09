/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invanders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author yenma
 */
public class EnemyR extends MovingObject {
    
    ImageIcon alienR = new ImageIcon("images/alienR.gif");
    
    private int width;
    private int height;
    
    public EnemyR(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.width = width;
        this.height = height;
    }
    
    
    @Override
    public Rectangle getBounds() {
        Rectangle enemyHitBox = new Rectangle(this.getXPosition(), this.getYPosition());
        return enemyHitBox;
    }
    
    @Override
    public void move() {
        xPosition += xVel;
    }

    @Override
    public void draw(Graphics g) {

            alienR.paintIcon(null, g, this.getXPosition(), this.getYPosition());

    }
    
}
