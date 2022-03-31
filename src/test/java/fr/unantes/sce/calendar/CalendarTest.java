package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Agent;
import fr.unantes.sce.people.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;

public class CalendarTest {

    private Agent jean;
    private Calendar jeanCalendar;
    private Travel jeanHoliday;

    /**
     * Initialize basics object for the tests
     */

    @BeforeEach
    public void setUp() throws Exception {
        jean = new Agent("Jean");
        jeanCalendar = new Calendar(jean);
        jeanHoliday = new Travel(jeanCalendar);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }


    @Test
    public void testAddTravel() {
        jeanCalendar.addTravel(jeanHoliday);
        Assertions.assertTrue(jeanCalendar.getTravels().contains(jeanHoliday));
    }


    @Test
    public void testRemoveTravel() throws InvalidClassException {
        jeanCalendar.addTravel(jeanHoliday);
        jeanCalendar.removeTravel(jeanHoliday);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }

    /***Issue #3  - Calendar <--> travel association ***/
    @Test
    public void testAddBasicCalendar() {
        jeanHoliday.getTravelToCalendar().basicRemoveCalendar();
        jeanHoliday.getTravelToCalendar().basicAddCalendar(jeanCalendar);
        Assertions.assertTrue(jeanHoliday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveBasicCalendar() {
        //Already contained at the begginning (the consutructor do the link)
        jeanHoliday.getTravelToCalendar().basicRemoveCalendar();
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
    }

    /**
     * Test wheither or not we have an exception if we try to add a travel into a full calendar
     * @throw IndexOutOfBoundsException if calendar is full
     */
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
        jeanHoliday.getTravelToCalendar().basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertFalse(jeanHoliday.getTravelToCalendar().getCalendar() == jeanCalendar);
        jeanHoliday.addCalendar(jeanCalendar); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHoliday.getCalendar() == jeanCalendar);
    }

    @Test
    public void testRemoveCalendar() {
        jeanHoliday.getTravelToCalendar().basicRemoveCalendar(); //Constructor add the calendar in parameter
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
    }

    @Test
    public void testRemoveCalendarConsistency() {
        jeanHoliday.addCalendar(jeanCalendar); //
        jeanHoliday.removeCalendar();
        Assertions.assertTrue(jeanHoliday.getCalendar() == null);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }


    @Test
    public void testAddCalendarConsistency() throws InvalidClassException {
        Calendar paulCalendar = new Calendar(new Agent("paul"));
        jeanHoliday.addCalendar(paulCalendar);
        Assertions.assertFalse(jeanCalendar.getTravels().contains(jeanHoliday));
    }
}
