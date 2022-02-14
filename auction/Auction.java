import java.util.*;
import java.util.ArrayList;


/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author Nicolas Alarcon-Belanger
 * @student_number 101066600
 * @version 2019.02.07
 * 
 */
public class Auction
{
    /** The list of Lots in this auction. */
    private ArrayList<Lot> lots;
   
    /** 
     * The number that will be given to the next lot entered
     * into this auction.  Every lot gets a new number, even if some lots have
     * been removed.  (For example, if lot number 3 has been deleted, we don't
     * reuse it.)
     */
    private int nextLotNumber;
    
    /**
     * boolean open is used to open and close the auction
     */
    private boolean open;
    

    /**
     * Create a new auction.
     * 
     * Uses ArrayList to group objects: lots
     * Defaulted auction is open
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
        open=true;
    }
    
    /**
     * Provided the auction parameter
     * is closed, the constructor creates a new auction containing
     * the unsold lots of the closed auction.  If the auction parameter
     * is still open or null, this constructor behaves like the
     * default constructor.
     *  
     * @param auction Uses information from the auction lots
     */
    
    public Auction(Auction auction){
        if(auction.open || auction.equals(null)){
            lots = new ArrayList<Lot>();
            nextLotNumber = 1;
        }else {
            lots = auction.getNoBids();
            nextLotNumber = auction.nextLotNumber;
        }   
        open=true;
    }

    /**
     * Enter a new lot into the auction
     * This method has set conditions before creating a new lot
     * 
     * @param description A description of the lot.
     * @return false if auction is not open or is null
     * @return true when initial conditions are passed
     */
    public boolean enterLot(String description)
    {
        if(!open|| description.equals(null)){
            return false;
        }else{
            lots.add(new Lot(nextLotNumber, description));
            nextLotNumber++;
            return true;
        }
    }

    /**
     * Shows the full list of lots in this auction.
     * Uses condition to test if lot is empty or not.
     * If it passes: prints lots in console.
     */
    public void showLots()
    {
      System.out.println();
        if (lots.isEmpty()){
          System.out.println("There are no lots available");
      }else{
            for(Lot lot : lots) {
                System.out.println(lot.toString());
            }
        }
    }
    
    /**
     * Bid for a lot.
     *   
     * Method used to bid for a selected lot.
     * If initial conditions pass, a bid is testest against the lot.
     * The outcome of the bid is outputted in the console.
     *
     * @param number The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     * @return false If auction conditions are not met
     * @return true by default
     */
    public boolean bidFor(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        
        if(!open || bidder.equals(null) || value <= 0 || selectedLot == null){
            return false;
        }
        
        System.out.println();
        Bid bid = new Bid(bidder, value);
        boolean successful = selectedLot.bidFor(bid);
        
        if(selectedLot !=null) {
            if(successful) {
                System.out.println("The bid was was successful. The lot number is: " + lotNumber + ". The highest bidder is: " + selectedLot.getHighestBid().getBidder().getName() + 
                ". The value of the bid is: " + selectedLot.getHighestBid().getValue());
            } else {
                Bid highBid = selectedLot.getHighestBid();
                System.out.println("The bid was not successful. The lot number was: " + lotNumber + ". The value of the highest bid is:" + highBid.getValue());
            }
       }
       
       return true;
    
    }


    /**
     * Return the lot with the given number.  
     * Returns null if the lot does not exist.
     * 
     * @param lotNumber The number of the lot to return.
     * @return the Lot with the given number
     * @return null when lot number is unable to be returned.
     */
    public Lot getLot(int lotNumber)
    {
       for(Lot lot : lots) { 
            if(lot.getNumber() == lotNumber)
                return lot;
       }
       
       return null; 
    }
    
    /**
     * Closes the auction and prints information on the lots.
     * Console Outputs depending on condition:
     * If it did sell, the high bidder and bid value are also printed.  
     * If it didn't sell, print that it didn't sell.
     *
     * @return false if the auction is already closed, true otherwise.
     */
    public boolean close()
    {
    
        if(!open) {
            return false;
        }
        
        open = false;
        System.out.println();
        
        for(Lot lot : lots) {
            System.out.println("The number is: " + lot.getNumber() + ". The description is: " + lot.getDescription() + " ");
            
            if(lot.getHighestBid()!= null) {
                System.out.println("The highest bidder was:" + lot.getHighestBid().getBidder().getName() + ". And the bid value was:" + 
                lot.getHighestBid().getValue());
            } 
            if(lot.getHighestBid() == null) {
                System.out.println("The item did not sell.");
            }
        }
        return true;    
    }
    
    
     /**
     * Returns an ArrayList containing all the items that have no bids so far.
     * (or have not sold if the auction has ended).
     * 
     * @return an ArrayList of the Lots which currently have no bids
     */
    public ArrayList<Lot> getNoBids()
    {
       ArrayList<Lot> noBid = new ArrayList<Lot>();
       for(Lot lot : lots) {
           if(lot.getHighestBid()== null) {
               noBid.add(lot);
            }
       }
        return noBid;
    }
    
    /**
     * Remove the lot with the given lot number, as long as the lot has
     * no bids, and the auction is open.  
     *
     * @param number The number of the lot to be removed.
     * @return true if successfull
     * @return false if auction is closed, lot doesnt exist, or lot has a bid.
     */
    public boolean removeLot(int number)
    {
      if (!open) {
            return false;
      }
      
      Lot temp;
      Iterator<Lot> itr = lots.iterator();
      
      while(itr.hasNext()) {
          temp = itr.next();
            if(temp.getNumber() == number && temp.getHighestBid() == null ) {
                lots.remove(temp);
                return true;
            }
        }
        return false;      
    }  
}
