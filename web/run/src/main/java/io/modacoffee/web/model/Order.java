package io.modacoffee.web.model;

import java.util.List;

/**
 * An order is the items in a {@link Cart}
 *
 * @see Cart
 * @see CartItem
 */
public class Order
{
    private final Cart cart;

    public Order(Cart cart)
    {
        this.cart = cart;
    }

    public List<CartItem> items()
    {
        return cart.items();
    }
}
