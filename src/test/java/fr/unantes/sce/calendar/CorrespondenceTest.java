package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CorrespondenceTest {
    private Person jean ;
    private Calendar jeanAgenda ;
    private Travel holyday;
    private City paris, nantes, grenoble, rennes ;
    private Correspondence parisNantes, grenobleRennes ;
    private ZonedDateTime departure, arrival ;

    @BeforeEach
    void setUp() throws InvalidClassException {
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
        arrival = ZonedDateTime.of(2022, 3, 16, 02, 20, 01,200, ZoneId.systemDefault()) ;

        /**Correspondance**/
        parisNantes   = new Correspondence (holyday, paris, nantes, departure, arrival ) ;
        grenobleRennes = new Correspondence (holyday, grenoble, rennes, departure, arrival );
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
    public void testEquals() {
        /**Instanciate a deep copy of parisNantes to test the equals methode*/
        ZonedDateTime sameDeparture = ZonedDateTime.of(2022, 3, 15, 21, 30, 59,00000, ZoneId.systemDefault());
        Correspondence sameCorrespondance = new Correspondence (holyday, paris, nantes, sameDeparture, arrival ) ;
        Assertions.assertTrue(parisNantes.equals(sameCorrespondance));
    }
}
