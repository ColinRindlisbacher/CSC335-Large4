/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: Librarian.java
 * Purpose: Serves as the central model to the Library Program
 * that performs actions as specified by the MyLibrary class such as
 * rating books, retrieving all books, and searching for books.
 * Encaspulation: Encaspulation is maintained by having private instance variables
 * that do not have getters because the direct information from them isn't needed.
 * Also, all methods either return primitive types or immutable types (including our
 * book class) preventing any corruption of important data.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Librarian {
    
    private RatedBooks ratings;
    private ReadBooks readStatus;
    private ArrayList<Book> allBooks;

    public Librarian() {
        this.ratings = new RatedBooks();
        this.readStatus = new ReadBooks();
        allBooks = new ArrayList<Book>();
    }

     /**
     * @pre method.equals("title") || method.equals("author") || method.equals("rating")
     */
    public String search(String method, String search) throws NumberFormatException{
        // store search results into new ArrayList
        ArrayList<Book> bookResults = new ArrayList<Book>();

        // each method will loop through all books and look for matches
        if(method.equalsIgnoreCase("title")){
            for(Book currBook : allBooks){
                if(currBook.getTitle().equalsIgnoreCase(search))
                    bookResults.add(currBook);
            }
        }

        else if(method.equalsIgnoreCase("author")){
            for(Book currBook : allBooks){
                if(currBook.getAuthor().equalsIgnoreCase(search))
                    bookResults.add(currBook);
            }
        }

        else if(method.equalsIgnoreCase("rating")){
                // ratings are stored in hashmap, so grab that to do comparison
                int rating = Integer.parseInt(search);
                HashMap<Book, Rating> bookRatings = ratings.getRatings();
    
                for(Book currBook: allBooks){
                    Rating currRating = bookRatings.get(currBook);
                    if(currRating != null && currRating.getRating() == rating){
                        bookResults.add(currBook);
                    }
                }          
            }
        return arrayToString(bookResults, method);
    }

    /**
     * @param newBook = a book to be added.
     * Purpose: adds books to the full collection and initializes
     * their read status to unread.
     */
    public void addBook(Book newBook) {
        allBooks.add(newBook);
        readStatus.addNewBook(newBook);
    }

    /**
     * @param title = title of book to set to read.
     * @param author = author of book to set to read.
     */
    public void setToRead(String title, String author) {
        Book foundBook = findBook(title, author);
        if (foundBook != null) {
            readStatus.readBook(foundBook);
        }
        
    }

    /**
     * @param title = title of book to be found.
     * @param author = author of book to set to read.
     */

    private Book findBook(String title, String author) {
        for (Book currBook : allBooks) {
            if(title.equals(currBook.getTitle()) && author.equals(currBook.getAuthor())) {
                return currBook;
            }
        }
        return null;
    }

    /**
     * @param title = title of book to set to read.
     * @param author = author of book to set to read.
     * @param newRating = the rating that is wanted to be set.
     * @pre newRating int >= 1 and <= 5
     */
    public void rate(String title, String author, int newRating) {
        Book bookToUpdate = findBook(title, author);
        if(bookToUpdate != null){
            ratings.putRating(bookToUpdate, newRating);
        }
        
    }

    /**
     * @param method = the method that you want to list the books by.
     * @pre method.equals("title") || method.equals("author") || method.equals("read")  || method.equals("unread") 
     */
    public String getBooks(String method) {
        // Store all books in a new array as to not mess with the order of the original array.
        ArrayList<Book> booksToPrint = new ArrayList<Book>();
        if (method.equals("title")) {
            // Sort using Title Comparator if we want list by title.
            booksToPrint = new ArrayList<Book>(allBooks);
            Collections.sort(booksToPrint, new TitleComparator());

        } else if (method.equals("author")){
            // Sort using Author Comparator if we want list by title.
            booksToPrint = new ArrayList<Book>(allBooks);
            Collections.sort(booksToPrint, new AuthorComparator());

        } else if (method.equals("read")) {

            for (Book currBook : allBooks) {
                if (readStatus.isRead(currBook)) booksToPrint.add(currBook);
            }
            // Sort using Title Comparator as default sorting.
            Collections.sort(booksToPrint, new TitleComparator());

        } else if (method.equals("unread")) {

            for (Book currBook : allBooks) {
                if (!readStatus.isRead(currBook)) booksToPrint.add(currBook);
            }
            // Sort using Title Comparator as default sorting.
            Collections.sort(booksToPrint, new TitleComparator());

        } 
        return arrayToString(booksToPrint, method);
    }

    /**
     * @param booksToPrint = array of books that should be listed in string.
     * @param method = method in which books are being listed by.
     * @return = a random book that has not been read yet.
     */

    private String arrayToString(ArrayList<Book> booksToPrint, String method) {
        String printString = "";
        if (method.equals("author")) {
            for (Book currBook : booksToPrint) {
                printString += String.format("%s : %s\n", currBook.getAuthor(), currBook.getTitle());
            }
        } else {
            for (Book currBook : booksToPrint) {
                printString += String.format("%s : %s\n", currBook.getTitle(), currBook.getAuthor());
            }
        }
        return printString;
    }

    /**
     * @return = a random book that has not been read yet.
     */
    public Book suggestRead() {
        ArrayList<Book> suggestions = new ArrayList<Book>(allBooks);
        if (suggestions.isEmpty()) return null;
        // Make a copy and shuffle the copy and pick the first book.
        Collections.shuffle(suggestions);
        int i = 0;
        Book currBook = suggestions.get(i);

        // Loop until the current book hasn't been read or you've been through all your books.
        while(readStatus.isRead(currBook) && i < suggestions.size()) {
            currBook = suggestions.get(i);
            i++;
        }
        if (i == suggestions.size()) {
            return null;
        }
        return currBook;
        
    }

    /**
     * @param fileName = name of txt file to be read in
     * 
     * @throws FileNotFoundException if the txt file cannot be found
     */
    public void addBooks(String fileName) throws FileNotFoundException{
			File file = new File(fileName);
			Scanner fiScanner = new Scanner(file);
						
			while(fiScanner.hasNextLine()){
				String line = fiScanner.nextLine();
				String[] bookInfo = line.split(";");
				// make sure line is valid length of 2
				if(bookInfo.length == 2){
					String newTitle = bookInfo[0].trim();
					String newAuthor = bookInfo[1].trim();
					Book fileBook = new Book(newTitle, newAuthor);
					addBook(fileBook);
			    }
			}
			fiScanner.close();
    }
}
