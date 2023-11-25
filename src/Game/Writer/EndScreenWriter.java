package Game.Writer;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class EndScreenWriter extends JPanel{
	public JFrame frame;
	public JTextArea rank_text;
	public EndScreenWriter(JFrame frame) {
		//게임 화면 정리 및 해당 패널을 화면에 추가
		this.frame = frame;
		frame.getContentPane().removeAll();
		frame.getContentPane().add(this);

		//랭킹 텍스트 화면에 추가
		rank_text = new JTextArea();
		rank_text.setEditable(false);
		rank_text.setFocusable(true);
		rank_text.setOpaque(true);
		//Monospaced font 사용해 글자 별 넓이 동일하게해 랭킹 출력시 통일감 줌
		rank_text.setFont(new Font("Consolas", Font.PLAIN, 16));
		add(rank_text);
	}

	public void paintComponent(Graphics g){
		frame.repaint();
	}
}
