import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String[] args) {
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//Print N° of courses returned by API
		int count= js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		int amount= js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);
		
		//Print title of the first course
		String first = js.getString("courses[0].title");
		System.out.println(first);
		
		//Print all course title and their respective prices
		for (int i = 0; i < count; i++) {
			String title = js.getString("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			System.out.println("Course: " + title + " // Price: $" + price);
		}
		
	}
}
