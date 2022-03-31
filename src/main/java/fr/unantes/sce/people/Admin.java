package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Travel;

/**
 * An admin is a person allowed to manage agents calendarn, but don't travel and don't have a calendar himself
 */

public class Admin extends Person{

    public Admin(String name) {
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
    public boolean addTravelTo(Travel travel, Agent agent) {
        return agent.getCalendar().addTravel(travel);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}