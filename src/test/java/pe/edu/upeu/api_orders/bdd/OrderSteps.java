package pe.edu.upeu.api_orders.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pe.edu.upeu.api_orders.model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderSteps {

    // Se cambia a comodín (?) para que pueda almacenar tanto un Order individual (POST) como un String/List (GET)
    private ResponseEntity<?> response;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl = "http://localhost:8080";

    @Given("the order API is up")
    public void the_order_api_is_up() {
        // Al ejecutar el test, CucumberSpringConfiguration ya habrá levantado la app en el puerto 8080
    }

    @When("I send a POST request to {string} with customer {string} and amount {double}")
    public void i_send_a_post_request_to_with_customer_and_amount(String path, String customer, Double amount) {
        Order order = new Order(null, customer, amount);
        String url = baseUrl + path;
        response = restTemplate.postForEntity(url, order, Order.class);
    }

    // NUEVO MÉTODO: Hace la petición GET para listar las órdenes
    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String path) {
        String url = baseUrl + path;
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Then("the saved order should have amount {double}")
    public void the_saved_order_should_have_amount(Double expectedAmount) {
        assertNotNull(response.getBody());
        // Casteamos el cuerpo a Order ya que este paso solo se ejecuta tras el POST
        Order order = (Order) response.getBody();
        assertEquals(expectedAmount, order.getAmount(), 0.01);
    }
}