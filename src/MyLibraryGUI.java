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

//import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

 public class MyLibraryGUI extends JFrame{
 
	private LibraryController controller;
	private JPanel optionsPanel;
	private JPanel displayPanel;
	private JSplitPane splitPane;
	
	
	public MyLibraryGUI() {
		this.controller = new LibraryController();
		this.setTitle("CroRind Library");
		this.setSize(750,500);
		this.setUp();
	}
	
	private void setUp() {
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		JLabel welcomeLabel = new JLabel("Welcome to the CroRind Library!");

		optionsPanel.add(welcomeLabel);
		JLabel optionsLabel = new JLabel("Please choose from one of the below options:");
		optionsPanel.add(optionsLabel);

		displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Create a JSplitPane to divide the options panel and display panel
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, displayPanel);
        splitPane.setDividerLocation(275); // Set initial divider position
        splitPane.setResizeWeight(0.3); // Allocate space for the left panel
        splitPane.setContinuousLayout(true);

        // Add the split pane to the frame
        this.add(splitPane);

		// ends the program when window is closed
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		this.setVisible(true);
	}
	
	public void start() {
		ArrayList<JRadioButton> JRadioButtons = new ArrayList<JRadioButton>();
		JRadioButtons.add(new JRadioButton("Search"));
		JRadioButtons.add(new JRadioButton("Add A Book"));
		JRadioButtons.add(new JRadioButton("Set To Read"));
		JRadioButtons.add(new JRadioButton("Rate A Book"));
		JRadioButtons.add(new JRadioButton("Get List of Books"));
		JRadioButtons.add(new JRadioButton("Get Suggestion"));
		JRadioButtons.add(new JRadioButton("Add Book File"));

		ButtonGroup G = new ButtonGroup();

		for(JRadioButton currButton: JRadioButtons) {
			optionsPanel.add(currButton);
			G.add(currButton);
		}

		// Should we have a switch case for this? I think it could look a lot nicer

		
		// Add functionality to "Search" button
		JRadioButtons.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySearchForm();
            }
        });

		// Add functionality to "Add A Book" button
        JRadioButtons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddBookForm();
            }
        });

		// Add functionality to "Set to Read" button
		JRadioButtons.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySetReadForm();
            }
        });

		// Add functionality to "Rate A Book" button
		JRadioButtons.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRateBookForm();
            }
        });

		// Add functionality to "Get List of Books" button
		JRadioButtons.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayGetListForm();
            }
        });

		// Add functionality to "Get Suggestion" button
		JRadioButtons.get(5).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				displayBookSuggestionForm();
			}
		});

		// Add functionality to "Add Book File" button
		JRadioButtons.get(6).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				displayAddBookFileForm();
			}
		});
	}



	private void displayAddBookFileForm(){
		// Clear the display panel
		displayPanel.removeAll();

		// Add filename prompt and text field
		JLabel fileNameLabel = new JLabel("Please enter file name:");
		JTextField fileNameField = new JTextField(20);
		fileNameField.setMaximumSize(new Dimension(300, 25));
		displayPanel.add(fileNameLabel);
		displayPanel.add(fileNameField);

		// Add an Add File button
        JButton addFileButton = new JButton("Add File");
        displayPanel.add(addFileButton);

		// Add functionality to the Add File button
        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameField.getText();
                JOptionPane.showMessageDialog(
                    displayPanel,
                    "Attempting to add book file, " + fileName + " to Library.",
					"Book file add attempt",
                    JOptionPane.INFORMATION_MESSAGE
                );
                // Add book file to Library
				try {
					controller.addBooks(fileName);
					JOptionPane.showMessageDialog(
                    displayPanel,
                    "Successfully added, " + fileName + " to Library.",
					"Book file added",
                    JOptionPane.INFORMATION_MESSAGE
                );
				} catch (FileNotFoundException f) {
					// Display error message
					JOptionPane.showMessageDialog(
                    displayPanel,
                    "Error File not found: " + fileName,
					"File Error",
                    JOptionPane.ERROR_MESSAGE
                );
				}
            }
        });

        // Refresh the display panel to show the new components
        displayPanel.revalidate();
        displayPanel.repaint();
	}

	private void displayBookSuggestionForm(){
		// Clear the display panel
		displayPanel.removeAll();

		JButton suggestButton = new JButton("Get Suggestion");
		displayPanel.add(suggestButton);

		suggestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				Book suggestion = controller.suggestRead();
				if(suggestion == null){
					JOptionPane.showMessageDialog(
                    	displayPanel,
                    	"You have read all the books!",
						"No more Books!",
                    	JOptionPane.INFORMATION_MESSAGE
                	);
				}
				else{
					// clear panel so we can display book info
					displayPanel.removeAll();
					// display book info
					JPanel bookPanel = new JPanel();
					bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));

					JLabel bookIntroLabel = new JLabel("Here is a book suggestion that you haven't read: ");
					JLabel titleLabel = new JLabel("Title: " + suggestion.getTitle());
					JLabel authorLabel = new JLabel("Author: " + suggestion.getAuthor());

					bookPanel.add(bookIntroLabel);
					bookPanel.add(titleLabel);
					bookPanel.add(authorLabel);

					displayPanel.add(bookPanel);
					// refresh display to show book suggestion
					displayPanel.revalidate();
        			displayPanel.repaint();
				}
			}
		});

		// Refresh the display panel to show the new components
        displayPanel.revalidate();
        displayPanel.repaint();
	}

	private void displayGetListForm(){
		// Clear the display panel
        displayPanel.removeAll();

		// Add prompt and text field
		JLabel sortLabel = new JLabel("Please select what method you would like to sort the books(title,author,read,unread):");
		JTextField sortField = new JTextField(20);
		sortField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(sortLabel);
        displayPanel.add(sortField);

		// add area and scroll to display our resulting list
		JTextArea listArea = new JTextArea(15, 50);
		listArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(listArea);
		displayPanel.add(scrollPane);

		// Add a submit button
        JButton submitButton = new JButton("Submit");
        displayPanel.add(submitButton);

		submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String method = sortField.getText();

				if(method.equalsIgnoreCase("title")  ||
				   method.equalsIgnoreCase("author") ||
				   method.equalsIgnoreCase("read")   ||
				   method.equalsIgnoreCase("unread")){
					String bookList = controller.getBooks(method);
					listArea.setText(bookList);
				}
				else{
					JOptionPane.showMessageDialog(
                    	displayPanel,
                    	"Invalid input: " + method +  ", Please enter 'title', 'author', 'read', or 'unread'.",
						"Error",
                    	JOptionPane.ERROR_MESSAGE
                	);
					// clear text area 
					listArea.setText("");
				}
            }
        });		

		// Refresh the display panel to show the new components
        displayPanel.revalidate();
        displayPanel.repaint();
	}

	private void displayRateBookForm(){

		// Clear the display panel
		displayPanel.removeAll();
		
		// Add title prompt and text field
        JLabel titleLabel = new JLabel("Enter title of book that you want to rate:");
        JTextField titleField = new JTextField(20);
		titleField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(titleLabel);
        displayPanel.add(titleField);

        // Add author prompt and text field
        JLabel authorLabel = new JLabel("Enter the author of book that you want to rate:");
        JTextField authorField = new JTextField(20);
		authorField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(authorLabel);
        displayPanel.add(authorField);

		// Add rate prompt and text field
		JLabel rateLabel = new JLabel("Please enter rating for book(1-5):");
		JTextField rateField = new JTextField(20);
		rateField.setMaximumSize(new Dimension(300, 25));
		displayPanel.add(rateLabel);
		displayPanel.add(rateField);

		// Add a submit button
        JButton rateButton = new JButton("Rate Book");
        displayPanel.add(rateButton);

		// Add functionality to the submit button
		rateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				String author = authorField.getText();
				String rating = rateField.getText();

				// ArrayList to check against if string is 1-5
				ArrayList<String> range = new ArrayList<String>();
				range.add("1");
				range.add("2");
				range.add("3");
				range.add("4");
				range.add("5");

				if(range.contains(rating)){
					JOptionPane.showMessageDialog(
						displayPanel,
						"Book rated:\nTitle: " + title + "\nAuthor: " + author + "\nRating: " + rating,
						"Book Rated",
						JOptionPane.INFORMATION_MESSAGE
					);
					int r = Integer.parseInt(rating);
					controller.rate(title, author, r);
				}
				else{
					JOptionPane.showMessageDialog(
						displayPanel,
						"Invalid input. Please enter an integer 1-5",
						"Rating error",
						JOptionPane.ERROR_MESSAGE
					);
				}
				
			}
		});
		
		// Refresh the display panel to show the new components
		displayPanel.revalidate();
		displayPanel.repaint();
	}

	private void displaySetReadForm(){
		// Clear the display panel
        displayPanel.removeAll();

        // Add title prompt and text field
        JLabel titleLabel = new JLabel("Enter title of book that you read:");
        JTextField titleField = new JTextField(20);
		titleField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(titleLabel);
        displayPanel.add(titleField);

        // Add author prompt and text field
        JLabel authorLabel = new JLabel("Enter the author of book that you read:");
        JTextField authorField = new JTextField(20);
		authorField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(authorLabel);
        displayPanel.add(authorField);

		// Add a submit button
        JButton addButton = new JButton("Read Book");
        displayPanel.add(addButton);

		        // Add functionality to the submit button
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = titleField.getText();
				String author = authorField.getText();
				JOptionPane.showMessageDialog(
					displayPanel,
					"Book read:\nTitle: " + title + "\nAuthor: " + author,
					"Book Read",
					JOptionPane.INFORMATION_MESSAGE
				);
				// Set book to read
				controller.setToRead(title, author);
			}
		});
		
		// Refresh the display panel to show the new components
		displayPanel.revalidate();
		displayPanel.repaint();

	}

	private void displayAddBookForm(){
        // Clear the display panel
        displayPanel.removeAll();

        // Add title prompt and text field
        JLabel titleLabel = new JLabel("Please enter the title of the book to be added:");
        JTextField titleField = new JTextField(20);
		titleField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(titleLabel);
        displayPanel.add(titleField);

        // Add author prompt and text field
        JLabel authorLabel = new JLabel("Please enter the author of the book to be added:");
        JTextField authorField = new JTextField(20);
		authorField.setMaximumSize(new Dimension(300, 25));
        displayPanel.add(authorLabel);
        displayPanel.add(authorField);

        // Add a submit button
        JButton addButton = new JButton("Add Book");
        displayPanel.add(addButton);

        // Add functionality to the submit button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                JOptionPane.showMessageDialog(
                    displayPanel,
                    "Book added:\nTitle: " + title + "\nAuthor: " + author,
                    "Book Added",
                    JOptionPane.INFORMATION_MESSAGE
                );
                // Add book to Library
				Book newBook = new Book(title, author);
				controller.addBook(newBook);
            }
        });

        // Refresh the display panel to show the new components
        displayPanel.revalidate();
        displayPanel.repaint();
    }

	private void displaySearchForm(){
		// Clear the display panel
		displayPanel.removeAll();

		JLabel searchMethodPrompt = new JLabel("Please enter method of search(title, author, or rating):");
		JTextField searchMethodField = new JTextField(20);
		searchMethodField.setMaximumSize(new Dimension(300, 25));
		displayPanel.add(searchMethodPrompt);
		displayPanel.add(searchMethodField);

		JLabel search = new JLabel("Please enter what you want to search for:");
		JTextField searchField = new JTextField(20);
		searchField.setMaximumSize(new Dimension(300, 25));
		displayPanel.add(search);
		displayPanel.add(searchField);

		// add area and scroll to display our resulting list
		JTextArea listArea = new JTextArea(15, 50);
		listArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(listArea);
		displayPanel.add(scrollPane);

		// Add a submit button
        JButton submitButton = new JButton("Submit");
        displayPanel.add(submitButton);

		submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String method = searchMethodField.getText();
				String srch = searchField.getText();

				// check method is valid
				if(method.equalsIgnoreCase("title")  ||
				   method.equalsIgnoreCase("author") ||
				   method.equalsIgnoreCase("rating")){
					try {
						String bookList = controller.search(method, srch);
						listArea.setText(bookList);
					} catch (NumberFormatException f) {
						JOptionPane.showMessageDialog(
                    		displayPanel,
                    		"Invalid rating input, please enter a number.",
							"Rating input Error",
                    		JOptionPane.ERROR_MESSAGE
                		);
					}
				}
				else{
					JOptionPane.showMessageDialog(
                    	displayPanel,
                    	"Invalid input: " + method +  ", Please enter 'title', 'author', 'rating'.",
						"Error",
                    	JOptionPane.ERROR_MESSAGE
                	);
					// clear text area 
					listArea.setText("");
				}
            }
        });
		
		// Refresh the display panel to show the new components
		displayPanel.revalidate();
        displayPanel.repaint();
	}

	public static void main(String[] args) {
		MyLibraryGUI myLib = new MyLibraryGUI();
		myLib.start();
		myLib.setVisible(true);
	}
 
 }
 