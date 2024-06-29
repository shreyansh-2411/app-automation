package main.restassured;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BaseApiClass {

    public Response get(Map<String,String> headers, String url, Map<String,String> queryParams) throws MalformedURLException {
        return RestAssured.given().headers(headers)
                .log().all()
                .queryParams(queryParams)
                .get(new URL(url))
                .then()
                .extract().response();
    }



    public Response get(Map<String,String> headers, String url) throws MalformedURLException {
        return get(headers,url,Map.of());
    }

    public Response post(Map<String,String> headers, String url, Object reqBody) throws MalformedURLException {
        Filter requestLoggingFilter = new RequestLoggingFilter();
        return RestAssured.given().headers(headers)
                .filter(requestLoggingFilter)
                .body(reqBody)
                .post(new URL(url))
                .then()
                .extract().response();
    }








}
