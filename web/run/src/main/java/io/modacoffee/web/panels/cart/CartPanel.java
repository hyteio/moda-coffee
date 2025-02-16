package io.modacoffee.web.panels.cart;

import io.modacoffee.web.panels.ModaCofeePanel;
import io.modacoffee.web.panels.cart.item.CartItemPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

public class CartPanel extends ModaCofeePanel
{
    public CartPanel(String id)
    {
        super(id);

        // Create a repeating view of the items in the cart in the user's session, adding a markup id to
        // the output so the view can be refreshed with AJAX,
        var repeater = new RepeatingView("items");
        for (var item : cart(this).items())
        {
            repeater.add(new CartItemPanel(repeater.newChildId(), () -> item));
        }
        repeater.setOutputMarkupId(true);
        add(repeater);

        // and also allow the panel itself to be dynamically updated.
        setOutputMarkupId(true);
    }
}
