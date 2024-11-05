import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.event.*; 


public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	
	private BufferedImage back; 
	private int key, x, y; 

    private HashMap <String, ArrayList<String>> cardDeck;
    private HashMap <String, String> murderInfo;
    private ArrayList<Card> cards;

	
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
        cards = setCards();
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
		
		g2d.drawString("Hello!" , x, y);
		
		//System.out.println(cardDeck);
        System.out.println("Selected Murder Info: "+murderInfo);
        removeMurderFromCardDeck();
        System.out.println(cardDeck);
		twoDgraph.drawImage(back, null, 0, 0);

	}

  
    public ArrayList<Card> setCards(){
        ArrayList<Card> temp = new ArrayList<Card>();
        return temp;
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
