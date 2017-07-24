package eventplannerPD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Customer object represents the customer who commissioned Eagle Event Planning to host the event.
 */
@Entity(name = "customer")
public class Customer {

	/**
	 * Allows Serialization so that the item may be stored in the
	 * database
	 */
	private static final long serialVersionUID = 2312995381938814489L;
    /**
     * The name of the customer.
     */
	@Column(name = "customer_name", nullable = false)
    private String name;
    /**
     * The email of the customer is needed for confirmation emails.
     */
	@Column(name = "customer_email", nullable = false)
    private String email;
    /**
     * The phone number of the customer, which is necessary to contact the customer when necessary.
     */
	@Column(name = "customer_phone", nullable = true)
    private String phoneNumber;
    /**
     * The ID number is used to uniquely identify customers in the database.
     */
	@Id // signifies primary key
	@Column(name = "customer_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * The default constructor for a customer. 
     * This is required for JPA persistence.
     */
    public Customer() {
        // No argument constructor for JPA persistence
    }

    /**
     * This method is used to determine whether it is okay to delete a customer or not. 
     * The customer may be deleted if they are not associated with any event in the system.
     * @return True: The customer may be deleted.
     *         False: The customer may not be deleted.
     */
    public boolean isOkToDelete() {
        // TODO - implement Customer.isOkToDelete
        throw new UnsupportedOperationException();
    }

}