package Game;

public class Difficulty {
    int difficulty = 0;
    public int spawn_coin_cooltime = 0;
    public int spawn_moving_ball_cooltime = 0;
	public int max_moving_ball = 0;
	public int max_coin = 0;

    public Difficulty(int difficulty){
        this.difficulty = difficulty;
        if(difficulty == 0){
            spawn_coin_cooltime = 5*1000;
            spawn_moving_ball_cooltime = 3*1000;
            max_moving_ball = 20;
            max_coin = 13;
        }
        else{
            spawn_coin_cooltime = 7*1000;
            spawn_moving_ball_cooltime = 5*1000;
            max_moving_ball = 12;
            max_coin = 8;
        }
    }
}
