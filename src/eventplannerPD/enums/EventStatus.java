package eventplannerPD.enums;

/**
 * An enumeration that captures the state of the event. The valid states are Open, Ready for Customer Review, Closed, and Canceled.
 * 
 * Open: Eagle Event Planning is actively working the event.
 * Ready for Customer Review: The initial planning is complete. Waiting on Customer.
 * Approved: The customer has approved the event plan.
 * Closed: The event has been held.
 * Canceled: The event is no longer going to occur. Work has been abandoned.
 */
public enum EventStatus {
    /**
     * The event is actively be worked or reworked by Eagle Event Planning.
     */
    Open,
    /**
     * The event has been initially planned and is ready for customer review. After the customer review, the event will either go to the approved state or back to the open state for additional work.
     */
    ReadyForCustomerReview,
    /**
     * There is no more work to do and the event has been held; therefore, the event is closed.
     */
    Closed,
    /**
     * The customer has decided not to have the event; therefore, the event is closed.
     */
    Canceled,
    /**
     * The customer has approved the work; therefore, there is no more work to do. The event has not yet been held though, so it cannot go to the closed state. In this scenario, the event is in the approved state.
     */
    Approved
}