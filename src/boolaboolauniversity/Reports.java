/*
 * This class creates a "General concept" of the reports, we can use this class
 * to give the user financial information about a student, or the schedule
 * of that student. The menus options are determine by a number set in the menu.
 * If that number is set to 1 this class would behave of one way, if is 2 it
 * will behave differently. The reason for me to do that is to save lines of code,
 * instead of creating two different clases, one for each option, I create one,
 * for both of the options.
 */
package boolaboolauniversity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

public class Reports {

    //String for the URL
    static final String Database = "jdbc:mysql://localhost:3306/bbu";
    //JList where all the infromarmation wil be displayed.
    final JList cList = new JList();
    //We add the list to a scrollpane so the list can be scrobable.
    JScrollPane scrollPane = new JScrollPane(cList);
    JPanel panel = new JPanel();
    JTextField ssField = new JTextField();
    //JButtons.
    JButton search = new JButton("Search", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\user_16.png"));
    JButton Clear = new JButton("Clear", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\document_16.png"));
    Border Border = LineBorder.createBlackLineBorder();
    //JLabels
    JLabel total = new JLabel("Total >>");
    JLabel credits = new JLabel("Credits:");
    JLabel cost = new JLabel("Cost:");
    JLabel totalcost = new JLabel("$0");
    JLabel totalcredits = new JLabel("0");
    JLabel info = new JLabel("Please Search for a student");
    JLabel Associate = new JLabel();
    JLabel Year = new JLabel();
    JLabel student = new JLabel("Student:");
    JLabel searchedStudent = new JLabel();
    JLabel ssLabel = new JLabel("Social Securiy:");
    JLabel courses = new JLabel();
    //data that is going to be deisplayed in the list.
    DefaultListModel data = new DefaultListModel();
    //Variable that determines which option is being pressed.
    int RECEIVABLE_OR_CLASSCHEDULE;
    String[] NCcourses = new String[50];

    public Reports(JFrame frame) {

        //this two labels display the associate and yea.
        Associate.setVisible(false);
        Year.setVisible(false);


        /*
         * Search button is the one used to search for a person by the
         * social security in the table student(table where all the registered
         * classes are). If the person is not found, it will return an Error message
         * saying that it didn't find the person with that social security.
         * Otherwise it will print the information found on the table studen.
         */
        search.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int iLine = 0, iLinecourses = 0, iTotalCost = 0,
                        iCost = 0, iClases = 0, iNCCourses = 0;
                String newline;
                String[] Courses = new String[100];
                String[] NCcourses = new String[100];

                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(Database, "santi", "santi");
                    Statement statement = connection.createStatement();
                    ResultSet resultset = statement.executeQuery("SELECT SS,CNumber,Day,Time FROM student ORDER BY CNumber ");

                    ResultSetMetaData metaData = resultset.getMetaData();
                    int nColumns = metaData.getColumnCount();
                    data.clear();
                    String line = "";

                    //Let's start all over again.
                    resultset.beforeFirst();
                    /*
                     * The next While-loop is to read all the contentes from
                     * the student table, save it into the data variable
                     * and then display it.
                     * One of the rules for databases is that
                     * you don't save what it can be calculated. So That's why
                     * I Didn't save it.
                     *
                     */
                    while (resultset.next()) {
                        if (resultset.getObject(2).toString().charAt(0) != 'N') {
                            iClases++;
                        } else {
                            iNCCourses += 150;
                        }
                        line = (String) resultset.getObject(1);
                        if (ssField.getText().equals(line)) {
                            line = "";
                            for (int y = 2; y <= 4; y++) {
                                line += (String) resultset.getObject(y) + "    ";
                            }
                            if (line.charAt(0) != 'N') {
                                Courses[iLinecourses++] = line;
                            } else {
                                if (RECEIVABLE_OR_CLASSCHEDULE == 1) {
                                    line += "= $150";
                                }
                                NCcourses[iLine++] = line;
                            }
                            line = "";
                        }
                    }
                    iCost = (iClases > 3) ? 265 : 285;
                    //Credit courses are printed first so we add a string that anounces.
                    data.addElement("-----------------------------------Credit Courses-----------------------------------");
                    for (int h = 0; h < Courses.length; h++) {
                        if (Courses[h] == null) {
                            break;
                        }
                        if (RECEIVABLE_OR_CLASSCHEDULE == 1) {
                            Courses[h] += "= $" + iCost;
                        }
                        data.addElement(Courses[h]);
                    }

                    data.addElement("---------------------------------Non-Credit Courses---------------------------------");
                    for (int h = 0; h < NCcourses.length; h++) {
                        if (NCcourses[h] != null) {
                            data.addElement(NCcourses[h]);
                        }
                    }
                    cList.setModel(data);
                    iTotalCost = iCost * iClases + 5 + iNCCourses;
                    iClases *= 3;
                    totalcost.setText(Integer.toString(iTotalCost));
                    totalcredits.setText(Integer.toString(iClases));

                    /*
                     * The next try-Catch is to determine the name of the
                     * person that was looked for by the social security.
                     * And also determine if the perosn is matriculated or not,
                     * if is matriculated in what year is matriculated.
                     */
                    try {

                        Class.forName("com.mysql.jdbc.Driver");
                        connection = DriverManager.getConnection(Database, "santi", "santi");
                        statement = connection.createStatement();
                        resultset = statement.executeQuery("SELECT Name, SS, associate,year FROM regStudents");
                        metaData = resultset.getMetaData();
                        nColumns = metaData.getColumnCount();
                        boolean found = false;
                        line = "";
                        while (resultset.next()) {
                            for (int i = 1; i <= nColumns; i++) {
                                line = "";
                                line = (String) resultset.getObject(i);
                                if (ssField.getText().equals(line)) {
                                    searchedStudent.setText((String) resultset.getObject(i - 1));
                                    if (RECEIVABLE_OR_CLASSCHEDULE == 2) {
                                        Associate.setText((String) resultset.getObject(i + 1));
                                        line = (String) resultset.getObject(i + 2);
                                        newline = (line.equals("Select Year")) ? "No Year" : line;
                                        Year.setText(newline);
                                    }
                                    found = true;
                                    return;
                                }
                            }
                        }
                        //Error message that is going to display if the social security
                        //is not found in the database.
                        if (!found) {
                            JOptionPane.showOptionDialog(null, "The student was not found\nPlease try it again",
                                    "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                            reSet();
                            return;
                        }
                        statement.close();
                        connection.close();
                        resultset.close();
                    } catch (SQLException te) {
                        System.out.println("SQL Exception: " + te.toString());
                    } catch (ClassNotFoundException cE) {
                        System.out.println("Class Not Found Exception: " + cE.toString());
                    }
                    connection.close();
                    resultset.close();
                    statement.close();
                } catch (SQLException te) {
                    System.out.println("SQL Exception: " + te.toString());
                } catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                }
            }
        });
        Clear.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                reSet();
            }
        });

        panel.add(scrollPane);

        search.setBounds(250, 90, 95, 20);
        info.setBounds(115, 30, 230, 20);
        ssField.setBounds(115, 90, 130, 20);
        ssLabel.setBounds(25, 90, 100, 20);
        student.setBounds(63, 120, 75, 20);
        searchedStudent.setBorder(Border);
        searchedStudent.setBounds(115, 120, 210, 20);
        courses.setBounds(25, 170, 350, 10);
        scrollPane.setBounds(25, 190, 390, 180);
        Clear.setBounds(300, 430, 95, 20);
        total.setBounds(140, 380, 85, 20);
        credits.setBounds(200, 380, 45, 20);
        totalcredits.setBorder(Border);
        totalcredits.setBounds(245, 380, 35, 20);
        cost.setBounds(300, 380, 85, 20);
        totalcost.setBorder(Border);
        totalcost.setBounds(330, 380, 85, 20);
        Associate.setBounds(25, 142, 300, 20);
        Associate.setBorder(Border);
        Year.setBounds(290, 165, 120, 20);
        Year.setBorder(Border);


        frame.add(scrollPane);
        frame.add(courses);
        frame.add(ssLabel);
        frame.add(ssField);
        frame.add(search);
        frame.add(student);
        frame.add(searchedStudent);
        frame.add(Associate);
        frame.add(Year);
        frame.add(Clear);
        frame.add(cost);
        frame.add(credits);
        frame.add(total);
        frame.add(totalcost);
        frame.add(totalcredits);
        frame.add(info);
    }

    public void setVisible(boolean visibility) {
        scrollPane.setVisible(visibility);
        courses.setVisible(visibility);
        ssField.setVisible(visibility);
        ssLabel.setVisible(visibility);
        search.setVisible(visibility);
        student.setVisible(visibility);
        searchedStudent.setVisible(visibility);
        Clear.setVisible(visibility);
        credits.setVisible(visibility);
        cost.setVisible(visibility);
        total.setVisible(visibility);
        totalcredits.setVisible(visibility);
        totalcost.setVisible(visibility);
        info.setVisible(visibility);
    }

    public void reSet() {
        totalcost.setText(null);
        totalcredits.setText(null);
        searchedStudent.setText(null);
        ssField.setText(null);
        cList.setModel(new DefaultListModel());
        search.setVisible(true);
        Associate.setText(null);
        Year.setText(null);
    }

    public void setOption(int x) {
        RECEIVABLE_OR_CLASSCHEDULE = x;

    }
}
