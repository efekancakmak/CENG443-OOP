import java.awt.*;

import javax.swing.plaf.ColorUIResource;

public class ElectronicsOrder extends Order {
    // TODO
    // RGB --> (0, 182, 204)

    ElectronicsOrder(Country w){
        super(w, w.getPosition().getX(), w.getPosition().getY());
    }

    @Override
    public void draw(Graphics2D g2d) {
        Color c = new ColorUIResource(0, 182, 204);
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
            whose.updateCash(-amount*Common.getElectronicsPrice().getCurrentPrice());
            whose.updateHappiness(amount*0.4);
            Common.removeOrder(this);
        }
        // here need to add corporation cases
    }
}