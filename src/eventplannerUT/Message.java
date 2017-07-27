package eventplannerUT;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The message class used to carry information when using REST services
 * @author David.North
 *
 */
@XmlRootElement(name = "message")
public class Message implements Serializable {

	/**
	 * Allows the message to be sent over the wire and reconstructed
	 * on the recipient's end.
	 */
	private static final long serialVersionUID = 752173698797993003L;
	/**
	 * The HTTP code associated with the message
	 */
	private String code;
	/**
	 * The message body itself
	 */
	private String message;
	/**
	 * The name of the attribute
	 */
	private String attributeName;

	/**
	 * The constructor for the message object
	 * @param code - the HTTP status code associated with the message
	 * @param message - the message body itself
	 * @param attributeName - the name of the attribute associated with the message
	 */
	public Message(String code, String message, String attributeName) {
		this.code = code;
		this.message = message;
		this.attributeName = attributeName;
	}
	
	public Message(int code, String message, String attributeName) {
		this.code = String.valueOf(code);
		this.message = message;
		this.attributeName = attributeName;
	}
	
	public String getCode() {
		return code;
	}

	@XmlElement
	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttributeName() {
		return attributeName;
	}

	@XmlElement
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
