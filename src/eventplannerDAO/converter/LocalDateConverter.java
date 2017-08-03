package eventplannerDAO.converter;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
	
/**
 * LocalDate Converter Class. This creates Dates.
 * This file could be changed to user GregorianCalendar.
 * GregorianCalendars could also be changed to use Dates.
 * 
 * @author rdnot
 *
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	  @Override
	  public Date convertToDatabaseColumn(LocalDate date) {
	      Instant instant = 
			date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
	      return (Date)Date.from(instant);
	  }

	  @Override
	  public LocalDate convertToEntityAttribute(Date date) {
	      Instant instant = Instant.ofEpochMilli(date.getTime());
	      return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	  }
}
