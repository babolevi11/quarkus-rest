package example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookResourceTest {

    @Test
    @Order(1)
    public void testGet() {
        given()
            .when().get("/books")
            .then()
            .statusCode(200)
            .body(
                containsString("\"title\":\"The Art of Programming\","),
                containsString("\"title\":\"The Power of Words\","),
                containsString("\"title\":\"A Journey Through Time\","),
                containsString("\"title\":\"The Hidden Secrets\","),
                containsString("\"title\":\"The Science of Nature\",")
            );
    }

    @Test
    @Order(2)
    public void testPost() {
        given()
            .header("Content-Type", "application/json")
            .body("{" +
                    "\"title\": \"The Art of Programming2\"," +
                    "\"author\": \"John Smith\"," +
                    "\"pubDate\": \"2025-12-15\"," +
                    "\"price\": 69.99," +
                    "\"genre\": \"Computer Science\"," +
                    "\"isbn\": \"9780123456790\"," +
                    "\"quantity\": 5" +
                    "}"
            )
            .when().post("/books")
            .then()
            .statusCode(201)
            .body(containsString("\"title\":\"The Art of Programming2\","));

        given()
            .when().get("/books")
            .then()
            .statusCode(200)
            .body(
                containsString("\"title\":\"The Art of Programming\","),
                containsString("\"title\":\"The Power of Words\","),
                containsString("\"title\":\"A Journey Through Time\","),
                containsString("\"title\":\"The Hidden Secrets\","),
                containsString("\"title\":\"The Science of Nature\","),
                containsString("\"title\":\"The Art of Programming2\",")
            );

        given()
            .header("Content-Type", "application/json")
            .body("{" +
                    "\"title\": \"The Art of Programming2\"," +
                    "\"author\": \"John Smith\"," +
                    "\"pubDate\": \"2025-12-15\"," +
                    "\"price\": 79.99," +
                    "\"genre\": \"Computer Science\"," +
                    "\"isbn\": \"9780123456790\"," +
                    "\"quantity\": 10" +
                    "}"
            )
            .when().post("/books")
            .then()
            .statusCode(409)
            .body(is("Duplicate Record!"));
    }

    @Test
    @Order(3)
    public void testPut() {
        given()
            .header("Content-Type", "application/json")
            .body("{" +
                    "\"id\": 1," +
                    "\"title\": \"The Art of Programming 3.3\"," +
                    "\"author\": \"John Smith\"," +
                    "\"pubDate\": \"2026-06-22\"," +
                    "\"price\": 45.85," +
                    "\"genre\": \"Computer Science\"," +
                    "\"isbn\": \"9780123456791\"," +
                    "\"quantity\": 13" +
                    "}"
            )
            .when().put("/books")
            .then()
            .statusCode(200)
            .body(containsString("\"title\":\"The Art of Programming 3.3\","));

        given()
            .when().get("/books")
            .then()
            .statusCode(200)
            .body(
                containsString("\"title\":\"The Art of Programming 3.3\","),
                containsString("\"title\":\"The Power of Words\","),
                containsString("\"title\":\"A Journey Through Time\","),
                containsString("\"title\":\"The Hidden Secrets\","),
                containsString("\"title\":\"The Science of Nature\","),
                containsString("\"title\":\"The Art of Programming2\",")
            );

        given()
                .header("Content-Type", "application/json")
                .body("{" +
                        "\"id\": 9," +
                        "\"title\": \"The Other World\"," +
                        "\"author\": \"James Thompson\"," +
                        "\"pubDate\": \"2021-09-22\"," +
                        "\"price\": 22.33," +
                        "\"genre\": \"Fantasy\"," +
                        "\"isbn\": \"9760123756794\"," +
                        "\"quantity\": 6" +
                        "}"
                )
                .when().put("/books")
                .then()
                .statusCode(409)
                .body(is("Id(9) not found!"));

        given()
                .when().get("/books")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"title\":\"The Art of Programming 3.3\","),
                        containsString("\"title\":\"The Power of Words\","),
                        containsString("\"title\":\"A Journey Through Time\","),
                        containsString("\"title\":\"The Hidden Secrets\","),
                        containsString("\"title\":\"The Science of Nature\","),
                        containsString("\"title\":\"The Art of Programming2\","),
                        not(containsString("\"title\":\"The Other World\","))
                );
    }

    @Test
    @Order(4)
    public void testDelete() {
        given()
            .header("Content-Type", "application/json")
            .body("{" +
                    "\"id\": 6," +
                    "\"title\": \"The Art of Programming2\"," +
                    "\"author\": \"John Smith\"," +
                    "\"pubDate\": \"2025-12-15\"," +
                    "\"price\": 79.99," +
                    "\"genre\": \"Computer Science\"," +
                    "\"isbn\": \"9780123456790\"," +
                    "\"quantity\": 10" +
                    "}"
            )
            .when().delete("/books")
            .then()
            .statusCode(200)
            .body(
                not(containsString("*\"title\":\"The Art of Programming2\",*")),
                containsString("\"title\":\"The Art of Programming 3.3\",")
            );

        given()
            .header("Content-Type", "application/json")
            .body("{" +
                    "\"id\": 9," +
                    "\"title\": \"The Art of Programming 3.3\"," +
                    "\"author\": \"John Smith\"," +
                    "\"pubDate\": \"2026-06-22\"," +
                    "\"price\": 45.85," +
                    "\"genre\": \"Computer Science\"," +
                    "\"isbn\": \"9780123456791\"," +
                    "\"quantity\": 13" +
                    "}"
            )
            .when().delete("/books")
            .then()
            .statusCode(409)
            .body(is("Id(9) not found!"));

        given()
            .when().get("/books")
            .then()
            .statusCode(200)
            .body(
                containsString("\"title\":\"The Art of Programming 3.3\","),
                containsString("\"title\":\"The Power of Words\","),
                containsString("\"title\":\"A Journey Through Time\","),
                containsString("\"title\":\"The Hidden Secrets\","),
                containsString("\"title\":\"The Science of Nature\",")
            );
    }
}
