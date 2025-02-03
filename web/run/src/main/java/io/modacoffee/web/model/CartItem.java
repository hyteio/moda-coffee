package io.modacoffee.web.model;

/**
 * A {@link CartItem} is a {@link MenuItem} with a quantity.
 *
 * @see MenuItem
 */
public class CartItem
{
    private final MenuItem item;

    private int quantity;

    public CartItem(MenuItem item)
    {
        this.item = item;
        this.quantity = 1;
    }

    public MenuItem item()
    {
        return item;
    }

    public int quantity()
    {
        return quantity;
    }

    public void incrementQuantity()
    {
        quantity++;
    }
}
