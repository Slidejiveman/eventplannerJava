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
		Event e = (Event) events.toArray()[0];	
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
