package Game;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JTextArea;

import Game.Entity.*;
import Game.Writer.AnimationWriter;
import Game.Writer.BallWriter;
import Game.Writer.EndScreenWriter;
import Game.Writer.StartInfoWriter;

public class GameController {
	private EntityController entity_controller; // model object
	private AnimationWriter animation_writer; // output-view object
	private int time_unit = 20;
	private int painting_delay = 10;
	private int mouse_x = 0;
	private int mouse_y = 0;
	private int game_status = -1; // 게임 상태, -1 : 설정 안됨, 0 : 게임 전, 1 : 게임 중, 2 : 게임 종료

	private Difficulty difficulty;
	private String name;
	private Random random = new Random();

	private Rank rank;

	public GameController(EntityController ec, AnimationWriter aw){
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
		game_status = 0;
		rank = new Rank();
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
		System.out.println("[INFO] Try to show end screen");
		
		System.out.println("[INFO] Clean entity and animation");
		while(entity_controller.getLastEntityId() <= 0){
			entity_controller.removeEntity(0);
		}
		while(animation_writer.getLastAnimationId() <= 0){
			animation_writer.removeAnimation(0);
		}
		
		
		boolean result;
		if(difficulty.difficulty == 0)
			result = rank.addEasyRank(name, entity_controller.getPlayer().getPoint());
		else
			result = rank.addHardRank(name, entity_controller.getPlayer().getPoint());
		rank.saveRankFile();
		System.out.printf("[INFO] Add rank, result : %b, name : %s, point : %d\n",result,name,entity_controller.getPlayer().getPoint());

		EndScreenWriter end_screen_writer = new EndScreenWriter(animation_writer.getFrame());
		//rank_text.setLocation(128*3,72*2);
		String text = "";
		if(difficulty.difficulty == 0){
			//easy
			text = "Easy rank\n\n";
			text += String.format("Your Point : %d\n\n",entity_controller.getPlayer().getPoint());
			for(int i=0;i<rank.easy_rank_count;i++){
				text += String.format("%6s : %d\n",rank.easy_rank[i].name,rank.easy_rank[i].point);
			}
			end_screen_writer.rank_text.setText(text);
		}	
		else{
			//hard
			text = "Hard rank\n\n";
			text += String.format("Your Point : %d\n\n",entity_controller.getPlayer().getPoint());
			for(int i=0;i<rank.hard_rank_count;i++){
				text += String.format("%6s : %d\n",rank.hard_rank[i].name,rank.hard_rank[i].point);
			}
			end_screen_writer.rank_text.setText(text);
		}

		return;
	}
	public void runGame() {
		int id;
		int runtime=0;
		int last_moving_ball_time = 0;
		int last_coin_time = 0;
		
		StartInfoWriter start_info = new StartInfoWriter();
		difficulty = new Difficulty(start_info.diff);
		this.name = start_info.name;
		game_status = 1;
		
		createPlayerBall();

		while (true)
		{
			delay(painting_delay);
			switch(game_status){
				case 0:
					//아무것도 안함
					System.out.println("[ERROR] Something going wrong! Exit game");
					return;
				case 1:
					//게임 작동 중

					runtime += painting_delay;

					if(runtime == painting_delay*100) {
						createMovingBall();
						createMovingBall();
						createMovingBall();
						createCoin();
					}

					//MovingBall 생성
					if(runtime - last_moving_ball_time > difficulty.spawn_moving_ball_cooltime && entity_controller.getMovingBallCount() <= difficulty.max_moving_ball){
						last_moving_ball_time = runtime;
						createMovingBall();
						System.out.printf("[INFO] Create new moving ball, runtime : %d\n",runtime);
					}

					//Coin 생성
					if(runtime - last_coin_time > difficulty.spawn_coin_cooltime && entity_controller.getCoinCount() <= difficulty.max_coin){
						last_coin_time = runtime;
						createCoin();
						System.out.printf("[INFO] Create new coin, runtime : %d\n",runtime);
					}

					//공 움직이기 + 플레이어 공 위치 옮기기
					entity_controller.run(time_unit);

					if(!(entity_controller.getPlayer() != null && entity_controller.runPlayerBall(mouse_x,mouse_y))){
							//System.err.println("No player ball entity, skip");
							continue;
					}
					//플레이어랑 볼 충돌 테스트
					id = entity_controller.collideMovingBallTest();
					if(id != -1){
						System.out.println("[INFO] Dead");
						game_status = 2;
						showEndScreen();
						entity_controller.setPlayer(null);
						continue;
					}
					//플레이어랑 코인 충돌 테스트
					id = entity_controller.collideCoinTest();
					if(id != -1){
						entity_controller.getPlayer().addPoint(1);
						entity_controller.removeEntity(id);
						animation_writer.removeAnimation(id);
						animation_writer.updatePointText(entity_controller.getPlayer().getPoint());
						System.out.printf("[INFO] Point : %d, Remain coin : %d\n",entity_controller.getPlayer().getPoint(), entity_controller.getCoinCount());
					}
					break;
				case 2:
					//사망 화면

					break;
				default:
					break;
			}
			
			
			animation_writer.repaint();
		}
	}
	
	private void delay(int how_long) {
		try { Thread.sleep(how_long); }
		catch (InterruptedException e) { }
	}
}