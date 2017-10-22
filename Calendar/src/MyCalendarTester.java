import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A tester class that outputs the console application for MyCalendar.
 * 
 * @author 		Priscilla Ng
 * @copyright	09/17/2017
 * @version		1.0
 * 
 */
public class MyCalendarTester {
	
	public static void main(String[] args) throws ParseException, IOException {
		MyCalendar c = new MyCalendar();
		GregorianCalendar cal = new GregorianCalendar(); // Capture today
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Map<Date, ArrayList<Event>> dateToEvents = new TreeMap<Date, ArrayList<Event>>();
		c.printCalendar(cal, dateToEvents);
		
		Scanner main = new Scanner(System.in);
		c.displayMainMenu();

		while (true) {
			String input = main.nextLine().toLowerCase();
			switch(input.charAt(0)) {
				case 'l' :
					c.load();
					c.displayMainMenu();
					break;
				case 'v' :
					c.view();
					c.displayMainMenu();
					break;
				case 'c' :
					System.out.println("Enter the title of your event: ");
					String title = main.nextLine();
					System.out.println("Enter the date of your event in MM/DD/YYYY format: ");
					String inputDate = main.nextLine();
					Date date = new java.sql.Date(dateFormat.parse(inputDate).getTime());
					System.out.println("Enter the start time of your event in 24-hour clock format: ");
					String startTime = main.nextLine();
					System.out.println("Enter the end time of your event in 24-hour clock format (if not applicable, press enter): ");
					String endTime = main.nextLine();
					if (endTime.equals("")) {
						endTime = "00:00";
					}
					Event e = new Event(title, inputDate, startTime, endTime);
					c.create(date, e);
					System.out.print("EVENT CREATED: ");
					System.out.println(e.printEvent());
					c.displayMainMenu();
					break;
				case 'g' :
					System.out.println("Enter a date to go to in MM/DD/YYYY format: ");
					String date2 = main.nextLine();
					c.goTo(date2);
					c.displayMainMenu();
					break;
				case 'e' :
					c.eventList();
					c.displayMainMenu();
					break;
				case 'd' :
					System.out.println("[S]elected or [A]ll?");
					String nextInput = main.nextLine().toLowerCase();
					if (nextInput.equals("s")) {
						System.out.println("Enter a date in MM/DD/YYYY format to delete all events on that date: ");
						String date3 = main.nextLine();
						c.deleteEvent(date3);
					}
					else if (nextInput.equals("a")) {
						c.deleteAllEvents();
					}
					else {
						System.out.println("Invalid input. Redirecting to main menu");
						break;
					}
					c.displayMainMenu();
					break;
				case 'q' :
					c.quit(); // save events into events.txt
					c.displayMainMenu();
					break;
				default :
					c.displayMainMenu();
					break;
			}
		}
		
	}
	
}
