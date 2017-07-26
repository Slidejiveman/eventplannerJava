package eventplannerDAO;

import java.util.List;
import javax.persistence.TypedQuery;
import eventplannerPD.User;

public class UserDAO {
	/**
	 * Add the given User to the database
	 * @param User to be added to the database.
	 */
	public static void saveUser(User user) {
		EM.getEntityManager().persist(user);
	}
	
	/**
	 * Alias for saveUser method. They are identical.
	 * @param User
	 */
	public static void addUser(User user) {
		EM.getEntityManager().persist(user);
	}
	
	// The SQL statements need to be checked.
	/**
	 * Returns the list of all Users in the database. This is very useful for list panels.
	 * @return List<User> - all Users in the database
	 */
	public static List<User> listUsers() {
		TypedQuery<User> query = EM.getEntityManager().createQuery("SELECT user FROM user user", User.class);
		return query.getResultList();
	}
	
	/**
	 * Returns the User specified by the given id number.
	 * @param id - the number that uniquely identifies the User
	 * @return User - the User specified by the id
	 */
	public static User findUserById(int id) {
		User user = EM.getEntityManager().find(User.class, new Integer(id));
		return user;
	}
	
	/**
	 * Removes the given User from the database.
	 * @param User
	 */
	public static void removeUser(User user) {
		EM.getEntityManager().remove(user);
	}
	
	/**
	 * An alias for the removeUser method. It is identical.
	 * @param User
	 */
	public static void deleteUser(User user) {
		EM.getEntityManager().remove(user);
	}
}
