import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
	
public static void main(String[] args) {
	
	// Validate if Add Place API is working as expected
	
		// given - all input details
		// when - submit the API - resource, http method
		// Then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		JsonPath js= new JsonPath(response); //for parsing Json
		String placeId= js.getString("place_id");
		System.out.println("place_id: "+placeId);
		
		//Update Place
		
		String newAddress= "78 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body("{\n" + 
				"\"place_id\":\""+ placeId +"\",\n" + 
				"\"address\":\""+newAddress+"\",\n" + 
				"\"key\":\"qaclick123\"\n" + 
				"}\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get Place
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1= ReUsableMethods.rawToJson(getPlaceResponse); //buenas practicas extraido en una clase para reusar metodos
		String actualAddress = js1.get("address");
		System.out.println(actualAddress);
		Assert.assertEquals(newAddress, actualAddress);
		
		
	}

}
