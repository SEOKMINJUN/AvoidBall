package Game.Writer;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class EndScreenWriter extends JPanel{
	public JFrame frame;
	public JTextArea rank_text;
	public EndScreenWriter(JFrame frame) {
		this.frame = frame;
		frame.getContentPane().removeAll();
		frame.getContentPane().add(this);

		rank_text = new JTextArea(11,5);
		rank_text.setBounds(300,100,1000,700);
		rank_text.setEditable(false);
		rank_text.setFocusable(true);
		rank_text.setOpaque(true);
		rank_text.setWrapStyleWord(false);
		rank_text.setFont(new Font("Consolas", Font.PLAIN, 16));
		add(rank_text);
	}

	public void paintComponent(Graphics g){
		frame.repaint();
	}
}
