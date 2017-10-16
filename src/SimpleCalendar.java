/**
 * Created by puneettokhi on 7/21/17.
 */
/**
 * testing the calendar app
 */

/**
 *
 * @author Puneet
 *
 */

// Tester program to test the GUI of Calendar application

// inheritance, polymorphism nd other object
// oriented concepts are used in this program

public class SimpleCalendar {



    public static void main(String[] args) {

//
//        EventStart start = new EventFiler();
//        start.loadEvent();

  //      EventModel m = new EventModel();

  //      Class c = m.getClass();
       // System.out.println(c);

     //   Method[] m = Rectangle.class.getDeclaredConstructors();

      //  Event m = m.class.getProtectionDomain();

        EventModel model_class = new EventModel();

        Calendar_View view_class = new Calendar_View(model_class);


        // Using the setView method from the model class and passing the
        // view_class parameter in accordance with Is-A relationship

        model_class.setView(view_class);


    }
}