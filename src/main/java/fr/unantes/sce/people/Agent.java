package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Calendar;
import fr.unantes.sce.calendar.Travel;

import java.io.InvalidClassException;

public class Agent extends Person{

    private Calendar calendar;

    public Agent(String name) throws InvalidClassException {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public Calendar getCalendar() throws InvalidClassException {
        return calendar;
    }

    @Override
    public void setCalendar(Calendar calendar) throws InvalidClassException {
        this.calendar = calendar;
    }

    @Override
    public boolean addTravelTo(Travel travel, Person agent) throws InvalidClassException {
        if (agent != this){
            return false;
        }
        return agent.getCalendar().addTravel(travel);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}