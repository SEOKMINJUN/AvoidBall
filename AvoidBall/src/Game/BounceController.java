package Game;

public class BounceController {
	private MovingBall ball; // model object
	private AnimationWriter writer; // output-view object
	public BounceController(MovingBall b, AnimationWriter w)
	{ ball = b; writer=w; }
	public void runAnimation() {
		int time_unit = 1;
		int painting_delay = 20;
		while (true)
		{
			delay(painting_delay);
			ball.move(time_unit);
			writer.repaint();
		}
	}
	private void delay(int how_long) {
		try { Thread.sleep(how_long); }
		catch (InterruptedException e) { }
	}
}