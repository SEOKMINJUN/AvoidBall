package Game.Entity;

import java.awt.Color;

public class Coin extends Ball
{
	private int x_velocity = 0;
	private int y_velocity = 0;
	public Coin(int x, int y, int r) {
		super("Coin",x, y, r, Color.YELLOW);
	}
	public Coin(int x, int y, int r, int x_velo, int y_velo) {
		super("Coin",x, y, r, Color.YELLOW);
		x_velocity = x_velo;
		y_velocity = y_velo;
	}

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