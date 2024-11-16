/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: MyLibrary.java
 * Purpose: Serves as the view/UI for our Library.
 * Gets and validates user inputs and calls the 
 * related methods.
 * Encaspulation: Encaspulation is maintained by having all methods other
 * than main be set to private so that if this was used elsewhere, none of
 * our methods could be called and therefore have no access to the controller/model.
 */

 import java.util.ArrayList;
 import java.util.Scanner;
 
  public class MyLibraryText {
  
      public static void main(String[] args) {
          LibraryController libCon = new LibraryController();
          Scanner s = new Scanner(System.in);
          String input = "";
  
          // Welcome
          System.out.println("Welcome to the CroRind Library!");
  
          // Main loop until user inputs Q or q
          while(!input.equalsIgnoreCase("q")){
              // printing options
              System.out.println("Please choose from one of the below options:");
              System.out.println("1- Search for a book");
              System.out.println("2- Add a book to Library");
              System.out.println("3- Set a book to read");
              System.out.println("4- Rate a book");
              System.out.println("5- Get a list of books");
              System.out.println("6- Get a book suggestion");
              System.out.println("7- Add books file");
              System.out.println("Type 'q' or 'Q' to exit");
  
              input = s.nextLine();
  
              switch(input){
                  // book search selected
                  case "1":
                      searchPrompt(s, libCon);
                  break;
  
                  // add book selected
                  case "2":
                     addBookPrompt(s, libCon);
                     break;
  
                  // setToRead selected
                  case "3":
                     setToReadPrompt(s, libCon);
                      break;
  
                  // rate selected
                  case "4":
                     ratePrompt(s, libCon);
                      break;
  
                  // getBooks selected
                  case "5":
                      getBooksPrompt(s, libCon);
                      break;
  
                  // suggestRead selected
                  case "6":
                      suggestReadPrompt(s, libCon);
                      break;
  
                  // addBooks selected
                  case "7":
                      addBooksPrompt(s, libCon);
                      break;
  
                  // if no other valid option is selected
                  default:
                      // makes sure quit not selected
                      if(!input.equalsIgnoreCase("q")){
                          System.out.println("Error input is not a valid option, try again.");
                      }
                      break;
              }
              System.out.println("");
          }
          s.close();
          System.out.println("Thank you for visiting CroRind Library, have a good day!");
      }
 
     private static void ratePrompt(Scanner s, LibraryController libCon) {
         System.out.println("Enter title for book you want to rate:");
         String title = s.nextLine();
         System.out.println("Enter author for book you want to rate:");
         String author = s.nextLine();
         String  rating = "";
         boolean isValidRate = false;
         // ArrayList to check against if string is 1-5
         ArrayList<String> range = new ArrayList<String>();
         range.add("1");
         range.add("2");
         range.add("3");
         range.add("4");
         range.add("5");
 
             
         // loop to get valid rating(1-5)
         while(!isValidRate){
             System.out.println("Please enter rating for book(1-5):");
             rating = s.nextLine();
             if(range.contains(rating)){
                 isValidRate = true;
             }
             if(!isValidRate){
                 System.out.println("Invalid input. Please enter an integer 1-5");
             }
         }
         int r = Integer.parseInt(rating);
         libCon.rate(title, author, r);
     }
 
     private static void getBooksPrompt(Scanner s, LibraryController libCon) {
         boolean isValidMethod = false;
         String method = "";
         
         while(!isValidMethod){
             System.out.println("Please select what method you would like to sort the books(title,author,read,unread)");
             method = s.nextLine();
             isValidMethod = method.equalsIgnoreCase("title")  ||
                             method.equalsIgnoreCase("author") ||
                             method.equalsIgnoreCase("read") ||
                             method.equalsIgnoreCase("unread");
 
             // print error message if bad input
             if(!isValidMethod){
                 System.out.println("Invalid input. Please enter 'title', 'author', 'read', or 'unread'.");
             }
         }
         System.out.println(libCon.getBooks(method));
     }
 
     private static void suggestReadPrompt(Scanner s, LibraryController libCon) {
         Book suggestion = libCon.suggestRead();
         if (suggestion == null) {
             System.out.println("You've read all your books!");
         } else {
             System.out.println("Here is a book suggestion that you haven't read:\n" + suggestion.toString());
         }
     }
 
     private static void addBooksPrompt(Scanner s, LibraryController libCon) {
         System.out.println("Please enter file name:");
         String fileName = s.nextLine();
 
         if (libCon.addBooks(fileName)){
            System.out.println("Added books from file: " + fileName);
         } else {
            System.out.println("Error: File not found: " + fileName);
         }
         
     }
 
     private static void searchPrompt(Scanner s, LibraryController libCon) {
         String method = "";
         boolean isValidMethod = false;
 
         // keep getting user input until they input "title" or "author" or "rating"
         while(!isValidMethod){
             System.out.println("Please enter method of search(title, author, or rating):");
             method = s.nextLine();
             isValidMethod = method.equalsIgnoreCase("title")  ||
                             method.equalsIgnoreCase("author") ||
                             method.equalsIgnoreCase("rating");
 
             // print error message if bad input
             if(!isValidMethod){
                 System.out.println("Invalid input. Please enter 'title', 'author', or 'rating'.");
             }
         }
 
         String search = "";
         System.out.println("Please enter the " + method + " you would like to search");
         search = s.nextLine();	
         System.out.println("Searching for book by "+ method + "...");
         System.out.println(libCon.search(method, search));
      }
 
     private static void addBookPrompt(Scanner s, LibraryController libCon) {
         // get info about new book
         System.out.println("Please enter title of book to be added:");
         String title = s.nextLine();
         System.out.println("Please enter author of book to be added:");
         String author = s.nextLine();
         System.out.println("Adding book to library...");
         // create new book
         Book newBook = new Book(title, author);
         libCon.addBook(newBook);
     }
 
     private static void setToReadPrompt(Scanner s, LibraryController libCon) {
         System.out.println("Enter title of book that you read:");
         String title = s.nextLine();
         System.out.println("Enter the author of book that you read:");
         String author = s.nextLine();
         // can add status message here if we want
         libCon.setToRead(title, author);
     }
  
  }
  