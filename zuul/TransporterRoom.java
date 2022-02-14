import java.util.Random;
import java.util.ArrayList;
/**
 * The TransporterRoom sub-class based off of Room.
 * TransporterRoom used to generate a random room for the player
 * to be transported to.
 *
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 2019-03-14
 */
public class TransporterRoom extends Room
{
    private Room randomRoom;
    private ArrayList<Room> tempList;
    
    /**
     * Constructor for Transporter Room
     * 
     * @param list is an ArrayList containing all possible rooms to be teleported to
     */
    public TransporterRoom(ArrayList list){
        super("in the transporter room");
        tempList = list; //a reference list used further in the code
    }
    
    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction used to check if valid input instead of a user inputting "go asdf"
     * @return A randomly selected room.
     */
    public Room getExit(String direction)
    {
        if (direction.equals("north") || direction.equals("south") || direction.equals("east") || direction.equals("west")){
            return findRandomRoom();
        }
        else {
            return null;
        }
    }
     
    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
     */
    private Room findRandomRoom()
    {
         Random r = new Random();
         int i = r.nextInt(5); //select a room between 0(inclusive) and 5(exclusive)
         randomRoom = tempList.get(i);
         return randomRoom;
    }
    
    /**
     * Return a string describing the room's exits
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        return "Exits: Any direction will teleport you randomly.\nUse north, south, east, or west.";
    }
    
    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are in the transporter room.\n" + getExitString();
    }
}
