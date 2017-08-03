package eventplannerREST.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import eventplannerDAO.EM;
import eventplannerPD.User;
import eventplannerPD.security.System;
import eventplannerPD.security.Token;
import eventplannerUT.Message;

@Path("/systemservice")
public class SystemService {

	@Context
	SecurityContext securityContext;
	
	List<Message> messages = new ArrayList<Message>();
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(User user) {
		try {
			// Authenticate the user using the credentials provided
			authenticate(user.getUsername(), user.getPassword());
			
			// Issue a token for the user
			String token = issueToken(user.getUsername());
			
			// Return the token on the response
			return Response.ok(token).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	private void authenticate(String username, String password) throws Exception {
		// Authenticate against a database, LDAP, file, or whatever
		// Throw an Exception if the credentials are invalid
		User user = System.findUserByUserName(username);
		if(user == null) {
			throw new Exception();
		}
		if(!user.authenticate(password)) {
			throw new Exception();
		}
	}
	
	private String issueToken(String username) {
		// Issue a token (can be a random String persisted to a database eor a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		EntityTransaction userTransaction = EM.getEntityManager().getTransaction();
		userTransaction.begin();
		Token token = new Token(System.findUserByUserName(username));
		token.save();
		userTransaction.commit();
		return token.getToken();
	}
	
	
} 
