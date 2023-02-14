package telran.time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.time.BarMizvaAdjuster;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDateTest() {		
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,d");
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
	}

}
