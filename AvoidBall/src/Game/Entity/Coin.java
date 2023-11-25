package Game.Entity;

import java.awt.Color;

public class Coin extends Ball
{
	//현재는 코인을 움직이지 않지만, 나중의 변경을 위해 만들어둠.
	private int x_velocity = 0;	//x축 속도
	private int y_velocity = 0;	//y축 속도

	//생성자
	public Coin(int x, int y, int r) {
		super("Coin",x, y, r, Color.YELLOW);
	}

	//생성자, 속도의 초기값을 설정 가능
	public Coin(int x, int y, int r, int x_velo, int y_velo) {
		super("Coin",x, y, r, Color.YELLOW);
		x_velocity = x_velo;
		y_velocity = y_velo;
	}

	//시간을 기준으로한 엔티티 위치 변경
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

	public boolean run(Box box, int time_units){
		move(box, time_units);
		return true;
	}
}