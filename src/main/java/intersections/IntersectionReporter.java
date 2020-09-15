package intersections;


//Local imports and Java language imports: 
import java.util.ArrayList;


public class IntersectionReporter {

	private ArrayList<Rectangle> initialRectangles; 
	private ArrayList<Rectangle> allIntersections;
	private int lastIntersection; 
	
	public IntersectionReporter(ArrayList<Rectangle> initialRectangles) {
		this.initialRectangles = initialRectangles; 
		this.allIntersections = new ArrayList<Rectangle>();
		this.lastIntersection = 0; 
	}
	
	
	public void reportIntersections() {
		
		//Generate initial rectangles: 
		generateIntersections(initialRectangles, 0);
		boolean noIntersection = noIntersections(0); 
		
		//Generate further rectangles until all intersection rectangles are found: 
		reportIntersections(noIntersection, this.lastIntersection - 1);
		
		//Print out the inputs and intersections:
		printInputs(); 
		printIntersections();
	}
	
	
	private void printInputs() {
		System.out.println("Inputs: ");
		for (Rectangle rectangle : initialRectangles) {
			System.out.println(rectangle.getId() + ": Rectangle at " + 
							 "(" + rectangle.getX() + "," + rectangle.getY() + "),"
							  + " delta_x=" + rectangle.getDeltaX() + ", "
							  + " delta_y=" + rectangle.getDeltaY());
		}
	}
	
	private void printIntersections() {
		System.out.println("Intersections: ");
		for (int i = 0; i < allIntersections.size(); i++) {
			Rectangle rectangle = allIntersections.get(i); 
			StringBuilder outputBuilder = new StringBuilder();
			outputBuilder.append("" + (i + 1) + ": Between rectangle");
			ArrayList<Integer> contributors = rectangle.getContributors(); 
			

			for (Integer contributor : contributors) {
				if (contributors.indexOf(contributor) == contributors.size() - 1)
					outputBuilder.append(" and " + contributor); 
				
				else 
					outputBuilder.append(" " + contributor + ","); 
					
			}
			
			outputBuilder.append(" at (" + rectangle.getX() + "," + rectangle.getY() + ")"
			        +  "), " + "delta_x= " + rectangle.getDeltaX()
			        +  ",  delta_y= " + rectangle.getDeltaY() + ".");

			String output = outputBuilder.toString(); 
			System.out.println(output);
		}
	}
	
	
	private void reportIntersections(boolean noIntersections, int lastIntersection) {
		if (noIntersections)
			return; 
		
		if (!noIntersections) {
			int oldLast = lastIntersection; 
			generateIntersections(allIntersections, oldLast);
			noIntersections = noIntersections(oldLast); 
			reportIntersections(noIntersections, this.lastIntersection - 1);
		}
	}
	
	
	private Rectangle findIntersection(Rectangle rectangle1, Rectangle rectangle2) {
		//Input rectangle coordinates: (Rename variables)  
		int x1 = rectangle1.getX(); 
		int y1 = rectangle1.getY(); 
		int x2 = rectangle1.getX() + rectangle1.getDeltaX(); 
		int y2 = rectangle1.getY() + rectangle1.getDeltaY(); 
		int x3 = rectangle2.getX(); 
		int y3 = rectangle2.getY(); 
		int x4 = rectangle2.getDeltaX() + rectangle2.getX();
		int y4 = rectangle2.getDeltaY() + rectangle2.getY(); 
		
		//Intersecting rectangle coordinates: (Rename Variables) 
		int x5 = Math.max(x1, x3);
		int y5 = Math.max(y1, y3); 
		int x6 = Math.min(x2, x4); 
		int y6 = Math.min(y2, y4); 
		int deltaX = x6 - x5; 
		int deltaY = y6 - y5; 
		
		
		//Prevent creation of "0 area" or negative area intersection triangles: 
		if (deltaX <= 0 || deltaY <= 0) throw new IllegalArgumentException("Negative overlap not allowed");  
			
		//Add intersection rectangle to list: 
		Rectangle newRectangle = new Rectangle(deltaX, deltaY, x5, y5); 
		if (rectangle1.getContributors() == null && rectangle2.getContributors() == null) {
			newRectangle.addInitialContributors(rectangle1.getId(), rectangle2.getId());
		} 
			
		return newRectangle; 
	}
	
	
	
	private boolean noIntersections(int lastIntersection) {
		int endingPoint = allIntersections.size();
		for (int i = lastIntersection; i < endingPoint; i++) {
			Rectangle rectangle1 = allIntersections.get(i); 
			for (int j = i + 1; j < endingPoint; j++) {
				Rectangle rectangle2 = allIntersections.get(j);
				if (overlapPresent(rectangle1, rectangle2)) 
					return false; 
			}
		}
		return true; 
	}
	
	
	private void generateIntersections(ArrayList<Rectangle> rectangleList, int lastIntersection) {
		int endPoint = rectangleList.size(); 
		
		for (int i = lastIntersection; i < endPoint; i++) {
			Rectangle rectangle1 =  rectangleList.get(i);
			int startPoint = rectangle1.getId();

			for (int j = startPoint; j < endPoint; j++) {
				Rectangle rectangle2 = rectangleList.get(j);
				if (overlapPresent(rectangle1, rectangle2)) {
					Rectangle newRectangle = findIntersection(rectangle1, rectangle2); 
					if (isUnique(newRectangle)) {
						newRectangle.addUniqueContributors(rectangle1.getContributors());
						newRectangle.addUniqueContributors(rectangle2.getContributors());
						allIntersections.add(newRectangle);
						this.lastIntersection++;
					}
						
				}
			}
		}
	}
	
	
	
	
	
	private boolean isUnique(Rectangle newRectangle) {
		for (Rectangle rectangle : allIntersections) {
			if (!rectangle.isIntersectionUnique(newRectangle))
				return false; 
		}
		return true; 
	}
	
	
	private boolean overlapPresent(Rectangle rectangle1, Rectangle rectangle2) {
		//Rectangle 1 coordinates: 
		int R1LeftX = rectangle1.getX();
		int R1RightX = R1LeftX + rectangle1.getDeltaX(); 
		int R1BottomY = rectangle1.getY(); 
		int R1TopY = R1BottomY + rectangle1.getDeltaY();  
	
		//Rectangle 2 coordinates: 
		int R2LeftX = rectangle2.getX();
		int R2RightX = R2LeftX + rectangle2.getDeltaX(); 
		int R2BottomY = rectangle2.getY(); 
		int R2TopY = R2BottomY + rectangle2.getDeltaY();  
	
	
		//Conduct comparisons: 
		if (R1LeftX > R2RightX || R1RightX < R2LeftX || R1TopY < R2BottomY || R1BottomY > R2TopY) {
			return false; 
		} else {
			return true; 
		}
	}
}
