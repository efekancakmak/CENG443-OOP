import java.util.Random;

public class Shake extends State {
    /*
    In shake state, corporation does moves randomly to
    its close areas. Our path variable(Position class) must
    randomly change. To do that, in overridden stateMove function
    we update the path variable (Position class) with random 
    numbers between -2 and 2, so that the corporation can move 
    shake every direction.
    Also, I implemented overridden method toString to print state name.
    */

    public void stateMove(Corporation corp){
        Random ran = new Random();
        double x = ran.nextDouble()*4 - 2;
        double y = ran.nextDouble()*4 - 2;
        path.setX(x);
        path.setY(y);
        corp.setState(this);
    }


    @Override
    public String toString() {
        return "Shake";    
    }
}