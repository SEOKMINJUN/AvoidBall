package Game.Entity;

public class Box extends Entity{
	private int width;
	private int height;
	
	//생성자, 박스의 넓이, 높이의 초기값 설정
	public Box(int width, int height) {
		super("Box",0,0);
		this.width = width;
		this.height = height;
	}
	//생성자, 박스의 위치, 넓이, 높이의 초기값 설정
	public Box(int x, int y, int width, int height) {
		super("Box",x,y);
		this.width = width;
		this.height = height;
	}
	//넓이 반환
	public int getWidth(){return width;}
	//높이 반환
	public int getHeight(){return height;}


	//수평적으로 주어진 2차원 선분이 박스의 외부(또는 겹침)에 있는지 확인
	public boolean inHorizontalContactLine(int x_1, int x_2) {
		if(x_1 > x_2){
			int temp = x_1;
			x_1 = x_2;
			x_2 = temp;
		}
		int x_pos = getXPos();
		return x_1 <=x_pos || x_2 >= width+x_pos;
	}


	//수직적으로 주어진 2차원 선분이 박스의 외부(또는 겹침)에 있는지 확인
	public boolean inVerticalContactLine(int y_1, int y_2) {
		if(y_1 > y_2){
			int temp = y_1;
			y_1 = y_2;
			y_2 = temp;
		}
		int y_pos = getYPos();
		return y_1 <=y_pos || y_2 >= height+y_pos;
	}

	public boolean run(Box box, int time_units){
		return true;
	}
}