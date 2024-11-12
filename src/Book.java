/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: Book.java
 * Purpose: Represents a book that has a title and an author.
 * Encaspulation: Encaspulation is maintained by making the instance
 * variables private, making the class immutable by only allowing to
 * get the information of the book rather than change it once it's
 * created, and by having no escaping references of mutable objects (Strings are immutable).
 */

public class Book {

    private final String title;
    private final String author;
    
    /**
     * @pre title !=null && author !=null
     */
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String toString() {
        return String.format("%s : %s", title, author);
    }
}
