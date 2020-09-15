package intersections;

//Java Language Imports and local imports: 
import java.io.FileReader;
import java.util.ArrayList;


//External Library classes: 
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;



public class App {
    public static void main(String[] args) {
    	try {
			String jsonFilePath = args[0]; 
			FileReader jsonReader = new FileReader(jsonFilePath);
			JSONTokener tokener = new JSONTokener(jsonReader); 
			JSONObject parser = new JSONObject(tokener); 
			JSONArray rectangleCoordinates = parser.getJSONArray("rects");
			ArrayList<Rectangle> rectangles = createRectangles(rectangleCoordinates);
			IntersectionReporter reporter = new IntersectionReporter(rectangles);
			reporter.reportIntersections();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			System.out.println("Done");
		}
    }
    
    private static ArrayList<Rectangle> createRectangles(JSONArray coordinates) {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>(); 
		
		//Potential use for a try-catch block?: 
		for (int i = 0; i < coordinates.length(); i++) {
			JSONObject rectangleInfo = coordinates.getJSONObject(i); 
			int deltaX = rectangleInfo.getInt("delta_x"); 
			int deltaY = rectangleInfo.getInt("delta_y"); 
			int x = rectangleInfo.getInt("x"); 
			int y = rectangleInfo.getInt("y");
			Rectangle rectangle = new Rectangle(deltaX, deltaY, x, y, i + 1); 
			rectangles.add(rectangle); 
		}
		
		return rectangles; 
	}
}
