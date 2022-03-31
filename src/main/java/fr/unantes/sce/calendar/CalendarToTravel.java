package fr.unantes.sce.calendar;

import java.io.InvalidClassException;

/**
 * A Class used to manage the Calendar --> Travel association
 */
public class CalendarToTravel {
    private Calendar parent;
    private MultiValuedAttribute<Travel>  travels ;

    public CalendarToTravel (Calendar c) {
        this.parent = c ;
        travels = new MultiValuedAttribute<Travel>(10) ;
    }

    /**
     * add a travel from a calendar, handling handshaking
     */
    public boolean addTravel(Travel t) {
        t.getTravelToCalendar().basicAddCalendar(parent);
        return basicAddTravel(t) ;
    }

    public boolean basicAddTravel(Travel t) {
        return this.travels.add(t) ;
    }

    /**
     * remove a travel from a calendar, handle handshaking
     */
    public boolean removeTravel(Travel t) throws InvalidClassException {
        t.getTravelToCalendar().basicRemoveCalendar();
        return basicRemoveTravel(t) ;
    }

    public boolean basicRemoveTravel(Travel t) {
        return travels.remove(t) ;
    }

    public boolean contains(Travel t) {
        return travels.contains(t) ;
    }

}
