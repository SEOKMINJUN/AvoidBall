package Game.Entity;

public class Box extends Entity{
	private int width;
	private int height;
	
	public Box(int width, int height) {
		super("Box",0,0);
		this.width = width;
		this.height = height;
	}
	public Box(int x, int y, int width, int height) {
		super("Box",x,y);
		this.width = width;
		this.height = height;
	}
	public int getWidth(){return width;}
	public int getHeight(){return height;}

	public boolean inHorizontalContactPoint(int x) {
		int x_pos = getXPos();
		return x <=x_pos || x >= width+x_pos;
	}
	public boolean inVerticalContactPoint(int y) {
		int y_pos = getYPos();
		return y <=y_pos || y >= height+y_pos;
	}
	public boolean inHorizontalContactLine(int x_1, int x_2) {
		if(x_1 > x_2){
			int temp = x_1;
			x_1 = x_2;
			x_2 = temp;
		}
		int x_pos = getXPos();
		return x_1 <=x_pos || x_2 >= width+x_pos;
	}
	public boolean inVerticalContactLine(int y_1, int y_2) {
		if(y_1 > y_2){
			int temp = y_1;
			y_1 = y_2;
			y_2 = temp;
		}
		int y_pos = getYPos();
		return y_1 <=y_pos || y_2 >= height+y_pos;
	}
	public boolean inBox(int x, int y){
		int x_pos = getXPos();
		int y_pos = getYPos();
		return (x <=x_pos || x >= width+x_pos) && (y <=y_pos || y >= height+y_pos);
	}

	public boolean run(Box box, int time_units){
		return true;
	}
}