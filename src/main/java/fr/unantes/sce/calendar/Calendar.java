package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Person;

import java.io.InvalidClassException;

/**
 * A Calendar stores a list of travels for an agent
 */
public class Calendar {
    private CalendarToTravel travels ;
    private Person owner;

    public Calendar(Person owner) {
        this.owner = owner;
        travels = new CalendarToTravel(this) ;
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

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public CalendarToTravel getTravels() {
        return travels ;
    }


}
