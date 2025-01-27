package io.modacoffee.web.pages.order.status;

import io.modacoffee.web.mockup.model.Order;
import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.order.status.order.OrderPanel;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.io.Serial;
import java.util.List;

public class OrderStatusPage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public OrderStatusPage()
    {
        var repeater = new RepeatingView("orders-repeater");
        for (var order : orders())
        {
            repeater.add(new OrderPanel(repeater.newChildId(), () -> order));
        }
        add(repeater);
    }

    private List<Order> orders()
    {
        return List.of(order("Coffee"), order("Wood Coffee Mug"));
    }

    private Order order(String name)
    {
       return new Order(name);
    }
}
