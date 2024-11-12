/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: LibraryController.java
 * Purpose: Serves as the controller to the Library Program
 * that is the middle ground between the view (the user) and the
 * model that performs all the actions.
 * Encaspulation: Encaspulation is maintained by having a private instance
 * variable and not having a getter for it because the model doesn't need to
 * be returned ever. It also only returns primitive or immutable types, meaning
 * our data cannot be corrupted from outside code.
 */

public class LibraryController {
    private Librarian libModel;
    
    public LibraryController(){
        libModel = new Librarian();
    }


    public String search(String method, String search){
        return libModel.search(method, search);
    }


    public void addBook(Book newBook){
        libModel.addBook(newBook);
    }


    public void setToRead(String title, String author){
        libModel.setToRead(title, author);
    }


    public void rate(String title, String author, int rating){
        libModel.rate(title, author, rating);
    }


    public String getBooks(String method){
       return libModel.getBooks(method);
    }


    public Book suggestRead(){
        return libModel.suggestRead();
    }


    public void addBooks(String fileName){
        libModel.addBooks(fileName);
    }

}
