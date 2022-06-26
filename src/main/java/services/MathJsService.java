package services;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@AllArgsConstructor
@NoArgsConstructor
public class MathJsService {
    private Integer precise;

    @Step
    public String add(StringBuilder expression) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("expr", expression.toString());
        if (precise != null) {
            queryParams.put("precision", precise);
        }
        return given()
                .queryParams(queryParams)
                .get("http://api.mathjs.org/v4/")
                .then()
                .log().all()
                .extract()
                .body()
                .asString();
    }
}
