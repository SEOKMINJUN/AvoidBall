package Game.Entity;

public abstract class Entity {
	private int x_pos;
	private int y_pos;
	private String type;
	public Entity(String classname, int x, int y) {
		type=classname;
		x_pos=x; 
		y_pos=y;
	}
	public String getType(){return type;}
	public int getXPos() { return x_pos; }
	public int getYPos() { return y_pos; }

	public void setXPos(int x) { x_pos = x; }
	public void setYPos(int y) { y_pos = y; }

	public abstract boolean run(Box box, int time_units);
}
