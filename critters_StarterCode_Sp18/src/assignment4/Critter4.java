package assignment4;

/* custom critter subclass by Desiree
 * as denoted by the "D" symbol in the toString function.
 * This Critter is a kind critter to critters from
 * its own Creator (Desiree) and will walk away from a fight.
 * However, this Critter is not nice to other
 * Critters made by others i.e. Craig. and will fight them.
 *
 */

public class Critter4 extends Critter {

    private static int numTimesRan;

    @Override
    public void doTimeStep() {
        int runNum = Critter.getRandomInt(7);
        run(runNum);
        numTimesRan++;
    }

    public Critter4(){
        numTimesRan = 0;
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.toString().equals("4")){
            walk(2);
            return false;
        }
        else{
            return true;
        }
    }
    @Override
    public String toString() {
        return "4";
    }

    public static void runStats(java.util.List<Critter> Critters4) {
        System.out.print("" + Critters4.size() + " total Critters4");
        System.out.println("");
        System.out.println("Number of times Ran: " + numTimesRan);
    }
}
