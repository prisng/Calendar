import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Map;
import java.text.ParseException;
import java.util.Scanner;
import java.util.TreeMap;


enum MONTHS {
	January, February, March, April, May, June, July, August, September, October, November, December;
}

enum DAYS {
	Sun, Mon, Tue, Wed, Thur, Fri, Sat ;
}

/**
 * A calendar model that holds events using a tree map with a Date object as the key and
 * an array list of events as the value. Functionalities include the creation and deletion
 * of events, viewing of events on specific dates, a day/month view of the calendar, a
 * listing of all events of the calendar, and the loading/creation of an events.txt file
 * that holds a list of all events.
 * 
 * @author 		Priscilla Ng
 * @copyright	09/17/2017
 * @version		1.0
 */
public class MyCalendar {

	private static Map<Date, ArrayList<Event>> dateToEvents;
	private Calendar c = new GregorianCalendar();
	private static final String EVENTFILE = "events.txt";
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Constructs a calendar object with a tree map of dates and a list of events.
	 */
	public MyCalendar() {
		this.dateToEvents = new TreeMap<Date, ArrayList<Event>>();
	}

	/**
	 * Prints the calendar.
	 * @param c				the calendar to be printed
	 * @param dateToEvent	the tree map of dates and respective events on that date
	 */
	public void printCalendar(Calendar c, Map<Date, ArrayList<Event>> dateToEvent) {
		dateToEvent = dateToEvents;
		
		// Access Month enum as array to get month headers
		MONTHS[] arrayOfMonths = MONTHS.values();

		// Transfer Calendar info to Gregorian Calendar
		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

		System.out.println();
		
		// Get this month & year
		int thisMonth = c.get(Calendar.MONTH);
		int thisYear = c.getWeekYear();
		
		// Print header for month & year
		System.out.printf("%29s", arrayOfMonths[thisMonth] + " " + thisYear + "\n");

		// Print header for days of the week
		for (DAYS day: DAYS.values()) {
			System.out.printf("%6s", day);
		}
		System.out.println();
		
		// Number of days in this month
		int daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

		// First day of the week
		int startingDay = temp.get(Calendar.DAY_OF_WEEK) - 1;
		
		// Today (numerical date)
		int today = c.get(Calendar.DAY_OF_MONTH);

		// Empty space for first row of calendar dates (if necessary)
		for (int i = 0; i < startingDay; i++) {
			System.out.print("      ");
		}
		
		// Print days of the month
		for (int i = 1; i <= daysInMonth; i++) {
			
			// Mark the calendar dates with events
			for (Map.Entry<Date, ArrayList<Event>> entry : dateToEvent.entrySet()) {
				ArrayList<Event> eventList = entry.getValue();
				for (int j = 0; j < eventList.size(); j++) {
					Date date = entry.getKey();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH);
					int day = cal.get(Calendar.DAY_OF_MONTH);
					
					if (dateToEvent.containsKey(date)) {
						// Check if the events are in this month/year
						if (thisYear == year && thisMonth == month) {
							// If there is an event this month, print { } around the date of the event
							// First "if" is if event is on Saturday; need to move to next row after
							if ((i == day) && ((i + startingDay) % 7 == 0)) {
								System.out.format("   {%d}", i);
								i++;
								System.out.println();
							}
							if (i == day) {
								System.out.format("   {%d}", i);
								i++;
							}
						}
					}
					
				}	// end for loop
			}	// end for loop
			
			// Highlight today in brackets			
			if (i == today) {
				System.out.format("   [%d]", i);
			}
			else {
				System.out.format("%6d", i);		
			}
			
			// For moving numbers onto new row
			if ((i + startingDay) % 7 == 0) {
				System.out.println();
			}
			
		}	// end for loop
		
	}

	/**
	 * Prints the current day.
	 * Example: Sun September 17, 2017
	 * @return	the current day
	 */
	public String printToday() {
		// Month and day enums as arrays
		MONTHS[] arrayOfMonths = MONTHS.values();
		DAYS[] arrayOfDays = DAYS.values();

		return arrayOfDays[c.get(Calendar.DAY_OF_WEEK) - 1] + " " +
		arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.DAY_OF_MONTH) + ", " + c.get(Calendar.YEAR);
	}

	/**
	 * Displays a main menu for navigation.
	 */
	public void displayMainMenu() {
		System.out.println("\nSelect one of the following options:");
		System.out.println("[L]oad   [V]iew by   [C]reate   [G]o to   [E]vent list   [D]elete   [Q]uit");
	}

	/**
	 * Displays the navigation menu for viewing by day or month.
	 */
	public void displayNavigation() {
		System.out.println("\n[P]revious or [N]ext or [M]ain menu");
	}
	
	/**
	 * Loads a text file events.txt to populate calendar with events (if any).
	 * @throws IOException		if stream to events.txt cannot be written to
	 * @throws ParseException	if the date of events are not written in the correct format MM/dd/yyyy
	 */
	public void load() throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(EVENTFILE));
		try {
			String line = "";
			// For each line in the text file, create a new event
			while ((line = br.readLine()) != null) {
				String inputDate = line.substring(0, 10);
				Date date = new java.sql.Date(dateFormat.parse(inputDate).getTime());
				String startTime = line.substring(11, 16);
				String endTime = line.substring(19, 24);
				String title = line.substring(25);
				
				// Create an event based on that line
				Event e = new Event(title, inputDate, startTime, endTime);
				create(date, e);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Events loaded onto the calendar.");
	}

	/**
	 * Displays the day view or month view of the calendar.
	 */
	public void view() {
		try {
			System.out.println("[D]ay view or [M]onth view");
			Scanner view = new Scanner(System.in);
			String input = view.nextLine().toLowerCase();
			
			while (!input.equals("q")) {
				// Enter day view of calendar
				if (input.equals("d")) {
					String pattern = "MM/dd/yyyy";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String today = simpleDateFormat.format(new Date());
					Date eventDate = new java.sql.Date(dateFormat.parse(today).getTime());

					// Check if there are any scheduled events today
					if (dateToEvents.containsKey(eventDate)) {
						System.out.println("Events today " + "(" + printToday() + "):");
						ArrayList<Event> eventsToday = dateToEvents.get(eventDate);
						for (int j = 0; j < eventsToday.size(); j++) {
							Event e = eventsToday.get(j);
							System.out.println("\t" + e.printEvent());
						}
					}
					else {
						System.out.println("There are no scheduled events today.");
					}
					viewDay();
					break;
				}
				// Enter month view of calendar
				else if (input.equals("m")) {
					printCalendar(c, dateToEvents);
					viewMonth();
					break;
				}
				else {
					System.out.println("Invalid input. Redirecting to main menu");
					break;
				}
			}	// end while loop
		} catch (ParseException e) {
			System.err.println("The date entered is not in the right format.");
		}
	}

	/**
	 * Displays the previous or next day views of the calendar.
	 */
	public void viewDay() {
		try {
			// Formatting date pattern to match user input
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			// [P]revious or [N]ext or [M]ain menu?
			displayNavigation();
			
			// User input
			Scanner view = new Scanner(System.in);
			
			// Exit condition
			boolean exit = false;

			while (exit == false) {
				String nextInput = view.nextLine().toLowerCase();
				if (nextInput.equals("p")) {
					// Go back 1 day
					c.add(Calendar.DAY_OF_WEEK, -1);
					
					// Get that date
					Date previous = c.getTime();
					String previousDay = simpleDateFormat.format(previous);
					Date eventDate = new java.sql.Date(dateFormat.parse(previousDay).getTime());
					
					// Print that date
					System.out.println(previousDay);
					
					// Check if there are any scheduled events today
					if (dateToEvents.containsKey(eventDate)) {
						System.out.println("Events on " + printToday() + ":");
						ArrayList<Event> eventsToday = dateToEvents.get(eventDate);
						for (int j = 0; j < eventsToday.size(); j++) {
							Event e = eventsToday.get(j);
							System.out.println("\t" + e.printEvent());
						}
					}
					else {
						System.out.println("There are no scheduled events today.");
					}
					viewDay();
					break;
				}
				else if (nextInput.equals("n")) {
					// Go forward 1 day
					c.add(Calendar.DAY_OF_WEEK, 1);

					// Get that date
					Date next = c.getTime();
					String nextDay = simpleDateFormat.format(next);
					Date eventDate = new java.sql.Date(dateFormat.parse(nextDay).getTime());
					
					// Print that date
					System.out.println(nextDay);
					
					// Check tree map if previous date has any events
					if (dateToEvents.containsKey(eventDate)) {
						// Print out the event for the next day
						System.out.println("Events on " + printToday() + ":");

						ArrayList<Event> eventsToday = dateToEvents.get(eventDate);
						for (int j = 0; j < eventsToday.size(); j++) {
							Event e = eventsToday.get(j);
							System.out.println("\t" + e.printEvent());
						}
					}
					else {
						System.out.println("There are no scheduled events that day.");
					}
					
					// Recursively call this function
					viewDay();
					break;
				}
				else if (nextInput.equals("m")) {
					System.out.println("Redirecting to main menu.");
					exit = true;
				}
			}	// end while loop
		} catch (ParseException e) {
			System.err.println("The date entered is not in the right format.");
		}
		
	}
	
	/**
	 * Displays the previous or next month views of the calendar.
	 */
	public void viewMonth() {
		// Formatting date pattern to match user input
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		// [P]revious or [N]ext or [M]ain menu?
		displayNavigation();
		
		// User input
		Scanner view = new Scanner(System.in);
		
		// Exit condition
		boolean exit = false;

		while (exit == false) {
			String nextInput = view.nextLine().toLowerCase();
			if (nextInput.equals("p")) {
				// Go back 1 month
				c.add(Calendar.MONTH, -1);
				
				// Get that date
				Date previous = c.getTime();
				String previousMonth = simpleDateFormat.format(previous);
				
				// Print the calendar for that month
				printCalendar(c, dateToEvents);
				
				// [P]revious or [N]ext or [M]ain menu?
				System.out.println();
				displayNavigation();

			}
			else if (nextInput.equals("n")) {
				// Go forward 1 month
				c.add(Calendar.MONTH, 1);
				
				// Get that date
				Date next = c.getTime();
				String nextMonth = simpleDateFormat.format(next);
				
				// Print the calendar for that month
				printCalendar(c, dateToEvents);
				
				// [P]revious or [N]ext or [M]ain menu?
				System.out.println();
				displayNavigation();
			}
			else if (nextInput.equals("m")) {
				System.out.println("Redirecting to main menu.");
				exit = true;
			}
		}	// end while loop
	}

	/**
	 * Creates an event in this calendar.
	 * @param d		the date of the event
	 * @param e		the event
	 */
	public void create(Date d, Event e) {
		// Create condition to check if there are conflicting events
		boolean conflict = false;
		
		// If there is an event that day, check if there are conflicting events existing
		if (dateToEvents.containsKey(d)) {
			ArrayList<Event> events = dateToEvents.get(d);
			for (int i = 0; i < events.size(); i++) {
				Event e2 = dateToEvents.get(d).get(i);
				// Check for time conflict
				if (e.getStartTimeInt() <= e2.getEndTimeInt() && e2.getStartTimeInt() <= e.getEndTimeInt()) {
					System.out.println("The event you are trying to create is conflicting with an existing event.");
					System.out.println("Cannot create overlapping event. Redirecting to main menu.");
					conflict = true;
				}
			}
		}
		
		// Create a non-conflicting event that day
		if (dateToEvents.containsKey(d) && conflict == false) {
			ArrayList<Event> events = dateToEvents.get(d);
			events.add(e);
			dateToEvents.put(d, events);
		}
		// Otherwise, create an event that day
		else if (conflict == false) {
			ArrayList<Event> events = new ArrayList<Event>();
			events.add(e);
			dateToEvents.put(d, events);
		}
	}

	/**
	 * Goes to a specific date of the calendar and shows all events on that day.
	 * @param date	the date to go to
	 */
	public void goTo(String date) {
		try {
			// Formatting date pattern to match user input
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String[] dateSplit = date.split("/");
			int month = Integer.parseInt(dateSplit[0].replaceFirst("^0+(?!$)", "")) - 1;
			int day = Integer.parseInt(dateSplit[1].replaceFirst("^0+(?!$)", ""));
			int year = Integer.parseInt(dateSplit[2]);
			
			c.set(year, month, day);
			Date that = c.getTime();
			String thatDay = simpleDateFormat.format(that);
			Date eventDate = new java.sql.Date(dateFormat.parse(thatDay).getTime());
			
			// If the event tree map has the same date the user entered
			if (dateToEvents.containsKey(eventDate) && thatDay.equals(date)) {
				//System.out.println("Events on);
				ArrayList<Event> eventsThatDay = dateToEvents.get(eventDate);
				// Print the events that day
				for (int i = 0; i < eventsThatDay.size(); i++) {
					System.out.println(eventsThatDay.get(i).printEvent());
				}
			}
			else {
				System.out.println("There are no scheduled events on that day.");
				System.out.println("Redirecting to main menu.");
			}
		} catch (ParseException e) {
			System.err.println("The date entered is not in the right format.");
		}
	}
	
	/**
	 * Lists all of this calendar's events.
	 */
	public void eventList() {
		// Formatting date
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		System.out.println("List of all scheduled events: ");
		
		if (dateToEvents.isEmpty()) {
			System.out.println("\tThere are no scheduled events to show.");
		}
		
		// Iterate through event tree map to print events
		for (Map.Entry<Date, ArrayList<Event>> entry : dateToEvents.entrySet()) {
			Date key = entry.getKey();
			ArrayList<Event> eventList = entry.getValue();
			for (int i = 0; i < eventList.size(); i++) {
				System.out.println("\t" + eventList.get(i).printEvent());
			}
		}
	}

	/**
	 * Deletes a specific event on this calendar.
	 * @param dateOfEvent	the date of the event to be deleted
	 */
	public void deleteEvent(String dateOfEvent) {
		try {
			// Formatting date pattern to match user input
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String[] dateSplit = dateOfEvent.split("/");
			int month = Integer.parseInt(dateSplit[0].replaceFirst("^0+(?!$)", "")) - 1;
			int day = Integer.parseInt(dateSplit[1].replaceFirst("^0+(?!$)", ""));
			int year = Integer.parseInt(dateSplit[2]);
			
			c.set(year, month, day);
			Date that = c.getTime();
			String thatDay = simpleDateFormat.format(that);
			Date eventDate = new java.sql.Date(dateFormat.parse(thatDay).getTime());
			
			if (dateToEvents.containsKey(eventDate)) {
				dateToEvents.remove(eventDate);
				System.out.println("Event successfully deleted.");
			}
			else {
				System.out.println("There are no scheduled events to delete on that date.");
				System.out.println("Redirecting to main menu.");
			}
			return;
		} catch (ParseException e) {
			System.err.println("The date entered is not in the right format.");
		}
	}

	/**
	 * Deletes all events on this calendar.
	 */
	public void deleteAllEvents() {
		if (!dateToEvents.isEmpty()) {
			dateToEvents.clear();
			System.out.println("All events successfully deleted.");
		}
		else {
			System.out.println("There are no scheduled events to delete.");
			System.out.println("Redirecting to main menu.");
		}
	}

	/**
	 * Exits the calendar console and saves all created events into events.txt.
	 */
	public void quit() {
		// Formatting date
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		// BufferedWriter used to write content of event map into text file
		BufferedWriter writer = null;
		
		try {
		    writer = new BufferedWriter(new FileWriter(EVENTFILE));
		    
		    if (!dateToEvents.isEmpty()) {
				for (Map.Entry<Date, ArrayList<Event>> entry : dateToEvents.entrySet()) {
					Date key = entry.getKey();
					ArrayList<Event> eventList = entry.getValue();
					String date = simpleDateFormat.format(key);
					for (int i = 0; i < eventList.size(); i++) {
						writer.write(eventList.get(i).printEvent() + "\n");
					}
				}
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
					System.out.println("File successfully created. (events.txt)");
				}
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		
	}
}