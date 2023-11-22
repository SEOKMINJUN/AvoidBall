package Game;

public class Difficulty {
    int difficulty = 0;
    public int spawn_coin_cooltime = 8*1000;
    public int spawn_moving_ball_cooltime = 16*1000;
	public int max_moving_ball = 6;
	public int max_coin = 12;

    public Difficulty(int difficulty){
        this.difficulty = difficulty;
        if(difficulty == 1){
            spawn_coin_cooltime = 12*1000;
            spawn_moving_ball_cooltime = 12*1000;
            max_moving_ball = 12;
            max_coin = 8;
        }
    }
}
