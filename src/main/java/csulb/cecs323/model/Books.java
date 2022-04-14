
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Locale;

/**
* @author Daniel Tha, John Teano, Steven Dao
* @version 1.0
*
* Books are literary works of art which consist of various genres and authors.
* Books are publicized by publishers, and are validated by their corresponding Authoring Entity.
 */
@Entity
@Table(
     name = "BOOKS",
     uniqueConstraints = {
          @UniqueConstraint(name = "books_uk_01", columnNames = {"title", "publisher_name"}),
          @UniqueConstraint(name = "books_uk_02", columnNames = {"title", "authoring_entities_email"})
     }
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ReturnBook",
                query = "SELECT * " +
                        "FROM BOOKS " +
                        "WHERE ISBN = ? ",
                resultClass = Books.class
        ),
        @NamedNativeQuery(
                name = "ReturnBookByAuthor",
                query = "SELECT * " +
                        "FROM BOOKS " +
                        "INNER JOIN AUTHORING_ENTITIES A on BOOKS.AUTHORING_ENTITIES_EMAIL = A.EMAIL " +
                        "WHERE BOOKS.title = ? AND A.EMAIL = ?",
                resultClass = Books.class
        ),
        @NamedNativeQuery(
                name = "ReturnBookByPublisher",
                query = "SELECT * " +
                        "FROM BOOKS " +
                        "INNER JOIN PUBLISHERS P on P.NAME = BOOKS.PUBLISHER_NAME " +
                        "WHERE BOOKS.title = ? AND P.NAME = ?",
                resultClass = Books.class
        ),
        @NamedNativeQuery(
                name = "ReturnAllBooks",
                query = "SELECT *" +
                        "FROM BOOKS",
                resultClass = Books.class
        )
})
public class Books {

    /** The ISBN of the book (primary key) */
    @Id
    @Column(nullable = false, length = 17)
    private String ISBN;

    /** The title of the book */
    @Column(nullable = false, length = 80)
    private String title;

    /** The year published of the book */
    @Column(name = "year_published", nullable = false)
    private int yearPublished;

    /** The publisher for the Many-to-One relationship */
    @ManyToOne
    @JoinColumn (name = "publisher_name", referencedColumnName = "name", nullable = false)
    private Publishers publisher;

    /** The authoring entity for the Many-to-One relationship */
    @ManyToOne
    @JoinColumn (name = "authoring_entities_email", referencedColumnName = "email", nullable = false)
    private AuthoringEntities author;


    /** default constructor */
    public Books() {} // end of default constructor

    /** overloaded constructor **/
    public Books(AuthoringEntities author, Publishers publisher, String ISBN, String title, int year) {
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.title = title;
        this.yearPublished = year;
    } // end of overloaded constructor


    /* Accessor methods */


    /** Returns the ISBN of the book */
    public String getISBN(){
        return  ISBN;
    } // end of getISBN()


    /** Returns the title of the book */
    public String getTitle(){
            return title;
    } // end of getTitle()

    /** Returns the publishing year of the book */
    public int getYearPublished(){
        return yearPublished;
    } // end of getYearPublished()

    /** Returns the publisher of the book */
    public Publishers getPublisher() {
        return publisher;
    } // end of getPublisher()


    /** Returns the author of the book */
    public AuthoringEntities getAuthor() {
        return author;
    } // end of getAuthor()


    /* Mutator methods */


    /** Sets the ISBN of the book */
    public void setISBN(String isbn){
        this.ISBN = isbn;
    } // end of setISBN()

    /** Sets the title of the book */
    public void setTitle(String title) {
        this.title = title;
    } // end of setTitle()

    /** Sets the publishing year of the book */
    public void setYearPublished(int year){
        yearPublished = year;
    } // end of setYearPublished()

    // TODO: method for changing author of the book (requirement #4)
   /** Sets the author of the book */
   public void setAuthor(AuthoringEntities newAuthor) {
       author = newAuthor;
   } // end of setAuthor()


    /** Overloaded to_string */
    @Override
    public String toString () {
        return "Book" +
                "\n\tISBN: " + this.ISBN +
                "\n\tTitle: " + this.title +
                "\n\tYear Published: " + this.yearPublished +
                "\n\tAuthor email: " + this.author.getEmail() +
                "\n\tPublisher name : " + this.publisher.getName() +
                "\n - " + this.author.toString() +
                "\n - " + this.publisher.toString();
    } // end of toString()
} // end of Books class
