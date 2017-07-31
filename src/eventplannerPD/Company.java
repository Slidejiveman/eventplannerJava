package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import eventplannerDAO.UserDAO;

/**
 * The company class is a container class for the others. It can be refreshed
 * so that all of its fields will also be refreshed.
 * There is no way to set the company from the UI at present. The company will
 * always be Eagle Event Planning by default.
 * @author rdnot
 *
 */
@XmlRootElement(name = "company")
@Entity(name = "company")
public class Company implements Serializable {

	/**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = 702769719742745258L;
	
	/**
	 * The unique identifier of the company
	 */
	@Id
	@Column(name = "company_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * The name of the company
	 */
	@Column(name = "company_name", nullable = false)
	private String name;
	
	/**
	 * The collection of people who work for the company
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "company", targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "company_employees", nullable = true)
	private Collection<User> users;
	
	/**
	 * Default no-argument constructor
	 */
	public Company() {
		
	}

	/**
	 * Constructor that accepts the Company name.
	 * @param name - the name of the company
	 */
	public Company(String name) {
		this();
		this.name = name;
	}

	public int getId() {
		return id;
	}
    
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public List<User> getUsers() {
		return (List<User>) users;
	}
	@JsonIgnore
	@XmlTransient
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * This wrapper class allows us to add a
	 * user and to assign the company to that user
	 * in the same method. It also returns a Boolean
	 * so that we can add messages based on a successful
	 * add.
	 * 
	 * @param user - the user to add to the database
	 * @return true - there was a successful addition
	 */
	public Boolean addUser(User user) {
		user.setCompany(this);
		UserDAO.addUser(user);
		return true;
	}
	
	/**
	 * This wrapper class allows us to send messages
	 * based on the removal of a user from the database
	 * @param user - the user to remove from the database
	 * @return true - the removal was successful
	 */
	public Boolean removeUser(User user) {
		UserDAO.removeUser(user);
		return true;
	}
	
	/**
	 * This wrapper class allows us to return a set number of
	 * users from the database. That way the size of the list doesn't
	 * overwhelm the screen
	 * @param page - the starting place
	 * @param perPage - the last place to show on the same page
	 * @return the list of users between page and perPage
	 */
	public List<User> getAllUsers(int page, int perPage) {
		List <User> userList = UserDAO.getAllUsersForCompany(this, page, perPage);
		setUsers(userList);
		return userList;
	}
	
	/**
	 * Wrapper class for the database access class
	 * @param idNumber - the id number of the user
	 * @return the desired user
	 */
	public User findUserByIdNumber(int idNumber) {
		return UserDAO.findUserById(idNumber);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
