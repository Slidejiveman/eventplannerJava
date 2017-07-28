package eventplannerREST;

import java.io.IOException;
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

import eventplannerDAO.EM;
import eventplannerDAO.GuestDAO;
import eventplannerPD.Guest;

@Path("/guestservice")
public class GuestService {
	public List<Guest> getguests(
			@DefaultValue("0") @QueryParam("page") String page,
			@DefaultValue("10") @QueryParam("per_page") String perPage){
		return GuestDAO.listGuests();
	}
	@GET
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public Guest getguest(@PathParam("id") String id){
		return GuestDAO.findGuestById(Integer.parseInt(id));
	}
	@POST
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addguest(Guest guest, @Context final HttpServletResponse response)throws IOException{
		if(guest.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
			guestTransaction.begin();
			GuestDAO.addGuest(guest);
			guestTransaction.commit();
		}
	}
	@PUT
	@Path("/guests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateguest(Guest guest, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Guest guestToUpdate = GuestDAO.findGuestById(Integer.parseInt (id));
		if(guestToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		GuestDAO.saveGuest(guest);
		guestTransaction.commit();	
	}
	@DELETE
	@Path("/guests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteguest(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Guest guestToDelete = GuestDAO.findGuestById(Integer.parseInt(id));
		if(guestToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
			guestTransaction.begin();
			GuestDAO.deleteGuest(guestToDelete);
			guestTransaction.commit();
		}
	}
	@OPTIONS
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add a guest'}} {'GET' : {'description' : 'get a guest'}}}");
	}
}
