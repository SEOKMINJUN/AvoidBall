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
	private JTextArea point_text;
	public AnimationWriter(int width, int height) {
		MAX_ANIMATION = 256;
		animation_list = new JPanel[MAX_ANIMATION];
		last_animation_id = 0;
		
        frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("AvoidBall");
        frame.setSize(width, height);
        frame.setVisible(true);

		point_text = new JTextArea();
		point_text.setEditable(false);
		point_text.setFocusable(false);
		point_text.setOpaque(false);
		point_text.setWrapStyleWord(false);
		point_text.removeMouseMotionListener(point_text.getMouseMotionListeners()[0]);
		point_text.removeMouseMotionListener(point_text.getMouseMotionListeners()[0]);
		System.out.println(Integer.toString(point_text.getMouseMotionListeners().length));
		add(point_text);
		updatePointText(0);
		frame.addMouseMotionListener(null);
	}
	public JTextArea getPointText(){
		return point_text;
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
		point_text.setText("Score : "+Integer.toString(point));
	}

	public void printAnimation(){
		for(int i=0;i<last_animation_id;i++){
			System.out.printf("%s \n",animation_list[i].getClass());
		}
	}
	
	public int getLastAnimationId() {
		return last_animation_id;
	}

	public boolean removeAnimation(int id){
		if(last_animation_id <= id)
			return false;
		System.out.printf("[INFO] Remove animation type:%s id:%d\n",animation_list[id].getClass(),id);
		for(int i=id;i<last_animation_id;i++){
			System.out.printf("[INFO] Override anim id:%d\n",i);
			animation_list[i] = animation_list[i+1];
		}
		last_animation_id -= 1;
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

	public JFrame getFrame(){
		return frame;
	}

}
