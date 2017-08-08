package eventplannerDAO;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import eventplannerPD.Customer;
import eventplannerPD.User;

/**
 * This class will hold the database access code so that we can read values in
 * from the database and populate the application lists with good data.
 * 
 * @author rdnot
 *
 */
public class CustomerDAO {
	/**
	 * Add the given Customer to the database
	 * @param Customer to be added to the database.
	 */
	public static void saveCustomer(Customer customer) {
		EM.getEntityManager().persist(customer);
	}
	
	/**
	 * Alias for saveCustomer method. They are identical.
	 * @param Customer
	 */
	public static void addCustomer(Customer customer) {
		EM.getEntityManager().persist(customer);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all Customers in the database. This is very useful for list panels.
	 * @return List<Customer> - all Customers in the database
	 */
	public static List<Customer> listCustomers() {
		TypedQuery<Customer> query = EM.getEntityManager().createQuery("SELECT customer FROM customer customer", Customer.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the Customer specified by the given id number.
	 * @param id - the number that uniquely identifies the Customer
	 * @return Customer - the Customer specified by the id
	 */
	public static Customer findCustomerById(int id) {
		Customer customer = EM.getEntityManager().find(Customer.class, new Integer(id));
		return customer;
	}
	
	public static Customer findCustomerByName(String name)
	  {
	    String qString = "SELECT customer FROM customer customer  WHERE customer.name ='"+name+"'";
	    Query query = EM.getEntityManager().createQuery(qString);
	    Customer customer = (Customer)query.getSingleResult();
	    return customer;
	  }
	
	/**
	 * Removes the given Customer from the database.
	 * @param Customer
	 */
	public static void removeCustomer(Customer customer) {
		EM.getEntityManager().remove(customer);
	}
	
	/**
	 * An alias for the removeCustomer method. It is identical.
	 * @param Customer
	 */
	public static void deleteCustomer(Customer customer) {
		EM.getEntityManager().remove(customer);
	}
}
