package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Formatter;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.time.BarMizvaAdjuster;
import telran.time.NextFriday13;
import telran.time.WorkingDays;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDateTest() {		
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,d", Locale.forLanguageTag("rus"));
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.MONTHS;
		System.out.printf("Number of %s between %s and %s is %d", unit,
				birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));
		
	}
	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}
	@Test
	void displayCurrentDateTimeCanadaTimeZones () {
		//displaying current local date and time for all Canada time zones
		//displaying should contains time zone name
	
		System.out.format("\r\n\t -- Current DateTime Canada TimeZones: --");	
		ZoneId.getAvailableZoneIds().stream().filter(e -> e.startsWith("Canada")).forEach(el -> System.out.format("\r\n%20s  %-50s", el, ZonedDateTime.now(ZoneId.of(el)).toLocalDateTime()));
	}
	
	@Test
	void friday13Test() {
		Temporal expected = LocalDate.of(2023, 10, 13);
		NextFriday13 fri13 = new NextFriday13();
		assertEquals(expected,fri13. adjustInto(LocalDate.now()));
	}

	@Test
	void workingDaysTest() {
		WorkingDays wd = new WorkingDays(15, new DayOfWeek[]{DayOfWeek.SUNDAY, DayOfWeek.MONDAY , DayOfWeek.WEDNESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY});
		assertEquals(LocalDate.of(2023, 5,30), wd.adjustInto(LocalDate.of(2023, 2, 15)));
		
		
	}
}
