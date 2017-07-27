package eventplannerREST;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import eventplannerDAO.CustomerDAO;
import eventplannerDAO.EM;
import eventplannerPD.Customer;
import eventplannerUT.Message;

/**
 * This class handles all of the RESTful services related
 * to customers. JQuery will be used to call these services
 * through the backend.
 * @author rdnot
 *
 */
@Path("/customerservices")
public class CustomerService {

	List<Message> messages = new ArrayList<Message>();
	
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
          return CustomerDAO.listCustomers(); // add a list customer's method that takes the parameters in DAO
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
	
	@POST
	@Path("/customer/{customer_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addCustomer(
			Customer customer, @Context final HttpServletResponse response
			) throws Exception {
		
		try {
			int newCustomerId = customer.getId();
			Customer possibleCustomer = CustomerDAO.findCustomerById(newCustomerId);
			// If possibleCustomer isn't null, the ID already exists
			if(possibleCustomer != null) {
				response.setStatus(HttpServletResponse.SC_CONFLICT); // 409
				try {
					response.flushBuffer();
				} catch (Exception ex) {}
				messages.add(new Message(HttpServletResponse.SC_CONFLICT, "A Customer with this ID already exists.", "CustomerService"));
			    return messages;
			} else { // if the value is null, we can add the customer
				EntityTransaction userTransaction = EM.getEntityManager().getTransaction();
				userTransaction.begin();
				CustomerDAO.addCustomer(possibleCustomer);
				userTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messages;
		
	}
}
