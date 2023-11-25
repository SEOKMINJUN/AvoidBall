package Game.Entity;

import java.awt.Color;

public class PlayerBall extends Ball
{
	int point; //얻은 포인트

	//생성자
	public PlayerBall(int x, int y, int r) {
		super("PlayerBall",x, y, r, Color.GREEN);
		point = 0;
	}

	public int getPoint(){return point;}
	public void addPoint(int p){
		point += p;
	}
	public void move(int x, int y) {
		setXPos(x);
		setYPos(y);
	}

	public boolean run(Box box, int time_unit){
		return true;
	}

	public boolean runPlayerBall(int x, int y){
		move(x,y);
		return true;
	}
}