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

import com.owlike.genson.Genson;

import eventplannerDAO.CompanyDAO;
import eventplannerDAO.EM;
import eventplannerDAO.UserDAO;
import eventplannerPD.Company;
import eventplannerPD.Customer;
import eventplannerPD.User;
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
	
	/**
	 * logger
	 */
	Log log = new Log();
	
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
			System.out.println(txt);
		}
		log.log(users.toString());
		log.logJAXB();
		return users;
	}
	@GET
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") String id){
		User user = company.findUserByIdNumber(Integer.parseInt(id));
		EM.getEntityManager().refresh(user);
		return user;
	}
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User User, @Context final HttpServletResponse response)throws IOException{
		if(User.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
			UserTransaction.begin();
			UserDAO.addUser(User);
			UserTransaction.commit();
		}
	}
	@PUT
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User User, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		User UserToUpdate = UserDAO.findUserById(Integer.parseInt (id));
		if(UserToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}
		EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
		UserTransaction.begin();
		UserDAO.saveUser(User);
		UserTransaction.commit();	
	}
	@DELETE
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		User UserToDelete = UserDAO.findUserById(Integer.parseInt(id));
		if(UserToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction UserTransaction = EM.getEntityManager().getTransaction();
			UserTransaction.begin();
			UserDAO.deleteUser(UserToDelete);
			UserTransaction.commit();
		}
	}
	@OPTIONS
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add a user'}} {'GET' : {'description' : 'get a user'}}}");
	}
}
