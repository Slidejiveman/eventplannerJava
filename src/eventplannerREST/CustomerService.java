package eventplannerREST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.owlike.genson.Genson;

import eventplannerDAO.CustomerDAO;
import eventplannerDAO.EM;
import eventplannerDAO.EventDAO;
import eventplannerPD.Customer;
import eventplannerPD.Event;
import eventplannerUT.Log;
import eventplannerUT.Message;

@Path("/customerservice")
public class CustomerService {
	//The list of messages to deliver to the user.
	ArrayList<Message> messages = new ArrayList<Message>();
	//logger
	Log log = new Log();
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers(
			@DefaultValue("0") @QueryParam("page") String page,
			@DefaultValue("10") @QueryParam("per_page") String perPage){
		List<Customer>customers=CustomerDAO.listCustomers();
		log.log(customers.toString());
		log.logJAXB();
		Genson gen = new Genson();
		for(Customer customer:customers){
			String txt = gen.serialize(customer);
			System.out.println(txt);
		}
		return customers;
	}
	@GET
	@Path("/customers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id") String id){
		Customer customer = CustomerDAO.findCustomerById(Integer.parseInt(id));
		EM.getEntityManager().refresh(customer);
		return customer;
	}
	@POST
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> addCustomer(Customer customer, @Context final HttpServletResponse response)throws IOException{
		if(customer.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Add"));
			return messages;
		}else{
			List<Message> errMessages = customer.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
			EntityTransaction customerTransaction = EM.getEntityManager().getTransaction();
			customerTransaction.begin();
			CustomerDAO.addCustomer(customer);
			customerTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Add"));
			return messages;
		}
	}
	@PUT
	@Path("/customers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Message> updatecustomer(Customer customer, @PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Customer customerToUpdate = CustomerDAO.findCustomerById(Integer.parseInt (id));
		if(customerToUpdate.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Update"));
			return messages;
		} else {
			List<Message> errMessages = customer.validate();
			if (errMessages.size() != 0) {
				response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				try {
					response.flushBuffer();
				} catch (Exception e) {}
				return errMessages;
			}
		}
		EntityTransaction customerTransaction = EM.getEntityManager().getTransaction();
		customerTransaction.begin();
		Boolean result = customerToUpdate.update(customer);
		customerTransaction.commit();
		if (result) {
			messages.add(new Message("rest001", "Success Operation", "Update"));
			return messages;
		} else {
			try {
			    response.flushBuffer();
			}catch (Exception e) {}	 
		messages.add(new Message("rest002", "Fail Operation", "Update"));
		return messages;
		}
	}
	@DELETE
	@Path("/customers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> deletecustomer(@PathParam("id") String id, @Context final HttpServletResponse response)throws IOException{
		Customer customerToDelete = CustomerDAO.findCustomerById(Integer.parseInt(id));
		if(customerToDelete.equals(null)){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			try{
				response.flushBuffer();
			}catch(Exception e){}
			messages.add(new Message("rest002", "Fail Operation", "Delete"));
			return messages;
		}else{
			EntityTransaction customerTransaction = EM.getEntityManager().getTransaction();
			customerTransaction.begin();
			CustomerDAO.deleteCustomer(customerToDelete);
			customerTransaction.commit();
			messages.add(new Message("rest001", "Success Operation", "Delete"));
			return messages;
		}
	}
	@OPTIONS
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSupportedOperations(){
		return ("{ {'POST' : { 'description' : 'add a customer'}} {'GET' : {'description' : 'get a customer'}}}");
	}
}
