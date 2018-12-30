import java.util.Comparator;

/**
 * @author vivi3
 * Sort numerically DESCENDING based on the PageRank of the WebPage
 */
public class RankComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPage p1 = (WebPage) o1;
		WebPage p2 = (WebPage) o2;
		
		if (p1.getRank() == p2.getRank())
			return 0;
		else if (p1.getRank() > p2.getRank())
			return -1;
		else 
			return 1;
	}
}
