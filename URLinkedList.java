/**
 * 
 *
 * @author Jack Mandell
 * @version 2/08/20
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class URLinkedList<E> implements URList<E>,Iterable<E>
{
    private URNode<E> head;
    private URNode<E> tail;
    //Constructor creates a head and tail and connects them accordingly
    public URLinkedList() {
        head = new URNode(null,null,null);
        tail = new URNode(null,null,null);
        head.setNext(tail);
        tail.setPrev(head);
    }
    // Appends the specified element to the end of this list 
    public boolean add(E e) {
        if ( head.element() == null ) {
            head.setElement(e);
        }
        else if ( tail.element() == null ) {
            tail.setElement(e);
        }
        else {
            URNode<E> newNode = new URNode<E>(e,tail,null);
            tail.setNext(newNode);
            tail = newNode;
        } 
        return true;
    }

    // Inserts the specified element at the specified position in this list. 
    // If index = this.size(), the element is added to the end of the list
    public void add(int index, E element){
        if ( index < 0 || index > this.size() ) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        else if (index == 0 ) {
            this.addFirst(element);
        }
        else if (index == this.size() ) {
            this.addLast(element);
        }           
        else {
            URNode<E> newNode = new URNode<E>(element,null,null);
            URNode<E> leftNode = head;
            int count = 0;
            while ( count < index - 1 ) {
                leftNode = leftNode.next();
                count++; 
            }
            URNode<E> rightNode = leftNode.next();
            leftNode.setNext(newNode);
            newNode.setNext(rightNode);
            rightNode.setPrev(newNode);
            newNode.setPrev(leftNode);
         }
    }

    // Appends all of the elements in the specified collection to the end of this list,
    // in the order that they are returned by the specified collection's iterator 
    public boolean addAll(Collection<? extends E> c){
        int s = this.size();
        for ( Iterator<?> iterator = c.iterator(); iterator.hasNext(); ) {
            this.add( (E) iterator.next() );          
        }
        if ( this.size() == s ) {
            return false;
        }
        return true;
    }

    // Inserts all of the elements in the specified collection into this list 
    // at the specified position
    public boolean addAll(int index, Collection<? extends E> c){
        int s = this.size();
        for ( Iterator<?> iterator = c.iterator(); iterator.hasNext(); ) {
            this.add(index , (E) iterator.next() );          
        }
        if ( this.size() == s ) {
            return false;
        }
        return true;
    }

    // Removes all of the elements from this list, but ressettign references of head and tail
    public void clear(){
        head.setElement(null);
        tail.setElement(null);
        head.setPrev(null);
        tail.setNext(null);
        head.setNext(tail);
        tail.setPrev(head);
    }

    // Returns true if this list contains the specified element.
    public boolean contains(Object o){
        if ( this.indexOf(o) == -1 ) {
            return false;
        }
        return true;
    }

    // Returns true if this list contains all of the elements of the specified collection
    public boolean containsAll(Collection<?> c){
        for ( Iterator<?> iterator = c.iterator() ; iterator.hasNext() ; ) {
            if ( this.contains(iterator.next()) == false ) {
                return false;
            }
        }
        return true;
    }

    // Compares the specified object with this list for equality. 
    // Returns true if both contain the same elements. Ignore capacity
    // The object and this URLinkedLink must have the same elements at the same index
    public boolean equals(Object o) {
        if ( ( (Collection) o).size() != this.size() ) {
            return false;
        } 
        int i = 0;
        for ( Iterator<?> iterator = ( (Collection)o).iterator() ; iterator.hasNext() ; ) {
            if ( ! iterator.next().equals(this.get(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    // Returns the element at the specified position in this list.
    public E get(int index){
        if ( index < 0 || index > this.size() - 1 ) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        else  {
            URNode<E> temp = head;
            int count = 0;
            while ( count < index ) {
                temp = temp.next();
                count++; 
            }
            return temp.element();
        }
    }

    // Returns the index of the first occurrence of the specified element in this list, 
    // or -1 if this list does not contain the element.
    public int indexOf(Object o){
        URNode<E> temp = head;
        int count = 0;
        while (temp != null ) {
            if ( temp.element().equals( (E) o)) {
                return count;
            }
            temp = temp.next();
            count++;
        }
        return -1;
    }

    // Returns true if this list contains no elements.
    public boolean isEmpty(){
        if ( head.element() == null && tail.element() == null  ) {
            return true;
        }
        return false;
    }

    // Returns an iterator over the elements in this list in proper sequence.
    public Iterator<E> iterator(){
        return new MyIterator<E>();
    }
    //Creation of custom Iterator for URLinkedList
    class MyIterator<E> implements Iterator<E> {
          URNode c = head;         
          public boolean hasNext() {
              if ( c == null ) {
                  return false;
                }
              else if ( c.next() != null) {
                  return true;
                } 
              return false;        
           }
          public E next() {             
              if (c != null ) {
                 Object temp = c.element();
                 c = c.next();
                 return (E) temp;
                }
              throw new NoSuchElementException();
           }
        }
     
    // Removes the element at the specified position in this list and returns the element
    public E remove(int index){
        if ( index < 0 || index > this.size() - 1 ) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        } 
        E temp = this.get(index);
        URNode<E> current = head;
        int i = 0;
        if ( index == 0 ) {
                this.pollFirst();
            }
        else if ( index == this.size()-1 ) {
                this.pollLast();
            }
        else {
            while (current != null ) {
                if ( temp.equals(current.element()) ){
                    current.next().setPrev(current.prev());
                    current.prev().setNext(current.next());
                }
                current = current.next();
                i++;
            }
        } 
        return temp;
    }

    // Removes the first occurrence of the specified element from this list,
    // if it is present and returns true if an element was removed 
    public boolean remove(Object o){      
        if ( this.indexOf(o) == -1 ) {
            return false;
        }
        else {
            if ( this.indexOf(o) == 0 ) {
                this.pollFirst();
            }
            else if ( this.indexOf(o) == this.size()-1) {
                this.pollLast();               
            }
            else {
                this.remove(this.indexOf(o));
            }           
         }
         return true;
    }

    // Removes from this list all of its elements that are contained
    //  in the specified collection. Returns true if at least one element was removed
    public boolean removeAll(Collection<?> c){
        int oldsize = this.size();
        for ( Iterator<?> iterator = c.iterator() ; iterator.hasNext() ; ) {
            this.remove( iterator.next() );
        }
        if ( this.size() == oldsize ) {
            return false;
        }
        return true;
    }

    // Replaces the element at the specified position in this list
    // with the specified element 
    // Returns the original element at the given index
    public E set(int index, E element){
        if ( index < 0 || index > this.size() - 1 ) {
            throw new IndexOutOfBoundsException("Index out of Bounds");
        }
        URNode<E> current = head;
        E temp;
        int i = 0;
        while (current != null && i < index ) {
             current = current.next();
             i++;
          }
        temp = current.element();
        current.setElement(element);
        return temp;
     }

    // Returns the number of elements in this list.
    public int size(){
        URNode<E> current = head;
        int count = 0;
        while ( current != null ) {
            current = current.next();
            count++;
         }
        return count;
     }

    // Returns a view of the portion of this list 
    // between the specified fromIndex, inclusive, and toIndex, exclusive.
    // toIndex must be larger than fromIndex 
    public URList<E> subList(int fromIndex, int toIndex){
        if ( fromIndex >= toIndex || fromIndex < 0 || fromIndex > this.size()  || toIndex < 0 || toIndex > this.size()) {
            throw new IndexOutOfBoundsException("Index out of Bounds or fromIndex was not less than toIndex");
         }
        URLinkedList<E> sub = new URLinkedList<E>();
        for ( int i = fromIndex ; i < toIndex ; i++ ) {
            sub.add(this.get(i));
        }
         return sub;
    }
    // Returns an array containing all of the elements in this list
    //  in proper sequence (from first to the last element).
    public Object[] toArray(){
        E[] array = (E[]) new Object[this.size()];
        URNode<E> current = head;
        int i = 0;
        while ( current != null ) {
           array[i] = current.element();
           current = current.next();
           i++;
         }
         return array;
    }   
    // Inserts the specified element at the beginning of this list.
    void addFirst(E e) {
        if ( head.element() == null && tail.element() == null ) {
        	head.setElement(e);
        }
        else if ( head.element() != null && tail.element() != null) {
        	URNode<E> temp = new URNode<E>(e,null,head);
        	head.setPrev(temp);
        	head = temp;     
        }
    	else {
    		tail.setElement(head.element());
    		head.setElement(e);
    	}
    }
    // Appends the specified element to the end of this list.
    void addLast(E e) {
        if ( head.element() == null && tail.element() == null ) {
        	head.setElement(e);     
        }
        else if (head.element() != null && tail.element() == null ) {
        	tail.setElement(e);        	
        }
        else {
        	URNode<E> temp = new URNode<E>(e,tail,null);
        	tail.setNext(temp);
        	tail = temp;   
        }
    }
    // Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
    E peekFirst() {
        if ( head.element() == null ) {
            return null;
        }
        return head.element();
    }
    // Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
    E peekLast() {
        if ( tail.element() == null && head.element() == null ) {
            return null;
        }
        else if (head.element() != null && tail.element() == null) {
            return head.element();
        }
        else {
            return tail.element();
        }
    }
    // Retrieves and removes the first element of this list, or returns null if this list is empty.
    E pollFirst() {
    	if ( tail.element() == null && head.element() == null) {
            return null;
        }
    	else if (head.element() != null && tail.element() == null  ) {
    		E temp = head.element();
    		head.setElement(null);
    		return temp;
    	}
    	else if (head.element() != null && tail.element() != null && head.next() == tail) {
            E temp = head.element();
            head.setElement(tail.element());
            tail.setElement(null);
         	return temp;
          }
        else {
            E o = head.element();
            head = head.next();
            return o;
        }
    }
    // Retrieves and removes the last element of this list, or returns null if this list is empty.
    E pollLast() {
        if ( tail.element() == null && head.element() == null) {
            return null;
        }
        else if (head.element() != null && tail.element() == null ) {
            E temp = head.element();
            head.setElement(null);
        	return temp;
         }
        else {
            E o = tail.element();
            tail = tail.prev();
            tail.next().setPrev(null);
            tail.setNext(null);
            return o;
         }
        
     }
    //Converts LinkedList into a formatted String
     public String toString () {
        String toString = " {";
        int length = this.size();
        for ( int i = 0 ; i < length ; i++ ) {
            if ( this.get(i) != null ) {
                toString+=this.get(i) + " ";
          }
        }
        return toString + "}";
     }
   
}
