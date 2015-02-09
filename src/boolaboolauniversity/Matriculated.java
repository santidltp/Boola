/*
 * This class is to matriculate students either for degree seeking or
 * "non-matriculated.
 */
package boolaboolauniversity;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;

public class Matriculated {

    JLabel msLabel1 = new JLabel("Please Fill out the Information.");
    JLabel nLabel = new JLabel("Name:");
    JLabel lnLabel = new JLabel("Last Name:");
    JLabel sLabel = new JLabel("Street address:");
    JLabel ssLabel = new JLabel("Social Security:");
    JLabel cLabel = new JLabel("City:");
    JLabel zLabel = new JLabel("Zip Code:");
    JLabel stLabel = new JLabel("State:");
    JLabel tLabel = new JLabel("Date:", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\calendar_16.png"), JLabel.LEFT);
    JLabel date = new JLabel();
    JLabel yLabel = new JLabel("Year:");
    JPanel panel = new JPanel();
    Border Border = LineBorder.createBlackLineBorder();
    JTextField lnField = new JTextField();
    JTextField nField = new JTextField();
    JTextField sField = new JTextField();
    JTextField cField = new JTextField();
    JTextField zField = new JTextField();
    JTextField ssField = new JTextField();
    JCheckBox hsCheck = new JCheckBox("High School");
    JCheckBox inmuneCheck = new JCheckBox("Inmunization");
    JRadioButton ascmp = new JRadioButton("Asssociate of Science in Computer Programming");
    JRadioButton aahum = new JRadioButton("Associate of Arts in Humanities");
    final JButton cButton = new JButton("Create");
    JButton cancel = new JButton("Cancel");
    JComboBox stateCombo = new JComboBox();
    JComboBox yearCombo = new JComboBox();
    String associate = "Non-Matriculated";
    static final String Database = "jdbc:mysql://localhost:3306/bbu";
    int MATRICULATEDORNOT;

    public Matriculated(final JFrame frame) {

        String DATE_FORMAT_NOW = "dd/MM/yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        frame.setLayout(null);
        String[] comboList = {
            "Select Location", "Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
            "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine",
            "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
            "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
            "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
            "Wisconsin", "Wyoming"
        };
        String[] yearComboList = {
            "Select Year", "Freshman", "Sophomore", "Junior", "Senior"
        };




        aahum.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ascmp.setSelected(false);
                aahum.setSelected(true);
                associate = aahum.getText().toString();
            }
        });

        ascmp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ascmp.setSelected(true);
                aahum.setSelected(false);
                associate = ascmp.getText().toString();
            }
        });

        /*
         * Create button asks the user to fill out the information about
         * the student that you are registering. Don't forget to
         * fill out all the fields otherwise an error message would pamp out.
         */
        cButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String errormsg = "";
                if (nField.getText().length() == 0) {
                    errormsg += "- Name\n";
                }
                if (lnField.getText().length() == 0) {
                    errormsg += "- Last Name\n";
                }
                if (sField.getText().length() == 0) {
                    errormsg += "- Street Address\n";
                }
                if (cField.getText().length() == 0) {
                    errormsg += "- City\n";
                }
                if (ssField.getText().length() == 0) {
                    errormsg += "- Social Security\n";
                }
                if (zField.getText().length() == 0) {
                    errormsg += "- Zip Code\n";
                }
                if (stateCombo.getSelectedItem().toString().equals("Select Location")) {
                    errormsg += "- Choose a State\n";
                }
                if (!inmuneCheck.isSelected()) {
                    errormsg += "- Check inmunization\n";
                }
                switch (MATRICULATEDORNOT) {

                    case 1:

                        if (yearCombo.getSelectedItem().toString().equals("Select Year")) {
                            errormsg += "- Choose a Year\n";
                        }
                        if (!hsCheck.isSelected()) {
                            errormsg += "- Check High School diploma\n";
                        }
                        if (ascmp.isSelected() == false && aahum.isSelected() == false) {
                            errormsg += "- Choose an Associate\n";
                        }

                }
                if (!(errormsg.length() == 0)) {
                    JOptionPane.showOptionDialog(frame, "You forgot to provide the following Information:\n\n" + errormsg,
                            "Input Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return;
                }

                try {
                    Connection connection = null;
                    Statement statement = null;

                    Class.forName("com.mysql.jdbc.Driver");

                    connection = DriverManager.getConnection(Database, "santi", "santi");
                    statement = connection.createStatement();
                    statement.executeUpdate("INSERT INTO regStudents(Name, LastName,Street, city,ss,zip,State,date,year,associate)VALUES('" + nField.getText() + "','" + lnField.getText() + "','" + sField.getText() + "','" + cField.getText() + "','" + ssField.getText() + "','" + zField.getText() + "','" + stateCombo.getSelectedItem().toString() + "','" + date.getText() + "','" + yearCombo.getSelectedItem().toString() + "','" + associate + "')");

                    JOptionPane.showOptionDialog(frame, "The student was\ncreated successfully",
                            "Boola Boola University", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    cancel();
                    connection.close();
                    statement.close();

                } catch (SQLException sqlE) {
                    System.out.println("SQL Exception: " + sqlE.toString());
                    System.out.print("matri");
                } catch (ClassNotFoundException cE) {
                    System.out.println("Class Not Found Exception: " + cE.toString());
                }

            }
        });


        msLabel1.setBounds(130, 35, 200, 10);
        nLabel.setBounds(80, 82, 50, 10);
        nField.setBounds(130, 80, 200, 20);
        lnLabel.setBounds(53, 112, 70, 10);
        lnField.setBounds(130, 110, 200, 20);
        sLabel.setBounds(29, 142, 100, 10);
        sField.setBounds(130, 140, 200, 20);
        cLabel.setBounds(93, 172, 100, 15);
        cField.setBounds(130, 170, 200, 20);
        ssLabel.setBounds(29, 202, 100, 15);
        ssField.setBounds(130, 200, 200, 20);
        zLabel.setBounds(67, 232, 100, 15);
        zField.setBounds(130, 230, 70, 20);
        stLabel.setBounds(210, 232, 100, 15);
        stateCombo.setBounds(250, 230, 120, 20);
        tLabel.setBounds(67, 262, 50, 15);
        date.setBorder(Border);
        date.setText(sdf.format(cal.getTime()));
        date.setBounds(120, 263, 80, 15);
        yLabel.setBounds(213, 262, 100, 15);
        yearCombo.setBounds(250, 260, 100, 20);
        hsCheck.setBounds(250, 290, 100, 20);
        inmuneCheck.setBounds(87, 290, 100, 15);
        cButton.setBounds(87, 420, 100, 20);
        cancel.setBounds(250, 420, 100, 20);
        panel.setBorder(Border);
        panel.setLayout(new GridLayout(0, 1));
        panel.setBounds(60, 330, 320, 70);
        panel.add(ascmp);
        panel.add(aahum);
        for (int i = 0; i < comboList.length; i++) {
            stateCombo.addItem(comboList[i]);
        }
        for (int i = 0; i < yearComboList.length; i++) {
            yearCombo.addItem(yearComboList[i]);
        }


        frame.add(panel);
        frame.add(nLabel);
        frame.add(nField);
        frame.add(lnLabel);
        frame.add(lnField);
        frame.add(sLabel);
        frame.add(sField);
        frame.add(cLabel);
        frame.add(ssLabel);
        frame.add(ssField);
        frame.add(cField);
        frame.add(zLabel);
        frame.add(zField);
        frame.add(stLabel);
        frame.add(stateCombo);
        frame.add(msLabel1);
        frame.add(tLabel);
        frame.add(yearCombo);
        frame.add(yLabel);
        frame.add(inmuneCheck);
        frame.add(hsCheck);
        frame.add(cButton);
        frame.add(date);
        frame.add(cancel);

    }

    public void setVisible(boolean visibility) {
        nLabel.setVisible(visibility);
        nField.setVisible(visibility);
        lnLabel.setVisible(visibility);
        lnField.setVisible(visibility);
        sLabel.setVisible(visibility);
        sField.setVisible(visibility);
        ssLabel.setVisible(visibility);
        ssField.setVisible(visibility);
        cLabel.setVisible(visibility);
        cField.setVisible(visibility);
        zLabel.setVisible(visibility);
        zField.setVisible(visibility);
        stateCombo.setVisible(visibility);
        stLabel.setVisible(visibility);
        msLabel1.setVisible(visibility);
        tLabel.setVisible(visibility);
        yearCombo.setVisible(visibility);
        yLabel.setVisible(visibility);
        hsCheck.setVisible(visibility);
        inmuneCheck.setVisible(visibility);
        cButton.setVisible(visibility);
        date.setVisible(visibility);
        panel.setVisible(visibility);
        cancel.setVisible(visibility);

    }

    public void cancel() {
        nField.setText("");
        lnField.setText("");
        sField.setText("");
        cField.setText("");
        ssField.setText("");
        zField.setText("");
        stateCombo.setSelectedItem("Select Location");
        yearCombo.setSelectedItem("Select Year");
        inmuneCheck.setSelected(false);
        hsCheck.setSelected(false);
        ascmp.setSelected(false);
        aahum.setSelected(false);


    }

    public void setMatriculated(int x) {
        MATRICULATEDORNOT = x;
    }
}
