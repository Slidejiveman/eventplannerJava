package eventplannerDAO;

import java.util.List;

import javax.persistence.TypedQuery;

import eventplannerPD.GuestGuestAvoidBridge;

public class GuestToAvoidDAO {
	public static void saveGuestGuestAvoidBridge(GuestGuestAvoidBridge guestAvoidBridge) {
		EM.getEntityManager().persist(guestAvoidBridge);
	}
	
	public static void addGuestGuestAvoidBridge(GuestGuestAvoidBridge guestAvoidBridge) {
		EM.getEntityManager().persist(guestAvoidBridge);
	}
	
	public static List<GuestGuestAvoidBridge> listGuestGuestAvoidBridge() {
		TypedQuery<GuestGuestAvoidBridge> query = EM.getEntityManager().createQuery("SELECT guestAvoidBridge FROM guestAvoidBridge guestAvoidBridge", GuestGuestAvoidBridge.class);
		return query.getResultList();
	}
	
	public static GuestGuestAvoidBridge findGuestGuestAvoidBridgeById(int id) {
		GuestGuestAvoidBridge GuestGuestAvoidBridge = EM.getEntityManager().find(GuestGuestAvoidBridge.class, new Integer(id));
		return GuestGuestAvoidBridge;
	}
	
	public static void removeGuestGuestAvoidBridge(GuestGuestAvoidBridge guestAvoidBridge) {
		EM.getEntityManager().remove(guestAvoidBridge);
	}
	
	public static void deleteGuestGuestAvoidBridge(GuestGuestAvoidBridge guestAvoidBridge) {
		EM.getEntityManager().remove(guestAvoidBridge);
	}
}
