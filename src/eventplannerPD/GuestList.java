package eventplannerPD;

import java.util.*;

/**
 * The guest list is a collection of guests associated with a single event. 
 * The guest list is received from the customer as an Excel file and imported into the system.
 */
public class GuestList {

    /**
     * The collection of guests that make up the guest list.
     */
    private Collection<Guest> guests;
    /**
     * The unique identifier of the guest list. 
     * This allows the guest list to take up a single row in the database.
     */
    private Integer id;
    /**
     * The size of the guest list measured in the number of guests.
     */
    private int size;

    public Collection<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Collection<Guest> guests) {
        this.guests = guests;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * The default constructor for a Guest.
     */
    public GuestList() {
        // No argument constructor to be used if the Guest List is persisted.
    }

    /**
     * Determines whether a Guest list can be deleted. The guest list can be deleted if its related event is in the Open or Canceled states.
     */
    public boolean isOkayToDelete() {
        // TODO - implement GuestList.isOkayToDelete
        throw new UnsupportedOperationException();
    }

}