import java.util.HashMap;
import java.util.ArrayList;
import ecs100.*;
/**
 * GUI window fo the pokemon card collection program
 * Allows user to click buttom and print all cards
 * Add, find, or clear the current card
 * Check if the input is valid
 *
 * @author Jasmine Yip
 * @version 23/05/2022
 */
public class GUI
{
    // declare instances
    private PokemonCards pokemonCards;
    private PokemonCard pokemonCard;
    
    static final int INCREMENT = 1;
    
    // name instances
    String name;
    String nameStriped; // name stripped white spaces
    boolean nmValid = false;
    boolean exist = false;
    
    // price instances
    String priceS; // price string input
    String priceSStriped; // price stripped white spaces
    double priceD; // price converted to double
    double price; // valid price
    boolean prValid = false;
    static final int MAXDP = 2;
    
    // instance variables for finding card
    String cardName;
    String cnStriped;
    boolean cnValid = false;
    
    // instance variable for image
    String imgFileName;

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
        // initialise instance variables
        // instantiate the pokemonCards object
        pokemonCards = new PokemonCards();
        
        UI.initialise(); // create GUI window
        UI.addButton("Print All", pokemonCards::printAll);
        UI.addButton("Add", this::addCard);
        UI.addButton("Find", this::findCard);
        UI.addButton("Clear", UI::clearPanes);
        UI.addButton("Quit", UI::quit);
        
        // create mouse listener
        UI.setMouseListener(this::doMouse);
    }

    /**
     * Add a card to the card list
     */
    public void addCard() 
    {
        UI.println("-------- Add Pokèmon Card --------");
        
        // ask and check name
        askName();
        
        // check if the card already exist
        if (exist == false) {
            // doesn't exist, can add
            // ask and check price
            askPrice();
            
            // add a avatar image for diaplay in the GUI
            imgFileName = UIFileChooser.open("Choose Image File");
            
            // increase the id by 1
            pokemonCards.setCardId(); 
            
            // add card to hashmap
            pokemonCards.addCard(name, price, imgFileName);
        } 
        else {
            // already exist in collection, can't add
            UI.println("\"" + name + "\"" + " already exists!");
            lineBreak();
        }
    }
    
    /**
     * Ask for the name of new card
     * Submethod of addCard method
     */
    public void askName() 
    {
        // ask and check validity of name
        do {
            // ask name, strip leading and trailing white spaces
            name = UI.askString("Name: ").trim(); 
            
            // strip all white space just for checking
            nameStriped = name.replaceAll(" ", ""); 
            
            // pass input onto name validity checker
            nmValid = nameChecker(nameStriped); 
        } while (nmValid == false);
        
        // pass input into existence checker
        exist = pokemonCards.existCard(name);
    }
    
    /**
     * Ask for the price of the new card
     * Submethod of addCard method
     */
    public void askPrice() 
    {
        // ask and check validility of price
        do {
            priceS = UI.askString("Price: ");
            priceSStriped = priceS.trim();
           
            // pass input into price checker
            prValid = priceChecker(priceSStriped);
        } while (prValid == false);
        lineBreak();
    }
    
    /**
     * Finds pokemon card based on name
     * Prints out the details of card if found
     */
    public void findCard() {
        // clear graphics
        UI.clearGraphics();
        
        // allows user to search
        UI.println("-------- Find Pokèmon Card --------");
        do {
            // ask user to input name
            cardName = UI.askString("Find a card: ");
            cnStriped = cardName.trim();
            
            // pass input into name validility checker
            cnValid = searchChecker(cnStriped);
        } while (cnValid == false);
        
        // print all matched cards
        printMatches();
    }
    
    /**
     * Iterate ArrayList of matched items and print them out
     * Submethod of findCard
     */
    public void printMatches() {
        // call ArrayList of matched cards
        ArrayList<PokemonCard> matchedCards;
        matchedCards = pokemonCards.findCard(cnStriped.toLowerCase());
        
        // printing out all matched items, if any
        if (!matchedCards.isEmpty()) {
            lineBreak();
            UI.println("-------- Matched Items --------");
            
            // store current card as pokemonCard 
            pokemonCard = pokemonCards.getCard();
            
            // iterate matched list and print all items
            for (PokemonCard match : matchedCards) {
                UI.println(match.getName());
                UI.println(String.format("$ " + "%.2f", match.getPrice()));
                lineBreak();
                UI.printMessage("Click on the first card to clear panes.");
            }
        } 
        else {
            // no matches
            UI.println("\"" + cardName + "\"" + " is not found in collection");
            lineBreak();
        }
    }
    
    /**
     * A checker method that returns true if the 
     * name input during 'add card' is valid
     * @param nm takes the name added
     * @return boolean valid validity of the name
     */
    public boolean nameChecker(String nm) {
        // boolean for validity check
        boolean valid = false;
        
        if (nm.isEmpty()) {
            // prevent empty input
            UI.println("Don't leave it blank");
            lineBreak();
        } 
        else if (!nm.matches("[a-zA-Z]+")) {
            // prevent numbers and special characters
            UI.println("Enter only character A-Z");
            lineBreak();
        } 
        else if (nm.length() < 4) {
            UI.println("Enter a valid pokèmon name");
            lineBreak();
        } 
        else {
            // name is valid
            valid = true;
        }
        return valid;
    }
    
    /**
     * A cheaker method that returns true if the 
     * name searched duirng 'find card' is valid
     * @param search takes the name searched
     * @return boolean valid validity of the searched name
     */
    public boolean searchChecker(String search) {
        // boolean for validity check
        boolean valid = false;
        
        if (search.isEmpty()) {
            // prevent empty input
            UI.println("Don't leave it blank");
            lineBreak();
        } 
        else {
            // search is valid
            valid = true;
        }
        return valid;
    }
    
    /**
     * A checker method that returns true if a price input is valid
     * @param priceStr takes the price entered as a string
     * @return boolean valid validity of the price
     */
    public boolean priceChecker(String priceStr) {
        // boolean for validity check
        boolean valid = false;
        
        try {
            if (priceStr.isEmpty()) {
                // prevent blank input
                UI.println("Don't leave it blank");
                lineBreak();
            } 
            else { // if parsable to double
                // convert string price to double 
                priceD = Double.parseDouble(priceStr);
                
                if (priceD <= 0) {
                    // catch negative numerical input
                    UI.println("Enter a value over 0.00");
                    lineBreak();
                } 
                else if (!dPChecker(priceStr)) {
                    // limit to 2 decimal places
                    UI.println("Enter a maximum of 2 D.P.");
                    lineBreak();
                } 
                else {
                    // store the valid price
                    price = priceD;
                    valid = true;
                }
            }
        } 
        catch (NumberFormatException e) { // if NOT parsable to double
            // prevent input with wrong data type 
            UI.println("Enter a valid price");
            lineBreak();
        }
        return valid;
    }
    
    /**
     * Check number of decimal places
     * @param priceStr takes the price entered as a string
     * @return boolean valid validity of the d.p of price
     */
    public boolean dPChecker(String priceStr) {
        // check number of decimal places of price
        if (priceS.contains(".")) {
            // store index of dot
            int index = priceS.indexOf(".");
            
            // check length after the dot
            int dP = priceS.substring(index + INCREMENT).length();
            
            // prevents more than 2 d.p.
            if (dP > MAXDP) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Clear text and graphics
     * @param action for the click motion
     * @param x for x position of the mouse click
     * @param y for y position of the mouse click
     */
    public void doMouse(String action, double x, double y) {
        if (pokemonCard != null) { // ensure pokemon obj has been created
            if (action.equals("clicked")) {
                if (pokemonCard.onCard(x, y)) {
                    UI.clearPanes();
                }
            }
        }
    }
    
    /**
     * Break a line 
     */
    public void lineBreak() {
        UI.print(System.lineSeparator());
    }
}
