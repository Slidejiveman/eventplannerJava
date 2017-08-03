package eventplannerDAO;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eventplannerPD.EventTable;


public class TableDAO {
	/**
	 * Add the given EventTable to the database
	 * @param EventTable to be added to the database.
	 */
	public static void saveTable(EventTable EventTable) {
		EM.getEntityManager().persist(EventTable);
	}
	
	/**
	 * Alias for saveTable method. They are identical.
	 * @param EventTable
	 */
	public static void addTable(EventTable EventTable) {
		EM.getEntityManager().persist(EventTable);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all Tables in the database. This is very useful for list panels.
	 * @return List<EventTable> - all Tables in the database
	 */
	public static List<EventTable> listTables() {
		TypedQuery<EventTable> query = EM.getEntityManager().createQuery("SELECT eventtable FROM eventtable eventtable", EventTable.class);
		return query.getResultList();
	}
	
	/**
	 * Returns all Tables associated with an event.
	 * @param event - the event we want the tables for
	 * @return The tables for the event
	 */
	public static List<EventTable> findTablesByEvent(int eventId) {
		TypedQuery<EventTable> query = EM.getEntityManager().createQuery("SELECT eventtable FROM eventtable eventtable WHERE eventtable.event.id="+eventId, EventTable.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the EventTable specified by the given id number.
	 * @param id - the number that uniquely identifies the EventTable
	 * @return EventTable - the EventTable specified by the id
	 */
	public static EventTable findTableById(int id) {
		EventTable EventTable = EM.getEntityManager().find(EventTable.class, new Integer(id));
		return EventTable;
	}
	
	/**
	 * Returns the EventTable specified by the given EventTable number for the event.
	 * @param tableNum - the number of the EventTable, which uniquely identifies it at event scope
	 * @return EventTable - the EventTable specified by the given number
	 */
	public static EventTable findTableByNumber(String tableNum)
    {
      String qString = "SELECT eventtable FROM eventtable eventtable  WHERE eventtable.number ="+tableNum;
      Query query = EM.getEntityManager().createQuery(qString);
      EventTable EventTable = (EventTable)query.getSingleResult();
      return EventTable;
    }
	
	/**
	 * Removes the given EventTable from the database.
	 * @param EventTable
	 */
	public static void removeTable(EventTable EventTable) {
		EM.getEntityManager().remove(EventTable);
	}
	
	/**
	 * An alias for the removeTable method. It is identical.
	 * @param EventTable
	 */
	public static void deleteTable(EventTable EventTable) {
		EM.getEntityManager().remove(EventTable);
	}
}
