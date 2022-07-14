import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CakeStand{
    // instance variables
    Integer cake_amount;
    Integer id;
    Semaphore semForAstand; // Prevention to multiple monsters on the same stand.
    // instance methods
    CakeStand(Integer inp){
        id = inp;
        cake_amount = 0;
        semForAstand = new Semaphore(1);
    }

    public void addCake(int amount){
        cake_amount += amount;
    }
    
    // class variables
    static List<CakeStand> stands; // list to keep Stand instances
    static List<Boolean> board;   // board to show availibility of stands
    public static int stand_amount;

    static Semaphore semForStands; // A semaphore to control accesses on Stand instances.
    static Boolean standAvailable; // A monitor(or CV) to synchronize threads that need to sleep or wake up others.
    static Boolean newCakesHere; // A monitor(or CV) to sleep & wake up monsters.

    static {
        // init static variables
        stands = new ArrayList<CakeStand>();
        board = new ArrayList<Boolean>();
        stand_amount = 0;
        semForStands = new Semaphore(1);
        standAvailable = false;
        newCakesHere = false;
    }

    public static void supplyStand(){
        String name = Thread.currentThread().getName();
        System.out.println(name + " brought a new stand.");
        try {
            /*
            Only one thread should modify
            or access the stands list.
            So, I keep it with a semaphore.
            */
            semForStands.acquire();
            stands.add(new CakeStand(stand_amount));
            board.add(true);
            System.out.println(name + " added Stand#" + (stand_amount+1) + ".");
            stand_amount++;
            semForStands.release();
            /*
            Some CakeMonsters or CakeBakers
            can come to ground before stand
            suppliers. If so, they must be
            waiting there.. I think we should 
            wake up them with standAvailable signal.
            */
            synchronized (standAvailable){
                standAvailable.notifyAll();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void putCake(int slices){
        String name = Thread.currentThread().getName();
        System.out.println(name + " baked a cake with " + slices + " slices.");
        while(true){
            try {
                /*
                Only one thread should modify
                or access the stands&board list.
                So, I keep it with a semaphore.
                */
                semForStands.acquire();
                for(int i=0; i<stand_amount; i++){
                    if(board.get(i).equals(true)){
                        board.set(i,false);
                        stands.get(i).semForAstand.acquire();
                        stands.get(i).addCake(slices);
                        stands.get(i).semForAstand.release();
                        /*
                        Since we added cake, we need to
                        notify monsters that waits for a cake.
                        */
                        synchronized(newCakesHere){
                            newCakesHere.notifyAll();
                        }
                        System.out.println(name + " put the cake with " + slices + " slices on Stand#" + (i+1) +".");
                        semForStands.release();
                        return;
                    }
                }  
                semForStands.release();
                /*
                if the function did not return,
                means that the baker could not
                find an available stand.
                Therefore, the baker should 
                wait until a new stand is added or
                an existing stand becomes empty.
                */             
                synchronized (standAvailable){
                    standAvailable.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static CakeStand randomStand(){
        int randNum = 0;
        try{
            /*
            Only one thread should modify
            or access the stand_amount variable.
            So, I keep it with a semaphore.
            */
            semForStands.acquire();
            if (stand_amount!=0){
                randNum = ThreadLocalRandom.current().nextInt(0, stand_amount);
                semForStands.release();
                return stands.get(randNum);
            }
            semForStands.release();
            /*
            When the function reaches here,
            it means that there is no stand
            in the ground.
            Thus, we need to wait for new stands            
            */
            synchronized(standAvailable){
                standAvailable.wait();
            }
            /*
            After waking up, we need to try again
            */
            return randomStand();
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }
        return stands.get(randNum);
    }
    
    public void getSlice(){
        String name = Thread.currentThread().getName();
        System.out.println(name + " came to Stand#" + (id+1) + " for a slice.");
        while(true) {
            try {
                /*
                Only one monster, at the same time,
                should get a slice of cake from the same stand.
                So, I keep a cake_amount of instance with a semaphore.
                */
                semForAstand.acquire();
                if(cake_amount > 0){
                    cake_amount--;
                    if(cake_amount.equals(0)){
                        board.set(id,true);
                        /*
                        Since there is no cake in the
                        ground anymore. We call bakers :)
                        */
                        synchronized (standAvailable){
                            standAvailable.notifyAll();
                        }
                    }
                    System.out.println(name + " got a slice from Stand#" + (id+1) + ", so " + cake_amount + " slices left.");
                    semForAstand.release();
                    return;
                }
                semForAstand.release();
                /*
                If function reaches here,
                there is nothing to eat on the stand.
                So, cute monsters shall wait bakers
                putting cake slices.                
                */
                synchronized (newCakesHere){
                    newCakesHere.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


}