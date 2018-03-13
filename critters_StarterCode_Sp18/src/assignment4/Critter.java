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
	
	protected int x_coord;
	protected int y_coord;
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
				break;
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
				break;
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
				break;
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
				break;
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
				break;
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
				break;
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
				break;
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
				break;
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
			case 2:
				if(y_coord == 0){
					y_coord = Params.world_height-1;
				}
				else{
					y_coord--;
				}
			case 4:
				if(x_coord == 0){
					x_coord = Params.world_width-1;
				}
				else{
					x_coord--;
				}

			case 6:
				if(y_coord == Params.world_height-1){
					y_coord = 0;
				}
				else{
					y_coord++;
				}

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
			Class<?> myCrit = Class.forName(critter_class_name);
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

				if (Class.forName(critter_class_name).isInstance(c)) {
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
	
	public static void worldTimeStep() {
		// Complete this method.
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
