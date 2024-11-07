import javax.swing.*;
import java.awt.*;

public class Card{
    private int x, y, w, h;
    private String name;
    private Color col;
    
    public Card(){
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        name = "";
        col = Color.white;
    }

    public Card(int x, int y, String name, Color c){
        this.x = x;
        this.y = y;
        this.name = name;
        w = 200;
        h = 75;
        col = c;
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
    public void drawCard(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
    }
}