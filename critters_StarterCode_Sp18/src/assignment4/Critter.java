package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Isabelle Villamiel
 * iv3235
 * Desiree Tang
 * dot227
 * Spring 2018
 */


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	// this is the
	private static List<String> worldSet = new java.util.ArrayList<>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	/**this flag is to indicate whether or not the critter is allowed to move
	* during the call to walk. ie. if critter has already moved, it can't move again
	* and will be penalized by using energy even if it didn't change locations
	* NEED TO RESTART EVERY WORLD STEP
	 *
	 * 0 - can move
	 * 1 - coming from fight
	 * 2 - already used movement and coming from fight
	 * */
	private int movementFlag = 0;
	/** Function allows critter to move in one direction,
	 *  need to account for wrapping around the world
	 *
	 *  @param direction the basics:
	 *                      0- right, 4- left, 2- north, 6- south
	 * */
	protected final void walk(int direction) {
		if(movementFlag > 1){
			this.energy -= Params.walk_energy_cost;
			return;
		}
		switch (direction){
			case 0:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord+1,this.y_coord)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(0);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 1:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord+1,this.y_coord-1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(2);
				move(0);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 2:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord,this.y_coord-1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(2);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 3:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord-1,this.y_coord-1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(4);
				move(2);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 4:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord-1,this.y_coord)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(4);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 5:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord-1,this.y_coord+1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(4);
				move(6);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 6:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord,this.y_coord+1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(6);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
			case 7:
				if(this.movementFlag == 1){
					if (isOccupied(this.x_coord+1,this.y_coord+1)){
						this.energy -= Params.walk_energy_cost;
						movementFlag+=1;
						return;
					}
				}
				move(0);
				move(6);
				energy -= Params.walk_energy_cost;
				movementFlag += 2;
				return;
		}
	}
	private final boolean isOccupied(int x, int y){
		for(Critter c: population){
			if(x == c.x_coord && y == c.y_coord){
				return true;
			}
		}
		return false;
	}

	//0- right, 4- left, 2- north, 6- south
	private final void move(int direction){
		switch (direction){
			case 0:
				if(x_coord == Params.world_width-1){
					x_coord = 0;
				}
				else{
					x_coord++;
				}
				return;
			case 2:
				if(y_coord == 0){
					y_coord = Params.world_height-1;
				}
				else{
					y_coord--;
				}
				return;
			case 4:
				if(x_coord == 0){
					x_coord = Params.world_width-1;
				}
				else{
					x_coord--;
				}
				return;
			case 6:
				if(y_coord == Params.world_height-1){
					y_coord = 0;
				}
				else{
					y_coord++;
				}
				return;
		}
	}
	protected final void run(int direction) {
		if(movementFlag > 1){
			energy -= Params.run_energy_cost;
			return;
		}
		walk(direction);
		energy += Params.walk_energy_cost;
		// 2 so critter doesn't get penalized for wanting to run
		// in the call to walk
		movementFlag -= 2;
		walk(direction);
		energy += Params.walk_energy_cost;
		energy -= Params.run_energy_cost;
		
	}

    /**
     * Implements the details of the baby critter
     * @param offspring the baby critter
     * @param direction where the baby critter is placed in respect to the parent critter
     */
	protected final void reproduce(Critter offspring, int direction) {
	    // offspring is the critter BABY not the parent because precondition
        // is a new critter is made before calling reproduce

		if(this.energy < Params.min_reproduce_energy){
			return;
		}

		offspring.energy = (int) Math.floor(0.5 * this.energy);
		this.energy = (int) Math.ceil(0.5 * this.energy);

		switch(direction){
			case 0: // right
				if(this.x_coord == Params.world_width-1){
					offspring.x_coord = 0;
				}
				else{
					offspring.x_coord = this.x_coord + 1;
				}
				break;
			case 1: // up and right
				if(this.x_coord == Params.world_width-1){
					offspring.x_coord = 0;
				}
				else{
					offspring.x_coord = this.x_coord + 1;
				}
				if(this.y_coord == 0){
					offspring.y_coord = Params.world_height-1;
				}
				else{
					offspring.y_coord = this.y_coord - 1;
				}
				break;
			case 2: // up
				if(this.y_coord == 0){
					offspring.y_coord = Params.world_height-1;
				}
				else{
					offspring.y_coord = this.y_coord - 1;
				}
				break;
			case 3: // left and up
				if(this.x_coord == 0){
					offspring.x_coord = Params.world_width-1;
				}
				else{
					offspring.x_coord = this.x_coord - 1;
				}
				if(this.y_coord == 0){
					offspring.y_coord = Params.world_height-1;
				}
				else{
					offspring.y_coord = this.y_coord - 1;
				}
				break;
			case 4: // left
				if(this.x_coord == 0){
					offspring.x_coord = Params.world_width-1;
				}
				else{
					offspring.x_coord = this.x_coord - 1;
				}
				break;
			case 5: // left and down
				if(this.x_coord == 0){
					offspring.x_coord = Params.world_width-1;
				}
				else{
					offspring.x_coord = this.x_coord - 1;
				}
				if(this.y_coord == Params.world_height-1){
					offspring.y_coord = 0;
				}
				else{
					offspring.y_coord = this.y_coord + 1;
				}
				break;
			case 6: // down
				if(this.y_coord == Params.world_height-1){
					offspring.y_coord = 0;
				}
				else{
					offspring.y_coord = this.y_coord + 1;
				}
				break;
			case 7: // right and down
				if(this.x_coord == Params.world_width-1){
					offspring.x_coord = 0;
				}
				else{
					offspring.x_coord = this.x_coord + 1;
				}
				if(this.y_coord == Params.world_height-1){
					offspring.y_coord = 0;
				}
				else{
					offspring.y_coord = this.y_coord + 1;
				}
				break;
		}

	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			Class myCrit = Class.forName("assignment4." + critter_class_name);
				Critter obj = (Critter) myCrit.newInstance();

				obj.energy = Params.start_energy;
				obj.x_coord = getRandomInt(Params.world_width-1);
				obj.y_coord = getRandomInt(Params.world_height-1);
				population.add(obj);
				if(!worldSet.contains(critter_class_name)){
				    worldSet.add(critter_class_name);
                }
		}
		catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}

		catch (InstantiationException | IllegalAccessException g){
			g.printStackTrace();
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		for(Critter c : population){
			try{
				if (Class.forName("assignment4." + critter_class_name).isInstance(c)) {
					result.add(c);
				}

			}catch(Exception e){
				e.printStackTrace();
				throw new InvalidCritterException(critter_class_name);
			}
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
		worldSet.clear();
	}


	/**
	 * finds all the critters that occupy a certain position
	 * @param x x coordinate of the position being checked
	 * @param y y coordinate of the position being checked
	 * @return a List of Critters that occupy that position
	 */
	public static List<Critter> critterEncounters(int x, int y){
		List<Critter> encountersList = new java.util.ArrayList<>();
		for(Critter crit : population){
			if(crit.x_coord == x && crit.y_coord == y){
				encountersList.add(crit);
			}
		}
		return encountersList;
	}

	/**
	 * function handles all encounters in each position of the world
	 */
	public static void doEncounters(){
		for(int y = 0; y < Params.world_height; y++){
			for(int x = 0; x < Params.world_width; x++){
				List<Critter> encountersList = critterEncounters(x,y);
				//if(encountersList.size() == 2){ //STAGE 2: ONLY 2 CRITTERS OCCUPY A POSITION
				while(encountersList.size() > 1){ // encounter needed
					int rand1 = 0;
					int rand2 = 0;
					if(encountersList.get(0).fight(encountersList.get(1).toString())
							&& encountersList.get(1).fight(encountersList.get(0).toString())){
						// both returned true so both critters want to fight
						if(encountersList.get(0).x_coord == encountersList.get(1).x_coord
								&& encountersList.get(0).y_coord == encountersList.get(1).y_coord
								&& encountersList.get(0).energy > 0
								&& encountersList.get(1).energy > 0){
							// if both critters are still in the same position and are both still alive
							rand1 = Critter.getRandomInt(encountersList.get(0).energy);
							rand2 = Critter.getRandomInt(encountersList.get(1).energy);
						}
					}
					else{
						// both critters did not return true and even if one did not want to fight,
						// but it was not able to walk or run then they occupy the same position
						// and an encounter must still occur
						if(!encountersList.get(0).fight(encountersList.get(1).toString())){
							// Critter 1 did not want to fight
							rand1 = 0;
						}
						if(!encountersList.get(1).fight(encountersList.get(0).toString())){
							// Critter 1 did not want to fight
							rand2 = 0;
						}
					}
					if(rand1 >= rand2){
						// Critter 1 wins
						encountersList.get(0).energy += (int)Math.floor(encountersList.get(1).energy*0.5);
						encountersList.get(1).energy = 0; // Critter 2 dies
						encountersList.remove(1);
					}
					else{
						// Critter 2 wins
						encountersList.get(1).energy += (int)Math.floor(encountersList.get(0).energy*0.5);
						encountersList.get(0).energy = 0; // Critter 1 dies
						encountersList.remove(0);
					}
				}
			}
		}
	}

	/**
	 * subtract rest energy cost from all critters including Algae
	 */
	public static void updateRestEnergy(){
		// rest energy subtracted from all critters regardless if moved or not
		for(Critter crit : population){
			crit.energy -= Params.rest_energy_cost;
		}
	}

	/**
	 * removes all dead critters from the population
	 */
	public static void removeDead(){
		Iterator<Critter> it = population.iterator();
		while(it.hasNext()){
			Critter crit = it.next();
			if(crit.energy <= 0){
				it.remove();
			}
		}
	}

    /**
     * generates new Algae critters for every world time step
     */
	private static void genAlgae(){
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try{
				// this automatically puts the new instance into the population through makeCritter
				makeCritter("Algae");

			}
			catch(Exception e){
				// do nothing
			}
		}
	}
	private static int NumberofInstances(String type){
		int result = 0;
		for(Critter c : population){
			try{
				if(Class.forName("assignment4." + type).isInstance(c)){
					result++;
				}
			}
			catch(Exception e){
				// do nothing rn
			}
		}
		return result;
	}
    /**
     * implements all the time steps of every critter in the world, resolves all encounters, updates the
     * rest energy, removes all dead critters, generates algae for every world time step, and add all
     * baby critters to the population
     */

	public static void worldTimeStep() {
		System.out.println("# of old Craig "+NumberofInstances("Craig"));
		System.out.println("# of old population: "+population.size());

		// 1. increment timestep; timestep++;
		// 2. doTimeSteps();
		doTimeSteps();
		// 3. Do the fights. doEncounters();
		doEncounters();
		// 4. updateRestEnergy();
		updateRestEnergy();
		removeDead();
		// 5. Generate Algae genAlgae();
		System.out.println("# of old Algae: "+NumberofInstances("Algae"));
		genAlgae();
		System.out.println("# of new Algae pop: "+NumberofInstances("Algae"));
		// 6. Move babies to general population.
		population.addAll(babies);
		babies.clear();

		System.out.println("# of new total Craig "+NumberofInstances("Craig"));
		System.out.println("Number of total population: "+population.size());
	}
	/** Goes through every critter in the population and does its step
	 * */
	private static void doTimeSteps(){
		for(Critter crit: population){
			//System.out.println(crit+"old coords: "+crit.x_coord+", "+crit.y_coord);
			crit.doTimeStep();
			//System.out.println(crit+"new coords: "+crit.x_coord+", "+crit.y_coord);

		}
		// babies will do their timestep if they are already included in the population
		// which is the turn after they are... pooped out
	}
	/**
	 * Prints the simulation model to the console
	 */
	public static void displayWorld() {
	    // plus 2 because of the border
        String[][] array = new String[Params.world_height+2][Params.world_width+2];

        array[0][0] = "+";
        array[Params.world_height+ 1][0] = "+";
        array[Params.world_height + 1][Params.world_width + 1] = "+";
        array[0][Params.world_width + 1] = "+";

        for(int i = 1; i < Params.world_width +1; i++){
            array[0][i] = "-";
            array[Params.world_height +1][i] = "-";
        }

        for(int i = 1; i < Params.world_height+1; i++){
            array[i][0] = "|";
            array[i][Params.world_width +1] = "|";
        }

        for(int r = 0; r < Params.world_height+2; r++){
            for(int c = 0; c < Params.world_width+2; c++){
                try{
                    boolean occupied = false;
                    for(Critter crit : population){
                        if(crit.x_coord+1 == r && crit.y_coord+1 == c && array[r][c] == null){
                            array[r][c] = crit.toString();
                            occupied = true;
                        }
                        // new
                        else if(crit.x_coord+1 == r && crit.y_coord+1 == c && array[r][c] != null && occupied==true){
                        	array[r][c] = crit.toString();
						}
                    }
                    if(!occupied && array[r][c] == null){
                       array[r][c] = " ";
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.print(array[r][c]);
            }

            System.out.println("");
        }

	}
}
