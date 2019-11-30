/**
 * Creates a panel for the date selection on the main panel.
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class DatePanel extends JPanel {
    private JLabel message;
    private JComboBox day;
    private JComboBox month;
    private JComboBox year;

    private final String LENGTH_PROTO_TYPE = "AAAAAAAA";        // used to fix the size of the date combo boxes


    public DatePanel() {
        addMessage();
        addDays();
        addMonths();
        addYears();
    }


    public Date getDate() throws InvalidDateException {
        return new Date(getDay(), getMonth(), getYear());
    }


    public int getDay() throws InvalidDateException {
        if (day.getSelectedIndex() < 0)
            throw new InvalidDateException();
        return Integer.parseInt(day.getSelectedItem().toString());
    }


    public int getMonth() throws InvalidDateException {
        if (month.getSelectedIndex() < 0)
            throw new InvalidDateException();
        return month.getSelectedIndex() + 1;
    }


    public int getYear() throws InvalidDateException {
        if (year.getSelectedIndex() < 0)
            throw new InvalidDateException();
        return Integer.parseInt(year.getSelectedItem().toString());
    }


    private void addMessage() {
        message = new JLabel("Date: ");
        add(message);
    }


    // Adds days combobox
    private void addDays() {
        day = new JComboBox();
        day.setPrototypeDisplayValue(LENGTH_PROTO_TYPE);
        day.setRenderer(new PromptComboBoxRenderer("Day"));
        for (int i = Date.MIN_VALUE; i <= Date.MAX_DAYS; i++)
            day.addItem(Integer.toString(i));
        day.setSelectedIndex(-1);
        add(day);
    }


    // Adds months combobox
    private void addMonths() {
        String names[] = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        month = new JComboBox();
        month.setPrototypeDisplayValue(LENGTH_PROTO_TYPE);
        month.setRenderer(new PromptComboBoxRenderer("Month"));
        for (int i = Date.MIN_VALUE; i <= Date.MAX_MONTHS; i++)
            month.addItem(names[i - 1]);
        month.setSelectedIndex(-1);
        add(month);
    }


    // Adds years combobox
    private void addYears() {
        year = new JComboBox();
        year.setPrototypeDisplayValue(LENGTH_PROTO_TYPE);
        year.setRenderer(new PromptComboBoxRenderer("Year"));
        for (int i = Date.MIN_YEAR; i <= Date.MAX_YEAR; i++)
            year.addItem(Integer.toString(i));
        year.setSelectedIndex(-1);
        add(year);
    }


    // Private class to add prompt to the date combo boxes
    private class PromptComboBoxRenderer extends BasicComboBoxRenderer {
        private String prompt;

        public PromptComboBoxRenderer(String prompt) {
            this.prompt = prompt;
        }


        public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value == null)
                setText(prompt);

            return this;
        }
    }


}
