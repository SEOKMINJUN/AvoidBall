package Game.Writer;

import java.awt.*;

import Game.Entity.Ball;
import javax.swing.JPanel;


public class BallWriter extends JPanel{
	private Ball ball; // the (address of the) ball object displayed

	/** Constructor BallWriter
	 * @param x - the ball to be displayed*/
	public BallWriter(Ball x){
		ball = x;
	}
	/** paint paints the ball on the view
	 * @param g - the graphics pen used to paint the ball */
	public void paint(Graphics g){
		g.setColor(ball.getColor());
		int radius = ball.getRadius();
		g.fillOval(ball.getXPos() - radius, ball.getYPos() - radius, radius * 2, radius * 2);
	}
}