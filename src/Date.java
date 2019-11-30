import java.util.Objects;

public class Date {

    private int day;
    private int month;
    private int year;


    // Constants to check if a date is valid
    public static final int MIN_VALUE = 1;
    public static final int MAX_DAYS = 31;
    public static final int MAX_MONTHS = 12;
    public static final int MAX_YEAR = 2050;
    public static final int MIN_YEAR = 2010;


    public Date(int day, int month, int year) throws InvalidDateException {
        if (!isValidDate(day, month, year))
            throw new InvalidDateException();
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public Date() {
        super();
    }


    public int getDay() {
        return day;
    }


    public void setDay(int day) {
        this.day = day;
    }


    public int getMonth() {
        return month;
    }


    public void setMonth(int month) {
        this.month = month;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return day == date.day &&
                month == date.month &&
                year == date.year;
    }


    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }


    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }


    private static boolean isValidDate(int d, int m, int y) {

        if ((y > MAX_YEAR || y < MIN_YEAR)
                || (m < MIN_VALUE || m > MAX_MONTHS)
                || (d < MIN_VALUE || d > MAX_DAYS))
            return false;

        if (m == 2) {
            if (isLeap(y))
                return (d <= 29);
            else
                return (d <= 28);
        }

        if (m == 4 || m == 6 || m == 9 || m == 11)
            return (d <= 30);

        return true;
    }


    private static boolean isLeap(int year) {
        return (((year % 4 == 0) &&
                (year % 100 != 0)) ||
                (year % 400 == 0));
    }


}
