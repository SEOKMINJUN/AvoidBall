package Game.Entity;

import Game.Box;

public class Entity {
	private int x_pos;
	private int y_pos;

	public Entity(int x, int y) {
		x_pos=x; y_pos=y;
	}
	public int GetXPos() { return x_pos; }
	public int GetYPos() { return y_pos; }
	public void NextFrame() {
		return;
	}
}
