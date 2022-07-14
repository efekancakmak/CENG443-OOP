import java.awt.Graphics2D;

public abstract class GoldOrder extends Order {
    // TODO
    
    GoldOrder(Country w, double x, double y){
        super(w, x, y);
    }


    @Override
    public void draw(Graphics2D g2d) {
    }

    @Override
    public void step() {
        super.step();
    }
}