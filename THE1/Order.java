import java.awt.*;
import java.util.Random;

public abstract class Order extends Entity {
    // Order is 24 x 24
    protected int amount;
    protected int speed;
    protected Position path;
    protected Country whose;

    Order(Country w, double x, double y){
        super(x+200, y+10);
        whose = w;
        Random ran = new Random();
        amount = ran.nextInt(5) + 1;
        speed = 1;//ran.nextInt(3)+1;
        path = new Position(ran.nextInt(11)-5,-ran.nextInt(2)-1);
    }

    @Override
    public void step() {
        double tx = position.getX();
        double ty = position.getY();
        position.setX(tx + path.getX() * speed);
        position.setY(ty + path.getY() * speed);
        // may be order fillation will come here..
    }

    @Override
    public void draw(Graphics2D g2d) {

    }
}