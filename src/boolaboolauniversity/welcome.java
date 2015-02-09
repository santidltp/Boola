/*
 * The class welcom includes three labels. The first label is to say:
 * "Welcome to Boola Boola Univeristy" the second one to display a picture
 * of NorthEastern University(just to make it a little bit real).
 * and the third one is a little bit of explanation of how to use the program.
 */
package boolaboolauniversity;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Santy
 */
public class welcome {

    final JLabel wLabel = new JLabel("Welcome to Boola Boola University");
    final JLabel image = new JLabel(new ImageIcon("C:\\Documents and Settings\\Santy\\Desktop\\Boola Boola University\\Images\\NU.jpg"), JLabel.RIGHT);
    final JLabel msLabel = new JLabel("<html>" + "To start choose an option"
            + " from the menu bar: " + "<br>" + "<br>" + "A) Admissions: Click here to matriculate a studens."
            + "<br>" + "B) Registration: Click here to registrate students by providing the Social Security."
            + "<br>" + "C) Reports provides you information of a student; Schedule or Financial information.");

    public welcome(JFrame frame) {

        wLabel.setBounds(120, 50, 200, 15);
        image.setBounds(65, 90, 300, 250);
        msLabel.setBounds(78, 290, 300, 200);
        frame.add(wLabel);
        frame.add(msLabel);
        frame.add(image);
    }

    public void setVisible(boolean visibility) {
        wLabel.setVisible(visibility);
        msLabel.setVisible(visibility);
        image.setVisible(visibility);
    }
}
