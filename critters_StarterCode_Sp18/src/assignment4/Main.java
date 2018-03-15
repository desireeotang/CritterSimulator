package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Isabelle Villamiel
 * iv3235
 * Desiree Tang
 * dot227
 *
 *
 * Spring 2018
 */


import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        try {
            // FOR LOOPS FOR STAGES 1 AND 2 ONLY
            // STAGE 3: WORLD STARTS EMPTY
            for(int i =0; i < 25; i++) {
                Critter.makeCritter("assignment4.Craig");
            }
            for(int j =0; j <100; j++){
                Critter.makeCritter("assignment4.Algae");
            }
        } catch (InvalidCritterException e) {
            e.printStackTrace();
        }

        String input;

        do{
            // prompts next command
            System.out.print("critters> ");
            input = kb.nextLine();
            String [] parts = input.split(" ");
            String command = parts[0];

            if(command.equals("show")){
                Critter.displayWorld();
            }
            else if(command.equals("seed")){
                if(parts[1] != null){
                    int seedNum = Integer.parseInt(parts[1]);
                    Critter.setSeed(seedNum);
                }
                else{
                    System.out.println("No seed number entered");
                }

            }
            else if(command.equals("make")){
                // NOT DONE: TO DO FOR STAGE 3
                if(parts[1] != null){
                    String newCritter = "assignment4." + parts[1];
                    if(parts[2] != null){
                        int makeNum = Integer.parseInt(parts[2]);

                        for(int i =0; i < makeNum; i++){
                            //Critter.makeCritter(newCritter);
                        }
                    }
                    else{
                       // Critter.makeCritter(newCritter);
                    }
                }
                else{
                    System.out.println("Name of subclass of Critter not specified for make");
                }
            }
            else if(command.equals("stats")){
                // NOT DONE: TO DO FOR STAGE 3
                if(parts[1] != null){

                }
                else{
                    System.out.println("Name of subclass of Critter not specified for stats");
                }

               // java.util.List<Critter> ListOfCritters = Critter.getInstances("Craig");
            }
            else if (command.equals("step")){

               if(parts[1] != null){
                   // count specified
                   int stepNum = Integer.parseInt(parts[1]);

                   for(int i = 0; i < stepNum; i++){
                       Critter.worldTimeStep();
                   }
                }
                else{
                   // do time step once if count number is not provided
                   Critter.worldTimeStep();
                }

            }
            else{
                System.out.println("invalid command: " + input);
            }

        } while(!input.equals("quit"));


        System.out.flush();

    }
}
