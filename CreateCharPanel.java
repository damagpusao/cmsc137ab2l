import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.*;
public class CreateCharPanel extends JPanel{
	JLabel nameLabel = new JLabel("Avatar Name:");	
//	JTextArea enterName = new JTextArea("Aura");
	JPanel enterNamePanel = new JPanel();
	JLabel createCharLabel;
	JButton go, backToMenu;
	Character character;
	String[] imgs = null;
	Weapon weapon = null;
	
	
	public CreateCharPanel(JLabel createCharLabel, JButton go, JButton backToMenu){
		this.setLayout(null);
		
		this.createCharLabel = createCharLabel;
		this.go = go;
		this.backToMenu = backToMenu;

		init();
	}

	public void init(){

		//Label "Create your Character:"
		createCharLabel.setFont(new Font("Liberation Serif", Font.BOLD,40));
		createCharLabel.setForeground(Color.white);
		createCharLabel.setBounds(440,30,600,60);
/*
		nameLabel.setFont(new Font("Liberation Serif", Font.BOLD,24));
		nameLabel.setBounds(520,420,200,50);
		nameLabel.setForeground(Color.WHITE);
		enterName.setFont(new Font("Liberation Serif", Font.BOLD,20));
		enterName.setBounds(0,0,250,60);
		enterNamePanel.setBounds(470,480,250,60);
		enterNamePanel.add(enterName);
*/
		go.setFont(new Font("Liberation Serif", Font.BOLD,25));
		go.setForeground(Color.white);
		go.setBounds(900,600,250,50);
		go.setBackground(Color.RED);

    ActionListener al = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				charSelect(e);
			}
		};

		JButton char1 = new JButton("char1");
		char1.addActionListener(al);
		char1.setForeground(Color.white);
		char1.setBounds(170,100,200,200);
		try {
			Image img = ImageIO.read(getClass().getResource("images/b_orange-large.png"));
			char1.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		JButton char2 = new JButton("char2");
		
		char2.addActionListener(al);
		char2.setForeground(Color.white);
		char2.setBounds(380,100,200,200);
		try {
			Image img = ImageIO.read(getClass().getResource("images/b_red-large.png"));
			char2.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		JButton char3 = new JButton("char3");
		char3.addActionListener(al);
		char3.setForeground(Color.white);
		char3.setBounds(590,100,200,200);
		try {
			Image img = ImageIO.read(getClass().getResource("images/g_pink-large.png"));
			char3.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}


		JButton char4 = new JButton("char4");
		char4.addActionListener(al);
		char4.setForeground(Color.white);
		char4.setBounds(800,100,200,200);
		try {
			Image img = ImageIO.read(getClass().getResource("images/g_green-large.png"));
			char4.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		JButton weapon1 = new JButton("Long Range");
		weapon1.addActionListener(al);
		weapon1.setForeground(Color.white);
		weapon1.setBounds(380,310,200,50);
		weapon1.setBackground(Color.RED);

		JButton weapon2 = new JButton("Killer");
		weapon2.addActionListener(al);
		weapon2.setForeground(Color.white);
		weapon2.setBounds(590,310,200,50);
		weapon2.setBackground(Color.RED);


		//"BACK TO MENU" button
		backToMenu.setFont(new Font("Algerian", Font.BOLD,13));
		backToMenu.setForeground(Color.white);
		backToMenu.setBounds(10,10,150,30);
		backToMenu.setBackground(Color.RED);

		this.add(char1);
		this.add(char2);
		this.add(char3);
		this.add(char4);
		this.add(weapon1);
		this.add(weapon2);
		this.add(createCharLabel);
		this.add(nameLabel);		
		this.add(enterNamePanel);
		this.add(go);
		this.add(backToMenu);
		this.setBackground(new Color(10,0,40));
	}

	private void charSelect(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
	   
	    if(btn.getText() == "Long Range") {
			System.out.println("Long Range");
			this.weapon = new Weapon(10,50);
		}

		else if(btn.getText() == "Killer") {
			System.out.println("Killer");
			this.weapon = new Weapon(30,30);
		}

		else if (btn.getText() == "char1" || btn.getText() == "char2" || btn.getText() == "char3" || btn.getText() == "char4"){
			this.character = new Character(btn.getText());
		}
			
	}

	public Character getCharacter() {
		return this.character;
	}



}
