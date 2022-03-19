package fr.unantes.sce.calendar;
import fr.unantes.sce.people.Person ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TravelTest {
    /**Declare once for all test**/
    private Person jean ;
    private Calendar jeanAgenda ;
    private Travel holyday;
    private City paris, nantes, grenoble, rennes ;
    private Correspondence parisNantes, grenobleRennes ;

    @BeforeEach
    public void setUp() throws Exception {
        /**Person**/
        jean = new Person("Jean", "agent");

        /**Calendar**/
        jeanAgenda = new Calendar(jean) ;

        /**Travel**/
        holyday = new Travel(jeanAgenda) ;

        /**City**/
        paris = new City("Paris", "France") ;
        nantes = new City("Nantes" , "France") ;
        grenoble = new City("Grenoble", "France") ;
        rennes = new City("Rennes" , "France") ;

        /**Correspondance**/
        parisNantes   = new Correspondence (holyday, paris, nantes, 12, 15 ) ;
        grenobleRennes = new Correspondence (holyday, grenoble, rennes, 10, 16) ;
    }

    @AfterEach
    public void tearDown() throws Exception {

    }


    /***Issue #1  - Vector - Test ***/
    /**Check if given a correspondance, the addCorrespondance method will add the correpondance in steps**/
    //@ParametrizedTest
    //@ValueSource = {Correspondances =   , ,,}
    @Test
    public void testAddStep() throws Exception {
        //setUp();
        holyday.addCorrespondence(parisNantes) ;
        Assertions.assertTrue(holyday.getSteps().contains(parisNantes)) ;
    }

    /**Check if given a correspondance, the removeCorrespondance method will remove the correpondance in steps**/
    @Test
    public void testRemoveStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        holyday.removeCorrespondence(parisNantes) ;
        Assertions.assertFalse(holyday.getSteps().contains(parisNantes)) ;
    }

    /**Check if adding in the correspondance list keep the order**/
    @Test
    public void testGetFirstStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holyday.getFirstStep() == parisNantes) ;
    }

    /**Check if adding in the correspondance list keep the order (last element check) **/
    @Test
    public void testGetLastStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holyday.getLastStep() == grenobleRennes) ;
    }
}
