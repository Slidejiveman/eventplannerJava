package eventplannerPD;

import java.util.*;

/**
 * The authorizer is a class used to determine if a login attempt is successful. 
 * Only authorized individuals as determined by username and password may use the system.
 */
public class Authorizer {

    /**
     * The string message returned if the login attempt fails.
     */
    private String invalidMsg = "Invalid login credentials. Please try again.";
    /**
     * The collection of authorized users the authorizer can use to determine if a login attempt is valid.
     */
    private Collection<User> authorizedUsers;
    /**
     * The currently logged in user if applicable. 
     * The permissions related to the application are determined by which user is the active user.
     */
    private User activeUser;

    public String getInvalidMsg() {
        return this.invalidMsg;
    }

    public void setInvalidMsg(String invalidMsg) {
        this.invalidMsg = invalidMsg;
    }

    public Collection<User> getAuthorizedUsers() {
        return this.authorizedUsers;
    }

    public void setAuthorizedUsers(Collection<User> authorizedUsers) {
        this.authorizedUsers = authorizedUsers;
    }

    public User getActiveUser() {
        return this.activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * This method determines whether the individual who attempted to login is a valid user. 
     * If so, the user gains access to the system.
     * @param user The user is passed in to compare whether or 
     *        not the user's username and password matches what was entered at the keyboard.
     * @return True: The login is successful.
     *         False: The login fails.
     */
    public boolean validateLoginCredentials(User user) {
        // TODO - implement Authorizer.validateLoginCredentials
        throw new UnsupportedOperationException();
    }

    /**
     * The default constructor for the authorizer.
     */
    public Authorizer() {
        // TODO - implement Authorizer.Authorizer
        throw new UnsupportedOperationException();
    }

}