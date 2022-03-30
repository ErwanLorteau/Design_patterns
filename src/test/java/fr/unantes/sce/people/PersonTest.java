package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Calendar;
import fr.unantes.sce.calendar.Travel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;

class PersonTest {

    private Agent agent1, dupond, bruceWayne, batman;
    private Admin admin1, dupont;
    private Calendar agentCal, bruceCal;
    private Travel agentTravel, bruceTravel;

    @BeforeEach
    public void setUp() throws Exception {

        /**Person**/
        agent1 = new Agent("Erwan");
        admin1 = new Admin("Moncef");

        dupond = new Agent("Dupon");
        dupont = new Admin("Dupon");

        bruceWayne = new Agent("Wayne");
        batman = new Agent("Wayne");

        /**Calendar**/
        agentCal = new Calendar(agent1);

        /**Travel**/
        agentTravel = new Travel(agentCal);
        bruceTravel = new Travel(bruceCal);
    }

    @AfterEach
    void tearDown() {
    }

    /***Issue #6  - Refactor  ***/
    @Test
    public void testEquals() {
        Assertions.assertFalse(dupond.equals(dupont));
        Assertions.assertTrue(bruceWayne.equals(batman));
    }

    @Test
    public void testAdminWithoutCalendar() {

        InvalidClassException AdminCantHaveCalendar = Assertions.assertThrows(
                InvalidClassException.class,
                () -> dupont.getCalendar(),
                "truc"
        );

        Assertions.assertDoesNotThrow(() -> dupond.getCalendar());
    }

    @Test
    public void testAddCalendarCoherence() throws InvalidClassException {
        agent1.setCalendar(agentCal);
        Assertions.assertTrue(agent1.addTravelTo(agentTravel,agent1));

        bruceWayne.setCalendar(bruceCal);
        Assertions.assertFalse(agent1.addTravelTo(bruceTravel, bruceWayne));

        Assertions.assertTrue(admin1.addTravelTo(agentTravel, agent1));
    }
}