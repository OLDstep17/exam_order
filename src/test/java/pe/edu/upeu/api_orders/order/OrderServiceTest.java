package pe.edu.upeu.api_orders.order;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upeu.api_orders.model.Order;
import pe.edu.upeu.api_orders.repository.OrderRepository;
import pe.edu.upeu.api_orders.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    @Test
    void whenAmountIsLessThan1000_thenSaveWithoutDiscount() {
        // Arrange: Orden con monto menor o igual a 1000 (No aplica descuento)
        Order order = new Order(null, "Juan Perez", 500.0);

        // Configuramos el Mock para que devuelva la misma orden al guardar
        when(repository.save(any(Order.class))).thenReturn(order);

        // Act: Ejecutamos el método del servicio
        Order saved = service.createOrder(order);

        // Assert: Validamos que los datos se mantengan intactos
        assertNotNull(saved);
        assertEquals("Juan Perez", saved.getCustomer());
        assertEquals(500.0, saved.getAmount()); // El monto sigue igual
        verify(repository, times(1)).save(order);
    }

    @Test
    void whenAmountIsGreaterThan1000_thenApplyTenPercentDiscount() {
        // Arrange: Orden con un monto mayor a 1000 (Debe aplicar 10% de descuento)
        Order order = new Order(null, "Maria Lopez", 2000.0);

        // El servicio modificará el objeto interno reduciendo el monto a 1800.0 (2000 * 0.9)
        // Configuramos el Mock para retornar la orden procesada por el repositorio
        when(repository.save(any(Order.class))).thenReturn(order);

        // Act: Ejecutamos el método
        Order saved = service.createOrder(order);

        // Assert: Validamos que la lógica de negocio del descuento funcionó
        assertNotNull(saved);
        assertEquals("Maria Lopez", saved.getCustomer());
        assertEquals(1800.0, saved.getAmount()); // 2000 - 10% = 1800
        verify(repository, times(1)).save(order);
    }
}