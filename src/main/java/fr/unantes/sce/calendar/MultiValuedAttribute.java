package fr.unantes.sce.calendar;

import java.util.LinkedList;

/**
 * A Class to handle sized list in the whole project
 */

public class MultiValuedAttribute <T> {
   private LinkedList<T> list ;
   private int maxSize ;

    /**
     * Instanciate a MultivaluedAttribute wich store a list of a maximum size
     * @param maxSize The maximum size
     */

    public MultiValuedAttribute(int maxSize){
        list = new LinkedList<T>() ;
        this.maxSize = maxSize  ;
    }

    /**
     * Add an element to the list if the list isn't full
     */

    public boolean add(T elem) {
        if (list.size() < maxSize) {
            return list.add(elem) ;
        } else {
            throw new IndexOutOfBoundsException("Cant add more than "+ maxSize +"elements in the list");
        }
    }

    public boolean remove(T elem) {
        return list.remove(elem);
    }

    public T get(int i) { return this.list.get(i); }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int size(){
        return list.size();
    }

    public boolean isFull() {return this.list.size() == maxSize ; }

    public boolean contains(T elem) {
        return list.contains(elem) ;
    }
}
