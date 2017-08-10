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

public class AlgorithmTester {

	public static void main(String[] args) {
		System.out.println("Testing The Algorithm\n\n");
		
		List<Event>events = EventDAO.listEvents();
		Event e = (Event) events.toArray()[1];
		/*List<GuestList> guestlists = GuestListDAO.listGuestLists();
		//For each event, produce a seating plan
		//set guest list for the event.
		for(GuestList guestlist: guestlists){
			if (guestlist.getEvent().equals(e)){
				e.setGuestList(guestlist);
			}
		}
		for(Guest guest: e.getGuestList().getGuests()){
			guest.setGuestsToAvoidList(GuestDAO.listGuestsToAvoid(guest.getId()));
			guest.setGuestsToSitWithList(GuestDAO.listGuestsToSitWith(guest.getId()));
		}
		*/	
		System.out.println("Seating plan for event, "+e.getName());
		//Generate Seating Arrangement
		SeatingArrangement seats = GeneticSeatArranger.generateSeatingArrangement(e);
		System.out.println("Selected Plan");
		for(Entry<Guest,EventTable> seat: seats.getSeatingAssignments().entrySet()){
			System.out.println(seat.getKey().getName()+" seats on table "+seat.getValue().getNumber());
		}	
		System.out.println("Fitness score: "+seats.getArrangementScore());
	}
}
