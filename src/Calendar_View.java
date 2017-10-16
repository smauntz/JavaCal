/**
 * Created by puneettokhi on 7/21/17.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * This code to Create the view portion of Calendar
 *
 * @author Puneet
 *
 */
public class Calendar_View {
    /**
     * This class is used to display the day view panel with all events that have been added to the data
     * model
     */

    private final JPanel day_panel;
    private final JLabel month_label = new JLabel();
    private final JPanel month_panel;
    private static EventModel model;
    private final Calendar cal;

    ArrayList<Event> events_list;
    EventField[] event_array = new EventField[48];
    JLabel top_DayView;
    public static String name;
    public static String date;
    public static String start;
    public static String end;


    private static JFrame frame;
    private static JTextField jTextField;
    private static JPanel timePanel;


    private final String[] DAYS = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    /**
     * Constructs a new Main View using an EventModel data model.
     *
     * @param model
     *            is required to display the right data for the view.
     */
    public void drawAgenda(JPanel agendaPanel) {
    	ArrayList<Event> currentEvents = model.getEvents();
        for (Event e : currentEvents) {
            if (e.start.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                
                Date startDate = e.start.getTime();
                Date endDate = e.end.getTime();
                
                SimpleDateFormat sf = new SimpleDateFormat("hh:mm aa");
               
                agendaPanel.setLayout(new CardLayout());
                agendaPanel.add(new JLabel(e.event_Name +(sf.format(startDate)) +(sf.format(endDate))));
            }
        }
    }
    
    public Calendar_View(final EventModel model) {

        // Initializes model variable


        // Loads and displays the events from the text file

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

        this.model = model;
        this.cal = model.getCal();
        events_list = model.getEvents();
        top_DayView = new JLabel(DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + " " + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DAY_OF_MONTH), SwingConstants.CENTER);
        top_DayView.setBackground(Color.WHITE);


        // Screen display and defining all the buttons
        // Buttons for buttonPanel panel
        JButton todayButton = new JButton("Today");
        JButton monthprevButton = new JButton("<");
        JButton monthnextButton = new JButton(">");
        JButton dayButton = new JButton("Day");
        JButton agendaButton = new JButton("Agenda");
        JButton weekButton = new JButton("Week");
        JButton monthButton = new JButton("Month");
        JButton fileButton = new JButton("From File");
        //end of buttonPanel panel
        JButton createButton = new JButton("CREATE");  // Create button for the event;
        createButton.setBackground(Color.RED);
        createButton.setForeground(Color.WHITE);

//        createButton.setBackground(Color.BLACK);

     //   createButton.setForeground(Color.RED);
        
        JButton previousButton = new JButton("<");

       
        previousButton.setBackground(Color.DARK_GRAY);
        previousButton.setForeground(Color.WHITE);
       // previousButton.setSize(new Dimension(10,10));

        JButton nextButton = new JButton(">");
        
        nextButton.setBackground(Color.DARK_GRAY);
        nextButton.setForeground(Color.WHITE);

        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Color.RED);
        

        quitButton.setBackground(Color.BLUE);
        quitButton.setForeground(Color.WHITE);
        //quitButton.setPreferredSize(new Dimension(10,10));
        
        JButton delete_Button = new JButton("Delete");

        

     //   delete_Button.setBackground(Color.RED);
        delete_Button.setForeground(Color.RED);


		/*
		 * This is where CONTROLLER comes into play and is used for main view.
		 * The model is used by adding or
		 * subtracting a day, creating event and exiting the application.
		 */

        quitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                System.exit(0);   // terminates the program
            }
        });

        delete_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                for (int i = 0; i< events_list.size(); i++) {
                    Event e = events_list.get(i);
                    model.clear_Event(e);
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	model.next_Day();
                
            }
        });

        createButton.addActionListener(new ActionListener() {

            @Override    // overriding the action performed method
            public void actionPerformed(ActionEvent e) {
                CreateEventView eve = new CreateEventView(model);

            }
        });

        previousButton.addActionListener(new ActionListener() {

            @Override    // overriding the action performed method
            public void actionPerformed(ActionEvent e) {
            	model.previous_Day();
            }

        });
        agendaButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JFrame frame = new JFrame();
        		JPanel panelAgenda = new JPanel();
        		JTextField startAgenda = new JTextField("Enter start of agenda (MM/DD/YYYY)");
        		JTextField endAgenda = new JTextField("Enter end of agenda (MM/DD/YYYY)");
        		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
        		JButton okButton = new JButton("Search");
                JButton cancelButton = new JButton("Cancel");
                
                cancelButton.addActionListener(new ActionListener() {
                
                    
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                okButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		try {
        					Date start1 = sdf.parse(startAgenda.getText());
        					Date end1 = sdf.parse(endAgenda.getText());
        					GregorianCalendar startCal = (GregorianCalendar) Calendar.getInstance();
        					GregorianCalendar endCal = (GregorianCalendar) Calendar.getInstance();
        					startCal.setTime(start1);
        					endCal.setTime(end1);
        					ArrayList<Event> current_EventsList = model.getEvents();
        					for (int i = 0; i < events_list.size(); i++) {
        						Event e1 = events_list.get(i);
        						
        						if (e1.start.get(Calendar.DAY_OF_MONTH) >= startCal.get(Calendar.DAY_OF_MONTH)) {
        							if(e1.end.get(Calendar.DAY_OF_MONTH) <= endCal.get(Calendar.DAY_OF_MONTH)) {
        										System.out.println("it works!");
        							}
        						}
        					}
        					frame.dispose();
        				} catch (ParseException e2) {
        					e2.printStackTrace();
        				}
                		/**
                		ArrayList<Event> currentEvents = model.getEvents();
                        
                        for (Event e1 : currentEvents) {
                            if (e1.start.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                                
                                Date startDate = e1.start.getTime();
                                Date endDate = e1.end.getTime();
                                
                                SimpleDateFormat sf = new SimpleDateFormat("hh:mm aa");
                                
                                panelAgenda.add(new JLabel(e1.event_Name));
                                panelAgenda.add(new JLabel(sf.format(startDate)));
                                panelAgenda.add(new JLabel(sf.format(endDate)));
                            }
                        }
                    frame.remove(day_panel);
                    frame.add(panelAgenda);
                    frame.setVisible(true);
                    */
                		
                	}
                	
                });
                dayButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		//CardLayout cl = 
                		//frame.add(day_panel);
                		//frame.remove(timePanel);
                		//frame.setVisible(true);
                	}
                });
                monthButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		frame.remove(day_panel);
                		frame.revalidate();
                		frame.repaint();
                	}
                });
                weekButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		JPanel weekPanel = new JPanel();
                        for (int i = 0; i < 7; i++) {
                        	top_DayView = new JLabel(DAYS[cal.get(Calendar.DAY_OF_WEEK) - i] + " " + (cal.get(Calendar.MONTH) + i) + "/"
                                + cal.get(Calendar.DAY_OF_MONTH), SwingConstants.CENTER);
                        	top_DayView.setBackground(Color.WHITE);
                        }
                	}
                });
        		
        		
        		panelAgenda.add(startAgenda);
        		panelAgenda.add(endAgenda);
        		panelAgenda.add(okButton);
        		panelAgenda.add(cancelButton);
        		frame.add(panelAgenda);
        		frame.pack();
                frame.setVisible(true);
        	}
        });
     
        // Adds buttons to button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 8, 5, 5));
        buttonPanel.add(todayButton);
        buttonPanel.add(monthprevButton);
        buttonPanel.add(monthnextButton);
        buttonPanel.add(Box.createHorizontalStrut(25));
        buttonPanel.add(dayButton);
        buttonPanel.add(weekButton);
        buttonPanel.add(agendaButton);
        buttonPanel.add(monthButton);
        buttonPanel.add(createButton);
        buttonPanel.add(previousButton);

        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createHorizontalStrut(25));

        buttonPanel.add(delete_Button);
        buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        buttonPanel.add(fileButton);
        buttonPanel.add(quitButton);
      //  buttonPanel.setForeground(Color.RED);

        buttonPanel.setBackground(Color.LIGHT_GRAY);

//        JPanel panel = new JPanel();
//
//        panel.add(createButton);
//        panel.setBackground(Color.RED);

      // buttonPanel.add(new JLabel("\n"));


        // Sets up month panel and calls draw method to fill in
        // the default data data
        
        month_panel = new JPanel();
        month_panel.setLayout(new GridLayout(0, 7, 5, 5));
        month_panel.setBorder(new EmptyBorder(0, 10, 0, 0));
        month_panel.setBackground(Color.WHITE);

        JPanel monthWrap = new JPanel();
        monthWrap.setLayout(new BoxLayout(monthWrap, BoxLayout.Y_AXIS));
        //monthWrap.add(month_label);
        Box b = Box.createHorizontalBox();
        monthWrap.add(b);
        b.add(previousButton);
        b.add(month_label);
        b.add(nextButton);
        //monthWrap.add(previousButton);
        //monthWrap.add(nextButton);
        monthWrap.add(month_panel);
        monthWrap.setBackground(Color.WHITE);
        draw_MonthView(month_panel);

        // Sets up day view and puts in a scroll pane
        JScrollPane scroll = new JScrollPane();
        day_panel = new JPanel();
        timePanel = new JPanel();
        day_panel.setLayout(new BoxLayout(day_panel, BoxLayout.PAGE_AXIS));
        day_panel.setBackground(Color.WHITE);
        day_panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        day_panel.setLayout(new GridLayout(48, 1, 0, 0));
        JPanel w = new JPanel();
        w = new JPanel(new BorderLayout());
        w.add(timePanel, BorderLayout.WEST);
        w.add(day_panel, BorderLayout.CENTER);
        w.add(top_DayView, BorderLayout.NORTH);
        drawDay(day_panel);

        scroll.getViewport().add(w);
        scroll.setPreferredSize(new Dimension(550, 200));
        scroll.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);


        // Adds all panels to frame and sets up frame
        frame = new JFrame();
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(monthWrap, BorderLayout.WEST);
        frame.add(scroll, BorderLayout.EAST);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * draws the event window in the Calendar application
     *
     * @param dayPanel,
     *            everything related to the particular day is attached to the
     *            day panel (right part)
     */
    
    public void drawDay(JPanel dayPanel) {
        for (int i = 0; i < 48; i++) {
            EventField event_field = new EventField(30);
            event_field.setBackground(Color.WHITE);
            event_field.setEditable(false);

            dayPanel.add(event_field);
            event_array[i] = event_field;
        }

        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBackground(Color.WHITE);
        timePanel.setLayout(new GridLayout(24, 1, 0, 0));

        Calendar c = new GregorianCalendar();
        int sm = c.getActualMaximum(Calendar.HOUR_OF_DAY);
        int s = c.getActualMinimum(Calendar.HOUR_OF_DAY);

        for (int i = 0; i < 24; i++)

        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(dayPanel, BoxLayout.PAGE_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setLayout(new GridLayout(2, 1, 0, 0));
            jTextField = new JTextField(5);
            jTextField.setEditable(false);

            int hour = i;
            String duration = "PM";
            if (hour < 12) {
                duration = "AM";
            }
            if (hour == 0) {
                hour += 12;
            }
            if (hour > 12) {
                hour -= 12;
            }
            jTextField.setText("" + hour + duration);

            jTextField.setHorizontalAlignment(JTextField.RIGHT);
            jTextField.setBorder(null);

            JTextField jtf = new JTextField(5);
            panel.add(jTextField);
            jtf.setBorder(null);
            jtf.setEditable(false);
            panel.add(jtf);
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            timePanel.add(panel);
        }

        ArrayList<Event> current_EventsList = model.getEvents();

        for (int i = 0; i < events_list.size(); i++) {

            Event e = current_EventsList.get(i);
            if (e.start.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {

                Date startDate = e.start.getTime();
                Date endDate = e.end.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");

                SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
                String starting_hour = sdf1.format(startDate);
                int startHour = Integer.parseInt(starting_hour);

                SimpleDateFormat hh = new SimpleDateFormat("mm");
                String start_time = hh.format(startDate);
                int start_min = Integer.parseInt(start_time);

                String end_time = sdf.format(endDate);


                int index = startHour * 2;
                if (start_min >= 30) {
                    index++;
                }
                event_array[index].setText(e.event_Name + " " + sdf.format(startDate) + " - " + sdf.format(endDate));
                event_array[index].setEvent(e);

                index++;
                // if(end_time != null)
                {
                    String time_endHour = sdf1.format(endDate);
                    int end_hour = Integer.parseInt(time_endHour);

                    while (index / 2 < end_hour) {
                        event_array[index].setText(e.event_Name + " " + sdf.format(startDate) + " - " + sdf.format(endDate));
                        event_array[index].setEvent(e);
                        index++;
                    }
                    {
                        event_array[index].setText(e.event_Name + " " + sdf.format(startDate) + " - " + sdf.format(endDate));
                        event_array[index].setEvent(e);
                        index++;
                    }
                    {
                        event_array[index].setText(e.event_Name + " " + sdf.format(startDate) + " - " + sdf.format(endDate));
                        event_array[index].setEvent(e);
                        index++;
                    }
                }

            }
        }

    }
    
    public void deleteEvent()
    {
        int index = 0;

    }

    /**
     * This forces the repainting of all variable items in main view. It starts
     * by removing all previous items then redrawing with updated data
     */
    public void repaint() {
        month_panel.removeAll();
        draw_MonthView(month_panel);
        month_panel.revalidate();
        month_panel.repaint();
        top_DayView.revalidate();
        top_DayView.repaint();
        
        day_panel.removeAll();
        timePanel.removeAll();
        drawDay(day_panel);
        day_panel.revalidate();
        day_panel.repaint();

    }

    /**
     * Takes a panel and populates it with the month set in the data model.
     *
     * @param monthPanel,
     *            everything related to the monthly calendar is attached to
     *            month panel( left part)
     */
    private void draw_MonthView(JPanel monthPanel) {

        month_label.setText(new SimpleDateFormat("MMM yyyy").format(cal.getTime()));
        
        // Add Week Labels at top of Month View
        String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        for (int i = 0; i < 7; i++) {


            JLabel day = new JLabel(daysOfWeek[i]);
            day.setPreferredSize(new Dimension(35,35));

            monthPanel.add(day);
        }
        // The code below all the days in month

        int month_days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar beginning_date = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        int start_day = beginning_date.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i < month_days + start_day; i++) {

            if (i < start_day) {
                final JLabel day = new JLabel("");

                monthPanel.add(day);
            } else {
                int dayNumber = i - start_day + 1;
                final JLabel day = new JLabel(dayNumber + "");
                day.addMouseListener(new MouseListener() {

                    // CONTROLLER updates the model on the currently looked day

                    /**
                     * Overriding the mouseClicked method
                      * @param e
                     */
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int num = Integer.parseInt(day.getText());
                        model.set_Day(num);

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                if (dayNumber == cal.get(Calendar.DAY_OF_MONTH)) {
                    day.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                }
                monthPanel.add(day);
            }
        }
    }

}