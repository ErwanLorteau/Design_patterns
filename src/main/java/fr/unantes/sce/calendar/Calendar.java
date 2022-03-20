package fr.unantes.sce.calendar;

import fr.unantes.sce.people.Person;

/**
 * A Calendar stores a list of travels for an agent
 */
public class Calendar {
    private MultiValuedAttribute<Travel> travels ;
    private Person owner;

    public Calendar(Person owner) {
        this.owner = owner;
        travels = new MultiValuedAttribute<Travel>(10) ;
    }

    //We call two time basic add travel cause  :
    // 1) we verify if the list if full or not for the return statement
    // 2) after handling consistancy, we need a second call to add the travel for real (we need to remove it before for edge consistancy testing case)
    public boolean addTravel(Travel t) {
       if ( basicAddTravel(t)  ) { //can fail if size >10
           t.getCalendar().basicRemoveTravel(t); //handling consistency
           t.basicAddCalendar(this);
           return basicAddTravel(t); // adding
       }
       return false ;
    }

    public boolean removeTravel(Travel t) {
        t.basicRemoveCalendar();
        return basicRemoveTravel(t) ;
    }

    public boolean basicAddTravel(Travel t) {
        return travels.add(t);
    }

    public boolean basicRemoveTravel(Travel t) {
        return travels.remove(t);
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public MultiValuedAttribute<Travel> getTravels() {
        return travels;
    }
}
