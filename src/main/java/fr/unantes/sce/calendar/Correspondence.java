package fr.unantes.sce.calendar;

import java.io.InvalidClassException;
import java.util.Objects;
import java.time.* ;

public class Correspondence {
    private Travel travel;
    private City startCity;
    private City destinationCity;
    private ZonedDateTime startTime;
    private ZonedDateTime arrivalTime;

    public Correspondence(Travel travel, City startCity, City destinationCity, ZonedDateTime startTime, ZonedDateTime arrivalTime) throws InvalidClassException {
        this.travel = travel;
        if(travel.getSteps().isFull()) throw new InvalidClassException("The travel can't have more than " + travel.getSteps().getMaxSize() + " correspondances");
        travel.basicAddCorrespondence(this);
        if(startCity == destinationCity) throw new InvalidClassException("The start and destination of a correspondance must be different");
        this.startCity = startCity;
        this.destinationCity = destinationCity;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
    }

    public Travel getTravel() {
        return travel;
    }

    public boolean setTravel(Travel t) {
        if( t.getSteps().isFull() ) {
            return false;
        }
        this.travel.basicRemoveCorrespondence((this));
        t.basicAddCorrespondence(this) ;
        this.basicSetTravel(t);
        return true ;
    }

    public void basicSetTravel(Travel t) {
        this.travel = t;
    }

    public City getStartCity() {
        return startCity;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correspondence that = (Correspondence) o;
        return getStartCity().equals(that.getStartCity()) &&
                getDestinationCity().equals(that.getDestinationCity()) &&
                getStartTime().equals(that.getStartTime()) && //#2, we replace == by equals considering we know using an object instead of an int
                getArrivalTime().equals(that.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTravel(), getStartCity(), getDestinationCity(), getStartTime(), getArrivalTime());
    }
}
