package rest.webapi.laptopbag.post;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.webapi.laptopbag.helpers.RestHelper;
import rest.webapi.laptopbag.helpers.RestHelper.HeaderType;
import rest.webapi.laptopbag.helpers.RestHelper.Methods;
import rest.webapi.laptopbag.pojos.Features;
import rest.webapi.laptopbag.pojos.Laptop;

public class PostWithPojos {
	
	/*
	 * Given Headers are type Json
	 * And Laptop object 
	 * when i perform post action with pojo
	 * Then status code is 200
	 * 
	 * {
    "BrandName": "Apple",
    "Features": {
        "Feature": [
            "16GB RAM",
            "500GB SSD",
            "i7 CPU"
        ]
    },
    "Id": 111,
    "LaptopName": "Macbook Pro"
}
	 */ 
	
	
	@Test // SERIALIZATION
	public void PostActionUsingPOJO() throws URISyntaxException{
		URI postURI=new URI(RestHelper.buildUri(Methods.ADD));
		
		Laptop laptop=new Laptop();
		laptop.setBrandName("ASUS");
		laptop.setId(333);
		laptop.setLaptopName("ZenBook");
		
		List<String> laptopFeatures=Arrays.asList("32GB RAM","2GB VRAM","255GB SSD");
		Features features=new Features();
		features.setFeature(laptopFeatures);
		
		laptop.setFeatures(features);
		
		given().headers(RestHelper.getHeaders(HeaderType.JSON))
		.and().body(laptop)
		.when().post(postURI)
		.then().assertThat().statusCode(200);
	}
	
	@Test // DE-SERIALIZATION
	public void PostActionUsingPOJO2() throws URISyntaxException{
		URI postURI=new URI(RestHelper.buildUri(Methods.ADD));
		
		Laptop laptop=new Laptop();
		laptop.setBrandName("HP");
		laptop.setId(444);
		laptop.setLaptopName("Envy");
		
		List<String> laptopFeatures=Arrays.asList("32GB RAM","2GB VRAM","255GB SSD");
		Features features=new Features();
		features.setFeature(laptopFeatures);
		
		laptop.setFeatures(features);
		
		Laptop returnLaptop=given().headers(RestHelper.getHeaders(HeaderType.JSON))
							.and().body(laptop).log().all()
							.when().post(postURI)
							.thenReturn().body().as(Laptop.class);
//		Gson gson = new Gson();
//		String jsonStr=gson.toJson(returnLaptop);
//		
//		System.out.println(jsonStr);
//		
		System.out.println(returnLaptop.getBrandName());
		assertTrue(returnLaptop.getId()==444);
		assertEquals("Envy",returnLaptop.getLaptopName());
		
		List<String> featuress=returnLaptop.getFeatures().getFeature();
		System.out.println(featuress);
		//......
	}
	
}
