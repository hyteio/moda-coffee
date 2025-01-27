package io.modacoffee.web.pages.menu.item;

import io.modacoffee.web.ModaComponent;
import io.modacoffee.web.v5.MenuItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.function.Function;

public class MenuItemPanel extends Panel implements ModaComponent
{
    public MenuItemPanel(final String id, final IModel<MenuItem> model, Function<String, ResourceReference> resources)
    {
        super(id, model);

        var name = model.getObject().getName();

        add(new Label("item-name", () -> name));
        add(new Label("item-cost", "$9,000"));
        add(new Image("item-picture", resources.apply(name)));
        add(ajaxLink("item-add-to-cart", "Add to Cart", ajax ->
        {
            info("Item added");
            updateFeedbackPanel(this, ajax);
        }));
    }
}
