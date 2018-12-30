import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author vivi3
 * Organizes the WebPage objects as a directed graph
 */
public class WebGraph {
	
	private static final int MAX_PAGES = 40;
	private static ArrayList<WebPage> pages = new ArrayList<WebPage>();
	private static int[][] links = new int[MAX_PAGES][MAX_PAGES];

	public WebGraph() {}
	
	/**
	 * @param pages
	 * @param links
	 */
	public WebGraph(ArrayList<WebPage> pages, int[][] links) {
		this.pages = pages;
		this.links = links;
	}

	/**
	 * @return an array of webpages
	 */
	public static ArrayList<WebPage> getPages() {
		return pages;
	}

	/**
	 * @return a 2-dimensional array of integers (adjacency matrix)
	 */
	public static int[][] getLinks() {
		return links;
	}

	/**
	 * Constructs a WebGraph object using the indicated files as the source for pages and edges.
	 * @param pagesFile String of the relative path to the file containing the page information.
	 * @param linksFile String of the relative path to the file containing the link information.
	 * @return The WebGraph constructed from the text files.
	 * @throws IllegalArgumentException Thrown if either of the files does not reference a valid text file,
	 * or if the files are not formatted correctly.
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException {
		File pf = new File(pagesFile);
		File lf = new File(linksFile);

		Scanner input;
		Scanner input2;
		
		if (pagesFile.equals(null) || linksFile == null) 
			throw new IllegalArgumentException();
		
		try {
			input = new Scanner(pf);
			input2 = new Scanner(lf);
			ArrayList<String> keywords = new ArrayList<String>();
			int count = 0;
			while (input.hasNextLine()) {
				String line = input.nextLine();
				line = line.trim();
				String[] arr = line.split(" ");
				WebPage page = new WebPage();
				page.setUrl(arr[0]);
				page.setIndex(count);
				page.setRank(0);
				for (int i = 1; i < arr.length; i++) {
					keywords.add(arr[i]);
				}
				page.setKeywords(keywords);
				keywords = new ArrayList<String>();
				pages.add(page);	
				count++;
			}
			
			links = new int[pages.size()][pages.size()];
			while (input2.hasNextLine()) {
				String line2 = input2.nextLine();
				line2 = line2.trim();
				String[] arr = line2.split(" ");
				int from = -1;
				int to = -1;
				
				for (int i = 0 ; i < pages.size(); i++) {
					if (pages.get(i).getUrl().equals(arr[0])) {
						for (int j = 0 ; j < pages.size(); j++) {
							if (pages.get(j).getUrl().equals(arr[1])) {
								from = pages.get(i).getIndex();
								to = pages.get(j).getIndex();
								links[from][to] = 1;
							}
						}
					}
				}
			}
			
			WebGraph graph = new WebGraph(pages, links);
			graph.genLink();
			graph.updatePageRanks();
			
			
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found exception.");
		}
		
		return null;
	}
	
	/**
	 * Adds a page to the WebGraph.
	 * @param url The URL of the webpage (must not already exist in the WebGraph).
	 * @param keywords The keywords associated with the WebPage.
	 * @throws IllegalArgumentException If url is not unique and already exists in the graph, or if either argument is null.
	 */
	public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException {
		if (url.equals(null) || keywords == null) 
			throw new IllegalArgumentException();
		
		for (WebPage p : pages) {
			if (p.getUrl().equals(url))
				throw new IllegalArgumentException();
		}
		
		WebPage pg = new WebPage();
		pg.setIndex(pages.size());
		pg.setKeywords(keywords);
		pg.setUrl(url);
		pages.add(pg);
		int[][] links1 = new int[pages.size()][pages.size()];
		
		for (int i = 0; i < links.length; i++) {
		    for (int j = 0; j < links[i].length; j++) {
		    	links1[i][j] = links[i][j];
		    }
		}
				
		links = links1;
		genLink();
	}
	
	/**
	 * Adds a link from the WebPage with the URL indicated by source to the WebPage with the URL indicated by destination
	 * @param source the URL of the page which contains the hyperlink to destination.
	 * @param destination the URL of the page which the hyperlink points to.
	 * @throws IllegalArgumentException If either of the URLs are null or could not be found in pages.
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException {
		boolean check = false;
		if (source.equals(null) || destination.equals(null))
			throw new IllegalArgumentException();
		
		for (WebPage p : pages) {
			if (p.getUrl().equals(source))
				for (WebPage pg : pages)
					if (pg.getUrl().equals(destination))
						check = true;
		}
		
		if (check == false)
				throw new IllegalArgumentException();
		
		int from = -1;
		int to = -1;
			
		for (int i = 0 ; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(source)) {
				for (int j = 0 ; j < pages.size(); j++) {
					if (pages.get(j).getUrl().equals(destination)) {
						from = pages.get(i).getIndex();
						to = pages.get(j).getIndex();
						links[from][to] = 1;
						break;
					}
				}
			}
		}
		updatePageRanks();
		genLink();

	}
	
	/**
	 * Removes the WebPage from the graph with the given URL.
	 * @param url The URL of the page to remove from the graph.
	 */
	public void removePage(String url) {
		int ind = -1;
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(url)) {
				ind = pages.get(i).getIndex();
				pages.remove(i);
				break;
			}	
		}
		
		int[][] links1 = new int[pages.size()][pages.size()];
		int m = 0;
	
		for (int i = 0; i < links.length; i++) {
			int n  = 0;
			if (i != ind) {
				for (int j = 0; j < links[i].length; j++) {
					if (j != ind) {
						links1[m][n] = links[i][j];
						n++;
					}
				}
				m++;
			}			
		}
		
		for (WebPage pg : pages) {
			if (pg.getIndex() >= ind) {
				pg.setIndex(ind++);
			}
		}
		
		links = links1;
		updatePageRanks();
		genLink();
	}
	
	/**
	 * Removes the link from WebPage with the URL indicated by source to the WebPage with the URL indicated by destination.
	 * @param source The URL of the WebPage to remove the link.
	 * @param destination The URL of the link to be removed.
	 */
	public void removeLink(String source, String destination) {
		int from = -1;
		int to = -1;
		
		for (int i = 0 ; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(source)) {
				for (int j = 0 ; j < pages.size(); j++) {
					if (pages.get(j).getUrl().equals(destination)) {
						from = pages.get(i).getIndex();
						to = pages.get(j).getIndex();
						links[from][to] = 0;
						break;
					}
				}
			}
		}
		updatePageRanks();
		genLink();
	}
	
	/**
	 * Calculates and assigns the PageRank for every page in the WebGraph 
	 */
	public void updatePageRanks() {
		int[] col = new int[links.length];
		for (int i = 0; i < links.length; i++) {
		    for (int j = 0; j < links[i].length; j++) {
		    	col[j] += links[i][j];
		    }
		}
		
		int count = 0;
		for (WebPage p : pages) {
			p.setRank(col[count]);
			count++;
		}
	}
	
	/**
	 * Generates an array with the indices of WebPages the url is connected to
	 */
	public void genLink() {
		ArrayList num = new ArrayList();
		for (int i = 0; i < links.length; i++) {
			ArrayList arr = new ArrayList();
			for (int j = 0; j < links[i].length; j++) {
				if (links[i][j] == 1) {
					arr.add(j);
				}
			}
			num.add(arr);			
		}
		
		int c = 0;
		for (WebPage pg : pages) {
			pg.setLink((ArrayList) num.get(c));
			c++;
		}
	}
	
	/**
	 * Prints the WebGraph in tabular form 
	 */
	public void printTable() {
		System.out.printf("%-10s%-20s%-15s%-20s%-30s", "Index", "URL", "PageRank", "Links", "Keywords");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------");
				
		int count = 0;
		for (WebPage pg : pages) {
			String str = pg.getLink().toString();
			str = str.substring(1, str.length()-1);
			String key = pg.getKeywords().toString();
			key = key.substring(1,  key.length() - 1);
			System.out.printf("%-10s%-23s%-10s%-22s%-30s", pg.getIndex(), pg.getUrl(), pg.getRank(), str, key);
			System.out.println();
			count++;
		}
	}
	
}
