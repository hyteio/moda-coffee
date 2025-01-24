package io.modacoffee.web.pages.home;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import org.apache.wicket.markup.html.image.Image;

import java.io.Serial;

public class HomePage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public HomePage()
    {
        add(new Image("background", imageResource("background.jpg")));
        add(bookmarkablePageLink("menu", MenuPage.class));
        add(bookmarkablePageLink("order-status", OrderStatusPage.class));
    }
}
