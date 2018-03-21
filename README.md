# CritterSimulator

Project by Desiree Tang and Isabelle Villamiel

The Critter Simulator project is developed in Java and focused on developing a simulated 2D model 
that is populated by various Critters. These Critters will eat, fight, reproduce, and die. 
The controller is a text-based controller in which commands are read from System.in. 

Code Structure:
  - Critters are made from an abstract class and all Critters are stored in a List of Critters called population.
  - Four new classes were created to represent unique, custom Critters. The Critter Class is the superclass for all
  these custom Critters. All classes also have a constructor, doTimeStep(), fight(), runStats(), and toString() 
  methods. 
      1. Critter1.java - this Critter runs diagonally towards NE and only fights one type of Critter
      2. Critter2.java - this Critter only desires to smell the algae and does not like to fight
      3. Critter3.java - this Critter never backs down from a fight
      4. Critter4.java - this Critter only fights Critters that are not of its own type
