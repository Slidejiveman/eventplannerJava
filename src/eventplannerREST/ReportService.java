package eventplannerREST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import eventplannerDAO.EventDAO;
import eventplannerDAO.GuestDAO;
import eventplannerPD.Event;
import eventplannerPD.Guest;
import eventplannerUT.Log;
import eventplannerUT.Message;

@Path("/reportservice")
public class ReportService {
	//The list of messages to deliver to the user.
	List<Message> messages = new ArrayList<Message>();
	//logger
	Log log = new Log();
		
	/**
	 * Rest service that will return all table markers for an event
	 * This service works, so it will be used as a pattern for the others.
	 * This service relies on a file being existing in Tomcat's file system
	 * before it can actually send it.
	 * 
	 * @return
	 */
	@GET
	@Path("/events/{id}/tablemarkers")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getTableMarkers(@PathParam("id") String id) {
		// Get the Event Object where this matters.
		Event event = EventDAO.findEventById(Integer.parseInt(id));
		List<Guest> guestsForEvent = GuestDAO.listGuestsByGuestList(event.getId()); // event id always equals guestlist id
		String fileString = "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".pdf";
		
		// Create the PDF Document		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileString));
			document.open();
			addTableMarkerContent(document, guestsForEvent);
			document.close();
		} catch (FileNotFoundException e) {
			System.err.println("I AM ERROR: File not found.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.err.println("I AM ERROR: Something went wrong with the document.");
			e.printStackTrace();
		}
		
		// Send the created file to the client
		File file = new File("C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".txt");
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"")
				.build();
	}

	private void addTableMarkerContent(Document document, List<Guest> guests) {
		// Loop through Guests and print out guest information
		for (Guest g : guests) {			
			try {
				System.out.println(g.getName() + "\n" + g.getRelationshipDescriptor());
				Paragraph para = new Paragraph(g.getName() + "\n" + g.getRelationshipDescriptor() + "\n");
				para.setAlignment(Element.ALIGN_CENTER);
				if (g.getTable() != null) {
					para.add("Table No.: " + g.getTable().getNumber());
					System.out.println("Table No.: " + g.getTable().getNumber());
				} else {
					para.add("This guest has not been assigned a table.");
					System.out.println("This guest has not been assigned a table.");
				}
				document.add(para);
				//Post spaces for table markers
				for (int i = 0; i < 10; i++) {
					document.add(Chunk.NEWLINE);
				}				
			} catch (DocumentException e) {
				System.err.println("I AM ERROR: Something went wrong with the document.");
				e.printStackTrace();
			}			
			
		}
		
	}
	
	
}
