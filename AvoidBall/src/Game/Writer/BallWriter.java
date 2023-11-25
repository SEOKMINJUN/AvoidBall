package Game.Writer;

import java.awt.*;

import Game.Entity.Ball;
import javax.swing.JPanel;


public class BallWriter extends JPanel{
	private Ball ball;

	//생성자
	public BallWriter(Ball x){
		ball = x;
	}
	
	//그리기
	public void paint(Graphics g){
		g.setColor(ball.getColor());
		int radius = ball.getRadius();
		g.fillOval(ball.getXPos() - radius, ball.getYPos() - radius, radius * 2, radius * 2);
	}
}