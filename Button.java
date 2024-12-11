import javax.swing.*;
import java.awt.*;

public class Button{
    private int x, y, w, h;
    private String name;
    private Color col;
    private int player;
    private boolean seePlayerHand;
    private ImageIcon image;
    
    public Button(){
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        name = "";
        col = Color.white;
        seePlayerHand = false;
        image = new ImageIcon("");
    }

    public Button(int x, int y, String name, Color c){
        this.x = x;
        this.y = y;
        this.name = name;
        w = 200;
        h = 75;
        col = c;
        player = 0;
        seePlayerHand = false;
        image = new ImageIcon("");
    }
    public Button(int x, int y, String name, Color c, int player){
        this.x = x;
        this.y = y;
        this.name = name;
        w = 200;
        h = 75;
        col = c;
        this.player = player;
        seePlayerHand = false;
        image = new ImageIcon("");
    }

    public Button(int x, int y, int w, int h, ImageIcon img){
        this.x = x;
        this.y = y;
        name = "name";
        this.w = w;
        this.h = h;
        image = img;
        seePlayerHand = false;
    }

    public void setImage(ImageIcon img){
        image = img;
    }
    public ImageIcon getImage(){
        return image;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
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
    public int getPlayerNum(){
        return player;
    }
    public void setSeePlayerHand(boolean clicked){
        seePlayerHand = clicked;
    }
    public boolean getSeePlayerHand(){
        return seePlayerHand;
    }
    public void drawButton(Graphics g2d){
        g2d.setColor(col);
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(Color.black);
        g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
        g2d.drawString(name, x+40, y+40);
    }
    public void drawImageButton(Graphics g2d){
        g2d.drawImage(image.getImage(), x, y, w, h, null);
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