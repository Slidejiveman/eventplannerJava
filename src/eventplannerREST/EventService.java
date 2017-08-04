package eventplannerREST;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.owlike.genson.Genson;

import eventplannerDAO.EM;
import eventplannerDAO.EventDAO;
import eventplannerDAO.TableDAO;
import eventplannerPD.Event;
import eventplannerPD.EventTable;
import eventplannerPD.GeneticSeatArranger;
import eventplannerPD.SeatingArrangement;
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
	
	@GET
	@Path("/events/{id}/tables")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventTable> getTablesForEvent(@PathParam("id") String id){
		List<EventTable> tables =  TableDAO.findTablesByEvent(Integer.parseInt(id));
		return tables;
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
	
	/**
	 * Returns a string. Used to test the service after creation
	 * @return
	 */
	@OPTIONS
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations() {
		return ("{ {'POST' : { 'description' : 'add an event'}} {'GET' : {'description' : 'get an event'}}}");
	}
	
	/**
	 * Calling this service will import the guests into the database.
	 * This method will also add a new GuestList to the database. This
	 * will create an ID that all of the guests will be associated with.
	 * 
	 * @param uploadedInputStream - the bytes that were in the file
	 * @param fileDetail - the details sent along with the body
	 * @param path - the path to write the resulting file to. (for logging)
	 * @return
	 */
	@POST
	@Path("/events/importguestlist")
	@Produces(MediaType.MULTIPART_FORM_DATA) // Not sure here?
	public Response importGuestList(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("path") String path) {
		
		// Path format //IP/Installables/uploaded
		System.out.println("path::"+path);
		System.out.println(uploadedInputStream.toString());
		System.out.println(fileDetail.toString());
		
		String uploadedFileLocation = path + fileDetail.getFileName();
		
		// save it off to prove that it worked.
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		String output = "File uploaded to : " + uploadedFileLocation;
		
		return Response.status(200).entity(output).build();
	}
	
	/**
	 *Helper method that writes the received guest list out to a file 
	 *for logging purposes
	 *
	 *@param uploadedInputStream - the bytes that were in the file
	 *@param uploadedFileLocation - the place to save the file
	 */
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This service will create the seating assignment and assign the
	 * guests from the guest list to tables. The tables will then be stored
	 * in the database and associated with the correct event.
	 * 
	 * I'll need to know more details from Augustin to know exactly what to call 
	 * and do here.
	 */
	@POST
	@Path("/events/{id}/createseatingassignment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> createSeatingAssignment(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException {
		// find the event from the database with the given ID
		// and run it through the seating algorithm
		Event event = EventDAO.findEventById(Integer.parseInt(id));
		if (event == null) {
			messages.add(new Message("rest002", "Failure operation", "Create Seating Assignment"));
			return messages;
		}
		SeatingArrangement seatingAssignment = GeneticSeatArranger.generateSeatingArrangement(event);
		//SeatingArrangementDAO.addSeatingArrangement(seatingAssignment); // can't persist until this returns something.
		
		// This will likely have the database update issue
		EntityTransaction eventTransaction = EM.getEntityManager().getTransaction();
		eventTransaction.begin();
		event.setSeatingAssigment(seatingAssignment);
		event.setTables(seatingAssignment.getTables());
		eventTransaction.commit();
		messages.add(new Message("rest001", "Success operation", "Create Seating Assignment"));
		
		return messages;
	}
	
	
}
