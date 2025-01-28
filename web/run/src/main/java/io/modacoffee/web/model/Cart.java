package io.modacoffee.web.model;

import io.modacoffee.web.run.ModaCoffeeSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * Holds a list of {@link CartItem}s. This object is serializable so that it can be placed in the user's session.
 *
 * @see CartItem
 * @see ModaCoffeeSession
 */
public class Cart implements Serializable
{
    /**
     * The list of items in this cart
     */
    private final List<CartItem> items = new ArrayList<>();

    /**
     * @return Any item matching the given item in this cart
     */
    public Optional<CartItem> find(MenuItem item)
    {
        return items.stream().filter(at -> at.item().equals(item)).findAny();
    }

    /**
     * Adds the given item to this cart. If the item is already in this cart, its quantity is increased by one.
     */
    public void add(MenuItem item)
    {
        var existing = find(item);
        if (existing.isPresent())
        {
            existing.get().incrementQuantity();
        }
        else
        {
            items.add(new CartItem(item));
        }
    }

    /**
     * @return The items in this cart as an immutable list
     */
    public List<CartItem> items()
    {
        return unmodifiableList(items);
    }

    /**
     * @return The total cost of all items in this cart
     */
    public double totalCost()
    {
        return items.stream().mapToDouble(item -> item.item().cost()).sum();
    }
}
