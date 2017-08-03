package eventplannerUT;

import java.util.ArrayList;
import java.util.Map.Entry;

import eventplannerPD.Event;
import eventplannerPD.EventTable;
import eventplannerPD.GeneticSeatArranger;
import eventplannerPD.Guest;
import eventplannerPD.GuestList;
import eventplannerPD.SeatingArrangement;

public class AlgorithmTester {

	public static void main(String[] args) {
		System.out.println("Testing The Algorithm");
		Event e = new Event();
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
		
		//Generate Seating Arrangement
		SeatingArrangement seats = GeneticSeatArranger.generateSeatingArrangement(e);
		System.out.println("Seats generated");
		for(Entry<Guest,EventTable> seat: seats.getSeatingAssignments().entrySet()){
			System.out.println(seat.getKey().getName()+" seats on table "+seat.getValue().getNumber());
		}	

	}

}
