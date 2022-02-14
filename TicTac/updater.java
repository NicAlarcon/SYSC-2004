import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Write a description of class updater here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class updater implements Observer
{
    JFrame frame;
    Container container;
    JTextArea status;
    Button buttonPressed;
    int i;
    /**
     * Constructor for objects of class Button
     */
    public updater()
    {
       JFrame frame = new JFrame();
       Container container = frame.getContentPane();
       status = new JTextArea();
       container.add(status);
       frame.setVisible(true);
       frame.pack();
       buttonPressed = new Button();
       buttonPressed.addObserver(this);
       i=0;
    }
    
    public void update(Observable o, Object arg){
        i++;
        status.setText(i + "");
    }
}
