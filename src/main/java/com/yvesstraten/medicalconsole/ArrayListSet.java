package com.yvesstraten.medicalconsole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/** 
 * This class is an alternate version of an {@link ArrayList}, with capabilities of a {@link Set}
*/
public class ArrayListSet<E> extends ArrayList<E> implements Set<E> {
  /**
   * Constructs an empty list with an initial capacity of ten.
   */
	public ArrayListSet(){
		super();
	}

  /**
   * Constructs a list containing the elements of the specified
   * collection, in the order they are returned by the collection's
   * iterator.
   *
   * @param c the collection whose elements are to be placed into this list
   * @throws NullPointerException if the specified collection is null
   */
	public ArrayListSet(Collection<? extends E> c){
		super(c);
	}


	/** 
	 * {@inheritDoc}
	 *
	 * Furthermore, this insertion checks that there are 
	 * no duplicates already present
	*/
	@Override
	public boolean add(E e){
		if(!super.contains(e)){
			return super.add(e);
		} 	

		return false;
	}
}
