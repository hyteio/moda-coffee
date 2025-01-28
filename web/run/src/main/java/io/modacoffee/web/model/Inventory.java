package io.modacoffee.web.model;

import java.util.List;

/**
 * Holds the list of items that are in stock. Our imaginary store never runs out of items!
 */
public class Inventory
{
    private static final Inventory inventory = new Inventory();

    public static Inventory get()
    {
        return inventory;
    }

    public List<MenuItem> items()
    {
        return List.of(
            item("Cappuccino", 9.99),
            item("Cold Brew", 8.99),
            item("Drip Coffee", 7.99),
            item("Hot Tea", 9.99),
            item("Latte", 11.99)
        );
    }

    private MenuItem item(String name, double price)
    {
        return new MenuItem(name, price);
    }
}
