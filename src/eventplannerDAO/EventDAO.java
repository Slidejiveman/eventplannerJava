package eventplannerDAO;

import java.util.List;
import javax.persistence.TypedQuery;
import eventplannerPD.Event;

/**
 * This class will hold the database access code so that we can read values in
 * from the database and populate the application lists with good data.
 * 
 * @author rdnot
 *
 */
public class EventDAO {
    
	/**
	 * Add the given Event to the database
	 * @param Event to be added to the database.
	 */
	public static void saveEvent(Event event) {
		EM.getEntityManager().persist(event);
	}
	
	/**
	 * Alias for saveEvent method. They are identical.
	 * @param Event
	 */
	public static void addEvent(Event event) {
		EM.getEntityManager().persist(event);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all Events in the database. This is very useful for list panels.
	 * @return List<Event> - all Events in the database
	 */
	public static List<Event> listEvents() {
		TypedQuery<Event> query = EM.getEntityManager().createQuery("SELECT event FROM event event", Event.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the Event specified by the given id number.
	 * @param id - the number that uniquely identifies the Event
	 * @return Event - the Event specified by the id
	 */
	public static Event findEventById(int id) {
		Event event = EM.getEntityManager().find(Event.class, new Integer(id));
		return event;
	}
	
	/**
	 * Removes the given Event from the database.
	 * @param Event
	 */
	public static void removeEvent(Event event) {
		EM.getEntityManager().remove(event);
	}
	
	/**
	 * An alias for the removeEvent method. It is identical.
	 * @param Event
	 */
	public static void deleteEvent(Event event) {
		EM.getEntityManager().remove(event);
	}
}
