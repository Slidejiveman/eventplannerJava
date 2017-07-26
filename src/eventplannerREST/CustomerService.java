package eventplannerREST;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eventplannerDAO.CustomerDAO;
import eventplannerDAO.EM;
import eventplannerPD.Customer;

/**
 * This class handles all of the RESTful services related
 * to customers. JQuery will be used to call these services
 * through the backend.
 * @author rdnot
 *
 */
@Path("/customerservices")
public class CustomerService {

	/**
	 * Returns all customers.
	 * 
	 * Currently, this does not implement the pagination feature.
	 * 
	 * @param page
	 * @param perPage
	 * @return
	 */
     @GET
     @Path("/customers")
     @Produces(MediaType.APPLICATION_JSON)
     public List<Customer> getCustomers(
	      @DefaultValue("0") @QueryParam("page") String page,
          @DefaultValue("10") @QueryParam("per_page") String perPage){
          return CustomerDAO.listCustomers();
     }
	
	/**
	 * Gets a customer by the customer's ID number in the database.
	 * @param customerId - the customers unique identifier
	 * @return the customer associated with the ID number
	 * @throws Exception - the general exception if anything goes wrong
	 */
	@GET
	@Path("/customer/{customer_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerById(@PathParam("customer_id") String customerId) throws Exception {
		Customer customer = CustomerDAO.findCustomerById(Integer.parseInt(customerId));
		EM.getEntityManager().refresh(customer);
		return customer;
	}
}
