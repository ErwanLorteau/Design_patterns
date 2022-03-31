package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Agent;
import fr.unantes.sce.people.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class CorrespondenceTest {
    private Agent jean, paul;
    private Calendar jeanCalendar, paulCalendar;
    private Travel jeanHoliday, paulHoliday;
    private City paris, nantes, grenoble, rennes;
    private Correspondence parisNantes, grenobleRennes;
    private ZonedDateTime departureTime, arrivalTime;

    @BeforeEach
    void setUp() throws InvalidClassException {
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
        departureTime = ZonedDateTime.of(2022, 3, 15, 21, 30, 59, 00000, ZoneId.systemDefault());
        arrivalTime = ZonedDateTime.of(2022, 3, 16, 02, 20, 01, 200, ZoneId.systemDefault());


        /**Correspondence**/
        parisNantes = new Correspondence(jeanHoliday, paris, nantes, departureTime, arrivalTime);
        grenobleRennes = new Correspondence(jeanHoliday, grenoble, rennes, departureTime, arrivalTime);
    }

    @AfterEach
    void tearDown() {
    }


    /**#Issue 2 - Replace ints by a Date Type **/

    /**
     * Verify the getters/setters
     **/
    @Test
    public void testStartTime() {
        parisNantes.setStartTime(departureTime);
        Assertions.assertTrue(parisNantes.getStartTime() == departureTime);
    }

    @Test
    public void testArrivalTime() {
        parisNantes.setArrivalTime(arrivalTime);
        Assertions.assertTrue(parisNantes.getArrivalTime() == arrivalTime);
    }

    /**
     * Verify the override of Equals methode (two instance of the same correspondance with two "equals" and distinct ZonedDateTimeObject will return true)
     **/
    @Test
    public void testEquals() throws Exception {
        /**Instanciate a deep copy of parisNantes to test the equals methode*/
        ZonedDateTime sameDeparture = ZonedDateTime.of(2022, 3, 15, 21, 30, 59, 00000, ZoneId.systemDefault());
        Correspondence sameCorrespondance = new Correspondence(jeanHoliday, paris, nantes, sameDeparture, arrivalTime);
        Assertions.assertTrue(parisNantes.equals(sameCorrespondance));
    }

    /***Issue #4  - Travel <--> Correspondence association ***/

    @Test
    public void testStepsMaxSizeTravel() throws InvalidClassException {

        Calendar cal = new Calendar(jean);
        Travel travel = new Travel(cal);
        City city1 = new City("City1", "Somewhere");
        City city2 = new City("City2", "Somewhere");
        Correspondence cor1 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor2 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor3 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor4 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor5 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor6 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor7 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor8 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor9 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);
        Correspondence cor10 = new Correspondence(travel, city1, city2, departureTime, arrivalTime);

        InvalidClassException travelCantHave11Correspondences = Assertions.assertThrows(
                InvalidClassException.class,
                () -> new Correspondence(travel, city1, city2, departureTime, arrivalTime),
                ""
        );
    }

    @Test
    public void testEdgeCaseCorrespondenceConsistency() {
        grenobleRennes.setTravel(jeanHoliday);
        parisNantes.setTravel(paulHoliday);
        parisNantes.setTravel(jeanHoliday);

        // Check that the link between grenobleRennes and jeanHoliday is created.
        Assertions.assertTrue(grenobleRennes.getTravel() == jeanHoliday);
        Assertions.assertTrue(jeanHoliday.getSteps().contains(grenobleRennes));

        // Check that the link between parisNantes and paulHoliday is cut off.
        Assertions.assertFalse(parisNantes.getTravel() == paulHoliday);
        Assertions.assertFalse(paulHoliday.getSteps().contains(parisNantes));

        // Check that the link between parisNantes and jeanHoliday is created.
        Assertions.assertTrue(parisNantes.getTravel() == jeanHoliday);
        Assertions.assertTrue(jeanHoliday.getSteps().contains(parisNantes));
    }


    @Test
    public void cantInitializeNullOriginCorrespondance() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Correspondence(paulHoliday, null, rennes, departureTime, arrivalTime),
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("Null city"));
    }

    @Test
    public void cantSetNullOrigin() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> parisNantes.setOrigin(null) ,
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("Origin can't be set as null"));
    }

    @Test
    public void cantInitializeNullDestinationCorrespondance() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Correspondence(paulHoliday, rennes, null, departureTime, arrivalTime),
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("Null city"));
    }

    @Test
    public void cantSetNullDestination() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> parisNantes.setDestination(null),
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("Destination can't be set as null"));
    }

    @Test
    public void expectStartTimeIsBeforeArrivalTimeInConstructor() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Correspondence( paulHoliday, rennes, paris, arrivalTime, departureTime) ,
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("Cant 'initialize a correspondance with arrivalTime anterior at startTime"));
    }

    @Test
    public void cantSetAnArrivalTimeAnteriorToDepartureTime() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> parisNantes.setArrivalTime(parisNantes.getStartTime().minusDays(1)) ,  //
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("arrivalTime can't be set, must be after startTime"));
    }

    @Test
    public void cantSetAStartTimeSuperiorToArrivalTime() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> parisNantes.setStartTime(parisNantes.getArrivalTime().plusDays(1)) ,  //
                ""
        );
        Assertions.assertTrue(thrown.getMessage().contains("startTime can't be set, it must be anterior to arrivalTime"));
    }
}
