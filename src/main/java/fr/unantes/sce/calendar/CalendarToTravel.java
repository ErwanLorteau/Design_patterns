package fr.unantes.sce.calendar;

import java.io.InvalidClassException;

public class CalendarToTravel {
    private Calendar parent;
    private MultiValuedAttribute<Travel>  travels ;

    public CalendarToTravel (Calendar c) {
        this.parent = c ;
        travels = new MultiValuedAttribute<Travel>(10) ;
    }
    public boolean addTravel(Travel t) {
        t.getTravelToCalendar().basicAddCalendar(parent);
        return basicAddTravel(t) ;
    }

    public boolean basicAddTravel(Travel t) {
        return this.travels.add(t) ;
    }

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
