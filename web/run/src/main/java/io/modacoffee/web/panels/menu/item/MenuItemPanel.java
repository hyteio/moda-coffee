package io.modacoffee.web.panels.menu.item;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.MenuItem;
import io.modacoffee.web.panels.menu.item.picture.MenuItemPicturePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.text.NumberFormat;

public class MenuItemPanel extends Panel implements ModaCoffeeComponent
{
    public MenuItemPanel(final String id, final IModel<MenuItem> model)
    {
        super(id, model);

        add(new Label("name", () -> model.getObject().name()));
        add(new Label("cost", NumberFormat.getCurrencyInstance().format(model.getObject().cost())));
        add(new MenuItemPicturePanel("picture", model));
        add(newAjaxButton("add-to-cart", "Add to Cart", ajax ->
        {
            session(this).cart().add(model.getObject());
            info("Added '" + model.getObject().name() + "' to cart");
            updateFeedbackPanel(this, ajax);
        }));
    }
}
