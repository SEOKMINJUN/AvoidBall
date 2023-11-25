package Game;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import Game.Entity.*;
import Game.Writer.AnimationWriter;
import Game.Writer.BallWriter;
import Game.Writer.StartInfoWriter;

public class GameController {
	private EntityController entity_controller; // model object
	private AnimationWriter animation_writer; // output-view object
	private int time_unit = 20;
	private int painting_delay = 10;
	private int mouse_x = 0;
	private int mouse_y = 0;

	private Difficulty difficulty;

	private Random random = new Random();

	public GameController(EntityController ec, AnimationWriter aw,int stage){
		entity_controller = ec;
		animation_writer = aw; 
		aw.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event){
				mouse_x = event.getX();
				mouse_y = event.getY();
				//System.out.printf("\r[M] x: %d y: %d",mouse_x,mouse_y);
			}
		});
		aw.getPointText().addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event){
				Rectangle r = aw.getPointText().getBounds();
				mouse_x = event.getX()+r.x;
				mouse_y = event.getY()+r.y;
				//System.out.printf("\r[M] x: %d y: %d",mouse_x,mouse_y);
			}
		});
		time_unit = 1;
		difficulty = new Difficulty(stage);
	}

	int _randomPickBallXPos(int r){
		return random.nextInt(1280 - r - r + 1) + r;
	}

	int _randomPickBallYPos(int r){
		return random.nextInt(720 - r - r + 1) + r;
	}

	public void createMovingBall(){
		int r = 12;
		int x = _randomPickBallXPos(r);
		int y = _randomPickBallYPos(r);
		int x_velo = random.nextInt(15)-8;
		int y_velo = random.nextInt(15)-8;
		MovingBall ball = new MovingBall(x,y,r,x_velo,y_velo);
		BallWriter ball_writer = new BallWriter(ball);
		entity_controller.register(ball);
		animation_writer.register(ball_writer);
	}

	public void createCoin(){
		int r = 12;
		int x = _randomPickBallXPos(r);
		int y = _randomPickBallYPos(r);
		int x_velo = 0;
		int y_velo = 0;
		Coin ball = new Coin(x,y,r,x_velo,y_velo);
		BallWriter ball_writer = new BallWriter(ball);
		entity_controller.register(ball);
		animation_writer.register(ball_writer);
	}

	public void createPlayerBall(){
		if(entity_controller.getPlayer() != null){
			System.out.println("[ERROR] Try to override player ball");
			return;
		}
		PlayerBall player = new PlayerBall(500, 500, 15);
		BallWriter ball_writer = new BallWriter(player);
		entity_controller.setPlayer(player);
		entity_controller.register(player);
		animation_writer.register(ball_writer);
	}

	public void showEndScreen(){
		//TODO implement
		return;
	}
	public void runGame() {
		int id;
		int runtime=0;
		int last_moving_ball_time = 0;
		int last_coin_time = 0;
		
		StartInfoWriter start_info = new StartInfoWriter();
		if(start_info != null)
			return;
		
		while (true)
		{
			delay(painting_delay);
			runtime += painting_delay*5;
			if(runtime % 50000 == 0) {
				System.out.printf("[DEBUG] Entity : %d / Anim : %d / MovingBall : %d / Coin : %d\n", entity_controller.getLastEntityId(), animation_writer.getLastAnimationId(), entity_controller.getMovingBallCount(), entity_controller.getCoinCount());
			}
			if(runtime - last_moving_ball_time > difficulty.spawn_moving_ball_cooltime && entity_controller.getMovingBallCount() <= difficulty.max_moving_ball){
				last_moving_ball_time = runtime;
				createMovingBall();
				System.out.printf("[INFO]Create new moving ball at %d\n",runtime);
			}
			if(runtime - last_coin_time > difficulty.spawn_coin_cooltime && entity_controller.getCoinCount() <= difficulty.max_coin){
				last_coin_time = runtime;
				createCoin();
				System.out.printf("[INFO]Create new coin at %d\n",runtime);
			}
			entity_controller.run(time_unit);
			if(!(entity_controller.getPlayer() != null && entity_controller.runPlayerBall(mouse_x,mouse_y))){
					//System.err.println("No player ball entity, skip");
					continue;
			}
			id = entity_controller.collideMovingBallTest();
			if(id != -1){
				/*System.out.println("죽음");
				showEndScreen();
				entity_controller.setPlayer(null);*/
				continue;
			}
			id = entity_controller.collideCoinTest();
			if(id != -1){
				entity_controller.getPlayer().addPoint(1);
				entity_controller.removeEntity(id);
				animation_writer.removeAnimation(id);
				animation_writer.updatePointText(entity_controller.getPlayer().getPoint());
				System.out.printf("점수 증가 : %d, 남은 코인 : %d\n",entity_controller.getPlayer().getPoint(), entity_controller.getCoinCount());
			}
			animation_writer.repaint();
		}
	}
	
	private void delay(int how_long) {
		try { Thread.sleep(how_long); }
		catch (InterruptedException e) { }
	}
}