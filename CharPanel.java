import java.awt.*;
import javax.swing.*;


//A class for drawing the avatar
public class CharPanel extends JPanel {
		ImageIcon img;	

		public CharPanel(){
			this.img = new ImageIcon(this.getClass().getResource("fish.png"));

		}
		
		//draws the avatar to the panel
		public void paintComponent(Graphics g) {

			super.paintComponent(g); 
		  img.paintIcon(this, g, 0,0);
		}

	}

