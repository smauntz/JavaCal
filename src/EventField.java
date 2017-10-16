/**
 * Created by puneettokhi on 7/21/17.
 */
import javax.swing.JTextField;

/**
 * create event field for the events_list
 *
 * @author Puneet
 *
 */

public class EventField extends JTextField {
    private Event event;

    /**
     * make a field
     *
     * @param columns
     */
    public EventField(int columns) {
        super(columns);
        event = null;
    }

    /**
     * set events_list to the field
     *
     * @param event,
     *            event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * get events_list from the field
     *
     * @return
     */
    public Event getEvent() {
        return event;
    }
}
