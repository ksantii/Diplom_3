package api;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import io.qameta.allure.Step;

import static api.Endpoints.*;
import static io.restassured.RestAssured.given;

public class UserStep {
    private User user;

    public UserStep(User user) {
        this.user = user;
    }

    @Step("Создать пользователя")
    public Response createUser() {
        return given().log().all()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Удалить пользователя")
    public void deleteUser(String token) {
        given().log().all()
                .baseUri(BASE_URI)
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_ACCEPTED);
    }
}