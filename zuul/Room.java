import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 2019-03-14
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;              //stores an arraylist of type: Item, into a list: items
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
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
        String s = "";
        for(int i=0; i<items.size();i++){
             s +="\n";
             s += items.get(i).getLongItem();
            
            }
        return "You are " + description + ".\n" + getExitString() + "\n" + "Items in the room:" + s;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Method used to add a new item to a defined room.
     * Uses an array list to store items.
     * creates new item by calling the class Item.
     * @param item item of type Item to be added to array list.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Method used to add a new item to a defined room.
     * Uses an array list to store items.
     * creates new item by calling the class Item.
     * @param item item of type Item to be added to array list.
     */
    public Item getItem(int i)
    {
        return items.get(i);
    }
    
    /**
     * Method used to add a new item to a defined room.
     * Uses an array list to store items.
     * creates new item by calling the class Item.
     * @param item item of type Item to be added to array list.
     */
    public ArrayList getItemList()
    {
        return items;
    }
}

