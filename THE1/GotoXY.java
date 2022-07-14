import java.util.Random;
import java.lang.Math;
public class GotoXY extends State {
    /*
    In GotoXY state, my implementation is differs.
    Once we get into GotoXY, actually we generate GotoXY
    object. When generating, we select random integers that
    are in the window domain. Then edited path variable
    as path = RANDOMXY - CURRENT_CORP_POS.
    After that, we should normalize the path since its speed
    can be huge. After normalizing, we can enforce it by multiplying
    random integer between 1 & 3 for seeing different speeds.
    Lastly, we set path variable in the superclass so that a
    corporation can read it.
    Also, I implemented overridden method toString to print state name.
    */
        
    private double x;
    private double y;
    private boolean first;
    
    GotoXY(){
        super();
        Random ran = new Random();
        x = ran.nextInt(1600)+20;
        y = ran.nextInt(450)+130;
        first = false;
    }

    public void stateMove(Corporation corp){
        if(!first){
            Random ran = new Random();
            Position crp = corp.getPosition();
            double xx = x-crp.getX();
            double yy = y-crp.getY();
            double len = Math.sqrt(xx*xx + yy*yy);
            double constant = ran.nextInt(3) + 1;
            path.setX(constant*xx/len);
            path.setY(constant*yy/len);
            corp.setState(this);
        }
        else{

        }
    }

    @Override
    public String toString() {
        return "GotoXY";    
    }
}