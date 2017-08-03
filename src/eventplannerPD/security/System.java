package eventplannerPD.security;

import java.util.Collection;
import java.util.List;

import eventplannerDAO.UserDAO;
import eventplannerDAO.security.TokenDAO;
import eventplannerPD.User;
import eventplannerPD.enums.EmployeeRole;

public class System {

	public static System system;
	private Collection<Token> tokens;
	private Collection<EmployeeRole> roles;
	private Collection<User> users;
	
	public static Token findToken(String token) {
		return TokenDAO.findTokenByToken(token);
	}
	
	public static User findUserById(int id) {
		User user = UserDAO.findUserById(id);
		return user;
	}
	
	public static Boolean addUser(User user) {
		UserDAO.addUser(user);
		return true;
	}
	
	public static Boolean removeUser(User user) {
	    UserDAO.removeUser(user);
	    return true;
	}
	  	  
	public static List<User> findAllUsers(int page, int pageSize) {
	  List<User> list = UserDAO.getAllUsers(page, pageSize);
	  return list;
    }
	
	public static System getSystem() {
		return system;
	}
	public static void setSystem(System system) {
		System.system = system;
	}
	public Collection<Token> getTokens() {
		return tokens;
	}
	public void setTokens(Collection<Token> tokens) {
		this.tokens = tokens;
	}
	public Collection<EmployeeRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<EmployeeRole> roles) {
		this.roles = roles;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public static User findUserByUserName(String username) {
		return UserDAO.findUserByUserName(username);
	}
}
