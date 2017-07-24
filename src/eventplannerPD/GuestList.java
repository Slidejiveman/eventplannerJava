package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The guest list is a collection of guests associated with a single event. 
 * The guest list is received from the customer as an Excel file and imported into the system.
 */
public class GuestList implements Serializable{

    /**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = -1202722124490976992L;
	/**
     * The collection of guests that make up the guest list.
     */
	@OneToMany(targetEntity = Guest.class)
	@Column(name = "guestlist_guests", nullable = true)
    private Collection<Guest> guests;
    /**
     * The unique identifier of the guest list. 
     * This allows the guest list to take up a single row in the database.
     */
    @Id
	@Column(name = "guestlist_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The size of the guest list measured in the number of guests.
     */
    @Column(name = "guestlist_size", nullable = true)
    private int size;

    public Collection<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Collection<Guest> guests) {
        this.guests = guests;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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