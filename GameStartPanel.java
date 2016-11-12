import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameStartPanel extends JPanel{	
	Image image = Toolkit.getDefaultToolkit().getImage("image.png");
	Image fish = Toolkit.getDefaultToolkit().getImage("fish.png");
	int x=200, y=200;
	JButton quit;
	JPanel chat;

	public GameStartPanel(JButton quit, JPanel chat){
		this.setLayout(null);

		this.quit = quit;
		this.chat = chat;

		init();
	}

	public void init(){
		quit.setFont(new Font("Algerian", Font.BOLD,13));
		quit.setForeground(Color.white);
		quit.setBounds(10,10,100,30);
		quit.setBackground(Color.RED);

		//Chat panel
		chat.setBounds(0,620,1200,100);

		this.add(quit);
		this.add(chat);

		this.setBackground(new Color(10,0,40));
	}


	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		
		g.drawImage(image,0,0,this); //draws the image "welcome.jpg"
		g.drawImage(fish, x,y,this);
	}


}
