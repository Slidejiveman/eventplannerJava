package eventplannerPD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "guesttositwith")
@IdClass(GuestGuestSitWithBridgeId.class)
public class GuestGuestSitWithBridge implements Serializable {

	/**
	 * for persistence
	 */
	private static final long serialVersionUID = 7521307151065741799L;
	
	@Id
	@Column(name = "guesttositwith_table_id")
	private int guestSitWithBridgeId;
	
	@Id
	@Column(name = "guest_id")
	private int guestId;
	
	@Id
	@Column(name = "guest_sitwith_id")
	private int guestSitWithId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_id", referencedColumnName = "guest_id")
	private Guest guest;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "guest_sitwith_id", referencedColumnName = "guest_sitwith_id")
	private Guest guestToSitWith;

	public GuestGuestSitWithBridge() {
		
	}

	public int getTableId() {
		return guestSitWithBridgeId;
	}

	public void setTableId(int tableId) {
		this.guestSitWithBridgeId = tableId;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public int getGuestSitWithId() {
		return guestSitWithId;
	}

	public void setGuestSitWithId(int guestSitWithId) {
		this.guestSitWithId = guestSitWithId;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Guest getGuestToSitWith() {
		return guestToSitWith;
	}

	public void setGuestToSitWith(Guest guestToSitWith) {
		this.guestToSitWith = guestToSitWith;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
