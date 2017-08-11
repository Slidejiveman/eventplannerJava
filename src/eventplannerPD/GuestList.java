package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

/**
 * The guest list is a collection of guests associated with a single event. 
 * The guest list is received from the customer as an Excel file and imported into the system.
 */
@XmlRootElement(name = "guestlist")
@Entity(name = "guestlist")
public class GuestList implements Serializable{

    /**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = -1202722124490976992L;
	/**
     * The collection of guests that make up the guest list.
     */
	@JsonIgnore
	@OneToMany(targetEntity = Guest.class, mappedBy = "guestlist", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@JoinColumn(name = "guestlist_guests", nullable = false)
    private Collection<Guest> guests ;
    /**
     * The unique identifier of the guest list. 
     * This allows the guest list to take up a single row in the database.
     */
    @Id
	@Column(name = "guestlist_id", updatable = false, nullable = false)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The size of the guest list measured in the number of guests.
     */
    @Transient
    @JsonIgnore
    private transient int size;
    /**
     * This is the Event that the guest list is associated with.
     */
    @JsonIgnore
    @OneToOne(mappedBy="guestList",optional=true)
    @JoinColumn(name = "guestlist_event", nullable = true)
    private Event event;
    
    @JsonIgnore
    public Event getEvent() {
		return event;
	}
    @JsonIgnore
    @XmlTransient
	public void setEvent(Event event) {
		this.event = event;
	}
    @JsonIgnore
	public Collection<Guest> getGuests() {
        return this.guests;
    }
    @JsonIgnore
	@XmlTransient
    public void setGuests(Collection<Guest> guests) {
        this.guests = guests;
    }

    public int getId() {
        return this.id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public int getSize() {
        return this.size;
    }
    @JsonIgnore
    @XmlTransient
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
        return true;
    }

}