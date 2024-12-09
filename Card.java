import javax.swing.*;
import java.awt.*;

public class Card{
    private int x, y, w, h;
    private String name;
    private Color col;
    private boolean revealed;
    
    public Card(){
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        name = "";
        col = Color.white;
        revealed = false;
    }

    public Card(int x, int y, String name, Color c){
        this.x = x;
        this.y = y;
        this.name = name;
        w = 200;
        h = 75;
        col = c;
        revealed = false;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return w;
    }
    public int getHeight(){
        return h;
    }
    public String getName(){
        return name;
    }
    public Color getColor(){
        return col;
    }
    public String toString(){
        return name;
    }
    public void drawCard(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(Color.black);
        g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        g2d.drawString(name, x+40, y+40);
    }
    public void drawOutlinedCard(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(Color.black);
        g2d.drawRoundRect(x, y, w, h, 20,20);
        g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        g2d.drawString(name, x+40, y+40);
    }
    public void drawCardWOName(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(Color.black);
        g2d.drawRoundRect(x, y, w, h, 20,20);
        //g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        //g2d.drawString(name, x+40, y+40);
    }
    public void revealAnswer(Graphics g2d){
        g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        g2d.drawString(name, x+40, y+40);
    }
    public boolean hover(int mouseX, int mouseY){
        Rectangle point = new Rectangle(mouseX, mouseY, 1,1);
        Rectangle card = new Rectangle(x, y, w, h);
        if(point.intersects(card)){
            return true;
        }
        return false;
    }
    public void setColor(Color c){
        col = c;
    }
    public void setRevealed(boolean revealed){
        this.revealed = revealed;
    }
    public boolean isRevealed(){
        return revealed;
    }
}