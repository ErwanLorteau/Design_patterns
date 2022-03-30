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
    private Travel jeanHoliday;

    @BeforeEach
    public void setUp() throws Exception {
        jean = new Person("Jean", "agent");
        /**Calendar**/
        jeanCalendar = new Calendar(jean);
        /**Travel**/
        jeanHoliday = new Travel(jeanCalendar);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /*** Verify if IdTravel Agenda method add a travel in travels list***/
    @Test
    public void testAddTravel() {
        jeanCalendar.addTravel(jeanHoliday);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    /*** Verify if IdTravel Agenda method remove a travel in travels list***/
    @Test
    public void testRemoveTravel() {
        jeanCalendar.addTravel(jeanHoliday);
        jeanCalendar.removeTravel(jeanHoliday);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    /***Issue #3  - Calendar <--> travel association ***/
    @Test
    public void testAddBasicCalendar() {
        jeanHoliday.basicRemoveCalendar();
        jeanHoliday.basicAddCalendar(jeanCalendar);
        Assertions.assertTrue(jeanHoliday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveBasicCalendar() {
        //Already contained at the begginning (the consutructor do the link)
        jeanHoliday.basicRemoveCalendar();
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
    }

    @Test
    public void testExceptionExpectedIfListIsFull() {
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < 11; i++) {
                jeanCalendar.addTravel(jeanHoliday); //To change later cause we can't have the same travel twice , but for this issue it's ok
            }
        });
    }

    @Test
    public void testAddCalendar() {
        jeanHoliday.basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertFalse(jeanHoliday.getCalendar() == jeanCalendar);
        jeanHoliday.addCalendar(jeanCalendar); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHoliday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveCalendar() {
        jeanHoliday.basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
    }

    @Test
    public void testRemoveCalendarConsistency() {
        jeanHoliday.addCalendar(jeanCalendar); //
        //jeanCalendar.addTravel(jeanHoliday) ; //Never call twice otherwise its added two time in the list !
        jeanHoliday.removeCalendar();
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    @Test
    public void testAddCalendarConsistency() throws InvalidClassException {
        jeanHoliday.addCalendar(jeanCalendar);
        Calendar paulCalendar = new Calendar(new Person("paul", "agent"));
        jeanHoliday.addCalendar(paulCalendar);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }
}
