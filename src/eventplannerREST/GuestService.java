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
import eventplannerPD.Guest;
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
