package eventplannerPD;

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
public class User {

    /**
     * A boolean used to determine if this user is an administrator, 
     * meaning this user has the ability to add/update/delete user data in the system.
     */
    private boolean isAdmin = false;
    /**
     * The name of the user held in the system.
     */
    private String name;
    /**
     * The username of the user. This is used to identify a user in the system.
     */
    private String username;
    /**
     * The private access phrase or word used by the user during login. 
     * If the password provided matches the password held in the system, 
     * then the user is granted access. This assumes the user has also 
     * provided the correct username.
     */
    private String password;
    /**
     * The unique identifier of a user. This ensures each user has 
     * its own unique entry in the database table of users.
     */
    private Integer id;
    /**
     * Represents whether the user is an active employee.
     */
    private boolean isActive = true;
    /**
     * The employee's system permission level.
     */
    private EmployeeRole employeeRole;
    /**
     * The authentication token associated with the actively logged in user.
     */
    private String token;

    public boolean isIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getToken() {
        return this.token;
    }
    
    public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}
    /**
     * The default constructor for a user. 
     * This is required for the JPA database to provide persistence.
     */
    public User() {
        // TODO - implement User.User
        throw new UnsupportedOperationException();
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