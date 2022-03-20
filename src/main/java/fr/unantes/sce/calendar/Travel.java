package fr.unantes.sce.calendar;

import java.util.LinkedList;

/**
 * A Travel goes from one place to another, with a departure date and an arrival date
 */
public class Travel {
    private LinkedList<Correspondence> steps;
    private Calendar calendar;

    public Travel(Calendar c) {
        this.calendar = c;
        steps = new LinkedList<Correspondence>() ;
    }

    public void addCalendar(Calendar c) {
        if (calendar != null) {
            calendar.basicRemoveTravel(this) ; //Consistancy
        }
        c.basicAddTravel(this) ;
        this.basicAddCalendar(c);
    }

    public void removeCalendar() {
        this.calendar.basicRemoveTravel(this) ;  //Consistancy
        this.basicRemoveCalendar();
    }

    //Can't garantee that we have a calendar even with [1] cardinality in the case we want to remove a calendar
    public void basicRemoveCalendar() {
        this.calendar = null ;
    }

    public void basicAddCalendar(Calendar c) {
        this.calendar = c;
    }

     /**Getters & Setter**/
    public Calendar getCalendar() {
        return calendar;
    }

    public Correspondence getFirstStep() {
        return (Correspondence) steps.get(0);
    }

    public Correspondence getLastStep() {
        return (Correspondence) steps.get(steps.size() - 1);
    }

    public boolean addCorrespondence(Correspondence step) {
        return steps.add(step);
    }

    public boolean removeCorrespondence(Correspondence step) {
        return steps.remove(step);
    }

    public LinkedList<Correspondence> getSteps(){ return this.steps ; }
}
