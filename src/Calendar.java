/**
 * Represents a calendar which is implemented with a hash table, such that the keys
 * are the dates, and the values are the reminders saved for each date.
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Calendar {

    private HashMap<Date, String> map;
    private File fileName;


    public Calendar(String name) {
        try {
            openFile(name);
            this.map = new HashMap<>();
            loadCalendar();
        } catch (IOException exception) {
            System.exit(1);
        }
    }


    public void add(Date date, String text) throws DateAlreadyExistsException {
        if (!map.containsKey(date)) {
            map.put(date, text);
            saveCalendar();
        } else
            throw new DateAlreadyExistsException();
    }


    public void edit(Date date, String text) throws DateDoesntExsitsException {
        if (map.containsKey(date)) {
            map.replace(date, text);
            saveCalendar();
        } else
            throw new DateDoesntExsitsException();
    }


    public String get(Date date) throws DateDoesntExsitsException {
        if (map.containsKey(date))
            return map.get(date);
        else
            throw new DateDoesntExsitsException();
    }


    public void remove(Date date) throws DateDoesntExsitsException {
        if (map.containsKey(date))
            map.remove(date);
        else
            throw new DateDoesntExsitsException();
    }


    // Save the map to the file
    private void saveCalendar() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fos);

            for (Map.Entry<Date, String> m : map.entrySet()) {
                pw.println(m.getKey() + "=" + m.getValue());
            }

            pw.flush();
            pw.close();
            fos.close();
        } catch (IOException exception) {
        }
    }


    // Load a map from the file
    private void loadCalendar() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Scanner scanner = new Scanner(fis);

            String currentLine;

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                StringTokenizer st = new StringTokenizer(currentLine, "=", false);
                Date d = convertStringToDate(st.nextToken());
                String text = st.nextToken();
                map.put(d, text);
            }
            fis.close();

        } catch (Exception exception) {
        }
    }


    // Initializes the file that is used for read/write
    private void openFile(String name) throws IOException {
        File f = new File(name);

        if (!f.exists()) {
            if (!f.createNewFile())
                throw new IOException("Failed to open file");
        }
        this.fileName = f;
    }


    // Converts a string of the format 'dd/mm/yyyy' to a 'Date' object
    private Date convertStringToDate(String s) throws InvalidDateException {
        StringTokenizer st = new StringTokenizer(s, "/", false);
        Date d;

        try {
            d = new Date(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
        } catch (InvalidDateException exception) {
            throw new InvalidDateException();
        }

        return d;
    }


}