/*
 *  Full_Time class
 */
package boolaboolauniversity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class Full_Time implements ListSelectionListener {

    static final String Database = "jdbc:mysql://localhost:3306/bbu";
    final JList cList = new JList();
    JScrollPane scrollPane = new JScrollPane(cList);
    JPanel panel = new JPanel();
    JTextField ssField = new JTextField();
    JButton search = new JButton("Search", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\user_16.png"));
    JButton register = new JButton("Register");
    JButton Cancel = new JButton("Cancel", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\block_16.png"));
    Border Border = LineBorder.createBlackLineBorder();
    JLabel courses = new JLabel();
    JLabel ssLabel = new JLabel("Social Securiy:");
    JLabel student = new JLabel("Student:");
    JLabel searchedStudent = new JLabel();
    JLabel total = new JLabel("Total >>");
    JLabel credits = new JLabel("Credits:");
    JLabel cost = new JLabel("Cost:");
    JLabel totalcost = new JLabel("$0");
    JLabel totalcredits = new JLabel("0");
    JLabel info = new JLabel("Please Search for a student");
    int FULLPART_TIME;
    DefaultListModel data = new DefaultListModel();
    final String[] Day = new String[10];
    final String[] Time = new String[10];

    public Full_Time(JFrame frame) {
        /*
         * Search Button looks for a student in the regStudent table by the social security.
         * If student is not found an error message will pump out. Otherwise the
         * name of the student will be displayed on the frame.
         */
        search.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {

                    Statement statement = null;
                    Connection connection = null;
                    ResultSet resultset = null;
                    Class.forName("com.mysql.jdbc.Driver");
                    String text2 = "";
                    connection = DriverManager.getConnection(Database, "santi", "santi");
                    statement = connection.createStatement();
                    resultset = statement.executeQuery("SELECT  Name, LastName,Street, city,ss,zip,State,date,year,associate FROM regStudents");
                    ResultSetMetaData metaData = resultset.getMetaData();
                    text2 = "";

                    /*Pseudo code:
                     * while(there is another line to read)
                     *          string text2 instance gets the value of the current field of the social security column.
                     *             if the number you are looking for is in the table
                     *                     display the number of the person found in JLabel.
                     *                          and finish.
                     *
                     */
                    while (resultset.next()) {
                        text2 += resultset.getString(5);
                        if (text2.equals(ssField.getText())) {
                            searchedStudent.setText(resultset.getString(1));
                            return;
                        }
                        text2 = "";

                    }
                    //Error message displayed if the person is not found.
                    JOptionPane.showOptionDialog(null, "The student was not found\nPlease try it again",
                            "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    ssField.setText("");
                    connection.close();
                    statement.close();
                    resultset.close();

                } catch (SQLException ent) {
                    System.out.println("SQL Exception: " + ent.toString());
                } catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                }
            }
        });

        courses.setText("Number                 Name           Day              Time           Room");

        panel.add(scrollPane);
        cList.addListSelectionListener(this);
        info.setBounds(115, 30, 230, 20);
        ssField.setBounds(115, 90, 130, 20);
        ssLabel.setBounds(25, 90, 100, 20);
        search.setBounds(250, 90, 95, 20);
        student.setBounds(63, 120, 75, 20);
        searchedStudent.setBorder(Border);
        searchedStudent.setBounds(115, 120, 210, 20);
        courses.setBounds(25, 170, 350, 10);
        scrollPane.setBounds(25, 190, 390, 180);
        register.setBounds(220, 430, 85, 20);
        Cancel.setBounds(300, 430, 95, 20);
        total.setBounds(140, 380, 85, 20);
        credits.setBounds(200, 380, 45, 20);
        totalcredits.setBorder(Border);
        totalcredits.setBounds(245, 380, 35, 20);
        cost.setBounds(300, 380, 85, 20);
        totalcost.setBorder(Border);
        totalcost.setBounds(330, 380, 85, 20);

        frame.add(scrollPane);
        frame.add(courses);
        frame.add(ssLabel);
        frame.add(ssField);
        frame.add(search);
        frame.add(student);
        frame.add(searchedStudent);
        frame.add(register);
        frame.add(Cancel);
        frame.add(cost);
        frame.add(credits);
        frame.add(total);
        frame.add(totalcost);
        frame.add(totalcredits);
        frame.add(info);

        /*
         * Cancel button is a "regret" button. If you don't want to
         * register a person but you already looked for it in the data
         * base, the cancel button would give back the default attributes.
         * And then look for antoher person if you wish.
         */
        Cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                reSet();
            }
        });
        /*
         * The register button  save the name of the courses that the student is
         * registering for. This courses would be save in the student table of the
         * bbu database.
         */
        register.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int iCredit = Integer.parseInt(totalcredits.getText());
                String MSG = "";
                /* Depending on what kind of option the user chose (1: Full Time
                 * 2:Par Time 3: Non-credit) this switch statement would pump out
                 * a different error message.
                 */
                switch (FULLPART_TIME) {
                    case 1:

                        if (iCredit < 9) {
                            MSG += "- Full-Time students should register\nfor at least 9 credits\n";
                        }

                        break;
                    case 2:

                        if (iCredit > 6) {
                            MSG += "- Part-Time students should register\nfor 6 credits or less\n";
                        }
                        break;
                    case 3:

                        if (iCredit > 0) {
                            MSG += "- You would have to choose\nNon-credit courses\n";
                        }
                }
                if (cList.isSelectionEmpty()) {
                    MSG += "- You would have to choose\nat least one course\n";
                }
                if (ssField.getText().equals("")) {
                    MSG += "- Look for a student";
                }
                if (!MSG.equals("")) {
                    JOptionPane.showOptionDialog(null, "You forgot to provide the following Information:\n\n" + MSG,
                            "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return;
                }
                try {
                    Statement statement = null;
                    Connection connection = null;
                    ResultSet resultset = null;
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(Database, "santi", "santi");
                    statement = connection.createStatement();
                    Object[] values = cList.getSelectedValues();
                    String[] iRegDay = new String[10];
                    int iRow = 0, iTimein1 = 0, iTimeout1 = 0, iTimein2 = 0, iTimeout2 = 0;

                    resultset = statement.executeQuery("SELECT SS,Day, Time from student");

                    //Let's start from the begining.
                    resultset.beforeFirst();

                    /*Pseudo code:
                     *  while(there is another line to read)
                     *      Array of String(iRegDay) gets of the days of the schedule from the clases selected
                     *          iTimein1,iTimeout2,iTimein2,iTimeout2 get the hours of the database and selected items.
                     *              note: this is done to see if a course can be chose by the schedule if not an error message.
                     *
                     *      If the S.S. number looked by the user equals to the current field in the S.S. column of the studen table.
                     *          If the days in the data base of the student are the same from the days chose from the list.
                     *             if timein1 is grater or equal to timin2 and timein1 is smaller than timeout2 OR viceversa.
                     *                  An error message would pump out saying that you already chose a class with the same schedule.
                     *                  note: If the schedule is in between the hours of another class  a message error will pump out.
                     *                        This will not allow the user to register a student for two courses with the same schedule.
                     *                         
                     */
                    while (resultset.next()) {

                        iRegDay[iRow] = Day[cList.getSelectedIndex()];

                        iTimein1 = Integer.parseInt(String.valueOf(Time[cList.getSelectedIndex()].charAt(0)));
                        iTimeout1 = Integer.parseInt(String.valueOf(Time[cList.getSelectedIndex()].charAt(5)));
                        iTimein2 = Integer.parseInt(String.valueOf(resultset.getObject(3).toString().charAt(0)));
                        iTimeout2 = Integer.parseInt(String.valueOf(resultset.getObject(3).toString().charAt(5)));

                        if (ssField.getText().toString().equals(resultset.getObject(1))) {
                            if (resultset.getObject(2).toString().equals(iRegDay[iRow])) {
                                if ((iTimein1 >= iTimein2 && iTimein1 < iTimeout2) || (iTimein2 >= iTimein1 && iTimein2 < iTimeout1)) {
                                    JOptionPane.showOptionDialog(null, "This student has a class in this schedule\n" + "                  please try it again!" + MSG,
                                            "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                                    return;
                                }
                            }
                        }
                        iRow++;
                    }
                    /*Pseudo code:
                     *     for(count from 0 to the value of selected items)
                     *         array of integer get the indexes of the items selected
                     *         save the selected items in the student table.
                     */
                    for (int i = 0; i < values.length; i++) {
                        int[] iselIndex = cList.getSelectedIndices();
                        statement.executeUpdate("INSERT INTO student(SS, cNumber,Day, Time)VALUES('" + ssField.getText() + "','" + values[i].toString().subSequence(0, 7) + "','" + Day[iselIndex[i]] + "','" + Time[iselIndex[i]] + "')");
                    }
                    resultset.close();
                    connection.close();
                    statement.close();
                } catch (SQLException ti) {
                    System.out.println("SQL Exception: " + ti.toString());
                } catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                }
                reSet();
                JOptionPane.showOptionDialog(null, "The student was registered succesfully",
                        "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

            }
        });

    }
    /*
     * Each time the user selects an item in the list it calculates the price
     * of the course selected. It also prints the total value of the total credits
     * and cost of the clases selected.
     */

    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();
        Object[] values = source.getSelectedValues();
        String text;
        int iCredit = 0, iCost = 0;
        if (FULLPART_TIME == 1) {
            for (int i = 0; i < values.length; i++) {
                text = (String) values[i];
                if (text.charAt(0) != 'N') {
                    iCredit += 3;
                }
                iCost = (iCredit <= 9) ? 285 : 265;
            }
            iCost *= iCredit / 3;
        } else if (FULLPART_TIME == 2) {
            for (int i = 0; i < values.length; i++) {
                text = (String) values[i];
                if (text.charAt(0) != 'N') {
                    iCredit += 3;
                }
                iCost += 300;
            }
        } else if (FULLPART_TIME == 3) {
            for (int i = 0; i < values.length; i++) {
                text = (String) values[i];
                if (text.charAt(0) == 'N') {
                    iCost += 150;
                }
            }
        }
        totalcost.setText("$" + Integer.toString(iCost + 5));
        totalcredits.setText(Integer.toString(iCredit));
    }

    public void setVisible(boolean visibility) {
        scrollPane.setVisible(visibility);
        courses.setVisible(visibility);
        ssField.setVisible(visibility);
        ssLabel.setVisible(visibility);
        search.setVisible(visibility);
        student.setVisible(visibility);
        searchedStudent.setVisible(visibility);
        Cancel.setVisible(visibility);
        register.setVisible(visibility);
        credits.setVisible(visibility);
        cost.setVisible(visibility);
        total.setVisible(visibility);
        totalcredits.setVisible(visibility);
        totalcost.setVisible(visibility);
        info.setVisible(visibility);
    }

    public void chooseTime(int x) {
        FULLPART_TIME = x;
    }

    public void reSet() {


        totalcredits.setText("0");
        totalcost.setText("$0");
        ssField.setText(null);
        searchedStudent.setText(null);
    }
    /*
     * This function does the reading of the two tables containing the credit courses
     * and Non-dredit courses of the database bbu.
     */

    public void readFile() {
        try {
            reSet();
            data.clear();
            Connection connection = null;
            Statement statement = null;
            Statement stmnt = null;
            ResultSet resultset = null;
            ResultSet rsltSet = null;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Database, "santi", "santi");
            statement = connection.createStatement();
            resultset = statement.executeQuery("SELECT Number,Name,Day, Timein,Room FROM Courses");
            stmnt = connection.createStatement();
            rsltSet = stmnt.executeQuery("SELECT Number,Name,Day, Timein,Room FROM ncCourses");

            ResultSetMetaData metaData = resultset.getMetaData();
            ResultSetMetaData mtData = rsltSet.getMetaData();

            int nColumns = metaData.getColumnCount();
            int nncColumns = mtData.getColumnCount();

            String line = "";
            int x = 1, lnth = 0;

            if (FULLPART_TIME != 3) {
                while (resultset.next()) {
                    for (int i = 1; i <= nColumns; i++) {
                        line += resultset.getObject(i) + "   ";
                        if (i == 1) {
                            Day[lnth] = (String) resultset.getObject(i + 2);
                            Time[lnth] = (String) resultset.getObject(i + 3);
                        }
                        if (x == 5) {
                            data.addElement(line);
                            line = "";
                            x = 1;
                            continue;
                        }
                        x++;
                    }
                    lnth++;
                }
            }


            x = 1;
            line = "";

            if (FULLPART_TIME == 3) {
                while (rsltSet.next()) {
                    for (int i = 1; i <= nncColumns; i++) {
                        line += rsltSet.getObject(i) + "   ";
                        if (i == 1) {

                            Day[lnth] = (String) rsltSet.getObject(i + 2);
                            Time[lnth] = (String) rsltSet.getObject(i + 3);
                        }
                        if (x == 5) {
                            data.addElement(line);
                            line = "";
                            x = 1;
                            continue;
                        }
                        x++;
                    }
                    lnth++;
                }
            }
            cList.setModel(data);
            connection.close();
            resultset.close();
            statement.close();
            stmnt.close();
            rsltSet.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }
    }
}
