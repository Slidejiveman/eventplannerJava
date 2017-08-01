package eventplannerDAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import eventplannerPD.Guest;
import eventplannerPD.GuestGuestAvoidBridge;
import eventplannerPD.GuestGuestSitWithBridge;

public class GuestDAO {
	/**
	 * Add the given guest to the database
	 * @param guest to be added to the database.
	 */
	public static void saveGuest(Guest guest) {
		EM.getEntityManager().persist(guest);
	}
	
	/**
	 * Alias for saveguest method. They are identical.
	 * @param guest
	 */
	public static void addGuest(Guest guest) {
		EM.getEntityManager().persist(guest);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all guests in the database. This is very useful for list panels.
	 * @return List<guest> - all guests in the database
	 */
	public static List<Guest> listGuests() {
		TypedQuery<Guest> query = EM.getEntityManager().createQuery("SELECT guest FROM guest guest", Guest.class);
		return query.getResultList();
	}
	
	/**
	 * Guests need to be returned by Event in order to get all of the guests that are on
	 * the same guest list. This will be more relevant than getting all guests.
	 * @return All guests who are going to the same event: based on Guest List
	 */
	public static List<Guest> listGuestsByGuestList(int guestlistId) {
		TypedQuery<Guest> query = EM.getEntityManager().createQuery("SELECT guest FROM guest guest WHERE guest.guestlist.id="+guestlistId, Guest.class);
		return query.getResultList();
	}
	
	/**
	 * This method is needed to get the guests that a given guest is supposed
	 * to avoid setting with
	 * 
	 * Note: I may need to change this to accept only the ID to make it easier to use.
	 * 
	 * @param guest - the guest on the front end side we are interested in.
	 * @return The list of guests this person should not sit with
	 */
	public static List<Guest> listGuestsToAvoid(Guest guest) {
		TypedQuery<GuestGuestAvoidBridge> query = EM.getEntityManager().createQuery("SELECT guestGuestAvoidBridge FROM guesttoavoid WHERE guest_id="+guest.getId(), GuestGuestAvoidBridge.class);
		List<Guest> guestsToAvoid = new ArrayList<Guest>();
		for(GuestGuestAvoidBridge row : query.getResultList()) {
		    Guest g = findGuestById(row.getGuestAvoidId());
		    guestsToAvoid.add(g);
		}
		return guestsToAvoid;
	}
	
	/**
	 * This method is needed to get the guests that a given guest is supposed
	 * to be sitting with
	 * 
	 * Note: I may need to change this to accept only the ID to make it easier to use.
	 * 
	 * @param guest - the guest on the front end side we are interested in.
	 * @return The list of guests this person should sit with
	 */
	public static List<Guest> listGuestsToSitWith(Guest guest) {
		TypedQuery<GuestGuestSitWithBridge> query = EM.getEntityManager().createQuery("SELECT guestGuestSitWithBridge FROM guesttositwith WHERE guest_id="+guest.getId(), GuestGuestSitWithBridge.class);
		List<Guest> guestsToSitWith = new ArrayList<Guest>();
		for(GuestGuestSitWithBridge row : query.getResultList()) {
		    Guest g = findGuestById(row.getGuestSitWithId());
		    guestsToSitWith.add(g);
		}
		return guestsToSitWith;
	}
	
	/**
	 * Returns the guest specified by the given id number.
	 * @param id - the number that uniquely identifies the guest
	 * @return guest - the guest specified by the id
	 */
	public static Guest findGuestById(int id) {
		Guest guest = EM.getEntityManager().find(Guest.class, new Integer(id));
		return guest;
	}
	
	/**
	 * Removes the given guest from the database.
	 * @param guest
	 */
	public static void removeGuest(Guest guest) {
		EM.getEntityManager().remove(guest);
	}
	
	/**
	 * An alias for the removeguest method. It is identical.
	 * @param guest
	 */
	public static void deleteGuest(Guest guest) {
		EM.getEntityManager().remove(guest);
	}
}
