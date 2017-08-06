package eventplannerPD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import eventplannerPD.enums.EventStatus;
import eventplannerUT.Message;

/**
 * The Event is the primary problem domain class. 
 * It contains the necessary information needed to generate seating assignments. 
 * The event class represents the event Eagle Event Planning is hosting for the customer.
 */
@XmlRootElement(name = "event")
@Entity(name = "event")
public class Event implements Serializable {

    /**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = 2332808667862489187L;
	/**
     * The ID is the unique identifier used in the database to 
     * ensure that each of the events have their own unique row.
     */
	@Id
	@Column(name = "event_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * The guestList is a collection of guests that are attending the event.
     */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,optional=true)
	@JoinColumn(name = "event_guestlist",nullable=true)
    private GuestList guestList;
    /**
     * The seating arrangement is the assignment of guests to tables.
     */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional=true)
	@JoinColumn(name = "event_seatingarrangement",nullable=true)
    private SeatingArrangement seatingAssigment;

	/**
     * The date is the day and time the event is being held.
     */
    @Column(name = "event_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    /**
     * The location is the venue for the event. 
     * This has little impact on the system's calculations. 
     * It is up to human intuition and experience to know whether or not a 
     * venue is large enough for the guest list provided.
     */
    @Column(name = "event_location", nullable = false)
    private String location;
    /**
     * The menu represents the food that will be provided at the event in the system. 
     * This is of little consequence to the seating problem.
     */
    @Column(name = "event_menu", nullable = true)
    private String menu;
    /**
     * This attribute represents the name of the event in the system.
     */
    @Column(name = "event_name", nullable = false)
    private String name;
    /**
     * The customer that commissioned Eagle Event Planning to host this event.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_customer_id", nullable = false, referencedColumnName = "customer_id")
    private Customer customer;
    /**
     * The Eagle Event Planning employee charged with planning the event.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "event_user_id", nullable = false, referencedColumnName = "user_id")
    private User assignedUser;
    /**
     * The current status of the event, whether it is in the planning process, 
     * newly opened, canceled, or over. See the EventStatus enumeration for more details.
     */
    @Enumerated(EnumType.STRING)
	@Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;
    /**
     * The collection of tables at the event. 
     * Each EventTable knows its size and shape. 
     * Seat numbers begin from the leftmost upper corner of rectangular 
     * tables or the twelve o'clock position of elliptical tables.
     */
    @OneToMany(targetEntity = EventTable.class, mappedBy = "event", orphanRemoval = true)
    @JoinColumn(name = "event_tables", nullable = true)
    private Collection<EventTable> tables;
    /**
     * The percentage of seats allowed to be vacant at a EventTable. 
     * This value is used along with the size of the guest list to 
     * determine the number of seats that should be available at the event. 
     * This will in turn be used to determine the number of tables at the event.
     */
    @Column(name = "event_percent_seats_empty", nullable = false)
    private double percentSeatsEmpty;
    /**
     * The total seats are the number of seats at the event based on the number of tables at the event.
     */
    @Column(name = "event_total_seats", nullable = false)
    private int totalSeats;

    public int getId() {
        return this.id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public GuestList getGuestList() {
        return this.guestList;
    }

    @XmlElement
    public void setGuestList(GuestList guestList) {
        this.guestList = guestList;
    }
    
    public SeatingArrangement getSeatingAssigment() {
		return seatingAssigment;
	}

    @XmlElement
	public void setSeatingAssigment(SeatingArrangement seatingAssigment) {
		this.seatingAssigment = seatingAssigment;
	}
	
    public Date getDate() {
        return this.date;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    @XmlElement
    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu() {
        return this.menu;
    }

    @XmlElement
    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return this.name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    @XmlElement
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getAssignedUser() {
        return this.assignedUser;
    }

    @XmlElement
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public EventStatus getEventStatus() {
        return this.eventStatus;
    }

    @XmlElement
    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Collection<EventTable> getTables() {
        return this.tables;
    }
    @XmlTransient
    public void setTables(Collection<EventTable> tables) {
        this.tables = tables;
    }

    public double getPercentSeatsEmpty() {
        return this.percentSeatsEmpty;
    }

    @XmlElement
    public void setPercentSeatsEmpty(double percentSeatsEmpty) {
        this.percentSeatsEmpty = percentSeatsEmpty;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    @XmlElement
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    /**
     * Default "no-argument" constructor for the Event. 
     * This is required for JPA persistence.
     */
    public Event() {
        
    }

    /**
     * Determines whether the event can be removed from the database. 
     * An event is only fit for deletion if it's eventStatus value is equal to "Canceled".
     * @return True: The event may be deleted and removed from the database.
     *         False: The event may not be deleted and removed from the database.
     */
    public boolean isOkToDelete() {
        return true;
    }

    /**
     * Calculates the number of tables needed to accommodate the 
     * guest list and the required ratio of empty seats.
     * @param pse 
     *        The percentage of empty seats we are expecting at a EventTable. 
     *        This is used to prevent filling up a tables and leaving others completely empty.
     * @param EventTable 
     *        The EventTable is an object of a certain shape, either rectangular or elliptical, 
     *        and a size from four to twelve. The EventTable is where the guests sit. 
     *        The EventTable object is passed in for its size attribute.
     * @param gl 
     *        The guest list is passed into this method for its size attribute. 
     *        The chosen EventTable size and the size of the guest list is needed to 
     *        determine the number of tables needed.
     * @return The number of tables needed to accommodate the guest list with the required ratio of empty seats.
     */
    public int calculateNumTables(double pse, EventTable EventTable, GuestList gl) {
        return 0;
    }

    /**
     * Calculates the number of seats that are needed for the guest list 
     * and the percentage of vacancies. This is required to accommodate 
     * guests that did not RSVP or brought extra people.
     * This method will multiply the number of seats by the number of tables.
     */
    public int calculateTotalSeats() {
        return 0;
    }
    
    /**
     * The validation method creates JSON messages
     * that are returned to the screen if an error occurs
     * @return the error messages
     */
    public List<Message> validate() {
    	List<Message> messages = new ArrayList<Message>();
    	
    	if (this.getAssignedUser() == null) {
    		messages.add(new Message("Event000","Events must have an assigned User","User"));
    	}
    	if (this.getCustomer() == null) {
    		messages.add(new Message("Event001", "Events must have an assigned Customer","Customer"));
    	}
    	if (this.getDate() == null) {
    		messages.add(new Message("Event002", "The Event date cannot be null.", "Date"));
    	}
    	if (!(this.getEventStatus().equals(EventStatus.Approved) || 
    			this.getEventStatus().equals(EventStatus.Canceled) || 
    			this.getEventStatus().equals(EventStatus.Closed) || 
    			this.getEventStatus().equals(EventStatus.Open) || 
    			this.getEventStatus().equals(EventStatus.ReadyForCustomerReview))) {
    		messages.add(new Message("Event003", "The Event Status must be one of the enumerated types", "Event Status"));
    	}
    	if (this.getLocation().equals(null) || this.getLocation().equals("")) {
    		messages.add(new Message("Event004", "The Event location cannot be null", "Location"));
    	}
    	if (this.getName().equals(null) || this.getName().equals("")) {
    		messages.add(new Message("Event005", "The Event name cannot be null or empty.", "Name"));
    	}
    	
    	return messages;
    }

    /**
     * The update method re-initializes an event's values
     * with the values of another event from the database.
     * @param event - the event whose values to apply to this customer
     * @return this event updated with the given event's values
     */
    public Boolean update(Event event) {
    	this.setAssignedUser(event.getAssignedUser());
    	this.setCustomer(event.getCustomer());
    	this.setDate(event.getDate());
    	this.setEventStatus(event.getEventStatus());
    	this.setGuestList(event.getGuestList());
    	this.setLocation(event.getLocation());
    	this.setMenu(event.getMenu());
    	this.setName(event.getName());
    	this.setPercentSeatsEmpty(event.getPercentSeatsEmpty());
    	this.setSeatingAssigment(event.getSeatingAssigment());
    	this.setTables(event.getTables());
    	this.setTotalSeats(event.getTotalSeats());
    	return true;
    }
}