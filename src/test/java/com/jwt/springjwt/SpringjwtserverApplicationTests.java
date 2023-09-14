package com.jwt.springjwt;


import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


import com.github.cliftonlabs.json_simple.JsonObject;



import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

//Automated Test case using RestAssured || TestNg Class
@SpringBootTest
class SpringjwtserverApplicationTests {
	
	//token
	private String token;
	
	//verify Register user
	@Test(priority = 0)
	public void register() {
		JsonObject req=new JsonObject();
		req.put("name","Jhon");
		req.put("username","jhon");
		req.put("password","jhon123");
		req.put("role","Admin");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/save")
		.then().statusCode(201).body(equalTo("UserId Created Successfully!!"))
		.log().all();

	}
	//verify Register with same username
	@Test(priority = 1)
	public void registerAgainWithSameid() {
		JsonObject req=new JsonObject();
		req.put("name","Jhon");
		req.put("username","jhon");
		req.put("password","jhon123");
		req.put("role","Admin");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/save")
		.then().statusCode(409).body(equalTo("UserId Allready Exixts!!"))
		.log().all();

	}
	
	//verify login 
	@Test(priority = 2)
	public void login() {
		JsonObject req=new JsonObject();
		req.put("username","jhon");
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
	//verify invalid login
	@Test(priority = 3)
	public void InvalidLogin() {
		JsonObject req=new JsonObject();
		req.put("username","jhon123");
		req.put("password","jhon123");
		baseURI="http://localhost:8080";
		given().header("Content-Type","application/json")
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/token")
		.then().statusCode(401)
		.log().all();
	}
	//verify add product
	@Test(priority = 4)
	public void addProduct() {
		JsonObject req=new JsonObject();
		req.put("productId","10");
		req.put("productName","Iphone 14+");
		req.put("supplier","Apple");
		req.put("category","Phone");
		req.put("price",95000.00f);
		baseURI="http://localhost:8080/dashboard/admin";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/addProduct")
		.then().statusCode(201).body("productName",equalTo("Iphone 14+"))
		.log().all();

	}
	
	//verify adding same product again
	@Test(priority = 5)
	public void addingSameProductAgain() {
		JsonObject req=new JsonObject();
		req.put("productId","10");
		req.put("productName","Iphone 14+");
		req.put("supplier","Apple");
		req.put("category","Phone");
		req.put("price",95000.00f);
		baseURI="http://localhost:8080/dashboard/admin";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/addProduct")
		.then().statusCode(409).body(equalTo("Product Allready there!!"))
		.log().all();

	}
	//verify update product
	@Test(priority = 6)
	public void updateProduct() {
		JsonObject req=new JsonObject();
		req.put("productId","10");
		req.put("productName","Iphone 13+ Max");
		req.put("supplier","Apple");
		req.put("category","Phone");
		req.put("price",85000.00f);
		baseURI="http://localhost:8080/dashboard/admin";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().put("/updateProduct")
		.then().statusCode(200).body("productName",equalTo("Iphone 13+ Max"))
		.log().all();

	}
	//verify Delete product
	@Test(priority = 7)
	public void deleteProduct() {
		baseURI="http://localhost:8080/dashboard/admin/deleteProduct";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when().delete("/10")
		.then().statusCode(200).body(equalTo("product deleted"))
		.log().all();

	}
	//verify invalid delete product
	@Test(priority = 8)
	public void InvalidDeleteProduct() {
		baseURI="http://localhost:8080/dashboard/admin/deleteProduct";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when().delete("/10")
		.then().statusCode(404).body(equalTo("product doesn't exist"))
		.log().all();

	}
	@Test(priority = 9)
	public void addProduct1() {
		JsonObject req=new JsonObject();
		req.put("productId","11");
		req.put("productName","Iphone 14+");
		req.put("supplier","Apple");
		req.put("category","Phone");
		req.put("price",95000.00f);
		baseURI="http://localhost:8080/dashboard/admin";
		given().header("Content-Type","application/json")
			.header("Authorization",this.token)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON).body(req.toJson())
		.when().post("/addProduct")
		.then().statusCode(201).body("productName",equalTo("Iphone 14+"))
		.log().all();;

	}
}
