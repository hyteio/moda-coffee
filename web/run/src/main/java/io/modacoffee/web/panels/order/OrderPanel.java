package io.modacoffee.web.panels.order;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.Order;
import io.modacoffee.web.panels.cart.item.CartItemPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

public class OrderPanel extends Panel implements ModaCoffeeComponent
{
    public OrderPanel(final String id, final IModel<Order> model)
    {
        super(id, model);

        // Render the items in the order as cart items with a quantity, adding a markup id
        // to the output so that Wicket can refresh the view via AJAX,
        var repeater = new RepeatingView("items");
        for (var item : model.getObject().items())
        {
            repeater.add(new CartItemPanel(repeater.newChildId(), () -> item));
        }
        repeater.setOutputMarkupId(true);
        add(repeater);

        // then add a button to cancel the order,
        add(newAjaxButton("cancel", "Cancel", ajax ->
        {
            orderQueue().cancel(model.getObject());
            info("Order cancelled.");
            updateFeedbackPanel(this, ajax);
            ajax.add(repeater);
        }));

        // and one to complete the order.
        add(newAjaxButton("complete", "Complete", ajax ->
        {
            orderQueue().complete(model.getObject());
            info("Order completed.");
            updateFeedbackPanel(this, ajax);
            ajax.add(repeater);
        }));

        setOutputMarkupId(true);
    }
}
