/**
 * The beamer sub-class based on the Item class.
 * Class used to charge and fire a beamer device.
 * Beamer stores location and transports player back when fired.
 *
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 2019-03-14
 */
public class Beamer extends Item
{
    private Room beamerRoom; //stores charged room
    private boolean isCharged; //used to check if charged
    
    /**
     * Constructor for objects of class beamer
     * A sub-class constructor of Item
     * By default the beamer is not charged
     */
    public Beamer(String description)
    {
        super(description,"Transport you to any room", 69);
        isCharged=false;
    }
    
    /**
     * Method to return if beamer is charged or not.
     */
    public boolean isCharged()
    {
        return isCharged;
    }
    
    /**
     * Method to charge beamer.
     * @param Room The room to be stored as the charged room.
     */
    public void charge(Room room)
    {
        if(!isCharged()){
            beamerRoom = room;
            isCharged = true;
            System.out.println("The Beamer has been charged.");
        }
        else if (isCharged){
            System.out.println("The beamer is already charged.");
        }
    }
    
    /**
     * Method to fire beamer.
     * Checks if isCharged returns true to fire and return the beamer room.
     * @return beamerRoom when beamer is charged
     * @return null when beamer is not charged
     */
    public Room fire(){
        if (isCharged){
            isCharged = false;
            return beamerRoom;
        }
        else {
            return null;
        }
    }
}
