package assignment4;
/* CRITTERS Critter1.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Isabelle Villamiel
 * iv3235
 * Desiree Tang
 * dot227
 * Spring 2018
 */

/**
 * first custom critter subclass by Isabelle
 * as denoted by the "I" symbol in the toString function.
 *
 * This critter only fights D's and runs diagonally towards NE
 * */
public class Critter1 extends Critter{
    @Override
    public String toString() {
        return "1";
    }
    private static int RunDirection;
    public Critter1() {
        RunDirection = 1;
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("3")){
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
        run(RunDirection);

    }

    public static void runStats(java.util.List<Critter> Critters1) {
        System.out.print("" + Critters1.size() + " total Critters1");
    }
}
