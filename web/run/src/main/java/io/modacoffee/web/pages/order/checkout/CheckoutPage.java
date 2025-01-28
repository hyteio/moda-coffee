package io.modacoffee.web.pages.order.checkout;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.panels.cart.CartPanel;
import org.apache.wicket.markup.html.basic.Label;

import java.io.Serial;

public class CheckoutPage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public CheckoutPage()
    {
        // Retrieve the cart for this user session,
        var cart = cart(this);

        // show the items in their cart,
        add(new CartPanel("cart"));

        // show the total cost,
        add(new Label("total-cost", cart::totalCost));

        // and if the user places the order,
        add(newAjaxButton("place-order", "Place Order", ajax ->
        {
            // add the contents of their cart to the order queue,
            orderQueue().add(cart(this));

            // and thank them.
            info("Thank you for your order!");
            updateFeedbackPanel(this, ajax);
        }));
    }
}
