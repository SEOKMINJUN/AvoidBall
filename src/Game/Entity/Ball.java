package Game.Entity;

import java.awt.*;

public abstract class Ball extends Entity{
    private int radius; // 반지름
    private Color ball_color; // 색깔
    //생성자
    public Ball(String type, int x, int y, int radius, Color color){
        super(type, x,y);
        this.radius = radius;
        ball_color = color;
    }

    public int getRadius(){return radius;}  //반지름 반환
    public Color getColor(){return ball_color;} //색깔 반환
}
