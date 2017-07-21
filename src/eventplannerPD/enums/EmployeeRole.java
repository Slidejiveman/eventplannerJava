package eventplannerPD.enums;

/**
 * Enumeration that represents whether a User is an Administrator or a Standard Event Planner. This enumeration is redundant because there is also a boolean value that can be used to determine whether the User is an administrator or not. It does serve a purpose however. Using this Enumeration, custom JAX-RS annotations can be made. This will be helpful when using REST services.
 */
public enum EmployeeRole {
    /**
     * A role assigned to a standard employee. Standard employees cannot add, update, or delete users.
     */
    Standard,
    /**
     * Role given to an administrator of the system. Administrators can add, update, and delete users--even other administrators.
     */
    Administrator
}