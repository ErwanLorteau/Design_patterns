package fr.unantes.sce.calendar;

import java.io.InvalidClassException;
import java.util.Objects;
import java.time.* ;

/**
 * A Correspondance in a Travel
 */
public class Correspondence {
    private Travel travel;
    private City origin;
    private City destination;
    private ZonedDateTime startTime;
    private ZonedDateTime arrivalTime;

    /**
     * Initialize a correspondance (Both city and time's cant be set as null. Times have to be coherent
     */

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

        if(travel.getSteps().isFull()) {
            throw new InvalidClassException("The travel can't have more than " + travel.getSteps().getMaxSize() + " correspondances");
        }

        travel.basicAddCorrespondence(this);
    }

    public Travel getTravel() {
        return travel;
    }


    /**
     * Test if departure date is before arrival date
     * @throw IllegalArgumentException if departureTime is after arrivalTime
     */

    private static void isCoherentDate(ZonedDateTime departureTime, ZonedDateTime arrivalTime) {
        if ( ! (departureTime.isBefore(arrivalTime))) {
            throw  new IllegalArgumentException("Cant 'initialize a correspondance with arrivalTime anterior at startTime") ;
        }
    }

    /**
     * Test if departure and destination city are equals
     * @throw IllegalArgumentException if they are equals
     */
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

    /**
     * Set a non-null origin city
     */
    public void setOrigin(City origin) {
        if (origin == null ) {
            throw new IllegalArgumentException("Origin can't be set as null");
        }
        this.origin = origin;
    }

    /**
     * Set a non-null destination city
     * @throw IllegalArgumentException if i'ts null
     */

    public void setDestination(City destination) {
        if (destination == null ) {
            throw new IllegalArgumentException("Destination can't be set as null");
        }
        this.destination = destination ;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }


    /**
     * set a non null startTime
     * @throw IllegalArgumentException if it's null
     * @throw IllegalArgumentException if time's are not coherent
     */
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

    /**
     * set a Non null arrivalTime
     * @throw IllegalArgumentException if it's null
     * @throw IllegalArgumentException if times ares not coherent
     */

    public void setArrivalTime(ZonedDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Correspondences must have an ArrivalTime") ;
        }
        if (this.startTime.isAfter(arrivalTime)) {
            throw new IllegalArgumentException("arrivalTime can't be set, must be after startTime") ;
        }

        this.arrivalTime = arrivalTime;
    }

    /**
     * Test if a city is null
     * @trhow IllegalArgumentException if null
     */

    private static void isNullCity(City c) {
        if (c==null) {
            throw new IllegalArgumentException("Null city") ;
        }
    }

    /**
     * Test if a time is null
     * @trhow IllegalArgumentException if null
     */
    private static void isNullTime(ZonedDateTime date) {
        if (date==null) {
            throw new IllegalArgumentException("Null date") ;
        }
    }
    /**
     * Test if two correspondence are equals (all attributes are equals)
     */

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

    /**
     * Generate a HashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTravel(), getOrigin(), getDestination(), getStartTime(), getArrivalTime());
    }
}
