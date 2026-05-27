Feature: order API

  Scenario: create a normal order without discount via REST
    Given the order API is up
    When I send a POST request to "/api/orders" with customer "Juan Perez" and amount 500.0
    Then the response status should be 201
    And the saved order should have amount 500.0

  Scenario: create an order with 10% discount when amount is greater than 1000
    Given the order API is up
    When I send a POST request to "/api/orders" with customer "Maria Lopez" and amount 2000.0
    Then the response status should be 201
    # Verifica que se aplique la regla de negocio: 2000 * 0.9 = 1800.0
    And the saved order should have amount 1800.0

  Scenario: List all registered orders via REST
    Given the order API is up
    When I send a GET request to "/api/orders"
    Then the response status should be 200