package edu.paidea.ocp8.ch5;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

/**
 * Checklist:
 * 1- No mezclar horas con fechas, chequee doble cascara
 * 2- En DateTimeFormatter observe el factory: ofLocalizedDate, ofLocalizedTime, etc
 * 
 */
public class Ch5Dates {

	public static void main(String[] args) {
		Ch5Dates ch5 = new Ch5Dates();
		//ch5.testLocale();
		ch5.testDate();
	}
	
	public void testLocale(){
		Locale l1= new Locale("hi");
		Locale l2 = new Locale("hi", "IN");
		Locale l3= new Locale("IN");
		Locale l4= new Locale("IN", "hi");
		
		Locale l5 = Locale.ITALY;
		Locale l6 = new Locale.Builder().setLanguage("en").setRegion("us").build();
		
		System.out.println(l1+" - "+l2+" - "+l3+" - "+l4+" - "+l5+" - "+l6);
		//Locale l5 = Locale.create("hi");
		//Locale l6 =Locale.create("IN");
	}
	
	public void testDate(){
		System.out.println(LocalDate.of(2014, 6, 21));
		System.out.println(LocalDate.of(2014, Month.JUNE, 21));
		System.out.println(LocalDate.of(2014, 5, 21));
		System.out.println(LocalDate.of(2014, Calendar.JUNE, 21));
		
		//LocalDate date = LocalDate.parse("2018-04-30", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate date = LocalDate.parse("2018-04-30", DateTimeFormatter.ISO_LOCAL_DATE);
		date.plusDays(2);
		//date.plusHours(3);
		System.out.println(date.getYear() + " "
		+ date.getMonth() + " "+ date.getDayOfMonth());
		
		LocalDate date1 = LocalDate.of(2018, Month.APRIL, 30);
		System.out.println(date1.getYear() + " " + date1.getMonth()
		+ " "+ date1.getDayOfMonth());
		
		LocalDate date2 = LocalDate.of(2018, Month.APRIL, 30);
		date2.plusDays(2);
		date2.plusYears(3);
		System.out.println(date2.getYear() + " "
		+ date2.getMonth() + " "+ date2.getDayOfMonth());
		
		LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
		Period p = Period.of(1, 2, 3);
		d = d.minus(p);
		DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		System.out.println(d.format(f));
		f = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		System.out.println(d.format(f));
		f = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		System.out.println(d.format(f));
		
		d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
		p = Period.ofDays(1).ofYears(2);
		d = d.minus(p);
		f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		System.out.println(f.format(d));
		
		System.out.println("Para ZoneDateTime con ZoneId US/Eastern el 13 marzo 2016 no hay 2 am, y 6 nov hay 2 veces 1 am");
		LocalDate d1 = LocalDate.of(2016, Month.MARCH, 13);
		LocalTime time = LocalTime.of(1, 30);
		ZoneId zone = ZoneId.of("US/Eastern");
		ZonedDateTime dateTime1 = ZonedDateTime.of(d1, time, zone);
		ZonedDateTime dateTime2 = dateTime1.plus(1, ChronoUnit.HOURS);
		long hours = ChronoUnit.HOURS.between(dateTime1, dateTime2);
		int clock1 = dateTime1.getHour();
		int clock2 = dateTime2.getHour();
		System.out.println(hours + "," + clock1 + "," + clock2);
		
		LocalDate d2 = LocalDate.of(2016, 3, 13);
		LocalTime time1 = LocalTime.of(2, 15);
		ZonedDateTime a = ZonedDateTime.of(d2, time1, zone);
		System.out.println(a);
		
		
		String m1 = Duration.of(1, ChronoUnit.MINUTES).toString();
		String m2 = Duration.ofMinutes(1).toString();
		String s = Duration.of(60, ChronoUnit.MINUTES).toString();
		String d3 = Duration.ofDays(1).toString();
		String p3 = Period.ofDays(1).toString();
		String p4 = Period.ofWeeks(5).toString();
		String p5 = Period.ofMonths(15).toString();
		System.out.println(m1+ " - "+m2+" - "+s+" - "+d3+" - "+p3+" - "+p4+" - "+p5);
		System.out.println(m1 == m2);
		System.out.println(m1.equals(m2));
		System.out.println(m1.equals(s));
		System.out.println(d3 == p3);
		System.out.println(d3.equals(p3));
		System.out.println("Duration tiene el prefijo PT, mientras que Period tiene el prefijo P");
		System.out.println("Duration convierte, segundos a minutos, etc y solo maneja tiempo, el dia lo convierte a horas");
		System.out.println("Period no convierte y trabaja fechas, solo comvierte semanas");

	}

}
