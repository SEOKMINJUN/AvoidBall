package Game.Writer;

import java.awt.*;

import Game.Entity.Box;
import javax.swing.JPanel;

public class BoxWriter extends JPanel{
	private Box box; // 박스 (게임 영역)
	
	//생성자
	public BoxWriter(Box b){ box = b; }

	//박스 그리기
	public void paint(Graphics g){ 
		int width = box.getWidth();
		int height = box.getHeight();
		
		g.setColor(Color.white);
		g.fillRect(box.getXPos(), box.getYPos(), width, height);
		g.setColor(Color.black);
		g.drawRect(box.getXPos(), box.getYPos(), width, height);
	}
}
