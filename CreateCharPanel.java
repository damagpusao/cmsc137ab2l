import java.awt.*;
import javax.swing.*;

public class CreateCharPanel extends JPanel{
	JLabel nameLabel = new JLabel("Avatar Name:");	
	JTextArea enterName = new JTextArea("Aura");
	JPanel enterNamePanel = new JPanel();
	
	
	public CreateCharPanel(JLabel createCharLabel, JButton go, JButton backToMenu){
		this.setLayout(null);
		
		//Label "Create your Character:"
		createCharLabel.setFont(new Font("Liberation Serif", Font.BOLD,40));
		createCharLabel.setForeground(Color.white);
		createCharLabel.setBounds(440,30,600,60);

		nameLabel.setFont(new Font("Liberation Serif", Font.BOLD,24));
		nameLabel.setBounds(520,300,200,50);
		nameLabel.setForeground(Color.WHITE);
		enterName.setFont(new Font("Liberation Serif", Font.BOLD,20));
		enterName.setBounds(0,0,250,60);
		enterNamePanel.setBounds(470,350,250,60);
		enterNamePanel.add(enterName);

		go.setFont(new Font("Liberation Serif", Font.BOLD,25));
		go.setForeground(Color.white);
		go.setBounds(900,650,250,50);
		go.setBackground(Color.RED);

		//"BACK TO MENU" button
		backToMenu.setFont(new Font("Algerian", Font.BOLD,13));
		backToMenu.setForeground(Color.white);
		backToMenu.setBounds(10,10,150,30);
		backToMenu.setBackground(Color.RED);


		this.add(createCharLabel);
		this.add(nameLabel);		
		this.add(enterNamePanel);
		this.add(go);
		this.add(backToMenu);
		this.setBackground(new Color(10,0,40));
	}
}