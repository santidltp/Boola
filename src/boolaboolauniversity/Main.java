/*
 *
 *  This program is designed and code for Boola Boola University to keep
 *  a database of the students registered.
 *  The main frame has three different manus; the first one is to
 *       Matriculate a student, the second one is to register a student already
 *       matriculated, and the third menu is to report an information about the
 *       student, either, Financial Information or the Schedule.
 *
 *  This program is also developed with MySQL. There are four different MySQL
 *  tables that this program uses: Courses, NCcourses, regStudents, Student.
 *  Courses: is where all three-credit courses are stored in.
 *  NCcourses: where  all non-credit courses are stored.
 *  regStudents: is the table where the program save all the information about
 *               a student; name, adress, state and so forth.
 * Student: is used by the Full-time-Part-time of the program, all the courses
 *                that the student chooses are saved here with the social security of
 *               the student that is matriculating.
 *       All these tables are stored in the bbu.




Developed by: Santiago De La Torre.
Last Modification: October 16 2010.
Educational Center: Bunker Hill Community College.


 */
package boolaboolauniversity;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main extends JFrame {
    /*
     * Create instances of the clases. Each class is created so we can recycle
     * it as many time as we want.
     */

    JFrame frame = new JFrame("Boola Boola University");
    final Matriculated matriOption = new Matriculated(frame);
    final Reports newReports = new Reports(frame);
    final Full_Time fTimeOption = new Full_Time(frame);
    final welcome welcomeMessage = new welcome(frame);

    public Main() {
        frame.setLayout(null);

        //Bar
        JMenuBar myBar = new JMenuBar();
        frame.setJMenuBar(myBar);
        //JMenu
        JMenu adm = new JMenu("Admissions");
        JMenu reg = new JMenu("Registration");
        JMenu rep = new JMenu("Reports");
        //JMenuItems
        JMenuItem matri = new JMenuItem("Matriculated", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\contact_24.png"));
        matri.setToolTipText("Matriculate for Degree seeking");
        JMenuItem nMatri = new JMenuItem("Non-Matriculated");
        nMatri.setToolTipText("Matriculate for Non-Degree seeking");
        final JMenuItem quit = new JMenuItem("Quit", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\delete_16.png"));
        JMenuItem fTime = new JMenuItem("Full-time", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\clipboard_16.png"));
        fTime.setToolTipText("Register for Full Time");
        JMenuItem pTime = new JMenuItem("Part-time");
        pTime.setToolTipText("Register for Part Time");
        JMenuItem nCredit = new JMenuItem("Non-credit");
        nCredit.setToolTipText("Register for Non-Credit courses");
        JMenuItem receivable = new JMenuItem("Receivable", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\statistics_16.png"));
        receivable.setToolTipText("Financial Information of a Student");
        JMenuItem classS = new JMenuItem("Class Schedule", new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Icons\\print_16.png"));
        classS.setToolTipText("Class Schedule of a Student");

        //add Jmenu to the bar: "Admissions","Registration","Reports"
        myBar.add(adm);
        myBar.add(reg);
        myBar.add(rep);

        //Add Menu items to the JMenu, each menu item is going to pump out
        //when the JMenu is being clicked.
        adm.add(matri);
        reg.add(fTime);
        rep.add(receivable);
        adm.add(nMatri);
        reg.add(pTime);
        rep.add(classS);
        adm.addSeparator();
        reg.add(nCredit);
        adm.add(quit);

        //set all the instances to false.
        fTimeOption.setVisible(false);
        matriOption.setVisible(false);
        newReports.setVisible(false);


        frame.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                quit.doClick();
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });

        //Frame attributes.
        frame.setSize(450, 530);
        frame.setLocation(300, 200);
        frame.setResizable(false);
        frame.setVisible(true);

        /*
         * Matriculation button sets all the instances to invisible,
         * but the one we are going to use which is the matriOption.
         * This program play with the setVisible(true/false) of all the
         * objects to display the Graphical User Interface.
         * The setMatriculate method is to determine what kind of matriculation
         * is. for exmple setMatriculate(1) is for MAtriculated Degree seeking.
         */
        matri.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                fTimeOption.setVisible(false);
                newReports.setVisible(false);
                matriOption.setVisible(true);
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
                welcomeMessage.setVisible(false);
                matriOption.hsCheck.setEnabled(true);
                matriOption.setMatriculated(1);
            }
        });
        /*
         * nMatri uses the same instance of the class Matriculated.
         * By setting the the setMatriculate(2) we adjust the appropriate
         * settings in the class so it does what we want, without creating
         * another class or another instance of the same class.
         */
        nMatri.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                newReports.setVisible(false);
                fTimeOption.setVisible(false);
                matriOption.setVisible(true);
                matriOption.hsCheck.setEnabled(false);
                matriOption.yearCombo.setEnabled(false);
                matriOption.aahum.setEnabled(false);
                matriOption.ascmp.setEnabled(false);
                welcomeMessage.setVisible(false);
                matriOption.setMatriculated(2);
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
            }
        });


        quit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int answer = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit?",
                        "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (answer == 0) {
                    System.exit(0);
                }
            }
        });
        /*
         * fTime, which stands from "Full Time", has the same mechanism
         * as matriOption. We create one class and we make that class do what
         * we want by settin a number. In this case the funcion is
         * chooseTime(1/2/3).
         */
        fTime.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                matriOption.setVisible(false);
                fTimeOption.chooseTime(1);
                fTimeOption.readFile();
                fTimeOption.setVisible(true);
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
                welcomeMessage.setVisible(false);
            }
        });

        pTime.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                matriOption.setVisible(false);
                fTimeOption.chooseTime(2);
                fTimeOption.readFile();
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
                fTimeOption.setVisible(true);
                welcomeMessage.setVisible(false);
            }
        });

        nCredit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                matriOption.setVisible(false);
                fTimeOption.chooseTime(3);
                fTimeOption.readFile();
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
                fTimeOption.setVisible(true);
                welcomeMessage.setVisible(false);
            }
        });

        /*
         * receivable and Classs work the same as the options above, but in this
         * case the method is called setOption(1/2). Notice that here we are
         * calling the reSet() method, and what it does is just reset all
         * the objects to their default state.
         */
        receivable.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                matriOption.setVisible(false);
                newReports.setOption(1);
                newReports.Associate.setVisible(false);
                newReports.Year.setVisible(false);
                newReports.setVisible(true);
                fTimeOption.setVisible(false);
                newReports.reSet();
                welcomeMessage.setVisible(false);
            }
        });


        classS.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                matriOption.setVisible(false);
                newReports.setOption(2);
                newReports.setVisible(true);
                newReports.cost.setVisible(false);
                newReports.totalcredits.setVisible(false);
                newReports.total.setVisible(false);
                newReports.Associate.setVisible(true);
                newReports.Year.setVisible(true);
                newReports.credits.setVisible(false);
                newReports.totalcost.setVisible(false);
                fTimeOption.setVisible(false);
                welcomeMessage.setVisible(false);
                newReports.reSet();
            }
        });

    }

    public void menuSelected(MenuEvent e) {
    }

    public void menuDeselected(MenuEvent e) {
    }

    public void menuCanceled(MenuEvent e) {
    }

    public static void main(String[] args) {
        new Main();
    }
}
