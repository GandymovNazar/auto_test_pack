package com.skywind.qa.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;

public class BaseTest {

    public BaseTest() {
        init();
    }

    private void init(){
        RestAssured.baseURI = "http://api.cd2.d.skywind-tech.com:4001";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json")
                .log(LogDetail.ALL)
                .build();
    }
}