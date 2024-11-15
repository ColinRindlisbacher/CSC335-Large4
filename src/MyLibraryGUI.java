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
import javax.swing.JSplitPane;
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
		//this.add(optionsPanel);

		displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        // Create a JSplitPane to divide the options panel and display panel
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionsPanel, displayPanel);
        splitPane.setDividerLocation(275); // Set initial divider position
        splitPane.setResizeWeight(0.3); // Allocate space for the left panel
        splitPane.setContinuousLayout(true);

        // Add the split pane to the frame
        this.add(splitPane);

        // End the program when the window is closed
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		// Should we have a switch case? I have it not for now but think bout it

		// Add functionality to "Add A Book" button
        JRadioButtons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddBookForm();
            }
        });

		JRadioButtons.get(6).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				displayAddBookFileForm();
			}
		});

	}

	/*
	 * I sort of have questions about input validation/error checking.
	 * Does error checking need to happen/show up in the GUI or is handing
	 * it in the terminal okay? addBooks just gets a filename and the Librarian
	 * addBooks handles the filenotfound but that prints to the terminal.. is that
	 * Okay or does the error checking need to happen here so that we can print
	 * error messages in the GUI? Other classes have input validation in MyLibraryText
	 */

	private void displayAddBookFileForm(){
		// Clear the display panel
		displayPanel.removeAll();

		// Add filename prompt and text field
		JLabel fileNameLabel = new JLabel("Please enter file name:");
		JTextField fileNameField = new JTextField(20);
		fileNameField.setMaximumSize(new Dimension(300, 25));
		displayPanel.add(fileNameLabel);
		displayPanel.add(fileNameField);

		// Add a submit button
        JButton addFileButton = new JButton("Add File");
        displayPanel.add(addFileButton);

		// Add functionality to the submit button
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

	private void displayAddBookForm() {
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

	public static void main(String[] args) {
		MyLibraryGUI myLib = new MyLibraryGUI();
		myLib.start();
		myLib.setVisible(true);
	}
 
 }
 