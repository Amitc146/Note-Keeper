/**
 * This class is used to launch the calendar app.
 */

import javax.swing.*;

public class CalendarFrame extends JFrame {
    private CalendarPanel calendarPanel;

    public CalendarFrame() {
        calendarPanel = new CalendarPanel();
        add(calendarPanel);

        setTitle("Calendar");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
