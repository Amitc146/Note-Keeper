/**
 * This class creates the main panel of the app, which contains
 * all of the graphic components of the calendar.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CalendarPanel extends JPanel implements ActionListener {

    private Calendar calendar;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private DatePanel datePanel;
    private JTextArea textArea;
    private JButton saveButton;
    private JButton showButton;
    private JLabel currentFile;
    private JLabel message;


    public CalendarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        addMenuBar();
        addDatePanel();
        addTextPanel();
        addButtons();
        addLabels();
    }


    public void actionPerformed(ActionEvent e) {

        // Open File
        if (e.getSource() == openFile) {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile();
                if (isValidFileExtension(chosenFile)) {
                    calendar = new Calendar(chosenFile.getAbsolutePath());
                    textArea.setText("");
                    currentFile.setText("Current File: " + chosenFile.getName());
                    message.setText("File Loaded: " + chosenFile.getName());
                } else {
                    message.setText("");
                    JOptionPane.showMessageDialog(null, "Only '.txt' files are allowed.",
                            null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // New File
        else if (e.getSource() == newFile) {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile();
                if (chosenFile.exists()) {
                    JOptionPane.showMessageDialog(null, "File name already exists.",
                            null, JOptionPane.ERROR_MESSAGE);
                } else if (isValidFileExtension(chosenFile)) {
                    calendar = new Calendar(chosenFile.getAbsolutePath());
                    textArea.setText("");
                    currentFile.setText("Current File: " + chosenFile.getName());
                    message.setText("File Created: " + chosenFile.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "Only '.txt' files are allowed.",
                            null, JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            if (calendar == null)
                message.setText("No file opened");
            else {
                try {
                    Date d = datePanel.getDate();

                    // Save Button
                    if (e.getSource() == saveButton) {
                        try {
                            calendar.add(d, textArea.getText());
                            message.setText("Saved");
                        } catch (DateAlreadyExistsException alreadyExistException) {
                            try {
                                calendar.edit(d, textArea.getText());
                                message.setText("Saved");
                            } catch (DateDoesntExsitsException doesntExistException) {
                                message.setText("Error: failed to save");
                            }
                        }
                    }

                    // Show Button
                    else if (e.getSource() == showButton) {
                        try {
                            textArea.setText(calendar.get(d));
                        } catch (DateDoesntExsitsException doesntExistException) {
                            textArea.setText("");
                        } finally {
                            message.setText("");
                        }
                    }

                } catch (InvalidDateException invalidException) {
                    message.setText("Invalid date");
                    textArea.setText("");
                }
            }
        }
    }


    private void addMenuBar() {
        JPanel menuBarPanel = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");

        newFile.addActionListener(this);
        openFile.addActionListener(this);

        fileMenu.add(newFile);
        fileMenu.add(openFile);

        menuBarPanel.setLayout(new BorderLayout());
        menuBar.add(fileMenu);
        menuBar.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        menuBarPanel.add(menuBar);

        add(menuBarPanel);
    }


    private void addDatePanel() {
        datePanel = new DatePanel();
        add(Box.createRigidArea(new Dimension(0, 15)));
        datePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(datePanel);
    }


    private void addTextPanel() {
        JPanel textPanel = new JPanel();
        textArea = new JTextArea(12, 30);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scroll = new JScrollPane(textArea);
        textPanel.add(scroll);
        add(textPanel);
    }


    private void addButtons() {
        JPanel buttonPanel = new JPanel();

        saveButton = new JButton("Save Reminder");
        showButton = new JButton("Show Reminder");
        saveButton.addActionListener(this);
        showButton.addActionListener(this);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(showButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        add(buttonPanel);
    }


    private void addLabels() {
        JPanel filePanel = new JPanel();
        JPanel messagePanel = new JPanel();

        message = new JLabel();
        currentFile = new JLabel();

        message.setPreferredSize(new Dimension(300, 15));
        currentFile.setPreferredSize(new Dimension(300, 15));

        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.LINE_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        messagePanel.add(message);

        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));
        filePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        filePanel.add(currentFile);

        add(filePanel);
        add(messagePanel);
    }


    // checks if a file extension is '.txt'
    private boolean isValidFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return (fileName.substring(fileName.lastIndexOf(".") + 1).equals("txt"));
        return false;
    }


}
