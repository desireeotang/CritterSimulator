package assignment4;
/* CRITTERS Critter3.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Isabelle Villamiel
 * iv3235
 * Desiree Tang
 * dot227
 * Spring 2018
 */

/**  custom critter subclass by Desiree
 *  as denoted by the "d" symbol in the toString function.
 *  This Critter is a strong independent female that
 *  walks during every time step and will walk in any direction
 *  because she's not afraid of anything.
 *  She will also never back down from a fight
 *  and will always fight for her territory.
 *  This Critter also REFUSES to reproduce although
 *  she has the capability to #SorryNotSorry
 *
 */

public class Critter3 extends Critter{

    private static int numFightsAttempted;

    @Override
    public void doTimeStep() {
        int walkNum = Critter.getRandomInt(7);
        walk(walkNum);
    }

    public Critter3(){
        numFightsAttempted = 0;
    }
    @Override
    public boolean fight(String opponent) {
        numFightsAttempted++;
        return true;
    }
    @Override
    public String toString() {
        return "3";
    }

    public static void runStats(java.util.List<Critter> Critters3) {
        System.out.print("" + Critters3.size() + " total Critters3");
        System.out.println("");
        System.out.println("Number of Fights Attempted: " + numFightsAttempted );
    }

}
