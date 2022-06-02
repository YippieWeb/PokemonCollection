import java.util.HashMap;
import ecs100.*;
import java.util.ArrayList;
/**
 * Holds a collection of Pokemon cards in a hashmap
 * Allows a user to add, find, print all, edit from a menu
 *
 * @author Jasmine Yip
 * @version 23/05/2022
 */
public class PokemonCards
{
    // instance variables
    private int x;
    private HashMap<Integer, PokemonCard> cardList;
    private int currCardId;
    private String currName;
    private String currNameL;
    private String nameL;
    private String existingName;
    private double currPr;
    private PokemonCard currCard;
    
    int toLeft = 100; // first printed card distance to left 

    /**
     * Constructor for objects of class PokemonCards
     */
    public PokemonCards()
    {
        // initialise instance variables
        cardList = new HashMap<Integer, PokemonCard>();
        
        // creating pokemon cards
        PokemonCard p1 = new PokemonCard (1, "Poliwrath", 17.95, "pw.png");
        PokemonCard p2 = new PokemonCard (2, "Venusaur V", 78.83, "vv.jpg");
        PokemonCard p3 = new PokemonCard (3, "Mewtwo V", 30.78, "mv.jpeg");
        PokemonCard p4 = new PokemonCard (4, "Mewtwo VSTAR", 56.32, "mvs.png");
        PokemonCard p5 = new PokemonCard (5, "Pikachu V", 70.99, "pv.png");
        PokemonCard p6 = new PokemonCard (6, "Pikachu VMAX", 120.99, "pvm.png");
        PokemonCard p7 = new PokemonCard (7, "Gold Pikachu", 279.98, "gp.jpeg");
        
        // add pokemon cards to card list
        cardList.put(1, p1);
        cardList.put(2, p2);
        cardList.put(3, p3);
        cardList.put(4, p4);
        cardList.put(5, p5);
        cardList.put(6, p6);
        cardList.put(7, p7);
        
        this.currCardId = 7; // store the current contact id
    }

    /**
     * Set ContactId
     */
    public void setCardId() {
        // add 1 to the current card id
        this.currCardId += 1;
    }
    
    /**
     * Add a pokemon card to the map
     * @param name for the name of the pokemon card
     * @param price double for the price of the pokemon card
     * @param img for the image of the pokemon card
     */
    public void addCard(String name, double price, String img) {
        cardList.put(currCardId, 
                        new PokemonCard(currCardId, name, price, img));
    }
    
    /** 
     * Check if a card to be added already exist
     * @param name for the name searched
     * @return whether the searched card already exist
     */
    public boolean existCard(String name) {
        // iterate throught list of cards
        for (int cardId : cardList.keySet()) {
            // store current name
            currName = cardList.get(cardId).getName(); 
            
            // stripe lead and trail white space and set to lower case
            currNameL = currName.toLowerCase().trim(); 
            nameL = name.toLowerCase().trim();
            
            // if name input is EXACTLY the same as any of those in the list
            if (currNameL.equals(nameL)) {
                return true;
            } 
        }
        return false;
    }
    
    /**
     * Find a card based on name
     * Sets the current card instance if found
     * @param name for the name searched
     * @return matches list of matched items
     */
    public ArrayList<PokemonCard> findCard(String name) {
        // reset distance to left
        toLeft = 100;
        
        // creating an ArrayList to store matched objects
        ArrayList<PokemonCard> matches = new ArrayList<PokemonCard>();
        
        // search for a pokemon card
        for (int cardId : cardList.keySet()) {
            // store current name, set to lower case
            currName = cardList.get(cardId).getName().toLowerCase();
            
            // if searched name exists
            if (nameMatcher(name, currName)) {
                // set the current card
                currCard = cardList.get(cardId);
                
                // add matched card to arraylist
                matches.add(currCard);
                
                // show avatar on Canvas
                cardList.get(cardId).displayContact(toLeft);
                
                // increase distance to left by 120
                toLeft += 120;
            }
        }
        return matches;
    }
    
    /**
     * Check if the name searched matches with 
     * the name of the pokemon card in the card list
     * @param name for the name searched
     * @param currNm for the name of current pokemon card iterated
     * @return match whether the searched name match current name
     */
    public boolean nameMatcher(String name, String currNm) {
        // instance
        int i = 0;
        
        // check name input against current name in map
        // return true if current name in list contains or equal to input
        for (i = 0; i <= currLength(currNm) - nameLength(name); i++) {
            if (currNm.substring(i, name.length() + i).equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return the length of the pokemon name in the card list
     * @param currNm for the current name
     * @return currLength length of current name
     */
    public int currLength(String currNm) {
        int currLength = currNm.length(); // check length
        return currLength;
    }
    
    /**
     * Return the length of the name searched
     * @param name for the name searched
     * @return nameLength length of name searched
     */
    public int nameLength(String name) {
        int nameLength = name.length(); // check length
        return nameLength;
    }
    
    /**
     * Getter for the current card instance
     * @return currCard current card instance
     */
    public PokemonCard getCard() {
        return this.currCard;
    }
    
    /** 
     * Print detail of all cards
     */
    public void printAll() {
        // reset distance to left
        toLeft = 100;
        
        UI.println("-------- All Pokèmon Cards --------");
        
        // traverse Map, print all cards 
        for (int cardId : cardList.keySet()) {
            UI.println("#" + cardId + " " + cardList.get(cardId).getName());
            currPr = cardList.get(cardId).getPrice();
            UI.println(String.format("$ " + "%.2f", currPr));
            lineBreak();
            
            // prints all pokemon cards, with increasing distance to left
            cardList.get(cardId).displayContact(toLeft);
                
            // increase distance to left by 120
            toLeft += 120;
        }
    }
    
    /**
     * Break a line 
     */
    public void lineBreak() {
        UI.print(System.lineSeparator());
    }
}
