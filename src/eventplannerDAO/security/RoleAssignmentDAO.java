package eventplannerDAO.security;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eventplannerDAO.EM;
import eventplannerPD.security.RoleAssignment;


public class RoleAssignmentDAO {
	public static void saveRoleAssignment(RoleAssignment roleAssignment) {
	    EM.getEntityManager().persist(roleAssignment);
	  }
	  public static void addRoleAssignment(RoleAssignment roleAssignment) {
	    EM.getEntityManager().persist(roleAssignment);
	  }
	  public static List<RoleAssignment> listRoleAssignment()
	  {
	    TypedQuery<RoleAssignment> query = EM.getEntityManager().createQuery("SELECT roleassignment FROM roleassignment roleassignment", RoleAssignment.class);
	    return query.getResultList();
	  }
	  
	  public static List<RoleAssignment> getAllRoleAssignments()
	  {
	    TypedQuery<RoleAssignment> query = EM.getEntityManager().createQuery("SELECT roleassignment FROM roleassignment roleassignment", RoleAssignment.class);
	    return query.getResultList();
	  }
	  public static RoleAssignment findRoleAssignmentById(int id)
	  {
	    RoleAssignment roleAssignment = EM.getEntityManager().find(RoleAssignment.class, new Integer(id));
	    return roleAssignment;
	  }
	  
	  public static List<RoleAssignment> findRoleAssignmentsByUserId(int id)
	  {
		TypedQuery<RoleAssignment> query = EM.getEntityManager().createQuery("SELECT roleassignment FROM roleassignment roleassignment WHERE roleassignment.user.id="+id, RoleAssignment.class);
	    return query.getResultList();
	  }
	  
	  public static RoleAssignment findRoleAssignmentByRoleAssignmentName(String roleAssignmentname)
	  {
	    String qString = "SELECT roleassignment FROM roleassignment roleassignment  WHERE roleassignment.roleAssignmentname ="+roleAssignmentname;
	    Query query = EM.getEntityManager().createQuery(qString);
	    RoleAssignment roleAssignment = (RoleAssignment)query.getSingleResult();
	    return roleAssignment;
	  }

	  public static List<RoleAssignment> getAllRoleAssignments(int page, int pageSize)
	  {
	    TypedQuery<RoleAssignment> query = EM.getEntityManager().createQuery("SELECT roleassignment FROM roleassignment roleassignment", RoleAssignment.class);
	    return query.setFirstResult(page * pageSize)
	            .setMaxResults(pageSize)
	            .getResultList();

	  }
	  
	  public static void removeRoleAssignment(RoleAssignment roleAssignment)
	  {
	    EM.getEntityManager().remove(roleAssignment);
	  }
}
