package Game.Writer;

import java.awt.Graphics;

import javax.swing.*;

public class AnimationWriter extends JPanel{
	private int MAX_ANIMATION;	//저장할 수 있는 에니메이션(Writer) 최대 갯수
	private JPanel animation_list[];	//에니메이션(Writer) 목록
	private int last_animation_id;	//현재 저장하고 있는 애니메이션 갯수 / 마지막 애니메이션 번호(리스트는 0부터 시작하기 떄문에 -1을 사용해야함)
	private JFrame frame;	// 게임 창
	private JTextArea point_text;	// 현재 얻은 점수

	//생성자
	public AnimationWriter(int width, int height) {
		MAX_ANIMATION = 256;
		animation_list = new JPanel[MAX_ANIMATION];
		last_animation_id = 0;
		
		//게임 창 설정
        frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("AvoidBall");
        frame.setSize(width, height);
		frame.setResizable(false);
        frame.setVisible(true);

		// 점수 표시
		point_text = new JTextArea();
		point_text.setEditable(false);
		point_text.setFocusable(false);
		point_text.setOpaque(false);
		point_text.setWrapStyleWord(false);
		add(point_text);
		updatePointText(0);
	}
	
	//point_text에 지정된 텍스트 반환
	public JTextArea getPointText(){
		return point_text;
	}

	//animation(writer) 등록
	public boolean register(JPanel animation) {
		if(last_animation_id >= MAX_ANIMATION) {
			return false;
		}
		animation_list[last_animation_id++] = animation;
		return true;
	}

	//point_text 텍스트 갱신
	public void updatePointText(int point){
		point_text.setText("Score : "+Integer.toString(point));
	}

	//현재 가지고 있는 Animation(Writer) 출력
	public void printAnimation(){
		for(int i=0;i<last_animation_id;i++){
			System.out.printf("%s \n",animation_list[i].getClass());
		}
	}
	
	//last_animation_id 값 반환
	public int getLastAnimationId() {
		return last_animation_id;
	}

	//animation_list에서 특정 원소 삭제
	//뒷 원소를 앞 원소에 덮어씌우는 방식
	public boolean removeAnimation(int id){
		if(last_animation_id <= id)
			return false;
		for(int i=id;i<last_animation_id;i++){
			animation_list[i] = animation_list[i+1];
		}
		last_animation_id -= 1;
		return true;
	}

	//화면에 그리기
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

	//게임 창(Frame) 반환
	public JFrame getFrame(){
		return frame;
	}

}
