// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author John Wahlig
*/
import java.util.ArrayList;

public class HashTable
{
	// Hashtable as an ArrayList
	private ArrayList<Tuple> bucketArray;
	
	// Parallel ArrayList of loads
	private ArrayList<Integer> loadArray;
	
	//Number of buckets
	private int size;
	
	//Number of distinct elements in hashtable
	private int numElements;
	
	//Max load of hashtable
	private int maxLoad;
	
	//Hashfunction of this table
	private HashFunction hashFunction;

	public HashTable(int size) 
	{
		bucketArray = new ArrayList<Tuple>();
		loadArray = new ArrayList<Integer>();
		
		int p = findPrime(size);
		this.size = p;
		numElements = 0;
		maxLoad = 0;
		hashFunction = new HashFunction(p);
		
		//Create empty buckets and initialize loadArray to 0's
		for(int i = 0; i < this.size; i++) {
			bucketArray.add(null);
			loadArray.add(0);
		}
	}
	
	private int findPrime(int n) {
		boolean found = false;
		int num = n;
		while(!found) {
			if (isPrime(num))
				return num;
			num++;
		}
		return -1;
	}
	
	private boolean isPrime(int n) {
		for(int i= 2; i<=Math.sqrt(n); i++)
			if (n%i==0)
				return false;
		return true;
	}
	
	public ArrayList<Tuple> getBucketArray()
	{
		return bucketArray;
	}

	public int maxLoad()
	{
		return maxLoad;
	}

	public float averageLoad()
	{
		float total = 0;
		int divisor = size;
		int i;
		for(i = 0; i < size; i++) {
				total += loadArray.get(i);	
				if(loadArray.get(i) == 0) {
					divisor--;
				}
		}
		
		return (total / divisor);
	}

	public int size()
	{
		return size;
	}

	public int numElements()
	{
		return numElements;
	}

	public float loadFactor()
	{
		return ((float)numElements / size);
	}

	public void add(Tuple t)
	{
		int index = hashFunction.hash(t.getKey());
		Tuple head = bucketArray.get(index); //Head of list of this bucket
		
		//Check if distinct and increase frequency if not
		while(head != null) {
			if(t.equals(head)) {
				head.setFrequency(head.getFrequency() + 1);
				return;
			}
			head = head.getNext();
		}
		
		//Is distinct, add to head of bucket
		numElements++;
		loadArray.set(index, loadArray.get(index) + 1);
	
		//Update maxLoad
		if(loadArray.get(index) > maxLoad) {
			maxLoad = loadArray.get(index);
		}
	
		//Place element at head of list
		head = bucketArray.get(index);
		t.setNext(head);
		bucketArray.set(index, t);
		
		//Check loadfactor
		if(loadFactor() > 0.7) {
			ArrayList<Tuple> temp = bucketArray;
			bucketArray = new ArrayList<Tuple>();
			loadArray = new ArrayList<Integer>();
			int p = findPrime(2 * size);
			this.size = p;
			numElements = 0;
			maxLoad = 0;
			hashFunction = new HashFunction(p);
			
			//Reinitialize the new hashtable and loadArray
			int i;
			for(i = 0; i < size; i++) {
				bucketArray.add(null);
				loadArray.add(0);
			}
			
			//Reinsert elements to new hashtable from temp
			for(Tuple node : temp) {
				Tuple nextNode = null;
				if(node != null) {
					while (node != null) {
						nextNode = node.getNext();
						add(node);
						node = nextNode;
					}
				}
			}
		}
	}

	public ArrayList<Tuple> search(int k)
	{
		ArrayList<Tuple> result = new ArrayList<Tuple>();
		Tuple head = bucketArray.get(hashFunction.hash(k));
		
		//Check if key == k and add to result
		while(head != null) {
			if(head.getKey() == k) {
				result.add(head);
			}
			head = head.getNext();
		}
		
		return result;
	}

	public int search(Tuple t)
	{
		//Find the bucket t is in
		Tuple head = bucketArray.get(hashFunction.hash(t.getKey()));
		
		//Find duplicates of t
		while(head != null) {
			if(head.equals(t)) {
				return head.getFrequency();
			}
			head = head.getNext();
		}
		
		//Tuple never found
		return 0;
	}

	public void remove(Tuple t)
	{
		//Find the bucket t would be in
		int index = hashFunction.hash(t.getKey());
		Tuple head = bucketArray.get(index);
		
		//Need this to remove node later
		Tuple prev = null;
		
		//Search for t
		while(head != null) {
			if(head.equals(t)) {
				break;
			}
			prev = head;
			head = head.getNext();
		}
		
		//t not found
		if(head == null) {
			return;
		}
		
		//Check if this bucket's load was the maxLoad
		boolean wasMax = (loadArray.get(index) == maxLoad);
		
		//Check if t is distinct
		Tuple node = head.getNext();
		while(node != null) {
			//if t is not distinct, decrement frequency and quit
			if(node.equals(t)) {
				node.setFrequency(node.getFrequency() - 1);
				return;
			}
			node = node.getNext();
		}
		
		//If t is distinct, decrease load and numElements
		numElements--;
		loadArray.set(index, loadArray.get(index) - 1);
		
		//Update maxLoad
		if(wasMax) {
			int i;
			int max = 0;
			for(i = 0; i < loadArray.size(); i++) {
				if(loadArray.get(i) > max) {
					max = loadArray.get(i);
				}
			}
			maxLoad = max;
		}
		
		//Remove t (unlink it)
		if(prev == null) {
			bucketArray.set(index, head.getNext());
		} else {
			prev.setNext(head.getNext());
		}
	}
	
	public void printTable()
	{
		int index = 0;
		for(Tuple head : bucketArray) {
			System.out.print(index + ": ");
			while(head != null) {
				for(int i = 0; i < head.getFrequency(); i++) {
					System.out.print("(" + head.getKey() + ", " + head.getValue() + "), ");
				}
				head = head.getNext();
			}
			index++;
			System.out.println();
		}
	}
}