package eventplannerDAO;

import java.util.List;

import javax.persistence.TypedQuery;

import eventplannerPD.GuestGuestSitWithBridge;

public class GuestToSitWithDAO {
	
	public static void saveGuestGuestSitWithBridge(GuestGuestSitWithBridge guestSitWithBridge) {
		EM.getEntityManager().persist(guestSitWithBridge);
	}
	
	public static void addGuestGuestSitWithBridge(GuestGuestSitWithBridge guestSitWithBridge) {
		EM.getEntityManager().persist(guestSitWithBridge);
	}
	
	public static List<GuestGuestSitWithBridge> listGuestGuestSitWithBridge() {
		TypedQuery<GuestGuestSitWithBridge> query = EM.getEntityManager().createQuery("SELECT guestSitWithBridge FROM guestSitWithBridge guestSitWithBridge", GuestGuestSitWithBridge.class);
		return query.getResultList();
	}
	
	public static GuestGuestSitWithBridge findGuestGuestSitWithBridgeById(int id) {
		GuestGuestSitWithBridge GuestGuestSitWithBridge = EM.getEntityManager().find(GuestGuestSitWithBridge.class, new Integer(id));
		return GuestGuestSitWithBridge;
	}
	
	public static void removeGuestGuestSitWithBridge(GuestGuestSitWithBridge guestSitWithBridge) {
		EM.getEntityManager().remove(guestSitWithBridge);
	}
	
	public static void deleteGuestGuestSitWithBridge(GuestGuestSitWithBridge guestSitWithBridge) {
		EM.getEntityManager().remove(guestSitWithBridge);
	}
}
