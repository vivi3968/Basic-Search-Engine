import java.util.Comparator;

/**
 * @author vivi3
 * Sort alphabetically ASCENDING based the URL of the WebPage 
 */
public class URLComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPage p1 = (WebPage) o1;
		WebPage p2 = (WebPage) o2;
		return (p1.getUrl().compareTo(p2.getUrl()));
	}

}
