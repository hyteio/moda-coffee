package io.modacoffee.web.pages.menu;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.item.MenuItemPanel;
import io.modacoffee.web.v5.MenuItem;
import org.apache.wicket.markup.repeater.RepeatingView;

import java.io.Serial;
import java.util.List;

public class MenuPage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public MenuPage()
    {
        var repeater = new RepeatingView("items-repeater");
        for (var item : items())
        {
            repeater.add(new MenuItemPanel(repeater.newChildId(), () -> item,
                name -> MenuPage.this.imageResource("resources/" + name + ".jpg")));
        }
        add(repeater);
    }

    private List<MenuItem> items()
    {
        return List.of(item("Coffee"), item("Wood Coffee Mug"));
    }

    private MenuItem item(String name)
    {
        var item = new MenuItem();
        item.setName(name);
        return item;
    }
}
