package school.sorokin.javacore;

import java.util.Objects;
import java.util.Set;

public class Customer {
    private final Long id;
    private final String name;
    private final Long level;
    private final Set<Order> orders;

    public Customer(Long id, String name, Long level, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getLevel() {
        return level;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(level, customer.level) && Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level, orders);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", orders=" + orders +
                '}';
    }


}
