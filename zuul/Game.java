import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Nicolas Alarcon-Belanger
 * @studentNumber 101066600
 * @version 2019-03-14 <--Hey look! Happy Pi-Day!
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private TransporterRoom transporterRoom; //used to invoke methods on the room subclass: transporterRoom
    private Stack<Room> backStack; //used for stackBack method
    private ArrayList<Item> hand; //inventory for the items -> please see constructor for reason of use!!
    private ArrayList<Room> rooms; //list of all rooms
    private boolean hasSpinach; // checks if player has spinach for added inventory -> spinach >> cookies
    private boolean eatenSpinach; //checks if player has consumed spiniach
    private int handCap; //capacity of inventory
    private Beamer beamer; //used to invoke methods on the Item subclass: beamer
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        backStack = new Stack<Room>();
        hasSpinach = false; //deafult setting -> player must first consume spinach
        //I choose an ArrayList, as it has additional methods -> runtime may be slower(unnoticeable).
        //The requirements also didn't specify I couldn't use one. Also allows implementation of a multi-item inventory. 
        hand = new ArrayList<Item>(1); 
        handCap = 0; //capacity of hand used to check if spinach needs to be consumed to pick up more
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        TransporterRoom transporterRoom;
        Item apple, stick, sapling, laptop, backpack, beer, snacks, desk, chair,
        stapler, pencil, brief_case, spinach;        
        
        //beamer initialization
        Beamer beamer1 = new Beamer("delorian");
        Beamer beamer2 = new Beamer("tardis");
        
        //spinach initialization
        spinach = new Item("spinach","Popeye Strong", 0.3);
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        rooms = new ArrayList<Room>();
        //adding rooms to list
        rooms.add(outside);
        rooms.add(theatre);
        rooms.add(pub);
        rooms.add(lab);
        rooms.add(office);
        
        //teleporterRoom initialization
        transporterRoom = new TransporterRoom(rooms);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north",transporterRoom);
        apple = new Item("apple","Green apple", 1);
        stick = new Item("stick","Long stick",6);
        sapling = new Item("sapling","Oak sapling", 10);
        outside.addItem(apple);
        outside.addItem(stick);
        outside.addItem(sapling);
        outside.addItem(spinach);
        outside.addItem(beamer1);
        
        theatre.setExit("west", outside);
        laptop = new Item("laptop","MacBook", 5);
        backpack = new Item("backpack","Nike backpack",6.5);
        theatre.addItem(laptop);
        theatre.addItem(backpack);
        
        pub.setExit("east", outside);
        beer = new Item("beer","Molson Canadian", 2);
        snacks = new Item("snacks","peanuts",0.5);
        pub.addItem(beer);
        pub.addItem(snacks);
        pub.addItem(spinach);
                
        lab.setExit("north", outside);
        lab.setExit("east", office);
        desk = new Item("desk","Brown Desk",35);
        chair = new Item("chair","Swivle Chair",15);
        lab.addItem(desk);
        lab.addItem(chair);
        lab.addItem(beamer2);
        
        office.setExit("west", lab);
        pencil = new Item("pencil","Pencil",.1);
        stapler = new Item("stapler","Stapler",1.5);
        brief_case = new Item("brief_case","Black brief case",4);
        office.addItem(pencil);
        office.addItem(stapler);
        office.addItem(brief_case);
        office.addItem(spinach);
                
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
             look(command);
        }
        else if (commandWord.equals("eat")) {
             eat(command);
        }
        else if (commandWord.equals("back")) {
             back(command);
        }
        else if(commandWord.equals("stackBack")) {
             stackBack(command);
        }
        else if (commandWord.equals("take")) {
             take(command);
        }
        else if(commandWord.equals("drop")) {
             drop(command);
        }
        else if (commandWord.equals("charge")) {
             charge(command);
        }
        else if(commandWord.equals("fire")) {
             fire(command);
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    
    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * Update: previousRoom has been added to set player's previous position after moving.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom;
            backStack.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }   
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Method to tell player what current room they're in.
     * If player enters a second word to command statement, it will return invalid.
     * Else, it will give description of current room.
     * @param command The command to be processed
     */
    private void look(Command command)
    {
        if(command.hasSecondWord()) {
            // if there is too many inputs
            System.out.println("Look what?");
            return;
        }
        else{
            System.out.println(currentRoom.getLongDescription());
            
            if(hand.size()==0){
                System.out.println("You have no items in your hand.");
                return;
            }
            else{
                String s = "Item in your hand:";
                for(int i=0;i<hand.size();i++){
                     s+= "\n" + hand.get(i).getLongItem();
                }
                System.out.println(s);
            }
        }  
    }
    
    /**
     * Method let's player eat.
     * If player enters a second word to command statement, it will return invalid.
     * Else, it will print the player has eaten.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command)
    {
        if(command.hasSecondWord()) {
            // if there is too many inputs
            System.out.println("Eat what?");
            return;
        }
        else if(hasSpinach){
            System.out.println("You have eaten the spinach and gained its strength.");
            hand.remove(0); //removes spinach as it has now been consumed
            hasSpinach=false; //no longer has spinach
            eatenSpinach=true; //allowing player to pickup items other than spinach
            handCap=0; //creating space for new items
        }
        else{
            System.out.println("You have no food!");
        }
    }
    
    /**
     * Method to return a player back to previous spot.
     * If player enters a second word to command statement, it will return invalid.
     * If player is at the starting position by default, player cannot go back.
     * Else, method will output the player has gone back, and call goRoom method to bring player back.
     * 
     * @param command The command to be processed
     */
    private void back(Command command)
    {
        if(command.hasSecondWord()) {
            // if there is too many inputs
            System.out.println("Back what?");
            return;
        }
        else if(previousRoom == null) {
            // if trying to go back to current room
            System.out.println("You can't go back to your current room.");
            return;
        }
        else{
            System.out.println("You have gone back.");
            Room temp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = temp;
            backStack.push(temp);
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Method uses a stack of all the locations that the player goes -> in order
     * If player enters a second word to command statement, it will return invalid.
     * If player is at the starting position by default, player cannot go back.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
            return;
        } else {
            if(backStack.empty()) {
                System.out.println("There is no previous room");
            } else {
                System.out.println("You have gone back.");
                previousRoom = currentRoom;
                currentRoom = backStack.pop(); //sets current room to the one popped from the top
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }

    /**
     * Method let's player take single item.
     * The player must first pickup spinich, and consume it to gain strength to pick up 5 additional items.
     * After the player has picked up and dropped 5 items, they will need to consume more spinach.
     * Players can only pick up items that exist in the current room they are in.
     * 
     * @param command The command to be processed
     */
    private void take(Command command)
    {   
        boolean noItem = true; //boolean used to check if item exists in the current room    
        if(!command.hasSecondWord()) { //checks that the command has a second word
            System.out.println("take what?");
        }
        //Checks if second command word is spinach, in order to allow for inventory
        else if(command.getSecondWord().equals("spinach") && hand.size() == 0){
            for(int i=0;i<currentRoom.getItemList().size();i++){
                if(currentRoom.getItem(i).getShortItem().equals("spinach")){
                    hasSpinach=true; //used for the eat command
                    hand.add(currentRoom.getItem(i)); //adds spinach to players inventory
                    System.out.println("You have picked up the spinach");
                    currentRoom.getItemList().remove(i); //removes spinach from room
                    noItem=false;
                }
                else{
                    noItem=true;
                }
            }
        }
        //checks if spinach has been consumed, there is nothing in the players hand, and that they has not exceeded 5 items
        else if(eatenSpinach && hand.size() == 0 && handCap < 5 ) {               
            for(int i=0; i < currentRoom.getItemList().size();i++){ //iterates through the items in the room
                    //checks if the item is in the room
                    //Please note that on the first assignment I lost marks here for using .equals() instead of ==, yet they don't
                    //perform the same function for strings!!!
                    if(command.getSecondWord().equals(currentRoom.getItem(i).getShortItem())){
                        //adds the item to the players inventory                    
                        hand.add(currentRoom.getItem(i));
                        //prints that the player has picked up the item
                        String s = "You have picked up the " +  command.getSecondWord() + ".";
                        System.out.println(s);
                        //removes item from current room
                        currentRoom.getItemList().remove(i);
                        noItem=false;
                        //increases after every item picked up
                        handCap++;
                        break;                
                    }
                }      
        }
        //player has picked up 5 items
        else if(handCap==5){ 
                System.out.println("You need to consume more spinach to pick up additional items.");
                noItem=false;
                }
        //trying to pick up item without eating first
        else if(!eatenSpinach && hand.size()==0){ 
            System.out.println("You need to eat spinach before you can do that.");
            noItem=false;
        }
        else {
            System.out.println("You can only hold one item at a time.");
            noItem=false;
        }
        //returned if the item searched for cannot be found in the room
        if(noItem){ 
            System.out.println("This item is not in this room.");
            return;
        }
    }
    
    /**
     * Method let's player drop item.
     * The player will drop the top item from their hand.
     * If player enters a second word to command statement, it will return invalid.
     * Else, it will print the player has dropped an item.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command)
    {
        if(command.hasSecondWord()){ //player cannot drop specified item
            System.out.println("Drop what");
        }
        else if(hand.size() != 0) {//checks that the hand is not empty
            //temporary item to add back into current room when removed
            Item temp = hand.get(0);
            //remove item from hand
            hand.remove(0);
            //puts item back into arraylist of current room
            currentRoom.getItemList().add(temp);
            String s = "You have dropped the " + temp.getShortItem() + " from your inventory.";
            System.out.println(s);
            return;
        }
        else{
            System.out.println("You have nothing to drop.");
            return;
        }  
    }
    
    /**
     * Method let's player charge beamer item.
     * Once beamer is charged, it will store the location it was charged at, allowing it to be fired.
     * If player enters a second word to command statement, it will return invalid.
     * Else, it will test if beamer is in hand, and potentially charge the beamer item.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command)
    {
        if(command.hasSecondWord()){
            System.out.println("Charge what?");
        }
        else if(hand.get(0).getShortItem() == "delorian"|| hand.get(0).getShortItem() == "tardis"){   
            Beamer beamer = (Beamer) hand.get(0);
            beamer.charge(currentRoom);
           }
        else{
            System.out.println("A beamer cannot be found in your inventory.");
           }
    }
    
    /**
     * Method let's player fire beamer item.
     * Once beamer is fired, player will returned to charged location, and beamer will no longer be charged.
     * If player enters a second word to command statement, it will return invalid.
     * Else, it will test if the beamer is in the inventory and charged, and will portentially fire beamer item.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command)
    {
        if(command.hasSecondWord()){
            System.out.println("Charge what?");
        }
        //checks if beamer is in inventory
        else if(hand.get(0).getShortItem() == "delorian"|| hand.get(0).getShortItem() == "tardis"){   
            //casts type beamer onto hand object
            Beamer beamer = (Beamer) hand.get(0);
            //beamer.isChared() is a function of the beamer sub-class -> checks that the beamer is charged to be fired
            if(beamer.isCharged()){
                    previousRoom = currentRoom;
                    //beamer.fire() is a function of the beamer sub-class
                    currentRoom = beamer.fire();
                    System.out.println("You have been teleported back.");
                    System.out.println(currentRoom.getLongDescription());
            }
            //if the beamer is not charged, it won't fire, and displays the message below
            else if (!beamer.isCharged()){
                System.out.println("The beamer is not charged.");
            }
        }
    }
   
}

