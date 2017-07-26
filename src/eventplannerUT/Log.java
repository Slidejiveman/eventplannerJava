package eventplannerUT;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Logging class used as a utility for the event planner.
 * Each developer needs to edit the path if they want to make use of this class.
 * @author David.North
 *
 */
public class Log {
    
	/**
	 * Default No Argument constructor
	 */
	public Log() {};
	
	/**
	 * Method that logs the message out to a file in the file system
	 * @param message - the text to be written in the log file
	 */
	public void log(String message) {
		String message1 = message + "\n";
		byte data[] = message1.getBytes();
		Path p = Paths.get("/Users/rdnot/Desktop/eventplannerlog.txt");
		
		try (OutputStream out = new BufferedOutputStream(
				Files.newOutputStream(p, CREATE, APPEND))) {
			out.write(data,  0,  data.length);
			out.close();
		} catch (IOException x) {
			
		}
	}
}
