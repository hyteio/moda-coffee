package io.modacoffee.web.panels.cart.item;

import io.modacoffee.web.components.ModaCoffeeComponent;
import io.modacoffee.web.model.CartItem;
import io.modacoffee.web.panels.card.cards.horizontal.HorizontalCard;
import io.modacoffee.web.panels.menu.item.picture.MenuItemPicturePanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.text.NumberFormat;

public class CartItemPanel extends Panel implements ModaCoffeeComponent
{
    public CartItemPanel(final String id, final IModel<CartItem> model)
    {
        super(id, model);

        var cartItem = model.getObject();
        var menuItem = cartItem.item();

        add(HorizontalCard.builder("card")
            .column(column -> column
                .child(childId -> new MenuItemPicturePanel(childId, () -> menuItem)))
            .column(column -> column
                .text(menuItem.name())
                .text("Cost: " + NumberFormat.getCurrencyInstance().format(menuItem.cost()))
                .text("Quantity: " + cartItem.quantity()))
            .build());
    }
}
