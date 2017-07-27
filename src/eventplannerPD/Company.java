package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The company class is a container class for the others. It can be refreshed
 * so that all of its fields will also be refreshed.
 * There is no way to set the company from the UI at present. The company will
 * always be Eagle Event Planning by default.
 * @author rdnot
 *
 */
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
	@OneToMany(mappedBy = "company", targetEntity = User.class, fetch = FetchType.EAGER)
	@Column(name = "company_employees", nullable = true)
	private Collection<User> users;
	
	/**
	 * Default no-argument constructor
	 */
	public Company() {};

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

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
