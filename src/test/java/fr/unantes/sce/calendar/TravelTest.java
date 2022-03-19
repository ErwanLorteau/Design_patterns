package fr.unantes.sce.calendar;
import fr.unantes.sce.people.Person ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import java.io.InvalidClassException;

public class TravelTest {
    /**Declare once for all test**/
    Person jean ;
    Calendar jeanAgenda ;
    Travel holidayTrip ;
    City paris, nantes, grenoble, rennes ;
    Correspondence parisNantes, grenobleRennes ;

    @BeforeEach
    public void setUp() throws Exception {
        /**Person**/
        jean = new Person("Jean", "agent");

        /**Calendar**/
        jeanAgenda = new Calendar(jean) ;

        /**Travel**/
        holidayTrip = new Travel(jeanAgenda) ;

        /**City**/
        paris = new City("Paris", "France") ;
        nantes = new City("Nantes" , "France") ;
        grenoble = new City("Grenoble", "France") ;
        rennes = new City("Rennes" , "France") ;

        /**Correspondance**/
        parisNantes   = new Correspondence (holidayTrip, paris, nantes, 12, 15 ) ;
        grenobleRennes = new Correspondence (holidayTrip, grenoble, rennes, 10, 16) ;
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    /***Issue #1  - Vector - Test ***/
    /**Check if given a correspondance, the addCorrespondance method will add the correponsance in steps**/
    //@ParametrizedTest
    //@ValueSource = {Correspondances =   , ,,}
    public void testAddStep() {
        holidayTrip.addCorrespondence(parisNantes) ;
        Assertions.assertTrue(holidayTrip.getSteps().contains(parisNantes)) ;
    }

    /**Check if given a correspondance, the removeCorrespondance method will remove the correponsance in steps**/
    public void testRemoveStep() {
        holidayTrip.addCorrespondence(parisNantes) ;
        holidayTrip.addCorrespondence(grenobleRennes) ;
        holidayTrip.removeCorrespondence(parisNantes) ;
        Assertions.assertFalse(holidayTrip.getSteps().contains(parisNantes)) ;
    }

    /**Check if adding in the correspondance list keep the order**/
    public void testGetFirstStep() {
        holidayTrip.addCorrespondence(parisNantes) ;
        holidayTrip.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holidayTrip.getFirstStep() == parisNantes) ;
    }

    /**Check if adding in the correspondance list keep the order (last element check) **/
    public void testGetLastStep() {
        holidayTrip.addCorrespondence(parisNantes) ;
        holidayTrip.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holidayTrip.getLastStep() == grenobleRennes) ;
    }
}
