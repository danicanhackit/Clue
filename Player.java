import java.util.ArrayList;

public class Player{
    private ArrayList<Card> cards;
    public Player(){
        cards = new ArrayList<Card>();
    }
    public void setCards(ArrayList<Card> _cards){
        cards = _cards;
    }
    public ArrayList<Card> getCards(){
        return cards;
    }
    public String toString(){
        return "Player";
    }
    
}