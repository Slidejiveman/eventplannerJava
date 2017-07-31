package eventplannerDAO;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eventplannerPD.Company;
import eventplannerPD.User;

/**
 * Database Access object for the User PD class.
 * Allows things to be returned from the database.
 * @author rdnot
 *
 */
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
	 * Returns the list of users in the company with a maximum and minimum page range.
	 * @param company - the company that employs the users
	 * @param page - the starting user to fetch
	 * @param perPage - the last user that is grabbed
	 * @return the list of users between page and perPage's values
	 */
	public static List<User> getAllUsersForCompany(Company company, int page, int perPage) {
		TypedQuery<User> query = EM.getEntityManager().createQuery("SELECT user FROM user user", User.class);
	      return query.setFirstResult(page).setMaxResults(perPage).getResultList();
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
	 * Returns a user by the specified Id number.
	 * Since the idNumber is the primary key of user, this is the same
	 * as findUserById.
	 * @param idNumber - The id number of the desired User
	 * @return user with the specified Id number
	 */
	public static User findUserByIdNumber(String idNumber)
    {
      String qString = "SELECT user FROM user user WHERE user.id ="+idNumber;
      Query query = EM.getEntityManager().createQuery(qString);
      User user = (User)query.getSingleResult();
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
