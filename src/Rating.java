/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: Rating.java
 * Purpose: Holds information on the rating of a book.
 * Encaspulation: Encaspulation is maintained by only having a method to get the
 * rating but not set it, making it immutable. Plus the only type returned is primitive
 * meaning no escaping references.
 */

public class Rating {
    private int rating;
    
    /**
     * @pre 1 <= initRating <= 5
     */
    public Rating(int initRating) {
        this.rating = initRating;
    }

    public int getRating(){
        return rating;
    }
}
