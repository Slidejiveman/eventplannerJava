package eventplannerPD.security;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eventplannerDAO.security.TokenDAO;
import eventplannerPD.User;

@XmlRootElement(name = "token")
@Entity(name = "token")
public class Token implements Serializable {

	/**
	 * Required for serialization
	 */
	private static final long serialVersionUID = 6224694521294785796L;

	@Id
	@Column(name = "token_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tokenID;
	
	@Column(name = "token", nullable = false, length = 40)
	private String token;
	
	@Column(name = "expire_date")
	private LocalDate expireDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	/**
	 * No argument constructor for JAX-B and JPA
	 */
	public Token() {}
	
	public Token (User user) {
		setUser(user);
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		setToken(token);
		setExpireDate(LocalDate.now());
	}

	public long getTokenID() {
		return tokenID;
	}

	public void setTokenID(long tokenID) {
		this.tokenID = tokenID;
	}

	public String getToken() {
		return token;
	}
    @XmlElement
	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}
    @XmlElement
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public User getUser() {
		return user;
	}
    @XmlElement
	public void setUser(User user) {
		this.user = user;
	}

    public Boolean validate() {
    	return LocalDate.now().equals(getExpireDate());
    }
    
    public void save() {
    	TokenDAO.saveToken(this);
    }
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
