package eventplannerPD;

import java.io.Serializable;
import java.util.Collection;

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

import eventplannerPD.enums.TableShape;
import eventplannerPD.enums.TableSize;

/**
 * The table class represents the table at which guests sit during an event.
 */
@XmlRootElement(name = "table")
@Entity(name = "\"table\"") // Table is a reserved word. Escape it.
public class Table  implements Serializable {

	/**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = -5478274599311347270L;
	/**
	 * The unique identifier for a persisted table.
	 */
	@Id
	@Column(name = "table_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
     * The size of the table measured in number of seats.
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "table_size", nullable = false)
    private TableSize size;
    /**
     * The shape of the table, which is some version of elliptical or rectangular.
     */
	@Enumerated(EnumType.STRING)
	@Column(name = "table_shape", nullable = false)
    private TableShape shape;
    /**
     * The number of the table at the event. 
     * This is a unique identifier per event, meaning that each event has only one table two (for example).
     */
	@Column(name = "table_number", nullable = false)
    private int number;
    /**
     * The guests collection represents the group of guests sitting at a table. 
     * The number of empty seats can be determined by the difference in the size 
     * of the guest collection and the table size.
     */
	@OneToMany(targetEntity = Guest.class, mappedBy = "table")
	@JoinColumn(name = "table_guests", nullable = true)
    private Collection<Guest> guests;
    /**
     * The event the table is associated with.
     */
	@ManyToOne(optional = false)
	@JoinColumn(name = "table_event_id", nullable = false, referencedColumnName = "event_id")
    private Event event;
    /**
     * The seating arrangement the table is associated with
     */
	@ManyToOne(optional = true)
	@JoinColumn(name = "table_seatingarrangement_id", nullable = true, referencedColumnName = "seatingarrangement_id")
    private SeatingArrangement seatingArrangement;

    public int getId() {
		return id;
	}
    @XmlElement
	public void setId(int id) {
		this.id = id;
	}
   
    public SeatingArrangement getSeatingArrangement() {
		return seatingArrangement;
	}
    @XmlElement
	public void setSeatingArrangement(SeatingArrangement seatingArrangement) {
		this.seatingArrangement = seatingArrangement;
	}

	public Event getEvent() {
		return event;
	}

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

    public Collection<Guest> getGuests() {
        return this.guests;
    }
    @XmlTransient
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