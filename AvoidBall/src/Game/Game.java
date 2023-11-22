package Game;

import java.awt.*;

public class Game {

	public static void main(String[] args) {
		Box box = new Box(280);
		MovingBall moving_ball = new MovingBall(2, 2, 2, box);
		BallWriter ball_writer = new BallWriter(moving_ball, Color.GREEN);
		BoxWriter box_writer = new BoxWriter(box);
		AnimationWriter ani_writer = new AnimationWriter(box_writer, ball_writer, 300);
		BounceController controller = new BounceController(moving_ball, ani_writer);
		
		controller.runAnimation();
	}

}
