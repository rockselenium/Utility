package rest.webapi.laptopbag.get;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class PerformGetRequests {
	
	/*
	 * When I perform GET Request to ping method
	 * Then status code should be 200
	 * And Message should be Hi! Name
	 */
	@Test
	public void testPingMethod() throws URISyntaxException {
		URI pingURI=new URI("http://localhost:8085/laptop-bag/webapi/api/ping/Murodil");
		Response response=when().get(pingURI);
		
		assertEquals(200, response.statusCode());
		assertEquals("Hi! Murodil", response.body().asString());
	}
	/*
	 * When I perform GET Request to ping method without an argument
	 * Then status code should be 404
	 */
	@Test
	public void testPingWithNoArg() throws URISyntaxException{
		URI pingURI=new URI("http://localhost:8085/laptop-bag/webapi/api/ping");
		System.out.println(pingURI.getPort());
		System.out.println(pingURI.getHost());
		System.out.println(pingURI.getPath());
		
		Response response=when().get(pingURI);
		int statusCode=response.getStatusCode();
		System.out.println(statusCode);		
		assertEquals(404, statusCode);
	}
	
	
	/*Scenario: All method
	 * Given Accept content is Json
	 * When I perform GET request to all api method
	 * Then status code should be 200
	 * And Body should contain Laptop details in JSON
	 */
	@Test //GIVEN, WHEN, THEN, AND, ACCEPT, GET, ASSERTTHAT
	public void testALLMethod() throws URISyntaxException{
		URI allURI=new URI("http://localhost:8085/laptop-bag/webapi/api/all");
		
		Response response=given().accept(ContentType.JSON)
						  .when().get(allURI);
		assertEquals(200, response.getStatusCode());
		System.out.println(response.body().asString());
		//2nd way with built in assertion and body return
		given().accept(ContentType.JSON)
		.when().get(allURI)
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON);
	}
	
	/*Scenario: Get laptop infromation by using find method and id
	 * Given Accept type is JSON
	 * When I perform GET request to find method with id 150
	 * Then Status code should be 200
	 * And ID should be 150 
	 * And BrandName  is Dell
	 */
	@Test
	public void testFindWithID() throws URISyntaxException{
		URI findURI=new URI("http://localhost:8085/laptop-bag/webapi/api/find/150");
		
		given().accept(ContentType.JSON)
		.when().get(findURI)
		.then().assertThat().statusCode(200)
		.and().assertThat().body("Id", equalTo(150))
		.and().assertThat().body("BrandName",equalToIgnoringCase("Dell"));
		
		given().accept(ContentType.JSON)
		.when().get(findURI)
		.then().assertThat().statusCode(200)
		.and().assertThat().body("Id", equalTo(150),"BrandName",equalToIgnoringCase("Dell"));
	
	}
	/*
	 * Scenario: Features list content
	 * Given  accept type is JSON
	 * When I perform GET request with id 150
	 * Then status code should be 200
	 * And Features should be 3 items
	 * And Features should contain following:
	 * 				"16GB RAM",
            			"500GB SSD",
            			"i7 CPU"
	 */
	@Test //VERIFY INNER JSON OBJECT, HOW TO VALIDATE A LIST
	public void testFeaturesList() throws URISyntaxException{
		URI findURI=new URI("http://localhost:8085/laptop-bag/webapi/api/find/150");
		
		given().accept(ContentType.JSON)
		.when().get(findURI)
		.then().assertThat().statusCode(200)
		.and().assertThat().body("Features.Feature", hasSize(3))
		.and().assertThat().body("Features.Feature", hasItems("16GB RAM","500GB SSD","i7 CPU"));
	}
	
	@Test
	public void testUsingJsonPath() throws URISyntaxException{
		URI findURI=new URI("http://localhost:8085/laptop-bag/webapi/api/find/150");
		
		String body=given().accept(ContentType.JSON)
					.when().get(findURI)
					.thenReturn().body().asString();
		
		JsonPath json=new JsonPath(body);
		
		System.out.println(json.getString("BrandName"));
		assertEquals("Dell", json.getString("BrandName"));
		
		System.out.println(json.getInt("Id"));
		assertEquals(150, json.getInt("Id"));

		System.out.println(json.getString("LaptopName"));
		assertEquals("Latitude", json.getString("LaptopName"));

		List<String> features=json.getList("Features.Feature");
		List<String> expFeatures=Arrays.asList("500GB SSD","16GB RAM", "i7 CPU");
		
		//I dont care about ORDER
		assertTrue(features.containsAll(expFeatures));
		//I care about ORDER
		assertEquals(expFeatures, features);
		System.out.println(features.toString());
	
	}
	
}
