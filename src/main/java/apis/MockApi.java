package apis;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.util.List;


public class MockApi {
    private final String baseApiUrl = "https://jsonplaceholder.typicode.com";
    private Response response;
    private final int userId;

    /**
     *
     * @param userId starts from 1 to 10
     */
    public MockApi(int userId) {
        this.userId = userId;
    }

    public void getRandomUserEmailAddress() {
        response = RestAssured.get(baseApiUrl + "/users/" + userId);
        JsonPath jsonPath = response.jsonPath();
        System.out.println("User email address is: "+jsonPath.get("email"));
    }

    public List<Integer> getAssociatedPostsForRandomUser() {
        response = RestAssured.get(baseApiUrl + "/users/" + userId + "/posts");
        JsonPath jsonPath = response.jsonPath();
        System.out.println(response.asPrettyString());
        return jsonPath.getList("id");
    }

    public boolean checkPostsIds(List<Integer> ids) {
        boolean check = true;
        for (int id : ids) {
            if (id < 1 || id > 100) {
                check = false;
                break;
            }
        }
        return check;
    }

    public int postRequest() {
        RestAssured.baseURI = baseApiUrl;
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "Hi");
        requestParams.put("body", "This is my post");
        requestParams.put("userId", userId);
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("/posts");
        System.out.println(response.statusLine());
        return response.getStatusCode();
    }

}
