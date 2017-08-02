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
import eventplannerDAO.EventDAO;
import eventplannerPD.Event;
import eventplannerUT.Log;
import eventplannerUT.Message;

@Path("/eventservice")
public class EventService {
	//The list of messages to deliver to the user.
	ArrayList<Message> messages = new ArrayList<Message>();
	//logger
	Log log = new Log();
	
	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getEvents(
			@DefaultValue("0") @QueryParam("page") String page,
			@DefaultValue("10") @QueryParam("per_page") String perPage){
		List<Event>events=EventDAO.listEvents();
		Genson gen = new Genson();
		gen.serialize(events);
		log.log(events.toString());
		log.logJAXB();
		return events;
	}
	@GET
	@Path("/events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event getEvent(@PathParam("id") String id){
		Event event =  EventDAO.findEventById(Integer.parseInt(id));
		EM.getEntityManager().refresh(event);
		return event;
	}
	@POST
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addEvent(Event event, @Context final HttpServletResponse response)throws IOException{
		if(event.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Add"));
			return messages;
		}else{
			List<Message> errMessages = event.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
			EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
			eventTransaction.begin();
			EventDAO.addEvent(event);
			eventTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Add"));
			return messages;
		}
	}
	@PUT
	@Path("/events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> updateEvent(Event event, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Event eventToUpdate = EventDAO.findEventById(Integer.parseInt (id));
		Genson gen = new Genson();
		gen.serialize(eventToUpdate);
		gen.serialize(event);
		if(eventToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Update"));
			return messages;
		} else {
			List<Message> errMessages = event.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
		}
		EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
		eventTransaction.begin();
<<<<<<< Upstream, based on branch 'dev' of https://github.com/Slidejiveman/eventplannerJava
		/*eventToUpdate.setName(event.getName());
		eventToUpdate.setDate(event.getDate());
		eventToUpdate.setCustomer(event.getCustomer());
		eventToUpdate.setAssignedUser(event.getAssignedUser());
		eventToUpdate.setEventStatus(event.getEventStatus());
		eventToUpdate.setLocation(event.getLocation());
		eventToUpdate.setMenu(event.getMenu());*/
		EventDAO.saveEvent(eventToUpdate);
		eventTransaction.commit();	
=======
		Boolean result = eventToUpdate.update(event);
		eventTransaction.commit();
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
>>>>>>> 7812577 I forgot to commit this. Not sure what it is. lol
	}
	@DELETE
	@Path("/events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deleteEvent(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Event eventToDelete = EventDAO.findEventById(Integer.parseInt(id));
		if(eventToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}else{
			EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
			eventTransaction.begin();
			EventDAO.deleteEvent(eventToDelete);
			eventTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Delete"));
			return messages;
		}
	}
	@OPTIONS
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add an event'}} {'GET' : {'description' : 'get an event'}}}");
	}
}
