import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count= js.getInt("courses.size()");
		int purchase= js.getInt("dashboard.purchaseAmount"); 
		int expectedPurchase=0;
		for (int i = 0; i < count; i++) {
			int price= js.getInt("courses["+i+"].price");
			int copies= js.getInt("courses["+i+"].copies");
			
			expectedPurchase= expectedPurchase + price*copies;	
		}
		Assert.assertEquals(expectedPurchase, purchase,"Courses DOESN'T match the pruchase with the copies sold");
		
	}
}
