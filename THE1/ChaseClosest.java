import java.util.ArrayList;
import java.util.Random;

public class ChaseClosest extends State {
    public void stateMove(Corporation corp){
        /*
        In this state, 
        simply we need to traverse all gold orders, then we
        shall choose the closest one to corresponding corporations.
        To do that, I get referance of orders array from Common class.
        Then, traversed all orders and checked whether it is a GoldOrder.
        Then, find closest one.
        Lastly, we need to set State super class's path variable directing
        to resulting (closest) GoldOrder.
        since path's speed can be larger or too smaller. I normalize and multiply
        a random variable between 1 & 3
        Also, I implemented overridden method toString to print state name.
        */

        ArrayList<Order> ords = Common.getOrders();
        Order closest = null;
        double distance = 9999999.9;
        for (Order o : ords) {
            if ( o instanceof GoldOrder){
                double distx = corp.getPosition().getX() - o.getPosition().getX();
                double disty = corp.getPosition().getY() - o.getPosition().getY();
                double d = Math.sqrt(distx*distx + disty*disty);
                if ( d < distance ){
                    closest = o;
                    distance = d;
                }
            }
        }
        if (closest==null)
            return;
        Random ran = new Random();
        Position crp = corp.getPosition();
        double xx = closest.getPosition().getX()-crp.getX()-50;
        double yy = closest.getPosition().getY()-crp.getY()-50;
        double len = Math.sqrt(xx*xx + yy*yy);
        double constant = ran.nextInt(3) + 1;
        path.setX(constant*xx/len);
        path.setY(constant*yy/len);
        corp.setState(this);       
    }


    @Override
    public String toString() {
        return "Chase";    
    }
}