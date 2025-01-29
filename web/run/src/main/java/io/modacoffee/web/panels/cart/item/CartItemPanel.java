package io.modacoffee.web.panels.cart.item;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.CartItem;
import io.modacoffee.web.panels.menu.item.picture.MenuItemPicturePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.text.NumberFormat;

public class CartItemPanel extends Panel implements ModaCoffeeComponent
{
    public CartItemPanel(final String id, final IModel<CartItem> model)
    {
        super(id, model);

        add(new Label("name", () -> model.getObject().item().name()));
        add(new Label("cost", NumberFormat.getCurrencyInstance().format(model.getObject().item().cost())));
        add(new MenuItemPicturePanel("picture", () -> model.getObject().item()));
        add(new Label("quantity", () -> model.getObject().quantity()));
    }
}
