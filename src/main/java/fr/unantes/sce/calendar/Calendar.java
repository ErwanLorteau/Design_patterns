package fr.unantes.sce.calendar;
import fr.unantes.sce.people.Agent;
import fr.unantes.sce.people.Person;

import java.io.InvalidClassException;

/**
 * A Calendar, wich stores a list of travels for an agent
 */
public class Calendar {
    private CalendarToTravel travels ;
    private Person owner;

    /**
     * Instantiate a new Calendar
     */

    public Calendar(Agent owner){
        this.owner = owner;
        travels = new CalendarToTravel(this) ;
        owner.setCalendar(this);
    }

    public boolean addTravel(Travel t) {
        return travels.addTravel(t) ;
    }

    public boolean removeTravel(Travel t) throws InvalidClassException {
        return travels.removeTravel(t) ;
    }

    public Person getOwner() {
        return owner;
    }

    // Do not use outside exchangeCalendarWith
    // Classes Agent and Calendar in different packages --> can't set as package-private
    public void setOwner(Person owner){
        this.owner = owner;
    }

    public CalendarToTravel getTravels() {
        return travels ;
    }

}
