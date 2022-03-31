package fr.unantes.sce.calendar;

/**
 *  A Class used to manage the Travel --> calendar association
 */

public class TravelToCalendar {
    private Travel parent;
    private Calendar calendar;

    public TravelToCalendar (Travel t, Calendar c) {
        this.parent = t ;
        this.calendar = c ;
    }

    /**
     * add a Calendar to a travel (parent), handle handshaking
     */
    public boolean addCalendar(Calendar c) {
        c.getTravels().basicAddTravel(parent) ;
       return basicAddCalendar(c);
    }

    public boolean basicAddCalendar(Calendar c ) {
        this.calendar = c ;
        return true ;
    }

    /**
     * add a Calendar to a travel (parent), handle handshaking
     */
    public boolean removeCalendar() {
        this.calendar.getTravels().basicRemoveTravel(parent) ;
        return basicRemoveCalendar() ;
    }

    public boolean basicRemoveCalendar() {
      this.calendar = null ;
       return true ;
    }

    public Calendar getCalendar() {
        return calendar ;
    }
}
