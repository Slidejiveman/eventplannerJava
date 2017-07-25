package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The class representing a Guest in the system. 
 * The guest is an individual that will attend the event.
 */
public class Guest implements Serializable {

    /**
	 *  Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = -2752663033462674926L;
	/**
     * The guest ID is a unique identifier that is used to ensure 
     * that the guest has a unique location in the guest list.
     */
	@Id
	@Column(name = "guest_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The name of the guest.
     */
	@Column(name = "guest_name", nullable = false)
    private String name;
    /**
     * The descriptor associated with this guest. 
     * Example descriptors include the following:
     *     Mother of the Bride
     *     Father of the Groom
     *     Husband of Gertrude
     */
	@Column(name = "guest_relationship_descriptor", nullable = true)
    private String relationshipDescriptor;
    /**
     * A collection of the Guests this Guest is required to sit with in the same table.
     */
	@OneToMany(targetEntity = Guest.class, mappedBy = "theGuest")
	@Column(name = "guest_guests_to_sit_with", nullable = true)
    private Collection<Guest> guestsToSitWith;
    /**
     * The collection of Guests that this Guest must not sit with.
     */
	@OneToMany(targetEntity = Guest.class, mappedBy = "theGuest")
	@Column(name = "guest_guests_to_avoid", nullable = true)
    private Collection<Guest> guestsToAvoid;

    /**
     * Added so the JPA works. This is a special bidirectional case.
     * The class references itself, so it must have a field to reference.
     * This field should represent the guest that the must sit with or avoid
     * the guests in these two collections.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_theGuest_id", nullable = true, referencedColumnName = "guest_id")
    private Guest theGuest;
    /**
     * The table the guest is sitting at.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "guest_table_number", nullable = true, referencedColumnName = "table_number")
    private Table table;
    
    public Guest getTheGuest() {
		return theGuest;
	}

	public void setTheGuest(Guest theGuest) {
		this.theGuest = theGuest;
	}

	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipDescriptor() {
        return this.relationshipDescriptor;
    }

    public void setRelationshipDescriptor(String relationshipDescriptor) {
        this.relationshipDescriptor = relationshipDescriptor;
    }

    public Collection<Guest> getGuestsToSitWith() {
        return this.guestsToSitWith;
    }

    public void setGuestsToSitWith(Collection<Guest> guestsToSitWith) {
        this.guestsToSitWith = guestsToSitWith;
    }

    public Collection<Guest> getGuestsToAvoid() {
        return this.guestsToAvoid;
    }

    public void setGuestsToAvoid(Collection<Guest> guestsToAvoid) {
        this.guestsToAvoid = guestsToAvoid;
    }

    public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
     * The default constructor for a guest. This is required for the JPA database.
     */
    public Guest() {
        // No argument constructor for JPA storage.
    }

    /**
     * Determines whether or not it is okay to delete the guest.
     * A guest may only be deleted if associated with an Open or Canceled event. Otherwise the event is under review or has been approved. In these circumstances, the guest list should not be changed.
     */
    public boolean isOkToDelete() {
        // TODO - implement Guest.isOkToDelete
        throw new UnsupportedOperationException();
    }

    /**
     * Used to print out the Table Marker for this guest.
     * The toString() method is overwritten to display the name of the guest and the relationship descriptor of the guest.
     * @return The string containing the name and the relationship descriptor of the guest.
     */
    public String toString() {
        // TODO - implement Guest.toString
        throw new UnsupportedOperationException();
    }

}