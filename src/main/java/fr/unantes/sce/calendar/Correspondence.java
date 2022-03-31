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
       /**Guards**/
       isNullCity(origin);
       isNullCity(destination);
       isCoherentDate(startTime, arrivalTime) ;
       isCoherentCity(origin, destination);


       this.origin = origin ;
       this.destination = destination ;
       this.startTime = startTime ;
       this.arrivalTime = arrivalTime ;
       this.travel = travel;

        /**Useless, already tested in multivalued attribute, todo::remove**/
        if(travel.getSteps().isFull()) {
            throw new InvalidClassException("The travel can't have more than " + travel.getSteps().getMaxSize() + " correspondances");
        }

        travel.basicAddCorrespondence(this);
    }

    public Travel getTravel() {
        return travel;
    }

    /**Test if departure date is before arrival date**/
    private static void isCoherentDate(ZonedDateTime departureTime, ZonedDateTime arrivalTime) {
        if ( ! (departureTime.isBefore(arrivalTime))) {
            throw  new IllegalArgumentException("Cant 'initialize a correspondance with arrivalTime anterior at startTime") ;
        }
    }
    /**Test that departure and destination arent the same**/
    private void isCoherentCity(City departure, City destination) {
        if (departure.equals(destination)){
            throw new IllegalArgumentException("Departure City can't the the same as the destination city") ;
        }
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
            throw new IllegalArgumentException("Origin can't be set as null");
        }
        this.origin = origin;
    }

    public void setDestination(City destination) {
        if (destination == null ) {
            throw new IllegalArgumentException("Destination can't be set as null");
        }
        this.destination = destination ;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Correspondences must have an StartTime") ;
        }

        if (this.arrivalTime.isBefore(startTime)) {
            throw new IllegalArgumentException("startTime can't be set, it must be anterior to arrivalTime") ;
        }
        this.startTime = startTime;
    }

    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Correspondences must have an ArrivalTime") ;
        }
        if (this.startTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("arrivalTime can't be set, must be after startTime") ;
        }

        this.arrivalTime = arrivalTime;
    }

    private static void isNullCity(City c) {
        if (c==null) {
            throw new IllegalArgumentException("Null city") ;
        }
    }

    private static void isNullTime(ZonedDateTime date) {
        if (date==null) {
            throw new IllegalArgumentException("Null date") ;
        }
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
