import java.awt.*;
import javax.swing.ImageIcon;
import java.util.Random;

public class Corporation extends Entity {
    // Corporation image is 100 x 100
    // Cash RGB --> (180, 0, 0)
    // Badge is 20 x 20
    private String name;
    private String abbreviation;
    private State state;
    private int cash_amount;
    private boolean badge1;
    private boolean badge2;
    private boolean badge3;

    Corporation(String n, String a, double x, double y){
        super(x,y);
        name = n;
        abbreviation = a;
        cash_amount = 0;
        badge1 = false;
        badge2 = false;
        badge3 = false;
        state = State.getInitialState();
    }


    public void updateCash(int amount){
        cash_amount += amount;
    }

    public void setState(State s){
        state = s;
    }
    
    @Override
    public Position getPosition() {
        return super.getPosition();
    }


    @Override
    public void step() {
        /*
        HW1.pdf stated that a corporation should
        change state randomly.
        To do that, here I obtain a random number 
        between 0 & 1. Then if this number is below 0.005,
        we call changeState function that implemented in State class.
        */
        Random ran = new Random();
        double t = ran.nextDouble();
        if ( t < 0.005 ){
            state.changeState(this);
        }
        /*
        After changed or not changed the state
        we should make Corporation behave in this state.
        To do that, we call stateMove method implemented in State class.
        stateMove simply updates the path in super class State.
        */
        state.stateMove(this);
        /*
        After that, we simply get the path decided by
        current state. Then, following this path.
        Also we need to limit X and Y variables
        to keep corporations in GUI
        */
        Position tmp = state.getPath();
        position.setX(Math.min(Math.max(0.0,position.getX() + tmp.getX()), 1500 ));
        position.setY(Math.min(Math.max(0.0,position.getY() + tmp.getY()), 750));

        /*
        Checking cash amount to obtain badges.
        */
        if (!badge1 && cash_amount > 2000) {
            badge1 = true;
        }
        else if (!badge2 && cash_amount > 4000) {
            badge1 = true;
            badge2 = true;
            
        }
        else if (!badge3 && cash_amount > 6000) {
            badge1 = true;
            badge2 = true;
            badge3 = true;
        }
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        
        Font font = new Font("Verdana", Font.BOLD, 20);
        String tmp = name;
        ImageIcon icon = new ImageIcon("../images/" + tmp.toLowerCase() + ".png");
        Image image = icon.getImage();
        g2d.drawImage(image, position.getIntX(), position.getIntY(), 100, 100, null);
        // now draw its feautures
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(abbreviation, position.getIntX()+30, position.getIntY()-5);
        // here additional strings will come

        g2d.setColor(Color.BLUE);
        g2d.drawString(state.toString(),(int)(position.getX())+20, (int)position.getY()+120);

        g2d.setColor(Color.RED);
        String tmpstr = String.format("%d", cash_amount);
        g2d.drawString(tmpstr,(int)(position.getX())+30, (int)position.getY()+140);

        if(badge1 && badge2 && badge3){
            g2d.setColor(Color.WHITE);
            g2d.fillRect(position.getIntX()+5, position.getIntY()-50, 20, 20);
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(position.getIntX()+40, position.getIntY()-50, 20, 20);
            g2d.setColor(Color.RED);
            g2d.fillRect(position.getIntX()+75, position.getIntY()-50, 20, 20);

        }
        else if(badge1 && badge2){
            g2d.setColor(Color.WHITE);
            g2d.fillRect(position.getIntX()+5, position.getIntY()-50, 20, 20);
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(position.getIntX()+45, position.getIntY()-50, 20, 20);
            
        }
        else if(badge1){
            g2d.setColor(Color.WHITE);
            g2d.fillRect(position.getIntX()+5, position.getIntY()-50, 20, 20);
        }
    }
}