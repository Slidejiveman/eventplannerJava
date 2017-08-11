package eventplannerREST;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
import eventplannerDAO.TableDAO;
import eventplannerPD.Event;
import eventplannerPD.EventTable;
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
	 * @return The HTTP response if it succeeds
	 */
	@GET
	@Path("/events/{id}/tablemarkers")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//@Produces({"application/pdf"})
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
			addTableMarkersContent(document, guestsForEvent);
			document.close();
		} catch (FileNotFoundException e) {
			System.err.println("I AM ERROR: File not found.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.err.println("I AM ERROR: Something went wrong with the document.");
			e.printStackTrace();
		}
		
		// Send the created file to the client
		// .txt test path => "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".txt"
		File file = new File(fileString);
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"")
				.build();
	}

	/**
	 * Writes all table markers out all of the guests invited to
	 * a particular event.
	 * 
	 * @param document - the content that will be writtent to the PDF
	 * @param guests - the guests for the event
	 */
	private void addTableMarkersContent(Document document, List<Guest> guests) {
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
	
	/**
	 * Creates a table marker for a single guest for any event. 
	 * Only the guest id is needed.
	 * 
	 * @param id - the unique identifier for a guest in the database
	 * @return A response as to whether or not 
	 */
	@GET
	@Path("/tablemarkers/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//@Produces({"application/pdf"})
	public Response getTableMarkerForGuest(@PathParam("id") String id) {
		// Get the Event Object where this matters.
		Guest guest = GuestDAO.findGuestById(Integer.parseInt(id));
		String fileString = "C:\\Users\\rdnot\\Desktop\\Reports\\" + guest.getName() + "tm.pdf";
		
		// Create the PDF Document		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileString));
			document.open();
			addTableMarkerContent(document, guest);
			document.close();
		} catch (FileNotFoundException e) {
			System.err.println("I AM ERROR: File not found.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.err.println("I AM ERROR: Something went wrong with the document.");
			e.printStackTrace();
		}
		
		// Send the created file to the client
		// .txt test path => "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".txt"
		File file = new File(fileString);
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"")
				.build();
	}
	
	/**
	 * Creates a table marker for a single guest
	 * @param document - the document contents
	 * @param g - the guest in question
	 */
	private void addTableMarkerContent(Document document, Guest g) {			
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
	
	/**
	 * Rest service that will return all table markers for an event
	 * This service works, so it will be used as a pattern for the others.
	 * This service relies on a file being existing in Tomcat's file system
	 * before it can actually send it.
	 * 
	 * @return The HTTP response if it succeeds
	 */
	@GET
	@Path("/events/{id}/tablenumberseatingreport")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//@Produces({"application/pdf"})
	public Response getSeatingReportByTableNumber(@PathParam("id") String id) {
		// Get the Event Object where this matters.
		// And get the guests so you can display their table numbers
		Event event = EventDAO.findEventById(Integer.parseInt(id));
		List<EventTable> tablesForEvent = TableDAO.findTablesByEvent(event.getId());
		String fileString = "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + "seatingreporttn.pdf";
		
		// Create the PDF Document		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileString));
			document.open();
			createSeatingReportByTableNumber(document, tablesForEvent);
			document.close();
		} catch (FileNotFoundException e) {
			System.err.println("I AM ERROR: File not found.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.err.println("I AM ERROR: Something went wrong with the document.");
			e.printStackTrace();
		}
		
		// Send the created file to the client
		// .txt test path => "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".txt"
		File file = new File(fileString);
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"")
				.build();
	}

	/**
	 * This method creates the seating report content, grouped by table number.
	 * @param document - the content to be printed to PDF
	 * @param guests - the guests for the event which have been assigned tables
	 */
	private void createSeatingReportByTableNumber(Document document, List<EventTable> tables) {
		// Loop through tables and print out guest information
		// These are ordered by table number by nature of the looping structure
		for (EventTable t : tables) {			
			for (Guest g : t.getGuests()) {
				try {
					System.out.println("Table No." + t.getNumber() + " " + g.getName());
					Paragraph para = new Paragraph("Table No. " + t.getNumber() + " " + g.getName() + "\n");
					para.setAlignment(Element.ALIGN_CENTER);
					document.add(para);				
				} catch (DocumentException e) {
					System.err.println("I AM ERROR: Something went wrong with the document.");
					e.printStackTrace();
				}
			}
		}		
	}
	
	@GET
	@Path("/events/{id}/alphabeticalseatingreport")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//@Produces({"application/pdf"})
	public Response getSeatingReportAlphabetically(@PathParam("id") String id) {
		// Get the Event Object where this matters.
		// And get the guests so you can display their table numbers
		Event event = EventDAO.findEventById(Integer.parseInt(id));
		List<Guest> guestsForEvent = GuestDAO.listGuestsByGuestList(event.getId());
		String fileString = "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + "seatingreportalpha.pdf";
		
		// Create the PDF Document		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileString));
			document.open();
			createSeatingReportAlphabetically(document, guestsForEvent);
			document.close();
		} catch (FileNotFoundException e) {
			System.err.println("I AM ERROR: File not found.");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.err.println("I AM ERROR: Something went wrong with the document.");
			e.printStackTrace();
		}
		
		// Send the created file to the client
		// .txt test path => "C:\\Users\\rdnot\\Desktop\\Reports\\" + event.getName() + event.getId() + ".txt"
		File file = new File(fileString);
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"")
				.build();
	}

	/**
	 * Writes a seating report, ordering guests alphabetically by last name.
	 * In order to do this, the guest.name property must be spit on " ".
	 * @see eventplannerPD.Guest
	 * @param document - holds the content to be written to PDF
	 * @param guestsForEvent - the guests who are on the seating assignment
	 */
	private void createSeatingReportAlphabetically(Document document, List<Guest> guests) {
		// Loop through Guests and print out guest information
		SortedSet<Guest> sortedGuests = new TreeSet<Guest>();
		sortedGuests.addAll(guests); // using a sorted set causes it to call my custome comparator for Guests
		for (Guest g : sortedGuests) {
			if (g.getTable() != null) {
				try {
					Paragraph para = new Paragraph();
					para.setAlignment(Element.ALIGN_CENTER);
					System.out.println(g.getName() + " Table No. " + g.getTable().getNumber() + "\n");
					para.add(g.getName() + " Table No. " + g.getTable().getNumber() + "\n");				
					document.add(para);
				} catch (DocumentException e) {
					System.err.println("I AM ERROR: Something went wrong with the document.");
					e.printStackTrace();
				}
			} else {	
				try {	
				    Paragraph para = new Paragraph();
					para.setAlignment(Element.ALIGN_CENTER);
					para.add(g.getName() + " has not been assigned a table.");
					System.out.println(g.getName() + " has not been assigned a table.");				
					document.add(para);
				} catch (DocumentException e) {
					System.err.println("I AM ERROR: Something went wrong with the document.");
					e.printStackTrace();
				}
			}
		}		
	}
}
