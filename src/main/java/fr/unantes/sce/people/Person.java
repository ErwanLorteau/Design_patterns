package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Calendar;
import fr.unantes.sce.calendar.Travel;

import java.io.InvalidClassException;
import java.util.Objects;

/**
 * A Generic person, which can be an agent or an administrator
 */
public abstract class Person {
    public String name;

    public Person(String name) throws InvalidClassException {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Calendar getCalendar() throws InvalidClassException;
    public abstract void setCalendar(Calendar calendar) throws InvalidClassException;
    public abstract boolean addTravelTo(Travel travel, Person agent) throws InvalidClassException ;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getName().equals(person.getName());
    }

    @Override
    public int hashCode() {
        try {
            return Objects.hash(getName(), getCalendar());
        } catch (InvalidClassException e) {
            return Objects.hash(getName());
        }
    }
}