
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * Authoring entities consist of the person or people who helped to create the book.
 * They can consist of a Writing group, an Individual Author, or an Ad Hoc Team.
 */
@Entity
@Table(name = "Authoring_Entities")
@DiscriminatorColumn(name = "Authoring_Entity_Type")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ReturnAuthor",
                query = "SELECT * " +
                        "FROM AUTHORING_ENTITIES " +
                        "WHERE email = ? ",
                resultClass = AuthoringEntities.class
        ),
        @NamedNativeQuery(
                name = "ReturnAllAuthors",
                query = "SELECT * " +
                        "FROM AUTHORING_ENTITIES ",
                resultClass = AuthoringEntities.class
        )
})
public abstract class AuthoringEntities {

    /** The name of the author */
    @Column(nullable = false, length = 80)
    private String name;

    /** The email of the author (primary key) */
    @Id
    @Column(nullable = false, length = 30)
    private String email;

    /** The list of books for the Many-to-Many relationship */
    @OneToMany(mappedBy = "author")
    private List<Books> booksList;

    /** default constructor **/
    public AuthoringEntities() {
        // use null values due to being an abstract method
        this("", "");
    } // end of default constructor

    /** overloaded constructor **/
    public AuthoringEntities(String name, String email) {
        this.name = name;
        this.email = email;
    } // end of overloaded constructor

    /* Accessor methods */

    /** Returns the email of the authoring entity */
    public String getEmail() {
        return email;
    } // end of getEmail()

    /** Returns the name of the authoring entity */
    public String getName() {
            return name;
    } // end of getName()

    /** Returns the list of books associated with the authoring entity */
    public List<Books> getBooksList() {
        return booksList;
    } // end of getBooksList()

    /* Mutator methods */

    /** Sets the email of the authoring entity */
    public void setEmail(String email) {
        this.email = email;
    } // end of setEmail()

    /** Sets the name of the authoring entity */
    public void setName(String name) {
        this.name = name;
    } // end of setName()


    /** Override toString */
    @Override
    public String toString () {
        return "  Email: " + this.email +
                "\n  Name: " + this.name;
    } // end of toString()
} // end of AuthoringEntities class
