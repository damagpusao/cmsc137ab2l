import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BummRoyale extends JFrame implements ActionListener, MouseListener{
	

	//First panel to show (Title Page)
	JLabel title = new JLabel("BUMM! Royale");
	JButton howToPlay	= new JButton("HOW TO PLAY");
	JButton play = new JButton("PLAY");
	MainPanel mainMenu = new MainPanel(title, howToPlay, play);

	//Instructions Panel
	JLabel howToLabel = new JLabel("How to Play:");
	JButton backToMenu1 = new JButton("BACK TO MENU");
	JButton proceed = new JButton("START");
	InstructionsPanel howToFrame = new InstructionsPanel(howToLabel, backToMenu1, proceed);

	//Create Character Page
	JLabel createCharLabel = new JLabel("Create Character:");
	JButton go = new JButton("ENTER ARENA!");
	JButton backToMenu2 = new JButton("BACK TO MENU");
	CreateCharPanel createChar = new CreateCharPanel(createCharLabel, go, backToMenu2);
	
	//Choose Team Panel
	JLabel chooseTeamLabel = new JLabel("Choose a Team!");
	JButton backToChar = new JButton("BACK");	
	JButton backToMenu3 = new JButton("BACK TO MENU");
	JLabel A_B = new JLabel("A                                   B");
	JPanel teamA0 = new JPanel();
	JPanel teamA1 = new JPanel();
	JPanel teamA2 = new JPanel();
	JPanel teamA3 = new JPanel();
	JPanel teamB0 = new JPanel();
	JPanel teamB1 = new JPanel();
	JPanel teamB2 = new JPanel();
	JPanel teamB3 = new JPanel();
	JButton ready = new JButton("READY!");
	JButton start = new JButton("START");
	ChatGUI chat1 = new ChatGUI();
	TCPClient chatClient;
	
	JPanel lobbyPanel = new JPanel();
	String ipaddress;
	ChooseTeamPanel pickTeam = new ChooseTeamPanel(chooseTeamLabel, A_B, teamA0, teamA1, teamA2, teamA3, teamB0, teamB1, teamB2, teamB3, backToChar, backToMenu3, ready, start);

	BummRoyaleGame game;
	//Game Start Page
	JButton quit = new JButton("QUIT");
	GameStartPanel gameStart;


	//Character character;
	public BummRoyale(String ipaddress, String playerName) throws Exception{
		//sets main frame to menu
		this.game = new BummRoyaleGame(this);
		this.gameStart = new GameStartPanel(quit, ipaddress, playerName, game);
		this.ipaddress = ipaddress;

		this.ipaddress = ipaddress;
		setContentPane(mainMenu);

		howToPlay.addActionListener(this);
		proceed.addActionListener(this);
		play.addActionListener(this);
		backToMenu1.addActionListener(this);
		go.addActionListener(this);
		backToMenu2.addActionListener(this);
		backToChar.addActionListener(this);
		backToMenu3.addActionListener(this);
		start.addActionListener(this);
		quit.addActionListener(this);

		
		//specifications for window
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); //gets the screensize
		int screenHeight = screenSize.height; //gets the screen height
		int screenWidth = screenSize.width; //gets the screen width
		
		setBounds(0,0,1200,700);
		//setBounds(screenWidth/6,screenHeight/16,1200,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		
	}
	
	//for ActionListener
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == howToPlay){
			setContentPane(howToFrame);
		}else if (e.getSource() == play || e.getSource() == proceed){
			setContentPane(createChar);
		}else if (e.getSource() == backToMenu1 || e.getSource() == backToMenu2 || e.getSource() == backToMenu3 ){
			setContentPane(mainMenu);
		}else if (e.getSource() == go){	
			System.out.println(createChar.getCharacter().getName());
			this.chatClient = new TCPClient(createChar.getCharacter().getName(),ipaddress);
			chatClient.addGUI(chat1);
			chat1.addClient(chatClient);
			chatClient.start();
			lobbyPanel.setLayout(new BorderLayout());
			lobbyPanel.add(pickTeam,BorderLayout.CENTER);
			lobbyPanel.add(chat1, BorderLayout.PAGE_END);
			setContentPane(lobbyPanel);
		}else if (e.getSource() == start){
			System.out.println("startbtn");
			JPanel mainGame = new JPanel();
			createChar.getCharacter().setTeam(1); //temporary
			gameStart.setCharacter(createChar.getCharacter());
			game.setCharacter(createChar.getCharacter());
			mainGame.setLayout(new BorderLayout());
			mainGame.add(chat1,BorderLayout.PAGE_END);
			mainGame.add(gameStart,BorderLayout.CENTER);
			gameStart.start();
			setContentPane(mainGame);
		}else if (e.getSource() == backToChar){
			setContentPane(createChar);
		}
		else if( e.getSource() == quit) {
			chatClient.stopClient();
			gameStart.stop();
			setContentPane(mainMenu);

		}
		setVisible(true);
	}

	
	//for MouseListener
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	
	//main function
	public static void main(String args[]) throws Exception{
		if (args.length != 2){
			System.out.println("Usage: java BummRoyale <server ip address> <player name>");
			System.exit(1);
		}

		new BummRoyale(args[0],args[1]);
	}	
}

