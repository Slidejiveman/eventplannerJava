package eventplannerPD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "guesttoavoid")
@Entity(name = "guesttoavoid")
//@IdClass(GuestGuestAvoidBridgeId.class)
public class GuestGuestAvoidBridge implements Serializable {

	/**
	 * for persistence
	 */
	private static final long serialVersionUID = 7270632053724473363L;

	@Id
	@Column(name = "guesttoavoid_table_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guestAvoidBridgeId;
	
	//@Id
	@Column(name = "guest_id")
	private int guestId;
	
	//@Id
	@Column(name = "guest_avoid_id")
	private int guestAvoidId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_id", referencedColumnName = "guest_id")
	private Guest guest;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_avoid_id", referencedColumnName = "guest_id")
	private Guest guestToAvoid;

    public GuestGuestAvoidBridge() {
		
	}
	
	public int getTableId() {
		return guestAvoidBridgeId;
	}
    @XmlElement
	public void setTableId(int tableId) {
		this.guestAvoidBridgeId = tableId;
	}

	public int getGuestId() {
		return guestId;
	}
	@XmlElement
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public int getGuestAvoidId() {
		return guestAvoidId;
	}
	@XmlElement
	public void setGuestAvoidId(int guestAvoidId) {
		this.guestAvoidId = guestAvoidId;
	}

	public Guest getGuest() {
		return guest;
	}
	@XmlElement
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Guest getGuestToAvoid() {
		return guestToAvoid;
	}
	@XmlElement
	public void setGuestToAvoid(Guest guestToAvoid) {
		this.guestToAvoid = guestToAvoid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
