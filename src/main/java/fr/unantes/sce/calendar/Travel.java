package fr.unantes.sce.calendar;

/**
 * A Travel goes from one place to another, with a departure date and an arrival date
 */
public class Travel {
    private MultiValuedAttribute<Correspondence> steps;
    private Calendar calendar;

    public Travel(Calendar c) {
        this.calendar = c;
        steps = new MultiValuedAttribute<Correspondence>(10) ;
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

        if(steps.contains(step)){
            return true;
        }

        if (!steps.isFull()) {
            step.getTravel().basicRemoveCorrespondence(step); //handling consistency
            step.basicSetTravel(this);
            return basicAddCorrespondence(step); // adding
        }
        return false ;
    }

    public boolean removeCorrespondence(Correspondence step) {
        return steps.remove(step);
    }

    public MultiValuedAttribute<Correspondence> getSteps(){ return this.steps ; }

    public boolean basicAddCorrespondence(Correspondence step) { return steps.add(step) ; }

    public boolean basicRemoveCorrespondence(Correspondence step) { return steps.remove(step) ; }

}
