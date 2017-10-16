/**
 * Created by puneettokhi on 7/21/17.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Object type Event which could hold a name, Calendar start and end times.
 *
 * @author Puneet
 */
class Event implements Comparable<Event> { // To compare two events_list
    String event_Name;   // local variables
    Calendar start;
    Calendar end;

    /**
     * create an event
     *
     * @param name
     * @param start
     * @param end
     * @throws IllegalArgumentException
     */

    // constructor of the event class

    public Event(String name, GregorianCalendar start, GregorianCalendar end) throws IllegalArgumentException {
        this.event_Name = name;
        this.start = start;
        this.end = end;
    }

    /**
     * get the event string
     *
     * @param e,
     *            the event @return, the event string
     */
    public String getEvent_String(Event e) {  // returns the event in String format
        Date startDate = e.start.getTime();
        Date endDate = e.end.getTime();

        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        return e.event_Name + sf.format(startDate) + " - " + sf.format(endDate);
    }

    @Override
    /**
     * compare two events_list
     */

    public int compareTo(Event new_Event) {


        if (new_Event.start.before(this.start) && new_Event.end.before(this.start)) {
            return 1;
        } else if (new_Event.start.after(this.end) && new_Event.end.after(this.end)) {
            return -1;
        } else {
            return 0;
        }
    }

}
