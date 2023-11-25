package Game;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import Game.Entity.*;
import Game.Writer.AnimationWriter;
import Game.Writer.BallWriter;
import Game.Writer.EndScreenWriter;
import Game.Writer.StartInfoWriter;

public class GameController {
	private EntityController entity_controller;
	private AnimationWriter animation_writer;
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
			}
		});
		aw.getPointText().addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent event){
				Rectangle r = aw.getPointText().getBounds();
				mouse_x = event.getX()+r.x;
				mouse_y = event.getY()+r.y;
			}
		});
		time_unit = 1;
		game_status = 0;
		rank = new Rank();
	}

	//랜덤한 위치(x축) 선택
	int _randomPickBallXPos(int r){
		return random.nextInt(1280 - r - r + 1) + r;
	}

	//랜덤한 위치(y축) 선택
	int _randomPickBallYPos(int r){
		return random.nextInt(720 - r - r + 1) + r;
	}

	//움직이는 공 생성
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

	//코인 생성
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

	//플레이어 생성
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

	//얻은 점수 및 랭킹 출력
	public void showEndScreen(){
		while(entity_controller.getLastEntityId() <= 0){
			entity_controller.removeEntity(0);
		}
		while(animation_writer.getLastAnimationId() <= 0){
			animation_writer.removeAnimation(0);
		}
		
		
		if(difficulty.difficulty == 0)
			rank.addEasyRank(name, entity_controller.getPlayer().getPoint());
		else
			rank.addHardRank(name, entity_controller.getPlayer().getPoint());
		rank.saveRankFile();

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

					//흐른 시간 추가
					runtime += painting_delay;
					//흐른 시간*100 (기본 값 기준 1초)으로 기본 공, 코인 생성
					if(runtime == painting_delay*100) {
						createMovingBall();
						createMovingBall();
						createMovingBall();
						createCoin();
					}

					//일정 시간마다 MovingBall 생성
					if(runtime - last_moving_ball_time > difficulty.spawn_moving_ball_cooltime && entity_controller.getMovingBallCount() <= difficulty.max_moving_ball){
						last_moving_ball_time = runtime;
						createMovingBall();
					}

					//일정 시간마다 Coin 생성
					if(runtime - last_coin_time > difficulty.spawn_coin_cooltime && entity_controller.getCoinCount() <= difficulty.max_coin){
						last_coin_time = runtime;
						createCoin();
					}

					//공 움직이기 + 플레이어 공 위치 옮기기
					entity_controller.run(time_unit);

					if(!(entity_controller.getPlayer() != null && entity_controller.runPlayerBall(mouse_x,mouse_y))){
							System.err.println("[ERROR] No player ball entity, skip");
							continue;
					}
					//플레이어랑 볼 충돌 테스트
					id = entity_controller.collideMovingBallTest();
					if(id != -1){
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
					}
					break;
				case 2:
					//사망 화면
					//업데이트 대상 없음
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