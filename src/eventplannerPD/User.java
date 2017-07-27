package eventplannerPD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eventplannerPD.enums.EmployeeRole;

/**
 * The user class represents an employee of 
 * Eagle Event Planning with a need to use this system.
 * 
 * There are two levels of users: administrator and non-administrator. 
 * A boolean value is used to determine whether or not the user has 
 * administrator privileges. The only difference between the two is the 
 * ability to add, update, and delete user information in the system.
 */
@XmlRootElement(name = "user")
@Entity(name = "user")
public class User implements Serializable {

	
    /**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = 7349073660808331450L;
    /**
     * The name of the user held in the system.
     */
	@Column(name = "user_name", nullable = false)
    private String name;
    /**
     * The username of the user. This is used to identify a user in the system.
     */
	@Column(name = "user_username", nullable = false)
    private String username;
    /**
     * The private access phrase or word used by the user during login. 
     * If the password provided matches the password held in the system, 
     * then the user is granted access. This assumes the user has also 
     * provided the correct username.
     */
	@Column(name = "user_password", nullable = false)
    private String password;
    /**
     * The unique identifier of a user. This ensures each user has 
     * its own unique entry in the database table of users.
     */
	@Id
	@Column(name = "user_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Represents whether the user is an active employee.
     */
	@Column(name = "user_is_active", nullable = false)
    private boolean isActive = true;
    /**
     * The employee's system permission level.
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
    private EmployeeRole userRole;
    
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_company", nullable = false, referencedColumnName = "company_id")
	private Company company;
	/**
     * The authentication token associated with the actively logged in user.
     */
	@Transient
    private transient String token;

    public String getName() {
        return this.name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

    @XmlElement
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
		this.token = token;
	}

	public EmployeeRole getEmployeeRole() {
		return userRole;
	}

    @XmlElement
	public void setEmployeeRole(EmployeeRole employeeRole) {
		userRole = employeeRole;
	}
    
    public Company getCompany() {
		return company;
	}

    @XmlElement
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
     * The default constructor for a user. 
     * This is required for the JPA database to provide persistence.
     */
    public User() {
        
    }

    /**
     * Determines whether the user may be deleted. 
     * A user may be deleted if they are not associated 
     * with any events and their status is inactive.
     */
    public boolean isOkToDelete() {
        // TODO - implement User.isOkToDelete
        throw new UnsupportedOperationException();
    }

    /**
     * Assigns a token to the user. 
     * This provides security to the system when using REST services. 
     * Only authenticated users are able to log in.
     * @param s The long string (authorization token) to give to the user. 
     *          This is how a browser will know that the user is allowed to view content.
     * @return True: User is authenticated.
     *         False: User is not authenticated.
     */
    public boolean authenticate(String s) {
        // TODO - implement User.authenticate
        throw new UnsupportedOperationException();
    }

    /**
     * Determines whether an employee is of the appropriate role to see the User List screens. 
     * This will be implemented primarily with JAX-RS annotations.
     * @return True: User is allowed to view the User List.
     *         False: User is not allowed to view the User List.
     */
    public boolean isAuthorized() {
        // TODO - implement User.isAuthorized
        throw new UnsupportedOperationException();
    }

}