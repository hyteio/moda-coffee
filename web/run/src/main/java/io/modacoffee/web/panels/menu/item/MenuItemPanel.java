package io.modacoffee.web.panels.menu.item;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.MenuItem;
import io.modacoffee.web.panels.card.CardPanel;
import io.modacoffee.web.panels.menu.item.picture.MenuItemPicturePanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.text.NumberFormat;

public class MenuItemPanel extends Panel implements ModaCoffeeComponent
{
    public MenuItemPanel(final String id, final IModel<MenuItem> model)
    {
        super(id, model);

        var menuItem = model.getObject();

        add(CardPanel.builder("card")
            .child(childId -> new MenuItemPicturePanel(childId, () -> menuItem))
            .text(menuItem.name())
            .text("Cost: " + NumberFormat.getCurrencyInstance().format(menuItem.cost()))
            .ajaxButton("Add to Cart", ajax ->
            {
                cart(this).add(model.getObject());
                info("Added '" + model.getObject().name() + "' to cart");
                updateFeedbackPanel(this, ajax);
            })
            .build());
    }
}
