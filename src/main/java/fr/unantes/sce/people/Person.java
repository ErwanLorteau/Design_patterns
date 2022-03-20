package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Calendar;
import fr.unantes.sce.calendar.Travel;

import java.io.InvalidClassException;
import java.util.Objects;

/**
 * A Generic person, which can be an agent or an administrator
 */
public class Person {
    public String name;
    public String role;
    protected Calendar calendar;


    public Person(String name, String role) throws InvalidClassException {
        if (!role.equals("agent") && !role.equals("admin")) {
            throw new InvalidClassException("Invalid role supplied. A person can only be an agent or an admin");
        }
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) throws InvalidClassException {
        if (!role.equals("agent") && !role.equals("admin")) {
            throw new InvalidClassException("Invalid role supplied. A person can only be an agent or an admin");
        }
        this.role = role;
    }

    public Calendar getCalendar() throws InvalidClassException {
        if (role.equals("admin")) {
            throw new InvalidClassException("Invalid operation. Only agent have a calendar");
        }
        return calendar;
    }

    public void setCalendar(Calendar calendar) throws InvalidClassException {
        if (role.equals("admin")) {
            throw new InvalidClassException("Invalid operation. Only agent have a calendar");
        }
        this.calendar = calendar;
    }

    public boolean addTravelTo(Travel travel, Person agent) throws InvalidClassException {
        if (role.equals("agent")) {
            throw new InvalidClassException("Invalid operation. Only an administrator can add travel to an agent.");
        }
        try {
            return agent.getCalendar().addTravel(travel);
        } catch (InvalidClassException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        if (getRole().equals(person.getRole()) && role.equals("agent")) {
            try {
                return getName().equals(person.getName()) && getCalendar().equals(person.getCalendar());
            } catch (InvalidClassException e) {
                return false;
            }
        }
        return getName().equals(person.getName()) && getRole().equals(person.getRole());
    }

    @Override
    public int hashCode() {
        try {
            return Objects.hash(getName(), getRole(), getCalendar());
        } catch (InvalidClassException e) {
            return Objects.hash(getName(), getRole());
        }
    }
}
