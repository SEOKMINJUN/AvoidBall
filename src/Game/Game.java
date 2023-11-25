package Game;

import Game.Entity.Box;
import Game.Entity.EntityController;
import Game.Writer.AnimationWriter;
import Game.Writer.BoxWriter;

public class Game {
	/*
	 * TODO
	 * 주석 정리해야함
	 */
	public static void main(String[] args) {

		AnimationWriter animation_writer = new AnimationWriter(1280, 720);
		EntityController entity_controller = new EntityController();

		Box box = new Box(-60,-60,1280+60*2,720+60*2);
		BoxWriter box_writer = new BoxWriter(box);
		entity_controller.setBox(box);
		entity_controller.register(box);
		animation_writer.register(box_writer);
		
		GameController game_controller = new GameController(entity_controller, animation_writer);

		
		game_controller.runGame();
	}

}
