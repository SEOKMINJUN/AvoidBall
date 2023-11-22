package Game.Writer;

import java.awt.*;

import Game.Entity.Box;
import javax.swing.JPanel;

public class BoxWriter extends JPanel{
	private Box box; // the (address of the) box object that is displayed
	/** Constructor BoxWriter displays the box
	  * @param b - the box that is displayed */
	public BoxWriter(Box b){ box = b; }

	/** paint paints the box
	  * @param g - the graphics pen used to paint the box */
	public void paint(Graphics g){ 
		int width = box.getWidth();
		int height = box.getHeight();
		
		g.setColor(Color.white);
		g.fillRect(box.getXPos(), box.getYPos(), width, height);
		g.setColor(Color.black);
		g.drawRect(box.getXPos(), box.getYPos(), width, height);
	}
}
