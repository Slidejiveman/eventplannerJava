package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import eventplannerPD.enums.TableShape;
import eventplannerPD.enums.TableSize;

/**
 * The EventTable class represents the EventTable at which guests sit during an event.
 */
@XmlRootElement(name = "eventtable")
@Entity(name = "eventtable") // EventTable is a reserved word. Escape it.
public class EventTable  implements Serializable, Comparable<EventTable> {

	/**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = -5478274599311347270L;
	/**
	 * The unique identifier for a persisted EventTable.
	 */
	@Id
	@Column(name = "table_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
     * The size of the EventTable measured in number of seats.
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "table_size", nullable = true)
    private TableSize size;
    /**
     * The shape of the EventTable, which is some version of elliptical or rectangular.
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "table_shape", nullable = false)
    private TableShape shape;
    /**
     * The number of the EventTable at the event. 
     * This is a unique identifier per event, meaning that each event has only one EventTable two (for example).
     */
	@Column(name = "table_number", nullable = false)
    private int number;
    /**
     * The guests collection represents the group of guests sitting at a EventTable. 
     * The number of empty seats can be determined by the difference in the size 
     * of the guest collection and the EventTable size.
     */
	@JsonIgnore
	@OneToMany(targetEntity = Guest.class, mappedBy = "eventtable", orphanRemoval = false)
	@JoinColumn(name = "table_guests", nullable = true)
    private Collection<Guest> guests;
    /**
     * The event the EventTable is associated with.
     */
	@JsonIgnore
	@ManyToOne(optional = true)
	@JoinColumn(name = "table_event_id", nullable = true, referencedColumnName = "event_id")
    private Event event;
    /**
     * The seating arrangement the EventTable is associated with
     */
	@JsonIgnore
	@ManyToOne(optional = true)
	@JoinColumn(name = "table_seatingarrangement_id",nullable = true, referencedColumnName = "seatingarrangement_id")
    private SeatingArrangement seatingArrangement;

    public int getId() {
		return id;
	}
    @XmlElement
	public void setId(int id) {
		this.id = id;
	}
    @JsonIgnore
    public SeatingArrangement getSeatingArrangement() {
		return seatingArrangement;
	}
    @JsonIgnore
    @XmlElement
	public void setSeatingArrangement(SeatingArrangement seatingArrangement) {
		this.seatingArrangement = seatingArrangement;
	}
    @JsonIgnore
	public Event getEvent() {
		return event;
	}
    @JsonIgnore
	@XmlElement
	public void setEvent(Event event) {
		this.event = event;
	}

	public TableSize getSize() {
        return this.size;
    }

	@XmlElement
    public void setSize(TableSize size) {
        this.size = size;
    }

    public TableShape getShape() {
        return this.shape;
    }

    @XmlElement
    public void setShape(TableShape shape) {
        this.shape = shape;
    }

    public int getNumber() {
        return this.number;
    }

    @XmlElement
    public void setNumber(int number) {
        this.number = number;
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

    /**
     * The default "no-argument" constructor for a EventTable.
     */
    public EventTable() {
        // If we end up persisting this, then we'll need this constructor
    }

    /**
     * The constructor for a EventTable that accepts a EventTable size.
     * @param ts The size of the EventTable to be created.
     */
    public EventTable(TableSize ts) {
        size = ts;
    }
    
    /**
     * Constructor that accepts the associated event and the size
     * @param evt the event associated with this EventTable
     * @param ts the size of the EventTables used at this event
     */
    public EventTable(Event evt, TableSize ts) {
    	event = evt;
    	size = ts;
    }
	@Override
	public int compareTo(EventTable compareTable) {
		
		return this.getNumber() - compareTable.getNumber();
	}

}
