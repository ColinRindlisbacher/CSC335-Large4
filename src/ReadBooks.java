/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: ReadBooks.java
 * Purpose: Holds information about books and their read status.
 * The read status can only be updated to read, not un-read.
 * Encaspulation is maintained by having a private instance
 * variable that has a getter method that returns a copy of the referenced HashMap
 * preventing corruption from occuring from outside code. Other methods only return
 * primitive types preventing an escaping reference as well. 
 */

import java.util.HashMap;

public class ReadBooks {
    private HashMap<Book, Boolean> bookHashMap;

    public ReadBooks() {
        this.bookHashMap = new HashMap<Book, Boolean>();
    }

    public HashMap<Book, Boolean> getBooks() {
        return new HashMap<Book, Boolean>(bookHashMap);
    }

    public void addNewBook(Book currBook) {
        if (bookHashMap.containsKey(currBook)) {
            bookHashMap.put(currBook, bookHashMap.get(currBook));
        }
        bookHashMap.put(currBook, false);
    }

    public void readBook(Book currBook) {
        bookHashMap.put(currBook, true);
    }

    public boolean isRead(Book currBook) {
        return bookHashMap.get(currBook);
    }
}
