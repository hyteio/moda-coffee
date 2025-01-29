package io.modacoffee.web.pages.menu;

import io.modacoffee.web.model.Inventory;
import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.order.checkout.CheckoutPage;
import io.modacoffee.web.panels.menu.item.MenuItemPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.io.Serial;

public class MenuPage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public MenuPage()
    {
        // Show the list of menu items that are in inventory,
        var repeater = new RepeatingView("items");
        for (var item : Inventory.get().items())
        {
            repeater.add(new MenuItemPanel(repeater.newChildId(), () -> item));
        }
        add(repeater);

        // and add a bookmarkable button link to the checkout page.
        add(newButtonLink("checkout", CheckoutPage.class, "Check Out"));
    }
}
