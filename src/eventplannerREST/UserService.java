package eventplannerREST;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class UserService {

	@OPTIONS
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations() {
		return "{ {'POST' : { 'description' : 'add a user'}} {'GET' : {'description' : 'get user(s)'}}}";
	}
}
