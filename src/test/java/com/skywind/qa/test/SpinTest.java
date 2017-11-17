package com.skywind.qa.test;

import com.skywind.qa.core.dto.SpinDTO;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
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
import static org.testng.Assert.assertEquals;

public class SpinTest extends BaseTest {

    private String gameSession = "";
    private int requestId = 0;
    private static String url = "/casino/game2";
    private String gameId = System.getProperty("gameId");

    String spinPath = "src/test/resources/spin_" + gameId + ".csv";

    @BeforeClass
    public void setUp() {
        String payload = "{\n" +
                "  \"deviceId\": \"web\",\n" +
                "  \"gameId\": \""+ gameId +"\",\n" +
                "  \"request\": \"init\",\n" +
                "  \"startGameToken\": {  \n" +
                "    \"brandId\": 1,  \n" +
                "    \"currency\": \"USD\"\n" +
                "  }\n" +
                "}";
        Response response = given()
                .body(payload)
                .log().all()
                .post(url);
        assertEquals(response.getStatusCode(), 200);

        gameSession = response.jsonPath().getString("gameSession");
    }

    @DataProvider
    public Iterator<Object[]> combinations() throws IOException{
        List<Object[]> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(spinPath)));
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Object[] {new SpinDTO(split[0], split[1], Integer.valueOf(split[2]))});
            line = bufferedReader.readLine();
        }
        return list.iterator();
    }

    @Test(dataProvider = "combinations")
    public void testSpin(SpinDTO spinDTO) throws Exception {
        requestId++;
        System.out.println(spinDTO);
        System.out.println(gameSession);
        String payload = "{\n" +
                "  \"bet\": 1,\n" +
                "  \"coin\": 1,\n" +
                "  \"gameSession\": \""+gameSession+"\",\n" +
                "  \"lines\": 1,\n" +
                "  \"request\": \"spin\",\n" +
                "  \"positions\": ["+spinDTO.getCombinations()+"],\n" +
                "  \"requestId\": "+requestId+"\n" +
                "}";

        given()
                .body(payload)
                .post(url).then()
                .log().all()
                .statusCode(200).body("result.totalWin", equalTo(spinDTO.getTotalWin()));
    }
}