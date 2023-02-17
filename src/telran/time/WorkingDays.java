package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class WorkingDays implements TemporalAdjuster {
 private int [] daysOff;
 private int nDays;
	@Override
	public Temporal adjustInto(Temporal temporal) {
		int count = 0;
		if (daysOff.length < DayOfWeek.values().length) {
			while (count != nDays) {
				temporal = temporal.plus(1, ChronoUnit.DAYS);
				if (!contains(temporal.get(ChronoField.DAY_OF_WEEK))) {
					count++;
				}
			}
		}
	
		return temporal;
	}
	private boolean contains(int day) {
		return Arrays.stream(daysOff).anyMatch(d -> d == day);		
	}
	
	public WorkingDays(DayOfWeek[] dayOffs, int nDays) {
		this.nDays = nDays;
		daysOff = Arrays.stream(dayOffs).mapToInt(d -> d.getValue()).toArray();
	}
	

}