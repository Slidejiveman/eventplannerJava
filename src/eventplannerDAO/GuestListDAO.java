package eventplannerDAO;

import java.util.List;
import javax.persistence.TypedQuery;
import eventplannerPD.GuestList;

public class GuestListDAO {
	/**
	 * Add the given GuestList to the database
	 * @param GuestList to be added to the database.
	 */
	public static void saveGuestList(GuestList guestlist) {
		EM.getEntityManager().persist(guestlist);
	}
	
	/**
	 * Alias for saveGuestList method. They are identical.
	 * @param GuestList
	 */
	public static void addGuestList(GuestList guestlist) {
		EM.getEntityManager().persist(guestlist);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all GuestLists in the database. This is very useful for list panels.
	 * @return List<GuestList> - all GuestLists in the database
	 */
	public static List<GuestList> listGuestLists() {
		TypedQuery<GuestList> query = EM.getEntityManager().createQuery("SELECT guestlist FROM guestlist guestlist", GuestList.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the GuestList specified by the given id number.
	 * @param id - the number that uniquely identifies the GuestList
	 * @return GuestList - the GuestList specified by the id
	 */
	public static GuestList findGuestListById(int id) {
		GuestList GuestList = EM.getEntityManager().find(GuestList.class, new Integer(id));
		return GuestList;
	}
	
	/**
	 * Removes the given GuestList from the database.
	 * @param GuestList
	 */
	public static void removeGuestList(GuestList guestList) {
		EM.getEntityManager().remove(guestList);
	}
	
	/**
	 * An alias for the removeGuestList method. It is identical.
	 * @param GuestList
	 */
	public static void deleteGuestList(GuestList guestList) {
		EM.getEntityManager().remove(guestList);
	}
}
