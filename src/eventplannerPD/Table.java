package eventplannerPD;

import java.util.Collection;

import eventplannerPD.enums.TableShape;
import eventplannerPD.enums.TableSize;

/**
 * The table class represents the table at which guests sit during an event.
 */
public class Table {

    /**
     * The size of the table measured in number of seats.
     */
    private TableSize size;
    /**
     * The shape of the table, which is some version of elliptical or rectangular.
     */
    private TableShape shape;
    /**
     * The number of the table at the event. 
     * This is a unique identifier per event, meaning that each event has only one table two (for example).
     */
    private int number;
    /**
     * The guests collection represents the group of guests sitting at a table. 
     * The number of empty seats can be determined by the difference in the size 
     * of the guest collection and the table size.
     */
    private Collection<Guest> guests;
    /**
     * The event the table is associated with.
     */
    private Event event;

    public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public TableSize getSize() {
        return this.size;
    }

    public void setSize(TableSize size) {
        this.size = size;
    }

    public TableShape getShape() {
        return this.shape;
    }

    public void setShape(TableShape shape) {
        this.shape = shape;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Collection<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Collection<Guest> guests) {
        this.guests = guests;
    }

    /**
     * The default "no-argument" constructor for a table.
     */
    public Table() {
        // If we end up persisting this, then we'll need this constructor
    }

    /**
     * The constructor for a Table that accepts a table size.
     * @param ts The size of the table to be created.
     */
    public Table(TableSize ts) {
        size = ts;
    }
    
    /**
     * Constructor that accepts the associated event and the size
     * @param evt the event associated with this table
     * @param ts the size of the tables used at this event
     */
    public Table(Event evt, TableSize ts) {
    	event = evt;
    	size = ts;
    }

}