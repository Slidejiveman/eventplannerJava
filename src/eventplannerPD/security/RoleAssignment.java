package eventplannerPD.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import eventplannerPD.User;
import eventplannerPD.enums.EmployeeRole;

@XmlRootElement(name = "roleassignment")
@Entity(name = "roleassignment")
public class RoleAssignment implements Serializable {

	/**
	 * Required for Serialization
	 */
	private static final long serialVersionUID = 2351194501567111085L;

	@Id
	@Column(name = "role_assignment_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleAssignmentID;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private EmployeeRole role;
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "roleassignment_user_id", referencedColumnName = "user_id")
	private User user;
	
	public RoleAssignment() {}
	
	public RoleAssignment(EmployeeRole role, User user) {
		setRole(role);
		setUser(user);
	}

	public long getRoleAssignmentID() {
		return roleAssignmentID;
	}
	@XmlTransient
	public void setRoleAssignmentID(long roleAssignmentID) {
		this.roleAssignmentID = roleAssignmentID;
	}

	public EmployeeRole getRole() {
		return role;
	}
	@XmlElement
	public void setRole(EmployeeRole role) {
		this.role = role;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	@JsonIgnore
	@XmlTransient
	public void setUser(User user) {
		this.user = user;
	}
}
