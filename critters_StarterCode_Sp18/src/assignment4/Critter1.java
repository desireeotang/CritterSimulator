package assignment4;
/*
* */
/**
 * first custom critter subclass by Isabelle
 * as denoted by the "I" symbol in the toString function.
 *
 * This critter only fights D's and runs diagonally towards NE
 * */
public class Critter1 extends Critter{
    @Override
    public String toString() {
        return "I";
    }
    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("D")){
            return true;
        }
        else{
            int randomNum = getRandomInt(7);
            walk(randomNum);
            return false;
        }
    }

    @Override
    public void doTimeStep(){
        // always runs diagonally
        run(1);

    }

    public static void runStats(java.util.List<Critter> Critters1) {
        System.out.print("" + Critters1.size() + " total Critters1");
    }
}
