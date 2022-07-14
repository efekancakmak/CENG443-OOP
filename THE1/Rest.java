public class Rest extends State {
    /*
    Rest is the simpliest state.
    since corporation does not move in this state,
    our path variable(Position class) must be equal
    to Position(x:0, y:0). Overridden function, stateMove,
    does that, then writes resulted path to corresponding corporation
    Also, I implemented overridden method toString to print state name.
    */
    
    public void stateMove(Corporation corp){
        path.setX(0.0);
        path.setY(0.0);
        corp.setState(this);
    }

    @Override
    public String toString() {
        return "Rest";    
    }
}