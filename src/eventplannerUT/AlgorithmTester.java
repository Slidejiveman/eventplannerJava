package eventplannerUT;

import java.util.List;
import java.util.Map.Entry;

import eventplannerDAO.EventDAO;
import eventplannerDAO.GuestDAO;
import eventplannerDAO.GuestListDAO;

import eventplannerPD.Event;
import eventplannerPD.EventTable;
import eventplannerPD.GeneticSeatArranger;
import eventplannerPD.Guest;
import eventplannerPD.GuestList;
import eventplannerPD.SeatingArrangement;
import eventplannerPD.enums.TableShape;
import eventplannerPD.enums.TableSize;

public class AlgorithmTester {

	public static void main(String[] args) {
		System.out.println("Testing The Algorithm\n\n");
		/*Event e = new Event();
		Guest guest1 = new Guest();
		guest1.setName("John");
		Guest guest2 = new Guest();
		guest2.setName("Peter");
		Guest guest3 = new Guest();
		guest3.setName("Andrew");
		Guest guest4 = new Guest();
		guest4.setName("Barthelemeo");
		Guest guest5 = new Guest();
		guest5.setName("Nicodemus");
		
		//Setting who is seating with who and who does not
		GuestGuestAvoidBridge GAVBridge = new GuestGuestAvoidBridge();
		GAVBridge.setGuest(guest5);
		guest1.setGuestsToAvoid(new ArrayList<GuestGuestAvoidBridge>());
		guest1.getGuestsToAvoid().add(GAVBridge);
		GuestGuestAvoidBridge GAVBridge2 = new GuestGuestAvoidBridge();
		GAVBridge2.setGuest(guest3);
		guest1.getGuestsToAvoid().add(GAVBridge2);
		
		GuestGuestSitWithBridge GSWBridge = new GuestGuestSitWithBridge();
		guest2.setGuestsToSitWith(new ArrayList<GuestGuestSitWithBridge>());
		GSWBridge.setGuest(guest3);
		guest2.getGuestsToSitWith().add(GSWBridge);
		GuestGuestSitWithBridge GSWBridge2 = new GuestGuestSitWithBridge();
		GSWBridge2.setGuest(guest4);
		guest2.getGuestsToSitWith().add(GSWBridge2);
			
		
		//create tables
		EventTable table1= new EventTable();
		EventTable table2= new EventTable();
		table1.setNumber(1);
		table2.setNumber(2);
		
		//add tables and guests to event, and guests to guests list
		GuestList guests = new GuestList();
		guests.setGuests(new ArrayList<Guest>());
		guests.getGuests().add(guest1);
		guests.getGuests().add(guest2);
		guests.getGuests().add(guest3);
		guests.getGuests().add(guest4);
		guests.getGuests().add(guest5);
		e.setGuestList(guests);
		e.setTables(new ArrayList<EventTable>());
		e.getTables().add(table1);
		e.getTables().add(table2);
		
		*/
		
		List<Event>events = EventDAO.listEvents();
		List<GuestList> guestlists = GuestListDAO.listGuestLists();
		//For each event, produce a seating plan
		for(Event e: events){
			//set guest list for the event.
			for(GuestList guestlist: guestlists){
				if (guestlist.getEvent().equals(e)){
					e.setGuestList(guestlist);
				}
			}
			for(Guest guest: e.getGuestList().getGuests()){
			}
			//set tables
			int numberOfTablesToUse = (e.getGuestList().getGuests().size())/8;
			for(int count=0;count<numberOfTablesToUse;count++){
				EventTable table = new EventTable();
				table.setEvent(e);
				table.setNumber(count);
				table.setShape(TableShape.Circle);
				table.setSize(TableSize.Eight);
				e.getTables().add(table);
			}
			
			System.out.println("Seating plan for event, "+e.getName());
			//Generate Seating Arrangement
			SeatingArrangement seats = GeneticSeatArranger.generateSeatingArrangement(e);
			System.out.println("Selected Plan");
			for(Entry<Guest,EventTable> seat: seats.getSeatingAssignments().entrySet()){
				System.out.println(seat.getKey().getName()+" seats on table "+seat.getValue().getNumber());
			}	
			System.out.println("Fitness score: "+seats.getArrangementScore());
			for(Entry<Guest,EventTable> seat: seats.getSeatingAssignments().entrySet()){
				System.out.println(seat.getKey().getName()+" seats on table "+seat.getValue().getNumber());
			}	
			System.out.println("Fitness score: "+seats.getArrangementScore());
		}
	}
}
