package eventplannerREST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.owlike.genson.Genson;

import eventplannerDAO.CompanyDAO;
import eventplannerDAO.EM;
import eventplannerDAO.UserDAO;
import eventplannerPD.Company;
import eventplannerPD.User;
import eventplannerPD.enums.EmployeeRole;
import eventplannerPD.security.RoleAssignment;
import eventplannerPD.security.System;
import eventplannerREST.security.Secured;
import eventplannerUT.Log;
import eventplannerUT.Message;

@Path("/userservice")
public class UserService {
	
	/**
	 *  The list of messages to deliver to the user.
	 */
	ArrayList<Message> messages = new ArrayList<Message>();
	
	/**
	 * The company object that holds a list of users.
	 */
	Company company = (Company)(CompanyDAO.listCompany().get(0));
	
	@Context
	SecurityContext securityContext;
	
	/**
	 * logger
	 */
	Log log = new Log();
	
	//@Secured({EmployeeRole.Administrator})
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(
			@DefaultValue("0") @QueryParam("page") String page,
			@DefaultValue("10") @QueryParam("per_page") String perPage){
		EM.getEntityManager().refresh(company);
		List<User> users = company.getAllUsers(Integer.parseInt(page), Integer.parseInt(perPage));
		Genson gen = new Genson();
		for(User user:users){
			String txt = gen.serialize(user);
			java.lang.System.out.println(txt);
		}
		log.log(users.toString());
		log.logJAXB();
		return users;
	}
	//@Secured({EmployeeRole.Administrator})
	@GET
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") String id){
		User user = company.findUserByIdNumber(Integer.parseInt(id));
		EM.getEntityManager().refresh(user);
		return user;
	}
	
	//@Secured({EmployeeRole.Administrator})
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addUser(User user, @Context final HttpServletResponse response)throws IOException{
		if(user.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
				messages.add(new Message("rest002", "Fail Operation", "Add"));
				return messages;
			
		}else{
			
			List<Message> errMessages = user.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
					
				return errMessages;
			}
			
			EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
			UserTransaction.begin();
			Boolean result = company.addUser(user);
			if (user.getRoleAssignments() != null) {
				for (RoleAssignment ra : user.getRoleAssignments()) {
					user.addRoleAssignment(ra);
				}
			}
			UserTransaction.commit();
			if(result) {
				messages.add(new Message("rest001", "Success Operation", "Add"));
				return messages;
			}
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try {
				response.flushBuffer();
			} catch (Exception e) {}
			messages.add(new Message("rest002", "Fail Operation", ""));	
			
			return messages;
		}		
	}
	
	//@Secured({EmployeeRole.Administrator})
	@PUT
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> updateUser(User user, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		User userToUpdate = UserDAO.findUserById(Integer.parseInt (id));
		if(userToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Update"));
			return messages;
		} else {
			List<Message> errMessages = user.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
		}
		EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
		UserTransaction.begin();
		Boolean result = userToUpdate.update(user);
		UserTransaction.commit();	
		if (result) {
			messages.add(new Message("rest001", "Success Operation", "Update"));
			return messages;
		} else {
			try {
			    response.flushBuffer();
			}catch (Exception e) {}	 
		messages.add(new Message("rest002", "Fail Operation", "Update"));
		return messages;
		}	
	}
	
	//@Secured({EmployeeRole.Administrator})
	@DELETE
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deleteUser(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		User userToDelete = UserDAO.findUserById(Integer.parseInt(id));
		if(userToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}else{
			EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
			UserTransaction.begin();
			if (userToDelete.getRoleAssignments() != null) {
				for (RoleAssignment ra : userToDelete.getRoleAssignments()) {
					userToDelete.addRoleAssignment(ra);
				}
			}
			Boolean result = company.removeUser(userToDelete);
			UserTransaction.commit();
			if(result) {
				messages.add(new Message("rest001", "Success Operation", "Delete"));
				return messages;
			} else {
				messages.add(new Message("rest002", "Fail Operation", "Delete"));
				return messages;
			}
		}
	}
	
	/**
	 * Returns the currently logged in user.
	 * @return logged in user
	 */
	@Secured
	@GET
	@Path("/users/current")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser() {
		String username = securityContext.getUserPrincipal().getName();
		User user = System.findUserByUserName(username);
		return user;
	}
	
	@OPTIONS
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add a user'}} {'GET' : {'description' : 'get a user'}}}");
	}
}
