import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameStartPanel extends JPanel implements KeyListener, Runnable, Constants{	

	Image map = Toolkit.getDefaultToolkit().getImage("images/bg.jpg");
	JButton quit;
	Character character;
	CharPanel charPanel;
	BummRoyaleGame game;
	WeaponGUI weaponG;
	Boolean isRunning;

	/**
	 * Player position, speed etc.
	 */
	int x=10,y=10,xspeed=2,yspeed=2,prevX,prevY;

	/**
	 * Game timer, handler receives data from server to update game state
	 */
	Thread t=new Thread(this);
	
	/**
	 * Nice name!
	 */
	String name="SarIanDananDan";
	
	/**
	 * Player name of others
	 */
	String pname;
	
	/**
	 * Server to connect to
	 */
	String server="localhost";

	/**
	 * Flag to indicate whether this player has connected or not
	 */
	boolean connected=false;
	
	/**
	 * get a datagram socket
	 */
    DatagramSocket socket = new DatagramSocket();

	
    /**
     * Placeholder for data received from server
     */
	String serverData;
	
	/**
	 * Offscreen image for double buffering, for some
	 * real smooth animation :)
	 */
	BufferedImage offscreen;

	public GameStartPanel(JButton quit, String server, String name, BummRoyaleGame game) throws Exception{
		this.setLayout(null);
		this.quit = quit;
		this.game = game;
		this.isRunning = true;
		this.server = server;
		this.name = name;

		//set some timeout for the socket
		socket.setSoTimeout(100);
		
		//create the buffer
		offscreen=(BufferedImage)this.createImage(1100, 700);
		
		//Adds listeners
		//this.addKeyListener(new KeyHandler());		
		//this.addMouseMotionListener(new MouseMotionHandler());
		addKeyListener(this);

		//time to play
		t.start();
	}

	public void init(){
		ControlPane cp = new ControlPane(game);
		cp.setBounds(110,0,1090,50);
		quit.setFont(new Font("Algerian", Font.BOLD,13));
		quit.setForeground(Color.white);
		quit.setBounds(10,10,100,30);
		quit.setBackground(Color.RED);		

		//creates the panel containing the avatar
	    charPanel = new CharPanel(this.character);	


		//random placement of character (player)
		Random r = new Random();
		int xRand = r.nextInt(1100 - 100 + 1) + 100;
		charPanel.setLocation(xRand,500);
		charPanel.setSize(80,70);
		charPanel.setOpaque(false);
		character.setPos(xRand,500);

		weaponG = new WeaponGUI();
		weaponG.setSize(80,70);
		weaponG.setOpaque(false);
		weaponG.setLocation(xRand+3,517);

		//adds action to the panel containing the avatar
		MoveAvatars animation = new MoveAvatars(charPanel,weaponG, 24);
		animation.addAction("LEFT", -1,  0);
		animation.addAction("RIGHT", 1,  0);
		//animation.addAction("UP",    0, -1);
		//animation.addAction("DOWN",  0,  1);


		JPanel controlsPane = new JPanel();
		controlsPane.setLayout(new FlowLayout());
		JTextField powerField = new JTextField();
		JTextField angle = new  JTextField();


		this.add(cp);
		this.add(quit);
		this.add(weaponG);
		this.add(charPanel);
		this.setVisible(true);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		
		Thread angleAnimation = new Thread () {
			@Override
			public void run() {
				while (isRunning) {
				weaponG.setAngle(game.getAngle()*-1.0);
				weaponG.repaint();  // Refresh the display
				try {
					Thread.sleep(1000 / 10); // delay and yield to other threads
				} catch (InterruptedException ex) { }
				}
			}
		};
		angleAnimation.start();  // start the thread to run animation
	}//end of init


	/**
	 * Helper method for sending data to server
	 * @param msg
	 */
	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
			System.out.println("send to server: "+msg+"\n");
        }catch(Exception e){}
		
	}
	
	/**
	 * The juicy part!
	 */
	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}
						
			//Get the data from players
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
					//System.out.println("Received: \n");
			}catch(Exception ioe){
					//System.out.println("Received failed. \n");
			}
			
			serverData=new String(buf);
			//System.out.println(name+" serverData: "+serverData+"\n");
			serverData=serverData.trim();
			
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				if(serverData!=null) {
					//offscreen.getGraphics().clearRect(0, 0, 1100, 700);
				if (serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for (int i=0;i<playersInfo.length;i++){
						String[] playerInfo = playersInfo[i].split(" ");
						String pname =playerInfo[1];
					
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);
						int hp = Integer.parseInt(playerInfo[4]);
						String look = playerInfo[5];
						System.out.println("x:" + x + " y:" + y);
						//draw on the offscreen image
						//System.out.println("position x&y: "+x+" "+y+"\n");
						this.getGraphics().fillOval(x, y, 20, 20);
						this.getGraphics().drawString(pname,x-10,y+30);					
					}
					//show the changes
					//this.repaint();
				}
		
				}
			}			
		}
	}


	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		
		//draws the map
		g.drawImage(map,0,0,this);
		g.drawImage(offscreen, 0, 0, null);
		//g.fillOval(x, y, 20, 20);
	}

/*	class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){
			prevX = x; prevY = y;
			x = character.xPos; y = character.yPos;
			//x=me.getX();y=me.getY();
			if (prevX != x || prevY != y){
				//send("PLAYER "+name+" "+x+" "+y);
			}				
		}

	class MouseMotionHandler extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent me){
			x=me.getX();y=me.getY();
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}				
		}
	}
	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=x;prevY=y;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_DOWN:y+=yspeed;break;
			case KeyEvent.VK_UP:y-=yspeed;break;
			case KeyEvent.VK_LEFT:x-=xspeed;break;
			case KeyEvent.VK_RIGHT:x+=xspeed;break;
			}
			if (prevX != x || prevY != y){
				send("PLAYER "+name+" "+x+" "+y);
			}	
		}
	}

	
	class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent ke){
			prevX=x;prevY=y;
			switch (ke.getKeyCode()){
			case KeyEvent.VK_DOWN:y+=yspeed;break;
			case KeyEvent.VK_UP:y-=yspeed;break;
			case KeyEvent.VK_LEFT:x-=xspeed;break;
			case KeyEvent.VK_RIGHT:x+=xspeed;break;
			}
			x = character.xPos; y = character.yPos;
			if (prevX != x || prevY != y){
				System.out.println("keyhandler\n");
				send("PLAYER "+name+" "+x+" "+y);
			}	
		}
	}
*/

	public void setCharacter(Character c) {
		this.character = c;
		if(charPanel != null ) this.remove(charPanel);
		init();
	}

	public void start() {
		this.isRunning = true;
	}

	public void stop(){
		this.isRunning = false;
	}

	public WeaponGUI getWeapon() {
		return this.weaponG;
	} 

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch( keyCode ) { 
			case KeyEvent.VK_UP:
				// handle up 
			case KeyEvent.VK_DOWN:
				// handle down
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			default:
			System.out.println("warwarawaraw");
			prevX=x; prevY = y;
			x = character.xPos; y = character.yPos;
			//	if (prevX != x || prevY != y){
			//		System.out.println("keyhandler\n");
			//		if(character != null) {
						send(character.toString());
						//send("PLAYER "+name+" "+x+" "+y+" ");
			//		} 
					
			//	}	
				break;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

}
