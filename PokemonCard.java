import java.util.ArrayList;
import ecs100.*;
/**
 * Support class of Pokemon Card Collection
 * A Pokemon Card contains key, name, price
 *
 * @author Jasmine Yip
 * @version 23/05/2022
 */
public class PokemonCard
{
    // instance variables 
    private int id;
    private String name;
    private double price;
    private String image;
    static final String DEFAULT_IMAGE = "default.png"; // set default image 
    
    // position and size of card image
    private int locX = 100;
    private int locY = 100;
    static private final double WIDTH = 100.0;
    static private final double HEIGHT = 150.0;
    
    // boolean to check if clicked on card
    private boolean isOnCard;

    /**
     * Constructor for objects of class PokemonCard
     * @param key int for the id of pokemon card
     * @param nm for the name of the pokemon card
     * @param pr double for the price of the pokemon card
     * @param img for the image of the pokemon card
     */
    public PokemonCard(int key, String nm, double pr, String img)
    {
        // initialise instance variables
        id = key;
        name = nm;
        price = pr;
        
        // setting image
        if (img == null) {
            this.image = DEFAULT_IMAGE; // add default img if user clicks cancel
        } 
        else {
            this.image = img; // set img as chosen one
        }
    }

    /**
     * Pokemon constructor overloading
     * Set default image 
     * @param key int for the id of pokemon card
     * @param nm for the name of the pokemon card
     * @param pr for the price of the pokemon card
     */
    public PokemonCard(int key, String nm, double pr) {
        this(key, nm, pr, DEFAULT_IMAGE);
    }
    
    /**
     * Display image on GUI
     * @param toLeft int for the distance to the left
     */
    public void displayContact(int toLeft) {
        // display card image
        UI.drawImage(image, toLeft, locY, WIDTH, HEIGHT);
    }
    
    /**
     * Getter for Pokemon card id
     * @return the current id
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Getter for Pokemon card name
     * @return the current name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Getter for Pokemon card price
     * @return the current price
     */
    public double getPrice() {
        return this.price;
    }
    
    /**
     * Check if position of mouse is on the card
     * @return the boolean onCard
     * @param x for x position of mouse
     * @param y for y position of mouse
     */
    public boolean onCard(double x, double y) {
        // check if position of the click is on first card
        if ((x >= locX) && 
            (x <= locX + WIDTH) && 
            (y >= locY) && 
            (y <= locY + HEIGHT)) 
        {
            isOnCard = true;
        } 
        else {
            isOnCard = false;
        }
        return isOnCard;
    }
    
    /**
     * Break a line 
     */
    public void lineBreak() {
        UI.print(System.lineSeparator());
    }
}
