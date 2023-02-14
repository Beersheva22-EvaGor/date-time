package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if (!(temporal instanceof LocalDate)) {
			throw new IllegalArgumentException();
		}
		
		LocalDate res = (LocalDate) temporal;
		res = res.getDayOfMonth()<=13? LocalDate.of(res.getYear(), res.getMonth(), 13) : LocalDate.of(res.plusMonths(1).getYear(), res.plusMonths(1).getMonth(), 13);
		while (res.getDayOfWeek() != DayOfWeek.FRIDAY) {
			res = res.plusMonths(1);
		}
		return res;
	}

}