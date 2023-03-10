package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	public static void main(String[] args)  {
		try {
			//separate first arguments and dayOfWeek
			String dow = Stream.of(args).filter(a -> !tryParseInt(a)).findFirst().orElse(null);
			args = Stream.of(args).takeWhile(a -> tryParseInt(a)).toArray(String[]::new);
			
			
			//verify that name is in DayOfWeek
			String day1st = dow == null? null : verifyDOW(dow);
			
			int monthYear[] = getMonthYear(args);
			printCalendar(monthYear[0], monthYear[1], day1st);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static String verifyDOW(String dow) {
		try {
		 return Arrays.stream(DayOfWeek.values()).filter(el -> el.toString().toLowerCase().startsWith(dow.toLowerCase()))
				.findFirst().get().name();
		} catch (Exception ex){
			throw new IllegalArgumentException("Name of day of week is wrong");
		}
	}

	private static boolean tryParseInt(String a) {
		boolean res = false;
		try {
			Integer.parseInt(a);
			res = true;
		} catch(Exception ex) {			
		}
		return res;
	}

	private static void printCalendar(int month, int year, String dow) {
		printTitle(month, year);
		printWeekDays(dow);
		printDates(month, year, dow);
		
	}

	private static void printDates(int month, int year, String day1st) {
		int weekDayNumber = getFirstDay(month, year) - (day1st == null? 0 : DayOfWeek.valueOf(day1st).ordinal());
		if (weekDayNumber <= 0) {
			weekDayNumber = 7 + weekDayNumber;
		}
				
		int offset = getOffset(weekDayNumber);
		
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		System.out.printf("%s", " ".repeat(offset));
		for(int date = 1; date <= nDays ; date++) {
			System.out.printf("%4d",date);
			if (++weekDayNumber > 7) {
				System.out.println();
				weekDayNumber = 1;
			}
		}
		
	}

	private static int getOffset(int weekDayNumber) {
		
		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(int month, int year) {
		
		return LocalDate.of(year, month, 1).getDayOfWeek().getValue();
	}

	private static void printWeekDays(String day1st) {
		System.out.print("  ");
		DayOfWeek[] daysOfWeek = reorderDOW(day1st);
		Arrays.stream(daysOfWeek)
		.forEach(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, locale)));
		System.out.println();
		
	}

	

	private static DayOfWeek[] reorderDOW(String day1st) {
		DayOfWeek[] daysOfWeek = DayOfWeek.values();
		if (day1st != null) {
			System.arraycopy(DayOfWeek.values(), DayOfWeek.valueOf(day1st).ordinal(), 
					daysOfWeek, 0, daysOfWeek.length - DayOfWeek.valueOf(day1st).ordinal());
			System.arraycopy(DayOfWeek.values(), 0, 
					daysOfWeek,  daysOfWeek.length - DayOfWeek.valueOf(day1st).ordinal(), DayOfWeek.valueOf(day1st).ordinal());
		}
		return daysOfWeek;
	}

	private static void printTitle(int month, int year) {
		
		System.out.printf("%s%d, %s\n"," ".repeat(YEAR_OFFSET), year, Month.of(month).getDisplayName(TextStyle.FULL,
				locale ));
		
		
	}

	private static int[] getMonthYear(String[] args) throws Exception {
		
		return args.length == 0 ? getCurrentMonthYear() : getMonthYearArgs(args);
	}

	private static int[] getMonthYearArgs(String[] args) throws Exception{
		
		return new int[] {getMonthArgs(args), getYearArgs(args)};
	}

	private static int getYearArgs(String[] args) throws Exception{
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be a positive number");
				}
			} catch (NumberFormatException e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception{
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("Month should be a number in the range [1-12]");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("Month should be a number");
		}
		
		
	}

	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] {current.getMonth().getValue(), current.getYear()};
	}

}