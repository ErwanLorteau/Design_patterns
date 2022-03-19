package fr.unantes.sce.calendar;
import fr.unantes.sce.people.Person ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TravelTest {
    /**Declare once for all test**/
    private Person jean ;
    private Calendar jeanAgenda ;
    private Travel holyday;
    private City paris, nantes, grenoble, rennes ;
    private Correspondence parisNantes, grenobleRennes ;
    private ZonedDateTime departure, arrival ;

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

        /**ZoneDateTime**/
        departure = ZonedDateTime.of(2022, 3, 15, 21, 30, 59,00000, ZoneId.systemDefault());
        arrival = ZonedDateTime.of(2022, 3, 16, 02, 20, 01,200, ZoneId.systemDefault());


        /**Correspondence**/
        parisNantes   = new Correspondence (holyday, paris, nantes, departure, arrival ) ;
        grenobleRennes = new Correspondence (holyday, grenoble, rennes, departure, arrival );
    }

    @AfterEach
    public void tearDown() throws Exception {

    }


    /***Issue #1  - Vector - Test ***/
    /**Check if given a correspondence, the addCorrespondence method will add the correpondence in steps**/
    //@ParametrizedTest
    //@ValueSource = {Correspondances =   , ,,}
    @Test
    public void testAddStep() throws Exception {
        //setUp();
        holyday.addCorrespondence(parisNantes) ;
        Assertions.assertTrue(holyday.getSteps().contains(parisNantes)) ;
    }

    /**Check if given a correspondence, the removeCorrespondence method will remove the correpondence in steps**/
    @Test
    public void testRemoveStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        holyday.removeCorrespondence(parisNantes) ;
        Assertions.assertFalse(holyday.getSteps().contains(parisNantes)) ;
    }

    /**Check if adding in the correspondence list keep the order**/
    @Test
    public void testGetFirstStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holyday.getFirstStep() == parisNantes) ;
    }

    /**Check if adding in the correspondence list keep the order (last element check) **/
    @Test
    public void testGetLastStep() {
        holyday.addCorrespondence(parisNantes) ;
        holyday.addCorrespondence(grenobleRennes) ;
        Assertions.assertTrue(holyday.getLastStep() == grenobleRennes) ;
    }
}
