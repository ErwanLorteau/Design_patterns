package fr.unantes.sce.calendar;

public class TravelToCalendar {
    private Travel parent;
    private Calendar calendar;

    public TravelToCalendar (Travel t, Calendar c) {
        this.parent = t ;
        this.calendar = c ;
    }
    public boolean addCalendar(Calendar c) {
        c.getTravels().basicAddTravel(parent) ;
       return basicAddCalendar(c);
    }

    public boolean basicAddCalendar(Calendar c ) {
        this.calendar = c ;
        return true ;
    }

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
