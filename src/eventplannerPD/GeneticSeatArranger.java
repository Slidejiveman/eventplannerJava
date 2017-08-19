package eventplannerPD;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import eventplannerDAO.GuestDAO;
import eventplannerDAO.GuestListDAO;
import eventplannerPD.enums.TableShape;
import eventplannerPD.enums.TableSize;

import java.util.Random;
import java.util.TreeMap;

/**
 * The genetic seat arranger class is a static class used to 
 * house the algorithm that solves the table seating problem.
 */
public class GeneticSeatArranger {

    /**
     * This method creates the Seating Arrangement. 
     * This method is the primary unit of processing in this problem domain.
     * @param event
     * @return Returns the created seating arrangement need to finish the planning.
     */
	/*
	 * To solve the problem of seating people to tables, we will implement the Genetic
	 * Algorithm as follows:
	 * 	1. We shall randomly seat the guests at the tables in four different ways. The
	 * 	   resulting 4 seating assignments will be our initial population for our first
	 *     generation.
	 *	2. We shall initialize each seating plan fitness score with 0;
	 *	3. We shall initialize each guest seat score by 0, and create a one dimensional 
	 *	   array named guestsSeatScores to contain all the guests seats scores.
	 *	4. Based on the data about who can seat with whom and who cannot, the 
	 *	   system shall use a 2D matrix (just like a table) named guestsMatrix to 
	 *	   evaluate the fitness of each seating assignment.
	 *		a. The row index i is a number that will represent a guest number 0 to n-1,
	 *		   n being the total number of guests on the event’s guests list.
	 *		b. The column index j is a number that will represent a guest number 0 
	 *		   to n-1, n being the total number of guests on the event’s guests list.
	 *		c. The matrix values will be initialized as follows 
	 *			i.If person i must seat with person j, the matrix value ki,j will be 
	 *		   	  initialized to 10.
	 *			ii. If person i does not care about seating with person j and 
	 *				person j does not care either, the matrix value ki,j will be 
	 *				initialized to 5.
	 *			iii. If person i cannot seat with person j, the matrix value ki,j will 
	 *				 be initialized to 0.
	 *		d. For each seating plan in the population set, the system shall evaluate 
	 *		   the fitness score as follows:
	 *			i. For person i, (i being an index from 0 to the total number of 
	 *			   guests to attend the event -1)
	 *				1. For person j (j being an index from 0 to the total 
	 *				   number of guests to attend the event -1)
	 *					a. If person i and j are seated on the same table,
	 *						Fitness score += guestsMatrix value ki,j
	 *	5. While we’ve not yet reached the 10th generation,
	 *		a. For each possible selection of 2 solutions among the solutions in the 
	 *		   solutions set (there will always be 4 or them, therefore 6 possible pairs)
	 *			i. Move the first half of the seating assignments from parent 1 
	 *			   and the second half of the seating assignments from parent 2 to 
	 *			   produce seating assignments from child solution 1.
	 *			ii. Move the first half of the seating assignments from parent 2 
	 *			    and the second half of the seating assignments from parent 1 to 
	 *				produce seating assignments from child solution 1.
	 *			iii. Randomly rearrange the seats for each four people starting 
	 *				 from the first quartile, second quartile, and the 3rd quartile of each 
	 *				 child’s solution (mutation)
	 *			iv. Add the children solutions to the population set
	 *			v. Re-evaluate the fitness of the solutions and select 4 fittest 
	 *			   solutions from the population set
	 *				1. Total the seats scores for each solution
	 *				2. Select 4 solutions with the 4 highest scores.
	 *	6. Roll a 4 sided die and randomly select one of the 4 resulting fittest solutions
	 */ 
	public static SeatingArrangement generateSeatingArrangement(Event event) {
		List<GuestList> guestlists = GuestListDAO.listGuestLists();
		//For each event, produce a seating plan
		//set guest list for the event.
		for(GuestList guestlist: guestlists){
			if (guestlist.getEvent().equals(event)){
				event.setGuestList(guestlist);
			}
		}
		for(Guest guest: event.getGuestList().getGuests()){
			guest.setGuestsToAvoidList(GuestDAO.listGuestsToAvoid(guest.getId()));
			guest.setGuestsToSitWithList(GuestDAO.listGuestsToSitWith(guest.getId()));
		}
		int tablesToUse = event.calculateNumTables();
		int counter=0;
		event.setTables(new ArrayList<EventTable>());
		event.setSeatingAssigment(new SeatingArrangement());
    	System.out.println("There are "+event.getTables().size()+" tables for the event");

		System.out.println("Tables to use: "+tablesToUse);
		while(counter<tablesToUse){
			EventTable table = new EventTable();
			table.setEvent(event);
			table.setShape(TableShape.Circle);
			table.setSize(TableSize.Two);
			System.out.println("Created with size: "+table.getSize());
			table.setNumber(counter+1);
			table.setGuests(new ArrayList<Guest>());
			event.getTables().add(table);
			counter++;
		}
		
    	System.out.println("There are "+event.getTables().size()+" tables for the event");

    	List<SeatingArrangement> arrangementsPopulation  = generateInitPopulation(event);  
    	List<SeatingArrangement> bestSeatingArrangements = produceBestArrangements(arrangementsPopulation);
    	System.out.println("\n\n"+"All best arrangements");
    	for(SeatingArrangement s: bestSeatingArrangements){
    		for(Entry<Guest,EventTable> seat: s.getSeatingAssignments().entrySet()){
    			System.out.println(seat.getKey().getName()+" seats on table "+seat.getValue().getNumber());
    		}
    		System.out.println("Fitness score: "+s.getArrangementScore()+"\n\n");
    	}
    	SeatingArrangement choosenBestArrangement = selectRandomBestArrangement(bestSeatingArrangements);
    	
    	System.out.println("There are "+event.getTables().size()+" tables for the event");
    	return choosenBestArrangement;
    }

	/*
	 * The following method generates four seating arrangements samples to start with
	 */
	private static List<SeatingArrangement> generateInitPopulation( Event event) {
		List<SeatingArrangement> arrangementsPopulation  = new ArrayList<SeatingArrangement>();
		List<Guest> guests = (List<Guest>) event.getGuestList().getGuests();
		List<EventTable> tables = (List<EventTable>) event.getTables();
		for(int count=0;count<4;count++){
			//System.out.println("Generating assignment");
			SeatingArrangement seatingArrangement= new SeatingArrangement(0);
			seatingArrangement.setTables(new ArrayList<EventTable>());
			seatingArrangement.setEvent(event);
			for(EventTable table: seatingArrangement.getEvent().getTables()){
				table.setGuests(new ArrayList<Guest>());
			}
			Integer[]randomTableNums = generateRandomAssignments(event);
			for(int guestsCount=0;guestsCount<guests.size();guestsCount++){
				//System.out.println("Assigning a guest a table");
				EventTable assignedTable = tables.get((randomTableNums[guestsCount])-1);
				seatingArrangement.getSeatingAssignments().put(guests.get(guestsCount),assignedTable );
				//assignedTable.getGuests().add(guests.get(guestsCount));
			}
			for(Entry<Guest,EventTable> entry: seatingArrangement.getSeatingAssignments().entrySet()){
				EventTable table = entry.getValue();
				Guest guest = entry.getKey();
				guest.setTable(table);
				if(!table.getGuests().contains(guest))
					table.getGuests().add(guest);
				if(!seatingArrangement.getTables().contains(table))
					seatingArrangement.getTables().add(table);
			}
	
			arrangementsPopulation.add(seatingArrangement);
		}
		return arrangementsPopulation;
	}
	/*
	 * The following method assigns each guest a table number randomly
	 * The assignment is based on the count of tables to be used, and random numbers are generated by the tablePicker object
	 */
	private static Integer[] generateRandomAssignments(Event event) {
		Integer numberOfGuests = event.getGuestList().getGuests().size();
		Integer[]randomAssignments = new Integer[numberOfGuests];
		Integer tablesUsed = event.getTables().size();
		Integer counter =0;
		Random tablePicker = new SecureRandom();
		while (counter<numberOfGuests){
			randomAssignments[counter]=tablePicker.nextInt(tablesUsed)+1;
			counter++;
		}
		return randomAssignments;
	}
	/*
	 * The following function evaluates the fitness of a seating arrangement
	 * based on the data about who must sit with whom and who must not
	 */
	private static Integer evaluatePlanEfficiency(SeatingArrangement s) {
		int fitnessScore=0;
		int numberOfGuests = s.getEvent().getGuestList().getGuests().size();
		int[][] guestsMatrix = new int[numberOfGuests][numberOfGuests];
		
		for(int row =0; row<numberOfGuests;row++){
			for(int column=0;column<numberOfGuests;column++){
				Guest rowGuest = ((List<Guest>)s.getEvent().getGuestList().getGuests()).get(row);
				Guest colGuest = ((List<Guest>)s.getEvent().getGuestList().getGuests()).get(column);
				
				if(rowGuest.getGuestsToSitWithList()!=null){
					if(rowGuest.getGuestsToSitWithList().contains(colGuest)){
						guestsMatrix[row][column]=10;
					}
				}
				else if (rowGuest.getGuestsToAvoidList()!=null){
					if(rowGuest.getGuestsToAvoidList().contains(colGuest)){
						guestsMatrix[row][column]=0;
					}
				}
				if((rowGuest.getGuestsToSitWithList().size()==0)&&(rowGuest.getGuestsToAvoidList().size()==0))
				{
					guestsMatrix[row][column]=5;
				}
			}
		}
		for(int i=0; i<numberOfGuests;i++){
			for(int j=0;j<numberOfGuests;j++){
				Guest ithGuest = ((List<Guest>)s.getEvent().getGuestList().getGuests()).get(i);
				Guest jthGuest = ((List<Guest>)s.getEvent().getGuestList().getGuests()).get(j);
				
				for(Entry<Guest,EventTable> entry:s.getSeatingAssignments().entrySet()){
					if (entry.getKey().equals(ithGuest))
						ithGuest.setTable(entry.getValue());
					else if(entry.getKey().equals(jthGuest))
						jthGuest.setTable(entry.getValue());
				}
				if(!ithGuest.equals(jthGuest)){

					if(ithGuest.getTable().equals(jthGuest.getTable())){
						fitnessScore+=(int)guestsMatrix[i][j];
					}
				}
			}
		}
		s.setArrangementScore(fitnessScore);
		return fitnessScore;
	}
	/*
	 * The following method uses the genetic model to produce the bests of the possible
	 * seating arrangements.
	 */
	private static List<SeatingArrangement> produceBestArrangements(
			List<SeatingArrangement> arrangementsPopulation) {
		int generation=0;
		while (generation<50){
			List<SeatingArrangement> children = new ArrayList<SeatingArrangement>();
			List<SeatingArrangement> mutatedChildren = new ArrayList<SeatingArrangement>();
			for(SeatingArrangement sa1:arrangementsPopulation){
				for(SeatingArrangement sa2:arrangementsPopulation){
					sa1.setTables(new ArrayList<EventTable>());
					sa2.setTables(new ArrayList<EventTable>());

					if(!sa1.equals(sa2)){
						children = produceChildren(sa1,sa2);
						mutatedChildren = mutateChildren(children);
					}
				}
			}
			for(SeatingArrangement child: mutatedChildren){
				arrangementsPopulation.add(child);
			}
			//Select the top 4 best seating arrangements
			arrangementsPopulation = pickTopArrangements(arrangementsPopulation);
			generation ++;
		}
		return arrangementsPopulation;
	}
	/*
	 * The following method produces two children from two parent arrangements
	 */
	private static List<SeatingArrangement> produceChildren(SeatingArrangement sa1, SeatingArrangement sa2) {
		List<SeatingArrangement> children = new ArrayList<SeatingArrangement>();
		TreeMap<Guest,EventTable> secondPartforChild1=new TreeMap<Guest,EventTable>();
		TreeMap<Guest,EventTable> secondPartforChild2= new TreeMap<Guest,EventTable>();
		SeatingArrangement childArrangement1 = new SeatingArrangement(0);
		SeatingArrangement childArrangement2 = new SeatingArrangement(0);
		childArrangement1.setTables(new ArrayList<EventTable>());
		childArrangement1.setEvent(sa1.getEvent());
		childArrangement2.setTables(new ArrayList<EventTable>());
		childArrangement2.setEvent(sa1.getEvent());
		for(EventTable table: childArrangement1.getEvent().getTables()){
			table.setGuests(new ArrayList<Guest>());
		}
		for(EventTable table: childArrangement2.getEvent().getTables()){
			table.setGuests(new ArrayList<Guest>());
		}
		int middleIndex = (sa1.getSeatingAssignments().size())/2;
		int middleTracker =0;
		
		//Produce the children
		//Build the first half of the first child
		for(Entry<Guest,EventTable> e: sa1.getSeatingAssignments().entrySet()){
			if(middleTracker<=middleIndex){
				childArrangement1.getSeatingAssignments().put(e.getKey(), e.getValue());
			}else{
				if(middleTracker<sa1.getSeatingAssignments().size()){
					secondPartforChild2.put(e.getKey(), e.getValue());
				}
			}
			middleTracker++;
		}

		//Build the first half for the second child
		middleTracker=0;
		for(Entry<Guest,EventTable> e: sa2.getSeatingAssignments().entrySet()){
			if(middleTracker<=middleIndex){
				childArrangement2.getSeatingAssignments().put(e.getKey(), e.getValue());
			}else{
				if(middleTracker<sa2.getSeatingAssignments().size()){
					secondPartforChild1.put(e.getKey(), e.getValue());
				}
			}
			middleTracker++;
		}
		
		//Append the second half from parent 1 to child 2
		int count = sa2.getSeatingAssignments().size()-middleIndex;
		for(Entry<Guest,EventTable> e: secondPartforChild2.entrySet()){
			childArrangement2.getSeatingAssignments().put((Guest) sa2.
					getSeatingAssignments().keySet().toArray()[count],e.getValue());
			count++;
		}
		
		//Append the second half from parent 2 to child 1
		int count2 = sa1.getSeatingAssignments().size()-middleIndex;
		for(Entry<Guest,EventTable> e: secondPartforChild1.entrySet()){
			childArrangement1.getSeatingAssignments().put((Guest) sa1.
					getSeatingAssignments().keySet().toArray()[count2],e.getValue());
			count2++;
		}	
		
		for(Entry<Guest,EventTable> entry: childArrangement1.getSeatingAssignments().entrySet()){
			EventTable table = entry.getValue();
			Guest guest = entry.getKey();
			guest.setTable(table);
			if(!table.getGuests().contains(guest))
				table.getGuests().add(guest);
			if(!childArrangement1.getTables().contains(table))
				childArrangement1.getTables().add(table);

		}
		
		for(Entry<Guest,EventTable> entry: childArrangement2.getSeatingAssignments().entrySet()){
			EventTable table = entry.getValue();
			Guest guest = entry.getKey();
			guest.setTable(table);
			if(!table.getGuests().contains(guest))
				table.getGuests().add(guest);
			if(!childArrangement2.getTables().contains(table))
				childArrangement2.getTables().add(table);

		}
		

		children.add(childArrangement1);
		children.add(childArrangement2);
		
		return children;
	}
	
	/*
	 * The following method mutate the produced arrangements offsprings
	 */
	private static List<SeatingArrangement> mutateChildren(List<SeatingArrangement> children) {
		List<SeatingArrangement> childrenAfterMutation = new ArrayList<SeatingArrangement>();
		for(SeatingArrangement child:children){
			SeatingArrangement mutatedChild = swapAssignments(child);
			childrenAfterMutation.add(mutatedChild);
		}
		
		return childrenAfterMutation;
	}
	
	/*
	 * The following method swaps tables for guests in the n/4 with 3n/4, 
	 * n/n with n/2 assignments (n being the total number of guests)
	 */
	private static SeatingArrangement swapAssignments(SeatingArrangement child) {
		int secondQuarterIndexOfAssignments =(child.getSeatingAssignments().size()/2)-1;
		int firstQuarterIndexOfAssignments=0;
		int fourthQuarterIndexOfAssignments=0;
		int thirdQuarterIndexOfAssignments=0;
		if (child.getSeatingAssignments().size()%4==0){
			firstQuarterIndexOfAssignments = (child.getSeatingAssignments().size()/4)-1;
			thirdQuarterIndexOfAssignments = secondQuarterIndexOfAssignments+(child.getSeatingAssignments().size()/4);
			fourthQuarterIndexOfAssignments = child.getSeatingAssignments().size()-1;	
		}else{
			fourthQuarterIndexOfAssignments = child.getSeatingAssignments().size()-1;	
			firstQuarterIndexOfAssignments=secondQuarterIndexOfAssignments+(child.getSeatingAssignments().size()/4);
			thirdQuarterIndexOfAssignments = thirdQuarterIndexOfAssignments*3;
		}
		
		/*swap the seats for the guest at the first quarter of the assignments
		 *with the one at the third quarter of the assignments
		 */
		int index=0;
		for(Entry<Guest,EventTable> assignmentFromChild: child.getSeatingAssignments().entrySet()){
			EventTable tableA = null;
			EventTable tableB = null;
			if(index==0){
				Random assignmentIndexPicker = new SecureRandom();
				int indexOfAssignmentToSwapWith  = assignmentIndexPicker.nextInt(thirdQuarterIndexOfAssignments+1);
				if(indexOfAssignmentToSwapWith<fourthQuarterIndexOfAssignments){
					tableA=assignmentFromChild.getValue();
					EventTable tempTable = tableA;
					Guest guestA =  assignmentFromChild.getKey();
					Guest guestB = ((Guest)child.getSeatingAssignments().keySet().toArray()[indexOfAssignmentToSwapWith]);
					tableB = guestB.getTable();

					tableA.getGuests().remove(guestA);
					guestA.setTable(tableB);
					tableB.getGuests().add(guestA);
					
					tableB.getGuests().remove(guestB);
					guestB.setTable(tempTable);
					tempTable.getGuests().add(guestB);
				}
			}
			else if(index==firstQuarterIndexOfAssignments){
				tableA=assignmentFromChild.getValue();
				EventTable tempTable = tableA;
				Guest guestA =  assignmentFromChild.getKey();
				Guest guestB = ((Guest)child.getSeatingAssignments().keySet().toArray()[thirdQuarterIndexOfAssignments]);
				tableB = guestB.getTable();

				tableA.getGuests().remove(guestA);
				guestA.setTable(tableB);
				tableB.getGuests().add(guestA);
				
				tableB.getGuests().remove(guestB);
				guestB.setTable(tempTable);
				tempTable.getGuests().add(guestB);
			}
			else if(index ==secondQuarterIndexOfAssignments){
				tableA=assignmentFromChild.getValue();
				EventTable tempTable = tableA;
				Guest guestA =  assignmentFromChild.getKey();
				Guest guestB =((Guest)child.getSeatingAssignments().keySet().toArray()[fourthQuarterIndexOfAssignments]);
				tableB = guestB.getTable();
				
				tableA.getGuests().remove(guestA);
				guestA.setTable(tableB);
				tableB.getGuests().add(guestA);
				
				tableB.getGuests().remove(guestB);
				guestB.setTable(tempTable);
				tempTable.getGuests().add(guestB);
			}
			index++;
		}
		return child;
	}
	/*
	 * This method picks top Four arrangement from each generation to advance to the
	 * next generation until the desired arrangement is produced
	 */
	private static List<SeatingArrangement> pickTopArrangements(
			List<SeatingArrangement> arrangementsPopulation) {
		int [] arrangementScores = new int[arrangementsPopulation.size()];
		int count=0;
		int topScore = arrangementScores[0];
		for(SeatingArrangement s: arrangementsPopulation){
			arrangementScores[count] = evaluatePlanEfficiency(s);
			if(arrangementScores[count]>topScore){
				topScore = arrangementScores[count];
			}	
			count++;
		}
		
		//Add every seating plan with a top score to the arrayList return
		List<SeatingArrangement> bestArrangements = new ArrayList<SeatingArrangement>();
		for(SeatingArrangement sa: arrangementsPopulation){
			if(sa.getArrangementScore().equals(topScore)){
				bestArrangements.add(sa);
			}
		}	
		return bestArrangements;
	}
	
	/*private static EventTable getTableWithSpace(SeatingArrangement s) {
		for(EventTable t: s.getEvent().getTables()){
			int guestsPerTable=s.getEvent().tableSizeToInt(t.getSize());
			int peopleOnTablet = t.getGuests().size();
					
			System.out.println(peopleOnTablet+" were assigned to table "+t.getNumber());
			System.out.println(guestsPerTable+" can fit on the table "+t.getNumber());

			if (peopleOnTablet<guestsPerTable){
				System.out.println("Table "+t.getNumber()+" has a room. ");
				return t;
			}
		}
		return null;
	}*/

	/*
	 * This method randomly selects one of the generated best arrangements
	 */
	private static SeatingArrangement selectRandomBestArrangement(
			List<SeatingArrangement> bestSeatingArrangements) {
		Random arrangementPicker = new SecureRandom();
		int randomArrangementIndex = arrangementPicker.nextInt(bestSeatingArrangements.size());
		return bestSeatingArrangements.get(randomArrangementIndex);
	}
}