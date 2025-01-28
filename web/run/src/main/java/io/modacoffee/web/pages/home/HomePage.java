package io.modacoffee.web.pages.home;

import io.modacoffee.web.pages.ModaCoffeeWebPage;
import io.modacoffee.web.pages.menu.MenuPage;
import io.modacoffee.web.pages.order.status.OrderStatusPage;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serial;

public class HomePage extends ModaCoffeeWebPage
{
    @Serial
    private static final long serialVersionUID = 1L;

    public HomePage(PageParameters parameters)
    {
        super(parameters);

        add(new Image("background", imageResource(getClass(), "resources/ModaCoffeeBackground.jpg")));
        add(newButtonLink("menu", MenuPage.class, "Explore as a Customer"));
        add(newButtonLink("order-status", OrderStatusPage.class, "Explore as an Employee"));
    }
}
