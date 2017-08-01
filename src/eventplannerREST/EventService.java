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
		return EventDAO.findEventById(Integer.parseInt(id));
	}
	@POST
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEvent(Event event, @Context final HttpServletResponse response)throws IOException{
		if(event.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
			eventTransaction.begin();
			EventDAO.addEvent(event);
			eventTransaction.commit();
		}
	}
	@PUT
	@Path("/events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateEvent(Event event, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Event eventToUpdate = EventDAO.findEventById(Integer.parseInt (id));
		if(eventToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}
		EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
		eventTransaction.begin();
		EventDAO.saveEvent(event);
		eventTransaction.commit();	
	}
	@DELETE
	@Path("/events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteEvent(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Event eventToDelete = EventDAO.findEventById(Integer.parseInt(id));
		if(eventToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
		}else{
			EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
			eventTransaction.begin();
			EventDAO.deleteEvent(eventToDelete);
			eventTransaction.commit();
		}
	}
	@OPTIONS
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add an event'}} {'GET' : {'description' : 'get an event'}}}");
	}
}
