package Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public class DateHelper {
	public static long workDaysBetween(LocalDate start, LocalDate end) {
		LocalDate date = start;
		int result = 0;
		Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY);

		while(date.isBefore(end)) {
			if(!weekend.contains(date.getDayOfWeek()))
				result ++;
			date = date.plusDays(1);
		}
		return result;
	}
}
