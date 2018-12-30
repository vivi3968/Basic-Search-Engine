import java.util.ArrayList;
import java.util.Collection;

/**
 * @author vivi3
 * Represents a hyperlinked document.
 */
public class WebPage {
	
	private String url;
	private int index;
	private int rank;
	private ArrayList<String> keywords;
	private ArrayList link =  new ArrayList();
	
	/**
	 * Constructor for webpage
	 */
	public WebPage() {
		
	}
	
	/**
	 * 
	 * Constructor with parameters for webpage
	 * @param url
	 * @param index position in the adjacency matrix
	 * @param rank
	 * @param keywords describing this page.
	 */
	public WebPage(String url, int index, int rank, ArrayList<String> keywords) {
		this.url = url;
		this.index = index;
		this.rank = rank;
		this.keywords = keywords;
		this.link = link;
	}

	/**
	 * @return an array with links the url is connected to
	 */
	public ArrayList getLink() {
		return link;
	}

	/**
	 * @param link of webpage
	 */
	public void setLink(ArrayList link) {
		this.link = link;
	}

	/**
	 * @return url of the webpage
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url of webpage
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return position in the adjacency matrix
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index of webpage
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return rank of webpage
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank of webpage
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return an array of keywords describing the webpage
	 */
	public ArrayList<String> getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords of webpage
	 */
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}

	/** 
	 * returns string representation of webpage
	 */
	public String toString() {
		return this.getUrl() + " " + this.getIndex() + " " + this.getRank() + " " + this.getKeywords();
	}
	
}
