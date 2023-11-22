package Game;

public class Box {
	private int box_size;
	public Box(int size) { box_size = size; }
	public boolean inHorizontalContact(int x_position) {
		return x_position <=0 || x_position >= box_size;
	}
	public boolean inVerticalContact(int y_position) {
		return y_position <=0 || y_position >= box_size;
	}
	public int sizeOf() { return box_size; }
}