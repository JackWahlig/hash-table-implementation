// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author John Wahlig
*/

public class Tuple
{
	// member fields and other member methods
	private int key;
	private String value;
	private int frequency;
	private Tuple next;
	
	public Tuple(int keyP, String valueP)
	{
		key = keyP;
		value = valueP;
		frequency = 1;
	}

	public int getKey()
	{
		return key;
	}

	public String getValue()
	{
		return value;
	}

	public int getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(int freq)
	{
		frequency = freq;
	}
	
	public Tuple getNext()
	{
		return next;
	}
	
	public void setNext(Tuple t)
	{
		next = t;
	}
	
	public boolean equals(Tuple t)
	{
		return (this.key == t.key && this.value.equals(t.value));
	}
}