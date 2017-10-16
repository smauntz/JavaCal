/**
 * Created by puneettokhi on 7/21/17.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CreateEventView creates a new window to create events_list and add them to the
 * data model.
 *
 * @author Puneet
 *
 */
public class CreateEventView {

    /**
     * Create an event dialoge box
     *
     * @param model
     *            , event model
     */
    public CreateEventView(EventModel model) {
        final JFrame frame = new JFrame();

        Calendar cal = model.getCal();

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mmaa");
        Calendar formatEnd = new GregorianCalendar();
        formatEnd.setTime(cal.getTime());
        formatEnd.add(Calendar.MINUTE, 30);

        currentTime.format(cal.getTime());

        final JTextField descField = new JTextField("Description here");
        final JTextField dateField = new JTextField(
                (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
        final JTextField startField = new JTextField(currentTime.format(cal.getTime()));
        final JTextField endField = new JTextField(currentTime.format(formatEnd.getTime()));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // CONTROLLER gets the from the fields and updates the model with a new
        // Event.
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = descField.getText();
                String date = dateField.getText();
                String start = startField.getText();
                String end = endField.getText();
                 ArrayList<Event> events = model.getEvents();

                SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyyhh:mmaa");

                Date startDate = null;
                try {
                    startDate = sf.parse(date + start);
                } catch (ParseException ex) {
                    Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date endDate = null;
                try {
                    endDate = sf.parse(date + end);
                } catch (ParseException ex) {
                    Logger.getLogger(CreateEventView.class.getName()).log(Level.SEVERE, null, ex);
                }


                Calendar startCal = new GregorianCalendar();
                startCal.setTime(startDate);
                Calendar endCal = new GregorianCalendar();
                endCal.setTime(endDate);

                Event newEvent = new Event(name, (GregorianCalendar) startCal, (GregorianCalendar) endCal);

                boolean isConflicting = false;

                if (newEvent.end.before(newEvent.start) || newEvent.start.equals(newEvent.end)) {
                    JOptionPane.showMessageDialog(frame, "Sorry user, but the End can't come before start!", "Time Conflict",
                            JOptionPane.WARNING_MESSAGE);
                    isConflicting = true;
                }
                for (Event event : model.getEvents()) {  // Display error message
                    if (event.compareTo(newEvent) == 0) {
                        JOptionPane.showMessageDialog(frame, "Sorry but the Times can't overlap!", "Time Conflict",
                                JOptionPane.WARNING_MESSAGE);

                        isConflicting = true;
                        break;
                    }
                }

                if (!isConflicting) {
                    model.add_event(newEvent);

                }

                isConflicting = false;

                ArrayList<Event> events_list = new ArrayList<Event>();

                for (int i = 0; i < events.size(); i++) {
                    events_list.add(events.get(i));
                }
                for(int i=0;i<events_list.size();i++)
                {
                    System.out.println(i);
                }
                File file = new File("src/events.txt");

                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException element) {
                        // TODO Auto-generated catch block
                        element.printStackTrace();
                    }
                }

                // using file writer to write the events to the file
                FileWriter fw = null;

                try {
                    fw = new FileWriter(file.getAbsoluteFile());

                } catch (IOException e3) {

                    // TODO Auto-generated catch block
                    e3.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter(fw);


                for (Event s : events_list) {
                    try {
                        bw.write(s.getEvent_String(s) + System.getProperty("line.separator"));
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                try {
                    bw.close();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }


                frame.dispose();
            }
        });

        JPanel panel = new JPanel();

        panel.add(descField);
        panel.add(dateField);
        panel.add(startField);
        panel.add(endField);
        panel.add(saveButton);
        panel.add(cancelButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
