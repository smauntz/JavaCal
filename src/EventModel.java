/**
 * Created by puneettokhi on 7/21/17.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.SwingConstants;


/**
 * Data model used to hold an Arraylist of Events, Calendar of selected day.
 * (Model part)
 *
 * @author Puneet
 */


public final class EventModel {

    private Calendar current_cal;
    private Calendar_View view;
    ArrayList<Event> events = new ArrayList<>();
    String[] daysOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    /**
     * create an Event model
     */
    public EventModel() {
        current_cal = new GregorianCalendar();
    }

    /**
     * highlight the day in the calendar
     *
     * @param day
     */
    public void set_Day(int day) {
        current_cal.set(Calendar.DAY_OF_MONTH, day);
        view.repaint();
    }

    /**
     * goes to the previous day
     */
    public void previous_Day() {
        current_cal.add(Calendar.DAY_OF_MONTH, -1);
        view.top_DayView.setText(daysOfWeek[current_cal.get(Calendar.DAY_OF_WEEK) - 1] + " " + (current_cal.get(Calendar.MONTH) + 1) + "/"
                + current_cal.get(Calendar.DAY_OF_MONTH));
        view.repaint();        // Draws the previous date when clicked
    }

    /**
     * goes to the next day
     */
    public void next_Day() {
        current_cal.add(Calendar.DAY_OF_MONTH, 1);
        view.top_DayView.setText(daysOfWeek[current_cal.get(Calendar.DAY_OF_WEEK) - 1] + " " + (current_cal.get(Calendar.MONTH) + 1) + "/"
                + current_cal.get(Calendar.DAY_OF_MONTH));
        view.repaint();    // Draws the next date when clicked
    }

    /**
     * returns the calendar
     *
     * @return , the calendar
     */
    public Calendar getCal() {
        return current_cal;
    }

    /**
     * adds the event to the event_list
     *
     * @param event
     */

    public void add_event(Event event) {


        events.add(event);

        Collections.sort(events);   // using collection sort



        view.repaint();  // calling the repaint method
    }

    public void clear_Event(Event event)
    {
        for(int i = events.size() - 1; i >= 0; i--)
        {
            if(events.get(i) == event)
            {
                events.remove(event);
            }
        }
        view.repaint();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/events.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<String> str_list = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            str_list.add(scanner.nextLine());
        }

        scanner.close();
        for (String str : str_list) {
            System.out.println(str);
        }
    }

    /**
     * delete event from the list
     *
     * @param event
     */

    public void delete_Event(Event event) {
        events.remove(event);
        Collections.sort(events);
        view.repaint();
    }

    /**
     * This code gets all the events
     *
     * @return
     */

    public ArrayList<Event> getEvents() {

        return events;
    }

    /**
     * sets the main view
     *
     * @param view
     */
    void setView(Calendar_View view) {
        this.view = view;
    }
}
