/*******************************************************************************
 * 2008-2014 Public Domain
 * Contributors
 * Marco Lopes (marcolopes@netc.pt)
 *******************************************************************************/
package org.dma.java.utils.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {

	/*
	 * Conversion
	 */
	public static <T> T[] toArray(Collection<T> c, T[] a) {
		if (c.size()>a.length){
			a=(T[])Array.newInstance(a.getClass().getComponentType(), c.size());
		}
		return c.toArray(a);
	}

	/** The collection CAN be empty */
	public static <T> T[] toArray(Collection<T> c, Class klass) {
		return c.toArray((T[])Array.newInstance(klass, c.size()));
	}

	/** The collection CANNOT be empty! */
	public static <T> T[] toArray(Collection<T> c) {
		return toArray(c, c.iterator().next().getClass());
	}





	/*
	 * Analysis
	 */
	public static <T> int occurences(Collection<T> collection, String searchFor) {

		int count=0;

		for (T element: collection){
			if(element.toString().contains(searchFor)) count++;
		}

		return count;

	}





	/*
	 * Transformation
	 */
	public static <T> String concat(Collection<T> collection, String separator) {

		StringBuilder result=new StringBuilder();

		for (T element: collection){
			if (result.length()>0) result.append(separator);
			result.append(element.toString());
		}

		return result.toString();

	}


	public static <T> String[] addPrefix(Collection<T> collection, String prefix) {

		String[] result=new String[collection.size()];

		int index=0;
		for (T element: collection){
			result[index++]=prefix+element;
		}

		return result;

	}


	public static <T> String[] addSuffix(Collection<T> collection, String suffix) {

		String[] result=new String[collection.size()];

		int index=0;
		for (T element: collection){
			result[index++]=element+suffix;
		}

		return result;

	}


	/** Returns removed elements */
	public static <T> Collection<T> removeContaining(Collection<T> collection, String searchFor){

		List<T> removeList=new ArrayList();

		for (T element: collection){
			if(element.toString().contains(searchFor)) removeList.add(element);
		}

		collection.removeAll(removeList);

		return removeList;

	}


	public static <T> void removeContaining(Collection<T> collection, String[] searchFor){

		for(int i=0; i<searchFor.length; i++){
			removeContaining(collection, searchFor[i]);
		}

	}


	/** Returns removed elements */
	public static <T> Collection<T> remove(List<T> list, int[] indexes) {

		List<T> removeList=new ArrayList(indexes.length);

		for(int index: indexes){
			removeList.add(list.get(index));
		}

		list.removeAll(removeList);

		return removeList;

	}


	/** Returns inserted elements */
	public static <T> Collection<T> insert(List<T> into, List<T> from, int[] indexes, int position) {

		List<T> insertList=new ArrayList(indexes.length);

		Arrays.sort(indexes);
		for(int index: indexes){
			insertList.add(from.get(index));
		}

		into.addAll(position, insertList);

		return insertList;

	}


	public static <T> void moveDown(List<T> list, int[] indexes, int position) {

		Arrays.sort(indexes);
		list.addAll(position, remove(list, indexes));

	}


	public static <T> void moveUp(List<T> list, int[] indexes, int position) {

		Arrays.sort(indexes);
		list.addAll(position, remove(list, indexes));

	}


	public static void main(String[] argvs){
	}


}