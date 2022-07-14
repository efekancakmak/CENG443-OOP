import java.awt.*;

import java.util.Random;
import javax.swing.ImageIcon;

public class Country extends Entity {
    // TODO
    // Country image is 150 x 150
    // Name RGB --> Black
    // Worth RGB --> Blue
    // Cash RGB --> (0, 100, 0)
    // Gold RGB --> Yellow
    // Happiness RGB --> (180, 0, 0)

    private String name;
    private String abbrevation;
    private double gold_amount;
    private double cash_amount;
    private double dynamic_worth;
    private double happiness;
    
    public String getName(){ return name; }
    
    Country(String n, String a, double x, double y){
        super(x,y);
        name = n;
        abbrevation = a;
        gold_amount = 50.0;
        cash_amount = 5000.0;
        happiness = 50.0;
        dynamic_worth = cash_amount + gold_amount * Common.getGoldPrice().getCurrentPrice();
    }

    public String getAbbrevation(){ return abbrevation;}

    public void updateCash(double amount){
        cash_amount += amount;
    }


    public void updateGold(int amount){
        gold_amount += amount;
    }

    public void updateHappiness(double amount){
        happiness += amount;
    }
    
    public double getHappiness(){ return happiness;}
    

    @Override
    public void step() {
        /*
        I assumed step is a behavior in each timestamp.
        In each timestamp, countries may or not generate orders.
        To deal with it, I take a random number between 0 & 1,
        and if it is smaller than 0.005, we call generateOrder
        function implemented in Common class.
        */
        Random ran = new Random();
        double t = ran.nextDouble();
            
        if(t < 0.005){
            Common.generateOrder(this);
        }
        /*
        And also they shall update their worths dynamically in each step.
        other variables cash&gold amounts are updated by Order's step.
        We give Country as a referance to Orders so that they can update the generator
        country easily.
        */
        dynamic_worth =  cash_amount + gold_amount * Common.getGoldPrice().getCurrentPrice();
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 20);
        String tmp = name;
        ImageIcon icon = new ImageIcon("../images/" + tmp.toLowerCase() + ".png");
        Image image = icon.getImage();
        g2d.drawImage(image, position.getIntX()+150, position.getIntY(), 150, 150, null);

        // now draw its feautures
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(name, position.getIntX()+150, position.getIntY() + 200);

        g2d.setColor(Color.BLUE);
        g2d.drawString(String.format("%s: %.2f$", "Worth: ", dynamic_worth), position.getIntX()+150, position.getIntY() + 225);
   
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("%s: %.2f$", "Cash: ", cash_amount), position.getIntX()+150, position.getIntY() + 250);
   
        g2d.setColor(Color.YELLOW);
        g2d.drawString(String.format("%s: %.2f", "Gold: ", gold_amount), position.getIntX()+150, position.getIntY() + 276);
   
        g2d.setColor(Color.RED);
        g2d.drawString(String.format("%s: %.2f%%", "Happiness: ", happiness), position.getIntX()+150, position.getIntY() + 300);
   
    }


}