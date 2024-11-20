import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.event.*; 


public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	
	private BufferedImage back; 
	private int key, x, y; 

    private HashMap <String, ArrayList<String>> cardDeck;
    private HashMap <String, String> murderInfo;
    private ArrayList<String> myHand;
	private ArrayList<ArrayList<String>> allHands;
	private ArrayList<ArrayList<Card>> cardsToDisplay = new ArrayList<ArrayList<Card>>();

	private String screenStatus = "Loading";
	private int numPlayers = 0;
	
	public Game() {
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		key =-1; 
		x=0;
		y=0;
        murderInfo = selectMurderInfo();
        cardDeck = setCardDeck();
		removeMurderFromCardDeck();
	}

	
	
	public void run()
	   {
	   	try
	   	{
	   		while(true)
	   		{
	   		   Thread.currentThread().sleep(5);
	            repaint();
	         }
	      }
	   		catch(Exception e)
	      {
	      }
	  	}
	

	
	
	
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
		

		Graphics g2d = back.createGraphics();
	
		g2d.clearRect(0,0,getSize().width, getSize().height);
	
		g2d.setFont( new Font("Broadway", Font.BOLD, 50));
		
	
		if(screenStatus.equals("Loading")){
			g2d.drawString("Hello!" , x, y);
		} else if(screenStatus.equals("Start")){
			startGameplay(g2d);
		}
		
       	System.out.println("Selected Murder Info: "+murderInfo);
		System.out.println("my cards: "+myHand);
		System.out.println("testing shuffle: "+allHands);
		twoDgraph.drawImage(back, null, 0, 0);

	}

	public void startGameplay(Graphics g2d){
		drawCards(g2d);
		drawMurder(g2d);
	}

	public void drawCards(Graphics g2d){
		x = 10;
		y = 10;
		for(int i=0; i<allHands.size(); i++){
			ArrayList<Card> currentHand = new ArrayList<Card>();
			for(int j=0; j<allHands.get(i).size(); j++){
				currentHand.add(new Card(x, y, allHands.get(i).get(j), Color.red));
				x+=20;
				y+=50;
			}
			cardsToDisplay.add(currentHand);
			x+=200;
			y=10;
		}
		
		for(int i=0; i<cardsToDisplay.size(); i++){
			for(Card c: cardsToDisplay.get(i)){
				c.drawCard(g2d);
			}
		}
	}

	public void drawMurder(Graphics g2d){
		ArrayList<Card> currentHand = new ArrayList<Card>();
		int x = cardsToDisplay.get(cardsToDisplay.size()-1).get(0).getX();
		int y = 10;
		currentHand.add(new Card(x + 250, y, murderInfo.get("Suspect"), Color.MAGENTA));
		currentHand.add(new Card(x + 270, y + 50, murderInfo.get("Weapon"), Color.MAGENTA));
		currentHand.add(new Card(x + 290, y + 100, murderInfo.get("Room"), Color.MAGENTA));
		for(Card c: currentHand){
			c.drawCard(g2d);
		}
		

	}

	public ArrayList<ArrayList<String>> shuffleAndDealCards(){
		ArrayList<String> deck = new ArrayList<String>();
		deck.addAll(cardDeck.get("Suspect"));
		deck.addAll(cardDeck.get("Weapon"));
		deck.addAll(cardDeck.get("Room"));
		Collections.shuffle(deck);
	
		ArrayList<ArrayList<String>> playerHands = new ArrayList<ArrayList<String>>();
		for(int i=0; i<numPlayers; i++){
			playerHands.add(new ArrayList<String>());
		}
		int index = 0;
		for(int j=0; j<playerHands.size(); j++){
			int cardsPerHand = (int)(deck.size()/numPlayers);
			for(int k=0; k<cardsPerHand; k++){
				playerHands.get(j).add(deck.get(index));
				index++;
				System.out.println("index before remainder: "+index);
			}
		}
		int remainder = deck.size()%numPlayers;
		if(remainder>0){
			for(int i=0; i<remainder; i++){
				playerHands.get(i).add(deck.get(index));
				index++;
				System.out.println("New index: "+index);
			}
		}
		return playerHands;
	}


    // SETTER METHODS
    public HashMap<String, ArrayList<String>> setCardDeck(){
        HashMap<String, ArrayList<String>> temp = new HashMap<String, ArrayList<String>>();
        temp.put("Suspect", setSuspects());
        temp.put("Weapon", setWeapons());
        temp.put("Room", setRooms());
        return temp;
    }
    public HashMap<String, String> selectMurderInfo(){
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("Suspect", setSuspects().get(generateRandomNum(0,setSuspects().size()-1)));
        temp.put("Weapon", setWeapons().get(generateRandomNum(0,setWeapons().size()-1)));
        temp.put("Room", setRooms().get(generateRandomNum(0,setRooms().size()-1)));
        return temp;
    }
    public void removeMurderFromCardDeck(){
        cardDeck.get("Suspect").remove(murderInfo.get("Suspect"));
        cardDeck.get("Weapon").remove(murderInfo.get("Weapon"));
        cardDeck.get("Room").remove(murderInfo.get("Room"));
    }

    public ArrayList<String> setSuspects(){
        ArrayList<String> temp = new ArrayList<String>();
        File file = new File("C:\\Users\\s1734907\\repo\\Clue\\Cards\\Suspects.txt");
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                temp.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList<String> setWeapons(){
        ArrayList<String> temp = new ArrayList<String>();
        File file = new File("C:\\Users\\s1734907\\repo\\Clue\\Cards\\Weapons.txt");
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                temp.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList<String> setRooms(){
        ArrayList<String> temp = new ArrayList<String>();
        File file = new File("C:\\Users\\s1734907\\repo\\Clue\\Cards\\Rooms.txt");
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                temp.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
    }

    // HELPER METHODS
    public int generateRandomNum(int min, int max){
        return (int)(Math.random()*(max-min)+min)+1;
    }

	/*public ArrayList<String> shuffleAndDealCards(){
		ArrayList<String> temp = new ArrayList<String>();
		temp.addAll(cardDeck.get("Suspect"));
		temp.addAll(cardDeck.get("Weapon"));
		temp.addAll(cardDeck.get("Room"));
		Collections.shuffle(temp);
		System.out.println("Card deck size: " + temp.size());
		System.out.println("Num players: "+ numPlayers);
		ArrayList<String> mycards = new ArrayList<String>();
		for(int i=0; i<(int)(temp.size()/numPlayers); i++){
			mycards.add(temp.get(i));
		}
		return mycards;
	}*/

	//DO NOT DELETE
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




//DO NOT DELETE
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		key= e.getKeyCode();
		System.out.println(key);
		if(e.getKeyCode()-48>0 && e.getKeyCode()-48<=6){
			numPlayers = e.getKeyCode()-48;
		}
		else if(e.getKeyCode()==32){
			screenStatus = "Start";
			allHands = shuffleAndDealCards();
			myHand = allHands.get(0);
		}
		else{
			numPlayers = 1;
		}

	}
		
	


	//DO NOT DELETE
	@Override
	public void keyReleased(KeyEvent e) {
		
		
		
		
	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		x=arg0.getX();
		y=arg0.getY();
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("entered");
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("exited");
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("you clicked at"+ arg0.getY());
		x=arg0.getX();
		y=arg0.getY();
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	
}
