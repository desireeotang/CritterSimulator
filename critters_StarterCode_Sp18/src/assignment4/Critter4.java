package assignment4;

/* custom critter subclass by Desiree
 *  as denoted by the "D" symbol in the toString function. */

public class Critter4 extends Critter {
    @Override
    public void doTimeStep() {
        walk(6);
    }

    @Override
    public boolean fight(String opponent) {
        run(2);
        return false;
    }
    @Override
    public String toString() {
        return "D";
    }
}
