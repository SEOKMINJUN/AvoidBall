package Game.Entity;

public class EntityController {
	private int MAX_ENTITY; // 최대 엔티티 갯수
	private Entity entity_list[]; // 엔티티 목록
	private int last_entity_id; // 엔티티 갯수 / 마지막 엔티티 번호
	private Box box; // 게임 영역
	private PlayerBall player_ball;	//플레이어 
	public EntityController() {
		MAX_ENTITY = 256;
		entity_list = new Entity[MAX_ENTITY];
		last_entity_id = 0;
		box = null;
	}

	// entity_list 리스트에 엔티티 추가
	public boolean register(Entity entity) {
		if(last_entity_id >= MAX_ENTITY) {
			System.out.println("[ERROR] Failed to register entity to EntityController, So many entity.");
			return false;
		}
		entity_list[last_entity_id++] = entity;
		return true;
	}

	//게임 영역 담당할 Box 설정
	public boolean setBox(Box box){
		this.box = box;
		return true;
	}

	//Box 반환
	public Box getBox(){
		return box;
	}

	//플레이어 Entity 설정
	public boolean setPlayer(PlayerBall player){
		player_ball = player;
		return true;
	}

	//플레이어 반환
	public PlayerBall getPlayer(){
		return player_ball;
	}

	//현재 entity_list의 엔티티들 출력
	public void printEntity(){
		for(int i=0;i<last_entity_id;i++){
			System.out.printf("%s \n",entity_list[i].getClass());
		}
	}

	//엔티티 중 MovingBall의 갯수 
	public int getMovingBallCount(){
		int count = 0;
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "MovingBall"){
				count += 1;
			}
		}
		return count;
	}

	//엔티티 중 Coin의 갯수 반환
	public int getCoinCount(){
		int count = 0;
		for(int i=0;i<last_entity_id;i++){
			if(entity_list[i].getType() == "Coin"){
				count += 1;
			}
		}
		return count;
	}
	
	//last_entity_id 값 반환
	public int getLastEntityId() {
		return last_entity_id;
	}

	//엔티티 목록에서 특정 엔티티 제거
	//뒤의 엔티티를 앞으로 덮어씌워 없애는 방식
	public boolean removeEntity(int id){
		if(last_entity_id <= id)
			return false;
		for(int i=id;i<last_entity_id;i++){
			entity_list[i] = entity_list[i+1];
		}
		last_entity_id -= 1;
		return true;
	}

	//두 원이 겹치는지 테스트
	public boolean _collideCircleTest(int x1,int y1,int r1,int x2,int y2,int r2){
		return Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < (r1 + r2) * (r1 + r2);
	}

	//플레이어와 MovingBall이 충돌했는지 테스트
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

	//플레이어와 Coin이 충돌했는지 테스트
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

	//엔티티 작동
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

	//PlayerBall을 entity_list 안에서 관리하지 않고 따로 변수로 관리하여 
	//추가적인 함수를 만들어 관리
	//entity_list에 등록해서 관리하기 위해서는 마우스의 x,y값을 내부에서 구할 수 있게 변경해야함.
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
