package Game.Entity;

import java.awt.Color;

public class MovingBall extends Ball
{
	private int x_velocity = 5;
	private int y_velocity = 2;
	//생성자
	public MovingBall(int x, int y, int r) {
		super("MovingBall",x, y, r, Color.BLACK);
	}
	//생성자(초기 Velocity 값 설정)
	public MovingBall(int x, int y, int r, int x_velo, int y_velo) {
		super("MovingBall",x, y, r, Color.BLACK);
		x_velocity = x_velo;
		y_velocity = y_velo;
	}

	//흐른 시간을 기준으로 위치 이동
	public void move(Box container, int time_units) {
		int x_pos = getXPos();
		int y_pos = getYPos();

		setXPos(x_pos + x_velocity * time_units);
		if(container.inHorizontalContactLine(x_pos-getRadius(),x_pos+getRadius())){
			setXPos(x_pos - x_velocity * time_units);
			x_velocity = -x_velocity;
		}

		setYPos(y_pos + y_velocity * time_units);
		if(container.inVerticalContactLine(y_pos-getRadius(),y_pos+getRadius())){
			setYPos(y_pos - y_velocity * time_units);
			y_velocity = -y_velocity;
		}
	}

	//실행
	public boolean run(Box box, int time_units){
		move(box, time_units);
		return true;
	}
}