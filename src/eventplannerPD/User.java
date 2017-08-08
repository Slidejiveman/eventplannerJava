package eventplannerPD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import eventplannerDAO.security.RoleAssignmentDAO;
import eventplannerPD.enums.EmployeeRole;
import eventplannerPD.security.RoleAssignment;
import eventplannerPD.security.Token;
import eventplannerUT.Message;

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
    
	@OneToMany(mappedBy = "user", targetEntity = RoleAssignment.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_role_assignment_id", referencedColumnName = "role_assignment_id")
    private Collection<RoleAssignment> roleAssignments;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_company", nullable = false, referencedColumnName = "company_id")
	private Company company;
	/**
     * The authentication token associated with the actively logged in user.
     */
	@JsonIgnore
	@OneToMany(mappedBy = "user", targetEntity = Token.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_token_id", referencedColumnName = "token_id")
    private Collection<Token> tokens;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignedUser", targetEntity = Event.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_event_id", referencedColumnName = "event_id")
    private Collection<Event> events;
	
	@JsonIgnore
	@XmlTransient
    public Collection<Event> getEvents() {
		return events;
	}

	@JsonIgnore
	@XmlTransient
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

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
    @XmlElement
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
    @JsonIgnore
    @XmlTransient
	public Collection<Token> getTokens() {
		return tokens;
	}
    @JsonIgnore
	public void setTokens(Collection<Token> tokens) {
		this.tokens = tokens;
	}	
	
    public Collection<RoleAssignment> getRoleAssignments() {
		return roleAssignments;
	}
	
    @XmlElement
	public void setRoleAssignments(Collection<RoleAssignment> roleAssignments) {
		this.roleAssignments = roleAssignments;
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
        return true;
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
    public boolean authenticate(String password) {
        return getPassword().equals(password);
    }

    /**
     * Determines whether an employee is of the appropriate role to see the User List screens. 
     * This will be implemented primarily with JAX-RS annotations.
     * @return True: User is allowed to view the User List.
     *         False: User is not allowed to view the User List.
     */
    public boolean isAuthorized(EmployeeRole role) {
        for (RoleAssignment ra : getRoleAssignments()) {
        	if (ra.getRole().equals(role)) {
        		return true;
        	} 
        }
        return false;
    }
    
    /**
     * The validation method creates JSON messages
     * that are returned to the screen if an error occurs
     * @return the error messages
     */
    public List<Message> validate() {
    	List<Message> messages = new ArrayList<Message>();
    	
    	if (this.getName().equals(null) || this.getName().equals("")) {
    		messages.add(new Message("User000", "User's Name cannot be null or empty", "Name"));
    	}
    	if (this.getPassword().equals(null) || this.getPassword().equals("")) {
    		messages.add(new Message("User001", "User's Password cannot be null or empty", "Password"));
    	}
    	if (this.getUsername().equals(null) || this.getUsername().equals("")) {
    		messages.add(new Message("User002", "User's Username cannot be null or empty", "Username"));
    	}
    	
    	return messages;
    }
    
    /**
     * The update method re-initializes a user's values
     * with the values of another user from the database.
     * @param user - the user whose values to apply to this user
     * @return this user updated with the given user's values
     */
    public Boolean update(User user) {
    	this.setName(user.getName());
    	this.setCompany(user.getCompany());
    	this.setIsActive(user.isIsActive());
    	this.setPassword(user.getPassword());
    	this.setUsername(user.getUsername());
    	if (user.getRoleAssignments() != null) {
    		for (RoleAssignment ra : this.getRoleAssignments()) {
    			removeRoleAssignment(ra);
    		}
    		for (RoleAssignment ra : user.getRoleAssignments()) {
    			addRoleAssignment(ra);
    		}
    	}
    	return true;
    }
    
    public void removeRoleAssignment(RoleAssignment ra) {
        RoleAssignmentDAO.removeRoleAssignment(ra);
      }
      
      public void addRoleAssignment(RoleAssignment ra) {
        ra.setUser(this);
        RoleAssignmentDAO.saveRoleAssignment(ra);
      }
}