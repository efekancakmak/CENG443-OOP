
import java.util.Random;
public abstract class State {

    public static Position path;
    public static int speed;

    State(){
        path = new Position(0, 0);
        speed = 0;
    }

    public Position getPath(){
        return path;
    }

    public static State getInitialState(){
        return new Rest();
    }

    public void changeState(Corporation corp){
        Random ran = new Random();
        double t = ran.nextDouble();
        if ( t < 0.25) {
            corp.setState(new Rest());
        }
        else if ( t < 0.5) {
            corp.setState(new Shake());

        }
        else if ( t < 0.75) {
            corp.setState(new GotoXY());            
        }
        else{
            corp.setState(new ChaseClosest());            
        }
    }


    public void stateMove(Corporation corp){
        
    }

    @Override
    public String toString() {
        return "";
    }
}