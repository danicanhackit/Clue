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

    // hashmap of strings (card names) by suspect, weapon, and location
	private HashMap <String, ArrayList<String>> cardDeck;
	// hashmap of murderer suspect, weapon, and location (the answer)
    private HashMap <String, String> murderInfo;
	// first arraylist of strings in allHands (index 0)
    private ArrayList<String> myHand;
	// arraylists of string arraylists made by dividing remaining cards into number of players
	private ArrayList<ArrayList<String>> allHands;
	// arraylist of card arraylists that are the card representations of each hand (which is a string arraylist)
	private ArrayList<ArrayList<Card>> cardsToDisplay = new ArrayList<ArrayList<Card>>();
	private ArrayList<Player> players = new ArrayList<Player>();

	private ArrayList<Button> playerButtons = new ArrayList<Button>();
	private String screenStatus = "Loading";
	private int numPlayers = 0;
	
	private boolean openEnvelope = false;
	private Button start = new Button(500, 100, "start", Color.RED);
	private Button deleteCard = new Button(0,0,"confirm delete", Color.RED);
	private Button notepad = new Button(230, 30, 200, 200, new ImageIcon("Button Images\\notepad.png"));
	private Button envelope = new Button();
	private Button home = new Button(800,400, "home", Color.RED);
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
			g2d.drawString("How many players?" , x, y);
			g2d.drawString("Players: "+numPlayers, 500,400);
			start.drawButton(g2d);
		} else if(screenStatus.equals("Start")){
			startGameplay(g2d);
		} else if(screenStatus.equals("Notepad")){
			notepadScreen(g2d);
		} else if(screenStatus.equals("Envelope Results")){
			drawMurder(g2d);
			home.drawButton(g2d);
		}
		
		//also not using this rn
		else if(screenStatus.equals("See Player Cards")){
			seePlayerCardsScreen(g2d);
		}
		
       	//System.out.println("Selected Murder Info: "+murderInfo);
		//System.out.println("my cards: "+myHand);
		//System.out.println("testing shuffle: "+allHands);
		twoDgraph.drawImage(back, null, 0, 0);

	}

	public void notepadScreen(Graphics g2d){
		home.drawButton(g2d);
		g2d.setFont(new Font("Broadway", Font.PLAIN, 20));
		int x = 100;
		int y = 50;
		g2d.drawString("Suspects", x, y);
		for(int i=0; i<setSuspects().size(); i++){
			y+=20;
			g2d.drawString(setSuspects().get(i), x, y);
		}
		x+=200;
		y = 50;
		g2d.drawString("Rooms", x, y);
		for(int i=0; i<setRooms().size(); i++){
			y+=20;
			g2d.drawString(setRooms().get(i), x, y);
		}
		x+=200;
		y = 50;
		g2d.drawString("Weapons", x, y);
		for(int i=0; i<setWeapons().size(); i++){
			y+=20;
			g2d.drawString(setWeapons().get(i), x, y);
		}
	}

	public void startGameplay(Graphics g2d){
		//drawCards(g2d);
		notepad.drawImageButton(g2d);
		envelope = new Button(playerButtons.get(0).getX(), playerButtons.get(0).getY()-170-20, 170,100, new ImageIcon("Button Images\\envelope.png"));
		envelope.drawImageButton(g2d);
		drawMyHand(g2d);
		//drawPlayerHands(g2d);
		drawButtons(g2d);
		displaySelectedPlayerHand(g2d);
		g2d.drawLine(700, 0, 700, 1600);
	}
	public void seePlayerCardsScreen(Graphics g2d){
		displaySelectedPlayerHand(g2d);
	}

	public void setPlayerHands(){
		for(int i=0; i<numPlayers-1; i++){
			players.add(new Player());
		}
		int x = 10;
		int y = 600;
		for(int i=1; i<players.size()+1; i++){
			ArrayList<Card> curHand = new ArrayList<Card>();
			for(int j=0; j<allHands.get(i).size(); j++){
				curHand.add(new Card(x, y, allHands.get(i).get(j), Color.BLUE));
				//x+=20;
				y+=50;
			}
			players.get(i-1).setCards(curHand);
			x+=250;
			y = 600;
		}
		System.out.println(players.toString());
	}

	public void displaySelectedPlayerHand(Graphics g2d){
		for(Button b: playerButtons){
			if(b.getSeePlayerHand()){
				int player = b.getPlayerNum()-2;
				g2d.setFont( new Font("Broadway", Font.PLAIN, 20));
				g2d.drawString("Player "+b.getPlayerNum(), b.getX(), b.getY()-20);
				for(int i=0; i<players.get(player).getCards().size(); i++){
					players.get(player).getCards().get(i).drawCardWOName(g2d);
				}

				for(Card c: players.get(b.getPlayerNum()-2).getCards()){
					if(c.getRevealed()){
						c.revealAnswer(g2d);
						deleteCard.setX(c.getX()+250);
						deleteCard.setY(c.getY());
						deleteCard.drawButton(g2d);
					}
				}
			}
		}
	}

	public ArrayList<Button> setPlayerButtons(){
		int x = 10;
		int y = 500;
		ArrayList<Button> temp = new ArrayList<Button>();
		for(int i=0; i<players.size(); i++){
			temp.add(new Button(x,y,"Player "+(i+2), Color.RED, i+2));
			x+=250;
		}
		return temp;
	}

	public void drawButtons(Graphics g2d){
		for(int i=0; i<playerButtons.size(); i++){
			playerButtons.get(i).drawButton(g2d);
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

	public void drawMyHand(Graphics g2d){
		int x = 10;
		int y = 30;
		ArrayList<Card> currentHand = new ArrayList<Card>();
		for(int i=0; i<myHand.size(); i++){
			currentHand.add(new Card(x, y, myHand.get(i), Color.YELLOW));
			y+=50;
		}
		for(Card c: currentHand){
			c.drawOutlinedCard(g2d);
		}
	}

	public void drawMurder(Graphics g2d){
		ArrayList<Card> currentHand = new ArrayList<Card>();
		int x = 230;
		int y = 30;

		currentHand.add(new Card(x + 220, y, murderInfo.get("Suspect"), Color.MAGENTA));
		currentHand.add(new Card(x + 240, y + 50, murderInfo.get("Weapon"), Color.MAGENTA));
		currentHand.add(new Card(x + 260, y + 100, murderInfo.get("Room"), Color.MAGENTA));
		for(Card c: currentHand){
			c.drawCard(g2d);
		}
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

	// OLD CARD METHODS
	public void drawCards(Graphics g2d){
		int x = 250;
		int y = 10;
		for(int i=1; i<allHands.size(); i++){
			ArrayList<Card> currentHand = new ArrayList<Card>();
			for(int j=0; j<allHands.get(i).size(); j++){
				currentHand.add(new Card(x, y, allHands.get(i).get(j), Color.red));
				//x+=20;
				y+=50;
			}
			cardsToDisplay.add(currentHand);
			x+=250;
			y=10;
		}
		
		for(int i=0; i<cardsToDisplay.size(); i++){
			for(Card c: cardsToDisplay.get(i)){
				c.drawOutlinedCard(g2d);
			}
		}
	}

	public void drawPlayerHands(Graphics g2d){
		for(Player p: players){
			System.out.println("Player Hand: "+p.getCards());
			for(Card c: p.getCards()){
				c.drawOutlinedCard(g2d);
			}
		}
		System.out.println(players.get(0).getCards());
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
			playerButtons = setPlayerButtons();
		}
		else{
			numPlayers = 1;
		}

		// not using this right now
		if(screenStatus.equals("See Player Cards")){
			if(e.getKeyChar()=='h'){
				for(Button b: playerButtons){
					b.setSeePlayerHand(!b.getSeePlayerHand());
				}
				screenStatus.equals("Start");
			}
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
		if(screenStatus.equals("Loading")){
			if(start.hover(x, y)){
				start.setColor(Color.BLUE);
			} else{
				start.setColor(Color.RED);
			}
		}
		if(screenStatus.equals("Start")){
			for(Button b: playerButtons){
				if(b.hover(x, y)){
					b.setColor(Color.GREEN);
				} else{
					b.setColor(Color.RED);
				}
				if(b.getSeePlayerHand()){
					for(Card c: players.get(b.getPlayerNum()-2).getCards()){
						if(c.hover(x,y)){
							c.setColor(Color.GREEN);
						} else{
							c.setColor(Color.BLUE);
						}
					}
					if(deleteCard.hover(x,y)){
						deleteCard.setColor(Color.PINK);
					} else{
						deleteCard.setColor(Color.RED);
					}
				}
			}
			if(notepad.hover(x,y)){
				notepad.setImage(new ImageIcon("Button Images\\notepad hover.png"));
			} else{
				notepad.setImage(new ImageIcon("Button Images\\notepad.png"));
			}
			/*if(envelope.hover(x,y)){
				envelope.setImage(new ImageIcon("Button Images\\envelope hover.png"));
				openEnvelope = true;
			} else{
				envelope.setImage(new ImageIcon("Button Images\\envelope.png"));
				openEnvelope = false;
			}*/
		}
		if(screenStatus.equals("Notepad")||screenStatus.equals("Envelope Results")){
			if(home.hover(x,y)){
				home.setColor(Color.BLUE);
			} else{
				home.setColor(Color.RED);
			}
		}
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
		x=arg0.getX();
		y=arg0.getY();
		System.out.println("you clicked at"+ arg0.getY());
		if(screenStatus.equals("Loading")){
			if(start.hover(x, y)){
				if(numPlayers == 0){
					numPlayers = 1;
				}
				screenStatus = "Start";
				allHands = shuffleAndDealCards();
				myHand = allHands.get(0);
				setPlayerHands();
				playerButtons = setPlayerButtons();
			}
		}
		if(screenStatus.equals("Start")){
			for(Button b: playerButtons){
				if(b.hover(x, y)){
					b.setSeePlayerHand(!b.getSeePlayerHand());
					//screenStatus = "See Player Cards";
				}
				if(b.getSeePlayerHand()){
					for(Card c: players.get(b.getPlayerNum()-2).getCards()){
						if(c.hover(x,y)){
							c.setRevealed(!c.getRevealed());
							break;
						}
						if(c.getRevealed()){
							if(deleteCard.hover(x,y)){
								players.get(b.getPlayerNum()-2).getCards().remove(c);
								b.setSeePlayerHand(false);
							}
						}
					}
				}
			}
			if(notepad.hover(x,y)){
				screenStatus = "Notepad";
			}
			if(envelope.hover(x,y)){
				screenStatus = "Envelope Results";
			}
		}

		if(screenStatus.equals("Notepad")||screenStatus.equals("Envelope Results")){
			if(home.hover(x,y)){
				screenStatus = "Start";
			} 
		}
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	
}
