import java.awt.*;
import java.util.ArrayList;
import javax.swing.plaf.ColorUIResource;

public class BuyGoldOrder extends GoldOrder {
    // RGB --> (0, 200, 0)
    BuyGoldOrder(Country w){
        super(w, w.getPosition().getX(), w.getPosition().getY());
    }

    @Override
    public void draw(Graphics2D g2d) {
        Color c = new ColorUIResource(0, 200, 0);
        g2d.setColor(c);
        g2d.fillOval((int)(position.getX()), (int)position.getY()-30, 24, 24);
        g2d.drawString(String.format(whose.getAbbrevation(), amount),(int)(position.getX()), (int)position.getY()-30);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("%d", amount),(int)(position.getX())+5, (int)position.getY()-10);
    }

    @Override
    public void step() {
        super.step();
        if (position.getY() < 120) {
            whose.updateGold(amount);
            whose.updateCash(-amount*Common.getGoldPrice().getCurrentPrice());
            Common.removeOrder(this);
        }
        else{
            ArrayList<Corporation> cps = Common.getCorporations();
            for(Corporation c : cps){
                double x = position.getX();
                double y = position.getY();
                double xx = c.getPosition().getX();
                double yy = c.getPosition().getY();
                if (x > xx && y > yy && x < xx + 100 && y < yy + 100){
                    whose.updateCash(-amount*Common.getGoldPrice().getCurrentPrice());
                    whose.updateHappiness(-amount*0.1);
                    c.updateCash((int) (amount*Common.getGoldPrice().getCurrentPrice()));
                    Common.removeOrder(this);
                }
            }
        }

        
    }
}