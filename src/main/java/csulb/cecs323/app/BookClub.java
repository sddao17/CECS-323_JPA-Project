/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import java.sql.SQLDataException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class BookClub {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the BookClub
    * class, and create an instance of BookClub in the main.
    */
   private final EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(BookClub.class.getName());

   /**
    * The constructor for the BookClub class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    * @param manager    The EntityManager that we will use.
    */
   public BookClub(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {

      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookClub");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of BookClub and store our new EntityManager as an instance variable.
      BookClub bookClub = new BookClub(manager);


      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      // create and commit blank tables before user interaction
      tx.begin();

      // Create the parents before we try to initialize the children
      List<Publishers> publishers = new ArrayList<>();
      bookClub.createEntity(publishers);

      List<WritingGroups> writingGroups = new ArrayList<>();
      bookClub.createEntity(writingGroups);

      List<IndividualAuthors> individualAuthors = new ArrayList<>();
      bookClub.createEntity(individualAuthors);

      List<AdHocTeams> adHocTeams = new ArrayList<>();
      bookClub.createEntity(adHocTeams);

      List<Books> books = new ArrayList<>();
      bookClub.createEntity(books);

      tx.commit();


      // Menu Handling


      Scanner in = new Scanner(System.in);
      int menuChoice;
      int secondMenuChoice;

      do {
         // Restart the transaction every time we reiterate the main menu
         tx.begin();

         // Print the main menu and validate the user's input
         menuChoice = Functions.checkMainMenuInput();

         String name;
         String email;
         Publishers publisher1;
         AuthoringEntities author1;
         AuthoringEntities author2;
         boolean valid = false;

         // The user input was valid; now we can execute their command
         switch (menuChoice) {

            // User chose to add objects
            case 1:
               // Print the menu to add objects and validate the user's input
               secondMenuChoice = Functions.checkAddObjectInput();

               switch (secondMenuChoice) {
                  // Add a new publisher
                  case 1:
                     String phone;

                     // validating the publisher's name
                     do {
                        System.out.print("Enter the publisher's name:\n >> ");
                        name = in.nextLine();

                        // check if the publisher already exists using its primary key
                        Publishers publisher = bookClub.getPublisher(name);

                        // enforcing primary key constraint
                        if (publisher != null)
                           System.out.println("That publisher already exists in the table; please try again.");
                        else if (name.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the publisher's phone number
                     do {
                        System.out.print("Enter the publisher's phone:\n >> ");
                        phone = in.nextLine();

                        // check if the publisher's phone already exists
                        boolean phoneExists = bookClub.publisherPhoneExists(phone);

                        // enforcing unique key constraint
                        if (phoneExists)
                           System.out.println("That phone number already exists in the table; please try again.");
                        else if (phone.length() > 24)
                           System.out.println("The input exceeds the maximum character length (24); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the publisher's email
                     do {
                        System.out.print("Enter the publisher's email:\n >> ");
                        email = in.nextLine();

                        // check if the publisher's email already exists
                        boolean emailExists = bookClub.publisherEmailExists(email);

                        // enforcing unique key constraint
                        if (emailExists)
                           System.out.println("That email already exists in the table; please try again.");
                        else if (email.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);

                     // add the new publisher to the table
                     publishers.add(new Publishers(name, phone, email));
                     manager.persist(publishers.get(publishers.size() - 1));
                     break;

                  // Add a new book
                  case 2:
                     String isbn;
                     String title;
                     String yearPublished;
                     int yearPublishedToInt = 0;

                     // validating the book's ISBN
                     do {
                        System.out.print("Enter the book's ISBN:\n >> ");
                        isbn = in.nextLine();

                        // check if the book already exists using its primary key
                        Books book = bookClub.getBook(isbn);

                        // enforcing primary key constraint
                        if (book != null)
                           System.out.println("That book already exists in the table; please try again.");
                        else if (isbn.length() > 17)
                           System.out.println("The input exceeds the maximum character length (17); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the book's title
                     do {
                        System.out.print("Enter the book's title:\n >> ");
                        title = in.nextLine();

                        if (title.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the book's publication year
                     do {
                        try {
                           System.out.print("Enter the year published:\n >> ");
                           yearPublished = in.nextLine();
                           yearPublishedToInt = Integer.parseInt(yearPublished);
                           valid = true;

                        } catch (NumberFormatException e) {
                           System.out.println("The input is not an integer; please try again.");
                        }
                     } while (!valid);
                     valid = false;

                     // validating the author's existence within the table
                     do {
                        System.out.print("Enter the author's email:\n >> ");
                        String authorEmail = in.nextLine();
                        author1 = bookClub.getAuthor(authorEmail);

                        Books bookByAuthor = bookClub.getBookByAuthor(title, authorEmail);

                        if (author1 == null) {
                           System.out.println("The author doesn't exist in the table; please try again.");
                        } else if (bookByAuthor != null) {
                           System.out.println("The author has already written a book with this title; " +
                                             "please try again.");
                         } else {
                           valid = true;
                        }

                     } while (!valid);
                     valid = false;

                     // validating the publisher's existence within the table
                     do {
                        System.out.print("Enter the publisher's name:\n >> ");
                        String publisher = in.nextLine();
                        publisher1 = bookClub.getPublisher(publisher);

                        Books bookByPublisher = bookClub.getBookByPublisher(title, publisher);

                        if (publisher1 == null) {
                           System.out.println("The publisher doesn't exist in the table; please try again.");
                        } else if (bookByPublisher != null) {
                           System.out.println("The publisher has already published a book with this title; " +
                                             "please try again.");
                        } else {
                           valid = true;
                        }
                     } while (!valid);

                     // add the new book to the table
                     books.add(new Books(author1, publisher1, isbn, title, yearPublishedToInt));
                     manager.persist(books.get(books.size() - 1));
                     break;

                  // Add a new writing group
                  case 3:
                     String headWriter;
                     String yearFormed;
                     int yearFormedToInt = 0;

                     // validate the writing group's name
                     do {
                        System.out.print("Enter the writing group's name:\n >> ");
                        name = in.nextLine();

                        if (name.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the writing group's email
                     do {
                        System.out.print("Enter the writing group's email:\n >> ");
                        email = in.nextLine();

                        // check if the individual author already exists using its primary key
                        AuthoringEntities group = bookClub.getAuthor(email);

                        // enforcing primary key constraint
                        if (group != null)
                           System.out.println("That group already exists in the table; please try again.");
                        else if (email.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the writing group's head writer name
                     do {
                        System.out.print("Enter the head writer's name:\n >> ");
                        headWriter = in.nextLine();

                        if (headWriter.length() > 80)
                           System.out.println("The input exceeds the maximum character length (80); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validating the writing group's year formed
                     do {
                        try {
                           System.out.print("Enter the year formed:\n >> ");
                           yearFormed = in.nextLine();
                           yearFormedToInt = Integer.parseInt(yearFormed);
                           valid = true;
                        }
                        catch (NumberFormatException e) {
                           System.out.println("The input is not an integer; please try again.");
                        }
                     } while (!valid);

                     // add the new writing group to the table
                     writingGroups.add(new WritingGroups(name, email, headWriter, yearFormedToInt));
                     manager.persist(writingGroups.get(writingGroups.size() - 1));
                     break;

                  // Add a new individual author
                  case 4:
                     // validate the author's name
                     do {
                        System.out.print("Enter the individual author's name:\n >> ");
                        name = in.nextLine();

                        if (name.length() > 30)
                           System.out.println("The input exceeds the maximum character length (30); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validate the author's email
                     do {
                        System.out.print("Enter the individual author's email:\n >> ");
                        email = in.nextLine();

                        // check if the individual author already exists using its primary key
                        AuthoringEntities temp = bookClub.getAuthor(email);

                        // enforcing primary key constraint
                        if (temp != null)
                           System.out.println("That author already exists in the table; please try again.");
                        else if (email.length() > 30)
                           System.out.println("The input exceeds the maximum character length (30); please try again.");
                        else
                           valid = true;

                     } while (!valid);

                     // add the new individual author to the table
                     individualAuthors.add(new IndividualAuthors(name, email));
                     manager.persist(individualAuthors.get(individualAuthors.size() - 1));
                     break;

                  // Add a new ad hoc team
                  case 5:
                     // validate the ad hoc team's name
                     do {
                        System.out.print("Enter the ad hoc team's name:\n >> ");
                        name = in.nextLine();

                        if (name.length() > 30)
                           System.out.println("The input exceeds the maximum character length (30); please try again.");
                        else
                           valid = true;

                     } while (!valid);
                     valid = false;

                     // validate the ad hoc team's email
                     do {
                        System.out.print("Enter the ad hoc team's email:\n >> ");
                        email = in.nextLine();

                        // check if the individual author already exists using its primary key
                        AuthoringEntities temp = bookClub.getAuthor(email);

                        // enforcing primary key constraint
                        if (temp != null)
                           System.out.println("That team already exists in the table; please try again.");
                        else if (email.length() > 30)
                           System.out.println("The input exceeds the maximum character length (30); please try again.");
                        else
                           valid = true;

                     } while (!valid);

                     // add the new ad hoc team to the table
                     adHocTeams.add(new AdHocTeams(name, email));
                     manager.persist(adHocTeams.get(adHocTeams.size() - 1));
                     break;

                  // Assign an individual author to an ad hoc team
                  case 6:
                     do {
                        // validate that the author is not already assigned to the ad hoc team
                        try {
                           // get the instances of each Author corresponding to their primary keys
                           do {
                              System.out.print("Enter the individual author's email:\n >> ");
                              email = in.nextLine();
                              author1 = bookClub.getAuthor(email);

                              // the email should correspond to an IndividualAuthors instance
                              if (!(author1 instanceof IndividualAuthors)) {
                                 System.out.println("That author doesn't exist in the table; please try again.");
                              } else {
                                 valid = true;
                              }
                           } while (!valid);
                           valid = false;

                           do {
                              System.out.print("Enter the ad hoc team's email:\n >> ");
                              email = in.nextLine();
                              author2 = bookClub.getAuthor(email);

                              // the email should correspond to an AdHocTeams instance
                              if (!(author2 instanceof AdHocTeams)) {
                                 System.out.println("That team does not exist in the table; please try again.");
                              } else {
                                 valid = true;
                              }
                           } while (!valid);

                           // cast the AuthoringEntities into their correct types so we can access the team list
                           IndividualAuthors adHocTeamMember = (IndividualAuthors) author1;
                           AdHocTeams adHocTeam = (AdHocTeams) author2;

                           // check if the author is already assigned to that team
                           if (adHocTeamMember.getAdHocTeamsList().contains(adHocTeam)) {
                              throw new SQLDataException();
                           }

                           // add the ad hoc team to the author's list of joined teams
                           adHocTeamMember.addToAdHocTeamsList(adHocTeam);
                           manager.persist(adHocTeamMember);
                        }
                        catch (SQLDataException e) {
                           System.out.println("The author is already part of that team; please try again.");
                           valid = false;
                        }
                     } while (!valid);
                     break;
               }
               break;

            // User chose to list information about an object
            case 2:
               // Print the menu to list information and validate the user's input
               secondMenuChoice = Functions.checkListInformationInput();

               switch (secondMenuChoice) {
                  // list information about a publisher
                  case 1:
                     // If the list of publishers is empty, inform the user and exit the case
                     List<Publishers> publisherList = bookClub.getAllPublishers();

                     if (publisherList.size() == 0) {
                        System.out.println("Cannot show info; there are no publishers.\n");
                        break;
                     }

                     do {
                        System.out.print("Enter the publisher's name:\n >> ");
                        String pubName = in.nextLine();

                        Publishers publisher = bookClub.getPublisher(pubName);

                        if (publisher == null) {
                           System.out.println("That publisher does not exist. Please try again.");
                        } else {
                           valid = true;
                           System.out.println("\n" + publisher);
                        }
                     } while (!valid);

                     break;

                  // list information about a book
                  case 2:
                     // If the list of books if empty, inform the user and exit the case
                     List<Books> booksList = bookClub.getAllBooks();

                     if (booksList.size() == 0) {
                        System.out.println("Cannot show info; there are no books.\n");
                        break;
                     }

                     do {
                        System.out.print("Enter the book's ISBN:\n >> ");
                        String isbn = in.nextLine();

                        // check if the book exists
                        Books book = bookClub.getBook(isbn);

                        if (book == null) {
                           System.out.println("That book doesn't exist in the table; please try again.");
                        } else {
                           System.out.println("\n" + book);
                           valid = true;
                        }
                     } while (!valid);
                     break;

                  // list information about a writing group
                  case 3:
                     // if there are no WritingGroups among the authors, inform the user and exit the case
                     List<AuthoringEntities> allAuthors= bookClub.getAllAuthors();
                     boolean writingGroupExists = false;

                     for (AuthoringEntities a : allAuthors) {
                        if (a instanceof WritingGroups) {
                           writingGroupExists = true;
                           break;
                        }
                     }

                     if (!writingGroupExists) {
                        System.out.println("Cannot show info; there are no writing groups.\n");
                        break;
                     }

                     do {
                        System.out.print("Enter the writing group's email:\n >> ");
                        String writingGroupEmail = in.nextLine();

                        AuthoringEntities writingGroup = bookClub.getAuthor(writingGroupEmail);

                        if (writingGroup == null) {
                           System.out.println("That group doesn't exist in the table; please try again.");
                        } else if (!(writingGroup instanceof WritingGroups)) {
                           System.out.println("Alas, that is not a writing group; please try again.");
                        } else {
                           System.out.println("\n" + writingGroup);
                           valid = true;
                        }

                     } while (!valid);
                     break;
               }
               break;

            // User chose to delete a book
            case 3:
               // If the list of books is empty, inform the user and exit the case
               List<Books> booksList = bookClub.getAllBooks();

               if (booksList.size() == 0) {
                  System.out.println("Cannot delete a book; there are no books.\n");
                  break;
               }

               do {
                  System.out.print("Enter the title of the book:\n >> ");
                  String title = in.nextLine();
                  System.out.print("Enter the name of the publisher:\n >> ");
                  String publisher = in.nextLine();

                  Books book = bookClub.getBookByPublisher(title, publisher);

                  if (book == null) {
                     System.out.println("That book doesn't exist in the table; please try again.");
                  } else {
                     // locate and delete the book
                     Books bookToDelete = manager.find(Books.class, book.getISBN());
                     manager.remove(bookToDelete);

                     valid = true;
                  }
               } while (!valid);
               break;

            // User chose to update a book
            case 4:
               // If the list of books if empty, inform the user and exit the case
               booksList = bookClub.getAllBooks();

               if (booksList.size() == 0) {
                  System.out.println("Cannot update a book; there are no books.\n");
                  break;
               }

               do {
                  System.out.print("Enter the title of the book:\n >> ");
                  String title = in.nextLine();
                  System.out.print("Enter the name of the publisher:\n >> ");
                  String pub = in.nextLine();

                  Books book = bookClub.getBookByPublisher(title, pub);

                  if (book == null) {
                     System.out.println("That book doesn't exist in the table; please try again.");
                  } else {
                     do {
                        System.out.print("Enter the email of the new author:\n >> ");
                        email = in.nextLine();

                        AuthoringEntities newAuthor = bookClub.getAuthor(email);

                        if (newAuthor == null) {
                           System.out.println("That author doesn't exist in the table; please try again.");
                        } else {
                           book.setAuthor(newAuthor);
                           valid = true;
                        }
                     } while (!valid);
                  }
               } while (!valid);

               break;

            // User chose to list the primary keys of an object
            case 5:
               secondMenuChoice = Functions.checkPrimaryKeysInput();
               switch(secondMenuChoice) {
                  // listing primary keys of publishers
                  case 1:
                     List<Publishers> publishersList = bookClub.getAllPublishers();

                     // if the list of publishers is empty, inform the user and exit the case
                     if (publishersList.size() == 0) {
                        System.out.println("Cannot show primary keys; there are no publishers.\n");
                     } else {
                        System.out.println("\nPrimary Keys of Publishers: ");
                        for (Publishers publisher : publishersList) {
                           System.out.println("  - Name: " + publisher.getName());
                        }
                     }
                     break;

                  // listing primary keys of books
                  case 2:
                     booksList = bookClub.getAllBooks();

                     // if the list of books is empty, inform the user and exit the case
                     if (booksList.size() == 0) {
                        System.out.println("Cannot show primary keys; there are no books.\n");
                     } else {
                        System.out.println("\nPrimary Keys of Books: ");
                        for (Books b: booksList) {
                           System.out.println("  - Title: "  + b.getTitle() + "\n    ISBN: " + b.getISBN());
                        }
                     }
                     break;

                  // listing primary keys of authors
                  case 3:
                     List<AuthoringEntities> authors = bookClub.getAllAuthors();

                     // if the list of authors is empty, inform the user and exit the case
                     if (authors.size() == 0) {
                        System.out.println("Cannot show primary keys; there are no authors.\n");
                     } else {
                        String type;

                        System.out.println("\nPrimary Keys of Authoring Entities: ");
                        for (AuthoringEntities a : authors) {
                           if (a instanceof IndividualAuthors) {
                              type = "IndividualAuthors";
                           } else if (a instanceof WritingGroups) {
                              type = "WritingGroups";
                           } else {
                              type = "AdHocTeams";
                           }
                           System.out.println("  - Email: " + a.getEmail() + "\n    Type: " + type);
                        }
                     }
                     break;
               }
               break;
         }

         // At the end of the loop, commit any changes made so the data is visible to others
         tx.commit();

      } while (menuChoice != 0);

      LOGGER.fine("End of Transaction");

   } // End of the main method


   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /**
    * Returns the Publisher object using the provided name.
    * @param name    The name of the publisher you are searching for.
    * @return        The Publisher instance corresponding to that name.
    */
   public Publishers getPublisher(String name) {
      List<Publishers> publishersList = this.entityManager.createNamedQuery("ReturnPublisher", Publishers.class)
              .setParameter(1, name)
              .getResultList();

      if (publishersList.size() == 0) {
         return null;
      } else {
         return publishersList.get(0);
      }
   } // End of getPublisher method

   /**
    * Returns all the publishers within the Publishers table.
    * @return  the list of all publishers
    */
   public List<Publishers> getAllPublishers() {
      return this.entityManager.createNamedQuery("ReturnAllPublishers", Publishers.class)
              .getResultList();
   } // End of getAllPublishers method

   /**
    * Returns true if the publisher's phone already exists in the Publishers table.
    * @param phone    The phone of the publisher you are searching for.
    * @return         True if the phone number already exists; false otherwise.
    */
   public boolean publisherPhoneExists(String phone) {
      List<Publishers> publishersList = this.entityManager.createNamedQuery("ReturnPublisherPhone", Publishers.class)
              .setParameter(1, phone)
              .getResultList();

      return publishersList.size() != 0;
   } // End of publisherPhoneExists method

   /**
    * Returns true if the publisher's email already exists in the Publishers table.
    * @param email    The email of the publisher you are searching for.
    * @return         True if the email already exists; false otherwise.
    */
   public boolean publisherEmailExists(String email) {
      List<Publishers> publishersList = this.entityManager.createNamedQuery("ReturnPublisherEmail", Publishers.class)
              .setParameter(1, email)
              .getResultList();

      return publishersList.size() != 0;
   } // End of publisherEmailExists method

   /**
    * Returns the Book object using the provided name.
    * @param isbn    The isbn of the book you are searching for.
    * @return        The Book instance corresponding to that isbn.
    */
   public Books getBook(String isbn) {
      List<Books> booksList = this.entityManager.createNamedQuery("ReturnBook", Books.class)
              .setParameter(1, isbn)
              .getResultList();

      if (booksList.size() == 0) {
         return null;
      } else {
         return booksList.get(0);
      }
   } // End of getBook method

   /**
    * Returns the Book object using the provided book title and author's email.
    * @param title   The title of the book you are searching for.
    * @param email   The author email associated with the book you are searching for.
    * @return        The Book instance corresponding to these fields.
    */
   public Books getBookByAuthor(String title, String email) {
      List<Books> booksList = this.entityManager.createNamedQuery("ReturnBookByAuthor", Books.class)
              .setParameter(1, title)
              .setParameter(2, email)
              .getResultList();

      if (booksList.size() == 0) {
         return null;
      } else {
         return booksList.get(0);
      }
   } // End of getBookByAuthor method

   /**
    * Returns the Book object using the book title and publisher name.
    * @param title    The title of the book you are searching for.
    * @param pub      The name of publisher of the book you are searching for.
    * @return        The Book instance corresponding to that isbn.
    */
   public Books getBookByPublisher(String title, String pub) {
      List<Books> booksList = this.entityManager.createNamedQuery("ReturnBookByPublisher", Books.class)
              .setParameter(1, title)
              .setParameter(2, pub)
              .getResultList();

      if (booksList.size() == 0) {
         return null;
      } else {
         return booksList.get(0);
      }
   } // End of getBookByPublisher method

   /**
    * Returns all the books within the Books table.
    * @return  the list of all books
    */
   public List<Books> getAllBooks() {
      return this.entityManager.createNamedQuery("ReturnAllBooks", Books.class)
              .getResultList();
   } // End of getAllBooks method

   /**
    * Returns the AuthoringEntities object using the provided email.
    * @param email   The email of the author you are searching for.
    * @return        The IndividualAuthor instance corresponding to that email.
    */
   public AuthoringEntities getAuthor(String email) {
      List<AuthoringEntities> authorList = this.entityManager.createNamedQuery("ReturnAuthor", AuthoringEntities.class)
              .setParameter(1, email)
              .getResultList();

      if (authorList.size() == 0) {
         return null;
      } else {
         return authorList.get(0);
      }
   } // End of getAuthor method

   /**
    * Returns all the authors within the AuthoringEntities table.
    * @return  the list of all publishers
    */
   public List<AuthoringEntities> getAllAuthors(){
      return this.entityManager.createNamedQuery("ReturnAllAuthors", AuthoringEntities.class)
              .getResultList();
   } // End of getAllAuthors method
} // End of BookClub class
