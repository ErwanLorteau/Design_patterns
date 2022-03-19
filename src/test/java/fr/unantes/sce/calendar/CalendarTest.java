package fr.unantes.sce.calendar;


import fr.unantes.sce.people.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalendarTest {
    private Person jean ;
    private Calendar jeanAgenda;
    private Travel jeanHolyday;

    @BeforeEach
    public void setUp() throws Exception {
        jean = new Person("Jean", "agent");
        /**Calendar**/
        jeanAgenda = new Calendar(jean) ;
        /**Travel**/
        jeanHolyday = new Travel(jeanAgenda) ;
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**Verify if IdTravel Agenda method add a travel in travels list**/
    @Test
    public void testAddTravel() {
        jeanAgenda.addTravel(jeanHolyday) ;
        Assertions.assertTrue(jeanAgenda.getTravels().contains(jeanHolyday)) ;
    }

    /**Verify if IdTravel Agenda method remove a travel in travels list**/
    @Test
    public void testRemoveTravel() {
        jeanAgenda.addTravel(jeanHolyday) ;
        jeanAgenda.removeTravel(jeanHolyday) ;
        Assertions.assertFalse(jeanAgenda.getTravels().contains(jeanHolyday)) ;
    }
}
