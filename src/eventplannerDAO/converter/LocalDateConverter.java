package eventplannerDAO.converter;

import java.sql.Date;
import java.time.LocalDate;

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
    public Date convertToDatabaseColumn(LocalDate locDate) {
      return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
      return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}
