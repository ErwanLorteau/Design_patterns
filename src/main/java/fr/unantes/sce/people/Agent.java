package fr.unantes.sce.people;

import fr.unantes.sce.calendar.Calendar;
import fr.unantes.sce.calendar.Travel;

public class Agent extends Person{

    private Calendar calendar;

    public Agent(String name) {
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

    public Calendar getCalendar() {
        return calendar;
    }

    // Do not use outside Calendar constructor and exchangeCalendarWith
    // Classes Agent and Calendar in different packages --> can't set as package-private
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void exchangeCalendarWith(Agent other){
        Calendar stock = this.calendar;
        other.getCalendar().setOwner(this);
        this.setCalendar(other.getCalendar());
        other.setCalendar(stock);
        other.getCalendar().setOwner(other);
    }

    @Override
    public boolean addTravelTo(Travel travel, Agent agent) {
        if (agent != this){
            return false;
        }
        return agent.getCalendar().addTravel(travel);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}