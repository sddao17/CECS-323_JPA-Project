
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * Publishers handle the approval and publication of books to the public. They receive permission
 * from Authors to publish the books and give them the appropriate credit.
 */
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "publishers_uk_01", columnNames = {"phone"}),
                @UniqueConstraint(name = "publishers_uk_02", columnNames = {"email"})
        }
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ReturnPublisher",
                query = "SELECT * " +
                        "FROM PUBLISHERS " +
                        "WHERE name = ? ",
                resultClass = Publishers.class

        ),
        @NamedNativeQuery(
                name = "ReturnPublisherPhone",
                query = "SELECT * " +
                        "FROM PUBLISHERS " +
                        "WHERE phone = ?",
                resultClass = Publishers.class
        ),
        @NamedNativeQuery(
                name = "ReturnPublisherEmail",
                query = "SELECT * " +
                        "FROM PUBLISHERS " +
                        "WHERE email = ? ",
                resultClass = Publishers.class
        ),
        @NamedNativeQuery(
                name = "ReturnAllPublishers",
                query = "SELECT * " +
                        "FROM PUBLISHERS",
                resultClass = Publishers.class
        )
})
public class Publishers {

    /** The name of the publisher (primary key) */
    @Id
    @Column(nullable = false, length = 80)
    private String name;

    /** The phone number of the publisher (unique) */
    @Column(nullable = false, length = 24)
    private String phone;

    /** The email of the publisher (unique) */
    @Column(nullable = false, length = 80)
    private String email;

    /** The list of books for the One-To-Many relationship */
    @OneToMany(mappedBy = "publisher")
    private List<Books> books;


    /** Default constructor */
    public Publishers() {
    } // end of default constructor

    /** Overloaded constructor */
    public Publishers(String newName, String newPhone, String newEmail) {
        name = newName;
        phone = newPhone;
        email = newEmail;
    } // end of overloaded constructor


    /* Accessor Methods */


    /** Returns the name of the publisher. */
    public String getName() {
        return name;
    } // end of getName()

    /** Returns the phone number of the publisher. */
    public String getPhone() {
        return phone;
    } // end of getPhone()

    /** Returns the email of the publisher */
    public String getEmail() {
        return email;
    } // end of getEmail()


    /* Mutator methods */


    /** Sets the name of the publisher. */
    public void setName(String newName) {
        name = newName;
    } // end of setName()

    /** Sets the phone number of the publisher. */
    public void setPhone(String newPhone) {
        phone = newPhone;
    } // end of setPhone()

    /** Sets the contact email of the publisher. */
    public void setEmail(String newEmail) {
        email = newEmail;
    } // end of setEmail()

    /** Adds to the list of books. */
    public void addToBookList(Books newBook) {
        books.add(newBook);
    } // end of addToBookList

    /** Returns a description of the publisher. */
    @Override
    public String toString() {
        return "Publisher" +
                "\n\tName: " + name +
                "\n\tPhone number: " + phone +
                "\n\tEmail: " + email;
    } // end of toString()
} // end of Publishers class
