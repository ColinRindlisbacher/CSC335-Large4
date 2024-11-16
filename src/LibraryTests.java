import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

class LibraryTests {
	
	Book b1 = new Book("What Comes After 9?", "Roman Roy");
	Book b2 = new Book("Interested In Politics From A Very Young Age", "Connor Roy");
	Book b3 = new Book("Nero and Sporus", "Tom Wambsgans");
	
	RatedBooks myRatings = new RatedBooks();
	ReadBooks myReadBooks = new ReadBooks();
	HashMap<Book, Rating> ratingsHashMap = new HashMap<Book, Rating>();
	HashMap<Book, Boolean> readHashMap = new HashMap<Book, Boolean>();
	
	LibraryController myLib = new LibraryController();
	
	//Testing Book class
	
	@Test
	void testGetTitle() {
		assertEquals(b3.getTitle(), "Nero and Sporus");
	}
	
	@Test
	void testGetAuthor() {
		assertEquals(b2.getAuthor(), "Connor Roy");
	}
	
	@Test
	void testToString() {
		assertEquals(b1.toString(), "What Comes After 9? : Roman Roy");
	}
	
	// Test Rated Books
	
	@Test
	void testGetRating() {
		Rating myRating = new Rating(1);
		assertEquals(myRating.getRating(), 1);
	}
	
	@Test
	void testPutAndGetRatings() {
		myRatings.putRating(b1, 3);
		ratingsHashMap = myRatings.getRatings();
		assertEquals(ratingsHashMap.get(b1).getRating(), 3);
	}
	
	// Test Read Books
	
	@Test
	void testAddNewBook() {
		myReadBooks.addNewBook(b1);
		assertFalse(myReadBooks.isRead(b1));
	}
	
	@Test
	void testAddDuplicateBook() {
		myReadBooks.addNewBook(b1);
		myReadBooks.addNewBook(b1);
		assertFalse(myReadBooks.isRead(b1));
	}
	
	@Test
	void testReadBook() {
		myReadBooks.addNewBook(b1);
		myReadBooks.readBook(b1);
		assertTrue(myReadBooks.isRead(b1));
	}
	
	@Test
	void testGetBooks() {
		myReadBooks.addNewBook(b1);
		assertFalse(myReadBooks.getBooks().get(b1));
		myReadBooks.readBook(b1);
		assertTrue(myReadBooks.getBooks().get(b1));
	}
	
	// Test Library Controller and Librarian
	
	// Test Search
	@Test
	void testSearchByTitle() {
		myLib.addBook(b1);
		assertEquals(myLib.search("title", "What Comes After 9?"), "What Comes After 9? : Roman Roy\n");
	}
	
	@Test
	void testSearchByAuthor() {
		myLib.addBook(b3);
		assertEquals(myLib.search("author", "Tom Wambsgans"), "Tom Wambsgans : Nero and Sporus\n");
	}
	
	@Test
	void testSearchByRating() {
		myLib.addBook(b2);
		myLib.rate("Interested In Politics From A Very Young Age", "Connor Roy", 1);
		assertEquals(myLib.search("rating", "1"), "Interested In Politics From A Very Young Age : Connor Roy\n");
	}
	
	@Test
	void testSearchByInvalidRating() throws NumberFormatException{
		myLib.addBook(b1);
		assertThrows(NumberFormatException.class, () -> myLib.search("rating", "a"));
	}
	
	@Test
	void testNoResultsFound() {
		myLib.addBook(b1);
		myLib.addBook(b3);
		myLib.rate("Nero and Sporus", "Tom Wambsgans", 2);
		assertEquals(myLib.search("Title", "Cold and Inhospitable"), "");
		assertEquals(myLib.search("author", "Shiv Roy"), "");
		assertEquals(myLib.search("rating", "1"), "");
	}
	
	@Test
	void testInvalidSearchMethod() {
		assertEquals(myLib.search("length", "325"), "");
	}
	
	// Test getBooks
	
	@Test
	void testBooksSortedByTitle() {
		myLib.addBook(b1);
		myLib.addBook(b2);
		myLib.addBook(b3);
		assertEquals(myLib.getBooks("title"), "Interested In Politics From A Very Young Age : Connor Roy\n"
				+ "Nero and Sporus : Tom Wambsgans\n"
				+ "What Comes After 9? : Roman Roy\n");
	}
	@Test
	void testBooksSortedByAuthor() {
		myLib.addBook(b1);
		myLib.addBook(b2);
		myLib.addBook(b3);
		assertEquals(myLib.getBooks("author"), "Connor Roy : Interested In Politics From A Very Young Age\n"
				+ "Roman Roy : What Comes After 9?\n"
				+ "Tom Wambsgans : Nero and Sporus\n");
	}
	
	@Test
	void testGetReadAndUnreadBooks() {
		myLib.addBook(b1);
		myLib.addBook(b2);
		myLib.addBook(b3);
		
		myLib.setToRead("What Comes After 9?", "Roman Roy");
		myLib.setToRead("Interested In Politics From A Very Young Age", "Connor Roy");
		assertEquals(myLib.getBooks("read"), "Interested In Politics From A Very Young Age : Connor Roy\n"
				+ "What Comes After 9? : Roman Roy\n");
		
		assertEquals(myLib.getBooks("unread"), "Nero and Sporus : Tom Wambsgans\n");
	}
	
	@Test
	void testInvalidGetMethod() {
		assertEquals(myLib.getBooks("rating"), "");
	}
	
	// Test Other Librarian Features
	@Test
	void testLibSetToRead() {
		myLib.addBook(b1);
		myLib.setToRead("What Comes After 9?", "Roman Roy");
		assertEquals(myLib.getBooks("read"), "What Comes After 9? : Roman Roy\n");
	}
	
	@Test
	void testReadBooksNotInLibrary() {
		myLib.addBook(b1);
		myLib.setToRead("Cold and Inhospitable", "Roman Roy");
		myLib.setToRead("What Comes After 9?", "Shiv Roy");
		myLib.setToRead("Cold and Inhospitable", "Shiv Roy");
		assertEquals(myLib.getBooks("read"), "");
	}
	
	@Test
	void testLibRateBookNotInLibrary() {
		myLib.rate("Cold and Inhospitable", "Shiv Roy", 4);
		assertEquals(myLib.search("rating", "4"), "");
	}
	
	@Test
	void testSuggestBook() {
		myLib.addBook(b1);
		assertEquals(myLib.suggestRead(), b1);
	}
	
	@Test
	void testSuggestBook_EmptyCollection() {
		assertEquals(myLib.suggestRead(), null);
	}
	
	@Test
	void testSuggestBook_NoUnreadBooks() {
		myLib.addBook(b3);
		myLib.setToRead("Nero and Sporus", "Tom Wambsgans");
		assertEquals(myLib.suggestRead(), null);
	}

	@Test
	void testAddBooksFromFile() throws FileNotFoundException{
		myLib.addBooks("books3.txt");
		assertEquals(myLib.getBooks("title"), "Cold and Inhospitable : Shiv Roy\n"
				+ "Gregxit : Cousin Greg\n");
	}
	
	@Test
	void testAddBooksFromFile_InvalidBook() throws FileNotFoundException{
		myLib.addBooks("badBooks.txt");
		assertEquals(myLib.getBooks("title"), "");
	}
	
	@Test
	void testAddBooksFromFile_FileDoesNotExist() throws FileNotFoundException{
		assertThrows(FileNotFoundException.class, () -> myLib.addBooks("books4.txt"));
		assertEquals(myLib.getBooks("title"), "");
	}
	
}
