import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class FileReader {
	
	public static void main(String args[]) throws IOException {
		Scanner fileScan1 = new Scanner(new File("shak1.txt"));
		String shak1 = "";
		
		while(fileScan1.hasNextLine()) {
			String line = fileScan1.nextLine();
			
			Scanner lineScan = new Scanner(line);
			while(lineScan.hasNext()) {
				String word = lineScan.next();
				for(final char c : word.toCharArray()) {
					if(Character.isLetterOrDigit(c)) {
						shak1 += Character.toLowerCase(c);
					}
				}
			}
			lineScan.close();
		}
		fileScan1.close();
		
		
		Scanner fileScan2 = new Scanner(new File("shak2.txt"));
		String shak2 = "";
		
		while(fileScan2.hasNextLine()) {
			String line = fileScan2.nextLine();
			
			Scanner lineScan = new Scanner(line);
			while(lineScan.hasNext()) {
				String word = lineScan.next();
				for(final char c : word.toCharArray()) {
					if(Character.isLetterOrDigit(c)) {
						shak2 += Character.toLowerCase(c);
					}
				}
			}
			lineScan.close();
		}
		fileScan2.close();
		
		long time1 = System.currentTimeMillis();
		BruteForceSimilarity b = new BruteForceSimilarity(shak1, shak2, 8);
		long time2 = System.currentTimeMillis();
		float sim1 = b.similarity();
		long time3 = System.currentTimeMillis();
		System.out.println("Brute Force Similarity: " + sim1);
		System.out.println("Brute Force Construct Time: " + (time2 - time1));
		System.out.println("Brute Force Similarity Time: " + (time3 - time2));
		System.out.println("Brute Force Total Time: " + (time3 - time1));
	
		long time4 = System.currentTimeMillis();
		HashStringSimilarity s = new HashStringSimilarity(shak1, shak2, 8);
		long time5 = System.currentTimeMillis();
		float sim2 = s.similarity();
		long time6 = System.currentTimeMillis();
		System.out.println("Hash String Similarity: " + sim2);
		System.out.println("Hash String Construct Time: " + (time5 - time4));
		System.out.println("Hash String Similarity Time: " + (time6 - time5));
		System.out.println("Hash String Total Time: " + (time6 - time4));
		
		long time7 = System.currentTimeMillis();
		HashCodeSimilarity c = new HashCodeSimilarity(shak1, shak2, 8);
		long time8 = System.currentTimeMillis();
		float sim3 = c.similarity();
		long time9 = System.currentTimeMillis();
		System.out.println("Hash Code Similarity: " + sim3);
		System.out.println("Hash Code Construct Time: " + (time8 - time7));
		System.out.println("Hash Code Similarity Time: " + (time9 - time8));
		System.out.println("Hash Code Total Time: " + (time9 - time7));
	}
}
