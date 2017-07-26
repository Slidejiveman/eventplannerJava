package eventplannerDAO;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import eventplannerPD.Table;


public class TableDAO {
	/**
	 * Add the given Table to the database
	 * @param Table to be added to the database.
	 */
	public static void saveTable(Table table) {
		EM.getEntityManager().persist(table);
	}
	
	/**
	 * Alias for saveTable method. They are identical.
	 * @param Table
	 */
	public static void addTable(Table table) {
		EM.getEntityManager().persist(table);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all Tables in the database. This is very useful for list panels.
	 * @return List<Table> - all Tables in the database
	 */
	public static List<Table> listTables() {
		TypedQuery<Table> query = EM.getEntityManager().createQuery("SELECT table FROM table table", Table.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the Table specified by the given id number.
	 * @param id - the number that uniquely identifies the Table
	 * @return Table - the Table specified by the id
	 */
	public static Table findTableById(int id) {
		Table Table = EM.getEntityManager().find(Table.class, new Integer(id));
		return Table;
	}
	
	/**
	 * Returns the table specified by the given Table number for the event.
	 * @param tableNum - the number of the table, which uniquely identifies it at event scope
	 * @return table - the table specified by the given number
	 */
	public static Table findTableByNumber(String tableNum)
    {
      String qString = "SELECT table FROM table table  WHERE table.table_number ="+tableNum;
      Query query = EM.getEntityManager().createQuery(qString);
      Table table = (Table)query.getSingleResult();
      return table;
    }
	
	/**
	 * Removes the given Table from the database.
	 * @param Table
	 */
	public static void removeTable(Table table) {
		EM.getEntityManager().remove(table);
	}
	
	/**
	 * An alias for the removeTable method. It is identical.
	 * @param Table
	 */
	public static void deleteTable(Table table) {
		EM.getEntityManager().remove(table);
	}
}
