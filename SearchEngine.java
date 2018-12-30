import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* Vivian Lam
 * ID: 111549991
 * vivian.lam@stonybrook.edu
 * Homework 7
 * CSE214, R11 (Reed Gantz)
 */ 

public class SearchEngine {
	
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private WebGraph web;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Scanner inp = new Scanner(System.in);
		String command;
		WebGraph graph = new WebGraph();
		graph.buildFromFiles("pages.txt", "links.txt");
		System.out.println("Loading WebGraph data...");
		System.out.println("Success!");
		System.out.println();
		do {
			System.out.println("Menu: ");
			System.out.println("     (AP) - Add a new page to the graph");
			System.out.println("     (RP) - Remove a page from the graph");
			System.out.println("     (AL) - Add a link between pages in the graph");
			System.out.println("     (RL) - Remove a link between pages in the graph");
			System.out.println("     (P) - Print the graph");
			System.out.println("     (S) - Search for pages with a keyword");
			System.out.println("     (Q) - Quit");

			System.out.println();
			System.out.print("Please select an option: ");
			command = input.next();
			
			if (command.equals("AP")) {
				System.out.print("Enter a URL: ");
				String url = input.next();
				System.out.print("Enter keywords (space-separated): ");
				String key = inp.nextLine();
				String[] keys = key.split(" ");
				boolean check = false;
				
				ArrayList<String> keywords = new ArrayList<String>();
				
				for (WebPage p : graph.getPages()) {
					if (p.getUrl().equals(url)) {
						System.out.println();
						System.out.println("Error: " + url + " already exists in the WebGraph. Could not add new WebPage.");
						System.out.println();
						check = true;
						break;
					}
				}
				
				if (check == false) {
					for (int i = 0; i < keys.length; i++) {
						keywords.add(keys[i]);
					}
					graph.addPage(url, keywords);
					
					System.out.println();
					System.out.println(url + " successfully added to the webgraph");
					System.out.println();
				}
				
			}
			
			else if (command.equals("RP")) {
				System.out.print("Enter a URL: ");
				String url = input.next();
				boolean check = false;
				for (WebPage pg : graph.getPages()) {
					if (pg.getUrl().equals(url)) {
						check = true;
						break;
					}
				}
				
				if (check == false) {
					System.out.println("Error: " + url + " could not be found from the WebGraph");
				}
				else {
					graph.removePage(url);
					System.out.println();
					System.out.println(url + " has been removed from the graph");
					System.out.println();
				}
			}
			
			else if (command.equals("AL")) {
				System.out.print("Enter a source URL: ");
				String source = input.next();
				System.out.print("Enter a destination URL: ");
				String destination = input.next();
				
				boolean checksource = false;
				boolean checkdest = false;
				for (WebPage p : graph.getPages()) {
					if (p.getUrl().equals(source)) {
						checksource = true;
					}
					for (WebPage pg : graph.getPages())
						if (pg.getUrl().equals(destination))
							checkdest = true;
				}
				
				System.out.println();
				if (checkdest == false && checksource == false)
					System.out.println("Error: " + source + " and " + destination + " could not be found in the WebGraph");
				else if (checksource == false)
					System.out.println("Error: " + source + " could not be found in the WebGraph");
				else if (checkdest == false)
					System.out.println("Error: " + destination + " could not be found in the WebGraph");
				
				else {
					graph.addLink(source, destination);
					System.out.println("Link successfully added from " + source + " to " + destination);
				}
				
				System.out.println();

			}
			
			else if (command.equals("RL")) {
				System.out.print("Enter a source URL: ");
				String source = input.next();
				System.out.print("Enter a destination URL: ");
				String dest = input.next();
				boolean checksource = false;
				boolean checkdest = false;

				for (WebPage pg : graph.getPages()) {
					if (pg.getUrl().equals(source)) {
						checksource = true;
					}
					else if (pg.getUrl().equals(dest)) {
						checkdest = true;
					}
						
				}
				if (checksource == false && checkdest == false) 
					System.out.println("Error: " + source +  " and " + dest + " could not be found in the WebGraph");
		
				else if (checksource == false) 
					System.out.println("Error: " + source +  " could not be found in the WebGraph");
				else if (checkdest == false) 
					System.out.println("Error: " + dest +  " could not be found in the WebGraph");
		
				else {
					graph.removeLink(source, dest);
					System.out.println();
					System.out.println("Link removed from " + source + " to " + dest);
					System.out.println();
				}
				
			}
			
			else if (command.equals("P")) {
				System.out.println();
				System.out.println("\t(I) Sort based on index (ASC)");
				System.out.println("\t(U) Sort based on URL (ASC)");
				System.out.println("\t(R) Sort based on rank (ASC)");
				System.out.println();

				System.out.print("Please select an option: ");
				String comm = input.next();
				System.out.println();
				
				if (comm.equals("I")) {
					Collections.sort(graph.getPages(), new IndexComparator());
					graph.printTable();
				}
				
				else if (comm.equals("U")) {
					Collections.sort(graph.getPages(), new URLComparator());
					graph.printTable();
				}
				
				else if (comm.equals("R")) {
					Collections.sort(graph.getPages(), new RankComparator());
					graph.printTable();
				}
				
				System.out.println();
			}
			
			else if (command.equals("S")) {
				System.out.print("Search keyword: ");
				String key = input.next();
				System.out.println();
				boolean check = false;
				
				ArrayList<WebPage> arr = new ArrayList<WebPage>();
				for (WebPage p : graph.getPages()) {
					if (p.getKeywords().contains(key)) {
						arr.add(p);
						check = true;
					}
				}
				
				if (check == false)
					System.out.println("No search results found for the keyword " + key);
				
				else {
				
				Collections.sort(arr, new RankComparator());
				int count = 0;
				System.out.printf("%-8s%-12s%-8s", "Rank", "PageRank", "URL");
				System.out.println();
				System.out.println("-----------------------------------------");
				for (WebPage pg : arr) {
					System.out.printf("%-8d%-12s%-8s", ++count, pg.getRank(), pg.getUrl());
					System.out.println();
				}
				}
				
				System.out.println();
			}
			
			else if (command.equals("Q")) {
				System.out.println("Goodbye.");
				System.exit(0);
			}
		} while (!command.equals("Q"));
	}
	
}
