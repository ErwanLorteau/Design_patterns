package fr.unantes.sce.calendar;

/**
 * A Travel goes from one place to another, with a departure date and an arrival date
 */
public class Travel {
    private MultiValuedAttribute<Correspondence> steps;
    private TravelToCalendar travelToCalendar;

    /**
     * Instanciate a Travel and link it to i'ts calendar
     */

    public Travel(Calendar c) {
        this.travelToCalendar = new TravelToCalendar(this, c);
        steps = new MultiValuedAttribute<Correspondence>(10) ;
    }
    /**
     * Add a calendar to a travel, handle handshaking
     */

    public void addCalendar(Calendar c) {
        travelToCalendar.addCalendar(c) ;

    }

    /**
     * Remove a calendar of a travel, handle handshaking
     */

    public boolean removeCalendar() {
        return travelToCalendar.removeCalendar();
    }
    //Can't garantee that we have a calendar even with [1] cardinality in the case we want to remove a calendar


     /**Getters & Setter**/
    public Calendar getCalendar() {
        return travelToCalendar.getCalendar();
    }

    public TravelToCalendar getTravelToCalendar() {
        return travelToCalendar;
    }

    public Correspondence getFirstStep() {
        return (Correspondence) steps.get(0);
    }

    public Correspondence getLastStep() {
        return (Correspondence) steps.get(steps.size() - 1);
    }


    /**
     * Adding a Correspondence to a travel
     */

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


    public boolean hasChainPattern(){

        Correspondence actual, next;
        for(int i = 0; i < steps.size()-1; i++){
            actual = steps.get(i);
            next = steps.get(i+1);
            if(actual.getDestination() != next.getOrigin()){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the list of Correspondence are cohenrent in term of departure time and arrivalTime
     */
    public boolean isChronologicallyCorrect(){

        Correspondence actual, next;
        for(int i = 0; i < steps.size()-1; i++){
            actual = steps.get(i);
            next = steps.get(i+1);
            if(actual.getStartTime().isAfter(actual.getArrivalTime()) || actual.getArrivalTime().isAfter(next.getStartTime())){
                System.out.println(actual.getOrigin().name);
                System.out.println(actual.getDestination().name);
                return false;
            }
        }
        return true;
    }

    public boolean isCoherent(){
        return hasChainPattern() && isChronologicallyCorrect();
    }
}
