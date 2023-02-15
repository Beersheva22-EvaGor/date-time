package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		temporal = temporal.get(ChronoField.DAY_OF_MONTH) <=13 ? temporal.with(TemporalAdjusters.firstDayOfMonth()) : temporal.with(TemporalAdjusters.firstDayOfNextMonth());
		
		while (DayOfWeek.from(temporal) != DayOfWeek.SUNDAY) {
			temporal = temporal.plus(1, ChronoUnit.MONTHS);
		}
		return temporal.plus(12, ChronoUnit.DAYS );
	}

}