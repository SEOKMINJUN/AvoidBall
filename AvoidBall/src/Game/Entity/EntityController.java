package Game.Entity;

import Game.Rank;

public class EntityController {
	private int MAX_ENTITY = 256;
	private Entity entity_list[];
	private int last_entity_id;
	private Box box;
	private PlayerBall player_ball;
	public EntityController() {
		MAX_ENTITY = 256;
		entity_list = new Entity[MAX_ENTITY];
		last_entity_id = 0;
		box = null;
	}
	
	public boolean register(Entity entity) {
		if(last_entity_id >= MAX_ENTITY) {
			System.out.println("[ERROR] Failed to register entity to EntityController, So many entity.");
			return false;
		}
		entity_list[last_entity_id++] = entity;
		return true;
	}

	public boolean setBox(Box box){
		this.box = box;
		return true;
	}

	public Box getBox(){
		return box;
	}

	public boolean setPlayer(PlayerBall player){
		player_ball = player;
		return true;
	}

	public PlayerBall getPlayer(){
		return player_ball;
	}

	public void printEntity(){
		for(int i=0;i<last_entity_id;i++){
			System.out.printf("%s \n",entity_list[i].getClass());
		}
	}

	public int getMovingBallCount(){
		int count = 0;
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "MovingBall"){
				count += 1;
			}
		}
		return count;
	}

	public int getCoinCount(){
		int count = 0;
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "Coin"){
				count += 1;
			}
		}
		return count;
	}

	public boolean removeEntity(int id){
		if(last_entity_id <= id)
			return false;
		System.out.printf("[INFO] Remove entity type:%s id:%d\n",entity_list[id].getType(),id);
		for(int i=id;i<last_entity_id;i++){
			entity_list[i] = entity_list[i+1];
			last_entity_id -= 1;
		}
		return true;
	}

	public boolean _collideLineTest(int p1_left, int p1_right,int p2_left, int p2_right){
		if(p1_left < p2_left && p2_left < p1_right)
			return true;
		if(p2_left < p1_left && p1_left < p2_right)
			return true;
		return false;
	}

	public boolean _collideCircleTest(int x1,int y1,int r1,int x2,int y2,int r2){
		if(_collideLineTest(x1-r1,x1+r1,x2-r1,x2+r1) && _collideLineTest(y1-r1,y1+r1,y2-r1,y2+r1))
			return true;
		return false;
	}


	public int collideMovingBallTest(){
		int player_x = player_ball.getXPos();
		int player_y = player_ball.getYPos();
		int player_r = player_ball.getRadius();
		
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "MovingBall"){
				int entity_x = ((Ball)entity_list[i]).getXPos(); 
				int entity_y = ((Ball)entity_list[i]).getYPos(); 
				int entity_r = ((Ball)entity_list[i]).getRadius(); 
				if(_collideCircleTest(player_x,player_y,player_r,entity_x,entity_y,entity_r)){
					return i;
				}
			}
		}
		return -1;
	}

	public int collideCoinTest(){
		int player_x = player_ball.getXPos();
		int player_y = player_ball.getYPos();
		int player_r = player_ball.getRadius();
		
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "Coin"){
				int entity_x = ((Ball)entity_list[i]).getXPos(); 
				int entity_y = ((Ball)entity_list[i]).getYPos(); 
				int entity_r = ((Ball)entity_list[i]).getRadius(); 
				if(_collideCircleTest(player_x,player_y,player_r,entity_x,entity_y,entity_r)){
					return i;
				}
			}
		}
		return -1;
	}

	public boolean run(int time_units){
		if(box == null){
			System.out.println("[ERROR] Box not setted");
			return false;
		}
		for(int i=0;i<last_entity_id;i++){

			try{
				entity_list[i].run(box, time_units);
			}
			catch(Exception e){
				System.out.println("[ERROR] Failed entity run");
			}
		}
		return true;
	}

	public boolean runPlayerBall(int x, int y){
		if(player_ball == null){
			System.out.println("[ERROR] player ball not setted.");
			return false;
		}
		player_ball.setXPos(x);
		player_ball.setYPos(y);
		return true;
	}
}
