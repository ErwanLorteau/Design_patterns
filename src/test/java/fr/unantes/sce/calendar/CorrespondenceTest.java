package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Agent;
import fr.unantes.sce.people.Person;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CorrespondenceTest {
    private Person jean, paul ;
    private Calendar jeanCalendar, paulCalendar ;
    private Travel jeanHoliday, paulHoliday;
    private City paris, nantes, grenoble, rennes ;
    private Correspondence parisNantes, grenobleRennes ;
    private ZonedDateTime departure, arrival ;

    @BeforeEach
    void setUp() throws InvalidClassException {
        /**Person**/
        jean = new Agent("Jean");
        paul = new Agent("Paul");

        /**Calendar**/
        jeanCalendar = new Calendar(jean) ;
        paulCalendar = new Calendar(paul);

        /**Travel**/
        jeanHoliday = new Travel(jeanCalendar) ;
        paulHoliday = new Travel(paulCalendar);

        /**City**/
        paris = new City("Paris", "France") ;
        nantes = new City("Nantes" , "France") ;
        grenoble = new City("Grenoble", "France") ;
        rennes = new City("Rennes" , "France") ;

        /**ZoneDateTime**/
        departure = ZonedDateTime.of(2022, 3, 15, 21, 30, 59,00000, ZoneId.systemDefault());
        arrival = ZonedDateTime.of(2022, 3, 16, 02, 20, 01,200, ZoneId.systemDefault());


        /**Correspondence**/
        parisNantes    = new Correspondence (jeanHoliday, paris, nantes, departure, arrival ) ;
        grenobleRennes = new Correspondence (jeanHoliday, grenoble, rennes, departure, arrival );
    }

    @AfterEach
    void tearDown() {
    }


    /**#Issue 2 - Replace ints by a Date Type **/

    /**Verify the getters/setters**/
    @Test
    public void testStartTime() {
        parisNantes.setStartTime(departure);
        Assertions.assertTrue(parisNantes.getStartTime() == departure);
    }
    @Test
    public void testArrivalTime() {
        parisNantes.setArrivalTime(arrival);
        Assertions.assertTrue(parisNantes.getArrivalTime() == arrival);
    }

    /**Verify the override of Equals methode (two instance of the same correspondance with two "equals" and distinct ZonedDateTimeObject will return true)**/
    @Test
    public void testEquals() throws Exception {
        /**Instanciate a deep copy of parisNantes to test the equals methode*/
        ZonedDateTime sameDeparture = ZonedDateTime.of(2022, 3, 15, 21, 30, 59,00000, ZoneId.systemDefault());
        Correspondence sameCorrespondance = new Correspondence (jeanHoliday, paris, nantes, sameDeparture, arrival ) ;
        Assertions.assertTrue(parisNantes.equals(sameCorrespondance));
    }

    /***Issue #4  - Travel <--> Correspondence association ***/

    @Test
    public void testStepsMaxSizeTravel() throws InvalidClassException {

        Calendar cal = new Calendar(jean);
        Travel travel = new Travel(cal);
        City city1 = new City("City1","Somewhere");
        City city2 = new City("City2","Somewhere");
        Correspondence cor1 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor2 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor3 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor4 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor5 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor6 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor7 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor8 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor9 = new Correspondence(travel, city1, city2, departure, arrival);
        Correspondence cor10 = new Correspondence(travel, city1, city2, departure, arrival);
        try {
            Correspondence cor11 = new Correspondence(travel, city1, city2, departure, arrival);
        } catch (InvalidClassException e){
            //help
        }
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
}
