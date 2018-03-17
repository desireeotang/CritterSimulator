package assignment4;

/*  custom critter subclass by Desiree
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
    @Override
    public void doTimeStep() {
        int walkNum = Critter.getRandomInt(7);
        walk(walkNum);
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
    @Override
    public String toString() {
        return "d";
    }

    public static void runStats(java.util.List<Critter> critters3) {
        System.out.print("" + critters3.size() + " total Critters3");
    }

}
