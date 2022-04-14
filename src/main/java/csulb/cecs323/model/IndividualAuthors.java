
package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author Daniel Tha, John Teano, Steven Dao
 * @version 1.0
 *
 * Individual authors are a single instance of an author.
 * They may write books about any topic and may belong to a group or team of authors.
 */
@Entity
public class IndividualAuthors extends AuthoringEntities {

    /** The email of the individual author (primary key) */
    @Id
    @Column(nullable = false, name = "Individual_Authors_Email")
    private String email;


    /** The list of ad hoc teams for the Many-to-Many relationship */
    @ManyToMany()
    @JoinTable(name = "Ad_Hoc_Teams_Member",
            joinColumns = @JoinColumn(name = "Individual_Authors_Email"),
            inverseJoinColumns = @JoinColumn(name = "Ad_Hoc_Teams_Email")
    )
    List<AdHocTeams> adHocTeamsList;


    /** Default constructor */
    public IndividualAuthors() {} // end of default constructor

    /** Overloaded constructor */
    public IndividualAuthors(String name, String email) {
        super(name, email);
    } // end of overloaded constructor

    /** Returns the ad hoc teams joined list */
    public List<AdHocTeams> getAdHocTeamsList() {
        return adHocTeamsList;
    } // end of getAdHocTeamsList()

    /** Adds to the ad hoc teams joined list */
    public void addToAdHocTeamsList(AdHocTeams adHocTeam) {
        adHocTeamsList.add(adHocTeam);
    } // end of AddToAdHocTeamsList()


    /** Returns a description of the individual author. */
    @Override
    public String toString() {
        String description = "Authoring Entity: IndividualAuthor\n" +
                super.toString() +
                "\n\tTeams: { ";

        for (int i = 0; i < adHocTeamsList.size(); ++i)
            if (i < adHocTeamsList.size() - 1)
                description += adHocTeamsList.get(i).getName() + ", ";
            else
                description += adHocTeamsList.get(i).getName();

        description += " }";

        return description;
    } // end of toString()
} // end of IndividualAuthors class
