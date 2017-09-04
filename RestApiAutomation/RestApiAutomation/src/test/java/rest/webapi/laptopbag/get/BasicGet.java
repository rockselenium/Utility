package rest.webapi.laptopbag.get;

//IMPORT ALL STATIC METHODS OF RESTASSURED CLASS. WHEN,THEN,AND,
import static io.restassured.RestAssured.*;


public class BasicGet {
	
	static String pingUrl="http://localhost:8085/laptop-bag/webapi/api/ping/Murodil";
	
	public static void main(String[] args) {
		/*
		 * When I perform GET request to :
		 * http://localhost:8085/laptop-bag/webapi/api/ping/Murodil
		 */
		when().get(pingUrl);
		/*
		 * When I perform GET request to :
		 * http://localhost:8085/laptop-bag/webapi/api/ping/Murodil
		 * It should return Hi! Murodil
		 */
		String greeting=when().get(pingUrl).asString();
		
		System.out.println(greeting);
		
		int statusCode = when().get(pingUrl)
						 .thenReturn().statusCode();
		
		System.out.println("Status code:" + statusCode);
		
	}
}
