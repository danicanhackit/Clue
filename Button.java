import javax.swing.*;
import java.awt.*;

public class Button{
    private int x, y, w, h;
    private String name;
    private Color col;
    
    public Button(){
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        name = "";
        col = Color.white;
    }

    public Button(int x, int y, String name, Color c){
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
    public void setColor(Color c){
        col = c;
    }
    public void drawButton(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(Color.black);
        g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        g2d.drawString(name, x+40, y+40);
    }

    public boolean hover(int mouseX, int mouseY){
        Rectangle point = new Rectangle(mouseX, mouseY, 1,1);
        Rectangle button = new Rectangle(x, y, w, h);
        if(point.intersects(button)){
            return true;
        }
        return false;
    }
}