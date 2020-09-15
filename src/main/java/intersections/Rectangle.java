package intersections;

import java.util.ArrayList;
import java.util.Collections;

public class Rectangle {

	private int deltaX; 
	private int deltaY; 
	private int x; 
	private int y; 
	private final int id;
	private ArrayList<Integer> contributors; 
	
	
	
	public Rectangle(int deltaX, int deltaY, int x, int y, int id) {
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public Rectangle(int deltaX, int deltaY, int x, int y) {
		this(deltaX, deltaY, x, y, 0);
		this.contributors = new ArrayList<Integer>(); 
	}


	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Integer> getContributors() {
		return contributors; 
	}
	
	public boolean isIntersectionUnique(Rectangle rectangle2) {
		if (this.x == rectangle2.getX() && 
			this.y == rectangle2.getY()	&&
			this.deltaX == rectangle2.getDeltaX() && 
			this.deltaY == rectangle2.getDeltaY() && 
			!areContributorsUnique(rectangle2))
			
			return false; 
		
		else 
			return true; 
	}
	
	public void addUniqueContributors(ArrayList<Integer> contributorList) {
		if (contributorList != null) {
			for (Integer contributor : contributorList) {
				if (contributors.size() == 0)
					contributors.add(contributor);
				
				else if (isContributorUnique(contributor))
					contributors.add(contributor); 
			}
		}
		Collections.sort(contributors);
	}
	
	public void addInitialContributors(Integer id1, Integer id2) {
		contributors.add(id1); 
		contributors.add(id2);
	}
	
	
	private boolean areContributorsUnique(Rectangle rectangle) {
		//Scan across contributors of input rectangle: 
		for (Integer contributor : rectangle.getContributors()) {
			boolean isUnique = isContributorUnique(contributor);
			if (isUnique)
				return true; 
		}
		
		return false; 
	}
	
	private boolean isContributorUnique(Integer contributor) {
		Collections.sort(contributors); 
		int searchItem = Collections.binarySearch(contributors, contributor); 
		if (searchItem < 0) 
			return true; 
		else
			return false; 
	}
	
	
}
