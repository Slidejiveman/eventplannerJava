package eventplannerPD;

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
        // TODO - implement GeneticSeatArranger.generateSeatingArrangement
        throw new UnsupportedOperationException();
    }

}