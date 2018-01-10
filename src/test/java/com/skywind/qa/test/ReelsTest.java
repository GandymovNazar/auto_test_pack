package com.skywind.qa.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class ReelsTest extends BaseTest {

    private static String url = "/casino/game2";
    private String gameId = System.getProperty("gameId");


    String reelsPath = "src/test/resources/reel_" + gameId + ".csv";


    @DataProvider
    public Iterator<Object[]> reels() throws IOException {
        List<Object[]> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(reelsPath)));
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[]{split[0], split[1]});
        }
        return list.iterator();
    }

    @Test (dataProvider = "reels")
    public void testReels(String combination, String result) throws IOException {
        System.out.println(combination + "" + result);
        String payload = "{\n" +
                "  \"deviceId\": \"web\",\n" +
                "  \"gameId\": \""+gameId+"\",\n" +
                "  \"request\": \"init\",\n" +
                "  \"startGameToken\": {  \n" +
                "    \"brandId\": 1,  \n" +
                "    \"currency\": \"USD\"\n" +
                "  }\n" +
                "}";
        given()
                .body(payload)
                .post(url).then()
                .log().all()
                .statusCode(200).body("result.totalWin", equalTo(Integer.valueOf(result)));
    }



}