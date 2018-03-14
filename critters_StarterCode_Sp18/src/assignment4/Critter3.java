package assignment4;

/* custom critter subclass by Desiree
 *  as denoted by the "D" symbol in the toString function. */

public class Critter3 extends Critter{
    @Override
    public void doTimeStep() {
        walk(5);
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
    @Override
    public String toString() {
        return "D";
    }
}
