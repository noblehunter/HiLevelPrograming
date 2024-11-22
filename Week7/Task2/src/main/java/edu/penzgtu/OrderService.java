package edu.penzgtu;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderService {

    public static double calculateTotalSalesInFebruary2020(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getOrderDate() != null &&
                        order.getOrderDate().getYear() == 2020 &&
                        order.getOrderDate().getMonthValue() == 2) // Фильтрация по дате
                .flatMap(order -> order.getProducts().stream()) // Получаем поток продуктов из заказов
                .mapToDouble(Product::getPrice) // Получаем цену каждого продукта
                .sum(); // Суммируем цены
    }

    public static void main(String[] args) {
        // Пример создания заказов и продуктов
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setCategory("Category 1");
        product1.setPrice(100.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setCategory("Category 2");
        product2.setPrice(150.0);

        Set<Product> products1 = new HashSet<>();
        products1.add(product1);
        products1.add(product2);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDate(LocalDate.of(2020, 2, 15)); // Заказ в феврале
        order1.setProducts(products1);

        Set<Product> products2 = new HashSet<>();
        products2.add(product1);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDate(LocalDate.of(2020, 3, 1)); // Заказ в марте
        order2.setProducts(products2);

        List<Order> orders = List.of(order1, order2);

        double totalSales = calculateTotalSalesInFebruary2020(orders);
        System.out.println("Общая сумма всех заказов в феврале 2020 года: " + totalSales);
    }
}
