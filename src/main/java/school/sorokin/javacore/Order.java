package school.sorokin.javacore;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Order {
    private final Long id;
    private final LocalDate orderDate;
    private final LocalDate deliveryDate;
    private final String status;
    private final Set<Product> products;

    public Order(Long id, LocalDate orderDate, LocalDate deliveryDate, String status, Set<Product> products) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(status, order.status) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, deliveryDate, status, products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", products=" + products +
                '}';
    }


}
