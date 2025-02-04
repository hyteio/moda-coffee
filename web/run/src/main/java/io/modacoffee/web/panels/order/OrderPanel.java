package io.modacoffee.web.panels.order;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.Order;
import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.panels.card.cards.horizontal.HorizontalCard;
import io.modacoffee.web.panels.cart.item.CartItemPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

public class OrderPanel extends Panel implements ModaCoffeeComponent
{
    public OrderPanel(final String id, Class<? extends ModaCoffeeWebPage> refresh, final IModel<Order> model)
    {
        super(id, model);

        add(HorizontalCard.builder("card")
            .column(column -> column
                .child(childId -> itemsRepeater(childId, model))
            )
            .column(column -> column
                .ajaxButton("Cancel", ajax ->
                {
                    orderQueue().cancel(model.getObject());
                    getWebSession().info("Order cancelled");
                    updateFeedbackPanel(this, ajax);
                    setResponsePage(refresh);
                })
                .ajaxButton("Complete", ajax ->
                {
                    orderQueue().complete(model.getObject());
                    getWebSession().info("Order completed");
                    updateFeedbackPanel(this, ajax);
                    setResponsePage(refresh);
                }))
            .build());

        setOutputMarkupId(true);
    }

    private RepeatingView itemsRepeater(String childId, final IModel<Order> model)
    {
        // Render the items in the order as cart items with a quantity, adding a markup id
        // to the output so that Wicket can refresh the view via AJAX,
        var repeater = new RepeatingView(childId);
        for (var item : model.getObject().items())
        {
            repeater.add(new CartItemPanel(repeater.newChildId(), () -> item));
        }
        add(repeater);
        return repeater;
    }
}
