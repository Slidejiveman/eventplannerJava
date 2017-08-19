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

import eventplannerDAO.EM;
import eventplannerDAO.GuestDAO;
import eventplannerDAO.GuestToAvoidDAO;
import eventplannerDAO.GuestToSitWithDAO;
import eventplannerPD.Guest;
import eventplannerPD.GuestGuestAvoidBridge;
import eventplannerPD.GuestGuestSitWithBridge;
import eventplannerUT.Log;
import eventplannerUT.Message;

@Path("/guestservice")
public class GuestService {
	//The list of messages to deliver to the user.
	ArrayList<Message> messages = new ArrayList<Message>();
	//logger
	Log log = new Log();
	
	@GET
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Guest> getguests(
			@DefaultValue("0") @QueryParam("page") String page,
			@DefaultValue("10") @QueryParam("per_page") String perPage){
		List<Guest>guests=GuestDAO.listGuests();
		Genson gen = new Genson();
		String txt = "";
		for(Guest guest:guests){
			txt = gen.serialize(guest);
			System.out.println(txt);
			log.log(txt);
		}		
		log.logJAXB();
		return guests;
	}
	@GET
	@Path("/guests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Guest getguest(@PathParam("id") String id){
		Guest guest =  GuestDAO.findGuestById(Integer.parseInt(id));
		EM.getEntityManager().refresh(guest);
		return guest;
	}
	/**
	 * Fetches all of the guests associated with the same guest list in the database.
	 * This also means they are associated with the same event.
	 * 
	 * @param id - the guestlist id
	 * @return The guests on the same guest list
	 */
	@GET
	@Path("/guestsbyguestlist/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Guest> getGuestsByGuestList(@PathParam("id") String id) {
		List<Guest> guestsAtEvent = GuestDAO.listGuestsByGuestList(Integer.parseInt(id));
		return guestsAtEvent;
	}
	
	@GET
	@Path("/guests/{id}/gueststoavoid")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Guest> getGuestsToAvoidForGuestId(@PathParam("id") String id) {
		List<Guest> guestsToAvoid = GuestDAO.listGuestsToAvoid(Integer.parseInt(id));
		return guestsToAvoid;
	}
	
	@GET
	@Path("/guests/{id}/gueststositwith")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Guest> getGuestsToSitWithForGuestId(@PathParam("id") String id) {
		List<Guest> guestsToSitWith = GuestDAO.listGuestsToSitWith(Integer.parseInt(id));
		return guestsToSitWith;
	}
	
	/**
	 * This is a PUT rather than a POST because it is easiest
	 * to add a new bridge entity through a transaction like this.
	 * The bridge entities don't like to be added directly.
	 * @param guestId - the guest in question
	 * @param sitWithId - the guest to sit with
	 * @return Message indicating success or failure
	 */
	@PUT
	@Path("/guests/{guestId}/gueststositwith/{sitWithId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addGuestToSitWith(@PathParam("guestId") String guestId, @PathParam("sitWithId") String sitWithId) {
		Guest guest = GuestDAO.findGuestById(Integer.parseInt(guestId));		
		Guest sitWith = GuestDAO.findGuestById(Integer.parseInt(sitWithId));
        if (guest == null || sitWith == null) {
			messages.add(new Message("rest002", "Fail Operation", "Add"));
			return messages;
		}
		GuestGuestSitWithBridge ggswb = new GuestGuestSitWithBridge(guest, sitWith);
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		guest.getGuestsToSitWith().add(ggswb);
		guestTransaction.commit();
		messages.add(new Message("rest001", "Success Operation", "Add"));
		return messages;
	}
	
	@PUT
	@Path("/guests/{guestId}/gueststoavoid/{avoidId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addGuestToAvoid(@PathParam("guestId") String guestId, @PathParam("avoidId") String avoidId) {
		Guest guest = GuestDAO.findGuestById(Integer.parseInt(guestId));		
		Guest avoid = GuestDAO.findGuestById(Integer.parseInt(avoidId));
        if (guest == null || avoid == null) {
			messages.add(new Message("rest002", "Fail Operation", "Add"));
			return messages;
		}
		GuestGuestAvoidBridge ggab = new GuestGuestAvoidBridge(guest, avoid);
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		guest.getGuestsToAvoid().add(ggab);
		guestTransaction.commit();
		messages.add(new Message("rest001", "Success Operation", "Add"));
		return messages;
	}
	
	@PUT
	@Path("/guests/{guestId}/deletegueststositwith/{sitWithId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deleteGuestToSitWith(@PathParam("guestId") String guestId, @PathParam("sitWithId") String sitWithId) {
		GuestGuestSitWithBridge ggswb = GuestToSitWithDAO.findGuestGuestSitWithBridgeByEntities(guestId, sitWithId);
		Guest guest = GuestDAO.findGuestById(Integer.parseInt(guestId));
		if (guest == null || ggswb == null) {
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		guest.getGuestsToSitWith().remove(ggswb);
		guestTransaction.commit();
		messages.add(new Message("rest001", "Success Operation", "Delete"));
		return messages;
	}
	
	/**
	 * This is a PUT method because the best way to delete a bridge entity
	 * is to remove it in a transaction.
	 * @param guestId - the guest
	 * @param avoidId - the guest to avoid
	 * @return A success message that indicates this block of code had no errors.
	 */
	@PUT
	@Path("/guests/{guestId}/deletegueststoavoid/{avoidId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deleteGuestToAvoid(@PathParam("guestId") String guestId, @PathParam("avoidId") String avoidId) {
		GuestGuestAvoidBridge ggab = GuestToAvoidDAO.findGuestGuestAvoidBridgeByEntities(guestId, avoidId);
		Guest guest = GuestDAO.findGuestById(Integer.parseInt(guestId));
		if (guest == null || ggab == null) {
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		guest.getGuestsToAvoid().remove(ggab);
		guestTransaction.commit();
		messages.add(new Message("rest001", "Success Operation", "Delete"));
		return messages;
	}
	
	@POST
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addguest(Guest guest, @Context final HttpServletResponse response)throws IOException{
		if(guest.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Add"));
			return messages;
		}else{
			List<Message> errMessages = guest.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
			EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
			guestTransaction.begin();
			GuestDAO.addGuest(guest);
			guestTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Add"));
			return messages;
		}
	}
	@PUT
	@Path("/guests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> updateguest(Guest guest, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Guest guestToUpdate = GuestDAO.findGuestById(Integer.parseInt (id));
		if(guestToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Update"));
			return messages;
		} else {
			List<Message> errMessages = guest.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
		}
		EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
		guestTransaction.begin();
		Boolean result = guestToUpdate.update(guest);
		guestTransaction.commit();	
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
	@DELETE
	@Path("/guests/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deleteguest(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Guest guestToDelete = GuestDAO.findGuestById(Integer.parseInt(id));
		if(guestToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}else{
			EntityTransaction guestTransaction = EM.getEntityManager().getTransaction();
			guestTransaction.begin();
			GuestDAO.deleteGuest(guestToDelete);
			guestTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Delete"));
			return messages;
		}
	}
	
	
	
	@OPTIONS
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add a guest'}} {'GET' : {'description' : 'get a guest'}}}");
	}
}
