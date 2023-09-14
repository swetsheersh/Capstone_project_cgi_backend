package com.jwt.springjwt;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import com.github.cliftonlabs.json_simple.JsonObject;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

//Automated all possible test cases using RestAssured and TestNg
@SpringBootTest
public class UserTest {
	private String token;
  //Method to register user
  @Test(priority = 0)
  public void register() {
		JsonObject req=new JsonObject();
		req.put("name","Jhon");
		req.put("username","venky");
		req.put("password","jhon123");
		req.put("role","user");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/save")
		.then().statusCode(201).body(equalTo("UserId Created Successfully!!"))
		.log().all();
	}
	
  //Method to show error while registering with same details
	@Test(priority = 1)
	public void registerAgainWithSameid() {
		JsonObject req=new JsonObject();
		req.put("name","Jhon");
		req.put("username","venky");
		req.put("password","jhon123");
		req.put("role","user");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/save")
		.then().statusCode(409).body(equalTo("UserId Allready Exixts!!"))
		.log().all();
	}
	
  //Method for user login
	@Test(priority = 2)
	public void login() {
		JsonObject req=new JsonObject();
		req.put("username","venky");
		req.put("password","jhon123");
		baseURI="http://localhost:8080";
		Response response=given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/token")
		.then().statusCode(200)
		.extract().response();
		
		String responseBody = response.getBody().asString();
	    JsonPath jsonPath = new JsonPath(responseBody);
	    token = jsonPath.getString("token");
	    token="Bearer "+token;
	    //token.substring(1, token.length() - 1);
	    //System.out.println("Token: " + token);
	    response.then().log().all();
	}
	
	//Method for invalid login user
	@Test(priority = 3)
	public void InvalidLogin() {
		JsonObject req=new JsonObject();
		req.put("username","vcenky");
		req.put("password","jhon123");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/token")
		.then().statusCode(401)
		.log().all();
	}
	
	//Method for adding products to cart
	@Test(priority = 4)
	 public void addToCart() {
		 baseURI="http://localhost:8080";
		 Response response =
		 given()
		 .header("Authorization",this.token)
		 .when()
		 .get("/dashboard/user/addtocart/{id}",11);
		 response.then()
		 .statusCode(200)
		 .contentType(ContentType.TEXT);
		 response.body().prettyPrint();
		 // You can add more assertions here as needed
		 }
	
	 //Method to get products by id
	 @Test(priority=5) 
	 public void getProductById() {
		 baseURI="http://localhost:8080";
		 Response response = given()
		 .header("Authorization",this.token)
		 .when()
		 .get("/dashboard/user/id/{id}",11);
		 response.then()
		 .statusCode(200)
		 .contentType(ContentType.JSON);
		 response.body().prettyPrint();
		 }
	 
	 //Method to show error while trying to get product by invalid id
	 @Test(priority=6) 
	 public void invalidGetProductById() {
		 baseURI="http://localhost:8080"; 
		 given()
		 .header("Authorization",this.token)
		 .contentType(ContentType.JSON)
		 .when()
		 .get("/dashboard/user/id/11")
		 .then()
		 .statusCode(200)
		 .log().all(); 
		 }
	 
	 //Method to get product by category
	 @Test(priority=7) 
	 public void getProductsByCategory() {
		 baseURI="http://localhost:8080";
		 Response response =
		 given()
		 .header("Authorization",this.token)
		 // Set the category ID you want to test
		 .when()
		 .get("/dashboard/user/catogery/{id}","Phone");
		 response.then()
		 .body("[0].productId", equalTo(11))
		 .body("[0].productName", equalTo("Iphone 14+"))
		 .statusCode(200)
		 .contentType(ContentType.JSON);
		 response.body().prettyPrint();
		 }
	 
	//Method to get all products from cart
	@Test(priority = 8)
	 public void getAllFromCart() 
	{
		 baseURI="http://localhost:8080";
		 Response response = given()
		 .header("Authorization",this.token)
		 .when()
		 .get("/dashboard/user/getallcart");
		 response.then()
		 .statusCode(200)
		 .body("[0].productId", equalTo(11))
		 .body("[0].productName", equalTo("Iphone 14+"))
		 .contentType(ContentType.JSON);
		 response.body().prettyPrint();
	}
	
	//Method to remove products from cart
	@Test(priority = 9)
    public void removefromCart()
    {
		  baseURI="http://localhost:8080/dashboard/user/remove";
		  given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when().delete("/11")
		.then().statusCode(200).body(equalTo("Product Removed From Cart"))
		.log().all();;
    }
	
	//Method to throw error while trying to remove product which is not present in cart
	@Test(priority = 10)
    public void invalidRemovefromCart()
    {
		  baseURI="http://localhost:8080/dashboard/user/remove";
		  given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when().delete("/11")
		.then().statusCode(404).body(equalTo("Product Not Found in Cart"))
		.log().all();;
    }
  
	  //Method to get order history  
	  @Test(priority=11) 
	  public void orderHistory() 
	  {
		  baseURI="http://localhost:8080";
		  Response response = given()
		  .header("Authorization",this.token)
		  .when()
		  .get("/dashboard/user/orderhistory");
		  response.then()
		  .statusCode(200)
		  .body("[0].productId", equalTo(11))
		  .body("[0].productName", equalTo("Iphone 14+"))
		  .contentType(ContentType.JSON);
		  response.body().prettyPrint();
	  }
	 	
	 
}
