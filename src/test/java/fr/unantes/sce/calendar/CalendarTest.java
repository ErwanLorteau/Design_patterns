package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;

public class CalendarTest {

    private Person jean;
    private Calendar jeanCalendar;
    private Travel jeanHolyday;

    @BeforeEach
    public void setUp() throws Exception {
        jean = new Person("Jean", "agent");
        /**Calendar**/
        jeanCalendar = new Calendar(jean);
        /**Travel**/
        jeanHolyday = new Travel(jeanCalendar);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /*** Verify if IdTravel Agenda method add a travel in travels list***/
    @Test
    public void testAddTravel() {
        jeanCalendar.addTravel(jeanHolyday);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(jeanHolyday));
    }

    /*** Verify if IdTravel Agenda method remove a travel in travels list***/
    @Test
    public void testRemoveTravel() {
        jeanCalendar.addTravel(jeanHolyday);
        jeanCalendar.removeTravel(jeanHolyday);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHolyday));
    }

    /***Issue #3  - Calendar <--> travel association ***/
    @Test
    public void testAddBasicCalendar() {
        jeanHolyday.basicRemoveCalendar();
        jeanHolyday.basicAddCalendar(jeanCalendar);
        Assertions.assertTrue(jeanHolyday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveBasicCalendar() {
        //Already contained at the begginning (the consutructor do the link)
        jeanHolyday.basicRemoveCalendar();
        Assertions.assertTrue(jeanHolyday.getCalendar() == null);
    }

    @Test
    public void testExceptionExpectedIfListIsFull() {
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < 11; i++) {
                jeanCalendar.addTravel(jeanHolyday); //To change later cause we can't have the same travel twice , but for this issue it's ok
            }
        });
    }

    @Test
    public void testAddCalendar() {
        jeanHolyday.basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertFalse(jeanHolyday.getCalendar() == jeanCalendar);
        jeanHolyday.addCalendar(jeanCalendar); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHolyday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveCalendar() {
        jeanHolyday.basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHolyday.getCalendar() == null);
    }

    @Test
    public void testRemoveCalendarConsistancy() {
        jeanHolyday.addCalendar(jeanCalendar); //
        //jeanCalendar.addTravel(jeanHolyday) ; //Never call twice otherwise its added two time in the list !
        jeanHolyday.removeCalendar();
        Assertions.assertTrue(jeanHolyday.getCalendar() == null);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHolyday));
    }

    @Test
    public void testAddCalendarConsistancy() throws InvalidClassException {
        jeanHolyday.addCalendar(jeanCalendar);
        Calendar paulCalendar = new Calendar(new Person("paul", "agent"));
        jeanHolyday.addCalendar(paulCalendar);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHolyday));
    }
}
