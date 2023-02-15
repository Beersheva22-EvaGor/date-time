package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.Stream;

public class WorkingDays implements TemporalAdjuster {
	final int daysWeek = 7;
	DayOfWeek[] dayOffs;
	int numberOfDays;
	@Override
	public Temporal adjustInto(Temporal temporal) {	
		int[] allot = allotWeeksDaysRemainder(); 
		int weeks = allot[0];
		int additionalDays = allot[1];

		Temporal[] current  = {temporal.plus(weeks, ChronoUnit.WEEKS)};
		int i = 0;
		while (i < additionalDays){
			current[0] = current[0].plus(1, ChronoUnit.DAYS);
			if (Stream.of(dayOffs).filter(el -> el == DayOfWeek.from(current[0])).count()==0) {
				i++;
			}			
		}
		return current[0];
	}
	
	/**
	 * 
	 * @param days
	 * @return number of full weeks and days remainder
	 */
	private int[] allotWeeksDaysRemainder() {
		int days = numberOfDays;
		int resWeeks = 0;
		while (days > daysWeek) {			
			int weeks = days / daysWeek;
			days= days % daysWeek + weeks * dayOffs.length;
			resWeeks +=weeks;
		}	
		
		return new int[]{resWeeks, days};
	}
	
	
	public WorkingDays(int numberOfDays, DayOfWeek[] dayOffs) {
		this.numberOfDays = numberOfDays;
		this.dayOffs = Stream.of(dayOffs).distinct().toArray(DayOfWeek[]::new);
	}

}
