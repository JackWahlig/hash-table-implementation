

// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author John Wahlig
*/

public class HashCodeSimilarity
{
	private HashTable s1Table;
	private HashTable s2Table;

	public HashCodeSimilarity(String s1, String s2, int sLength)
	{
		s1Table = new HashTable(s1.length() - sLength);
		s2Table = new HashTable(s2.length() - sLength);
		
		//Create hashtables
		int i, j;
		int key = 0;
		long alpha = 1;
		//Get first substring and hashvalue (using 31 as alpha)
		for(j = sLength - 1; j >= 0; j--) {
			key += (alpha * s1.charAt(j));
			alpha = alpha * 31;
		}
		alpha /= 31;
		s1Table.add(new Tuple(key, ""));
		//Get subsequent substrings and hashvalues based on first
		for(i = 1; i <= s1.length() - sLength; i++) {
			key = (int) (31 * (key - (alpha * s1.charAt(i - 1))) + s1.charAt(i + sLength - 1));
			s1Table.add(new Tuple(key, ""));
		}
		//Same thing for s2
		key = 0;
		alpha = 1;
		//Get first substring and hashvalue (using 31 as alpha)
		for(j = sLength - 1; j >= 0; j--) {
			key += (alpha * s2.charAt(j));
			alpha = alpha * 31;
		}
		alpha /= 31;
		s2Table.add(new Tuple(key, ""));
		//Get subsequent substrings and hashvalues based on first
		for(i = 1; i <= s2.length() - sLength; i++) {
			key = (int) (31 * (key - (alpha * s2.charAt(i - 1))) + s2.charAt(i + sLength - 1));
			s2Table.add(new Tuple(key, ""));
		}
	}

	public float lengthOfS1()
	{
		int sum = 0;
		//iterate through s1Shingles
		for(Tuple node : s1Table.getBucketArray()) {
			while(node != null) {
				sum += node.getFrequency() * node.getFrequency();
				node = node.getNext();
			}
		}
	
		return (float) Math.sqrt(sum);
	}

	public float lengthOfS2()
	{
		int sum = 0;
		//iterate through s1Shingles
		for(Tuple node : s2Table.getBucketArray()) {
			while(node != null) {
				sum += node.getFrequency() * node.getFrequency();
				node = node.getNext();
			}
		}
		
		return (float) Math.sqrt(sum);
	}

	public float similarity()
	{
		int sum = 0;
		
		//Build union and find sum simultaneously (really just an intersection though)
		for(Tuple node1 : s1Table.getBucketArray()) {
			while(node1 != null) {
				sum += node1.getFrequency() * (s2Table.search(node1));
				node1 = node1.getNext();
			}
		}
		return sum / (lengthOfS1() * lengthOfS2());
	}
}