package eventplannerPD;

/**
 * The Customer object represents the customer who commissioned Eagle Event Planning to host the event.
 */
public class Customer {

    /**
     * The name of the customer.
     */
    private String name;
    /**
     * The email of the customer is needed for confirmation emails.
     */
    private String email;
    /**
     * The phone number of the customer, which is necessary to contact the customer when necessary.
     */
    private String phoneNumber;
    /**
     * The ID number is used to uniquely identify customers in the database.
     */
    private Integer id;

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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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