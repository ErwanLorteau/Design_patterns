package fr.unantes.sce.calendar;
import fr.unantes.sce.people.Agent;
import fr.unantes.sce.people.Person ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TravelTest {
    /**
     * Declare once for all test
     **/
    private Person jean, paul;
    private Calendar jeanCalendar, paulCalendar;
    private Travel jeanHoliday, paulHoliday;
    private City paris, nantes, grenoble, rennes;
    private Correspondence parisNantes, grenobleRennes;
    private ZonedDateTime departure, arrival;

    @BeforeEach
    public void setUp() throws InvalidClassException {

        /**Person**/
        jean = new Agent("Jean");
        paul = new Agent("Paul");

        /**Calendar**/
        jeanCalendar = new Calendar(jean);
        paulCalendar = new Calendar(paul);

        /**Travel**/
        jeanHoliday = new Travel(jeanCalendar);
        paulHoliday = new Travel(paulCalendar);

        /**City**/
        paris = new City("Paris", "France");
        nantes = new City("Nantes", "France");
        grenoble = new City("Grenoble", "France");
        rennes = new City("Rennes", "France");

        /**ZoneDateTime**/
        departure = ZonedDateTime.of(2022, 3, 15, 21, 30, 59, 00000, ZoneId.systemDefault());
        arrival = ZonedDateTime.of(2022, 3, 16, 02, 20, 01, 200, ZoneId.systemDefault());


        /**Correspondence**/
        parisNantes = new Correspondence(jeanHoliday, paris, nantes, departure, arrival);
        grenobleRennes = new Correspondence(jeanHoliday, grenoble, rennes, departure, arrival);

    }

    @AfterEach
    public void tearDown() throws Exception {

    }


    /***Issue #1  - Vector - Test ***/
    /**
     * Check if given a correspondence, the addCorrespondence method will add the correpondence in steps
     **/
    //@ParametrizedTest
    //@ValueSource = {Correspondances =   , ,,}
    @Test
    public void testAddStep() throws Exception {
        //setUp();
        jeanHoliday.addCorrespondence(parisNantes);
        Assertions.assertTrue(jeanHoliday.getSteps().contains(parisNantes));
    }

    /**
     * Check if given a correspondence, the removeCorrespondence method will remove the correpondence in steps
     **/
    @Test
    public void testRemoveStep() {
        jeanHoliday.addCorrespondence(parisNantes);
        jeanHoliday.addCorrespondence(grenobleRennes);
        jeanHoliday.removeCorrespondence(parisNantes);
        Assertions.assertFalse(jeanHoliday.getSteps().contains(parisNantes));
    }

    /**
     * Check if adding in the correspondence list keep the order
     **/
    @Test
    public void testGetFirstStep() {
        jeanHoliday.addCorrespondence(parisNantes);
        jeanHoliday.addCorrespondence(grenobleRennes);
        Assertions.assertTrue(jeanHoliday.getFirstStep() == parisNantes);
    }

    /**
     * Check if adding in the correspondence list keep the order (last element check)
     **/
    @Test
    public void testGetLastStep() {
        jeanHoliday.addCorrespondence(parisNantes);
        jeanHoliday.addCorrespondence(grenobleRennes);
        Assertions.assertTrue(jeanHoliday.getLastStep() == grenobleRennes);
    }


    /***Issue #3  - Calendar <--> travel association ***/
    @Test
    public void testAddBasicTravel() {
        jeanCalendar.getTravels().basicAddTravel(jeanHoliday);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    @Test
    public void testRemoveBasicTravel() {
        jeanCalendar.getTravels().basicAddTravel(jeanHoliday);
        jeanCalendar.getTravels().basicRemoveTravel(jeanHoliday);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    @Test
    public void testAddTravel() {
        jeanCalendar.addTravel(jeanHoliday);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    @Test
    public void testRemoveTravel() throws InvalidClassException {
        jeanCalendar.addTravel(jeanHoliday);
        jeanCalendar.getTravels().removeTravel(jeanHoliday);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    @Test
    public void testRemoveTravelConsistency() throws InvalidClassException {
        jeanCalendar.addTravel(jeanHoliday);
        jeanCalendar.removeTravel(jeanHoliday);
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
    }

    @Test
    public void testAddTravelConsistency() {
        jeanCalendar.addTravel(jeanHoliday);
        Assertions.assertTrue(jeanHoliday.getCalendar() == jeanCalendar);
    }

    @Test
    //Passe pas
    //Test when A is linked To B and we link A to C than B is no longer linked to A, and that C and A are linked
    public void testEdgeCaseTravelConsistency() throws InvalidClassException {
        Person paul = new Agent("Paul");
        Calendar paulCalendar = new Calendar(paul);
        Travel paulHoliday = new Travel(paulCalendar);

        jeanCalendar.addTravel(paulHoliday);

        Assertions.assertFalse(paulHoliday.getCalendar() == paulCalendar);
        Assertions.assertTrue(paulHoliday.getCalendar() == jeanCalendar);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(paulHoliday));
        Assertions.assertFalse(paulCalendar.getTravels().contains(paulHoliday));
    }

    /***Issue #4  - Travel <--> Correspondence association ***/

    @Test
    public void testAddCorrespondenceConsistency() {
        jeanHoliday.addCorrespondence(parisNantes);
        Assertions.assertTrue(parisNantes.getTravel() == jeanHoliday);
        Assertions.assertTrue(jeanHoliday.getSteps().contains(parisNantes));
    }

    @Test
    public void testEdgeCaseCorrespondenceConsistency() throws InvalidClassException {

        jeanHoliday.addCorrespondence(parisNantes);
        paulHoliday.addCorrespondence(grenobleRennes);
        paulHoliday.addCorrespondence(parisNantes);

        // Check that the link between paulHoliday and grenobleRennes is created.
        Assertions.assertTrue(paulHoliday.getSteps().contains(grenobleRennes));
        Assertions.assertTrue(grenobleRennes.getTravel() == paulHoliday);

        // Check that the link between paulHoliday and parisNantes is created.
        Assertions.assertTrue(paulHoliday.getSteps().contains(parisNantes));
        Assertions.assertTrue(parisNantes.getTravel() == paulHoliday);

        // Check that the link between jeanHoliday and parisNantes is cut off.
        Assertions.assertFalse(jeanHoliday.getSteps().contains(parisNantes));
        Assertions.assertFalse(parisNantes.getTravel() == jeanHoliday);
    }

}

