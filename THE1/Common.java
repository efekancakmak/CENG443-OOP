import java.util.ArrayList;
import java.util.Random;

public class Common {
    private static final String title = "Arms Race";
    private static final int windowWidth = 1650;
    private static final int windowHeight = 1000;

    private static final int firstVerticalLineX = 500;
    private static final int secondVerticalLineX = 1250;
    private static final int horizontalLineY = 100;

    private static final Random randomGenerator = new Random(1234);

    private static final LivePrice foodPrice = new LivePrice(30, 65, "Food Products", 5, 1, 1, 10);
    private static final LivePrice electronicsPrice = new LivePrice(580, 65,"Consumer Electronics", 30, 2, 10, 50);
    private static final LivePrice goldPrice = new LivePrice(1300, 65, "Gold", 75, 3, 50, 100);


    // my area 
    private static ArrayList<Entity> entities;
    private static ArrayList<Country> countries;
    private static ArrayList<Corporation> corporations;
    private static ArrayList<Order> orders;
    public static ArrayList<Entity> getEntities() { return entities; };
    public static ArrayList<Country> getCountries() { return countries; };
    public static ArrayList<Corporation> getCorporations() { return corporations; };
    public static ArrayList<Order> getOrders() { return orders; };
    public static void pushOrder(Order o){
        entities.add(o);
        orders.add(o);
    };
    
    public static void removeOrder(Order o){
        entities.remove(o);
        orders.remove(o);
    };

    public static void generateOrder(Country c){
        Order niyuv;
        Random ran = new Random();
        double t = ran.nextDouble();
        if (c.getHappiness() < 50 && t<0.25){
            niyuv = new FoodOrder(c);
        }
        else if(c.getHappiness() < 50 && t<0.50){
            niyuv = new ElectronicsOrder(c);
        }
        else if(t<0.75){
            niyuv = new BuyGoldOrder(c);
        }
        else{
            niyuv = new SellGoldOrder(c);
        }
        entities.add(niyuv);
        orders.add(niyuv);
    }

    // getters
    public static String getTitle() { return title; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }

    public static int getFirstVerticalLineX() { return firstVerticalLineX; }
    public static int getSecondVerticalLineX() { return secondVerticalLineX; }
    public static int getHorizontalLineY() { return horizontalLineY; }

    public static Random getRandomGenerator() { return randomGenerator; }

    public static LivePrice getFoodPrice() { return foodPrice; }
    public static LivePrice getElectronicsPrice() { return electronicsPrice; }
    public static LivePrice getGoldPrice() { return goldPrice; }

    static  {
        
        countries = new ArrayList<Country>();
        corporations = new ArrayList<Corporation>();
        orders = new ArrayList<Order>();
        entities = new ArrayList<Entity>();

        Country mexico = new Country("Mexico","MX",0,600);
        Country chile= new Country("Chile","CL",300,600);
        Country poland= new Country("Poland","PL",600,600);
        Country nigeria= new Country("Nigeria","NG",900,600);
        Country malaysia= new Country("Malaysia","MY",1200,600);

        countries.add(mexico);
        countries.add(chile);
        countries.add(poland);
        countries.add(nigeria);
        countries.add(malaysia);

        entities.add(mexico);
        entities.add(chile);
        entities.add(poland);
        entities.add(nigeria);
        entities.add(malaysia);

        Corporation lockheed_martin = new Corporation("Lockheed_martin","LMT", 250, 250);
        Corporation raytheon = new Corporation("Raytheon","RTX", 500, 250);
        Corporation boeing = new Corporation("Boeing","BA", 750, 250);
        Corporation northrop_grumman = new Corporation("Northrop_grumman","NOC", 1000, 250);
        Corporation general_dynamics = new Corporation("General_dynamics","GD", 1250, 250);
        
        corporations.add(lockheed_martin);
        corporations.add(raytheon);
        corporations.add(boeing);
        corporations.add(northrop_grumman);
        corporations.add(general_dynamics);
        
        entities.add(lockheed_martin);
        entities.add(raytheon);
        entities.add(boeing);
        entities.add(northrop_grumman);
        entities.add(general_dynamics);

    }


    public static void stepAllEntities() {
        if (randomGenerator.nextInt(200) == 0) foodPrice.step();
        if (randomGenerator.nextInt(300) == 0) electronicsPrice.step();
        if (randomGenerator.nextInt(400) == 0) goldPrice.step();

           
        /*
        I put all sub-types of entity type 
        (namely, Orders, Countries, Corporations) in
        an entities array in order to call them from
        one center and use overriden Entity method, step.
        */
        for(int i=0; i<entities.size();i++)
            entities.get(i).step();
        
        
    }
}