package A1Q1;
import java.util.*;

/**
 * Represents a sparse numeric vector. Elements are comprised of a (long)
 * location index an a (double) value.  The vector is maintained in increasing
 * order of location index, which facilitates numeric operations like
 * inner products (projections).  Note that location indices can be any integer
 * from 1 to Long.MAX_VALUE.  The representation is based upon a
 * singly-linked list.
 * The following methods are supported:  iterator, getSize, getFirst,
 * add, remove, and project, which projects the vector onto a second vector
 * passed as a parameter.
 * @author Anton Sitkovets
 */
public class SparseNumericVector implements Iterable {

    protected SparseNumericNode head = null;
    protected SparseNumericNode tail = null;
    protected long size;

  /**
     * Iterator
     */
    @Override
    public Iterator<SparseNumericElement> iterator() { //iterator
        return new SparseNumericIterator(this);
    }

    /**
     * @return number of non-zero elements in vector
     */
   public long getSize() {
        return size;
    }

     /**
     * @return the first node in the list.
     */
    public SparseNumericNode getFirst() {
        return head;
    }
    
    /**
     * Add the element to the vector.  It is inserted to maintain the
     * vector in increasing order of index.  If an element with the same
     * index already exists, its value is updated. 
     * If an element with 0 value is passed, it is ignored.
     * @param e element to add
     */
  public void add(SparseNumericElement e) {
       SparseNumericElement element = new SparseNumericElement(e.getIndex(), e.getValue());
       SparseNumericNode node = new SparseNumericNode(element, null);
       
       if(this.head == null){
    	   this.head = node;
    	   this.tail = head;
    	   size++;
       }else if( this.head == this.tail && element.getIndex() < head.getElement().getIndex()){
    	   node.setNext(this.head);
    	   this.head = node;
    	   size++;
       }else if(this.head == this.tail && element.getIndex() > this.head.getElement().getIndex()){
    	   this.head.setNext(node);
    	   this.tail = this.head.getNext();
    	   size++;
       }else if(this.head == this.tail && element.getIndex() == this.head.getElement().getIndex()){
    	   this.head.getElement().setValue(element.getValue());
       }
       else if(element.getValue() == 0){
    	   
       }else{
    	   SparseNumericNode i = head;
    	   SparseNumericNode j = i.getNext();
    	   while(i.getNext() != null){
    		   
    			   if(i.getElement().getIndex() == element.getIndex()){
    				   node.setNext(i.getNext());
    			   }
    			   if(element.getIndex() == j.getElement().getIndex()){
    				   j.getElement().setValue(element.getValue());
    				   break;
    			   }
    			   if(element.getIndex() < j.getElement().getIndex()){
    				   node.setNext(j);
    				   i.setNext(node);
    				   size++;
    				   break;
    			   }
    			   i = i.getNext();
    			   j = j.getNext();
    		   
    	   }
       } 
    }

    /**
     * If an element with the specified index exists, it is removed and the
     * method returns true.  If not, it returns false.
     *
     * @param index of element to remove
     * @return true if removed, false if does not exist
     */
    public boolean remove(Long index) {
       SparseNumericNode i = head;
       SparseNumericNode j = i.getNext();
        boolean r = false;
        for(int k = 0; k < index ; k++){
        	try{
        		if(j.getElement().getIndex() == index  && j.getElement().getValue() != 0){
        			i.setNext(j.getNext());
        			j.setNext(null);
        			r = true;
        		}else if(j.getElement().getIndex() == index  && j.getElement().getValue() == 0){
        			r = false;
        		}
            	 i = i.getNext();
    			 j = j.getNext();
        	}catch(NullPointerException e){
        		
        	}
        }
        return r;
       
        
    }

    /**
     * Returns the inner product of the vector with a second vector passed as a
     * parameter.  The vectors are assumed to reside in the same space.
     * Runs in O(m+n) time, where m and n are the number of non-zero elements in
     * each vector.
     * @param Y Second vector with which to take inner product
     * @return result of inner product
     */

    public double project(SparseNumericVector Y) {
        double product = 0;
        SparseNumericNode i = this.head;
        SparseNumericNode g = Y.head;
       
        while(i != null && g != null){
        		if(i.getElement().getIndex() == g.getElement().getIndex()){
            		product += i.getElement().getValue() * g.getElement().getValue();
            		if(this.getSize() < Y.getSize()){
            			i = i.getNext();
            		}else{
            			g = g.getNext();
            		}
            	}else{
            		if(this.getSize() < Y.getSize()){
            			g = g.getNext();
            		}else{
            			i = i.getNext();
            		}
            	}
        		
        }
        return product;
   } 
    
       /**
     * returns string representation of sparse vector
     */
    
    @Override
    public String toString() {
        String sparseVectorString = "";
        Iterator<SparseNumericElement> it = iterator();
        SparseNumericElement x;
        while (it.hasNext()) {
            x = it.next();
            sparseVectorString += "(index " + x.getIndex() + ", value " + x.getValue() + ")\n";
        }
        return sparseVectorString;
    }
}