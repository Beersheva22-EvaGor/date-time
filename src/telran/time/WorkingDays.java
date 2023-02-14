package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.Stream;

public class WorkingDays implements TemporalAdjuster {
	DayOfWeek[] dayOffs;
	int numberOfDays;
	@Override
	public Temporal adjustInto(Temporal temporal) {
		int weeks = numberOfDays / 7;
		int additionalDays = weeks * dayOffs.length;
		LocalDate[] current = {LocalDate.of(temporal.get(ChronoField.YEAR), temporal.get(ChronoField.MONTH_OF_YEAR), temporal.get(ChronoField.DAY_OF_MONTH))
				.plusWeeks(weeks)};
		int i = 0;
		while (i < numberOfDays % 7 + additionalDays){
			current[0] = current[0].plusDays(1);
			if (Stream.of(dayOffs).filter(el -> el == current[0].getDayOfWeek()).count()==0) {
				i++;
			}			
		}
		return current[0];
	}
	public WorkingDays(int numberOfDays, DayOfWeek[] dayOffs) {
		this.numberOfDays = numberOfDays;
		this.dayOffs = Stream.of(dayOffs).distinct().toArray(DayOfWeek[]::new);
	}

}
