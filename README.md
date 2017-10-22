# Calendar
A calendar implemented as a console application written in Java.
The initial screen shows the current month with the current day embraced in brackets.
The options for the user are: Load, View by, Create, Go to, Event list, Delete, and Quit

	[L]oad :	System loads an events.txt file to populate the calendar with events. The format of the events file is:
					MM/DD/YYYY	START:TIME - END:TIME	EVENT TITLE
				where start time and end time follow 24-hour clock formats. An example of event is as follows:
					06/17/2018	09:00 - 10:30	Meeting
				If there is no end time for the event, 00:00 will be automatically inputted as the END:TIME.

	[V]iew by :	Displays the calendar according to a monthly or daily view. The Day view displays the current day and any events associated with this day. The month view displays the current month and any events associated in the days of this month, with the days with events embraced by curly brackets. [N]ext and [P] display the next and previous day of the day or month depending on which view is chosen.

	[C]reate :	Prompts the user to create an event. The system will ask for a title, date, start time, and end time of the event. This feature does not support events that span over a course of more than one day. The system also accounts for overlapping events, e.g. if a user attempts to create an event on the same day and overlapping time of an existing event, they will be prompted that they cannot do so.

	[G]o to : 	User can choose a specific day to go to on the calendar. All the events on that date will then be displayed.

	[E]vent list :	Allows the user to browse his/her scheduled events. All events scheduled will be displayed in order of starting date and starting time.

	[D]elete : 	Allows the user to delete an existing event. The user has the option of deleting the events on a specific date, or all events scheduled on the calendar.

	[Q]uit :	Once the user quits, all his/her scheduled events will be saved in a text file events.txt.
