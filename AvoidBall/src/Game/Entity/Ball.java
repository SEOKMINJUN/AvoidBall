package Game.Entity;

import java.awt.*;

public abstract class Ball extends Entity{
    private int radius;
    private Color ball_color;
    public Ball(String type, int x, int y, int radius, Color color){
        super(type, x,y);
        this.radius = radius;
        ball_color = color;
    }

    public int getRadius(){return radius;}
    public Color getColor(){return ball_color;}
}
