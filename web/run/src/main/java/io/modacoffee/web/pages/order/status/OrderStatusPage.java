package io.modacoffee.web.pages.order.status;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.panels.order.OrderPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.io.Serial;

public class OrderStatusPage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public OrderStatusPage()
    {
        // Show the list of orders in the order queue
        var repeater = new RepeatingView("orders");
        for (var order : orderQueue().orders())
        {
            repeater.add(new OrderPanel(repeater.newChildId(), () -> order));
        }
        add(repeater);
    }
}
