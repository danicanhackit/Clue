import java.util.ArrayList;

public class Player{
    private ArrayList<Card> cards;
    public Player(){
        cards = new ArrayList<Card>();
    }
    public void setCards(ArrayList<Card> cardz){
        cards = cardz;
    }
    public ArrayList<Card> getCards(){
        return cards;
    }
    
}