import java.util.Comparator;

/* Vivian Lam
 * ID: 111549991
 * vivian.lam@stonybrook.edu
 * Homework 7
 * CSE214, R11 (Reed Gantz)
 */ 

/**
 * @author vivi3
 * Sort numerically ASCENDING based on index of the WebPage
 */
public class IndexComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPage p1 = (WebPage) o1;
		WebPage p2 = (WebPage) o2;
		if (p1.getIndex() == p2.getIndex())
			return 0;
		else if (p1.getIndex() > p2.getIndex())
			return 1;
		else 
			return -1;
	}
}
