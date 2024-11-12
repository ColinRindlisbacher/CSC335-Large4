/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: RatedBooks.java
 * Purpose: Holds information about books and their ratings.
 * These ratings can be updated.
 * Encaspulation: Encaspulation is maintained by having a private instance
 * variable that has a getter method that returns a copy of the referenced HashMap
 * preventing corruption from occuring from outside code.
 */

import java.util.HashMap;

public class RatedBooks {
    private HashMap<Book, Rating> bookHashMap;

    public RatedBooks() {
        this.bookHashMap = new HashMap<Book, Rating>();
    }

    public HashMap<Book, Rating> getRatings() {
        return new HashMap<Book, Rating>(bookHashMap);
    }

    public void putRating(Book currBook, int rating) {
        bookHashMap.put(currBook, new Rating(rating));
    }
}
