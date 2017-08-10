package eventplannerPD.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import eventplannerPD.Event;

/**
 * The Email Utility is used to send automated emails to customers when an event is ready for review and when the event has been accepted.
 */
public class EmailUtil {

	/**
	 * The Gmail username of the Ubiquity system.
	 */
	private static final String userName = "ubiquitymail@gmail.com";
	/**
	 * Ubiquity's Gmail password
	 */
	private static final String password = "UbiquityM41l$";
	
    /**
     * Sends the confirmation email to the customer associated with a given event when the event's status is changed.
     * @param to - the email address of the receiver
     * @param from - the email address of the sender, which is Ubiquity Mail
     * @param host - the email host service. It is gmail in this case
     * @param event = the event in question
     */
    public static List<eventplannerUT.Message> sendConfirmationEmail(String to, String from, String host, Event event) {
    	
    	InternetAddress fromAddress = null;
    	InternetAddress toAddress = null;
    	Address[] addressArray = new Address[1];
    	int sendingPort = 465;
    	Properties properties = new Properties();
    	properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(sendingPort));
        properties.put("mail.smtp.user", userName);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties);
        List<eventplannerUT.Message> returnMessages = new ArrayList<eventplannerUT.Message>();
        
        // Convert the email accounts into Internet addresses.
        try {
        	 
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
            addressArray[0] = toAddress;
 
        } catch (AddressException addex) {
        	returnMessages.add(new eventplannerUT.Message("email000", "Addresses failed to resolve", "Address"));
        	addex.printStackTrace();
        }
        
        // populate the message and send it
        try {
        	// Create a default MimeMessage object.
        	MimeMessage message = new MimeMessage(session);
        	
        	// Set From: header field of the header
        	message.setFrom(fromAddress);
        	
        	// Set To: header field of the header.
        	// An array of addresses is expected, so it must be converted and casted.
        	message.addRecipients(Message.RecipientType.TO, addressArray);
        	
        	// Set Subject: header field.
        	message.setSubject("Event: " + event.getName() + " is now " + event.getEventStatus());
        	
        	// The actual message
        	message.setText("Dear " + event.getCustomer().getName() + "\n" + 
        			"Your upcoming event is now in the " + event.getEventStatus() +" state\n" + 
        			"Our planner, " + event.getAssignedUser().getName() + " will be reaching out to you shortly.\n"
        			+ "Thank you for using Eagle Event Planning!");
        	
        	// Send the message
        	Transport transport = session.getTransport("smtps");
        	transport.connect(host, sendingPort, userName, password);
        	transport.sendMessage(message, message.getAllRecipients());
        	transport.close();
        	System.out.println("Sent message successfully");
        } catch (MessagingException mex) {
        	returnMessages.add(new eventplannerUT.Message("email001", "Email sent successfully", "Email"));
        	mex.printStackTrace();
        }
        return returnMessages;
    }

}