public class Card{
    private int x, y, w, h;
    private String name;
    
    public Card(){
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        name = "";
    }

    public Card(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
        w = 200;
        h = 75;
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
}