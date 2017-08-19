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

import com.owlike.genson.annotation.JsonIgnore;

@XmlRootElement(name = "guesttositwith")
@Entity(name = "guesttositwith")
//@IdClass(GuestGuestSitWithBridgeId.class)
public class GuestGuestSitWithBridge implements Serializable {

	/**
	 * for persistence
	 */
	private static final long serialVersionUID = 7521307151065741799L;
	
	@Id
	@Column(name = "guesttositwith_table_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int guestSitWithBridgeId;
	
	//@Id
	@Column(name = "guest_id")
	private int guestId;
	
	//@Id
	@Column(name = "guest_sitwith_id")
	private int guestSitWithId;

	@JsonIgnore
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_id", referencedColumnName = "guest_id")
	private Guest guest;
	
	@JsonIgnore
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_sitwith_id", referencedColumnName = "guest_id")
	private Guest guestToSitWith;

	public GuestGuestSitWithBridge() {
		
	}
	
    public GuestGuestSitWithBridge(Guest guest, Guest guestToSitWith) {
		this.setGuest(guest);
		this.setGuestId(guest.getId());
		this.setGuestToSitWith(guestToSitWith);
		this.setGuestSitWithId(guestToSitWith.getId());
	}

	public int getTableId() {
		return guestSitWithBridgeId;
	}
	
	@XmlElement
	public void setTableId(int tableId) {
		this.guestSitWithBridgeId = tableId;
	}

	public int getGuestId() {
		return guestId;
	}
	@XmlElement
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public int getGuestSitWithId() {
		return guestSitWithId;
	}
	@XmlElement
	public void setGuestSitWithId(int guestSitWithId) {
		this.guestSitWithId = guestSitWithId;
	}
	@JsonIgnore
	public Guest getGuest() {
		return guest;
	}
	@JsonIgnore
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	@JsonIgnore
	public Guest getGuestToSitWith() {
		return guestToSitWith;
	}
	@JsonIgnore
	public void setGuestToSitWith(Guest guestToSitWith) {
		this.guestToSitWith = guestToSitWith;
	}

	@Override
	public String toString() {
		return guest.getName() + ", " + guestToSitWith.getName();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
