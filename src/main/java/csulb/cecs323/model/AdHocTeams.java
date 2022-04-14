
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * Ad hoc teams are teams of authors that improvise writing projects.
 * They typically consist of a team of individual authors.
 */
@Entity
public class AdHocTeams extends AuthoringEntities {

    /** The email of the ad hoc team (primary key) */
    @Id
    @Column(nullable = false, name = "Ad_Hoc_Teams_Email")
    private String email;

    /** The list of individual authors for the Many-to-Many relationship */
    @ManyToMany(mappedBy = "adHocTeamsList")
    List<IndividualAuthors> individualAuthorsList;


    /** Default constructor */
    public AdHocTeams() {
        super();
    } // end of default constructor

    /** Overloaded constructor */
    public AdHocTeams(String name, String email) {
        super(name, email);
    } // end of overloaded constructor


    /* Accessor methods */


    /** Returns the list of individual authors */
    public List<IndividualAuthors> getIndividualAuthorsList() {
        return individualAuthorsList;
    } // end of getIndividualAuthorsList()


    /* Mutator methods */


    /** Adds to the list of individual authors */
    public void addToIndividualAuthorList(IndividualAuthors newIndividualAuthor) {
        individualAuthorsList.add(newIndividualAuthor);
    } // end of addToIndividualAuthorsList()


    /** Returns a description of the ad hoc team. */
    @Override
    public String toString() {
        String description = "Authoring Entity: AdHocTeams\n" +
                super.toString() +
                "\n\tMembers: { ";

        for (int i = 0; i < individualAuthorsList.size(); ++i)
            if (i < individualAuthorsList.size() - 1)
                description += individualAuthorsList.get(i).getName() + ", ";
            else
                description += individualAuthorsList.get(i).getName();

        description += " }";

        return description;
    } // end of toString()
} // end of AdHocTeams class
