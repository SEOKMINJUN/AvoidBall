package Game.Entity;

public abstract class Entity {
	private int x_pos;		// 엔티티의 x 좌표
	private int y_pos;		// 엔티티의 y 좌표
	private String type;	//어떤 엔티티인지 String으로 명시, 클래스 이름으로도 비슷한 작동이 가능하지만 유연한 관리를 위해 사용.
	//생성자
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
