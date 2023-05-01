package Oppgave;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
//import java.lang.*;
import java.net.*;

public class Oppgave {
	
	public static List<String> navn = new ArrayList<String>();

	public static void main(String[] args) {

		readFromFile();
		retrieveInformation();
		readFromUser(); 

	}
	
	public static void readFromFile() {
		File file = new File("C:\\Users\\moonl\\Java oppgave\\Vedlegg.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				navn.add(scanner.nextLine());		
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static String readfromURL(String stringurl) {
		StringBuilder content = new StringBuilder();
		
		try {
			URL url = new URL(stringurl);
			URLConnection urlconnection = url.openConnection();
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
			String line;
			while((line = bufferedreader.readLine()) != null) {
				content.append(line+"\n");
			}
			bufferedreader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
		
	}
	
	public static void retrieveInformation () {
		for (int i = 0; i < navn.size(); i++) {
			createFile(navn.get(i));
			String url = "https://api.tvmaze.com/singlesearch/shows?q="+navn.get(i)+"&embed=episodes";
			String tvshowInfo = readfromURL(url);
			writeToFile(navn.get(i),tvshowInfo);
			if(i==30) {
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
					
		}
	}
	
	public static void createFile (String filnavn) {
		try {
			File output = new File(filnavn+".txt");
			/*if (output.createNewFile()) {
				System.out.println("File created successfully");
			}else {
				System.out.println("File already exists");
			}*/
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void writeToFile (String filename, String tvshowInfo) {
		try {
			BufferedWriter skriver = new BufferedWriter(new FileWriter(filename+".txt", true));
			skriver.write(tvshowInfo);						
			//System.out.println("Successfully wrote to file");
			skriver.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFromUser() {
		try {
			System.out.println("Enter the number in the list of the title you wish to retrieve(must be int):");
			Scanner input = new Scanner(System.in);
			int linje = input.nextInt();
			System.out.println("The title of the show is: "+navn.get(linje-1));
			input.close();
		}catch(Exception e) {
			System.out.println("An error occured retrieving you TV show title");
			e.printStackTrace();
		}
	}

}
