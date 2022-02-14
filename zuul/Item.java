/**
 * This class is used to hold an item with a name, description, and weight
 *
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 2019-03-14
 */
public class Item
{
    //Title of the item
    private String description;
    //Weight of the item
    private double weight;
    //Name of item
    private String name;

    /**
     * Constructor to create a new item
     * @param Description This is used for the description of an item
     * @param weight This is used for the weight of the item
     */
    public Item(String name, String description, double weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * Method used to create a string combining the item description and weight
     * 
     * @return the items description and weight
     */
    public String getLongItem()
    {
        return "Name: " + name + ", " + "Descrpition: " + description + ", " + "weight: "+ weight + " lbs";
    }
    
    public String getShortItem()
    {
        return name;
    }
}