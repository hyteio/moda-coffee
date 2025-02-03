package io.modacoffee.web.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A list of orders with the ability to {@link #cancel(Order)} and {@link #complete(Order)} an order in the list.
 */
public class OrderQueue
{
    private static final OrderQueue queue = new OrderQueue();

    public static OrderQueue get()
    {
        return queue;
    }

    private final List<Order> orders = new LinkedList<>();

    public List<Order> orders()
    {
        return Collections.unmodifiableList(orders);
    }

    public void add(Cart cart)
    {
        // Add a new order to the queue, making a copy of the cart for the order
        orders.add(new Order(cart.copy()));
    }

    public void cancel(Order order)
    {
        orders.remove(order);
    }

    public void complete(Order order)
    {
        orders.remove(order);
    }
}
