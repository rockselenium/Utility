package rest.webapi.laptopbag.get;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import rest.webapi.laptopbag.helpers.RestHelper;
import rest.webapi.laptopbag.helpers.RestHelper.Methods;

public class ReqResApi {

	// @Test()
	public void GetValuesFromResource() throws URISyntaxException {
		URI uri = new URI("https://reqres.in/api/users?page=3");

		String body = given().accept(ContentType.JSON)
				.config(RestAssuredConfig.config().sslConfig(new SSLConfig().allowAllHostnames())).when().get(uri)
				.thenReturn().body().asString();
		System.out.println(body);
	}
	
	@Test
	public void JsonPathParactice() throws URISyntaxException{
		URI ping=new URI(RestHelper.buildUri(Methods.PING));
		
		System.out.println(when().get(ping).asString());
		/*
		 * Given accept type is json
		 * When i perform GET request using ALL method
		 * Then I can see all IDs
		 */
		String json=given().accept(ContentType.JSON)
		.when().get(RestHelper.buildUri(Methods.ALL))
		.andReturn().body().asString();
		
		//System.out.println(json);
		//parsing Json string into JsonPath object
		//So that I can easily navigate through objects in Json
		JsonPath jsonBody=new JsonPath(json);
		//Get all IDs
		List<Integer> ids=jsonBody.getList("Id");
		System.out.println(ids);
		
		//Get all BrandNames into a list
		List<String> brands=jsonBody.getList("BrandName");
		System.out.println(brands);
		
		//Get all Features into a list
		List<String> features=jsonBody.getList("Features.Feature");
		System.out.println(features);
		
		//Get all LaptopNames for laptops whose ID is less than 150
		List<String> laptopNames=jsonBody.getList("findAll{it.Id<150}.LaptopName");
		System.out.println(laptopNames);
		//Get all Ids for laptops whose brandname is Apple
		List<Integer> Ids = jsonBody.getList("findAll{it.BrandName=='Apple'}.Id");
		System.out.println(Ids);		
	}
	
	@Test
	public void XmlPathSamples() throws URISyntaxException{
		/*
		 * Given Accept type is xml
		 * When i perform GET request to get all laptops
		 * Then status is 200 
		 * And I can navigate through XML using XmlPath
		 */
		URI uri=new URI(RestHelper.buildUri(Methods.ALL));  //new URI("http://localhost:8085/laptop-bag/webapi/api/all");
		Response response=given().accept(ContentType.XML)
		.when().get(uri);
		
		assertEquals(200,response.statusCode());
		XmlPath xml=new XmlPath(response.body().asString());
		
		List<String> brands=xml.getList("laptopDetailss.Laptop.BrandName");
		System.out.println(brands);
		
	}
	
}
