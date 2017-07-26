package eventplannerDAO;

import java.util.List;
import javax.persistence.TypedQuery;
import eventplannerPD.SeatingArrangement;

public class SeatingArrangementDAO {
	/**
	 * Add the given SeatingArrangement to the database
	 * @param SeatingArrangement to be added to the database.
	 */
	public static void saveSeatingArrangement(SeatingArrangement seatingArrangement) {
		EM.getEntityManager().persist(seatingArrangement);
	}
	
	/**
	 * Alias for saveSeatingArrangement method. They are identical.
	 * @param SeatingArrangement
	 */
	public static void addSeatingArrangement(SeatingArrangement seatingArrangement) {
		EM.getEntityManager().persist(seatingArrangement);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all SeatingArrangements in the database. This is very useful for list panels.
	 * @return List<SeatingArrangement> - all SeatingArrangements in the database
	 */
	public static List<SeatingArrangement> listSeatingArrangements() {
		TypedQuery<SeatingArrangement> query = EM.getEntityManager().createQuery("SELECT seatingarrangement FROM seatingarrangement seatingarrangement", SeatingArrangement.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the SeatingArrangement specified by the given id number.
	 * @param id - the number that uniquely identifies the SeatingArrangement
	 * @return SeatingArrangement - the SeatingArrangement specified by the id
	 */
	public static SeatingArrangement findSeatingArrangementById(int id) {
		SeatingArrangement seatingArrangement = EM.getEntityManager().find(SeatingArrangement.class, new Integer(id));
		return seatingArrangement;
	}
	
	/**
	 * Removes the given SeatingArrangement from the database.
	 * @param SeatingArrangement
	 */
	public static void removeSeatingArrangement(SeatingArrangement seatingArrangement) {
		EM.getEntityManager().remove(seatingArrangement);
	}
	
	/**
	 * An alias for the removeSeatingArrangement method. It is identical.
	 * @param SeatingArrangement
	 */
	public static void deleteSeatingArrangement(SeatingArrangement seatingArrangement) {
		EM.getEntityManager().remove(seatingArrangement);
	}
}
