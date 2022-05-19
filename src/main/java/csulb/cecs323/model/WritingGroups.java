
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * A writing group consists of a team of writers who collectively collaborate on a book.
 */
@Entity
public class WritingGroups extends AuthoringEntities {

    /** The email of the writing group (primary key) */
    @Id
    @Column(name = "Writing_Group_Email", nullable = false, length = 80)
    private String email;

    @Column(name = "Head_Writer", length = 80)
    private String headWriter;

    @Column(name = "Year_Formed")
    private int yearFormed;


    /** Default constructor */
    public WritingGroups() {
        super();
    } //end of default constructor

    /** Overloaded constructor */
    public WritingGroups(String name, String email, String newHeadWriter, Integer newYearFormed) {
        super(name, email);
        headWriter = newHeadWriter;
        yearFormed = newYearFormed;
    } // end of overloaded constructor


    /* Accessor methods */


    /** Returns the head writer of the writing group. */
    public String getHeadWriter() {
        return headWriter;
    } // end of getHeadWriter()

    /** Returns the year the group was formed. */
    public Integer getYearFormed() {
        return yearFormed;
    } // end of getYearFormed()


    /* Mutator methods */


    /** Sets the head writer of the writing group. */
    public void setHeadWriter(String headWriter) {
        this.headWriter = headWriter;
    } // end of setHeadWriter()

    /** Sets the year formed for the writing group. */
    public void setYearFormed(Integer yearFormed) {
        this.yearFormed = yearFormed;
    } // end of setYearFormed()


    /** Overloaded to_string */
    @Override
    public String toString () {
        return "Authoring Entity: WritingGroup\n" +
                super.toString() +
                "\n  Head Writer: " + this.getHeadWriter() +
                "\n  Year Formed: " + this.getYearFormed();
    } // end of toString()
} // end of WritingGroups class
