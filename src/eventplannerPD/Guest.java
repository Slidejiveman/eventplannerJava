package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class representing a Guest in the system. 
 * The guest is an individual that will attend the event.
 */
@XmlRootElement(name = "guest")
@Entity(name = "guest")
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
	@OneToMany(mappedBy = "guestToSitWith")
	@JoinColumn(name = "guest_guests_to_sit_with", nullable = true)
    private Collection<GuestGuestSitWithBridge> guestsToSitWith;
    /**
     * The collection of Guests that this Guest must not sit with.
     */
	@OneToMany(mappedBy = "guestToAvoid")
	@JoinColumn(name = "guest_guests_to_avoid", nullable = true)
    private Collection<GuestGuestAvoidBridge> guestsToAvoid;

    /**
     * The table the guest is sitting at.
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "guest_table_id", nullable = true, referencedColumnName = "table_id")
    private Table table;
    
    @ManyToOne(optional = false) 
    @JoinColumn(name = "guest_guestlist", nullable = false, referencedColumnName = "guestlist_id")  
    private GuestList guestlist;
    
    public GuestList getGuestlist() {
		return guestlist;
	}
    
    @XmlElement
	public void setGuestlist(GuestList guestlist) {
		this.guestlist = guestlist;
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

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipDescriptor() {
        return this.relationshipDescriptor;
    }

    @XmlElement
    public void setRelationshipDescriptor(String relationshipDescriptor) {
        this.relationshipDescriptor = relationshipDescriptor;
    }

    public Collection<GuestGuestSitWithBridge> getGuestsToSitWith() {
        return this.guestsToSitWith;
    }

    @XmlElement
    public void setGuestsToSitWith(Collection<GuestGuestSitWithBridge> guestsToSitWith) {
        this.guestsToSitWith = guestsToSitWith;
    }

    public Collection<GuestGuestAvoidBridge> getGuestsToAvoid() {
        return this.guestsToAvoid;
    }

    @XmlElement
    public void setGuestsToAvoid(Collection<GuestGuestAvoidBridge> guestsToAvoid) {
        this.guestsToAvoid = guestsToAvoid;
    }

    public Table getTable() {
		return table;
	}

    @XmlElement
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