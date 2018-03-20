package assignment4;
/* CRITTERS Critter2.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Isabelle Villamiel
 * iv3235
 * Desiree Tang
 * dot227
 * Spring 2018
 */

/**
 * Second Critter implemented by Isabelle.
 * This critter is non-confrontational and just wants to
 * smell the algae.
 * Does not move if energy is below 50.
 * Walks aimlessly.
 * */
public class Critter2 extends Critter{
    @Override
    public String toString(){ return "2";  }

    private static int walkDirection;
    public Critter2(){
        int rand = getRandomInt(7);
        walkDirection = rand;
    }
    @Override
    public boolean fight(String opponent) {
        int rand = getRandomInt(7);
        walk(rand);
        return false;
    }

    @Override
    public void doTimeStep() {
        if(getEnergy() < 50){
            return;
        }
        // want to walk towards algae but idk how lol
        walk(walkDirection);
    }

    public static void runStats(java.util.List<Critter> Critters2) {
        System.out.print("" + Critters2.size() + " total Critters2");
    }
}
