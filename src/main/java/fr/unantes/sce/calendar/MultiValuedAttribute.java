package fr.unantes.sce.calendar;

import java.util.LinkedList;

public class MultiValuedAttribute <T> {
   private LinkedList<T> list ;
   private int maxSize ;

    public MultiValuedAttribute(int maxSize){
        list = new LinkedList<T>() ;
        this.maxSize = maxSize  ;
    }

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

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int size(){
        return list.size();
    }

    public boolean contains(T elem) {
        return list.contains(elem) ;
    }
}
