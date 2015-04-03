package A1Q3;
import java.util.*;

/**
 * Specializes the stack data structure for comparable elements, and provides
 * a method for determining the minimum element on the stack in O(1) time.
 * @author Anton Sitkovets
 */
public class MinStack<E extends Comparable> extends Stack<E> {

    private Stack<E> minStack1;
    private Stack<E> minStack2;
    private E top;

    public MinStack() {
        minStack1 = new Stack<>();
        minStack2 = new Stack<>();
        this.top = null;
        
    }

    /* must run in O(1) time */
    public E push(E element) {
        E ret = null;
    	if ((minStack1.size() == 0) && (minStack2.size() == 0)){
        	minStack1.push(element);
        	minStack2.push(element);
        	this.top = element;
        	ret =  element;
        }
    	else if (top.compareTo(element) >= 0){
    		minStack1.push(element);
    		minStack2.push(element);
    		this.top = element;
    		ret =  element;
    	}
    	else if (top.compareTo(element) < 0){
    		minStack1.push(element);
    		this.top = top;
    		ret = top;
    	}
    	
    	return ret;
    }

    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
   public synchronized E pop() {
       E ret = this.top;
	   if (minStack1.isEmpty()){
		   throw new EmptyStackException();
       }else if (minStack1.peek().compareTo(minStack2.peek()) == 0){ 
        	   minStack2.pop();
        	   this.top = minStack2.peek();

        }else if (minStack1.peek().compareTo(minStack2.peek()) < 0){
        		
        		this.top = minStack2.peek();
        }
	   return minStack1.pop();
    }

    /* Returns the minimum value currenctly on the stack. */
    /* @exception  EmptyStackException  if this stack is empty. */
    /* must run in O(1) time */
    public synchronized E min() {
        if(minStack1.isEmpty()){
        	throw new EmptyStackException();
        }else{
        	return minStack2.peek();
        }
    	
    }
}