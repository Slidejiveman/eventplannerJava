package eventplannerPD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import eventplannerDAO.GuestDAO;
import eventplannerUT.Message;

/**
 * The class representing a Guest in the system. 
 * The guest is an individual that will attend the event.
 */
@XmlRootElement(name = "guest")
@Entity(name = "guest")
public class Guest implements Serializable, Comparable<Guest>{

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
     * 
     * Or, the descriptor holds the string representation of a number
     * read in from the file.
     */
	@Column(name = "guest_relationship_descriptor", nullable = true)
    private String relationshipDescriptor;
    /**
     * A collection of the Guests this Guest is required to sit with in the same EventTable.
     */
	@JsonIgnore
	@OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "guest_guests_to_sit_with", nullable = true, referencedColumnName = "guesttositwith_table_id")
    private Collection<GuestGuestSitWithBridge> guestsToSitWith;
    /**
     * The collection of Guests that this Guest must not sit with.
     */
	@JsonIgnore
	@OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "guest_guests_to_avoid", nullable = true, referencedColumnName = "guesttoavoid_table_id")
    private Collection<GuestGuestAvoidBridge> guestsToAvoid;

    /**
     * The EventTable the guest is sitting at.
     */
	@JsonIgnore
    @ManyToOne(optional = true)
    @JoinColumn(name = "guest_table_id", nullable = true, referencedColumnName = "table_id")
    private EventTable eventtable;
    
	/**
	 * The guest list the guest is associated with
	 */
    @JsonIgnore
    @ManyToOne(optional = true) 
    @JoinColumn(name = "guest_guestlist", nullable = true, referencedColumnName = "guestlist_id")  
    private GuestList guestlist;
    
    /**
     * A List of guests used for the seating algorithm.
     * Holds the guests another guest is supposed to sit with.
     */
    @Transient
    @JsonIgnore
    private transient List<Guest> guestsToSitWithList;
    
    /**
     * A List of guests used for the seating algorithm.
     * Holds the guests another guest is supposed to avoid sitting with.
     */
    @Transient
    @JsonIgnore
    private transient List<Guest> guestsToAvoidList;
    
    @JsonIgnore
    public List<Guest> getGuestsToSitWithList() {
		return guestsToSitWithList;
	}

    @JsonIgnore
	public void setGuestsToSitWithList(List<Guest> guestsToSitWithList) {
		this.guestsToSitWithList = guestsToSitWithList;
	}

    @JsonIgnore
	public List<Guest> getGuestsToAvoidList() {
		return guestsToAvoidList;
	}

    @JsonIgnore
	public void setGuestsToAvoidList(List<Guest> guestsToAvoidList) {
		this.guestsToAvoidList = guestsToAvoidList;
	}

	@JsonIgnore
    public GuestList getGuestlist() {
		return guestlist;
	}
    
    @JsonIgnore
    @XmlElement
	public void setGuestlist(GuestList guestlist) {
		this.guestlist = guestlist;
	}

	public int getId() {
        return this.id;
    }
	@XmlElement
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
    @JsonIgnore
    public Collection<GuestGuestSitWithBridge> getGuestsToSitWith() {
        return this.guestsToSitWith;
    }
    @JsonIgnore
    @XmlTransient
    public void setGuestsToSitWith(Collection<GuestGuestSitWithBridge> guestsToSitWith) {
        this.guestsToSitWith = guestsToSitWith;
    }
    @JsonIgnore
    public Collection<GuestGuestAvoidBridge> getGuestsToAvoid() {
        return this.guestsToAvoid;
    }
    @JsonIgnore
    @XmlTransient
    public void setGuestsToAvoid(Collection<GuestGuestAvoidBridge> guestsToAvoid) {
        this.guestsToAvoid = guestsToAvoid;
    }
    @JsonIgnore
    public EventTable getTable() {
		return eventtable;
	}
    @JsonIgnore
    @XmlElement
	public void setTable(EventTable EventTable) {
		this.eventtable = EventTable;
	}
    
    /**
     * Gets the guests to avoid out of the database so that JSON can be generated
     * with this information in it.
     * @return The guests to avoid sitting with
     */
    public Collection<Guest> findGuestsToAvoid() {
    	return GuestDAO.listGuestsToAvoid(this.getId());
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
       return true;
    }

    /**
     * The validation method creates JSON messages
     * that are returned to the screen if an error occurs
     * @return the error messages
     */
    public List<Message> validate() {
    	List<Message> messages = new ArrayList<Message>();
    	
    	if (this.getName().equals(null) || this.getName().equals("")) {
    		messages.add(new Message("Guest000", "Guest's name cannot be null or empty.", "Name"));
    	}
    	
    	return messages;
    }
    
    /**
     * The update method re-initializes a guest's values
     * with the values of another guest from the database.
     * @param guest - the guest whose values to apply to this customer
     * @return this guest updated with the given guest's values
     */
    public Boolean update(Guest guest) {
    	this.setGuestlist(guest.getGuestlist());
    	this.setGuestsToAvoid(guest.getGuestsToAvoid());
    	this.setGuestsToSitWith(guest.getGuestsToSitWith());
    	this.setName(guest.getName());
    	this.setRelationshipDescriptor(guest.getRelationshipDescriptor());
    	this.setTable(guest.getTable());
    	return true;
    }
    
    /**
     * Used to print out the EventTable Marker for this guest.
     * The toString() method is overwritten to display the name of the guest and the relationship descriptor of the guest.
     * @return The string containing the name and the relationship descriptor of the guest.
     */
    public String toString() {
        return this.getName() + " " + this.getRelationshipDescriptor();
    }
    @Override
    public int compareTo(Guest compareGuest) {
     // comparison logic goes here
    	String[] guestNames = this.getName().trim().split(" ");
    	String[] compareGuestNames = compareGuest.getName().trim().split(" ");
    	
    	// If the array contains a first and last name and neither
    	// of them are the empty string, compare the two.
    	if (guestNames.length == 2 && !guestNames[1].equals("")
    			&& !compareGuestNames[1].equals("")) {
    		// The default compareToIgnoreCase string is what we want to use
    		// we just want to use it on the last name first.
    		// In this instance, the last names are equal. So, return based on
    		// the first names.
    		if (guestNames[1].compareToIgnoreCase(compareGuestNames[1]) != 0) {
    			// Last names are different, so return based on this.
    			return guestNames[1].compareToIgnoreCase(compareGuestNames[1]);
    		} else {
    			// Last names are the same, so return based on first names
    			return guestNames[0].compareToIgnoreCase(compareGuestNames[0]);
    		}		
    	} else {
    		// At least One of the two strings did not have a space in it, 
    		// so compare based on the first value and the second value
    		 if (guestNames.length < 2 && !guestNames[1].equals("")) {
    			 return guestNames[0].compareToIgnoreCase(compareGuestNames[1]);
    		 } else if (compareGuestNames.length < 2 && !compareGuestNames[1].equals("")) {   		 
    			 return guestNames[1].compareToIgnoreCase(compareGuestNames[0]);
    		 } else {
    			 // Catches the case where neither string split
    			 return guestNames[0].compareToIgnoreCase(compareGuestNames[0]);
    		 }
    		
    	} 	
    }
    
    /**
     * Return alphabetically by last name
     * given a single string that contains both 
     * last name and first name
     */
    public static Comparator<Guest> GuestLastNameComparator = new Comparator<Guest>() {

		@Override
		public int compare(Guest guest1, Guest guest2) {
			return guest1.compareTo(guest2);
		}  	
    	
    };
}