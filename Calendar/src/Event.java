/**
 * A class that represents a single calendar event.
 * 
 * @author 		Priscilla Ng
 * @copyright	09/17/2017
 * @version		1.0
 * 
 */
public class Event {
	
	private String title;
	private String date;
	private String startTime;
	private String endTime;
	
	/**
	 * Constructs an event with a title, date, start time, and end time.
	 * @param title		the title of the event
	 * @param date		the date of the event in format MM/dd/yyyy
	 * @param startTime	the start time of the event
	 * @param endTime	the end time of the event
	 */
	public Event(String title, String date, String startTime, String endTime) {
		this.title = title;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Gets the title of the event.
	 * @return	the title of the event
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Sets a new title for the event.
	 * @param newTitle	the new title to be set to
	 * @precondition	newTitle must not be empty
	 * @postcondition	the title of the event will be changed to newTitle
	 */
	public void setTitle(String newTitle) {
		this.title = newTitle;
	}
	
	/**
	 * Gets the date of the event.
	 * @return	the date of the event
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Sets a new date for the event.
	 * @param newDate	the new date to be set to
	 * @precondition	newDate must be a valid string in the form MM/dd/yyyy
	 * @postcondition	the date of the event will be changed to newDate
	 */
	public void setDate(String newDate) {
		this.date = newDate;
	}
	
	/**
	 * Gets the start time of the event.
	 * @return	the start time of the event
	 */
	public String getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Sets a new start time of the event.
	 * @param newStartTime	the new start time of the event
	 * @precondition		newStartTime must be a valid string in the form MM/dd/yyyy
	 * @postcondition		the start time of the event will be changed to newStartTime
	 */
	public void setStartTime(String newStartTime) {
		this.startTime = newStartTime;
	}
	
	/**
	 * Gets the end time of the event.
	 * @return	the end time of the event
	 */
	public String getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Sets a new end time of the event.
	 * @param newEndTime	the new end time of the event
	 * @precondition		newEndTime must be a valid string in the form MM/dd/yyyy
	 * @postcondition		the end time of the event will be changed to newEndTime
	 */
	public void setEndTime(String newEndTime) {
		this.endTime = newEndTime;
	}

	/**
	 * Converts the string representation of the start time to an integer representation.
	 * @param startTime	the start time of the event	in string form
	 * @return			the start time of the event in integer form
	 */
	public int stringToIntStartTime(String startTime) {
		int startTimeInt = Integer.parseInt(startTime.replaceAll(":", ""));
		return startTimeInt;
	}
	
	/**
	 * Converts the string representation of the end time to an integer representation.
	 * @param endTime	the end time of the event in string form
	 * @return			the end time of the event in integer form
	 */
	public int stringToIntEndTime(String endTime) {
		int endTimeInt = Integer.parseInt(endTime.replaceAll(":", ""));
		return endTimeInt;
	}
	
	/**
	 * Gets the start time of the event as an integer.
	 * @return	the start time of the event
	 */
	public int getStartTimeInt() {
		return stringToIntStartTime(startTime);
	}
	
	/**
	 * Gets the end time of the event as an integer.
	 * @return	the end time of the event
	 */
	public int getEndTimeInt() {
		return stringToIntEndTime(endTime);
	}
	
	/**
	 * Prints a string representation of the event.
	 * @return	a string representation of the event
	 */
	public String printEvent() {
		return date + " " + startTime + " - " + endTime + " " + title;
	}
	
}
