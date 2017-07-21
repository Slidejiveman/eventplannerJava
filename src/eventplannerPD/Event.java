package eventplannerPD;

import java.util.*;

import eventplannerPD.enums.EventStatus;

/**
 * The Event is the primary problem domain class. It contains the necessary information needed to generate seating assignments. The event class represents the event Eagle Event Planning is hosting for the customer.
 */
public class Event {

    /**
     * The ID is the unique identifier used in the database to ensure that each of the events have their own unique row.
     */
    private Integer id;
    /**
     * The guestList is a collection of guests that are attending the event.
     */
    private GuestList guestList;
    /**
     * The date is the day and time the event is being held.
     */
    private GregorianCalendar date;
    /**
     * The location is the venue for the event. This has little impact on the system's calculations. It is up to human intuition and experience to know whether or not a venue is large enough for the guest list provided.
     */
    private String location;
    /**
     * The menu represents the food that will be provided at the event in the system. This is of little consequence to the seating problem.
     */
    private String menu;
    /**
     * This attribute represents the name of the event in the system.
     */
    private String name;
    /**
     * The customer that commissioned Eagle Event Planning to host this event.
     */
    private Customer customer;
    /**
     * The Eagle Event Planning employee charged with planning the event.
     */
    private User assignedUser;
    /**
     * The current status of the event, whether it is in the planning process, newly opened, canceled, or over. See the EventStatus enumeration for more details.
     */
    private EventStatus eventStatus;
    /**
     * The collection of tables at the event. Each table knows its size and shape. Seat numbers begin from the leftmost upper corner of rectangular tables or the twelve o'clock position of elliptical tables.
     */
    private Collection<Table> tables;
    /**
     * The percentage of seats allowed to be vacant at a table. This value is used along with the size of the guest list to determine the number of seats that should be available at the event. This will in turn be used to determine the number of tables at the event.
     */
    private double percentSeatsEmpty;
    /**
     * The total seats are the number of seats at the event based on the number of tables at the event.
     */
    private int totalSeats;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GuestList getGuestList() {
        return this.guestList;
    }

    public void setGuestList(GuestList guestList) {
        this.guestList = guestList;
    }

    public GregorianCalendar getDate() {
        return this.date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu() {
        return this.menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getAssignedUser() {
        return this.assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public EventStatus getEventStatus() {
        return this.eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Collection<Table> getTables() {
        return this.tables;
    }

    public void setTables(Collection<Table> tables) {
        this.tables = tables;
    }

    public double getPercentSeatsEmpty() {
        return this.percentSeatsEmpty;
    }

    public void setPercentSeatsEmpty(double percentSeatsEmpty) {
        this.percentSeatsEmpty = percentSeatsEmpty;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    /**
     * Default "no-argument" constructor for the Event. This is required for JPA persistence.
     */
    public Event() {
        // TODO - implement Event.Event
        throw new UnsupportedOperationException();
    }

    /**
     * Determines whether the event can be removed from the database. An event is only fit for deletion if it's eventStatus value is equal to "Canceled".
     * @return True: The event may be deleted and removed from the database.
     * False: The event may not be deleted and removed from the database.
     */
    public boolean isOkToDelete() {
        // TODO - implement Event.isOkToDelete
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the number of tables needed to accommodate the guest list and the required ratio of empty seats.
     * @param pse The percentage of empty seats we are expecting at a table. This is used to prevent filling up a tables and leaving others completely empty.
     * @param table The table is an object of a certain shape, either rectangular or elliptical, and a size from four to twelve. The table is where the guests sit. The table object is passed in for its size attribute.
     * @param gl The guest list is passed into this method for its size attribute. The chosen table size and the size of the guest list is needed to determine the number of tables needed.
     * @return The number of tables needed to accommodate the guest list with the required ratio of empty seats.
     */
    public int calculateNumTables(double pse, Table table, GuestList gl) {
        // TODO - implement Event.calculateNumTables
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the number of seats that are needed for the guest list and the percentage of vacancies. This is required to accommodate guests that did not RSVP or brought extra people.
     * @param pse
     * @param gl
     */
    public int calculateTotalSeats(double pse, GuestList gl) {
        // TODO - implement Event.calculateTotalSeats
        throw new UnsupportedOperationException();
    }

}