package Game;

import java.awt.*;

import Game.Entity.Box;
import Game.Entity.EntityController;
import Game.Entity.MovingBall;
import Game.Writer.AnimationWriter;
import Game.Writer.BallWriter;
import Game.Writer.BoxWriter;

public class Game {
	/*
	 * TODO
	 * 창 크기 변경 막기
	 * 게임 끝나면 보여주는 창 만들기
	 */
	public static void main(String[] args) {
		AnimationWriter animation_writer = new AnimationWriter(1280, 720);
		EntityController entity_controller = new EntityController();
		GameController game_controller = new GameController(entity_controller, animation_writer,0);

		Box box = new Box(-60,-60,1280+60*2,720+60*2);
		BoxWriter box_writer = new BoxWriter(box);
		entity_controller.setBox(box);
		entity_controller.register(box);
		animation_writer.register(box_writer);

		game_controller.createPlayerBall();
		game_controller.createMovingBall();
		game_controller.createMovingBall();
		game_controller.createMovingBall();
		game_controller.createMovingBall();
		game_controller.createCoin();
		
		game_controller.runGame();
	}

}
