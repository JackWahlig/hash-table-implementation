import java.util.ArrayList;

// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

/**
* @author John Wahlig
*/

public class BruteForceSimilarity
{
	private ArrayList<String> s1Shingles;
	private ArrayList<String> s2Shingles;

	public BruteForceSimilarity(String s1, String s2, int sLength)
	{
		s1Shingles = new ArrayList<String>();
		s2Shingles = new ArrayList<String>();
		
		//Create sets of shingles
		int i;
		for(i = 0; i <= s1.length() - sLength; i++) {
			s1Shingles.add(s1.substring(i, i + sLength));
		}
		for(i = 0; i <= s2.length() - sLength; i++) {
			s2Shingles.add(s2.substring(i, i + sLength));
		}
	}

	public float lengthOfS1()
	{
		int numOccurrences;
		int sum = 0;
		boolean notCounted;
		ArrayList<String> alreadyCounted = new ArrayList<String>();
		
		//Iterate through s1Shingles and find vector length
		int i;
		for(i = 0; i < s1Shingles.size(); i++) {
			String str1 = s1Shingles.get(i);
			numOccurrences = 1;
			notCounted = true;
			for(String str2 : alreadyCounted) {
				if(str1.equals(str2)) {
					notCounted = false;
					break;
				}
			}
			if(notCounted) {
				int j;
				for(j = i + 1; j < s1Shingles.size(); j++) {
					if(str1.equals(s1Shingles.get(j))) {
						numOccurrences++;
					}
				}
				alreadyCounted.add(str1);
				sum += numOccurrences * numOccurrences;
			}
		}
		
		return (float) Math.sqrt(sum);
	}

	public float lengthOfS2()
	{
		int numOccurrences;
		int sum = 0;
		boolean notCounted;
		ArrayList<String> alreadyCounted = new ArrayList<String>();
		
		//Iterate through s1Shingles and find vector length
		int i;
		for(i = 0; i < s2Shingles.size(); i++) {
			String str1 = s2Shingles.get(i);
			numOccurrences = 1;
			notCounted = true;
			for(String str2 : alreadyCounted) {
				if(str1.equals(str2)) {
					notCounted = false;
					break;
				}
			}
			if(notCounted) {
				int j;
				for(j = i + 1; j < s2Shingles.size(); j++) {
					if(str1.equals(s2Shingles.get(j))) {
						numOccurrences++;
					}
				}
				alreadyCounted.add(str1);
				sum += numOccurrences * numOccurrences;
			}
		}
		
		return (float) Math.sqrt(sum);
	}

	public float similarity()
	{
		ArrayList<String> counted = new ArrayList<String>();
		int sum = 0;
		int numOccurrencesS1;
		int numOccurrencesS2;
		int i, j;
		for(i = 0; i < s1Shingles.size(); i++) {
			if(!counted.contains(s1Shingles.get(i))) {
				counted.add(s1Shingles.get(i));
				numOccurrencesS1 = 1;
				numOccurrencesS2 = 0;
				//Find number of times str is in s1Shingles
				for(j = i + 1; j < s1Shingles.size(); j++) {
					if(s1Shingles.get(i).equals(s1Shingles.get(j))) {
						numOccurrencesS1++;
					}
				}
				//Find number of times str is in s2Shingles
				for(String str : s2Shingles) {
					if(s1Shingles.get(i).equals(str)) {
						numOccurrencesS2++;
					}
				}
				sum += numOccurrencesS1 * numOccurrencesS2;
			}
		}
		
		return sum / (lengthOfS1() * lengthOfS2());
	}
}