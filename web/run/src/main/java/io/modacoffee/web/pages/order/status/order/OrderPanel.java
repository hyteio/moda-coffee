package io.modacoffee.web.pages.order.status.order;

import io.modacoffee.web.ModaComponent;
import io.modacoffee.web.mockup.model.Order;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class OrderPanel extends Panel implements ModaComponent
{
    public OrderPanel(final String id, final IModel<Order> model)
    {
        super(id, model);

        var name = model.getObject().name();
        add(new Label("order-name", () -> name));
        add(ajaxLink("order-cancel", "Cancel", ajax ->
        {
            info("Order cancelled.");
            updateFeedbackPanel(this, ajax);
        }));
        add(ajaxLink("order-complete", "Complete", ajax ->
        {
            info("Order completed.");
            updateFeedbackPanel(this, ajax);
        }));
    }
}
