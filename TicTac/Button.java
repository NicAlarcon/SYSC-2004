import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Write a description of class Button here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Button extends Observable implements ActionListener
{
    JButton button;
    JFrame frame;
    Container container;
    /**
     * Constructor for objects of class Button
     */
    public Button()
    {
       JButton button = new JButton("plus one");
       JFrame frame = new JFrame();
       Container container = frame.getContentPane();
       container.add(button);
       button.addActionListener(this);
       frame.setVisible(true);
       frame.pack();
    }
    
    public void actionPerformed(ActionEvent e){
        Object o = e.getSource();
        if(o instanceof JButton){
            setChanged();
            notifyObservers();
        }
    }
}
