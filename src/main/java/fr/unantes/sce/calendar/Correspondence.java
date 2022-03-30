package fr.unantes.sce.calendar;

import java.io.InvalidClassException;
import java.util.Objects;
import java.time.* ;

public class Correspondence {
    private Travel travel;
    private City origin;
    private City destination;
    private ZonedDateTime startTime;
    private ZonedDateTime arrivalTime;

    public Correspondence(Travel travel, City origin, City destination, ZonedDateTime startTime, ZonedDateTime arrivalTime) throws InvalidClassException, IllegalArgumentException{
        /**Exception for null origin or destination are handled in these methods**/
        this.setOrigin(origin);
        this.setDestination(destination);
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.travel = travel;

        if(travel.getSteps().isFull()) {
            throw new InvalidClassException("The travel can't have more than " + travel.getSteps().getMaxSize() + " correspondances");
        }

        travel.basicAddCorrespondence(this);

        if(origin == destination) {
            throw new InvalidClassException("The start and destination of a correspondance must be different");
        }
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

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setOrigin(City origin) {
        if (origin == null ) {
            throw new IllegalArgumentException("Origin can't be null");
        }
        this.origin = origin;
    }

    public void setDestination(City destination) {
        if (destination == null ) {
            throw new IllegalArgumentException("Destination can't be null");
        }
        this.destination = destination ;
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
        return getOrigin().equals(that.getOrigin()) &&
                getDestination().equals(that.getDestination()) &&
                getStartTime().equals(that.getStartTime()) && //#2, we replace == by equals considering we know using an object instead of an int
                getArrivalTime().equals(that.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTravel(), getOrigin(), getDestination(), getStartTime(), getArrivalTime());
    }
}
