import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;

// Testing ArrayofLists class with INPUT1.txt and INPUT2.txt
public class test {
	
		public static void main(String[] args) throws IOException {
			
			// File paths
			File input1 = new File("src/INPUT1.txt");
			File input2 = new File("src/INPUT2.txt");
			
			// Scanners
			Scanner s = null;
			Scanner m = null;
			
			// Construct List #1
			ArrayList<Integer> testInput1 = new ArrayList<Integer>();
			for(int i = 0; i < 20; i++)
			{
				testInput1.add(i);
			}
			ArrayofLists firstTest = new ArrayofLists(testInput1);
			
			// Construct List #2
			ArrayList<Double> testInput2 = new ArrayList<Double>();
			for(int i = 0; i < 20; i++)
			{
				testInput2.add(i + 0.5);
			}
			ArrayofLists secondTest = new ArrayofLists(testInput2);
		
			/* Test INPUT1.txt with firstTest ArrayofLists */
			/////////////////////////////////////////////////
			try {
					s = new Scanner(new BufferedReader(new FileReader(input1)));
					while (s.hasNext())
					{
						String str = s.nextLine(); 
						
						// Variables to utilize the read string from file
						String parts[];
						int intValue;
						String stringValue;
						
						// Capture command
						char[] myChar = str.toCharArray();
						char command = myChar[0];
						
						// Handle each command
						switch(command)
						{
							case 'a':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								stringValue = parts[2];
								firstTest.add(intValue,stringValue);
								break;
							case 'r':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);		
								firstTest.remove(intValue);
								break;
							case 'g':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								System.out.println(firstTest.get(intValue));
								break;
							case 's':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								stringValue = parts[2];
								firstTest.set(intValue,stringValue);
								break;
							case 'p':
								firstTest.printList();
								break;
							case 'q':
								parts = str.split(" ");
								stringValue = parts[1];
								firstTest.add(stringValue);
								break;
							default:
								break;
						}
					}
					        
		        } finally {
		            if (s != null) 
		            {
		                s.close();
					}
			    }

			/* Test INPUT2.txt with secondTest ArrayofLists */
			//////////////////////////////////////////////////
			try {
					m = new Scanner(new BufferedReader(new FileReader(input2)));
					while (m.hasNext())
					{
						String str = m.nextLine(); 
									
						// Variables to utilize the read string from file
						String parts[];
						int intValue;
						String stringValue;
						
						// Capture command
						char[] myChar = str.toCharArray();
						char command = myChar[0];
									
						// Handle each command
						switch(command)
						{
							case 'a':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								stringValue = parts[2];
								secondTest.add(intValue,stringValue);
								break;
							case 'r':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);		
								secondTest.remove(intValue);
								break;
							case 'g':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								System.out.println(secondTest.get(intValue));
								break;
							case 's':
								parts = str.split(" ");
								intValue = Integer.parseInt(parts[1]);
								stringValue = parts[2];
								secondTest.set(intValue,stringValue);
								break;
							case 'p':
								secondTest.printList();
								break;
							case 'q':
								parts = str.split(" ");
								stringValue = parts[1];
								secondTest.add(stringValue);
								break;
							default:
								break;									
						}
					}
								        
			} finally {
				if (m != null) 
				{
					m.close();
					}
				}

		}
}
	