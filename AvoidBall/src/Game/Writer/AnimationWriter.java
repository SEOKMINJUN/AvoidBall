package Game.Writer;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;

import javax.swing.*;

public class AnimationWriter extends JPanel{
	//상수로 바꿀
	private int MAX_ANIMATION = 256;
	private JPanel animation_list[];
	private int last_animation_id;
	private JFrame frame;
	private JTextArea pointText;
	public AnimationWriter(int width, int height) {
		MAX_ANIMATION = 256;
		animation_list = new JPanel[MAX_ANIMATION];
		last_animation_id = 0;
		
        frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("AvoidBall");
        frame.setSize(width, height);
        frame.setVisible(true);

		pointText = new JTextArea();
		pointText.setEditable(false);
		pointText.setFocusable(false);
		pointText.setOpaque(false);
		pointText.setWrapStyleWord(false);
		pointText.removeMouseMotionListener(pointText.getMouseMotionListeners()[0]);
		pointText.removeMouseMotionListener(pointText.getMouseMotionListeners()[0]);
		System.out.println(Integer.toString(pointText.getMouseMotionListeners().length));
		add(pointText);
		updatePointText(0);
		frame.addMouseMotionListener(null);
	}
	public JTextArea getPointText(){
		return pointText;
	}

	public boolean register(JPanel animation) {
		if(last_animation_id >= MAX_ANIMATION) {
			System.out.println("[ERROR] Failed to register entity to AnimationController, So many animation.");
			return false;
		}
		animation_list[last_animation_id++] = animation;
		return true;
	}

	public void updatePointText(int point){
		pointText.setText("Score : "+Integer.toString(point));
	}

	public void printAnimation(){
		for(int i=0;i<last_animation_id;i++){
			System.out.printf("%s \n",animation_list[i].getClass());
		}
	}

	public boolean removeAnimation(int id){
		if(last_animation_id <= id)
			return false;
		System.out.printf("[INFO] Remove animation type:%s id:%d\n",animation_list[id].getClass(),id);
		for(int i=id;i<last_animation_id;i++){
			animation_list[i] = animation_list[i+1];
			last_animation_id -= 1;
		}
		return true;
	}

	public void paintComponent(Graphics g){
		for(int i=0;i<last_animation_id;i++){
			try{
				animation_list[i].paint(g);
			}
			catch(Exception e){
				System.out.println("[ERROR] Failed animation draw");
			}
		}
		frame.repaint();
	}
}
