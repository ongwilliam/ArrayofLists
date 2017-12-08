import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class ArrayofLists<AnyType> {

	private static final int MAX_SUBLIST_SIZE = 4;
	
	private ArrayList<LinkedList> mainlist = new ArrayList<LinkedList>();
	private LinkedList<AnyType> sublist = new LinkedList<AnyType>();
	
	// Used to store last indexes
	private ArrayList<Integer> lastIndexes = new ArrayList<Integer>();
	
	// Used to initialize ArrayofLists object
	private ArrayList<AnyType> initialize = new ArrayList<AnyType>();
	
	// Constructor for INPUT1.txt and INPUT2.txt
	public ArrayofLists(AnyType x)
	{
		// Initialize lists
		mainlist.add(sublist);
		lastIndexes.add(0);
			
		// Add AnyType x to initialize list
		initialize.addAll((Collection<? extends AnyType>) x);

		// Initialize ArrayofLists object, adding one by one
		Iterator<AnyType> itr = initialize.iterator();
		while(itr.hasNext())
		{
			this.add(itr.next());
		}
		
		// LastIndexes off by one fix (lastIndex--)
		for(ListIterator<Integer> litr = lastIndexes.listIterator(); litr.hasNext();)
		{
		    int value = litr.next();
		    litr.remove();
		    litr.add(value-1);
		}
	}
	
	// Use ArrayList clear() on ArrayofLists object
	public void clear()
	{
		mainlist.clear();
	}
	
	// Return number of all items inside ArrayofLists object
	public int size()
	{
		int size = 0;
		if(this.isEmpty())
		{
			return size;
		}
		
		// Update sublists and add each size
		for(int i = 0; i < mainlist.size(); i++)
		{
			sublist = (LinkedList<AnyType>) mainlist.get(i);
			size += sublist.size();
		}
		return size;
	}
	
	// Return result of ArrayList isEmpty() on ArrayofLists object
	public boolean isEmpty()
	{
		return mainlist.isEmpty();
	}
	
	// Return value of item at index idx within ArrayofLists object
	public AnyType get(int idx)
	{
		if(idx < 0)
		{
			System.out.println("Negative index.");
			return null;
		}
		
		if(idx >= this.size())
		{
			System.out.println("Index is too large.");
			return null;
		}
		
		// Find mainlist index and update working sublist
		int mainIndex = Collections.binarySearch(lastIndexes, idx);
		
		// Collections.binarySearch returns (-(insertion point) - 1) if not found
		// Correct the mainIndex
		if(mainIndex < 0)
		{
			mainIndex *= -1;
			mainIndex--;
		}
		
		sublist = (LinkedList<AnyType>) mainlist.get(mainIndex);

		// Find the last index of the containing sublist
		int lastIndex = lastIndexes.get(mainIndex);
		
		// Calculate sublist offset and return value of correct item
		int offset = (sublist.size()-1) - (lastIndex - idx);
	
		return sublist.get(offset);
	}
	
	// Set value of item at index idx within ArrayofLists object
	public void set(int idx, AnyType newVal)
	{
		if(idx < 0)
		{
			System.out.println("Negative index.");
			return;
		}
		
		if(idx >= this.size())
		{
			System.out.println("Index is too large.");
			return;
		}
		
		// Find mainlist index and update working sublist
		int mainIndex = Collections.binarySearch(lastIndexes, idx);
		
		// Collections.binarySearch returns (-(insertion point) - 1) if not found
		// Correct the mainIndex
		if(mainIndex < 0)
		{
			mainIndex *= -1;
			mainIndex--;
		}
		
		sublist = (LinkedList<AnyType>) mainlist.get(mainIndex);
				
		// Find the last index of the containing sublist
		int lastIndex = lastIndexes.get(mainIndex);
				
		// Calculate sublist offset and set new value
		int offset = (sublist.size()-1) - (lastIndex - idx);
		sublist.set(offset, newVal);
	}
	
	// Add item to very end of ArrayofLists, creating new sublist and updating indexes if necessary
	// Returns true
	public boolean add(AnyType x)
	{
		// Find mainlist index and update working sublist
		int mainIndex = mainlist.size()-1;
		sublist = (LinkedList<AnyType>) mainlist.get(mainIndex);
		
		// New sublist needed
		if(sublist.size() >= MAX_SUBLIST_SIZE)
		{
			// Add item to new sublist and append to mainlist
			sublist = new LinkedList<AnyType>();
			sublist.addFirst(x);
			mainlist.add(sublist);
			
			// Update lastIndexes due to one new sublist
			int lastIndex = lastIndexes.get(mainIndex) + 1;
			lastIndexes.add(lastIndex);
			
			return true;
		}
		
		// Add item to sublist and update mainlist
		sublist.addLast(x);
		mainlist.set(mainIndex,sublist);
		
		// Update lastIndexes due to larger sublist
		int lastIndex = lastIndexes.get(mainIndex) + 1;
		lastIndexes.set(mainIndex,lastIndex);	
		
		return true;
	}
	
	// Add item at index idx, if sublists are full then shift items to right to make room
	// Update indexes as sublists are updated
	public void add(int idx, AnyType x)
	{	
		if(idx < 0)
		{
			System.out.println("Negative index.");
			return;
		}
		
		// Outside of indexes, create new sublist
		if(idx >= this.size())
		{
			add(x);
			return;
		}
		
		// Find mainlist index and update working sublist
		int mainIndex = Collections.binarySearch(lastIndexes, idx);
		
		// Collections.binarySearch returns (-(insertion point) - 1) if not found
		// Correct the mainIndex
		if(mainIndex < 0)
		{
			mainIndex *= -1;
			mainIndex--;
		}
		
		sublist = (LinkedList<AnyType>) mainlist.get(mainIndex);
		
		// Find the last index of the containing sublist
		int lastIndex = lastIndexes.get(mainIndex);
		// Calculate sublist offset
		int offset = (sublist.size()-1) - (lastIndex - idx);
		
		// Basecase: New sublist needed at end of ArrayofLists
		// Use add(AnyType x)
		if(idx > lastIndex)
		{
			add(x);
			return;
		}
		
		// Shifting needed
		else if(sublist.size() >= MAX_SUBLIST_SIZE)
		{
			// Insert new value
			sublist.add(offset, x);
			
			// Remove last item of sublist
			AnyType item = sublist.removeLast();
			
			// Update mainlist
			mainlist.set(mainIndex, sublist);

			// Update lastIndex
			lastIndex++;
			
			// Recursively add to next sublist
			add(lastIndex, item);
		}
		
		else
		{
			// Basecase: Sublist has room 
			// Insert item x and update last indexes
			sublist.add(offset, x);
			mainlist.set(mainIndex, sublist);
			for(int i = mainIndex; i < lastIndexes.size(); i++)
			{
				int updatedIndex = lastIndexes.get(i) + 1;
				lastIndexes.set(i, updatedIndex);
			}
		}
		
	}
	
	// Remove item at index idx then update sublists and indexes
	public AnyType remove(int idx)
	{
		if(idx < 0)
		{
			System.out.println("Negative index.");
			return null;
		}
		
		if(idx >= this.size())
		{
			System.out.println("Index is too large.");
			return null;
		}
		
		// Find mainlist index and update working sublist
		int mainIndex = Collections.binarySearch(lastIndexes, idx);
		
		// Collections.binarySearch returns (-(insertion point) - 1) if not found
		// Correct the mainIndex
		if(mainIndex < 0)
		{
			mainIndex *= -1;
			mainIndex--;
		}
		
		sublist = (LinkedList<AnyType>) mainlist.get(mainIndex);
		
		// Find the last index of the containing sublist
		int lastIndex = lastIndexes.get(mainIndex);
		// Calculate sublist offset
		int offset = (sublist.size()-1) - (lastIndex - idx);
		
		// Sublists need to be shifted left due to empty spot after remove
		if(sublist.size() == 1)
		{
			// Remove item
			AnyType item = sublist.remove(offset);
			// Update last indexes
			for(int i = mainIndex+1; i < lastIndexes.size(); i++)
			{
				int updatedIndex = lastIndexes.get(i) - 1;
				lastIndexes.set(i, updatedIndex);
			}
			
			// Remove empty sublist from mainlist
			mainlist.remove(mainIndex);
			// Remove corresponding last index
			lastIndexes.remove(mainIndex);
			return item;
		}
		
		// Remove item and update sublists
		AnyType item = sublist.remove(offset);
		mainlist.set(mainIndex, sublist);
		
		// Update last indexes
		for(int i = mainIndex; i < lastIndexes.size(); i++)
		{
			int updatedIndex = lastIndexes.get(i) - 1;
			lastIndexes.set(i, updatedIndex);
		}
		return item;
		
	}
	
	// Return true if there is a next item, false otherwise
	public boolean hasNext()
	{
		ListIterator<AnyType> litr = sublist.listIterator();
		
		if(litr.next() != null)
		{
			return true;
		}
		return false;
	}
	
	// Return item at the next index, null if none exists
	public AnyType next()
	{		
		Iterator<?> itr = mainlist.iterator();
		
		do
		{
			ListIterator<AnyType> litr = sublist.listIterator();
	
			if(litr.next() != null)
			{
				return litr.next();
			}
			else
			{
				sublist = (LinkedList<AnyType>) itr.next();
			}
		}
		while(itr.hasNext());
		
		return null;
		
	}
	
	// Print the ArrayofLists
	public void printList()
	{
		System.out.println("ArrayofLists object contains " + this.size() + " elements.");
		for(int i = 0; i < this.size(); i++)
		{
			AnyType value = this.get(i);
			System.out.println("Index " + i + ": " + value);
		}
	}

	
}
