package io.modacoffee.web.panels.menu.item.picture;

import io.modacoffee.web.model.MenuItem;
import io.modacoffee.web.panels.ModaCofeePanel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;

public class MenuItemPicturePanel extends ModaCofeePanel
{
    public MenuItemPicturePanel(final String id, final IModel<MenuItem> model)
    {
        super(id, model);

        add(new Image("picture", imageResource(getClass(), "resources/" + model.getObject().name() + ".jpg")));
    }
}
