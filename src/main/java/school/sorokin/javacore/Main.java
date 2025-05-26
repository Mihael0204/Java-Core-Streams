package school.sorokin.javacore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product(1L, "Плюшевый мишка", "Toys", new BigDecimal("25.00")),
                new Product(2L, "Развивающая игра", "Toys", new BigDecimal("35.00")),
                new Product(3L, "Сказки Андерсена", "Books", new BigDecimal("15.00")),
                new Product(4L, "Раскраска", "Books", new BigDecimal("5.00")),
                new Product(5L, "Памперсы", "Children's products", new BigDecimal("20.00")),
                new Product(6L, "Детский шампунь", "Children's products", new BigDecimal("8.00")),
                new Product(7L, "Конструктор", "Toys", new BigDecimal("40.00")),
                new Product(8L, "Азбука", "Books", new BigDecimal("120.00")),
                new Product(9L, "Игра престолов", "Books", new BigDecimal("60.00"))
        );

        Random random = new Random();

        List<Customer> customers = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Set<Order> orders = new HashSet<>();

            for (long j = 1; j <= 5; j++) {
                Set<Product> orderProducts = new HashSet<>();
                for (int k = 0; k < 3; k++) {
                    orderProducts.add(products.get(random.nextInt(products.size())));
                }

                Order order = new Order(
                        j,
                        LocalDate.of(2021, 3, 30).minusDays(random.nextInt(90)),
                        LocalDate.of(2021, 3, 30).plusDays(random.nextInt(90)),
                        random.nextBoolean() ? "SHIPPED" : "PENDING",
                        orderProducts
                );
                orders.add(order);
            }

            Customer customer = new Customer(i, "Покупатель " + i, i, orders);
            customers.add(customer);
        }

        //Задание 1
        System.out.println("Задание 1: ");
        List<Product> productList = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0)
                .distinct()
                .toList();
        System.out.println(productList);

        //Задание 2
        System.out.println("Задание 2: ");
        List<Order> orderList = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equals("Children's products")))
                .distinct()
                .toList();
        System.out.println(orderList);

        //Задание 3
        System.out.println("Задание 3: ");
        BigDecimal sumOfPrices = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Toys"))
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(0.9)))
                .distinct()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(sumOfPrices);

        //Задание 4
        System.out.println("Задание 4: ");
        customers.stream()
                .filter(customer -> customer.getLevel().equals(2L))
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)))
                .filter(order -> order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .forEach(System.out::println);

        //Задание 5
        System.out.println("Задание 5: ");
        customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .distinct()
                .limit(2)
                .forEach(System.out::println);

        //Задание 6
        System.out.println("Задание 6: ");
        customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .forEach(System.out::println);

        //Задание 7
        System.out.println("Задание 7: ");
        List<Product> productList1 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println(order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();
        System.out.println(productList1);

        //Задание 8
        System.out.println("Задание 8: ");
        BigDecimal orderSum = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> {
                    LocalDate date = order.getOrderDate();
                    return !date.isBefore(LocalDate.of(2021, 2, 1)) &&
                            !date.isAfter(LocalDate.of(2021, 2, 28));
                })
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(orderSum);

        //Задание 9
        System.out.println("Задание 9: ");
        double averageOrderCheck = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 14)))
                .flatMap(order -> order.getProducts().stream())
                .flatMapToDouble(product -> DoubleStream.of(product.getPrice().doubleValue()))
                .average()
                .orElse(0.0);
        System.out.println(averageOrderCheck);

        //Задание 10
        System.out.println("Задание 10: ");
        DoubleSummaryStatistics bookStats = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .mapToDouble(product -> product.getPrice().doubleValue())
                .summaryStatistics();
        System.out.println("Статистика по книгам:");
        System.out.println("Количество: " + bookStats.getCount());
        System.out.println("Сумма: " + bookStats.getSum());
        System.out.println("Среднее: " + bookStats.getAverage());
        System.out.println("Минимум: " + bookStats.getMin());
        System.out.println("Максимум: " + bookStats.getMax());

        //Задание 11
        System.out.println("Задание 11: ");
        Map<Long, Integer> productsAmount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size(),
                        Integer::sum
                ));
        System.out.println(productsAmount);

        //Задание 12
        System.out.println("Задание 12: ");
        Map<Customer, List<Order>> customerOrders = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> customer.getOrders().stream().toList()
                ));
        System.out.println(customerOrders);

        //Задание 13
        System.out.println("Задание 13: ");
        Map<Order, Double> sumOfOrderProducts = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .distinct()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getProducts().stream()
                                .mapToDouble(product -> product.getPrice().doubleValue()).sum()
                ));
        System.out.println(sumOfOrderProducts);

        //Задание 14
        System.out.println("Задание 14: ");
        Map<String, List<String>> productsByCategories = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));
        System.out.println(productsByCategories);

        //Задание 15
        System.out.println("Задание 15: ");
        Map<String, Product> mostExpensiveProductByCategories = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Product::getCategory,
                        product -> product,
                        (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()) >= 0 ? p1 : p2
                ));
        System.out.println(mostExpensiveProductByCategories);
    }
}
