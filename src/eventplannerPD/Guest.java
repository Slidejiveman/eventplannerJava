package eventplannerPD;

import java.util.*;

/**
 * The class representing a Guest in the system. The guest is an individual that will attend the event.
 */
public class Guest {

    /**
     * The guest ID is a unique identifier that is used to ensure that the guest has a unique location in the guest list.
     */
    private Integer id;
    /**
     * The name of the guest.
     */
    private String name;
    /**
     * The descriptor associated with this guest. Example descriptors include the following:
     * Mother of the Bride
     * Father of the Groom
     * Husband of Gertrude
     */
    private String relationshipDescriptor;
    /**
     * A collection of the Guests this Guest is required to sit with in the same table.
     */
    private Collection<Guest> guestsToSitWith;
    /**
     * The collection of Guests that this Guest must not sit with.
     */
    private Collection<Guest> guestsToAvoid;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipDescriptor() {
        return this.relationshipDescriptor;
    }

    public void setRelationshipDescriptor(String relationshipDescriptor) {
        this.relationshipDescriptor = relationshipDescriptor;
    }

    public Collection<Guest> getGuestsToSitWith() {
        return this.guestsToSitWith;
    }

    public void setGuestsToSitWith(Collection<Guest> guestsToSitWith) {
        this.guestsToSitWith = guestsToSitWith;
    }

    public Collection<Guest> getGuestsToAvoid() {
        return this.guestsToAvoid;
    }

    public void setGuestsToAvoid(Collection<Guest> guestsToAvoid) {
        this.guestsToAvoid = guestsToAvoid;
    }

    /**
     * The default constructor for a guest. This is required for the JPA database.
     */
    public Guest() {
        // TODO - implement Guest.Guest
        throw new UnsupportedOperationException();
    }

    /**
     * Determines whether or not it is okay to delete the guest.
     * A guest may only be deleted if associated with an Open or Canceled event. Otherwise the event is under review or has been approved. In these circumstances, the guest list should not be changed.
     */
    public boolean isOkToDelete() {
        // TODO - implement Guest.isOkToDelete
        throw new UnsupportedOperationException();
    }

    /**
     * Used to print out the Table Marker for this guest.
     * The toString() method is overwritten to display the name of the guest and the relationship descriptor of the guest.
     * @return The string containing the name and the relationship descriptor of the guest.
     */
    public String toString() {
        // TODO - implement Guest.toString
        throw new UnsupportedOperationException();
    }

}